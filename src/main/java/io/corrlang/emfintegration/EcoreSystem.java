package io.corrlang.emfintegration;

import io.corrlang.domain.Sys;
import io.corrlang.emfintegration.adapters.ToEObjectAdapter;
import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.predicates.BoolDT;
import no.hvl.past.graph.predicates.FloatDT;
import no.hvl.past.graph.predicates.IntDT;
import no.hvl.past.graph.predicates.StringDT;
import no.hvl.past.graph.trees.TypedTree;
import no.hvl.past.names.Name;
import org.eclipse.emf.ecore.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EcoreSystem extends Sys.Impl implements EMFRegistry.URIToNameResolve {

    private final EMFRegistry registry;
    private Map<Triple, EContainment> containments;
    private final EPackageFromSketchBuilder packageBuilder;

    public EcoreSystem(
            String url,
            Sketch sketch,
            Map<Name, String> displayNames,
            EMFRegistry registry,
            Map<Triple, EContainment> containments) {
        super(url, sketch, displayNames, new HashMap<>());
        this.registry = registry;
        this.containments = containments;
        this.packageBuilder = new EPackageFromSketchBuilder(this, registry);
    }

    public static EcoreSystem create(Sketch sketch, EMFRegistry registry, String url) {
        Map<Name, String> displayNames = new HashMap<>();
        Map<Triple, EContainment> containments = new HashMap<>();

        sketch.carrier().nodes().forEach(n -> displayNames.put(n, n.printRaw()));
        sketch.carrier().edges().forEach(t -> displayNames.put(t.getLabel(), t.getLabel().unprefixAll().printRaw()));

        sketch.diagrams().forEach(diagram -> {
            if (diagram.label() instanceof StringDT) {
                diagram.nodeBinding().ifPresent(n -> displayNames.put(n, "EString"));
            } else if (diagram.label() instanceof IntDT) {
                diagram.nodeBinding().ifPresent(n -> displayNames.put(n, "EInt"));
            } else if (diagram.label() instanceof FloatDT) {
                diagram.nodeBinding().ifPresent(n -> displayNames.put(n, "EDouble"));
            } else if (diagram.label() instanceof BoolDT) {
                diagram.nodeBinding().ifPresent(n -> displayNames.put(n, "EBoolean"));
            } else {
                // TODO src multiplicity 0..1 or 1.. + acyclicity (+ optionally invert are intepreted as EContainment)
            }

        });


        return new EcoreSystem(url, sketch, displayNames, registry, containments);
    }

    @Override
    public boolean isStringType(Name nodeType) {
        return EcoreDirectives.STRING_TYPE_NAMES.contains(nodeType);
    }

    @Override
    public boolean isIntType(Name nodeType) {
        return EcoreDirectives.INTEGER_TYPE_NAMES.contains(nodeType);
    }

    @Override
    public boolean isFloatType(Name nodeType) {
        return EcoreDirectives.FLOATING_POINT_TYPE_NAMES.contains(nodeType);
    }

    @Override
    public boolean isBoolType(Name nodeType) {
        return EcoreDirectives.FLOATING_POINT_TYPE_NAMES.contains(nodeType);
    }


    @Override
    public boolean isComposition(Triple edge) {
        if (this.containments.containsKey(edge)) {
            EContainment eContainment = this.containments.get(edge);
            return eContainment.getContainerEdge().equals(edge);
        }
        return false;
    }


    @Override
    public Optional<Name> resolveURI(String uri) {
        String s = uri.replaceAll("@", "");
        String[] split = s.split("/");
        return lookup(split).map(Triple::getLabel);
    }

    public EPackage getEPackage() {
        return packageBuilder.getePackage();
    }

    public Name getNameFor(EClass eClass) {
        return packageBuilder.getClassifierMapInverse().get(eClass);
    }

    public EClass getEClassFor(Name nodeType) {
        return (EClass) packageBuilder.getClassifierMap().get(nodeType);
    }

    public Triple getTripleFor(EStructuralFeature feature) {
        return packageBuilder.getFeatureMapInverse().get(feature);
    }

    public EStructuralFeature getFeatureFor(Triple typeFeature) {
        return packageBuilder.getFeatureMap().get(typeFeature);
    }


    /**
     * Adapts a tree shaped instance of this Ecore system as an eObject such that it can used by the EMFFramework.
     */
    public EObject asEObject(TypedTree instance) {
        return new ToEObjectAdapter(instance, instance.root(), this);
    }
}
