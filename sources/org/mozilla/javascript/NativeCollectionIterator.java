package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;
import org.mozilla.javascript.Hashtable;

public class NativeCollectionIterator extends ES6Iterator {
    private static final long serialVersionUID = 7094840979404373443L;
    private String className;
    private transient Iterator<Hashtable.Entry> iterator;
    private Type type;

    /* renamed from: org.mozilla.javascript.NativeCollectionIterator$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mozilla$javascript$NativeCollectionIterator$Type;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                org.mozilla.javascript.NativeCollectionIterator$Type[] r0 = org.mozilla.javascript.NativeCollectionIterator.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$mozilla$javascript$NativeCollectionIterator$Type = r0
                org.mozilla.javascript.NativeCollectionIterator$Type r1 = org.mozilla.javascript.NativeCollectionIterator.Type.KEYS     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$org$mozilla$javascript$NativeCollectionIterator$Type     // Catch:{ NoSuchFieldError -> 0x001d }
                org.mozilla.javascript.NativeCollectionIterator$Type r1 = org.mozilla.javascript.NativeCollectionIterator.Type.VALUES     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$org$mozilla$javascript$NativeCollectionIterator$Type     // Catch:{ NoSuchFieldError -> 0x0028 }
                org.mozilla.javascript.NativeCollectionIterator$Type r1 = org.mozilla.javascript.NativeCollectionIterator.Type.BOTH     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeCollectionIterator.AnonymousClass1.<clinit>():void");
        }
    }

    public enum Type {
        KEYS,
        VALUES,
        BOTH
    }

    public NativeCollectionIterator(String str) {
        this.iterator = Collections.emptyIterator();
        this.className = str;
        this.iterator = Collections.emptyIterator();
        this.type = Type.BOTH;
    }

    public static void init(ScriptableObject scriptableObject, String str, boolean z) {
        ES6Iterator.init(scriptableObject, z, new NativeCollectionIterator(str), str);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.className = (String) objectInputStream.readObject();
        this.type = (Type) objectInputStream.readObject();
        this.iterator = Collections.emptyIterator();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.className);
        objectOutputStream.writeObject(this.type);
    }

    public String getClassName() {
        return this.className;
    }

    public boolean isDone(Context context, Scriptable scriptable) {
        return !this.iterator.hasNext();
    }

    public Object nextValue(Context context, Scriptable scriptable) {
        Hashtable.Entry next = this.iterator.next();
        int i = AnonymousClass1.$SwitchMap$org$mozilla$javascript$NativeCollectionIterator$Type[this.type.ordinal()];
        if (i == 1) {
            return next.key;
        }
        if (i == 2) {
            return next.value;
        }
        if (i == 3) {
            return context.newArray(scriptable, new Object[]{next.key, next.value});
        }
        throw new AssertionError();
    }

    public NativeCollectionIterator(Scriptable scriptable, String str, Type type2, Iterator<Hashtable.Entry> it) {
        super(scriptable, str);
        Collections.emptyIterator();
        this.className = str;
        this.iterator = it;
        this.type = type2;
    }
}
