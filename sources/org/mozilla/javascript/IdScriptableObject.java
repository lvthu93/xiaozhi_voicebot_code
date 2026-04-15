package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class IdScriptableObject extends ScriptableObject implements IdFunctionCall {
    private static final long serialVersionUID = -3744239272168621609L;
    private transient PrototypeValues prototypeValues;

    public static final class PrototypeValues implements Serializable {
        private static final int NAME_SLOT = 1;
        private static final int SLOT_SPAN = 2;
        private static final long serialVersionUID = 3038645279153854371L;
        private short[] attributeArray;
        private IdFunctionObject constructor;
        private short constructorAttrs;
        int constructorId;
        private int maxId;
        private IdScriptableObject obj;
        private Object[] valueArray;

        public PrototypeValues(IdScriptableObject idScriptableObject, int i) {
            if (idScriptableObject == null) {
                throw new IllegalArgumentException();
            } else if (i >= 1) {
                this.obj = idScriptableObject;
                this.maxId = i;
            } else {
                throw new IllegalArgumentException();
            }
        }

        private Object ensureId(int i) {
            Object[] objArr = this.valueArray;
            if (objArr == null) {
                synchronized (this) {
                    objArr = this.valueArray;
                    if (objArr == null) {
                        int i2 = this.maxId;
                        Object[] objArr2 = new Object[(i2 * 2)];
                        this.valueArray = objArr2;
                        this.attributeArray = new short[i2];
                        objArr = objArr2;
                    }
                }
            }
            int i3 = (i - 1) * 2;
            Object obj2 = objArr[i3];
            if (obj2 == null) {
                int i4 = this.constructorId;
                if (i == i4) {
                    initSlot(i4, "constructor", this.constructor, this.constructorAttrs);
                    this.constructor = null;
                } else {
                    this.obj.initPrototypeId(i);
                }
                obj2 = objArr[i3];
                if (obj2 == null) {
                    throw new IllegalStateException(this.obj.getClass().getName() + ".initPrototypeId(int id) did not initialize id=" + i);
                }
            }
            return obj2;
        }

        private void initSlot(int i, Object obj2, Object obj3, int i2) {
            Object[] objArr = this.valueArray;
            if (objArr != null) {
                if (obj3 == null) {
                    obj3 = UniqueTag.NULL_VALUE;
                }
                int i3 = i - 1;
                int i4 = i3 * 2;
                synchronized (this) {
                    if (objArr[i4] == null) {
                        objArr[i4] = obj3;
                        objArr[i4 + 1] = obj2;
                        this.attributeArray[i3] = (short) i2;
                    } else if (!obj2.equals(objArr[i4 + 1])) {
                        throw new IllegalStateException();
                    }
                }
                return;
            }
            throw new IllegalStateException();
        }

        public final IdFunctionObject createPrecachedConstructor() {
            if (this.constructorId == 0) {
                int findPrototypeId = this.obj.findPrototypeId("constructor");
                this.constructorId = findPrototypeId;
                if (findPrototypeId != 0) {
                    this.obj.initPrototypeId(findPrototypeId);
                    IdFunctionObject idFunctionObject = this.constructor;
                    if (idFunctionObject != null) {
                        idFunctionObject.initFunction(this.obj.getClassName(), ScriptableObject.getTopLevelScope(this.obj));
                        this.constructor.markAsConstructor(this.obj);
                        return this.constructor;
                    }
                    throw new IllegalStateException(this.obj.getClass().getName() + ".initPrototypeId() did not initialize id=" + this.constructorId);
                }
                throw new IllegalStateException("No id for constructor property");
            }
            throw new IllegalStateException();
        }

        public final void delete(int i) {
            ensureId(i);
            int i2 = i - 1;
            if ((this.attributeArray[i2] & 4) == 0) {
                int i3 = i2 * 2;
                synchronized (this) {
                    this.valueArray[i3] = Scriptable.NOT_FOUND;
                    this.attributeArray[i2] = 0;
                }
            } else if (Context.getContext().isStrictMode()) {
                throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", (String) this.valueArray[(i2 * 2) + 1]);
            }
        }

        public final int findId(String str) {
            return this.obj.findPrototypeId(str);
        }

        public final Object get(int i) {
            Object ensureId = ensureId(i);
            if (ensureId == UniqueTag.NULL_VALUE) {
                return null;
            }
            return ensureId;
        }

        public final int getAttributes(int i) {
            ensureId(i);
            return this.attributeArray[i - 1];
        }

        public final int getMaxId() {
            return this.maxId;
        }

        public final Object[] getNames(boolean z, boolean z2, Object[] objArr) {
            int i;
            Object[] objArr2 = null;
            int i2 = 0;
            for (int i3 = 1; i3 <= this.maxId; i3++) {
                Object ensureId = ensureId(i3);
                if ((z || (this.attributeArray[i3 - 1] & 2) == 0) && ensureId != Scriptable.NOT_FOUND) {
                    Object obj2 = this.valueArray[((i3 - 1) * 2) + 1];
                    if (obj2 instanceof String) {
                        if (objArr2 == null) {
                            objArr2 = new Object[this.maxId];
                        }
                        i = i2 + 1;
                        objArr2[i2] = obj2;
                    } else if (z2 && (obj2 instanceof Symbol)) {
                        if (objArr2 == null) {
                            objArr2 = new Object[this.maxId];
                        }
                        i = i2 + 1;
                        objArr2[i2] = obj2.toString();
                    }
                    i2 = i;
                }
            }
            if (i2 == 0) {
                return objArr;
            }
            if (objArr != null && objArr.length != 0) {
                int length = objArr.length;
                Object[] objArr3 = new Object[(length + i2)];
                System.arraycopy(objArr, 0, objArr3, 0, length);
                System.arraycopy(objArr2, 0, objArr3, length, i2);
                return objArr3;
            } else if (i2 == objArr2.length) {
                return objArr2;
            } else {
                Object[] objArr4 = new Object[i2];
                System.arraycopy(objArr2, 0, objArr4, 0, i2);
                return objArr4;
            }
        }

        public final boolean has(int i) {
            Object obj2;
            Object[] objArr = this.valueArray;
            if (objArr == null || (obj2 = objArr[(i - 1) * 2]) == null || obj2 != Scriptable.NOT_FOUND) {
                return true;
            }
            return false;
        }

        public final void initValue(int i, String str, Object obj2, int i2) {
            if (1 > i || i > this.maxId) {
                throw new IllegalArgumentException();
            } else if (str == null) {
                throw new IllegalArgumentException();
            } else if (obj2 != Scriptable.NOT_FOUND) {
                ScriptableObject.checkValidAttributes(i2);
                if (this.obj.findPrototypeId(str) != i) {
                    throw new IllegalArgumentException(str);
                } else if (i != this.constructorId) {
                    initSlot(i, str, obj2, i2);
                } else if (obj2 instanceof IdFunctionObject) {
                    this.constructor = (IdFunctionObject) obj2;
                    this.constructorAttrs = (short) i2;
                } else {
                    throw new IllegalArgumentException("consructor should be initialized with IdFunctionObject");
                }
            } else {
                throw new IllegalArgumentException();
            }
        }

        public final void set(int i, Scriptable scriptable, Object obj2) {
            if (obj2 != Scriptable.NOT_FOUND) {
                ensureId(i);
                int i2 = i - 1;
                if ((this.attributeArray[i2] & 1) != 0) {
                    return;
                }
                if (scriptable == this.obj) {
                    if (obj2 == null) {
                        obj2 = UniqueTag.NULL_VALUE;
                    }
                    int i3 = i2 * 2;
                    synchronized (this) {
                        this.valueArray[i3] = obj2;
                    }
                    return;
                }
                Object obj3 = this.valueArray[(i2 * 2) + 1];
                if (!(obj3 instanceof Symbol)) {
                    scriptable.put((String) obj3, scriptable, obj2);
                } else if (scriptable instanceof SymbolScriptable) {
                    ((SymbolScriptable) scriptable).put((Symbol) obj3, scriptable, obj2);
                }
            } else {
                throw new IllegalArgumentException();
            }
        }

        public final void setAttributes(int i, int i2) {
            ScriptableObject.checkValidAttributes(i2);
            ensureId(i);
            synchronized (this) {
                this.attributeArray[i - 1] = (short) i2;
            }
        }

        public final int findId(Symbol symbol) {
            return this.obj.findPrototypeId(symbol);
        }

        public final void initValue(int i, Symbol symbol, Object obj2, int i2) {
            if (1 > i || i > this.maxId) {
                throw new IllegalArgumentException();
            } else if (symbol == null) {
                throw new IllegalArgumentException();
            } else if (obj2 != Scriptable.NOT_FOUND) {
                ScriptableObject.checkValidAttributes(i2);
                if (this.obj.findPrototypeId(symbol) != i) {
                    throw new IllegalArgumentException(symbol.toString());
                } else if (i != this.constructorId) {
                    initSlot(i, symbol, obj2, i2);
                } else if (obj2 instanceof IdFunctionObject) {
                    this.constructor = (IdFunctionObject) obj2;
                    this.constructorAttrs = (short) i2;
                } else {
                    throw new IllegalArgumentException("consructor should be initialized with IdFunctionObject");
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public IdScriptableObject() {
    }

    private ScriptableObject getBuiltInDescriptor(String str) {
        int findId;
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        int findInstanceIdInfo = findInstanceIdInfo(str);
        if (findInstanceIdInfo != 0) {
            return ScriptableObject.buildDataDescriptor(parentScope, getInstanceIdValue(65535 & findInstanceIdInfo), findInstanceIdInfo >>> 16);
        }
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0) {
            return null;
        }
        return ScriptableObject.buildDataDescriptor(parentScope, this.prototypeValues.get(findId), this.prototypeValues.getAttributes(findId));
    }

    public static EcmaError incompatibleCallError(IdFunctionObject idFunctionObject) {
        throw ScriptRuntime.typeError1("msg.incompat.call", idFunctionObject.getFunctionName());
    }

    public static int instanceIdInfo(int i, int i2) {
        return (i << 16) | i2;
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.ScriptableObject] */
    /* JADX WARNING: type inference failed for: r1v2, types: [org.mozilla.javascript.IdFunctionObjectES6] */
    /* JADX WARNING: type inference failed for: r2v2, types: [org.mozilla.javascript.IdFunctionObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.mozilla.javascript.IdFunctionObject newIdFunction(java.lang.Object r10, int r11, java.lang.String r12, int r13, org.mozilla.javascript.Scriptable r14) {
        /*
            r9 = this;
            org.mozilla.javascript.Context r0 = org.mozilla.javascript.Context.getContext()
            int r0 = r0.getLanguageVersion()
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 >= r1) goto L_0x0019
            org.mozilla.javascript.IdFunctionObject r0 = new org.mozilla.javascript.IdFunctionObject
            r2 = r0
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r8 = r14
            r2.<init>(r3, r4, r5, r6, r7, r8)
            goto L_0x0025
        L_0x0019:
            org.mozilla.javascript.IdFunctionObjectES6 r0 = new org.mozilla.javascript.IdFunctionObjectES6
            r1 = r0
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            r7 = r14
            r1.<init>(r2, r3, r4, r5, r6, r7)
        L_0x0025:
            boolean r10 = r9.isSealed()
            if (r10 == 0) goto L_0x002e
            r0.sealObject()
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.IdScriptableObject.newIdFunction(java.lang.Object, int, java.lang.String, int, org.mozilla.javascript.Scriptable):org.mozilla.javascript.IdFunctionObject");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        if (readInt != 0) {
            activatePrototypeMap(readInt);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int i;
        objectOutputStream.defaultWriteObject();
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 != null) {
            i = prototypeValues2.getMaxId();
        } else {
            i = 0;
        }
        objectOutputStream.writeInt(i);
    }

    public final void activatePrototypeMap(int i) {
        PrototypeValues prototypeValues2 = new PrototypeValues(this, i);
        synchronized (this) {
            if (this.prototypeValues == null) {
                this.prototypeValues = prototypeValues2;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void addIdFunctionProperty(Scriptable scriptable, Object obj, int i, String str, int i2) {
        newIdFunction(obj, i, str, i2, ScriptableObject.getTopLevelScope(scriptable)).addAsProperty(scriptable);
    }

    public final Object defaultGet(String str) {
        return super.get(str, (Scriptable) this);
    }

    public final boolean defaultHas(String str) {
        return super.has(str, (Scriptable) this);
    }

    public final void defaultPut(String str, Object obj) {
        super.put(str, (Scriptable) this, obj);
    }

    public void defineOwnProperty(Context context, Object obj, ScriptableObject scriptableObject) {
        int findId;
        if (obj instanceof String) {
            String str = (String) obj;
            int findInstanceIdInfo = findInstanceIdInfo(str);
            if (findInstanceIdInfo != 0) {
                int i = 65535 & findInstanceIdInfo;
                if (isAccessorDescriptor(scriptableObject)) {
                    delete(i);
                } else {
                    checkPropertyDefinition(scriptableObject);
                    checkPropertyChange(str, getOwnPropertyDescriptor(context, obj), scriptableObject);
                    int i2 = findInstanceIdInfo >>> 16;
                    Object property = ScriptableObject.getProperty((Scriptable) scriptableObject, ES6Iterator.VALUE_PROPERTY);
                    if (property != Scriptable.NOT_FOUND && (i2 & 1) == 0 && !sameValue(property, getInstanceIdValue(i))) {
                        setInstanceIdValue(i, property);
                    }
                    setAttributes(str, applyDescriptorToAttributeBitset(i2, scriptableObject));
                    return;
                }
            }
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (!(prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0)) {
                if (isAccessorDescriptor(scriptableObject)) {
                    this.prototypeValues.delete(findId);
                } else {
                    checkPropertyDefinition(scriptableObject);
                    checkPropertyChange(str, getOwnPropertyDescriptor(context, obj), scriptableObject);
                    int attributes = this.prototypeValues.getAttributes(findId);
                    Object property2 = ScriptableObject.getProperty((Scriptable) scriptableObject, ES6Iterator.VALUE_PROPERTY);
                    if (property2 != Scriptable.NOT_FOUND && (attributes & 1) == 0 && !sameValue(property2, this.prototypeValues.get(findId))) {
                        this.prototypeValues.set(findId, this, property2);
                    }
                    this.prototypeValues.setAttributes(findId, applyDescriptorToAttributeBitset(attributes, scriptableObject));
                    if (super.has(str, (Scriptable) this)) {
                        super.delete(str);
                        return;
                    }
                    return;
                }
            }
        }
        super.defineOwnProperty(context, obj, scriptableObject);
    }

    public void delete(String str) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(str);
        if (findInstanceIdInfo == 0 || isSealed()) {
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0) {
                super.delete(str);
            } else if (!isSealed()) {
                this.prototypeValues.delete(findId);
            }
        } else if (((findInstanceIdInfo >>> 16) & 4) == 0) {
            setInstanceIdValue(65535 & findInstanceIdInfo, Scriptable.NOT_FOUND);
        } else if (Context.getContext().isStrictMode()) {
            throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", str);
        }
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        throw idFunctionObject.unknown();
    }

    public final IdFunctionObject exportAsJSClass(int i, Scriptable scriptable, boolean z) {
        if (!(scriptable == this || scriptable == null)) {
            setParentScope(scriptable);
            setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        }
        activatePrototypeMap(i);
        IdFunctionObject createPrecachedConstructor = this.prototypeValues.createPrecachedConstructor();
        if (z) {
            sealObject();
        }
        fillConstructorProperties(createPrecachedConstructor);
        if (z) {
            createPrecachedConstructor.sealObject();
        }
        createPrecachedConstructor.exportAsScopeProperty();
        return createPrecachedConstructor;
    }

    public void fillConstructorProperties(IdFunctionObject idFunctionObject) {
    }

    public int findInstanceIdInfo(String str) {
        return 0;
    }

    public int findInstanceIdInfo(Symbol symbol) {
        return 0;
    }

    public int findPrototypeId(String str) {
        throw new IllegalStateException(str);
    }

    public int findPrototypeId(Symbol symbol) {
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r3 = r2.prototypeValues.get((r3 = r4.findId(r3)));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object get(java.lang.String r3, org.mozilla.javascript.Scriptable r4) {
        /*
            r2 = this;
            java.lang.Object r4 = super.get((java.lang.String) r3, (org.mozilla.javascript.Scriptable) r4)
            java.lang.Object r0 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r4 == r0) goto L_0x0009
            return r4
        L_0x0009:
            int r4 = r2.findInstanceIdInfo((java.lang.String) r3)
            if (r4 == 0) goto L_0x001a
            r1 = 65535(0xffff, float:9.1834E-41)
            r4 = r4 & r1
            java.lang.Object r4 = r2.getInstanceIdValue(r4)
            if (r4 == r0) goto L_0x001a
            return r4
        L_0x001a:
            org.mozilla.javascript.IdScriptableObject$PrototypeValues r4 = r2.prototypeValues
            if (r4 == 0) goto L_0x002d
            int r3 = r4.findId((java.lang.String) r3)
            if (r3 == 0) goto L_0x002d
            org.mozilla.javascript.IdScriptableObject$PrototypeValues r4 = r2.prototypeValues
            java.lang.Object r3 = r4.get(r3)
            if (r3 == r0) goto L_0x002d
            return r3
        L_0x002d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.IdScriptableObject.get(java.lang.String, org.mozilla.javascript.Scriptable):java.lang.Object");
    }

    public int getAttributes(String str) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(str);
        if (findInstanceIdInfo != 0) {
            return findInstanceIdInfo >>> 16;
        }
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0) {
            return super.getAttributes(str);
        }
        return this.prototypeValues.getAttributes(findId);
    }

    public Object[] getIds(boolean z, boolean z2) {
        Object[] ids = super.getIds(z, z2);
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 != null) {
            ids = prototypeValues2.getNames(z, z2, ids);
        }
        int maxInstanceId = getMaxInstanceId();
        if (maxInstanceId == 0) {
            return ids;
        }
        Object[] objArr = null;
        int i = 0;
        while (maxInstanceId != 0) {
            String instanceIdName = getInstanceIdName(maxInstanceId);
            int findInstanceIdInfo = findInstanceIdInfo(instanceIdName);
            if (findInstanceIdInfo != 0) {
                int i2 = findInstanceIdInfo >>> 16;
                if (!((i2 & 4) == 0 && Scriptable.NOT_FOUND == getInstanceIdValue(maxInstanceId)) && (z || (i2 & 2) == 0)) {
                    if (i == 0) {
                        objArr = new Object[maxInstanceId];
                    }
                    objArr[i] = instanceIdName;
                    i++;
                }
            }
            maxInstanceId--;
        }
        if (i == 0) {
            return ids;
        }
        if (ids.length == 0 && objArr.length == i) {
            return objArr;
        }
        Object[] objArr2 = new Object[(ids.length + i)];
        System.arraycopy(ids, 0, objArr2, 0, ids.length);
        System.arraycopy(objArr, 0, objArr2, ids.length, i);
        return objArr2;
    }

    public String getInstanceIdName(int i) {
        throw new IllegalArgumentException(String.valueOf(i));
    }

    public Object getInstanceIdValue(int i) {
        throw new IllegalStateException(String.valueOf(i));
    }

    public int getMaxInstanceId() {
        return 0;
    }

    public ScriptableObject getOwnPropertyDescriptor(Context context, Object obj) {
        ScriptableObject ownPropertyDescriptor = super.getOwnPropertyDescriptor(context, obj);
        if (ownPropertyDescriptor != null) {
            return ownPropertyDescriptor;
        }
        if (obj instanceof String) {
            return getBuiltInDescriptor((String) obj);
        }
        if (ScriptRuntime.isSymbol(obj)) {
            return getBuiltInDescriptor((Symbol) ((NativeSymbol) obj).getKey());
        }
        return ownPropertyDescriptor;
    }

    public boolean has(String str, Scriptable scriptable) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(str);
        if (findInstanceIdInfo == 0) {
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0) {
                return super.has(str, scriptable);
            }
            return this.prototypeValues.has(findId);
        } else if (((findInstanceIdInfo >>> 16) & 4) != 0) {
            return true;
        } else {
            if (Scriptable.NOT_FOUND != getInstanceIdValue(65535 & findInstanceIdInfo)) {
                return true;
            }
            return false;
        }
    }

    public final boolean hasPrototypeMap() {
        return this.prototypeValues != null;
    }

    public final void initPrototypeConstructor(IdFunctionObject idFunctionObject) {
        int i = this.prototypeValues.constructorId;
        if (i == 0) {
            throw new IllegalStateException();
        } else if (idFunctionObject.methodId() == i) {
            if (isSealed()) {
                idFunctionObject.sealObject();
            }
            this.prototypeValues.initValue(i, "constructor", (Object) idFunctionObject, 2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void initPrototypeId(int i) {
        throw new IllegalStateException(String.valueOf(i));
    }

    public final IdFunctionObject initPrototypeMethod(Object obj, int i, String str, int i2) {
        return initPrototypeMethod(obj, i, str, str, i2);
    }

    public final void initPrototypeValue(int i, String str, Object obj, int i2) {
        this.prototypeValues.initValue(i, str, obj, i2);
    }

    public void put(String str, Scriptable scriptable, Object obj) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(str);
        if (findInstanceIdInfo == 0) {
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0) {
                super.put(str, scriptable, obj);
            } else if (scriptable != this || !isSealed()) {
                this.prototypeValues.set(findId, scriptable, obj);
            } else {
                throw Context.reportRuntimeError1("msg.modify.sealed", str);
            }
        } else if (scriptable == this && isSealed()) {
            throw Context.reportRuntimeError1("msg.modify.sealed", str);
        } else if (((findInstanceIdInfo >>> 16) & 1) != 0) {
        } else {
            if (scriptable == this) {
                setInstanceIdValue(65535 & findInstanceIdInfo, obj);
            } else {
                scriptable.put(str, scriptable, obj);
            }
        }
    }

    public void setAttributes(String str, int i) {
        int findId;
        ScriptableObject.checkValidAttributes(i);
        int findInstanceIdInfo = findInstanceIdInfo(str);
        if (findInstanceIdInfo != 0) {
            int i2 = 65535 & findInstanceIdInfo;
            if (i != (findInstanceIdInfo >>> 16)) {
                setInstanceIdAttributes(i2, i);
                return;
            }
            return;
        }
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 == null || (findId = prototypeValues2.findId(str)) == 0) {
            super.setAttributes(str, i);
        } else {
            this.prototypeValues.setAttributes(findId, i);
        }
    }

    public void setInstanceIdAttributes(int i, int i2) {
        throw ScriptRuntime.constructError("InternalError", "Changing attributes not supported for " + getClassName() + " " + getInstanceIdName(i) + " property");
    }

    public void setInstanceIdValue(int i, Object obj) {
        throw new IllegalStateException(String.valueOf(i));
    }

    public IdScriptableObject(Scriptable scriptable, Scriptable scriptable2) {
        super(scriptable, scriptable2);
    }

    public final IdFunctionObject initPrototypeMethod(Object obj, int i, String str, String str2, int i2) {
        IdFunctionObject newIdFunction = newIdFunction(obj, i, str2 != null ? str2 : str, i2, ScriptableObject.getTopLevelScope(this));
        this.prototypeValues.initValue(i, str, (Object) newIdFunction, 2);
        return newIdFunction;
    }

    public final void initPrototypeValue(int i, Symbol symbol, Object obj, int i2) {
        this.prototypeValues.initValue(i, symbol, obj, i2);
    }

    public final IdFunctionObject initPrototypeMethod(Object obj, int i, Symbol symbol, String str, int i2) {
        IdFunctionObject newIdFunction = newIdFunction(obj, i, str, i2, ScriptableObject.getTopLevelScope(this));
        this.prototypeValues.initValue(i, symbol, (Object) newIdFunction, 2);
        return newIdFunction;
    }

    public int getAttributes(Symbol symbol) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(symbol);
        if (findInstanceIdInfo != 0) {
            return findInstanceIdInfo >>> 16;
        }
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 == null || (findId = prototypeValues2.findId(symbol)) == 0) {
            return super.getAttributes(symbol);
        }
        return this.prototypeValues.getAttributes(findId);
    }

    public boolean has(Symbol symbol, Scriptable scriptable) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(symbol);
        if (findInstanceIdInfo == 0) {
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (prototypeValues2 == null || (findId = prototypeValues2.findId(symbol)) == 0) {
                return super.has(symbol, scriptable);
            }
            return this.prototypeValues.has(findId);
        } else if (((findInstanceIdInfo >>> 16) & 4) != 0) {
            return true;
        } else {
            if (Scriptable.NOT_FOUND != getInstanceIdValue(65535 & findInstanceIdInfo)) {
                return true;
            }
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r3 = r2.prototypeValues.get((r3 = r4.findId(r3)));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object get(org.mozilla.javascript.Symbol r3, org.mozilla.javascript.Scriptable r4) {
        /*
            r2 = this;
            java.lang.Object r4 = super.get((org.mozilla.javascript.Symbol) r3, (org.mozilla.javascript.Scriptable) r4)
            java.lang.Object r0 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r4 == r0) goto L_0x0009
            return r4
        L_0x0009:
            int r4 = r2.findInstanceIdInfo((org.mozilla.javascript.Symbol) r3)
            if (r4 == 0) goto L_0x001a
            r1 = 65535(0xffff, float:9.1834E-41)
            r4 = r4 & r1
            java.lang.Object r4 = r2.getInstanceIdValue(r4)
            if (r4 == r0) goto L_0x001a
            return r4
        L_0x001a:
            org.mozilla.javascript.IdScriptableObject$PrototypeValues r4 = r2.prototypeValues
            if (r4 == 0) goto L_0x002d
            int r3 = r4.findId((org.mozilla.javascript.Symbol) r3)
            if (r3 == 0) goto L_0x002d
            org.mozilla.javascript.IdScriptableObject$PrototypeValues r4 = r2.prototypeValues
            java.lang.Object r3 = r4.get(r3)
            if (r3 == r0) goto L_0x002d
            return r3
        L_0x002d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.IdScriptableObject.get(org.mozilla.javascript.Symbol, org.mozilla.javascript.Scriptable):java.lang.Object");
    }

    private ScriptableObject getBuiltInDescriptor(Symbol symbol) {
        int findId;
        Scriptable parentScope = getParentScope();
        if (parentScope == null) {
            parentScope = this;
        }
        PrototypeValues prototypeValues2 = this.prototypeValues;
        if (prototypeValues2 == null || (findId = prototypeValues2.findId(symbol)) == 0) {
            return null;
        }
        return ScriptableObject.buildDataDescriptor(parentScope, this.prototypeValues.get(findId), this.prototypeValues.getAttributes(findId));
    }

    public void delete(Symbol symbol) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(symbol);
        if (findInstanceIdInfo == 0 || isSealed()) {
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (prototypeValues2 == null || (findId = prototypeValues2.findId(symbol)) == 0) {
                super.delete(symbol);
            } else if (!isSealed()) {
                this.prototypeValues.delete(findId);
            }
        } else if (((findInstanceIdInfo >>> 16) & 4) == 0) {
            setInstanceIdValue(65535 & findInstanceIdInfo, Scriptable.NOT_FOUND);
        } else if (Context.getContext().isStrictMode()) {
            throw ScriptRuntime.typeError0("msg.delete.property.with.configurable.false");
        }
    }

    public void put(Symbol symbol, Scriptable scriptable, Object obj) {
        int findId;
        int findInstanceIdInfo = findInstanceIdInfo(symbol);
        if (findInstanceIdInfo == 0) {
            PrototypeValues prototypeValues2 = this.prototypeValues;
            if (prototypeValues2 == null || (findId = prototypeValues2.findId(symbol)) == 0) {
                super.put(symbol, scriptable, obj);
            } else if (scriptable != this || !isSealed()) {
                this.prototypeValues.set(findId, scriptable, obj);
            } else {
                throw Context.reportRuntimeError0("msg.modify.sealed");
            }
        } else if (scriptable == this && isSealed()) {
            throw Context.reportRuntimeError0("msg.modify.sealed");
        } else if (((findInstanceIdInfo >>> 16) & 1) != 0) {
        } else {
            if (scriptable == this) {
                setInstanceIdValue(65535 & findInstanceIdInfo, obj);
            } else {
                ScriptableObject.ensureSymbolScriptable(scriptable).put(symbol, scriptable, obj);
            }
        }
    }
}
