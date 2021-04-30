package no.hvl.past.emf;

import no.hvl.past.names.Name;
import no.hvl.past.systems.Sys;
import no.hvl.past.util.FileSystemUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EMFRegistry implements URIConverter {

    public interface URIToNameResolve {

        Optional<Name> resolveURI(String uri);
    }

    private FileSystemUtils fileSystemUtils;
    private String platformResourcePath;
    private final Map<org.eclipse.emf.common.util.URI, String> aliases;
    private final Map<String, String> absoluteFilesToLocalURIs;
    private final Map<String, EcoreSystem> registeredSystems;

    public EMFRegistry(String platformResourcePath, FileSystemUtils fs) {
        this.platformResourcePath = platformResourcePath;
        this.fileSystemUtils = fs;
        this.aliases = new HashMap<>();
        this.absoluteFilesToLocalURIs = new HashMap<>();
        this.registeredSystems = new HashMap<>();
    }

    public String getPlatformResourcePath() {
        return platformResourcePath;
    }

    public void setPlatformResourcePath(String platformResourcePath) {
        this.platformResourcePath = platformResourcePath;
    }

    public Optional<Name> resolveURI(URIToNameResolve contextResolver, String uri) {
        // Throw away everything that comes before the uri and is separated by space
        String remaining = uri;
        while (remaining.contains(" ")) {
            int i = remaining.indexOf(' ');
            remaining = remaining.substring(i + 1);
        }

        if (remaining.contains("#")) {
            int i = remaining.indexOf("#");
            String container = remaining.substring(0, i);
            String localPart = remaining.substring(i + 1);
            if (container.isEmpty()) {
                return contextResolver.resolveURI(localPart);
            }
            if (container.equals("http://www.eclipse.org/emf/2002/Ecore")) {
                if (localPart.startsWith("//")) {
                    localPart = localPart.substring(2);
                }

                if (EcoreDirectives.ALL_BASE_TYPES.contains(localPart)) {
                    return Optional.of(Name.identifier(localPart));
                } else {
                    return Optional.empty();
                }
            }
            String absolute = makeToAbsoluteLocal(URI.createURI(container));
            EcoreSystem localSystem;
            if (absoluteFilesToLocalURIs.containsKey(absolute)) {
                localSystem = this.registeredSystems.get(absoluteFilesToLocalURIs.get(absolute));
            } else {
                localSystem = tryParsing(absolute);
            }
            return localSystem.resolveURI(localPart);
        } else {
            return contextResolver.resolveURI(uri);
        }
    }

    private EcoreSystem tryParsing(String absolute) {
        throw new RuntimeException("Naah!");
    }

    public String makeToAbsoluteLocal(URI uri) {
        return "";
    }


    @Override
    public org.eclipse.emf.common.util.URI normalize(org.eclipse.emf.common.util.URI uri) {
        return null;
    }

    @Override
    public Map<org.eclipse.emf.common.util.URI, org.eclipse.emf.common.util.URI> getURIMap() {
        return null;
    }

    @Override
    public EList<URIHandler> getURIHandlers() {
        return null;
    }

    @Override
    public URIHandler getURIHandler(org.eclipse.emf.common.util.URI uri) {
        return null;
    }

    @Override
    public EList<ContentHandler> getContentHandlers() {
        return null;
    }

    @Override
    public InputStream createInputStream(org.eclipse.emf.common.util.URI uri) throws IOException {
        return null;
    }

    @Override
    public InputStream createInputStream(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {
        return null;
    }

    @Override
    public OutputStream createOutputStream(org.eclipse.emf.common.util.URI uri) throws IOException {
        return null;
    }

    @Override
    public OutputStream createOutputStream(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {
        return null;
    }

    @Override
    public void delete(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {

    }

    @Override
    public Map<String, ?> contentDescription(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {
        return null;
    }

    @Override
    public boolean exists(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) {
        return false;
    }

    @Override
    public Map<String, ?> getAttributes(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) {
        return null;
    }

    @Override
    public void setAttributes(org.eclipse.emf.common.util.URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {

    }
}
