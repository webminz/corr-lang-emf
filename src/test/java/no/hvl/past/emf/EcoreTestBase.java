package no.hvl.past.emf;

import no.hvl.past.TestBase;
import no.hvl.past.graph.Universe;
import no.hvl.past.graph.UniverseImpl;
import no.hvl.past.util.FileSystemUtils;

public class EcoreTestBase extends TestBase {

    protected EMFRegistry registry = new EMFRegistry(getResourceFolderItem(".").getAbsolutePath(), FileSystemUtils.getInstance());

    protected Universe universe = new UniverseImpl(UniverseImpl.EMPTY);
}
