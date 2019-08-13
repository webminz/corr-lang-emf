package no.hvl.past.emf;

import no.hvl.past.graph.*;
import no.hvl.past.graph.names.Name;
import no.hvl.past.graph.names.PrintingStrategy;
import no.hvl.past.graph.operations.DiagrammaticGraph;
import no.hvl.past.graph.operations.Predefined;
import no.hvl.past.graph.techspace.TechnologicalSpace;
import no.hvl.past.graph.techspace.UnsupportedException;
import no.hvl.past.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class EcoreTechSpace implements TechnologicalSpace {

    // TODO: This strategy probably has to be aware of namespaces
    private final static PrintingStrategy STRATEGY = new PrintingStrategy() {
        @Override
        public String empty() {
            return "";
        }

        @Override
        public String sequentialComposition(String fst, String snd) {
            return null;
        }

        @Override
        public String coproduct(String fst, String snd) {
            return null;
        }

        @Override
        public String pullback(String applicant, String target) {
            return null;
        }

        @Override
        public String merge(Collection<String> transformedNames) {
            return null;
        }

        @Override
        public String transform(Name n, String prefix) {
            return n.toString();
        }

        @Override
        public String typedBy(String element, String type) {
            return element;
        }
    };

    private static final String ALL_CLASSIFIERS_QUERY = "//eClassifiers";
    private static final String ALL_FEATURE_QUERY = "//eClassifiers//eStructuralFeatures";

    private static class EcoreGraphAdapter implements AbstractGraph {

        private final Document xmlDocument;

        public EcoreGraphAdapter(Document xmlDocument) {
            this.xmlDocument = xmlDocument;
        }

        @Override
        public boolean contains(Triple triple) {
            return false;
        }

        @Override
        public boolean contains(Name name) {
            return false;
        }


        private Name extractReferenceValue(String val) {
            if (val.contains("#//")) {
                int i = val.lastIndexOf("#//");
                return Name.identifier(val.substring(i + 3));
            }
            return Name.identifier(val);
        }

        @Override
        public Set<Triple> outgoing(Name name) {
            String xpath = ALL_CLASSIFIERS_QUERY + "[" + "@name=\"" + name.print(STRATEGY) +  "\"]" + "//eStructuralFeatures";
            XPath xPathObj = XPathFactory.newInstance().newXPath();
            Set<Triple> result = new HashSet<>();
            try {
                NodeList nodeList = (NodeList) xPathObj.compile(xpath).evaluate(xmlDocument, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node item = nodeList.item(i);
                    result.add(new Triple(name,
                            extractReferenceValue(item.getAttributes().getNamedItem("name").getNodeValue()),
                            extractReferenceValue(item.getAttributes().getNamedItem("eType").getNodeValue())));
                }
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        public Set<Triple> incoming(Name name) {
            return null;
        }

        @Override
        public Iterator<Triple> iterator() {
            return null;
        }

        @Override
        public Name getName() {
            return null;
        }
    }


    private static class EcoreMorphismAdapter implements AbstractMorphism {

        private final Document xmlDocument;
        private final EcoreGraphAdapter domain;

        public EcoreMorphismAdapter(Document xmlDocument) {
            this.xmlDocument = xmlDocument;
            this.domain = new EcoreGraphAdapter(xmlDocument);
        }

        @Override
        public AbstractGraph getDomain() {
            return domain;
        }

        @Override
        public AbstractGraph getCodomain() {
            return ECORE_MM.effectiveGraph();
        }

        @Override
        public boolean definedAt(Name name) {
            return false;
        }

        @Override
        public boolean definedAt(Triple triple) {
            return false;
        }

        @Override
        public Optional<Name> apply(Name name) {
            return Optional.empty();
        }

        @Override
        public Optional<Triple> apply(Triple triple) {
            return Optional.empty();
        }

        @Override
        public Set<Triple> select(Triple triple) {
            return null;
        }

        @Override
        public Set<Triple> select(Set<Triple> set) {
            return null;
        }

        @Override
        public Iterator<Tuple> iterator() {
            return null;
        }

        @Override
        public Name getName() {
            return null;
        }
    }


    // TODO complete
    private static DiagrammaticGraph ECORE_MM =
            new DiagrammaticGraph.Builder(new Graph.Builder("Ecore")
                    .node("EClass")
                    .node("EClassifier")
                    .node("EDataType")
                    .node("EStructuralFeature")
                    .node("EReference")
                    .node("EAttribute")
                    .node("EClassifier")
                    .edge("EClass", "EClass.extends", "EClassifier")
                    .edge("EDataType", "EDataType.extends", "EClassifier")
                    .edge("EReference", "EReference.extends", "EStructuralFeature")
                    .edge("EAttribute", "EAttribute.extends", "EStructuralFeature")
                    .edge("EClass", "eStructuralFeatures", "EStructuralFeature")
                    .edge("EAttribute", "eAttributeType", "EDataType")
                    .edge("EReference", "eReferenceType", "EClass")
                    .edge("EClass", "eSuperTypes", "EClass")
                    .build())
                    .operation(Predefined.INHERITANCE_OP_NAME)
                    .bind("i", "ObjectType")
                    .bind("s", "ObjectType.extends")
                    .bind("t", "Type")
                    .apply()
                    .build();


    private Document loadXMLFile(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(file);
    }


    @Override
    public String name() {
        return "EMF_ECORE";
    }

    @Override
    public Optional<String> specifiedBy() {
        return Optional.of("EMF_ECORE");
    }

    @Override
    public List<String> realizedBy() {
        return Collections.singletonList("EMF_XMI");
    }

    @Override
    public Pair<AbstractMorphism, DiagrammaticGraph> load(String location) throws UnsupportedException {
        File f = new File(location);
        if (f.exists()) {
            try {
                Document document = loadXMLFile(f);
                EcoreMorphismAdapter m = new EcoreMorphismAdapter(document);
                return new Pair<>(m, ECORE_MM);
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new UnsupportedException(e.getMessage());
            }
        }
        throw new UnsupportedException("File does not exist");
    }

    @Override
    public OutputPort<?> write(String location) {
        return null;
    }
}
