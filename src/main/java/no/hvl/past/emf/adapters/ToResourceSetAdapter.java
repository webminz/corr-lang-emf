package no.hvl.past.emf.adapters;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.util.Collections;
import java.util.Map;

public class ToResourceSetAdapter implements ResourceSet {

    @Override
    public EList<Resource> getResources() {
        return null;
    }

    @Override
    public TreeIterator<Notifier> getAllContents() {
        return null;
    }

    @Override
    public EList<AdapterFactory> getAdapterFactories() {
        return null;
    }

    @Override
    public Map<Object, Object> getLoadOptions() {
        return null;
    }

    @Override
    public EObject getEObject(URI uri, boolean loadOnDemand) {
        return null;
    }

    @Override
    public Resource getResource(URI uri, boolean loadOnDemand) {
        return null;
    }

    @Override
    public Resource createResource(URI uri) {
        return null;
    }

    @Override
    public Resource createResource(URI uri, String contentType) {
        return null;
    }

    @Override
    public Resource.Factory.Registry getResourceFactoryRegistry() {
        return null;
    }

    @Override
    public void setResourceFactoryRegistry(Resource.Factory.Registry resourceFactoryRegistry) {

    }

    @Override
    public URIConverter getURIConverter() {
        return null;
    }

    @Override
    public void setURIConverter(URIConverter converter) {

    }

    @Override
    public EPackage.Registry getPackageRegistry() {
        return null;
    }

    @Override
    public void setPackageRegistry(EPackage.Registry packageRegistry) {

    }

    @Override
    public EList<Adapter> eAdapters() {
        return new BasicEList<>();
    }

    @Override
    public boolean eDeliver() {
        return false;
    }

    @Override
    public void eSetDeliver(boolean deliver) {
    }

    @Override
    public void eNotify(Notification notification) {
    }
}
