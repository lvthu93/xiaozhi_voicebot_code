package org.mozilla.javascript;

public final class NativeArrayIterator extends ES6Iterator {
    private static final String ITERATOR_TAG = "ArrayIterator";
    private static final long serialVersionUID = 1;
    private Scriptable arrayLike;
    private int index;
    private ARRAY_ITERATOR_TYPE type;

    public enum ARRAY_ITERATOR_TYPE {
        ENTRIES,
        KEYS,
        VALUES
    }

    private NativeArrayIterator() {
    }

    public static void init(ScriptableObject scriptableObject, boolean z) {
        ES6Iterator.init(scriptableObject, z, new NativeArrayIterator(), ITERATOR_TAG);
    }

    public String getClassName() {
        return "Array Iterator";
    }

    public String getTag() {
        return ITERATOR_TAG;
    }

    public boolean isDone(Context context, Scriptable scriptable) {
        return ((long) this.index) >= NativeArray.getLengthProperty(context, this.arrayLike, false);
    }

    public Object nextValue(Context context, Scriptable scriptable) {
        if (this.type == ARRAY_ITERATOR_TYPE.KEYS) {
            int i = this.index;
            this.index = i + 1;
            return Integer.valueOf(i);
        }
        Scriptable scriptable2 = this.arrayLike;
        Object obj = scriptable2.get(this.index, scriptable2);
        if (obj == Scriptable.NOT_FOUND) {
            obj = Undefined.instance;
        }
        if (this.type == ARRAY_ITERATOR_TYPE.ENTRIES) {
            obj = context.newArray(scriptable, new Object[]{Integer.valueOf(this.index), obj});
        }
        this.index++;
        return obj;
    }

    public NativeArrayIterator(Scriptable scriptable, Scriptable scriptable2, ARRAY_ITERATOR_TYPE array_iterator_type) {
        super(scriptable, ITERATOR_TAG);
        this.index = 0;
        this.arrayLike = scriptable2;
        this.type = array_iterator_type;
    }
}
