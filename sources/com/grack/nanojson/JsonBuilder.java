package com.grack.nanojson;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

public final class JsonBuilder<T> implements JsonSink<JsonBuilder<T>> {
    private Stack<Object> json;
    private T root;

    public JsonBuilder(T t) {
        Stack<Object> stack = new Stack<>();
        this.json = stack;
        this.root = t;
        stack.push(t);
    }

    private JsonArray arr() {
        try {
            return (JsonArray) this.json.peek();
        } catch (ClassCastException unused) {
            throw new JsonWriterException("Attempted to write a non-keyed value to a JsonObject");
        }
    }

    private JsonObject obj() {
        try {
            return (JsonObject) this.json.peek();
        } catch (ClassCastException unused) {
            throw new JsonWriterException("Attempted to write a keyed value to a JsonArray");
        }
    }

    public JsonBuilder<T> array() {
        JsonArray jsonArray = new JsonArray();
        value((Object) jsonArray);
        this.json.push(jsonArray);
        return this;
    }

    public JsonBuilder<T> array(String str) {
        JsonArray jsonArray = new JsonArray();
        value(str, (Object) jsonArray);
        this.json.push(jsonArray);
        return this;
    }

    public JsonBuilder<T> array(String str, Collection<?> collection) {
        return value(str, (Object) collection);
    }

    public JsonBuilder<T> array(Collection<?> collection) {
        return value((Object) collection);
    }

    public T done() {
        return this.root;
    }

    public JsonBuilder<T> end() {
        if (this.json.size() != 1) {
            this.json.pop();
            return this;
        }
        throw new JsonWriterException("Cannot end the root object or array");
    }

    public JsonBuilder<T> nul() {
        return value((Object) null);
    }

    public JsonBuilder<T> nul(String str) {
        return value(str, (Object) null);
    }

    public JsonBuilder<T> object() {
        JsonObject jsonObject = new JsonObject();
        value((Object) jsonObject);
        this.json.push(jsonObject);
        return this;
    }

    public JsonBuilder<T> object(String str) {
        JsonObject jsonObject = new JsonObject();
        value(str, (Object) jsonObject);
        this.json.push(jsonObject);
        return this;
    }

    public JsonBuilder<T> object(String str, Map<?, ?> map) {
        return value(str, (Object) map);
    }

    public JsonBuilder<T> object(Map<?, ?> map) {
        return value((Object) map);
    }

    public JsonBuilder<T> value(double d) {
        return value((Object) Double.valueOf(d));
    }

    public JsonBuilder<T> value(float f) {
        return value((Object) Float.valueOf(f));
    }

    public JsonBuilder<T> value(int i) {
        return value((Object) Integer.valueOf(i));
    }

    public JsonBuilder<T> value(long j) {
        return value((Object) Long.valueOf(j));
    }

    public JsonBuilder<T> value(Number number) {
        return value((Object) number);
    }

    public JsonBuilder<T> value(Object obj) {
        arr().add(obj);
        return this;
    }

    public JsonBuilder<T> value(String str) {
        return value((Object) str);
    }

    public JsonBuilder<T> value(String str, double d) {
        return value(str, (Object) Double.valueOf(d));
    }

    public JsonBuilder<T> value(String str, float f) {
        return value(str, (Object) Float.valueOf(f));
    }

    public JsonBuilder<T> value(String str, int i) {
        return value(str, (Object) Integer.valueOf(i));
    }

    public JsonBuilder<T> value(String str, long j) {
        return value(str, (Object) Long.valueOf(j));
    }

    public JsonBuilder<T> value(String str, Number number) {
        return value(str, (Object) number);
    }

    public JsonBuilder<T> value(String str, Object obj) {
        obj().put(str, obj);
        return this;
    }

    public JsonBuilder<T> value(String str, String str2) {
        return value(str, (Object) str2);
    }

    public JsonBuilder<T> value(String str, boolean z) {
        return value(str, (Object) Boolean.valueOf(z));
    }

    public JsonBuilder<T> value(boolean z) {
        return value((Object) Boolean.valueOf(z));
    }
}
