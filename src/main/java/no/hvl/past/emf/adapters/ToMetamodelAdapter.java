package no.hvl.past.emf.adapters;

import org.eclipse.epsilon.eol.compile.m3.MetaClass;
import org.eclipse.epsilon.eol.compile.m3.MetaType;
import org.eclipse.epsilon.eol.compile.m3.Metamodel;

import java.util.List;

public class ToMetamodelAdapter extends Metamodel {

    @Override
    public List<MetaType> getTypes() {
        return super.getTypes();
    }


    @Override
    public MetaClass getMetaClass(String name) {
        return super.getMetaClass(name);
    }
}
