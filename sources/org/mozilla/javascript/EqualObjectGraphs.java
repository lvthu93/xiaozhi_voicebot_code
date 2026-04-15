package org.mozilla.javascript;

import j$.util.Objects;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import org.mozilla.javascript.debug.DebuggableObject;

final class EqualObjectGraphs {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ThreadLocal<EqualObjectGraphs> instance = new ThreadLocal<>();
    private final Map<Object, Object> currentlyCompared = new IdentityHashMap();
    private final Map<Object, Object> knownEquals = new IdentityHashMap();

    private boolean equalGraphsNoMemo(Object obj, Object obj2) {
        if (obj instanceof Wrapper) {
            if (!(obj2 instanceof Wrapper) || !equalGraphs(((Wrapper) obj).unwrap(), ((Wrapper) obj2).unwrap())) {
                return false;
            }
            return true;
        } else if (obj instanceof Scriptable) {
            if (!(obj2 instanceof Scriptable) || !equalScriptables((Scriptable) obj, (Scriptable) obj2)) {
                return false;
            }
            return true;
        } else if (obj instanceof ConsString) {
            return ((ConsString) obj).toString().equals(obj2);
        } else {
            if (obj2 instanceof ConsString) {
                return obj.equals(((ConsString) obj2).toString());
            }
            if (obj instanceof SymbolKey) {
                if (!(obj2 instanceof SymbolKey) || !equalGraphs(((SymbolKey) obj).getName(), ((SymbolKey) obj2).getName())) {
                    return false;
                }
                return true;
            } else if (obj instanceof Object[]) {
                if (!(obj2 instanceof Object[]) || !equalObjectArrays((Object[]) obj, (Object[]) obj2)) {
                    return false;
                }
                return true;
            } else if (obj.getClass().isArray()) {
                return Objects.deepEquals(obj, obj2);
            } else {
                if (obj instanceof List) {
                    if (!(obj2 instanceof List) || !equalLists((List) obj, (List) obj2)) {
                        return false;
                    }
                    return true;
                } else if (obj instanceof Map) {
                    if (!(obj2 instanceof Map) || !equalMaps((Map) obj, (Map) obj2)) {
                        return false;
                    }
                    return true;
                } else if (obj instanceof Set) {
                    if (!(obj2 instanceof Set) || !equalSets((Set) obj, (Set) obj2)) {
                        return false;
                    }
                    return true;
                } else if (obj instanceof NativeGlobal) {
                    return obj2 instanceof NativeGlobal;
                } else {
                    if (obj instanceof JavaAdapter) {
                        return obj2 instanceof JavaAdapter;
                    }
                    if (obj instanceof NativeJavaTopPackage) {
                        return obj2 instanceof NativeJavaTopPackage;
                    }
                    return obj.equals(obj2);
                }
            }
        }
    }

    private static boolean equalInterpretedFunctions(InterpretedFunction interpretedFunction, InterpretedFunction interpretedFunction2) {
        return Objects.equals(interpretedFunction.getEncodedSource(), interpretedFunction2.getEncodedSource());
    }

