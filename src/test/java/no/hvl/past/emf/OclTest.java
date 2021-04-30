package no.hvl.past.emf;

import no.hvl.past.TestBase;
import org.checkerframework.checker.units.qual.C;
import org.eclipse.emf.ecore.*;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.compile.m3.Metamodel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OclTest extends TestBase {



    @Test
    public void testEasyOCL() throws IOException, EolRuntimeException {
        StringProperties properties = StringProperties.Builder().withProperty(EmfModel.PROPERTY_NAME, "TreeModel")
                .build();

        EvlRunConfiguration build = EvlRunConfiguration.Builder()
                .withScript(getResourceFolderItem("ocl/Demo.evl").getPath())
                .withModel(new IModel() {
                    @Override
                    public void load(StringProperties properties) throws EolModelLoadingException {
                        System.out.println("load");

                    }

                    @Override
                    public void load(StringProperties properties, String basePath) throws EolModelLoadingException {
                        System.out.println("load");

                    }

                    @Override
                    public void load(StringProperties properties, IRelativePathResolver relativePathResolver) throws EolModelLoadingException {
                        System.out.println("load");

                    }

                    @Override
                    public void load() throws EolModelLoadingException {
                        System.out.println("load");

                    }

                    @Override
                    public String getName() {
                        return "TreeModel";
                    }

                    @Override
                    public void setName(String name) {
                        System.out.println("setName");

                    }

                    @Override
                    public List<String> getAliases() {
                        return null;
                    }

                    @Override
                    public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
                        return null;
                    }

                    @Override
                    public Collection<?> allContents() {
                        System.out.println("contents");
                        return null;
                    }

                    @Override
                    public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
                        System.out.println("alltype");

                        return null;
                    }

                    @Override
                    public Collection<?> getAllOfKind(String kind) throws EolModelElementTypeNotFoundException {
                        System.out.println("allOfKind");

                        return Collections.singleton( new Object()); // TODO
                    }

                    @Override
                    public Object getTypeOf(Object instance) {
                        System.out.println("typeOf");

                        return null;
                    }

                    @Override
                    public String getTypeNameOf(Object instance) {
                        System.out.println("typeOf");

                        return null;
                    }

                    @Override
                    public String getFullyQualifiedTypeNameOf(Object instance) {
                        return null;
                    }

                    @Override
                    public Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
                        return null;
                    }

                    @Override
                    public Object createInstance(String type, Collection<Object> parameters) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
                        return null;
                    }

                    @Override
                    public Object getElementById(String id) {
                        System.out.println("elementById");

                        return null;
                    }

                    @Override
                    public String getElementId(Object instance) {
                        System.out.println("element id");

                        return null;
                    }

                    @Override
                    public void setElementId(Object instance, String newId) {
                        System.out.println("set ID");

                    }

                    @Override
                    public void deleteElement(Object instance) throws EolRuntimeException {
                        System.out.println("del");

                    }

                    @Override
                    public boolean isOfKind(Object instance, String type) throws EolModelElementTypeNotFoundException {
                        return false;
                    }

                    @Override
                    public boolean isOfType(Object instance, String type) throws EolModelElementTypeNotFoundException {
                        return false;
                    }

                    @Override
                    public boolean owns(Object instance) {
                        return false;
                    }

                    @Override
                    public boolean knowsAboutProperty(Object instance, String property) {
                        System.out.println("propert");

                        return true; // TODO
                    }

                    @Override
                    public boolean isPropertySet(Object instance, String property) throws EolRuntimeException {
                        return false;
                    }

                    @Override
                    public boolean isInstantiable(String type) {
                        return false;
                    }

                    @Override
                    public boolean isModelElement(Object instance) {
                        return false;
                    }

                    @Override
                    public boolean hasType(String type) {
                        return true; // TODO
                    }

                    @Override
                    public boolean store(String location) {
                        return false;
                    }

                    @Override
                    public boolean store() {
                        return false;
                    }

                    @Override
                    public void dispose() {
                        System.out.println("dispose");

                    }

                    @Override
                    public IPropertyGetter getPropertyGetter() {
                        return new IPropertyGetter() {
                            @Override
                            public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
                                return "b";
                            }
                        };
                    }

                    @Override
                    public IPropertySetter getPropertySetter() {
                        return null;
                    }

                    @Override
                    public boolean isStoredOnDisposal() {
                        return false;
                    }

                    @Override
                    public void setStoredOnDisposal(boolean storedOnDisposal) {
                        System.out.println("store");

                    }

                    @Override
                    public boolean isReadOnLoad() {
                        return false;
                    }

                    @Override
                    public void setReadOnLoad(boolean readOnLoad) {
                        System.out.println("contents");

                    }

                    @Override
                    public IModelTransactionSupport getTransactionSupport() {
                        return null;
                    }

                    @Override
                    public Metamodel getMetamodel(StringProperties properties, IRelativePathResolver resolver) {
                        System.out.println("metamodel");

                        return null;
                    }
                }, properties)
                .withResults()
                .build();
        build.run();
        Collection<UnsatisfiedConstraint> unsat = build.getResult();
        assertEquals(1, unsat.size());

    }

}
