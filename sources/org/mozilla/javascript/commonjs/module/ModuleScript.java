package org.mozilla.javascript.commonjs.module;

import java.io.Serializable;
import java.net.URI;
import org.mozilla.javascript.Script;

public class ModuleScript implements Serializable {
    private static final long serialVersionUID = 1;
    private final URI base;
    private final Script script;
    private final URI uri;

    public ModuleScript(Script script2, URI uri2, URI uri3) {
        this.script = script2;
        this.uri = uri2;
        this.base = uri3;
    }

    public URI getBase() {
        return this.base;
    }

    public Script getScript() {
        return this.script;
    }

    public URI getUri() {
        return this.uri;
    }

    public boolean isSandboxed() {
        URI uri2;
        URI uri3 = this.base;
        if (uri3 == null || (uri2 = this.uri) == null || uri3.relativize(uri2).isAbsolute()) {
            return false;
        }
        return true;
    }
}
