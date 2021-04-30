package no.hvl.past.emf;

import com.google.common.collect.Sets;
import no.hvl.past.names.Name;
import no.hvl.past.techspace.TechSpaceDirective;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EcoreDirectives implements TechSpaceDirective {

    public static final Set<String> STRING_TYPES = Sets.newHashSet("EString", "EChar", "ECharacterObject");

    public static final Set<Name> STRING_TYPE_NAMES = STRING_TYPES.stream().map(Name::identifier).collect(Collectors.toSet());


    public static final Set<String> FLOATING_POINT_TYPES = Sets.newHashSet(
            "EFloat",
            "EDouble",
            "EDoubleObject",
            "EFloatObject"
    );

    public static final Set<Name> FLOATING_POINT_TYPE_NAMES = FLOATING_POINT_TYPES.stream().map(Name::identifier).collect(Collectors.toSet());

    public static final Set<String> INTEGER_TYPES = Sets.newHashSet(
            "EByte",
            "EShort",
            "EInt",
            "ELong",
            "EByteObject",
            "EShortObject",
            "ELongObject",
            "EIntegerObject"
    );

    public static final Set<Name> INTEGER_TYPE_NAMES = INTEGER_TYPES.stream().map(Name::identifier).collect(Collectors.toSet());


    public static final Set<String> BOOL_TYPES = Sets.newHashSet("EBoolean", "EBooleanObject");


    public static final Set<Name> BOOL_TYPE_NAMES = BOOL_TYPES.stream().map(Name::identifier).collect(Collectors.toSet());


    public static final Set<String> OTHER_BASE_TYPES = Sets.newHashSet(
            "EByteArray",
            "EJavaObject",
            "EJavaClass"
    );

    public static final Set<Name> OTHER_BASE_TYPE_NAME = OTHER_BASE_TYPES.stream().map(Name::identifier).collect(Collectors.toSet());


    public static final Set<String> ALL_BASE_TYPES = Sets.union(Sets.union(STRING_TYPES, BOOL_TYPES), Sets.union(Sets.union(INTEGER_TYPES, FLOATING_POINT_TYPES), OTHER_BASE_TYPES));

    public static final Set<Name> ALL_BASE_TYPE_NAMEs = ALL_BASE_TYPES.stream().map(Name::identifier).collect(Collectors.toSet());



    public static EcoreDirectives INSTANCE = new EcoreDirectives();



    private EcoreDirectives() {
    }

    @Override
    public Optional<Name> stringDataType() {
        return Optional.of(Name.identifier("EString"));
    }

    @Override
    public Optional<Name> boolDataType() {
        return Optional.of(Name.identifier("EBoolean"));
    }

    @Override
    public Optional<Name> integerDataType() {
        return Optional.of(Name.identifier("EInt"));
    }

    @Override
    public Optional<Name> floatingPointDataType() {
        return Optional.of(Name.identifier("EDouble"));
    }

    @Override
    public Stream<Name> implicitTypeIdentities() {
        return ALL_BASE_TYPE_NAMEs.stream().filter(n -> {
            return !n.equals(Name.identifier("EString")) &&
                    !n.equals(Name.identifier("EBoolean")) &&
                    !n.equals(Name.identifier("EInt")) &&
                    !n.equals(Name.identifier("EDouble"));
        });
    }
}
