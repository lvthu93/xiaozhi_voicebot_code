package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class a {
    public final int a;

    /* renamed from: com.google.android.exoplayer2.extractor.mp4.a$a  reason: collision with other inner class name */
    public static final class C0014a extends a {
        public final long b;
        public final ArrayList c = new ArrayList();
        public final ArrayList d = new ArrayList();

        public C0014a(int i, long j) {
            super(i);
            this.b = j;
        }

        public void add(b bVar) {
            this.c.add(bVar);
        }

        public int getChildAtomOfTypeCount(int i) {
            ArrayList arrayList = this.c;
            int size = arrayList.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                if (((b) arrayList.get(i3)).a == i) {
                    i2++;
                }
            }
            ArrayList arrayList2 = this.d;
            int size2 = arrayList2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                if (((C0014a) arrayList2.get(i4)).a == i) {
                    i2++;
                }
            }
            return i2;
        }

        @Nullable
        public C0014a getContainerAtomOfType(int i) {
            ArrayList arrayList = this.d;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0014a aVar = (C0014a) arrayList.get(i2);
                if (aVar.a == i) {
                    return aVar;
                }
            }
            return null;
        }

        @Nullable
        public b getLeafAtomOfType(int i) {
            ArrayList arrayList = this.c;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                b bVar = (b) arrayList.get(i2);
                if (bVar.a == i) {
                    return bVar;
                }
            }
            return null;
        }

        public String toString() {
            String atomTypeString = a.getAtomTypeString(this.a);
            String arrays = Arrays.toString(this.c.toArray());
            String arrays2 = Arrays.toString(this.d.toArray());
            StringBuilder l = y2.l(y2.c(arrays2, y2.c(arrays, y2.c(atomTypeString, 22))), atomTypeString, " leaves: ", arrays, " containers: ");
            l.append(arrays2);
            return l.toString();
        }

        public void add(C0014a aVar) {
            this.d.add(aVar);
        }
    }

    public static final class b extends a {
        public final ParsableByteArray b;

        public b(int i, ParsableByteArray parsableByteArray) {
            super(i);
            this.b = parsableByteArray;
        }
    }

    public a(int i) {
        this.a = i;
    }

    public static String getAtomTypeString(int i) {
        StringBuilder sb = new StringBuilder(4);
        sb.append((char) ((i >> 24) & 255));
        sb.append((char) ((i >> 16) & 255));
        sb.append((char) ((i >> 8) & 255));
        sb.append((char) (i & 255));
        return sb.toString();
    }

    public static int parseFullAtomFlags(int i) {
        return i & ViewCompat.MEASURED_SIZE_MASK;
    }

    public static int parseFullAtomVersion(int i) {
        return (i >> 24) & 255;
    }

    public String toString() {
        return getAtomTypeString(this.a);
    }
}
