package no.hvl.past.emf;

import com.google.common.collect.Sets;
import no.hvl.past.names.Name;
import io.corrlang.plugins.techspace.TechSpaceDirective;
import io.corrlang.plugins.techspace.TechnologySpecificRules;

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
            "EDate",
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
    public Stream<StringTypeDescription> stringDataType() {
        return Stream.of(
                () -> Name.identifier("EString"),
                new StringTypeDescription() {
                    @Override
                    public Name typeName() {
                        return Name.identifier("EChar");
                    }

                    @Override
                    public Optional<Integer> limit() {
                        return Optional.of(1);
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new StringTypeDescription() {
                    @Override
                    public Name typeName() {
                        return Name.identifier("ECharacterObject");
                    }

                    @Override
                    public Optional<Integer> limit() {
                        return Optional.of(1);
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                }
                );
    }

    @Override
    public Stream<BaseTypeDescription> boolDataType() {
        return Stream.of(
                () -> Name.identifier("EBoolean"),
                new BaseTypeDescription() {
                    @Override
                    public Name typeName() {
                        return Name.identifier("EBooleanObject");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                });
    }

    @Override
    public Stream<IntTypeDescription> integerDataType() {
        return Stream.of(
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 32;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EInt");
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 32;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EIntegerObject");
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 64;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("ELong");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 64;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("ELongObject");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 16;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EShort");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 16;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EShortObject");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 8;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EByte");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IntTypeDescription() {
                    @Override
                    public int limit() {
                        return 8;
                    }

                    @Override
                    public IntTypeSizeRestriction restriction() {
                        return IntTypeSizeRestriction.BIT_LENGTH_RESTRICTED;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EByteObject");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                }); // TODO EMF lookup if EMF has BitInteger
    }

    @Override
    public Stream<FloatTypeDescription> floatingPointDataType() {
        return Stream.of(
                new IEEEFloatTypeDescription() {
                    @Override
                    public int bitSize() {
                        return 64;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EDouble");
                    }
                }, new IEEEFloatTypeDescription() {
                    @Override
                    public int bitSize() {
                        return 64;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EDoubleObject");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IEEEFloatTypeDescription() {
                    @Override
                    public int bitSize() {
                        return 32;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EFloat");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                },
                new IEEEFloatTypeDescription() {
                    @Override
                    public int bitSize() {
                        return 32;
                    }

                    @Override
                    public Name typeName() {
                        return Name.identifier("EFloatObject");
                    }

                    @Override
                    public boolean isDefault() {
                        return false;
                    }
                }); // TODO EMF lookup if EMF has BitInteger
    }

    @Override
    public Stream<CustomBaseTypeDescription> otherDataTypes() {
        return Stream.of(() -> Name.identifier("EDate"),
                () -> Name.identifier("EByteArray"),
                () -> Name.identifier("EJavaObject"),
                () -> Name.identifier("EJavaClass"));
    }

    @Override
    public void additionalTechnologySpecificRules(TechnologySpecificRules configure) {
    }




}
