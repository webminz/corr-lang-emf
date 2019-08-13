package no.hvl.past.emf;

import no.hvl.past.graph.AbstractMorphism;
import no.hvl.past.graph.OutputPort;
import no.hvl.past.graph.operations.DiagrammaticGraph;
import no.hvl.past.graph.techspace.TechnologicalSpace;
import no.hvl.past.graph.techspace.UnsupportedException;
import no.hvl.past.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class XmiTechSpace implements TechnologicalSpace {

    @Override
    public String name() {
        return "EMF_XMI";
    }

    @Override
    public Optional<String> specifiedBy() {
        return Optional.of("EMF_ECORE");
    }

    @Override
    public List<String> realizedBy() {
        return Collections.emptyList();
    }

    @Override
    public Pair<AbstractMorphism, DiagrammaticGraph> load(String location) throws UnsupportedException {
        return null;
    }

    @Override
    public OutputPort<?> write(String location) {
        return null;
    }
}
