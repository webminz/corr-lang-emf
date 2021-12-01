package no.hvl.past.emf;

import io.corrlang.domain.Sys;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.trees.TypedTree;
import no.hvl.past.graph.trees.XmlParser;
import no.hvl.past.names.Name;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestReadingXMI extends EcoreTestBase {



    @Test
    public void testReadingMarks() throws FileNotFoundException, EmfTechSpaceException, GraphError {
        UniverseImpl universe = new UniverseImpl(UniverseImpl.EMPTY);
        EcoreParser ecorParser = new EcoreParser(universe,registry);
        XMIParser xmiParser = new XMIParser(new XmlParser());


        File tablesEcore = getResourceFolderItem("metamodels/tables.ecore");
        File tablesXMI = getResourceFolderItem("models/marks.tables");

        Sys tables = ecorParser.parse(new FileInputStream(tablesEcore), tablesEcore.getAbsolutePath(), Name.identifier("tables"));
        TypedTree typedTree = xmiParser.readInstance(new FileInputStream(tablesXMI), "marks.tables", tables);

        Optional<Triple> collect = typedTree.preimage(Triple.edge(Name.identifier("Cell"), Name.identifier("column").prefixWith(Name.identifier("Cell")), Name.identifier("Column"))).findFirst();
        assertTrue(collect.isPresent());
        List<Triple> result = typedTree.allOutgoingInstances(Triple.edge(Name.identifier("Column"), Name.identifier("title").prefixWith(Name.identifier("Column")), Name.identifier("EString")), collect.get().getTarget()).collect(Collectors.toList());
        assertEquals(1, result.size());
        assertEquals(Name.value("Student No"), result.get(0).getTarget());


        ExecutionContext executionContext = new ExecutionContext() {
            @Override
            public Name generateNewNodeName() {
                return Name.anonymousIdentifier();
            }

            @Override
            public Name generateNewEdgeLabel() {
                return Name.anonymousIdentifier();
            }

            @Override
            public Random randomGenerator() {
                return new Random(42);
            }

            @Override
            public long systemTime() {
                return 1657662900000L; // 23:55 on 31.12.2020
            }

            @Override
            public Properties metaInformation() {
                return new Properties();
            }

            @Override
            public Universe universe() {
                return universe;
            }
        };

        DiagrammaticWorkflow diagrammaticWorkflow = new DiagrammaticWorkflow(
                Name.anonymousIdentifier(),
                typedTree,
                tables.schema(),
                executionContext);
        diagrammaticWorkflow.execute();
        assertTrue(diagrammaticWorkflow.executedCorrectly(tables.schema()));
    }


    // TODO read relational
}
