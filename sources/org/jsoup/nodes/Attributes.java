package org.jsoup.nodes;

import j$.util.Iterator;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Document;

public class Attributes implements Iterable<Attribute>, Cloneable {
    private static final String[] Empty = new String[0];
    private static final String EmptyString = "";
    private static final int GrowthFactor = 2;
    private static final int InitialCapacity = 4;
    static final int NotFound = -1;
    protected static final String dataPrefix = "data-";
    String[] keys;
    /* access modifiers changed from: private */
    public int size = 0;
    String[] vals;

    public static class Dataset extends AbstractMap<String, String> {
        /* access modifiers changed from: private */
        public final Attributes attributes;

        public class DatasetIterator implements Iterator<Map.Entry<String, String>>, j$.util.Iterator {
            private Attribute attr;
            private Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Dataset.this.attributes.iterator();
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    Attribute next = this.attrIter.next();
                    this.attr = next;
                    if (next.isDataAttribute()) {
                        return true;
                    }
                }
                return false;
            }

            public void remove() {
                Dataset.this.attributes.remove(this.attr.getKey());
            }

            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring(5), this.attr.getValue());
            }
        }

        public class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            public java.util.Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            public int size() {
                int i = 0;
                while (new DatasetIterator().hasNext()) {
                    i++;
                }
                return i;
            }
        }

        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        private Dataset(Attributes attributes2) {
            this.attributes = attributes2;
        }

        public String put(String str, String str2) {
            String access$400 = Attributes.dataKey(str);
            String str3 = this.attributes.hasKey(access$400) ? this.attributes.get(access$400) : null;
            this.attributes.put(access$400, str2);
            return str3;
        }
    }

    public Attributes() {
        String[] strArr = Empty;
        this.keys = strArr;
        this.vals = strArr;
    }

    private void add(String str, String str2) {
        checkCapacity(this.size + 1);
        String[] strArr = this.keys;
        int i = this.size;
        strArr[i] = str;
        this.vals[i] = str2;
        this.size = i + 1;
    }

    private void checkCapacity(int i) {
        boolean z;
        if (i >= this.size) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z);
        String[] strArr = this.keys;
        int length = strArr.length;
        if (length < i) {
            int i2 = 4;
            if (length >= 4) {
                i2 = this.size * 2;
            }
            if (i <= i2) {
                i = i2;
            }
            this.keys = copyOf(strArr, i);
            this.vals = copyOf(this.vals, i);
        }
    }

    public static String checkNotNull(String str) {
        return str == null ? "" : str;
    }

    private static String[] copyOf(String[] strArr, int i) {
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, Math.min(strArr.length, i));
        return strArr2;
    }

    /* access modifiers changed from: private */
    public static String dataKey(String str) {
        return y2.i(dataPrefix, str);
    }

    private int indexOfKeyIgnoreCase(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equalsIgnoreCase(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public void remove(int i) {
        Validate.isFalse(i >= this.size);
        int i2 = (this.size - i) - 1;
        if (i2 > 0) {
            String[] strArr = this.keys;
            int i3 = i + 1;
            System.arraycopy(strArr, i3, strArr, i, i2);
            String[] strArr2 = this.vals;
            System.arraycopy(strArr2, i3, strArr2, i, i2);
        }
        int i4 = this.size - 1;
        this.size = i4;
        this.keys[i4] = null;
        this.vals[i4] = null;
    }

    public void addAll(Attributes attributes) {
        if (attributes.size() != 0) {
            checkCapacity(this.size + attributes.size);
            java.util.Iterator<Attribute> it = attributes.iterator();
            while (it.hasNext()) {
                put(it.next());
            }
        }
    }

    public List<Attribute> asList() {
        Attribute attribute;
        ArrayList arrayList = new ArrayList(this.size);
        for (int i = 0; i < this.size; i++) {
            String str = this.vals[i];
            if (str == null) {
                attribute = new BooleanAttribute(this.keys[i]);
            } else {
                attribute = new Attribute(this.keys[i], str, this);
            }
            arrayList.add(attribute);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attributes attributes = (Attributes) obj;
        if (this.size == attributes.size && Arrays.equals(this.keys, attributes.keys)) {
            return Arrays.equals(this.vals, attributes.vals);
        }
        return false;
    }

    public String get(String str) {
        int indexOfKey = indexOfKey(str);
        if (indexOfKey == -1) {
            return "";
        }
        return checkNotNull(this.vals[indexOfKey]);
    }

    public String getIgnoreCase(String str) {
        int indexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (indexOfKeyIgnoreCase == -1) {
            return "";
        }
        return checkNotNull(this.vals[indexOfKeyIgnoreCase]);
    }

    public boolean hasKey(String str) {
        return indexOfKey(str) != -1;
    }

    public boolean hasKeyIgnoreCase(String str) {
        return indexOfKeyIgnoreCase(str) != -1;
    }

    public int hashCode() {
        return (((this.size * 31) + Arrays.hashCode(this.keys)) * 31) + Arrays.hashCode(this.vals);
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        try {
            html(sb, new Document("").outputSettings());
            return sb.toString();
        } catch (IOException e) {
            throw new SerializationException((Throwable) e);
        }
    }

    public int indexOfKey(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equals(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    public java.util.Iterator<Attribute> iterator() {
        return new Object() {
            int i = 0;

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                return this.i < Attributes.this.size;
            }

            public void remove() {
                Attributes attributes = Attributes.this;
                int i2 = this.i - 1;
                this.i = i2;
                attributes.remove(i2);
            }

            public Attribute next() {
                Attributes attributes = Attributes.this;
                String[] strArr = attributes.keys;
                int i2 = this.i;
                Attribute attribute = new Attribute(strArr[i2], attributes.vals[i2], attributes);
                this.i++;
                return attribute;
            }
        };
    }

    public void normalize() {
        for (int i = 0; i < this.size; i++) {
            String[] strArr = this.keys;
            strArr[i] = Normalizer.lowerCase(strArr[i]);
        }
    }

    public Attributes put(String str, String str2) {
        int indexOfKey = indexOfKey(str);
        if (indexOfKey != -1) {
            this.vals[indexOfKey] = str2;
        } else {
            add(str, str2);
        }
        return this;
    }

    public void putIgnoreCase(String str, String str2) {
        int indexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (indexOfKeyIgnoreCase != -1) {
            this.vals[indexOfKeyIgnoreCase] = str2;
            if (!this.keys[indexOfKeyIgnoreCase].equals(str)) {
                this.keys[indexOfKeyIgnoreCase] = str;
                return;
            }
            return;
        }
        add(str, str2);
    }

    public void removeIgnoreCase(String str) {
        int indexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (indexOfKeyIgnoreCase != -1) {
            remove(indexOfKeyIgnoreCase);
        }
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        return html();
    }

    public Attributes clone() {
        try {
            Attributes attributes = (Attributes) super.clone();
            attributes.size = this.size;
            this.keys = copyOf(this.keys, this.size);
            this.vals = copyOf(this.vals, this.size);
            return attributes;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Attributes put(String str, boolean z) {
        if (z) {
            putIgnoreCase(str, (String) null);
        } else {
            remove(str);
        }
        return this;
    }

    public final void html(Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            String str = this.keys[i2];
            String str2 = this.vals[i2];
            appendable.append(' ').append(str);
            if (!Attribute.shouldCollapseAttribute(str, str2, outputSettings)) {
                appendable.append("=\"");
                if (str2 == null) {
                    str2 = "";
                }
                Entities.escape(appendable, str2, outputSettings, true, false, false);
                appendable.append('\"');
            }
        }
    }

    public Attributes put(Attribute attribute) {
        Validate.notNull(attribute);
        put(attribute.getKey(), attribute.getValue());
        attribute.parent = this;
        return this;
    }

    public void remove(String str) {
        int indexOfKey = indexOfKey(str);
        if (indexOfKey != -1) {
            remove(indexOfKey);
        }
    }
}
