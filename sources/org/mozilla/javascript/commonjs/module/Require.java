package org.mozilla.javascript.commonjs.module;

import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Require extends BaseFunction {
    private static final ThreadLocal<Map<String, Scriptable>> loadingModuleInterfaces = new ThreadLocal<>();
    private static final long serialVersionUID = 1;
    private final Map<String, Scriptable> exportedModuleInterfaces = new ConcurrentHashMap();
    private final Object loadLock = new Object();
    private Scriptable mainExports;
    private String mainModuleId = null;
    private final ModuleScriptProvider moduleScriptProvider;
    private final Scriptable nativeScope;
    private final Scriptable paths;
    private final Script postExec;
    private final Script preExec;
    private final boolean sandboxed;

    public Require(Context context, Scriptable scriptable, ModuleScriptProvider moduleScriptProvider2, Script script, Script script2, boolean z) {
        this.moduleScriptProvider = moduleScriptProvider2;
        this.nativeScope = scriptable;
        this.sandboxed = z;
        this.preExec = script;
        this.postExec = script2;
        setPrototype(ScriptableObject.getFunctionPrototype(scriptable));
        if (!z) {
            Scriptable newArray = context.newArray(scriptable, 0);
            this.paths = newArray;
            defineReadOnlyProperty(this, "paths", newArray);
            return;
        }
        this.paths = null;
    }

    private static void defineReadOnlyProperty(ScriptableObject scriptableObject, String str, Object obj) {
        ScriptableObject.putProperty((Scriptable) scriptableObject, str, obj);
        scriptableObject.setAttributes(str, 5);
    }

    private Scriptable executeModuleScript(Context context, String str, Scriptable scriptable, ModuleScript moduleScript, boolean z) {
        ScriptableObject scriptableObject = (ScriptableObject) context.newObject(this.nativeScope);
        URI uri = moduleScript.getUri();
        URI base = moduleScript.getBase();
        defineReadOnlyProperty(scriptableObject, "id", str);
        if (!this.sandboxed) {
            defineReadOnlyProperty(scriptableObject, "uri", uri.toString());
        }
        ModuleScope moduleScope = new ModuleScope(this.nativeScope, uri, base);
        moduleScope.put("exports", (Scriptable) moduleScope, (Object) scriptable);
        moduleScope.put("module", (Scriptable) moduleScope, (Object) scriptableObject);
        scriptableObject.put("exports", (Scriptable) scriptableObject, (Object) scriptable);
        install(moduleScope);
        if (z) {
            defineReadOnlyProperty(this, "main", scriptableObject);
        }
        executeOptionalScript(this.preExec, context, moduleScope);
        moduleScript.getScript().exec(context, moduleScope);
        executeOptionalScript(this.postExec, context, moduleScope);
        return ScriptRuntime.toObject(context, this.nativeScope, ScriptableObject.getProperty((Scriptable) scriptableObject, "exports"));
    }

    private static void executeOptionalScript(Script script, Context context, Scriptable scriptable) {
        if (script != null) {
            script.exec(context, scriptable);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0093, code lost:
        return r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.Scriptable getExportedModuleInterface(org.mozilla.javascript.Context r11, java.lang.String r12, java.net.URI r13, java.net.URI r14, boolean r15) {
        /*
            r10 = this;
            java.lang.String r0 = "Module \""
            java.util.Map<java.lang.String, org.mozilla.javascript.Scriptable> r1 = r10.exportedModuleInterfaces
            java.lang.Object r1 = r1.get(r12)
            org.mozilla.javascript.Scriptable r1 = (org.mozilla.javascript.Scriptable) r1
            if (r1 == 0) goto L_0x0017
            if (r15 != 0) goto L_0x000f
            return r1
        L_0x000f:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "Attempt to set main module after it was loaded"
            r11.<init>(r12)
            throw r11
        L_0x0017:
            java.lang.ThreadLocal<java.util.Map<java.lang.String, org.mozilla.javascript.Scriptable>> r1 = loadingModuleInterfaces
            java.lang.Object r2 = r1.get()
            java.util.Map r2 = (java.util.Map) r2
            if (r2 == 0) goto L_0x002a
            java.lang.Object r3 = r2.get(r12)
            org.mozilla.javascript.Scriptable r3 = (org.mozilla.javascript.Scriptable) r3
            if (r3 == 0) goto L_0x002a
            return r3
        L_0x002a:
            java.lang.Object r3 = r10.loadLock
            monitor-enter(r3)
            java.util.Map<java.lang.String, org.mozilla.javascript.Scriptable> r4 = r10.exportedModuleInterfaces     // Catch:{ all -> 0x00a8 }
            java.lang.Object r4 = r4.get(r12)     // Catch:{ all -> 0x00a8 }
            org.mozilla.javascript.Scriptable r4 = (org.mozilla.javascript.Scriptable) r4     // Catch:{ all -> 0x00a8 }
            if (r4 == 0) goto L_0x0039
            monitor-exit(r3)     // Catch:{ all -> 0x00a8 }
            return r4
        L_0x0039:
            org.mozilla.javascript.commonjs.module.ModuleScript r8 = r10.getModule(r11, r12, r13, r14)     // Catch:{ all -> 0x00a8 }
            boolean r13 = r10.sandboxed     // Catch:{ all -> 0x00a8 }
            if (r13 == 0) goto L_0x0060
            boolean r13 = r8.isSandboxed()     // Catch:{ all -> 0x00a8 }
            if (r13 == 0) goto L_0x0048
            goto L_0x0060
        L_0x0048:
            org.mozilla.javascript.Scriptable r13 = r10.nativeScope     // Catch:{ all -> 0x00a8 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            r14.<init>(r0)     // Catch:{ all -> 0x00a8 }
            r14.append(r12)     // Catch:{ all -> 0x00a8 }
            java.lang.String r12 = "\" is not contained in sandbox."
            r14.append(r12)     // Catch:{ all -> 0x00a8 }
            java.lang.String r12 = r14.toString()     // Catch:{ all -> 0x00a8 }
            org.mozilla.javascript.JavaScriptException r11 = org.mozilla.javascript.ScriptRuntime.throwError(r11, r13, r12)     // Catch:{ all -> 0x00a8 }
            throw r11     // Catch:{ all -> 0x00a8 }
        L_0x0060:
            org.mozilla.javascript.Scriptable r13 = r10.nativeScope     // Catch:{ all -> 0x00a8 }
            org.mozilla.javascript.Scriptable r13 = r11.newObject(r13)     // Catch:{ all -> 0x00a8 }
            if (r2 != 0) goto L_0x006a
            r14 = 1
            goto L_0x006b
        L_0x006a:
            r14 = 0
        L_0x006b:
            if (r14 == 0) goto L_0x0075
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x00a8 }
            r2.<init>()     // Catch:{ all -> 0x00a8 }
            r1.set(r2)     // Catch:{ all -> 0x00a8 }
        L_0x0075:
            r2.put(r12, r13)     // Catch:{ all -> 0x00a8 }
            r0 = 0
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r9 = r15
            org.mozilla.javascript.Scriptable r11 = r4.executeModuleScript(r5, r6, r7, r8, r9)     // Catch:{ RuntimeException -> 0x0096 }
            if (r13 == r11) goto L_0x0088
            r2.put(r12, r11)     // Catch:{ RuntimeException -> 0x0096 }
            r13 = r11
        L_0x0088:
            if (r14 == 0) goto L_0x0092
            java.util.Map<java.lang.String, org.mozilla.javascript.Scriptable> r11 = r10.exportedModuleInterfaces     // Catch:{ all -> 0x00a8 }
            r11.putAll(r2)     // Catch:{ all -> 0x00a8 }
            r1.set(r0)     // Catch:{ all -> 0x00a8 }
        L_0x0092:
            monitor-exit(r3)     // Catch:{ all -> 0x00a8 }
            return r13
        L_0x0094:
            r11 = move-exception
            goto L_0x009b
        L_0x0096:
            r11 = move-exception
            r2.remove(r12)     // Catch:{ all -> 0x0094 }
            throw r11     // Catch:{ all -> 0x0094 }
        L_0x009b:
            if (r14 == 0) goto L_0x00a7
            java.util.Map<java.lang.String, org.mozilla.javascript.Scriptable> r12 = r10.exportedModuleInterfaces     // Catch:{ all -> 0x00a8 }
            r12.putAll(r2)     // Catch:{ all -> 0x00a8 }
            java.lang.ThreadLocal<java.util.Map<java.lang.String, org.mozilla.javascript.Scriptable>> r12 = loadingModuleInterfaces     // Catch:{ all -> 0x00a8 }
            r12.set(r0)     // Catch:{ all -> 0x00a8 }
        L_0x00a7:
            throw r11     // Catch:{ all -> 0x00a8 }
        L_0x00a8:
            r11 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00a8 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.commonjs.module.Require.getExportedModuleInterface(org.mozilla.javascript.Context, java.lang.String, java.net.URI, java.net.URI, boolean):org.mozilla.javascript.Scriptable");
    }

    private ModuleScript getModule(Context context, String str, URI uri, URI uri2) {
        try {
            ModuleScript moduleScript = this.moduleScriptProvider.getModuleScript(context, str, uri, uri2, this.paths);
            if (moduleScript != null) {
                return moduleScript;
            }
            Scriptable scriptable = this.nativeScope;
            throw ScriptRuntime.throwError(context, scriptable, "Module \"" + str + "\" not found.");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw Context.throwAsScriptRuntimeEx(e2);
        }
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        URI uri;
        URI uri2;
        if (objArr == null || objArr.length < 1) {
            throw ScriptRuntime.throwError(context, scriptable, "require() needs one argument");
        }
        String str = (String) Context.jsToJava(objArr[0], String.class);
        if (!str.startsWith("./") && !str.startsWith("../")) {
            uri2 = null;
            uri = null;
        } else if (scriptable2 instanceof ModuleScope) {
            ModuleScope moduleScope = (ModuleScope) scriptable2;
            URI base = moduleScope.getBase();
            URI uri3 = moduleScope.getUri();
            URI resolve = uri3.resolve(str);
            if (base == null) {
                str = resolve.toString();
            } else {
                str = base.relativize(uri3).resolve(str).toString();
                if (str.charAt(0) == '.') {
                    if (!this.sandboxed) {
                        str = resolve.toString();
                    } else {
                        throw ScriptRuntime.throwError(context, scriptable, "Module \"" + str + "\" is not contained in sandbox.");
                    }
                }
            }
            uri = base;
            uri2 = resolve;
        } else {
            throw ScriptRuntime.throwError(context, scriptable, "Can't resolve relative module ID \"" + str + "\" when require() is used outside of a module");
        }
        return getExportedModuleInterface(context, str, uri2, uri, false);
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        throw ScriptRuntime.throwError(context, scriptable, "require() can not be invoked as a constructor");
    }

    public int getArity() {
        return 1;
    }

    public String getFunctionName() {
        return "require";
    }

    public int getLength() {
        return 1;
    }

    public void install(Scriptable scriptable) {
        ScriptableObject.putProperty(scriptable, "require", (Object) this);
    }

    public Scriptable requireMain(Context context, String str) {
        URI uri;
        String str2 = this.mainModuleId;
        if (str2 == null) {
            try {
                if (this.moduleScriptProvider.getModuleScript(context, str, (URI) null, (URI) null, this.paths) != null) {
                    this.mainExports = getExportedModuleInterface(context, str, (URI) null, (URI) null, true);
                } else if (!this.sandboxed) {
                    try {
                        uri = new URI(str);
                    } catch (URISyntaxException unused) {
                        uri = null;
                    }
                    if (uri == null || !uri.isAbsolute()) {
                        File file = new File(str);
                        if (file.isFile()) {
                            uri = file.toURI();
                        } else {
                            Scriptable scriptable = this.nativeScope;
                            throw ScriptRuntime.throwError(context, scriptable, "Module \"" + str + "\" not found.");
                        }
                    }
                    URI uri2 = uri;
                    this.mainExports = getExportedModuleInterface(context, uri2.toString(), uri2, (URI) null, true);
                }
                this.mainModuleId = str;
                return this.mainExports;
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        } else if (str2.equals(str)) {
            return this.mainExports;
        } else {
            throw new IllegalStateException("Main module already set to " + this.mainModuleId);
        }
    }
}
