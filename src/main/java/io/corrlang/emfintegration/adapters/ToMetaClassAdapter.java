package io.corrlang.emfintegration.adapters;

import org.eclipse.epsilon.eol.compile.m3.MetaClass;
import org.eclipse.epsilon.eol.compile.m3.StructuralFeature;

import java.util.List;

public class ToMetaClassAdapter  extends MetaClass {

    @Override
    public List<MetaClass> getSuperTypes() {
        return super.getSuperTypes();
    }

    @Override
    public List<StructuralFeature> getAllStructuralFeatures() {
        return super.getAllStructuralFeatures();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public List<StructuralFeature> getStructuralFeatures() {
        return super.getStructuralFeatures();
    }

    @Override
    public StructuralFeature getStructuralFeature(String name) {
        return super.getStructuralFeature(name);
    }


}
