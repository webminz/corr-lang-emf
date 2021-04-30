package no.hvl.past.emf;

import no.hvl.past.techspace.TechSpace;

public class EMFTechSpace implements TechSpace {

    public static final EMFTechSpace INSTANCE = new EMFTechSpace();

    private EMFTechSpace() {
    }

    @Override
    public String ID() {
        return "ECORE";
    }
}
