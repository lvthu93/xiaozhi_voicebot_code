package org.mozilla.javascript.jdk18;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.InterfaceAdapter;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.VMBridge;

public class VMBridge_jdk18 extends VMBridge {
    private static final ThreadLocal<Object[]> contextLocal = new ThreadLocal<>();

    public Context getContext(Object obj) {
        return (Context) ((Object[]) obj)[0];
    }

    public Object getInterfaceProxyHelper(ContextFactory contextFactory, Class<?>[] clsArr) {
        try {
            return Proxy.getProxyClass(clsArr[0].getClassLoader(), clsArr).getConstructor(new Class[]{InvocationHandler.class});
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    public Object getThreadContextHelper() {
        ThreadLocal<Object[]> threadLocal = contextLocal;
        Object[] objArr = threadLocal.get();
        if (objArr != null) {
            return objArr;
        }
        Object[] objArr2 = new Object[1];
        threadLocal.set(objArr2);
        return objArr2;
    }

    public Object newInterfaceProxy(Object obj, ContextFactory contextFactory, InterfaceAdapter interfaceAdapter, Object obj2, Scriptable scriptable) {
        final Object obj3 = obj2;
        final InterfaceAdapter interfaceAdapter2 = interfaceAdapter;
        final ContextFactory contextFactory2 = contextFactory;
        final Scriptable scriptable2 = scriptable;
        try {
            return ((Constructor) obj).newInstance(new Object[]{new InvocationHandler() {
                public Object invoke(Object obj, Method method, Object[] objArr) {
                    if (method.getDeclaringClass() == Object.class) {
                        String name = method.getName();
                        if (name.equals("equals")) {
                            boolean z = false;
                            if (obj == objArr[0]) {
                                z = true;
                            }
                            return Boolean.valueOf(z);
                        } else if (name.equals("hashCode")) {
                            return Integer.valueOf(obj3.hashCode());
                        } else {
                            if (name.equals("toString")) {
                                return "Proxy[" + obj3.toString() + "]";
                            }
                        }
                    }
                    return interfaceAdapter2.invoke(contextFactory2, obj3, scriptable2, obj, method, objArr);
                }
            }});
        } catch (InvocationTargetException e) {
            throw Context.throwAsScriptRuntimeEx(e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException(e2);
        } catch (InstantiationException e3) {
            throw new IllegalStateException(e3);
        }
    }

    public void setContext(Object obj, Context context) {
        ((Object[]) obj)[0] = context;
    }

    public boolean tryToMakeAccessible(AccessibleObject accessibleObject) {
        if (accessibleObject.isAccessible()) {
            return true;
        }
        try {
            accessibleObject.setAccessible(true);
        } catch (Exception unused) {
        }
        return accessibleObject.isAccessible();
    }
}