    private boolean equalLists(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        Iterator<?> it = list.iterator();
        Iterator<?> it2 = list2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            if (!equalGraphs(it.next(), it2.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean equalMaps(java.util.Map<?, ?> r6, java.util.Map<?, ?> r7) {
        /*
            r5 = this;
            int r0 = r6.size()
            int r1 = r7.size()
            r2 = 0
            if (r0 == r1) goto L_0x000c
            return r2
        L_0x000c:
            java.util.Iterator r6 = sortedEntries(r6)
            java.util.Iterator r7 = sortedEntries(r7)
        L_0x0014:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x0049
            boolean r0 = r7.hasNext()
            if (r0 == 0) goto L_0x0049
            java.lang.Object r0 = r6.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r7.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r3 = r0.getKey()
            java.lang.Object r4 = r1.getKey()
            boolean r3 = r5.equalGraphs(r3, r4)
            if (r3 == 0) goto L_0x0048
            java.lang.Object r0 = r0.getValue()
            java.lang.Object r1 = r1.getValue()
            boolean r0 = r5.equalGraphs(r0, r1)
            if (r0 != 0) goto L_0x0014
        L_0x0048:
            return r2
        L_0x0049:
            r6 = 1
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.EqualObjectGraphs.equalMaps(java.util.Map, java.util.Map):boolean");
    }

    private boolean equalObjectArrays(Object[] objArr, Object[] objArr2) {
        if (objArr.length != objArr2.length) {
            return false;
        }
        for (int i = 0; i < objArr.length; i++) {
            if (!equalGraphs(objArr[i], objArr2[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean equalScriptables(Scriptable scriptable, Scriptable scriptable2) {
        Object[] sortedIds = getSortedIds(scriptable);
        Object[] sortedIds2 = getSortedIds(scriptable2);
        if (!equalObjectArrays(sortedIds, sortedIds2)) {
            return false;
        }
        int length = sortedIds.length;
        for (int i = 0; i < length; i++) {
            if (!equalGraphs(getValue(scriptable, sortedIds[i]), getValue(scriptable2, sortedIds2[i]))) {
                return false;
            }
        }
        if (!equalGraphs(scriptable.getPrototype(), scriptable2.getPrototype()) || !equalGraphs(scriptable.getParentScope(), scriptable2.getParentScope())) {
            return false;
        }
        if (scriptable instanceof NativeContinuation) {
            if (!(scriptable2 instanceof NativeContinuation) || !NativeContinuation.equalImplementations((NativeContinuation) scriptable, (NativeContinuation) scriptable2)) {
                return false;
            }
            return true;
        } else if (scriptable instanceof NativeJavaPackage) {
            return scriptable.equals(scriptable2);
        } else {
            if (scriptable instanceof IdFunctionObject) {
                if (!(scriptable2 instanceof IdFunctionObject) || !IdFunctionObject.equalObjectGraphs((IdFunctionObject) scriptable, (IdFunctionObject) scriptable2, this)) {
                    return false;
                }
                return true;
            } else if (scriptable instanceof InterpretedFunction) {
                if (!(scriptable2 instanceof InterpretedFunction) || !equalInterpretedFunctions((InterpretedFunction) scriptable, (InterpretedFunction) scriptable2)) {
                    return false;
                }
                return true;
            } else if (scriptable instanceof ArrowFunction) {
                if (!(scriptable2 instanceof ArrowFunction) || !ArrowFunction.equalObjectGraphs((ArrowFunction) scriptable, (ArrowFunction) scriptable2, this)) {
                    return false;
                }
                return true;
            } else if (scriptable instanceof BoundFunction) {
                if (!(scriptable2 instanceof BoundFunction) || !BoundFunction.equalObjectGraphs((BoundFunction) scriptable, (BoundFunction) scriptable2, this)) {
                    return false;
                }
                return true;
            } else if (!(scriptable instanceof NativeSymbol)) {
                return true;
            } else {
                if (!(scriptable2 instanceof NativeSymbol) || !equalGraphs(((NativeSymbol) scriptable).getKey(), ((NativeSymbol) scriptable2).getKey())) {
                    return false;
                }
                return true;
            }
        }
    }

    private boolean equalSets(Set<?> set, Set<?> set2) {
        return equalObjectArrays(sortedSet(set), sortedSet(set2));
    }

    private static Object[] getIds(Scriptable scriptable) {
        if (scriptable instanceof ScriptableObject) {
            return ((ScriptableObject) scriptable).getIds(true, true);
        }
        if (scriptable instanceof DebuggableObject) {
            return ((DebuggableObject) scriptable).getAllIds();
        }
        return scriptable.getIds();
    }

    private static Object[] getSortedIds(Scriptable scriptable) {
        Object[] ids = getIds(scriptable);
        Arrays.sort(ids, new a());
        return ids;
    }

    private static String getSymbolName(Symbol symbol) {
        if (symbol instanceof SymbolKey) {
            return ((SymbolKey) symbol).getName();
        }
        if (symbol instanceof NativeSymbol) {
            return ((NativeSymbol) symbol).getKey().getName();
        }
        throw new ClassCastException();
    }

    private static Object getValue(Scriptable scriptable, Object obj) {
        if (obj instanceof Symbol) {
            return ScriptableObject.getProperty(scriptable, (Symbol) obj);
        }
        if (obj instanceof Integer) {
            return ScriptableObject.getProperty(scriptable, ((Integer) obj).intValue());
        }
        if (obj instanceof String) {
            return ScriptableObject.getProperty(scriptable, (String) obj);
        }
        throw new ClassCastException();
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ int lambda$getSortedIds$0(Object obj, Object obj2) {
        if (obj instanceof Integer) {
            if (obj2 instanceof Integer) {
                return ((Integer) obj).compareTo((Integer) obj2);
            }
            if ((obj2 instanceof String) || (obj2 instanceof Symbol)) {
                return -1;
            }
        } else if (obj instanceof String) {
            if (obj2 instanceof String) {
                return ((String) obj).compareTo((String) obj2);
            }
            if (obj2 instanceof Integer) {
                return 1;
            }
            if (obj2 instanceof Symbol) {
                return -1;
            }
        } else if (obj instanceof Symbol) {
            if (obj2 instanceof Symbol) {
                return getSymbolName((Symbol) obj).compareTo(getSymbolName((Symbol) obj2));
            }
            if ((obj2 instanceof Integer) || (obj2 instanceof String)) {
                return 1;
            }
        }
        throw new ClassCastException();
    }

    private static Iterator<Map.Entry> sortedEntries(Map map) {
        if (!(map instanceof SortedMap)) {
            map = new TreeMap(map);
        }
        return map.entrySet().iterator();
    }

    private static Object[] sortedSet(Set<?> set) {
        Object[] array = set.toArray();
        Arrays.sort(array);
        return array;
    }

    public static <T> T withThreadLocal(Function<EqualObjectGraphs, T> function) {
        ThreadLocal<EqualObjectGraphs> threadLocal = instance;
        EqualObjectGraphs equalObjectGraphs = threadLocal.get();
        if (equalObjectGraphs != null) {
            return function.apply(equalObjectGraphs);
        }
        EqualObjectGraphs equalObjectGraphs2 = new EqualObjectGraphs();
        threadLocal.set(equalObjectGraphs2);
        try {
            T apply = function.apply(equalObjectGraphs2);
            threadLocal.set((Object) null);
            return apply;
        } catch (Throwable th) {
            instance.set((Object) null);
            throw th;
        }
    }

    public boolean equalGraphs(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        Object obj3 = this.currentlyCompared.get(obj);
        if (obj3 == obj2) {
            return true;
        }
        if (obj3 != null) {
            return false;
        }
        Object obj4 = this.knownEquals.get(obj);
        if (obj4 == obj2) {
            return true;
        }
        if (obj4 != null || this.knownEquals.get(obj2) != null) {
            return false;
        }
        this.currentlyCompared.put(obj, obj2);
        boolean equalGraphsNoMemo = equalGraphsNoMemo(obj, obj2);
        if (equalGraphsNoMemo) {
            this.knownEquals.put(obj, obj2);
            this.knownEquals.put(obj2, obj);
        }
        this.currentlyCompared.remove(obj);
        return equalGraphsNoMemo;
    }
}
