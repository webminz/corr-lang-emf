package no.hvl.past.emf;

import io.corrlang.plugins.techspace.TechSpace;

public class EMFTechSpace implements TechSpace {

    public static final EMFTechSpace INSTANCE = new EMFTechSpace();

    private EMFTechSpace() {
    }

    @Override
    public String ID() {
        return "ECORE";
    }
}
