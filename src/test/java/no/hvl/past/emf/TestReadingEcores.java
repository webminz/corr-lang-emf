package no.hvl.past.emf;

import no.hvl.past.TestBase;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.elements.Tuple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.plotting.PlantUMLPlotter;
import no.hvl.past.graph.predicates.EnumValue;
import no.hvl.past.graph.predicates.TargetMultiplicity;
import no.hvl.past.names.Name;
import no.hvl.past.systems.Sys;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestReadingEcores extends EcoreTestBase {

      // TODO make a distinct tests that prints all ecore files
//    private PlantUMLPlotter plantUMLPlotter = new PlantUMLPlotter("PNG", true);


    @Test
    public void testReadFamilies() throws EmfTechSpaceException, IOException {
        File familiesEcore = getResourceFolderItem("metamodels/Families.ecore");
        EcoreParser parser = new EcoreParser(universe, registry);
        Sys families = parser.parse(new FileInputStream(familiesEcore), familiesEcore.getAbsolutePath(),Name.identifier("families"));
        Sketch result = families.schema();
        Set<Name> nodes = result.carrier().nodes().collect(Collectors.toSet());
        assertEquals(3, nodes.size());
        Set<Triple> familyFeatures = result.carrier().outgoing(Name.identifier("Family")).filter(Triple::isEddge).collect(Collectors.toSet());
        Optional<Triple> triple = result.carrier().get(Name.identifier("familyFather").prefixWith(Name.identifier("Member")));
        assertTrue(triple.isPresent());
        assertEquals(Name.identifier("Member"), triple.get().getSource());
        assertEquals(Name.identifier("Family"), triple.get().getTarget());

        Set<Diagram> diagrams = result.diagramsOn(triple.get()).collect(Collectors.toSet());
        assertEquals(2, diagrams.size());
        TargetMultiplicity mult = diagrams.stream().filter(d -> d.label() instanceof TargetMultiplicity).map(d -> (TargetMultiplicity)d.label()).findFirst().get();
        assertEquals(0 , mult.getLowerBound());
        assertEquals(1 , mult.getUpperBound());
        Diagram diagram = diagrams.stream().filter(d -> d.label() instanceof Invert).findFirst().get();
        assertEquals(Name.identifier("father").prefixWith(Name.identifier("Family")), diagram.binding().map(Universe.CYCLE_FWD.getLabel()).get());
        assertEquals(Name.identifier("familyFather").prefixWith(Name.identifier("Member")), diagram.binding().map(Universe.CYCLE_BWD.getLabel()).get());

//        File familiesPNG = new File(getResourceFolderItem("drawings"), "families.png");
//        if (familiesPNG.exists()) {
//            familiesPNG.delete();
//        }
//        FileOutputStream fos = new FileOutputStream(familiesPNG);
//        plantUMLPlotter.plot(families,fos);
//        fos.close();
    }

    @Test
    public void testReadRelational() throws IOException, EmfTechSpaceException {
        File relationalEcor = getResourceFolderItem("metamodels/relational.ecore");
        EcoreParser parser = new EcoreParser(universe, registry);
        Sys relational = parser.parse(new FileInputStream(relationalEcor), relationalEcor.getAbsolutePath(),Name.identifier("relational"));
        Sketch result = relational.schema();
        Set<Name> nodes = result.carrier().nodes().collect(Collectors.toSet());
        assertEquals(13, nodes.size());

        Optional<Triple> triple = result.carrier().get(Name.identifier("target").prefixWith(Name.identifier("ForeignKeyConstraint")));
        assertTrue(triple.isPresent());
        assertEquals(Name.identifier("AbstractUniqueConstraint"), triple.get().getTarget());
        assertEquals(Name.identifier("relational"), result.getName());

        Optional<EnumValue> sqlDataType = result.diagramsOn(Triple.node(Name.identifier("SQLDataType"))).filter(d -> d.label() instanceof EnumValue).map(d -> (EnumValue) d.label()).findFirst();
        assertTrue(sqlDataType.isPresent());
        assertEquals(2, sqlDataType.get().literals().size());
        assertTrue(sqlDataType.get().literals().contains(Name.identifier("VARCHAR")));
        assertTrue(sqlDataType.get().literals().contains(Name.identifier("NUMBER")));


//        File relationalPNG = new File(getResourceFolderItem("drawings"), "relational.png");
//        if (relationalPNG.exists()) {
//            relationalPNG.delete();
//        }
//        FileOutputStream fos = new FileOutputStream(relationalPNG);
//        plantUMLPlotter.plot(relational,fos);
//        fos.close();

        // TODO test String type
    }


    @Test
    public void testReadPersons() throws IOException, EmfTechSpaceException {
        File relationalEcor = getResourceFolderItem("metamodels/persons.ecore");
        EcoreParser parser = new EcoreParser(universe, registry);
        Sys persons = parser.parse(new FileInputStream(relationalEcor), relationalEcor.getAbsolutePath(), Name.identifier("Persons"));

        // TODO test abstract predicate

        Set<Tuple> collect = persons.directSuperTypes().collect(Collectors.toSet());
        assertEquals(2, collect.size());

        // TODO test data type predicate

//        File personsPNG = new File(getResourceFolderItem("drawings"), "persons.png");
//        if (personsPNG.exists()) {
//            personsPNG.delete();
//        }
//        FileOutputStream fos = new FileOutputStream(personsPNG);
//        plantUMLPlotter.plot(persons,fos);
//        fos.close();
    }


    // Graph

    // Blog DSL

    // flowchart

    // tables

    // Tree

}
