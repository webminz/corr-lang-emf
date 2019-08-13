package no.hvl.past.emf;

import no.hvl.past.graph.AbstractMorphism;
import no.hvl.past.graph.Graph;
import no.hvl.past.graph.OutputPort;
import no.hvl.past.graph.operations.DiagrammaticGraph;
import no.hvl.past.graph.operations.Predefined;
import no.hvl.past.graph.techspace.TechnologicalSpace;
import no.hvl.past.graph.techspace.UnsupportedException;
import no.hvl.past.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EcoreTechSpace implements TechnologicalSpace {

    private static final String ALL_CLASSIFIERS_QUERY = "//eClassifiers";
    private static final String ALL_FEATURE_QUERY = "//eClassifiers//eStructuralFeatures";


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
        return null;
    }

    @Override
    public OutputPort<?> write(String location) {
        return null;
    }
}
