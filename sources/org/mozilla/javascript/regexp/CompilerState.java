package org.mozilla.javascript.regexp;

import androidx.appcompat.widget.ActivityChooserView;
import org.mozilla.javascript.Context;

class CompilerState {
    int backReferenceLimit;
    int classCount;
    int cp = 0;
    char[] cpbegin;
    int cpend;
    Context cx;
    int flags;
    int maxBackReference;
    int parenCount;
    int parenNesting;
    int progLength;
    RENode result;

    public CompilerState(Context context, char[] cArr, int i, int i2) {
        this.cx = context;
        this.cpbegin = cArr;
        this.cpend = i;
        this.flags = i2;
        this.backReferenceLimit = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.maxBackReference = 0;
        this.parenCount = 0;
        this.classCount = 0;
        this.progLength = 0;
    }
}
