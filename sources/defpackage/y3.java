package defpackage;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* renamed from: y3  reason: default package */
public final class y3 {

    /* renamed from: y3$a */
    public static class a extends AbstractList<Integer> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        public final int[] c;
        public final int f;
        public final int g;

        public a(int[] iArr, int i, int i2) {
            this.c = iArr;
            this.f = i;
            this.g = i2;
        }

        public final boolean contains(Object obj) {
            if (obj instanceof Integer) {
                int intValue = ((Integer) obj).intValue();
                int i = this.f;
                while (true) {
                    if (i >= this.g) {
                        i = -1;
                        break;
                    } else if (this.c[i] == intValue) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i != -1) {
                    return true;
                }
            }
            return false;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return super.equals(obj);
            }
            a aVar = (a) obj;
            int i = this.g;
            int i2 = this.f;
            int i3 = i - i2;
            if (aVar.g - aVar.f != i3) {
                return false;
            }
            for (int i4 = 0; i4 < i3; i4++) {
                if (this.c[i2 + i4] != aVar.c[aVar.f + i4]) {
                    return false;
                }
            }
            return true;
        }

        public final Object get(int i) {
            int i2 = this.g;
            int i3 = this.f;
            Preconditions.checkElementIndex(i, i2 - i3);
            return Integer.valueOf(this.c[i3 + i]);
        }

        public final int hashCode() {
            int i = 1;
            for (int i2 = this.f; i2 < this.g; i2++) {
                i = (i * 31) + this.c[i2];
            }
            return i;
        }

        public final int indexOf(Object obj) {
            if (obj instanceof Integer) {
                int intValue = ((Integer) obj).intValue();
                int i = this.f;
                int i2 = i;
                while (true) {
                    if (i2 >= this.g) {
                        i2 = -1;
                        break;
                    } else if (this.c[i2] == intValue) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 >= 0) {
                    return i2 - i;
                }
            }
            return -1;
        }

        public final boolean isEmpty() {
            return false;
        }

        public final int lastIndexOf(Object obj) {
            int i;
            if (obj instanceof Integer) {
                int intValue = ((Integer) obj).intValue();
                int i2 = this.g - 1;
                while (true) {
                    i = this.f;
                    if (i2 < i) {
                        i2 = -1;
                        break;
                    } else if (this.c[i2] == intValue) {
                        break;
                    } else {
                        i2--;
                    }
                }
                if (i2 >= 0) {
                    return i2 - i;
                }
            }
            return -1;
        }

        public final Object set(int i, Object obj) {
            int i2 = this.g;
            int i3 = this.f;
            Preconditions.checkElementIndex(i, i2 - i3);
            int i4 = i3 + i;
            int[] iArr = this.c;
            int i5 = iArr[i4];
            iArr[i4] = ((Integer) Preconditions.checkNotNull((Integer) obj)).intValue();
            return Integer.valueOf(i5);
        }

        public final int size() {
            return this.g - this.f;
        }

        public final List<Integer> subList(int i, int i2) {
            int i3 = this.g;
            int i4 = this.f;
            Preconditions.checkPositionIndexes(i, i2, i3 - i4);
            if (i == i2) {
                return Collections.emptyList();
            }
            return new a(this.c, i + i4, i4 + i2);
        }

        public final String toString() {
            int i = this.g;
            int i2 = this.f;
            StringBuilder sb = new StringBuilder((i - i2) * 5);
            sb.append('[');
            int[] iArr = this.c;
            sb.append(iArr[i2]);
            while (true) {
                i2++;
                if (i2 < i) {
                    sb.append(", ");
                    sb.append(iArr[i2]);
                } else {
                    sb.append(']');
                    return sb.toString();
                }
            }
        }
    }

    public static int a(long j) {
        boolean z;
        int i = (int) j;
        if (((long) i) == j) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Out of range: %s", j);
        return i;
    }

    public static int b(long j) {
        if (j > 2147483647L) {
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        if (j < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j;
    }

    public static int[] c(List list) {
        if (list instanceof a) {
            a aVar = (a) list;
            return Arrays.copyOfRange(aVar.c, aVar.f, aVar.g);
        }
        Object[] array = list.toArray();
        int length = array.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = ((Number) Preconditions.checkNotNull(array[i])).intValue();
        }
        return iArr;
    }
}
