package defpackage;

import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.span.HorizontalTextInVerticalContextSpan;
import com.google.android.exoplayer2.text.span.RubySpan;
import com.google.android.exoplayer2.text.span.SpanUtil;
import com.google.android.exoplayer2.text.span.TextEmphasisSpan;
import com.google.android.exoplayer2.text.ttml.TextEmphasis;
import com.google.android.exoplayer2.text.ttml.TtmlStyle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;
import java.util.Map;

/* renamed from: wc  reason: default package */
public final class wc {
    public static void applyStylesToSpan(Spannable spannable, int i, int i2, TtmlStyle ttmlStyle, @Nullable uc ucVar, Map<String, TtmlStyle> map, int i3) {
        uc ucVar2;
        int i4;
        int i5 = -1;
        if (ttmlStyle.getStyle() != -1) {
            spannable.setSpan(new StyleSpan(ttmlStyle.getStyle()), i, i2, 33);
        }
        if (ttmlStyle.isLinethrough()) {
            spannable.setSpan(new StrikethroughSpan(), i, i2, 33);
        }
        if (ttmlStyle.isUnderline()) {
            spannable.setSpan(new UnderlineSpan(), i, i2, 33);
        }
        if (ttmlStyle.hasFontColor()) {
            SpanUtil.addOrReplaceSpan(spannable, new ForegroundColorSpan(ttmlStyle.getFontColor()), i, i2, 33);
        }
        if (ttmlStyle.hasBackgroundColor()) {
            SpanUtil.addOrReplaceSpan(spannable, new BackgroundColorSpan(ttmlStyle.getBackgroundColor()), i, i2, 33);
        }
        if (ttmlStyle.getFontFamily() != null) {
            SpanUtil.addOrReplaceSpan(spannable, new TypefaceSpan(ttmlStyle.getFontFamily()), i, i2, 33);
        }
        if (ttmlStyle.getTextEmphasis() != null) {
            TextEmphasis textEmphasis = (TextEmphasis) Assertions.checkNotNull(ttmlStyle.getTextEmphasis());
            int i6 = textEmphasis.a;
            if (i6 == -1) {
                if (i3 == 2 || i3 == 1) {
                    i6 = 3;
                } else {
                    i6 = 1;
                }
                i4 = 1;
            } else {
                i4 = textEmphasis.b;
            }
            int i7 = textEmphasis.c;
            if (i7 == -2) {
                i7 = 1;
            }
            SpanUtil.addOrReplaceSpan(spannable, new TextEmphasisSpan(i6, i4, i7), i, i2, 33);
        }
        int rubyType = ttmlStyle.getRubyType();
        if (rubyType == 2) {
            while (true) {
                ucVar2 = null;
                if (ucVar == null) {
                    ucVar = null;
                    break;
                }
                TtmlStyle resolveStyle = resolveStyle(ucVar.f, ucVar.getStyleIds(), map);
                if (resolveStyle != null && resolveStyle.getRubyType() == 1) {
                    break;
                }
                ucVar = ucVar.j;
            }
            if (ucVar != null) {
                ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.push(ucVar);
                while (true) {
                    if (arrayDeque.isEmpty()) {
                        break;
                    }
                    uc ucVar3 = (uc) arrayDeque.pop();
                    TtmlStyle resolveStyle2 = resolveStyle(ucVar3.f, ucVar3.getStyleIds(), map);
                    if (resolveStyle2 != null && resolveStyle2.getRubyType() == 3) {
                        ucVar2 = ucVar3;
                        break;
                    }
                    for (int childCount = ucVar3.getChildCount() - 1; childCount >= 0; childCount--) {
                        arrayDeque.push(ucVar3.getChild(childCount));
                    }
                }
                if (ucVar2 != null) {
                    if (ucVar2.getChildCount() != 1 || ucVar2.getChild(0).b == null) {
                        Log.i("TtmlRenderUtil", "Skipping rubyText node without exactly one text child.");
                    } else {
                        String str = (String) Util.castNonNull(ucVar2.getChild(0).b);
                        TtmlStyle ttmlStyle2 = ucVar.f;
                        if (ttmlStyle2 != null) {
                            i5 = ttmlStyle2.getRubyPosition();
                        }
                        spannable.setSpan(new RubySpan(str, i5), i, i2, 33);
                    }
                }
            }
        } else if (rubyType == 3 || rubyType == 4) {
            spannable.setSpan(new g1(), i, i2, 33);
        }
        if (ttmlStyle.getTextCombine()) {
            SpanUtil.addOrReplaceSpan(spannable, new HorizontalTextInVerticalContextSpan(), i, i2, 33);
        }
        int fontSizeUnit = ttmlStyle.getFontSizeUnit();
        if (fontSizeUnit == 1) {
            SpanUtil.addOrReplaceSpan(spannable, new AbsoluteSizeSpan((int) ttmlStyle.getFontSize(), true), i, i2, 33);
        } else if (fontSizeUnit == 2) {
            SpanUtil.addOrReplaceSpan(spannable, new RelativeSizeSpan(ttmlStyle.getFontSize()), i, i2, 33);
        } else if (fontSizeUnit == 3) {
            SpanUtil.addOrReplaceSpan(spannable, new RelativeSizeSpan(ttmlStyle.getFontSize() / 100.0f), i, i2, 33);
        }
    }

    @Nullable
    public static TtmlStyle resolveStyle(@Nullable TtmlStyle ttmlStyle, @Nullable String[] strArr, Map<String, TtmlStyle> map) {
        int i = 0;
        if (ttmlStyle == null) {
            if (strArr == null) {
                return null;
            }
            if (strArr.length == 1) {
                return map.get(strArr[0]);
            }
            if (strArr.length > 1) {
                TtmlStyle ttmlStyle2 = new TtmlStyle();
                int length = strArr.length;
                while (i < length) {
                    ttmlStyle2.chain(map.get(strArr[i]));
                    i++;
                }
                return ttmlStyle2;
            }
        } else if (strArr != null && strArr.length == 1) {
            return ttmlStyle.chain(map.get(strArr[0]));
        } else {
            if (strArr != null && strArr.length > 1) {
                int length2 = strArr.length;
                while (i < length2) {
                    ttmlStyle.chain(map.get(strArr[i]));
                    i++;
                }
            }
        }
        return ttmlStyle;
    }
}
