package no.hvl.past.emf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(
        Suite.class
)
@Suite.SuiteClasses({
        EcoreInfrastructureTest.class,
        TestReadingEcores.class,
        TestReadingXMI.class,
        TestWritingEcores.class
})
public class EcoreTestSuite {
}
