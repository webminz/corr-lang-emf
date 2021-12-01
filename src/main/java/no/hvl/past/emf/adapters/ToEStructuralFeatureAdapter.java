package no.hvl.past.emf.adapters;

import io.corrlang.domain.Sys;
import no.hvl.past.graph.elements.Triple;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;

import java.lang.reflect.InvocationTargetException;

public class ToEStructuralFeatureAdapter implements EStructuralFeature {

    private Triple wrapped;
    private Sys metamodel;

    @Override
    public boolean isTransient() {
        return false;
    }

    @Override
    public void setTransient(boolean value) {

    }

    @Override
    public boolean isVolatile() {
        return false;
    }

    @Override
    public void setVolatile(boolean value) {

    }

    @Override
    public boolean isChangeable() {
        return false;
    }

    @Override
    public void setChangeable(boolean value) {

    }

    @Override
    public String getDefaultValueLiteral() {
        return null;
    }

    @Override
    public void setDefaultValueLiteral(String value) {

    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public void setDefaultValue(Object value) {

    }

    @Override
    public boolean isUnsettable() {
        return false;
    }

    @Override
    public void setUnsettable(boolean value) {

    }

    @Override
    public boolean isDerived() {
        return false;
    }

    @Override
    public void setDerived(boolean value) {

    }

    @Override
    public EClass getEContainingClass() {
        return null;
    }

    @Override
    public int getFeatureID() {
        return 0;
    }

    @Override
    public Class<?> getContainerClass() {
        return null;
    }

    @Override
    public boolean isOrdered() {
        return false;
    }

    @Override
    public void setOrdered(boolean value) {

    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public void setUnique(boolean value) {

    }

    @Override
    public int getLowerBound() {
        return 0;
    }

    @Override
    public void setLowerBound(int value) {

    }

    @Override
    public int getUpperBound() {
        return 0;
    }

    @Override
    public void setUpperBound(int value) {

    }

    @Override
    public boolean isMany() {
        return false;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public EClassifier getEType() {
        return null;
    }

    @Override
    public void setEType(EClassifier value) {

    }

    @Override
    public EGenericType getEGenericType() {
        return null;
    }

    @Override
    public void setEGenericType(EGenericType value) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String value) {

    }

    @Override
    public EList<EAnnotation> getEAnnotations() {
        return null;
    }

    @Override
    public EAnnotation getEAnnotation(String source) {
        return null;
    }

    @Override
    public EClass eClass() {
        return null;
    }

    @Override
    public Resource eResource() {
        return null;
    }

    @Override
    public EObject eContainer() {
        return null;
    }

    @Override
    public EStructuralFeature eContainingFeature() {
        return null;
    }

    @Override
    public EReference eContainmentFeature() {
        return null;
    }

    @Override
    public EList<EObject> eContents() {
        return null;
    }

    @Override
    public TreeIterator<EObject> eAllContents() {
        return null;
    }

    @Override
    public boolean eIsProxy() {
        return false;
    }

    @Override
    public EList<EObject> eCrossReferences() {
        return null;
    }

    @Override
    public Object eGet(EStructuralFeature feature) {
        return null;
    }

    @Override
    public Object eGet(EStructuralFeature feature, boolean resolve) {
        return null;
    }

    @Override
    public void eSet(EStructuralFeature feature, Object newValue) {

    }

    @Override
    public boolean eIsSet(EStructuralFeature feature) {
        return false;
    }

    @Override
    public void eUnset(EStructuralFeature feature) {

    }

    @Override
    public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
        return null;
    }

    @Override
    public EList<Adapter> eAdapters() {
        return null;
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
