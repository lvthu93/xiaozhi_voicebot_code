package org.mozilla.javascript.commonjs.module.provider;

import java.io.Serializable;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;

public abstract class CachingModuleScriptProviderBase implements ModuleScriptProvider, Serializable {
    private static final int loadConcurrencyLevel = (Runtime.getRuntime().availableProcessors() * 8);
    private static final int loadLockCount;
    private static final int loadLockMask;
    private static final int loadLockShift;
    private static final long serialVersionUID = -1;
    private final Object[] loadLocks = new Object[loadLockCount];
    private final ModuleSourceProvider moduleSourceProvider;

    public static class CachedModuleScript {
        private final ModuleScript moduleScript;
        private final Object validator;

        public CachedModuleScript(ModuleScript moduleScript2, Object obj) {
            this.moduleScript = moduleScript2;
            this.validator = obj;
        }

        public ModuleScript getModule() {
            return this.moduleScript;
        }

        public Object getValidator() {
            return this.validator;
        }
    }

    static {
        int i = 0;
        int i2 = 1;
        while (i2 < loadConcurrencyLevel) {
            i++;
            i2 <<= 1;
        }
        loadLockShift = 32 - i;
        loadLockMask = i2 - 1;
        loadLockCount = i2;
    }

    public CachingModuleScriptProviderBase(ModuleSourceProvider moduleSourceProvider2) {
        int i = 0;
        while (true) {
            Object[] objArr = this.loadLocks;
            if (i < objArr.length) {
                objArr[i] = new Object();
                i++;
            } else {
                this.moduleSourceProvider = moduleSourceProvider2;
                return;
            }
        }
    }

    private static boolean equal(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public static int getConcurrencyLevel() {
        return loadLockCount;
    }

    private static Object getValidator(CachedModuleScript cachedModuleScript) {
        if (cachedModuleScript == null) {
            return null;
        }
        return cachedModuleScript.getValidator();
    }

    public abstract CachedModuleScript getLoadedModule(String str);

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004c, code lost:
        if (r9 == null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004e, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0051, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
        if (r9 == null) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007f, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0080, code lost:
        if (r9 != null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0086, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0087, code lost:
        r6.addSuppressed(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x008a, code lost:
        throw r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.mozilla.javascript.commonjs.module.ModuleScript getModuleScript(org.mozilla.javascript.Context r6, java.lang.String r7, java.net.URI r8, java.net.URI r9, org.mozilla.javascript.Scriptable r10) throws java.lang.Exception {
        /*
            r5 = this;
            org.mozilla.javascript.commonjs.module.provider.CachingModuleScriptProviderBase$CachedModuleScript r0 = r5.getLoadedModule(r7)
            java.lang.Object r1 = getValidator(r0)
            if (r8 != 0) goto L_0x0011
            org.mozilla.javascript.commonjs.module.provider.ModuleSourceProvider r8 = r5.moduleSourceProvider
            org.mozilla.javascript.commonjs.module.provider.ModuleSource r8 = r8.loadSource((java.lang.String) r7, (org.mozilla.javascript.Scriptable) r10, (java.lang.Object) r1)
            goto L_0x0017
        L_0x0011:
            org.mozilla.javascript.commonjs.module.provider.ModuleSourceProvider r10 = r5.moduleSourceProvider
            org.mozilla.javascript.commonjs.module.provider.ModuleSource r8 = r10.loadSource((java.net.URI) r8, (java.net.URI) r9, (java.lang.Object) r1)
        L_0x0017:
            org.mozilla.javascript.commonjs.module.provider.ModuleSource r9 = org.mozilla.javascript.commonjs.module.provider.ModuleSourceProvider.NOT_MODIFIED
            if (r8 != r9) goto L_0x0020
            org.mozilla.javascript.commonjs.module.ModuleScript r6 = r0.getModule()
            return r6
        L_0x0020:
            if (r8 != 0) goto L_0x0024
            r6 = 0
            return r6
        L_0x0024:
            java.io.Reader r9 = r8.getReader()
            int r10 = r7.hashCode()     // Catch:{ all -> 0x007d }
            java.lang.Object[] r0 = r5.loadLocks     // Catch:{ all -> 0x007d }
            int r2 = loadLockShift     // Catch:{ all -> 0x007d }
            int r10 = r10 >>> r2
            int r2 = loadLockMask     // Catch:{ all -> 0x007d }
            r10 = r10 & r2
            r10 = r0[r10]     // Catch:{ all -> 0x007d }
            monitor-enter(r10)     // Catch:{ all -> 0x007d }
            org.mozilla.javascript.commonjs.module.provider.CachingModuleScriptProviderBase$CachedModuleScript r0 = r5.getLoadedModule(r7)     // Catch:{ all -> 0x007a }
            if (r0 == 0) goto L_0x0052
            java.lang.Object r2 = getValidator(r0)     // Catch:{ all -> 0x007a }
            boolean r1 = equal(r1, r2)     // Catch:{ all -> 0x007a }
            if (r1 != 0) goto L_0x0052
            org.mozilla.javascript.commonjs.module.ModuleScript r6 = r0.getModule()     // Catch:{ all -> 0x007a }
            monitor-exit(r10)     // Catch:{ all -> 0x007a }
            if (r9 == 0) goto L_0x0051
            r9.close()
        L_0x0051:
            return r6
        L_0x0052:
            java.net.URI r0 = r8.getUri()     // Catch:{ all -> 0x007a }
            org.mozilla.javascript.commonjs.module.ModuleScript r1 = new org.mozilla.javascript.commonjs.module.ModuleScript     // Catch:{ all -> 0x007a }
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x007a }
            java.lang.Object r3 = r8.getSecurityDomain()     // Catch:{ all -> 0x007a }
            r4 = 1
            org.mozilla.javascript.Script r6 = r6.compileReader(r9, r2, r4, r3)     // Catch:{ all -> 0x007a }
            java.net.URI r2 = r8.getBase()     // Catch:{ all -> 0x007a }
            r1.<init>(r6, r0, r2)     // Catch:{ all -> 0x007a }
            java.lang.Object r6 = r8.getValidator()     // Catch:{ all -> 0x007a }
            r5.putLoadedModule(r7, r1, r6)     // Catch:{ all -> 0x007a }
            monitor-exit(r10)     // Catch:{ all -> 0x007a }
            if (r9 == 0) goto L_0x0079
            r9.close()
        L_0x0079:
            return r1
        L_0x007a:
            r6 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x007a }
            throw r6     // Catch:{ all -> 0x007d }
        L_0x007d:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x007f }
        L_0x007f:
            r7 = move-exception
            if (r9 == 0) goto L_0x008a
            r9.close()     // Catch:{ all -> 0x0086 }
            goto L_0x008a
        L_0x0086:
            r8 = move-exception
            r6.addSuppressed(r8)
        L_0x008a:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.commonjs.module.provider.CachingModuleScriptProviderBase.getModuleScript(org.mozilla.javascript.Context, java.lang.String, java.net.URI, java.net.URI, org.mozilla.javascript.Scriptable):org.mozilla.javascript.commonjs.module.ModuleScript");
    }

    public abstract void putLoadedModule(String str, ModuleScript moduleScript, Object obj);
}
