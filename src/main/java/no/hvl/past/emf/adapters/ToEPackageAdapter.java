package no.hvl.past.emf.adapters;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;

import java.lang.reflect.InvocationTargetException;

public class ToEPackageAdapter implements EPackage {

    private System wrapped;

    @Override
    public String getNsURI() {
        return null;
    }

    @Override
    public void setNsURI(String value) {

    }

    @Override
    public String getNsPrefix() {
        return null;
    }

    @Override
    public void setNsPrefix(String value) {

    }

    @Override
    public EFactory getEFactoryInstance() {
        return null;
    }

    @Override
    public void setEFactoryInstance(EFactory value) {

    }

    @Override
    public EList<EClassifier> getEClassifiers() {
        return null;
    }

    @Override
    public EList<EPackage> getESubpackages() {
        return null;
    }

    @Override
    public EPackage getESuperPackage() {
        return null;
    }

    @Override
    public EClassifier getEClassifier(String name) {
        return null;
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
