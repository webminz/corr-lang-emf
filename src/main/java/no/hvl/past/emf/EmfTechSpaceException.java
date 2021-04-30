package no.hvl.past.emf;

import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceException;

public class EmfTechSpaceException  extends TechSpaceException {

    public EmfTechSpaceException(String message) {
        super(message, EMFTechSpace.INSTANCE);
    }

    public EmfTechSpaceException(Throwable cause) {
        super(cause, EMFTechSpace.INSTANCE);
    }
}
