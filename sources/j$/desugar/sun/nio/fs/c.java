package j$.desugar.sun.nio.fs;

import j$.util.Objects;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class c {
    public static /* synthetic */ List a(Object[] objArr) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object requireNonNull : objArr) {
            arrayList.add(Objects.requireNonNull(requireNonNull));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static FileChannel b(FileChannel fileChannel) {
        return a.k(fileChannel);
    }
}
