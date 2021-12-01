package no.hvl.past.emf.adapters;

import no.hvl.past.graph.trees.TypedTree;
import org.eclipse.epsilon.common.util.StringProperties;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ToIModelAdapter implements IModel, IPropertyGetter, IPropertySetter {

    private TypedTree wrapped; // Or sys data ??

    private boolean isLoad = true;
    private boolean isWrite = false;

    @Override
    public void load(StringProperties properties) throws EolModelLoadingException {
    }

    @Override
    public void load(StringProperties properties, String basePath) throws EolModelLoadingException {
    }

    @Override
    public void load(StringProperties properties, IRelativePathResolver relativePathResolver) throws EolModelLoadingException {
    }

    @Override
    public void load() throws EolModelLoadingException {
    }

    @Override
    public String getName() {
        return wrapped.getName().printRaw();
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
        // TODO implement retrieving
        return null;
    }

    @Override
    public Collection<?> allContents() {
        // TODO implement retrieving

        return null;
    }

    @Override
    public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
        // TODO implement retrieving

        return null;
    }

    @Override
    public Collection<?> getAllOfKind(String kind) throws EolModelElementTypeNotFoundException {
        // TODO implement retrieving

        return null;
    }

    @Override
    public Object getTypeOf(Object instance) {
        // TODO implement retrieving

        return null;
    }

    @Override
    public String getTypeNameOf(Object instance) {
        // TODO implement retrieving

        return null;
    }

    @Override
    public String getFullyQualifiedTypeNameOf(Object instance) {
        // TODO implement retrieving

        return null;
    }

    @Override
    public Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
        throw new ReadOnlyException();
    }

    @Override
    public Object createInstance(String type, Collection<Object> parameters) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
        throw new ReadOnlyException();
    }

    @Override
    public Object getElementById(String id) {
        // TODO implement retrieving

        return null;
    }

    @Override
    public String getElementId(Object instance) {
        // TODO implement retrieving

        return null;
    }

    @Override
    public void setElementId(Object instance, String newId) {
        throw new ReadOnlyException();
    }

    @Override
    public void deleteElement(Object instance) throws EolRuntimeException {
        throw new ReadOnlyException();
    }

    @Override
    public boolean isOfKind(Object instance, String type) throws EolModelElementTypeNotFoundException {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean isOfType(Object instance, String type) throws EolModelElementTypeNotFoundException {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean owns(Object instance) {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean knowsAboutProperty(Object instance, String property) {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean isPropertySet(Object instance, String property) throws EolRuntimeException {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean isInstantiable(String type) {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean isModelElement(Object instance) {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean hasType(String type) {
        // TODO implement retrieving

        return false;
    }

    @Override
    public boolean store(String location) {
        // TODO implement writing

        return false;
    }

    @Override
    public boolean store() {
        // TODO implement writing
        return false;
    }

    @Override
    public void dispose() {

    }

    @Override
    public IPropertyGetter getPropertyGetter() {
        return this;
    }

    @Override
    public IPropertySetter getPropertySetter() {
        return this;
    }

    @Override
    public boolean isStoredOnDisposal() {
        return isWrite;
    }

    @Override
    public void setStoredOnDisposal(boolean storedOnDisposal) {
        this.isWrite = storedOnDisposal;
    }

    @Override
    public boolean isReadOnLoad() {
        return isLoad;
    }

    @Override
    public void setReadOnLoad(boolean readOnLoad) {
        this.isLoad = readOnLoad;
    }

    @Override
    public IModelTransactionSupport getTransactionSupport() {
        return null;
    }

    @Override
    public Metamodel getMetamodel(StringProperties properties, IRelativePathResolver resolver) {
        // TODO implement metamodel adapter
        return null;
    }

    @Override
    public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
        // TODO implement getter
        return null;
    }

    @Override
    public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
        throw new ReadOnlyException();
    }
}
