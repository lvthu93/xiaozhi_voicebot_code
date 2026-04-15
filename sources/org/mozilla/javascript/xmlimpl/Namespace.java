package org.mozilla.javascript.xmlimpl;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.xmlimpl.XmlNode;

class Namespace extends IdScriptableObject {
    private static final int Id_constructor = 1;
    private static final int Id_prefix = 1;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    private static final int Id_uri = 2;
    private static final int MAX_INSTANCE_ID = 2;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final Object NAMESPACE_TAG = "Namespace";
    static final long serialVersionUID = -5765755238131301744L;
    private XmlNode.Namespace ns;
    private Namespace prototype;

    private Namespace() {
    }

    public static Namespace create(Scriptable scriptable, Namespace namespace, XmlNode.Namespace namespace2) {
        Namespace namespace3 = new Namespace();
        namespace3.setParentScope(scriptable);
        namespace3.prototype = namespace;
        namespace3.setPrototype(namespace);
        namespace3.ns = namespace2;
        return namespace3;
    }

    private boolean equals(Namespace namespace) {
        return uri().equals(namespace.uri());
    }

    private Object jsConstructor(Context context, boolean z, Object[] objArr) {
        if (!z && objArr.length == 1) {
            return castToNamespace(objArr[0]);
        }
        if (objArr.length == 0) {
            return constructNamespace();
        }
        if (objArr.length == 1) {
            return constructNamespace(objArr[0]);
        }
        return constructNamespace(objArr[0], objArr[1]);
    }

