package no.hvl.past.emf.adapters;

import no.hvl.past.attributes.BoolValue;
import no.hvl.past.attributes.FloatValue;
import no.hvl.past.attributes.IntegerValue;
import no.hvl.past.attributes.StringValue;
import no.hvl.past.emf.EcoreSystem;
import no.hvl.past.graph.trees.TypedBranch;
import no.hvl.past.graph.trees.TypedNode;
import no.hvl.past.graph.trees.TypedTree;
import no.hvl.past.names.Name;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceEList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ToEObjectAdapter implements EObject {

    private TypedNode wrapped;
    private EcoreSystem ecoreSystem;
    private Resource resource;
    private TypedTree tree;

    public ToEObjectAdapter(TypedTree container, TypedNode wrapped, EcoreSystem ecoreSystem) {
        this.wrapped = wrapped;
        this.ecoreSystem = ecoreSystem;
        this.tree = container;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ToEObjectAdapter adaptFor(TypedNode node) {
        ToEObjectAdapter result = new ToEObjectAdapter(tree, wrapped, ecoreSystem);
        if (resource != null) {
            result.setResource(resource);
        }
        return result;
    }

    @Override
    public EClass eClass() {
        return ecoreSystem.getEClassFor(wrapped.nodeType());
    }

    @Override
    public Resource eResource() {
        return resource;
    }

    @Override
    public EObject eContainer() {
        return wrapped.parent()
                .filter(n -> n instanceof TypedNode)
                .map(n -> (TypedNode)n)
                .map(this::adaptFor)
                .orElse(null);
    }

    @Override
    public EStructuralFeature eContainingFeature() {
        return wrapped.parent()
                .filter(b -> b instanceof TypedBranch)
                .map(b -> (TypedBranch) b)
                .map(b -> ecoreSystem.getFeatureFor(b.typeFeature()))
                .orElse(null);
    }

    @Override
    public EReference eContainmentFeature() {
        // don't really know what is the difference to eContainingFeature except for the more specific return type
        if (wrapped.parent().isPresent()) {
            return (EReference) eContainingFeature();
        }
        return null;
    }

    @Override
    public EList<EObject> eContents() {
        BasicEList<EObject> result = new BasicEList<>();
        wrapped.typedChildren().filter(b -> !ecoreSystem.isSimpleTypeNode(b.child().nodeType()) && ecoreSystem.isComposition(b.typeFeature()))
                .forEach(b -> result.add(adaptFor(b.child())));
        return result;
    }

    @Override
    public TreeIterator<EObject> eAllContents() {
        Iterator<TypedNode> typedNodeIterator = no.hvl.past.graph.trees.TreeIterator.depthFirstTypedComplex(wrapped);
        return new TreeIterator<EObject>() {
            @Override
            public boolean hasNext() {
                return typedNodeIterator.hasNext();
            }

            @Override
            public EObject next() {
                return adaptFor(typedNodeIterator.next());
            }

            @Override
            public void prune() {
                // don't know what do here
            }
        };
    }

    @Override
    public boolean eIsProxy() {
        return false;
    }

    @Override
    public EList<EObject> eCrossReferences() {
        return ECrossReferenceEList.createECrossReferenceEList(this);
    }

    @Override
    public Object eGet(EStructuralFeature feature) {
        List<TypedBranch> collect = wrapped.typedChildrenByLabel(feature.getName()).collect(Collectors.toList());
        if (collect.isEmpty()) {
            if (ecoreSystem.isCollectionValued(ecoreSystem.getTripleFor(feature))) {
                return new BasicEList<>();
            } else {
                return null;
            }
        } else if (collect.size() == 1) {
            return makeSingleResult(collect.get(0));
        } else {
            return makeCollectionsResult(collect);
        }
    }

    private Object makeCollectionsResult(List<TypedBranch> collect) {
        BasicEList<Object> objects = new BasicEList<>();
        for (TypedBranch b : collect) {
            Object o = makeSingleResult(b);
            if (o != null) {
                objects.add(o);
            }
        }
        return objects;
    }

    private Object makeSingleResult(TypedBranch typedBranch) {
        Name name = typedBranch.child().elementName();
        if (name.isValue()) {
            if (name instanceof StringValue) {
                return ((StringValue) name).getStringValue();
            }
            if (name instanceof IntegerValue) {
                return ((IntegerValue) name).getIntegerValue();
            }
            if (name instanceof FloatValue) {
                return ((FloatValue) name).getFloatValue();
            }
            if (name instanceof BoolValue) {
                return ((BoolValue) name).isTrue();
            }
            return name;
        } else {
            return tree.root().byName(name).orElse(null);
        }
    }

    @Override
    public Object eGet(EStructuralFeature feature, boolean resolve) {
        return eGet(feature);
    }

    @Override
    public void eSet(EStructuralFeature feature, Object newValue) {
        throw new ReadOnlyException();

    }

    @Override
    public boolean eIsSet(EStructuralFeature feature) {
        return wrapped.childrenByKey(feature.getName()).anyMatch(x -> true);
    }

    @Override
    public void eUnset(EStructuralFeature feature) {
        throw new ReadOnlyException();
    }

    @Override
    public Object eInvoke(EOperation operation, EList<?> arguments) {
        throw new ReadOnlyException();
    }

    @Override
    public EList<Adapter> eAdapters() {
        return new BasicEList<>();
    }

    @Override
    public boolean eDeliver() {
        return false;
    }

    @Override
    public void eSetDeliver(boolean deliver) {
    }

    @Override
    public void eNotify(Notification notification) {
    }
}
