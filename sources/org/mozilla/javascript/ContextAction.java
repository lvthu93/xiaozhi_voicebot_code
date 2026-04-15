package org.mozilla.javascript;

public interface ContextAction<T> {
    T run(Context context);
}
