package org.mozilla.javascript;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.mozilla.javascript.json.JsonParser;

public final class NativeJSON extends IdScriptableObject {
    private static final int Id_parse = 2;
    private static final int Id_stringify = 3;
    private static final int Id_toSource = 1;
    private static final Object JSON_TAG = "JSON";
    private static final int LAST_METHOD_ID = 3;
    private static final int MAX_ID = 3;
    private static final int MAX_STRINGIFY_GAP_LENGTH = 10;
    private static final long serialVersionUID = -4567599697595654984L;

    public static class StringifyState {
        Context cx;
        String gap;
        String indent;
        List<Object> propertyList;
        Callable replacer;
        Scriptable scope;
        Stack<Scriptable> stack = new Stack<>();

        public StringifyState(Context context, Scriptable scriptable, String str, String str2, Callable callable, List<Object> list) {
            this.cx = context;
            this.scope = scriptable;
            this.indent = str;
            this.gap = str2;
            this.replacer = callable;
            this.propertyList = list;
        }
    }

    private NativeJSON() {
    }

    public static void init(Scriptable scriptable, boolean z) {
        NativeJSON nativeJSON = new NativeJSON();
        nativeJSON.activatePrototypeMap(3);
        nativeJSON.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeJSON.setParentScope(scriptable);
        if (z) {
            nativeJSON.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, "JSON", nativeJSON, 2);
    }

    private static String ja(NativeArray nativeArray, StringifyState stringifyState) {
        String str;
        Object obj;
        if (stringifyState.stack.search(nativeArray) == -1) {
            stringifyState.stack.push(nativeArray);
            String str2 = stringifyState.indent;
            stringifyState.indent += stringifyState.gap;
            LinkedList linkedList = new LinkedList();
            long length = nativeArray.getLength();
            for (long j = 0; j < length; j++) {
                if (j > 2147483647L) {
                    obj = str(Long.toString(j), nativeArray, stringifyState);
                } else {
                    obj = str(Integer.valueOf((int) j), nativeArray, stringifyState);
                }
                if (obj == Undefined.instance) {
                    linkedList.add("null");
                } else {
                    linkedList.add(obj);
                }
            }
            if (linkedList.isEmpty()) {
                str = "[]";
            } else if (stringifyState.gap.length() == 0) {
                str = "[" + join(linkedList, ",") + ']';
            } else {
                str = "[\n" + stringifyState.indent + join(linkedList, ",\n" + stringifyState.indent) + 10 + str2 + ']';
            }
            stringifyState.stack.pop();
            stringifyState.indent = str2;
            return str;
        }
        throw ScriptRuntime.typeError0("msg.cyclic.value");
    }

    private static String jo(Scriptable scriptable, StringifyState stringifyState) {
        Object[] objArr;
        String str;
        if (stringifyState.stack.search(scriptable) == -1) {
            stringifyState.stack.push(scriptable);
            String str2 = stringifyState.indent;
            stringifyState.indent += stringifyState.gap;
            List<Object> list = stringifyState.propertyList;
            if (list != null) {
                objArr = list.toArray();
            } else {
                objArr = scriptable.getIds();
            }
            LinkedList linkedList = new LinkedList();
            for (Object obj : objArr) {
                Object str3 = str(obj, scriptable, stringifyState);
                if (str3 != Undefined.instance) {
                    String k = y2.k(new StringBuilder(), quote(obj.toString()), ":");
                    if (stringifyState.gap.length() > 0) {
                        k = y2.x(k, " ");
                    }
                    linkedList.add(k + str3);
                }
            }
            if (linkedList.isEmpty()) {
                str = "{}";
            } else if (stringifyState.gap.length() == 0) {
                str = "{" + join(linkedList, ",") + '}';
            } else {
                str = "{\n" + stringifyState.indent + join(linkedList, ",\n" + stringifyState.indent) + 10 + str2 + '}';
            }
            stringifyState.stack.pop();
            stringifyState.indent = str2;
            return str;
        }
        throw ScriptRuntime.typeError0("msg.cyclic.value");
    }

    private static String join(Collection<Object> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        Iterator<Object> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(it.next().toString());
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        return sb.toString();
    }

    private static Object parse(Context context, Scriptable scriptable, String str) {
        try {
            return new JsonParser(context, scriptable).parseValue(str);
        } catch (JsonParser.ParseException e) {
            throw ScriptRuntime.constructError("SyntaxError", e.getMessage());
        }
    }

