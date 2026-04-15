package org.mozilla.javascript.commonjs.module.provider;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public abstract class ModuleSourceProviderBase implements ModuleSourceProvider, Serializable {
    private static final long serialVersionUID = 1;

    private static String ensureTrailingSlash(String str) {
        if (str.endsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
            return str;
        }
        return str.concat(MqttTopic.TOPIC_LEVEL_SEPARATOR);
    }

    private ModuleSource loadFromPathArray(String str, Scriptable scriptable, Object obj) throws IOException {
        int i;
        long uint32 = ScriptRuntime.toUint32(ScriptableObject.getProperty(scriptable, "length"));
        if (uint32 > 2147483647L) {
            i = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        } else {
            i = (int) uint32;
        }
        int i2 = 0;
        while (i2 < i) {
            String ensureTrailingSlash = ensureTrailingSlash((String) ScriptableObject.getTypedProperty(scriptable, i2, String.class));
            try {
                URI uri = new URI(ensureTrailingSlash);
                if (!uri.isAbsolute()) {
                    uri = new File(ensureTrailingSlash).toURI().resolve("");
                }
                ModuleSource loadFromUri = loadFromUri(uri.resolve(str), uri, obj);
                if (loadFromUri != null) {
                    return loadFromUri;
                }
                i2++;
            } catch (URISyntaxException e) {
                throw new MalformedURLException(e.getMessage());
            }
        }
        return null;
    }

    public boolean entityNeedsRevalidation(Object obj) {
        return true;
    }

    public ModuleSource loadFromFallbackLocations(String str, Object obj) throws IOException, URISyntaxException {
        return null;
    }

    public ModuleSource loadFromPrivilegedLocations(String str, Object obj) throws IOException, URISyntaxException {
        return null;
    }

    public abstract ModuleSource loadFromUri(URI uri, URI uri2, Object obj) throws IOException, URISyntaxException;

    public ModuleSource loadSource(String str, Scriptable scriptable, Object obj) throws IOException, URISyntaxException {
        ModuleSource loadFromPathArray;
        if (!entityNeedsRevalidation(obj)) {
            return ModuleSourceProvider.NOT_MODIFIED;
        }
        ModuleSource loadFromPrivilegedLocations = loadFromPrivilegedLocations(str, obj);
        if (loadFromPrivilegedLocations != null) {
            return loadFromPrivilegedLocations;
        }
        if (scriptable == null || (loadFromPathArray = loadFromPathArray(str, scriptable, obj)) == null) {
            return loadFromFallbackLocations(str, obj);
        }
        return loadFromPathArray;
    }

    public ModuleSource loadSource(URI uri, URI uri2, Object obj) throws IOException, URISyntaxException {
        return loadFromUri(uri, uri2, obj);
    }
}
