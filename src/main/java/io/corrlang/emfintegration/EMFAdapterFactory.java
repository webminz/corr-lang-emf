package io.corrlang.emfintegration;

import no.hvl.past.di.PropertyHolder;
import no.hvl.past.graph.Universe;
import no.hvl.past.MetaRegistry;
import io.corrlang.plugins.techspace.TechSpaceAdapter;
import io.corrlang.plugins.techspace.TechSpaceAdapterFactory;
import no.hvl.past.util.FileSystemUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class EMFAdapterFactory implements TechSpaceAdapterFactory<EMFTechSpace> {

    @Autowired
    FileSystemUtils fileSystemUtils;

    @Autowired
    MetaRegistry pluginRegistry;

    @Autowired
    PropertyHolder propertyHolder;

    private EMFRegistry registry;

    @Autowired
    Universe universe;

    @Override
    public void doSetUp() {
        // TODO load from config
        this.registry = new EMFRegistry(fileSystemUtils.getBaseDir(), fileSystemUtils);
    }

    @Override
    public TechSpaceAdapter<EMFTechSpace> createAdapter() {
        return new EMFAdapter(registry,universe,propertyHolder);
    }

    @Override
    public void prepareShutdown() {

    }


    @PostConstruct
    public void setUp() {
        pluginRegistry.register(EMFTechSpace.INSTANCE.ID(),EMFTechSpace.INSTANCE);
        pluginRegistry.register(EMFTechSpace.INSTANCE.ID(), this);
    }


}
