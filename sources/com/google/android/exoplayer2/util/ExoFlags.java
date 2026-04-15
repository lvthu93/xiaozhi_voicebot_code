package com.google.android.exoplayer2.util;

import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;

public final class ExoFlags {
    public final SparseBooleanArray a;

    public ExoFlags(SparseBooleanArray sparseBooleanArray) {
        this.a = sparseBooleanArray;
    }

    public boolean contains(int i) {
        return this.a.get(i);
    }

    public boolean containsAny(int... iArr) {
        for (int contains : iArr) {
            if (contains(contains)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ExoFlags)) {
            return false;
        }
        return this.a.equals(((ExoFlags) obj).a);
    }

    public int get(int i) {
        Assertions.checkIndex(i, 0, size());
        return this.a.keyAt(i);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public int size() {
        return this.a.size();
    }

    public static final class Builder {
        public final SparseBooleanArray a = new SparseBooleanArray();
        public boolean b;

        public Builder add(int i) {
            Assertions.checkState(!this.b);
            this.a.append(i, true);
            return this;
        }

        public Builder addAll(int... iArr) {
            for (int add : iArr) {
                add(add);
            }
            return this;
        }

        public Builder addIf(int i, boolean z) {
            return z ? add(i) : this;
        }

        public ExoFlags build() {
            Assertions.checkState(!this.b);
            this.b = true;
            return new ExoFlags(this.a);
        }

        public Builder addAll(ExoFlags exoFlags) {
            for (int i = 0; i < exoFlags.size(); i++) {
                add(exoFlags.get(i));
            }
            return this;
        }
    }
}
