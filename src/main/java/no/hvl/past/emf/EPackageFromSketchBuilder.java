package no.hvl.past.emf;

import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import org.eclipse.emf.ecore.*;

import java.util.HashMap;
import java.util.Map;

public class EPackageFromSketchBuilder {


    private boolean isBuilt = false;
    private EPackage ePackage;
    private final EcoreSystem system;
    private final EMFRegistry emfRegistry;
    private final Map<Name, EClassifier> classifierMap = new HashMap<>();
    private final Map<EClassifier, Name> classifierMapInverse = new HashMap<>();
    private final Map<Triple, EStructuralFeature> featureMap = new HashMap<>();
    private final Map<EStructuralFeature, Triple> featureMapInverse = new HashMap<>();

    public EPackageFromSketchBuilder(EcoreSystem system, EMFRegistry emfRegistry) {
        this.system = system;
        this.emfRegistry = emfRegistry;
    }

    private void createEcorePackage() {
        EcorePackage.eINSTANCE.eClass();
        EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
        ePackage = ecoreFactory.createEPackage();

        ePackage.setName(system.schema().getName().printRaw());
        ePackage.setNsURI(system.url());


        system.types().forEach(name -> {
            // TODO data types
            if (system.isEnumType(name)) {
                EEnum eEnum = ecoreFactory.createEEnum();
                eEnum.setName(name.printRaw());
                for (Name literal : system.enumLiterals(name)) {
                    EEnumLiteral eEnumLiteral = ecoreFactory.createEEnumLiteral();
                    eEnumLiteral.setName(literal.printRaw());
                    eEnum.getELiterals().add(eEnumLiteral);
                }
                classifierMap.put(name, eEnum);
                ePackage.getEClassifiers().add(eEnum);
            } else {
                EClass eClass = ecoreFactory.createEClass();
                eClass.setName(name.printRaw());
                if (system.isAbstract(name)) {
                    eClass.setAbstract(true);
                }
                classifierMap.put(name, eClass);
                ePackage.getEClassifiers().add(eClass);
            }
        });

        system.types().forEach(name -> {
            EClass eClass = (EClass) classifierMap.get(name);

            system.features(name).forEach(triple -> {
                if (system.isAttributeType(triple)) {
                    EAttribute eAttribute = ecoreFactory.createEAttribute();
                    if (classifierMap.containsKey(triple.getTarget())) {
                        eAttribute.setEType(classifierMap.get(triple.getTarget()));
                    } else if (system.isStringType(triple.getTarget())) {
                        eAttribute.setEType(EcorePackage.eINSTANCE.getEString());
                    } else if (system.isIntType(triple.getTarget())) {
                        eAttribute.setEType(EcorePackage.eINSTANCE.getEInt());
                    } else if (system.isBoolType(triple.getTarget())) {
                        eAttribute.setEType(EcorePackage.eINSTANCE.getEBoolean());
                    } else if (system.isFloatType(triple.getTarget())) {
                        eAttribute.setEType(EcorePackage.eINSTANCE.getEDouble());
                    }

                    // TODO multiplicities

                    eClass.getEStructuralFeatures().add(eAttribute);
                    featureMap.put(triple, eAttribute);
                } else {
                    EReference eReference = ecoreFactory.createEReference();

                    // TODO multiplicities


                    eClass.getEStructuralFeatures().add(eReference);
                    featureMap.put(triple, eReference);
                }
            });


        });

        // TODO supertypes

        // TODO inverses
        isBuilt = true;
    }


    EPackage getePackage() {
        if (!isBuilt) {
            createEcorePackage();
        }
        return ePackage;
    }

    Map<Name, EClassifier> getClassifierMap() {
        if (!isBuilt) {
            createEcorePackage();
        }
        return classifierMap;
    }

    Map<EClassifier, Name> getClassifierMapInverse() {
        if (!isBuilt) {
            createEcorePackage();
        }
        return classifierMapInverse;
    }

    Map<Triple, EStructuralFeature> getFeatureMap() {
        if (!isBuilt) {
            createEcorePackage();
        }
        return featureMap;
    }

    Map<EStructuralFeature, Triple> getFeatureMapInverse() {
        if (!isBuilt) {
            createEcorePackage();
        }
        return featureMapInverse;
    }
}
