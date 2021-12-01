package no.hvl.past.emf;

import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceException;

public class EmfTechSpaceException  extends TechSpaceException {

    public EmfTechSpaceException(String message) {
        super(message, EMFTechSpace.INSTANCE);
    }

    public EmfTechSpaceException(Throwable cause) {
        super(cause, EMFTechSpace.INSTANCE);
    }
}
