package io.corrlang.emfintegration;

import no.hvl.past.TestBase;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.jetty.util.resource.ResourceFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

public class EcoreInfrastructureTest extends TestBase {


    @Test
    public void testEInfrastructure() throws IOException {
        EcorePackage.eINSTANCE.eClass();
        EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
        EPackage container = ecoreFactory.createEPackage();
        container.setName("Graphs");
        container.setNsPrefix("g");
        container.setNsURI("http://www.hvl.no/past/graphs");

        EClass g = ecoreFactory.createEClass();
        g.setName("Graph");


        EClass n = ecoreFactory.createEClass();
        n.setName("Node");

        EClass edge = ecoreFactory.createEClass();
        edge.setName("Arc");

        EReference es = ecoreFactory.createEReference();
        es.setName("source");
        es.setEType(n);
        es.setLowerBound(1);
        es.setUpperBound(1);

        EReference et1 = ecoreFactory.createEReference();
        et1.setName("target");
        et1.setEType(n);
        et1.setLowerBound(1);
        et1.setUpperBound(1);

        EReference nodes = ecoreFactory.createEReference();
        nodes.setName("nodes");
        nodes.setContainment(true);
        nodes.setEType(n);

        EReference edges = ecoreFactory.createEReference();
        edges.setName("edges");
        edges.setContainment(true);
        edges.setEType(edge);

        edge.getEStructuralFeatures().add(es);
        edge.getEStructuralFeatures().add(et1);
        g.getEStructuralFeatures().add(nodes);
        g.getEStructuralFeatures().add(edges);

        container.getEClassifiers().add(g);
        container.getEClassifiers().add(n);
        container.getEClassifiers().add(edge);

        Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
        registry.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

        ResourceSet resourceSet = new ResourceSetImpl();
        Resource resource = resourceSet.createResource(URI.createURI(getResourceFolderItem("metamodels").getAbsolutePath() + "/generated.ecore"));

        resource.getContents().add(container);

        resource.save(Collections.emptyMap());

    }
}