    private String js_toSource() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        toSourceImpl(this.ns.getPrefix(), this.ns.getUri(), sb);
        sb.append(')');
        return sb.toString();
    }

    private Namespace realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof Namespace) {
            return (Namespace) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    public static void toSourceImpl(String str, String str2, StringBuilder sb) {
        sb.append("new Namespace(");
        if (str2.length() != 0) {
            sb.append('\'');
            if (str != null) {
                sb.append(ScriptRuntime.escapeString(str, '\''));
                sb.append("', '");
            }
            sb.append(ScriptRuntime.escapeString(str2, '\''));
            sb.append('\'');
        } else if (!"".equals(str)) {
            throw new IllegalArgumentException(str);
        }
        sb.append(')');
    }

    public Namespace castToNamespace(Object obj) {
        if (obj instanceof Namespace) {
            return (Namespace) obj;
        }
        return constructNamespace(obj);
    }

    public Namespace constructNamespace(Object obj) {
        String str;
        String str2;
        if (obj instanceof Namespace) {
            Namespace namespace = (Namespace) obj;
            str2 = namespace.prefix();
            str = namespace.uri();
        } else {
            if (obj instanceof QName) {
                QName qName = (QName) obj;
                String uri = qName.uri();
                if (uri != null) {
                    String str3 = uri;
                    str2 = qName.prefix();
                    str = str3;
                } else {
                    str = qName.toString();
                }
            } else {
                str = ScriptRuntime.toString(obj);
                if (str.length() == 0) {
                    str2 = "";
                }
            }
            str2 = null;
        }
        return newNamespace(str2, str);
    }

    public Object equivalentValues(Object obj) {
        if (!(obj instanceof Namespace)) {
            return Scriptable.NOT_FOUND;
        }
        if (equals((Namespace) obj)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(NAMESPACE_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        boolean z = true;
        if (methodId == 1) {
            if (scriptable2 != null) {
                z = false;
            }
            return jsConstructor(context, z, objArr);
        } else if (methodId == 2) {
            return realThis(scriptable2, idFunctionObject).toString();
        } else {
            if (methodId == 3) {
                return realThis(scriptable2, idFunctionObject).js_toSource();
            }
            throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    public void exportAsJSClass(boolean z) {
        exportAsJSClass(3, getParentScope(), z);
    }

    public int findInstanceIdInfo(String str) {
        int i;
        String str2;
        int length = str.length();
        int i2 = 0;
        if (length == 3) {
            str2 = "uri";
            i = 2;
        } else if (length == 6) {
            str2 = "prefix";
            i = 1;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            i2 = i;
        }
        if (i2 == 0) {
            return super.findInstanceIdInfo(str);
        }
        if (i2 == 1 || i2 == 2) {
            return IdScriptableObject.instanceIdInfo(5, super.getMaxInstanceId() + i2);
        }
        throw new IllegalStateException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r5) {
        /*
            r4 = this;
            int r0 = r5.length()
            r1 = 8
            r2 = 0
            if (r0 != r1) goto L_0x001d
            r0 = 3
            char r1 = r5.charAt(r0)
            r3 = 111(0x6f, float:1.56E-43)
            if (r1 != r3) goto L_0x0015
            java.lang.String r1 = "toSource"
            goto L_0x0027
        L_0x0015:
            r0 = 116(0x74, float:1.63E-43)
            if (r1 != r0) goto L_0x0025
            java.lang.String r1 = "toString"
            r0 = 2
            goto L_0x0027
        L_0x001d:
            r1 = 11
            if (r0 != r1) goto L_0x0025
            java.lang.String r1 = "constructor"
            r0 = 1
            goto L_0x0027
        L_0x0025:
            r1 = 0
            r0 = 0
        L_0x0027:
            if (r1 == 0) goto L_0x0032
            if (r1 == r5) goto L_0x0032
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L_0x0032
            goto L_0x0033
        L_0x0032:
            r2 = r0
        L_0x0033:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.xmlimpl.Namespace.findPrototypeId(java.lang.String):int");
    }

    public String getClassName() {
        return "Namespace";
    }

    public Object getDefaultValue(Class<?> cls) {
        return uri();
    }

    public final XmlNode.Namespace getDelegate() {
        return this.ns;
    }

    public String getInstanceIdName(int i) {
        int maxInstanceId = i - super.getMaxInstanceId();
        if (maxInstanceId == 1) {
            return "prefix";
        }
        if (maxInstanceId != 2) {
            return super.getInstanceIdName(i);
        }
        return "uri";
    }

    public Object getInstanceIdValue(int i) {
        int maxInstanceId = i - super.getMaxInstanceId();
        if (maxInstanceId != 1) {
            if (maxInstanceId != 2) {
                return super.getInstanceIdValue(i);
            }
            return this.ns.getUri();
        } else if (this.ns.getPrefix() == null) {
            return Undefined.instance;
        } else {
            return this.ns.getPrefix();
        }
    }

    public int getMaxInstanceId() {
        return super.getMaxInstanceId() + 2;
    }

    public int hashCode() {
        return uri().hashCode();
    }

    public void initPrototypeId(int i) {
        String str;
        int i2;
        if (i != 1) {
            i2 = 0;
            if (i == 2) {
                str = "toString";
            } else if (i == 3) {
                str = "toSource";
            } else {
                throw new IllegalArgumentException(String.valueOf(i));
            }
        } else {
            str = "constructor";
            i2 = 2;
        }
        initPrototypeMethod(NAMESPACE_TAG, i, str, i2);
    }

    public Namespace newNamespace(String str) {
        Namespace namespace = this.prototype;
        if (namespace == null) {
            namespace = this;
        }
        return create(getParentScope(), namespace, XmlNode.Namespace.create(str));
    }

    public String prefix() {
        return this.ns.getPrefix();
    }

    public String toLocaleString() {
        return toString();
    }

    public String toString() {
        return uri();
    }

    public String uri() {
        return this.ns.getUri();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Namespace)) {
            return false;
        }
        return equals((Namespace) obj);
    }

    public Namespace newNamespace(String str, String str2) {
        if (str == null) {
            return newNamespace(str2);
        }
        Namespace namespace = this.prototype;
        if (namespace == null) {
            namespace = this;
        }
        return create(getParentScope(), namespace, XmlNode.Namespace.create(str, str2));
    }

    private Namespace constructNamespace(Object obj, Object obj2) {
        String str;
        if (obj2 instanceof QName) {
            QName qName = (QName) obj2;
            str = qName.uri();
            if (str == null) {
                str = qName.toString();
            }
        } else {
            str = ScriptRuntime.toString(obj2);
        }
        String str2 = "";
        if (str.length() == 0) {
            if (obj != Undefined.instance) {
                str2 = ScriptRuntime.toString(obj);
                if (str2.length() != 0) {
                    throw ScriptRuntime.typeError("Illegal prefix '" + str2 + "' for 'no namespace'.");
                }
            }
        } else if (obj != Undefined.instance && XMLName.accept(obj)) {
            str2 = ScriptRuntime.toString(obj);
        }
        return newNamespace(str2, str);
    }

    private Namespace constructNamespace() {
        return newNamespace("", "");
    }
}
