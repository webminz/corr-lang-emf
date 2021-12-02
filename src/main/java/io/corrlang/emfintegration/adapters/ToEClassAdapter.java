package io.corrlang.emfintegration.adapters;

import io.corrlang.domain.Sys;
import no.hvl.past.names.Name;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;

import java.lang.reflect.InvocationTargetException;

public class ToEClassAdapter implements EClass {

    private Name wrapped;
    private Sys metamodel;

    @Override
    public boolean isAbstract() {
        return false;
    }

    @Override
    public void setAbstract(boolean value) {

    }

    @Override
    public boolean isInterface() {
        return false;
    }

    @Override
    public void setInterface(boolean value) {

    }

    @Override
    public EList<EClass> getESuperTypes() {
        return null;
    }

    @Override
    public EList<EClass> getEAllSuperTypes() {
        return null;
    }

    @Override
    public EAttribute getEIDAttribute() {
        return null;
    }

    @Override
    public EList<EStructuralFeature> getEStructuralFeatures() {
        return null;
    }

    @Override
    public EList<EGenericType> getEGenericSuperTypes() {
        return null;
    }

    @Override
    public EList<EGenericType> getEAllGenericSuperTypes() {
        return null;
    }

    @Override
    public EList<EAttribute> getEAttributes() {
        return null;
    }

    @Override
    public EList<EAttribute> getEAllAttributes() {
        return null;
    }

    @Override
    public EList<EReference> getEReferences() {
        return null;
    }

    @Override
    public EList<EReference> getEAllReferences() {
        return null;
    }

    @Override
    public EList<EReference> getEAllContainments() {
        return null;
    }

    @Override
    public EList<EStructuralFeature> getEAllStructuralFeatures() {
        return null;
    }

    @Override
    public EList<EOperation> getEOperations() {
        return null;
    }

    @Override
    public EList<EOperation> getEAllOperations() {
        return null;
    }

    @Override
    public boolean isSuperTypeOf(EClass someClass) {
        return false;
    }

    @Override
    public int getFeatureCount() {
        return 0;
    }

    @Override
    public EStructuralFeature getEStructuralFeature(int featureID) {
        return null;
    }

    @Override
    public EStructuralFeature getEStructuralFeature(String featureName) {
        return null;
    }

    @Override
    public int getOperationCount() {
        return 0;
    }

    @Override
    public EOperation getEOperation(int operationID) {
        return null;
    }

    @Override
    public int getOperationID(EOperation operation) {
        return 0;
    }

    @Override
    public EOperation getOverride(EOperation operation) {
        return null;
    }

    @Override
    public EGenericType getFeatureType(EStructuralFeature feature) {
        return null;
    }

    @Override
    public int getFeatureID(EStructuralFeature feature) {
        return 0;
    }

    @Override
    public String getInstanceClassName() {
        return null;
    }

    @Override
    public void setInstanceClassName(String value) {

    }

    @Override
    public Class<?> getInstanceClass() {
        return null;
    }

    @Override
    public void setInstanceClass(Class<?> value) {

    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public String getInstanceTypeName() {
        return null;
    }

    @Override
    public void setInstanceTypeName(String value) {

    }

    @Override
    public EPackage getEPackage() {
        return null;
    }

    @Override
    public EList<ETypeParameter> getETypeParameters() {
        return null;
    }

    @Override
    public boolean isInstance(Object object) {
        return false;
    }

    @Override
    public int getClassifierID() {
        return 0;
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
