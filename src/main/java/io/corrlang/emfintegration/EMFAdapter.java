package io.corrlang.emfintegration;

import io.corrlang.domain.Data;
import io.corrlang.domain.QueryHandler;
import io.corrlang.domain.Sys;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.Universe;
import no.hvl.past.names.Name;
import no.hvl.past.UnsupportedFeatureException;

import io.corrlang.plugins.techspace.TechSpaceAdapter;
import io.corrlang.plugins.techspace.TechSpaceDirective;
import io.corrlang.plugins.techspace.TechSpaceException;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class EMFAdapter implements TechSpaceAdapter<EMFTechSpace> {

    private final EMFRegistry registry;
    private final Universe universe;
    private final PropertyHolder propertyHolder;

    public EMFAdapter(EMFRegistry registry, Universe universe, PropertyHolder propertyHolder) {
        this.registry = registry;
        this.universe = universe;
        this.propertyHolder = propertyHolder;
    }

    @Override
    public TechSpaceDirective directives() {
        return EcoreDirectives.INSTANCE;
    }

    @Override
    public Sys parseSchema(Name name, String locationURI) throws TechSpaceException, UnsupportedFeatureException {
        try {
            EcoreParser parser = new EcoreParser(universe, registry);
            File file = new File(new URI(locationURI));
            FileInputStream fileInputStream = new FileInputStream(file);
            return parser.parse(fileInputStream, locationURI, name);
        } catch (IOException | URISyntaxException e) {
            throw new TechSpaceException(e, EMFTechSpace.INSTANCE);
        }
    }

    @Override
    public void writeSchema(Sys sys, OutputStream outputStream) throws TechSpaceException, UnsupportedFeatureException {
        // TODO implement via existing framework
        throw new UnsupportedFeatureException();
    }

    @Override
    public QueryHandler queryHandler(Sys system) throws TechSpaceException, UnsupportedFeatureException {
        // TODO implement via generic CRUD Handler
        throw new UnsupportedFeatureException();
    }

    @Override
    public Data readInstance(Sys system, InputStream inputStream) throws TechSpaceException, UnsupportedFeatureException {
        XMIParser parser = new XMIParser(new XmlParser());
        return Data.fromTree(system, parser.readInstance(inputStream, "instance", system));
    }

    @Override
    public void writeInstance(Sys system, GraphMorphism instance, OutputStream outputStream) throws TechSpaceException, UnsupportedFeatureException {
        // TODO implement via existing framework
        throw new UnsupportedFeatureException();
    }
}
