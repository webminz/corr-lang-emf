package io.corrlang.emfintegration;

import com.google.common.collect.Sets;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.graph.trees.Tree;
import no.hvl.past.names.Name;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class EcoreParser implements EMFRegistry.URIToNameResolve {

    //    // EMF specific constants
    private static final String XMI_NS = "http://www.omg.org/XMI";
    private static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String ECORE_NS = "http://www.eclipse.org/emf/2002/Ecore";
    private static final String ECORE_PACKAGE = "EPackage";
    private static final String ECORE_CLASS = "EClass";
    private static final String ECORE_CLASSIFIERS = "eClassifiers";
    private static final String ECORE_FEATURES = "eStructuralFeatures";
    private static final String ECORE_DATATYPE = "EDataType";
    private static final String ECORE_ATTRIBUTE = "EAttribute";
    private static final String ECORE_REF = "EReference";
    private static final String CROSS_REF_PREFIX = "#//";

    private final GraphBuilders builders;
    private final EMFRegistry registry;
    private Tree ecoreTree;
    private Map<String, Classifier> classifiers  = new LinkedHashMap<>();

    private String ecorePrefix = "ecore";
    private String xmiPrefix = "xmi";
    private String xsiPrefix = "xsi";
    private String currentPath;
    private boolean isSinglePackageRoot;
    private int noOfOPackages;
    private Classifier currentClassifier;
    private Map<Triple, EContainment> containments = new HashMap<>();
    private Set<Name> includedNodeNames = new HashSet<>();



    private final class Classifier {
        private String name;
        private Name formalName;
        private List<String> superTypes = new ArrayList<>();
        private boolean isAbstract = false;
        private boolean isEnum = false;
        private boolean isDataType = false;
        private List<StructFeature> features = new ArrayList<>();
        private List<String> enumLiterals = new ArrayList<>();

        public String getName() {
            return name;
        }

        public Name getFormalName() {
            return formalName;
        }

        public void setFormalName(Name formalName) {
            this.formalName = formalName;
        }

        public List<String> getSuperTypes() {
            return superTypes;
        }

        public boolean isAbstract() {
            return isAbstract;
        }

        public void setEnum(boolean anEnum) {
            isEnum = anEnum;
        }

        public void setDataType(boolean dataType) {
            isDataType = dataType;
        }

        public List<StructFeature> getFeatures() {
            return features;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAbstract(boolean anAbstract) {
            isAbstract = anAbstract;
        }

        public List<String> getEnumLiterals() {
            return enumLiterals;
        }
    }

    private static class StructFeature {
        private String name;
        private Name formalName;
        private Name formalTarget;
        private boolean isAttribute;
        private String targetType;
        private boolean isContainment = false;

        private int lowerBound = 0;
        private int upperBound = 1;
        private boolean ordered = true;
        private boolean unique = false;
        private String opposite;

        public String getName() {
            return name;
        }

        public Name getFormalName() {
            return formalName;
        }

        public void setFormalName(Name formalName) {
            this.formalName = formalName;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAttribute() {
            return isAttribute;
        }

        public void setAttribute(boolean attribute) {
            isAttribute = attribute;
        }

        public String getTargetType() {
            return targetType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public int getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(int lowerBound) {
            this.lowerBound = lowerBound;
        }

        public int getUpperBound() {
            return upperBound;
        }

        public void setUpperBound(int upperBound) {
            this.upperBound = upperBound;
        }

        public boolean isOrdered() {
            return ordered;
        }

        public void setOrdered(boolean ordered) {
            this.ordered = ordered;
        }

        public boolean isUnique() {
            return unique;
        }

        public void setUnique(boolean unique) {
            this.unique = unique;
        }

        public String getOpposite() {
            return opposite;
        }

        public void setOpposite(String opposite) {
            this.opposite = opposite;
        }

        public boolean isContainment() {
            return isContainment;
        }

        public void setContainment(boolean containment) {
            isContainment = containment;
        }
    }


    public EcoreParser(Universe universe, EMFRegistry registry) {
        this.builders = new GraphBuilders(universe, true, false);
        this.registry = registry;
    }


    public EcoreSystem parse(InputStream inputStream, String fileName, Name name) throws EmfTechSpaceException {
        this.builders.clear();
        try {
            // TODO extra URLs if defined in the EPackage
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            Name ecoreName = name;

            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.getEventType() == XMLStreamConstants.START_DOCUMENT) {
                boolean rootElementFound = false;
                while (xmlEventReader.hasNext() && !rootElementFound) {
                    xmlEvent = xmlEventReader.nextEvent();
                    if (xmlEvent.getEventType() == XMLStreamConstants.START_ELEMENT) {
                        rootElementFound = true;
                    }
                }
            }

            StartElement rootElement = xmlEvent.asStartElement();
            Iterator nsIterator = rootElement.getNamespaces();
            while (nsIterator.hasNext()) {
                Namespace next = (Namespace) nsIterator.next();
                if (next.getNamespaceURI().equals(ECORE_NS)) {
                    ecorePrefix = next.getPrefix();
                }
                if (next.getNamespaceURI().equals(XMI_NS)) {
                    xmiPrefix = next.getPrefix();
                }
                if (next.getNamespaceURI().equals(XSI_NS)) {
                    xsiPrefix = next.getPrefix();
                }
            }

            if (rootElement.getName().getLocalPart().equals(ECORE_PACKAGE) && rootElement.getName().getPrefix().equals(ecorePrefix)) {
                this.isSinglePackageRoot = true;
                this.currentPath = "";
                ecoreName = Name.identifier(rootElement.getAttributeByName(new QName("name")).getValue());
            } else if (rootElement.getName().getLocalPart().equals("XMI") && rootElement.getName().getPrefix().equals(xmiPrefix)) {
                this.isSinglePackageRoot = false;
            }

            while (xmlEventReader.hasNext()) {
                xmlEvent = xmlEventReader.nextEvent();
                switch (xmlEvent.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = xmlEvent.asStartElement();
                        if (startElement.getName().getLocalPart().equals(ECORE_PACKAGE)) {
                            handlePackage(startElement);
                        } else if (startElement.getName().getLocalPart().equals(ECORE_CLASSIFIERS)) {
                            handleClassifier(startElement);
                        } else if (startElement.getName().getLocalPart().equals(ECORE_FEATURES)) {
                            handleFeature(startElement);
                        } else if (startElement.getName().getLocalPart().equals("eLiterals")) {
                            handleLiteral(startElement);
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        stepUp();
                        break;
                    default:
                        break;
                }
            }

            for (Classifier clas : this.classifiers.values()) {
                for (String supers : clas.getSuperTypes()) {
                    Name superTarget = lookup(supers);
                    builders.map(clas.formalName, superTarget);
                }
                if (!clas.isEnum && !clas.isDataType) {
                    for (StructFeature feature : clas.getFeatures()) {
                        Name target = lookup(feature.getTargetType());
                        feature.formalTarget = target;
                        builders.edge(clas.formalName, feature.formalName, target);
                    }
                }
            }

            builders.graph(ecoreName.absolute());

            for (Name baseTypeName : this.includedNodeNames) {
                if (EcoreDirectives.BOOL_TYPE_NAMES.contains(baseTypeName)) {
                    builders.startDiagram(BoolDT.getInstance());
                    builders.map(Universe.ONE_NODE_THE_NODE, baseTypeName);
                    builders.endDiagram(baseTypeName);
                } else if (EcoreDirectives.STRING_TYPE_NAMES.contains(baseTypeName)) {
                    builders.startDiagram(StringDT.getInstance());
                    builders.map(Universe.ONE_NODE_THE_NODE, baseTypeName);
                    builders.endDiagram(baseTypeName);
                } else if (EcoreDirectives.FLOATING_POINT_TYPE_NAMES.contains(baseTypeName)) {
                    builders.startDiagram(FloatDT.getInstance());
                    builders.map(Universe.ONE_NODE_THE_NODE, baseTypeName);
                    builders.endDiagram(baseTypeName);
                } else if (EcoreDirectives.INTEGER_TYPE_NAMES.contains(baseTypeName)) {
                    builders.startDiagram(IntDT.getInstance());
                    builders.map(Universe.ONE_NODE_THE_NODE, baseTypeName);
                    builders.endDiagram(baseTypeName);
                } else if (EcoreDirectives.OTHER_BASE_TYPE_NAME.contains(baseTypeName)) {
                    // TODO maybe diagram for custom datatypes
                    builders.startDiagram(DataTypePredicate.getInstance());
                    builders.map(Universe.ONE_NODE_THE_NODE, baseTypeName);
                    builders.endDiagram(baseTypeName);
                }
            }

            for (Classifier clas : this.classifiers.values()) {
                if (clas.isEnum) {
                    List<Name> literals = clas.enumLiterals.stream().map(Name::identifier).collect(Collectors.toList());
                    builders.startDiagram(EnumValue.getInstance(literals));
                    builders.map(Universe.ONE_NODE_THE_NODE, clas.formalName);
                    builders.endDiagram(Name.identifier(clas.name + "_ENUM"));
                } else if (clas.isDataType) {
                    builders.startDiagram(DataTypePredicate.getInstance());
                    builders.map(Universe.ONE_NODE_THE_NODE, clas.formalName);
                    builders.endDiagram(Name.identifier(clas.name + "_DT"));
                } else {
                    if (clas.isAbstract) {
                        builders.startDiagram(AbstractType.getInstance());
                        builders.map(Universe.ONE_NODE_THE_NODE, clas.formalName);
                        builders.endDiagram(Name.identifier(clas.name + "{abstract}"));
                    }

                    for (StructFeature feature : clas.getFeatures()) {
                        if (feature.lowerBound != 0 || feature.upperBound != -1) {
                            builders.startDiagram(TargetMultiplicity.getInstance(feature.lowerBound, feature.upperBound));
                            builders.map(Universe.ARROW_SRC_NAME, clas.formalName);
                            builders.map(Universe.ARROW_LBL_NAME, feature.formalName);
                            builders.map(Universe.ARROW_TRG_NAME, feature.formalTarget);
                            builders.endDiagram(Name.identifier(feature.name + "[" + feature.lowerBound + ".." + feature.upperBound + "]"));
                        }
                        if (feature.isOrdered()) {
                            builders.startDiagram(Ordered.getInstance());
                            builders.map(Universe.ARROW_SRC_NAME, clas.formalName);
                            builders.map(Universe.ARROW_LBL_NAME, feature.formalName);
                            builders.map(Universe.ARROW_TRG_NAME, feature.formalTarget);
                            builders.endDiagram(Name.identifier(feature.name + "{ordered}"));
                        }
                        if (feature.isUnique()) {
                            builders.startDiagram(Unique.getInstance());
                            builders.map(Universe.ARROW_SRC_NAME, clas.formalName);
                            builders.map(Universe.ARROW_LBL_NAME, feature.formalName);
                            builders.map(Universe.ARROW_TRG_NAME, feature.formalTarget);
                            builders.endDiagram(Name.identifier(feature.name + "{unique}"));
                        }
                        if (feature.opposite != null && !includedNodeNames.contains(feature.formalName)) {
                            Name opp = lookup(feature.opposite);
                            builders.startDiagram(Invert.getInstance());
                            builders.map(Universe.CYCLE_FWD.getSource(), clas.formalName);
                            builders.map(Universe.CYCLE_BWD.getSource(), feature.formalTarget);
                            builders.map(Universe.CYCLE_FWD.getLabel(), feature.formalName);
                            builders.map(Universe.CYCLE_BWD.getLabel(), opp);
                            builders.endDiagram(Name.identifier(feature.name + "#" + opp.printRaw()));
                            includedNodeNames.add(opp);

                        }
                        if (feature.isContainment) {
                            builders.startDiagram(Acyclicity.getInstance());
                            builders.map(Universe.ARROW_SRC_NAME, clas.formalName);
                            builders.map(Universe.ARROW_LBL_NAME, feature.formalName);
                            builders.map(Universe.ARROW_TRG_NAME, feature.formalTarget);
                            builders.endDiagram(Name.identifier(feature + "_CONTAINMENT1"));
                            builders.startDiagram(SourceMultiplicity.getInstance(0,1));
                            builders.map(Universe.ARROW_SRC_NAME, clas.formalName);
                            builders.map(Universe.ARROW_LBL_NAME, feature.formalName);
                            builders.map(Universe.ARROW_TRG_NAME, feature.formalTarget);
                            builders.endDiagram(Name.identifier(feature + "_CONTAINMENT2"));
                            Triple t = Triple.edge(clas.formalName, feature.formalName, feature.formalTarget);
                            if (feature.opposite != null) {
                                Name opp = lookup(feature.opposite);
                                EContainment eContainment = new EContainment(true, t, Triple.edge(t.getTarget(), opp, t.getSource()), Sets.newHashSet(
                                        Name.identifier(feature + "_CONTAINMENT1"),
                                        Name.identifier(feature + "_CONTAINMENT2"),
                                        Name.identifier(feature.name + "#" + opp.printRaw())));
                                this.containments.put(t, eContainment);
                                this.containments.put(Triple.edge(t.getTarget(),opp, t.getSource()), eContainment);
                            } else {
                                this.containments.put(t, new EContainment(false, t, null, Sets.newHashSet(Name.identifier(feature + "_CONTAINMENT1"), Name.identifier(feature + "_CONTAINMENT2"))));
                            }
                        }

                        includedNodeNames.add(feature.formalName);
                    }
                }

            }

            builders.sketch(ecoreName);
            return new EcoreSystem(fileName, builders.getResult(Sketch.class), new HashMap<>(), registry, containments);
        } catch (XMLStreamException | GraphError e) {
            throw new EmfTechSpaceException(e);
        }
    }

    private Name lookup(String uri) throws EmfTechSpaceException {
        Optional<Name> name = registry.resolveURI(this, uri);
        if (name.isPresent()) {
            if (!includedNodeNames.contains(name.get())) {
                builders.node(name.get());
            }
            return name.get();
        }
        throw new EmfTechSpaceException("The type reference '" + uri + "' cannot be resolved");
    }


    private void handleLiteral(StartElement startElement) {
        String name = startElement.getAttributeByName(new QName("name")).getValue();
        this.currentClassifier.getEnumLiterals().add(name);
    }


    @Override
    public Optional<Name> resolveURI(String uri) {
        if (!isSinglePackageRoot) {
            if (classifiers.containsKey(uri)) {
                return Optional.of(classifiers.get(uri).getFormalName());
            } else {
                int endIndex = uri.lastIndexOf('/');
                String prefix = uri.substring(0, endIndex);
                if (classifiers.containsKey(prefix)) {
                    String localName = uri.substring(endIndex + 1);
                    return classifiers.get(prefix).getFeatures().stream().filter(f -> f.getName().equals(localName)).findFirst().map(StructFeature::getFormalName);
                } else {
                    return Optional.empty();
                }
            }
        }
        if (uri.startsWith("//")) {
            uri = uri.substring(2);
        }
        if (uri.contains("/")) {
            int endIndex = uri.lastIndexOf('/');
            String prefix = uri.substring(0, endIndex);
            if (classifiers.containsKey(prefix)) {
                String localName = uri.substring(endIndex + 1);
                return classifiers.get(prefix).getFeatures().stream().filter(f -> f.getName().equals(localName)).findFirst().map(StructFeature::getFormalName);
            } else {
                return Optional.empty();
            }
        } else {
            if (classifiers.containsKey(uri)) {
                return Optional.of(this.classifiers.get(uri).getFormalName());
            }
        }
        return Optional.empty();
    }


    private void stepUp() {

    }

    private void handleFeature(StartElement startElement) {
        StructFeature feature = new StructFeature();
        this.currentClassifier.getFeatures().add(feature);
        String name = startElement.getAttributeByName(new QName("name")).getValue();
        feature.setName(name);
        String type = startElement.getAttributeByName(new QName(XSI_NS, "type")).getValue();
        if (type.equals(ecorePrefix + ":" + ECORE_ATTRIBUTE)) {
            feature.setAttribute(true);
        } else {
            feature.setAttribute(false);
        }
        String eType = startElement.getAttributeByName(new QName("eType")).getValue();
        feature.setTargetType(eType);
        feature.setFormalName(Name.identifier(name).prefixWith(this.currentClassifier.getFormalName()));
        if (startElement.getAttributeByName(new QName("lowerBound")) != null) {
            feature.setLowerBound(Integer.parseInt(startElement.getAttributeByName(new QName("lowerBound")).getValue()));
        }
        if (startElement.getAttributeByName(new QName("upperBound")) != null) {
            feature.setUpperBound(Integer.parseInt(startElement.getAttributeByName(new QName("upperBound")).getValue()));
        }
        if (startElement.getAttributeByName(new QName("ordered")) != null) {
            feature.setOrdered(Boolean.parseBoolean(startElement.getAttributeByName(new QName("ordered")).getValue()));
        }
        if (startElement.getAttributeByName(new QName("unique")) != null) {
            feature.setOrdered(Boolean.parseBoolean(startElement.getAttributeByName(new QName("unique")).getValue()));
        }
        if (startElement.getAttributeByName(new QName("eOpposite")) != null) {
            feature.setOpposite(startElement.getAttributeByName(new QName("eOpposite")).getValue());
        }
        if (startElement.getAttributeByName(new QName("containment")) != null) {
            feature.setContainment(Boolean.parseBoolean(startElement.getAttributeByName(new QName("containment")).getValue()));
        }
    }


    private void handleClassifier(StartElement startElement) {
        this.currentClassifier = new Classifier();
        String name = startElement.getAttributeByName(new QName("name")).getValue();
        this.currentClassifier.setName(name);
        classifiers.put(this.currentPath + name, currentClassifier);
        if (startElement.getAttributeByName(new QName("abstract")) != null) {
            this.currentClassifier.isAbstract = Boolean.parseBoolean(startElement.getAttributeByName(new QName("abstract")).getValue());
        }
        if (startElement.getAttributeByName(new QName("eSuperTypes")) != null) {
            for (String supers : startElement.getAttributeByName(new QName("eSuperTypes")).getValue().split(" ")) {
                this.currentClassifier.getSuperTypes().add(supers);
            }
        }
        String type = startElement.getAttributeByName(new QName(XSI_NS, "type")).getValue();
        if (type.equals(ecorePrefix + ":" + "EEnum")) {
            this.currentClassifier.setEnum(true);
        } else if (type.equals(ecorePrefix + ":" + ECORE_DATATYPE)) {
            this.currentClassifier.setDataType(true);
        }
        this.currentClassifier.setFormalName(Name.identifier(name));
        includedNodeNames.add(currentClassifier.getFormalName());
        builders.node(currentClassifier.getFormalName());

    }

    private void handlePackage(StartElement startElement) {
        if (!this.isSinglePackageRoot) {
            currentPath = "/" + (noOfOPackages++) + "/";
        }
    }

}
