package org.mozilla.javascript;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class InterfaceAdapter {
    private final Object proxyHelper;

    private InterfaceAdapter(ContextFactory contextFactory, Class<?> cls) {
        this.proxyHelper = VMBridge.instance.getInterfaceProxyHelper(contextFactory, new Class[]{cls});
    }

    public static Object create(Context context, Class<?> cls, ScriptableObject scriptableObject) {
        InterfaceAdapter interfaceAdapter;
        if (cls.isInterface()) {
            Scriptable topCallScope = ScriptRuntime.getTopCallScope(context);
            ClassCache classCache = ClassCache.get(topCallScope);
            InterfaceAdapter interfaceAdapter2 = (InterfaceAdapter) classCache.getInterfaceAdapter(cls);
            ContextFactory factory = context.getFactory();
            if (interfaceAdapter2 == null) {
                Method[] methods = cls.getMethods();
                if (scriptableObject instanceof Callable) {
                    int length = methods.length;
                    if (length == 0) {
                        throw Context.reportRuntimeError1("msg.no.empty.interface.conversion", cls.getName());
                    } else if (length > 1) {
                        String str = null;
                        for (Method method : methods) {
                            if (isFunctionalMethodCandidate(method)) {
                                if (str == null) {
                                    str = method.getName();
                                } else if (!str.equals(method.getName())) {
                                    throw Context.reportRuntimeError1("msg.no.function.interface.conversion", cls.getName());
                                }
                            }
                        }
                    }
                }
                InterfaceAdapter interfaceAdapter3 = new InterfaceAdapter(factory, cls);
                classCache.cacheInterfaceAdapter(cls, interfaceAdapter3);
                interfaceAdapter = interfaceAdapter3;
            } else {
                interfaceAdapter = interfaceAdapter2;
            }
            return VMBridge.instance.newInterfaceProxy(interfaceAdapter.proxyHelper, factory, interfaceAdapter, scriptableObject, topCallScope);
        }
        throw new IllegalArgumentException();
    }

    private static boolean isFunctionalMethodCandidate(Method method) {
        if (method.getName().equals("equals") || method.getName().equals("hashCode") || method.getName().equals("toString")) {
            return false;
        }
        return Modifier.isAbstract(method.getModifiers());
    }

    public Object invoke(ContextFactory contextFactory, Object obj, Scriptable scriptable, Object obj2, Method method, Object[] objArr) {
        return contextFactory.call(new x3(this, obj, scriptable, obj2, method, objArr));
    }

    /* renamed from: invokeImpl */
    public Object lambda$invoke$0(Context context, Object obj, Scriptable scriptable, Object obj2, Method method, Object[] objArr) {
        Callable callable;
        if (obj instanceof Callable) {
            callable = (Callable) obj;
        } else {
            String name = method.getName();
            Object property = ScriptableObject.getProperty((Scriptable) obj, name);
            if (property == Scriptable.NOT_FOUND) {
                Context.reportWarning(ScriptRuntime.getMessage1("msg.undefined.function.interface", name));
                Class<?> returnType = method.getReturnType();
                if (returnType == Void.TYPE) {
                    return null;
                }
                return Context.jsToJava((Object) null, returnType);
            } else if (property instanceof Callable) {
                callable = (Callable) property;
            } else {
                throw Context.reportRuntimeError1("msg.not.function.interface", name);
            }
        }
        WrapFactory wrapFactory = context.getWrapFactory();
        if (objArr == null) {
            objArr = ScriptRuntime.emptyArgs;
        } else {
            int length = objArr.length;
            for (int i = 0; i != length; i++) {
                Object obj3 = objArr[i];
                if (!(obj3 instanceof String) && !(obj3 instanceof Number) && !(obj3 instanceof Boolean)) {
                    objArr[i] = wrapFactory.wrap(context, scriptable, obj3, (Class<?>) null);
                }
            }
        }
        Object call = callable.call(context, scriptable, wrapFactory.wrapAsJavaObject(context, scriptable, obj2, (Class<?>) null), objArr);
        Class<?> returnType2 = method.getReturnType();
        if (returnType2 == Void.TYPE) {
            return null;
        }
        return Context.jsToJava(call, returnType2);
    }
}