    private static String quote(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == 12) {
                sb.append("\\f");
            } else if (charAt == 13) {
                sb.append("\\r");
            } else if (charAt == '\"') {
                sb.append("\\\"");
            } else if (charAt != '\\') {
                switch (charAt) {
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    default:
                        if (charAt >= ' ') {
                            sb.append(charAt);
                            break;
                        } else {
                            sb.append("\\u");
                            sb.append(String.format("%04x", new Object[]{Integer.valueOf(charAt)}));
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    private static String repeat(char c, int i) {
        char[] cArr = new char[i];
        Arrays.fill(cArr, c);
        return new String(cArr);
    }

    private static Object str(Object obj, Scriptable scriptable, StringifyState stringifyState) {
        Object obj2;
        if (obj instanceof String) {
            obj2 = ScriptableObject.getProperty(scriptable, (String) obj);
        } else {
            obj2 = ScriptableObject.getProperty(scriptable, ((Number) obj).intValue());
        }
        if (obj2 instanceof Scriptable) {
            Scriptable scriptable2 = (Scriptable) obj2;
            if (ScriptableObject.hasProperty(scriptable2, "toJSON") && (ScriptableObject.getProperty(scriptable2, "toJSON") instanceof Callable)) {
                obj2 = ScriptableObject.callMethod(stringifyState.cx, scriptable2, "toJSON", new Object[]{obj});
            }
        }
        Callable callable = stringifyState.replacer;
        if (callable != null) {
            obj2 = callable.call(stringifyState.cx, stringifyState.scope, scriptable, new Object[]{obj, obj2});
        }
        if (obj2 instanceof NativeNumber) {
            obj2 = Double.valueOf(ScriptRuntime.toNumber(obj2));
        } else if (obj2 instanceof NativeString) {
            obj2 = ScriptRuntime.toString(obj2);
        } else if (obj2 instanceof NativeBoolean) {
            obj2 = ((NativeBoolean) obj2).getDefaultValue(ScriptRuntime.BooleanClass);
        }
        if (obj2 == null) {
            return "null";
        }
        if (obj2.equals(Boolean.TRUE)) {
            return "true";
        }
        if (obj2.equals(Boolean.FALSE)) {
            return "false";
        }
        if (obj2 instanceof CharSequence) {
            return quote(obj2.toString());
        }
        if (obj2 instanceof Number) {
            double doubleValue = ((Number) obj2).doubleValue();
            if (Double.isNaN(doubleValue) || doubleValue == Double.POSITIVE_INFINITY || doubleValue == Double.NEGATIVE_INFINITY) {
                return "null";
            }
            return ScriptRuntime.toString(obj2);
        } else if (!(obj2 instanceof Scriptable) || (obj2 instanceof Callable)) {
            return Undefined.instance;
        } else {
            if (obj2 instanceof NativeArray) {
                return ja((NativeArray) obj2, stringifyState);
            }
            return jo((Scriptable) obj2, stringifyState);
        }
    }

    public static Object stringify(Context context, Scriptable scriptable, Object obj, Object obj2, Object obj3) {
        LinkedList linkedList;
        Callable callable;
        String str;
        String str2;
        if (obj2 instanceof Callable) {
            callable = (Callable) obj2;
            linkedList = null;
        } else if (obj2 instanceof NativeArray) {
            LinkedList linkedList2 = new LinkedList();
            NativeArray nativeArray = (NativeArray) obj2;
            for (Integer intValue : nativeArray.getIndexIds()) {
                Object obj4 = nativeArray.get(intValue.intValue(), nativeArray);
                if ((obj4 instanceof String) || (obj4 instanceof Number)) {
                    linkedList2.add(obj4);
                } else if ((obj4 instanceof NativeString) || (obj4 instanceof NativeNumber)) {
                    linkedList2.add(ScriptRuntime.toString(obj4));
                }
            }
            linkedList = linkedList2;
            callable = null;
        } else {
            callable = null;
            linkedList = null;
        }
        if (obj3 instanceof NativeNumber) {
            obj3 = Double.valueOf(ScriptRuntime.toNumber(obj3));
        } else if (obj3 instanceof NativeString) {
            obj3 = ScriptRuntime.toString(obj3);
        }
        if (obj3 instanceof Number) {
            int min = Math.min(10, (int) ScriptRuntime.toInteger(obj3));
            if (min > 0) {
                str2 = repeat(' ', min);
            } else {
                str2 = "";
            }
        } else {
            if (obj3 instanceof String) {
                String str3 = (String) obj3;
                if (str3.length() > 10) {
                    str2 = str3.substring(0, 10);
                } else {
                    str = str3;
                }
            } else {
                str = "";
            }
            StringifyState stringifyState = new StringifyState(context, scriptable, "", str, callable, linkedList);
            NativeObject nativeObject = new NativeObject();
            nativeObject.setParentScope(scriptable);
            nativeObject.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
            nativeObject.defineProperty("", obj, 0);
            return str("", nativeObject, stringifyState);
        }
        str = str2;
        StringifyState stringifyState2 = new StringifyState(context, scriptable, "", str, callable, linkedList);
        NativeObject nativeObject2 = new NativeObject();
        nativeObject2.setParentScope(scriptable);
        nativeObject2.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeObject2.defineProperty("", obj, 0);
        return str("", nativeObject2, stringifyState2);
    }

    private static Object walk(Context context, Scriptable scriptable, Callable callable, Scriptable scriptable2, Object obj) {
        Object obj2;
        if (obj instanceof Number) {
            obj2 = scriptable2.get(((Number) obj).intValue(), scriptable2);
        } else {
            obj2 = scriptable2.get((String) obj, scriptable2);
        }
        if (obj2 instanceof Scriptable) {
            Scriptable scriptable3 = (Scriptable) obj2;
            if (scriptable3 instanceof NativeArray) {
                long length = ((NativeArray) scriptable3).getLength();
                for (long j = 0; j < length; j++) {
                    if (j > 2147483647L) {
                        String l = Long.toString(j);
                        Object walk = walk(context, scriptable, callable, scriptable3, l);
                        if (walk == Undefined.instance) {
                            scriptable3.delete(l);
                        } else {
                            scriptable3.put(l, scriptable3, walk);
                        }
                    } else {
                        int i = (int) j;
                        Object walk2 = walk(context, scriptable, callable, scriptable3, Integer.valueOf(i));
                        if (walk2 == Undefined.instance) {
                            scriptable3.delete(i);
                        } else {
                            scriptable3.put(i, scriptable3, walk2);
                        }
                    }
                }
            } else {
                for (Object obj3 : scriptable3.getIds()) {
                    Object walk3 = walk(context, scriptable, callable, scriptable3, obj3);
                    if (walk3 == Undefined.instance) {
                        if (obj3 instanceof Number) {
                            scriptable3.delete(((Number) obj3).intValue());
                        } else {
                            scriptable3.delete((String) obj3);
                        }
                    } else if (obj3 instanceof Number) {
                        scriptable3.put(((Number) obj3).intValue(), scriptable3, walk3);
                    } else {
                        scriptable3.put((String) obj3, scriptable3, walk3);
                    }
                }
            }
        }
        return callable.call(context, scriptable, scriptable2, new Object[]{obj, obj2});
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        if (!idFunctionObject.hasTag(JSON_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        if (methodId == 1) {
            return "JSON";
        }
        Callable callable = null;
        if (methodId == 2) {
            String scriptRuntime = ScriptRuntime.toString(objArr, 0);
            if (objArr.length > 1) {
                callable = objArr[1];
            }
            if (callable instanceof Callable) {
                return parse(context, scriptable, scriptRuntime, callable);
            }
            return parse(context, scriptable, scriptRuntime);
        } else if (methodId == 3) {
            int length = objArr.length;
            if (length != 1) {
                if (length != 2) {
                    if (length != 3) {
                        obj2 = null;
                        obj = null;
                        return stringify(context, scriptable, callable, obj2, obj);
                    }
                    callable = objArr[2];
                }
                Object obj4 = callable;
                callable = objArr[1];
                obj3 = obj4;
            } else {
                obj3 = null;
            }
            obj = obj3;
            obj2 = callable;
            callable = objArr[0];
            return stringify(context, scriptable, callable, obj2, obj);
        } else {
            throw new IllegalStateException(String.valueOf(methodId));
        }
    }

    public int findPrototypeId(String str) {
        int i;
        String str2;
        int length = str.length();
        if (length == 5) {
            str2 = "parse";
            i = 2;
        } else if (length == 8) {
            str2 = "toSource";
            i = 1;
        } else if (length != 9) {
            str2 = null;
            i = 0;
        } else {
            str2 = "stringify";
            i = 3;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }

    public String getClassName() {
        return "JSON";
    }

    public void initPrototypeId(int i) {
        String str;
        int i2 = 3;
        if (i <= 3) {
            if (i == 1) {
                i2 = 0;
                str = "toSource";
            } else if (i == 2) {
                str = "parse";
                i2 = 2;
            } else if (i == 3) {
                str = "stringify";
            } else {
                throw new IllegalStateException(String.valueOf(i));
            }
            initPrototypeMethod(JSON_TAG, i, str, i2);
            return;
        }
        throw new IllegalStateException(String.valueOf(i));
    }

    public static Object parse(Context context, Scriptable scriptable, String str, Callable callable) {
        Object parse = parse(context, scriptable, str);
        Scriptable newObject = context.newObject(scriptable);
        newObject.put("", newObject, parse);
        return walk(context, scriptable, callable, newObject, "");
    }
}
