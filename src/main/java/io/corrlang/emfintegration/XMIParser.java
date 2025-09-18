package io.corrlang.emfintegration;

import io.corrlang.domain.Sys;
import no.hvl.past.graph.Graph;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.trees.*;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class XMIParser {

    public static final Identifier XMI_ELMENT = Name.identifier("XMI");
    private final XmlParser xmlParser;

    public XMIParser(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public TypedTree readInstance(InputStream inputStream, String fileName, Sys metamodel) throws EmfTechSpaceException {

        TreeBuildStrategy buildStrategy = makeBuildStrategy(fileName,metamodel);
        try {
            return xmlParser.parse(inputStream, buildStrategy);
        } catch (XMLStreamException | IOException e) {
            throw new EmfTechSpaceException(e);
        }
    }


    @NotNull
    private TreeBuildStrategy.TypedStrategy makeBuildStrategy(String fileURI,Sys metamodel) {
        return new TreeBuildStrategy.TypedStrategy() {

            private Name currentRootObject;
            private boolean isMultiRoot = true;
            private int counter = 0;

            @Override
            public Graph getSchemaGraph() {
                return metamodel.schema().carrier();
            }


            // TODO can give a better name than instance here: Use file URI
            @Override
            public Optional<Name> rootType(String label) {
                return metamodel.lookup(label).map(Triple::getLabel);
            }

            @Override
            public Optional<Triple> lookupType(Name parentType, String field) {
                return metamodel.lookup(metamodel.displayName(parentType), field);
            }

            @Override
            public boolean isStringType(Name typeName) {
                return metamodel.isStringType(typeName);
            }

            @Override
            public boolean isBoolType(Name typeName) {
                return metamodel.isBoolType(typeName);
            }

            @Override
            public boolean isFloatType(Name typeName) {
                return metamodel.isFloatType(typeName);
            }

            @Override
            public boolean isIntType(Name typeName) {
                return metamodel.isIntType(typeName);
            }

            @Override
            public boolean isEnumType(Name typeName) {
                return metamodel.isEnumType(typeName);
            }

            @Override
            public Optional<Triple> inverseOf(Triple edge) {
                return metamodel.getOppositeIfExists(edge);
            }

            @Override
            public TreeBuilder objectChild(TreeBuilder parent, String fieldName) {
                if (parent.getElementName().equals(XMI_ELMENT)) {
                    Optional<Triple> rootType = metamodel.lookup(fieldName);
                    if (!rootType.isPresent()) {
                        // TODO exception
                        throw new RuntimeException();
                    }
                    currentRootObject = Name.identifier("" + counter++);
                    return ((TypedNode.Builder) parent).beginChild(
                            fieldName,
                            currentRootObject,
                            Triple.edge(TypedNode.BUNDLE_TYPE, currentRootObject.prefixWith(TypedNode.BUNDLE_TYPE), rootType.get().getLabel()));
                } else if (parent.getElementName().equals(Node.ROOT_NAME)) {
                    if (fieldName.equals("XMI")) {
                        isMultiRoot = true;
                        return ((TypedNode.Builder) parent).beginChild(
                                fieldName,
                                XMI_ELMENT,
                                Triple.edge(TypedNode.BUNDLE_TYPE, XMI_ELMENT.prefixWith(TypedNode.BUNDLE_TYPE), XMI_ELMENT));
                    } else {
                        Optional<Triple> rootType = metamodel.lookup(fieldName);
                        if (!rootType.isPresent()) {
                            // TODO exception
                            throw new RuntimeException();
                        }
                        Name rootTypeName = rootType.get().getLabel();
                        currentRootObject = Name.identifier(fileURI);
                        return ((TypedNode.Builder) parent).beginChild(
                                fieldName,
                                currentRootObject,
                                Triple.edge(TypedNode.BUNDLE_TYPE, rootTypeName.prefixWith(TypedNode.BUNDLE_TYPE), rootTypeName));
                    }
                } else {
                    return super.objectChild(parent, fieldName);
                }
            }

            @Override
            public void simpleChild(TreeBuilder parent, String fieldName, boolean boolContent) {
                super.simpleChild(parent, fieldName, boolContent);
            }


            @NotNull
            @Override
            protected Name makeOID(TreeBuilder parent, String field) {
                int i = parent.peekNextIndex(field);
               return Name.identifier(field).index(i).childOf(parent.getElementName());
            }


            @Override
            public void simpleChild(TreeBuilder parent, String namespace, String name, String value) {
                if (namespace.equals("http://www.omg.org/XMI") && name.equals("id")) {
                    parent.changeElementName(Name.identifier(value));
                } else if (namespace.equals("http://www.w3.org/2001/XMLSchema-instance") && name.equals("type")) {
                    if (parent instanceof TypedNode.Builder) {
                        TypedNode.Builder b = (TypedNode.Builder) parent;
                        b.chandeTyping(Name.identifier(value.substring(value.indexOf(':') + 1))); // TODO better use a registry
                    }
                } else if (namespace.equals("http://www.omg.org/XMI") || namespace.equals("http://www.w3.org/2001/XMLSchema-instance")) {
                    return;
                } else {
                    super.simpleChild(parent, namespace, name, value);
                }
            }

            @Override
            public void simpleChild(TreeBuilder parent, String fieldName, String content) {
                if (parent instanceof TypedNode.Builder) {
                    TypedNode.Builder typedParent = (TypedNode.Builder) parent;
                    Name type = typedParent.getType();
                    Optional<Triple> triple = lookupType(type, fieldName);
                    if (!triple.isPresent()) {
                        // TODO report unknown element
                        return;
                    }
                    if (!metamodel.isSimpleTypeNode(triple.get().getTarget()) && content.startsWith("//@")) {
                        Name reference = makeRef(content);
                        ((TypedNode.Builder) parent).attribute(fieldName,reference,triple.get());
                    } else {
                        handleSimpleValue(typedParent,fieldName,content,triple.get());
                    }
                    return;
                }
                super.simpleChild(parent, fieldName, content);
            }


            private Name makeRef(String content) {
                content = content.substring(3);
                Name curent = currentRootObject;
                while (!content.isEmpty()) {
                    int idx = content.indexOf("/@");
                    String fragment;
                    if (idx < 0) {
                        fragment = content;
                        content = "";
                    } else {
                        fragment = content.substring(0, idx);
                        content = content.substring(idx + 2);
                    }
                    int i = fragment.indexOf('.');
                    if (i < 0) {
                        curent = Name.identifier(fragment).index(0).childOf(curent);
                    } else {
                        curent = Name.identifier(fragment.substring(0, i)).index(Long.parseLong(fragment.substring(i + 1))).childOf(curent);
                    }
                }
                return curent;

            }
        };
    }



}
