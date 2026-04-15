package org.mozilla.javascript;

import java.io.Closeable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorLikeIterable implements Iterable<Object>, Closeable {
    private boolean closed;
    /* access modifiers changed from: private */
    public final Context cx;
    /* access modifiers changed from: private */
    public final Scriptable iterator;
    /* access modifiers changed from: private */
    public final Callable next;
    private final Callable returnFunc;
    /* access modifiers changed from: private */
    public final Scriptable scope;

    public final class Itr implements Iterator<Object> {
        private boolean isDone;
        private Object nextVal;

        public Itr() {
        }

        public boolean hasNext() {
            Object call = IteratorLikeIterable.this.next.call(IteratorLikeIterable.this.cx, IteratorLikeIterable.this.scope, IteratorLikeIterable.this.iterator, ScriptRuntime.emptyArgs);
            Object property = ScriptableObject.getProperty(ScriptableObject.ensureScriptable(call), ES6Iterator.DONE_PROPERTY);
            if (property == Scriptable.NOT_FOUND) {
                property = Undefined.instance;
            }
            if (ScriptRuntime.toBoolean(property)) {
                this.isDone = true;
                return false;
            }
            this.nextVal = ScriptRuntime.getObjectPropNoWarn(call, ES6Iterator.VALUE_PROPERTY, IteratorLikeIterable.this.cx, IteratorLikeIterable.this.scope);
            return true;
        }

        public Object next() {
            if (!this.isDone) {
                return this.nextVal;
            }
            throw new NoSuchElementException();
        }
    }

    public IteratorLikeIterable(Context context, Scriptable scriptable, Object obj) {
        this.cx = context;
        this.scope = scriptable;
        this.next = ScriptRuntime.getPropFunctionAndThis(obj, ES6Iterator.NEXT_METHOD, context, scriptable);
        this.iterator = ScriptRuntime.lastStoredScriptable(context);
        Object objectPropNoWarn = ScriptRuntime.getObjectPropNoWarn(obj, "return", context, scriptable);
        if (objectPropNoWarn == null || Undefined.isUndefined(objectPropNoWarn)) {
            this.returnFunc = null;
        } else if (objectPropNoWarn instanceof Callable) {
            this.returnFunc = (Callable) objectPropNoWarn;
        } else {
            throw ScriptRuntime.notFunctionError(obj, objectPropNoWarn, "return");
        }
    }

    public void close() {
        if (!this.closed) {
            this.closed = true;
            Callable callable = this.returnFunc;
            if (callable != null) {
                callable.call(this.cx, this.scope, this.iterator, ScriptRuntime.emptyArgs);
            }
        }
    }

    public Itr iterator() {
        return new Itr();
    }
}
