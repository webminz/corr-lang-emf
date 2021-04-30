package no.hvl.past.emf;

import no.hvl.past.graph.GraphError;
import no.hvl.past.graph.GraphExampleLibrary;
import no.hvl.past.graph.GraphTest;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

public class TestWritingEcores extends GraphTest {

    @Before
    public void setUp() throws GraphError {
        GraphExampleLibrary.INSTANCE.initialize(getContextCreatingBuilder());
    }

    @Test
    public void testPersonsExample() throws IOException {
        EcoreSystem ecoreSystem = EcoreSystem.create(GraphExampleLibrary.INSTANCE.PersonsAndJobsSketch, null, "http://no.hvl.past/demos/PersonsAndJobs");

        Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
        registry.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

        ResourceSet resourceSet = new ResourceSetImpl();
        Resource resource = resourceSet.createResource(URI.createURI(getResourceFolderItem("metamodels").getAbsolutePath() + "/generated/PersonsAndJobs.ecore"));

        resource.getContents().add(ecoreSystem.getEPackage());

        resource.save(Collections.emptyMap());

    }
}
