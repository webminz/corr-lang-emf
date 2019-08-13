package no.hvl.past.emf;

import no.hvl.past.graph.AbstractMorphism;
import no.hvl.past.graph.names.Name;
import no.hvl.past.graph.operations.DiagrammaticGraph;
import no.hvl.past.graph.techspace.UnsupportedException;
import no.hvl.past.util.Pair;
import org.junit.Test;

public class EcoreParseTest {

    @Test
    public void testParse() throws UnsupportedException {
        String path = "/Users/past/Documents/dev/mdegraphlib-emf/src/main/test/resources/patients.ecore";
        Pair<AbstractMorphism, DiagrammaticGraph> result = new EcoreTechSpace().load(path);
        result.getFirst().getDomain().outgoing(Name.identifier("Patient")).forEach(System.out::println);

    }

}
