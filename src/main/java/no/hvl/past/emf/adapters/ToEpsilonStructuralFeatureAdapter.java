package no.hvl.past.emf.adapters;

import org.eclipse.epsilon.eol.compile.m3.StructuralFeature;
import org.eclipse.epsilon.eol.types.EolType;

public class ToEpsilonStructuralFeatureAdapter extends StructuralFeature {

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public EolType getType() {
        return super.getType();
    }

    @Override
    public boolean isMany() {
        return super.isMany();
    }

    @Override
    public boolean isOrdered() {
        return super.isOrdered();
    }

    @Override
    public boolean isUnique() {
        return super.isUnique();
    }
}
