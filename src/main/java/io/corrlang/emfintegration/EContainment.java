package io.corrlang.emfintegration;

import no.hvl.past.graph.Diagram;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;

import java.util.Set;

/**
 * Special treatment to work with containment edges in Ecore.
 */
public class EContainment {

    private final boolean isBidir;
    private final Triple containerEdge;
    private final Triple containeeEdge;
    private final Set<Name> diagramNames;


    public EContainment(boolean isBidir, Triple containerEdge, Triple containeeEdge, Set<Name> diagramNames) {
        this.isBidir = isBidir;
        this.containerEdge = containerEdge;
        this.containeeEdge = containeeEdge;
        this.diagramNames = diagramNames;
    }

    public boolean isBidir() {
        return isBidir;
    }

    public Triple getContainerEdge() {
        return containerEdge;
    }

    public Triple getContaineeEdge() {
        return containeeEdge;
    }

    public Set<Name> getDiagramNames() {
        return diagramNames;
    }
}
