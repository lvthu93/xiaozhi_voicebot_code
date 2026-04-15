package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.AutoSizeableTextView;
import java.lang.ref.WeakReference;

class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;
    private final TextView mView;

    public static class ApplyTextViewCallback extends ResourcesCompat.FontCallback {
        private final int mFontWeight;
        private final WeakReference<AppCompatTextHelper> mParent;
        private final int mStyle;

        public class TypefaceApplyCallback implements Runnable {
            private final WeakReference<AppCompatTextHelper> mParent;
            private final Typeface mTypeface;

            public TypefaceApplyCallback(@NonNull WeakReference<AppCompatTextHelper> weakReference, @NonNull Typeface typeface) {
                this.mParent = weakReference;
                this.mTypeface = typeface;
            }

            public void run() {
                AppCompatTextHelper appCompatTextHelper = this.mParent.get();
                if (appCompatTextHelper != null) {
                    appCompatTextHelper.setTypefaceByCallback(this.mTypeface);
                }
            }
        }

        public ApplyTextViewCallback(@NonNull AppCompatTextHelper appCompatTextHelper, int i, int i2) {
            this.mParent = new WeakReference<>(appCompatTextHelper);
            this.mFontWeight = i;
            this.mStyle = i2;
        }

        public void onFontRetrievalFailed(int i) {
        }

        public void onFontRetrieved(@NonNull Typeface typeface) {
            int i;
            boolean z;
            AppCompatTextHelper appCompatTextHelper = this.mParent.get();
            if (appCompatTextHelper != null) {
                if (Build.VERSION.SDK_INT >= 28 && (i = this.mFontWeight) != -1) {
                    if ((this.mStyle & 2) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    typeface = Typeface.create(typeface, i, z);
                }
                appCompatTextHelper.runOnUiThread(new TypefaceApplyCallback(this.mParent, typeface));
            }
        }
    }

    public AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(textView);
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
        }
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (drawable5 != null || drawable6 != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            TextView textView = this.mView;
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            Drawable[] compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
            Drawable drawable7 = compoundDrawablesRelative2[0];
            if (drawable7 == null && compoundDrawablesRelative2[2] == null) {
                Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
                TextView textView2 = this.mView;
                if (drawable == null) {
                    drawable = compoundDrawables[0];
                }
                if (drawable2 == null) {
                    drawable2 = compoundDrawables[1];
                }
                if (drawable3 == null) {
                    drawable3 = compoundDrawables[2];
                }
                if (drawable4 == null) {
                    drawable4 = compoundDrawables[3];
                }
                textView2.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
                return;
            }
            TextView textView3 = this.mView;
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative2[1];
            }
            Drawable drawable8 = compoundDrawablesRelative2[2];
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative2[3];
            }
            textView3.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, drawable8, drawable4);
        }
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setTextSizeInternal(int i, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i, f);
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        boolean z;
        boolean z2;
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        int i = Build.VERSION.SDK_INT;
        boolean z3 = false;
        if (i >= 28) {
            int i2 = tintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            this.mFontWeight = i2;
            if (i2 != -1) {
                this.mStyle = (this.mStyle & 2) | 0;
            }
        }
        int i3 = R.styleable.TextAppearance_android_fontFamily;
        if (tintTypedArray.hasValue(i3) || tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int i4 = R.styleable.TextAppearance_fontFamily;
            if (tintTypedArray.hasValue(i4)) {
                i3 = i4;
            }
            int i5 = this.mFontWeight;
            int i6 = this.mStyle;
            if (!context.isRestricted()) {
                try {
                    Typeface font = tintTypedArray.getFont(i3, this.mStyle, new ApplyTextViewCallback(this, i5, i6));
                    if (font != null) {
                        if (i < 28 || this.mFontWeight == -1) {
                            this.mFontTypeface = font;
                        } else {
                            Typeface create = Typeface.create(font, 0);
                            int i7 = this.mFontWeight;
                            if ((this.mStyle & 2) != 0) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            this.mFontTypeface = Typeface.create(create, i7, z2);
                        }
                    }
                    if (this.mFontTypeface == null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    this.mAsyncFontPending = z;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.mFontTypeface == null && (string = tintTypedArray.getString(i3)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                    this.mFontTypeface = Typeface.create(string, this.mStyle);
                    return;
                }
                Typeface create2 = Typeface.create(string, 0);
                int i8 = this.mFontWeight;
                if ((this.mStyle & 2) != 0) {
                    z3 = true;
                }
                this.mFontTypeface = Typeface.create(create2, i8, z3);
                return;
            }
            return;
        }
        int i9 = R.styleable.TextAppearance_android_typeface;
        if (tintTypedArray.hasValue(i9)) {
            this.mAsyncFontPending = false;
            int i10 = tintTypedArray.getInt(i9, 1);
            if (i10 == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
            } else if (i10 == 2) {
                this.mFontTypeface = Typeface.SERIF;
            } else if (i10 == 3) {
                this.mFontTypeface = Typeface.MONOSPACE;
            }
        }
    }

    public void applyCompoundDrawablesTints() {
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    @Nullable
    public ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    @Nullable
    public PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x01ce  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x01d5  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0254  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x025f  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0265  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0283  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x028c  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x029b  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x02a1  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x02b5  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x02d6  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x02ee  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x02f5  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02fc  */
    /* JADX WARNING: Removed duplicated region for block: B:157:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0161  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01a3  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01aa  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01ba  */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadFromAttributes(android.util.AttributeSet r24, int r25) {
        /*
            r23 = this;
            r7 = r23
            r0 = r24
            r1 = r25
            android.widget.TextView r2 = r7.mView
            android.content.Context r2 = r2.getContext()
            androidx.appcompat.widget.AppCompatDrawableManager r3 = androidx.appcompat.widget.AppCompatDrawableManager.get()
            int[] r4 = androidx.appcompat.R.styleable.AppCompatTextHelper
            r5 = 0
            androidx.appcompat.widget.TintTypedArray r4 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r4, r1, r5)
            int r6 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_textAppearance
            r8 = -1
            int r6 = r4.getResourceId(r6, r8)
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableLeft
            boolean r10 = r4.hasValue(r9)
            if (r10 == 0) goto L_0x0030
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableLeftTint = r9
        L_0x0030:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableTop
            boolean r10 = r4.hasValue(r9)
            if (r10 == 0) goto L_0x0042
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableTopTint = r9
        L_0x0042:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableRight
            boolean r10 = r4.hasValue(r9)
            if (r10 == 0) goto L_0x0054
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableRightTint = r9
        L_0x0054:
            int r9 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableBottom
            boolean r10 = r4.hasValue(r9)
            if (r10 == 0) goto L_0x0066
            int r9 = r4.getResourceId(r9, r5)
            androidx.appcompat.widget.TintInfo r9 = createTintInfo(r2, r3, r9)
            r7.mDrawableBottomTint = r9
        L_0x0066:
            int r9 = android.os.Build.VERSION.SDK_INT
            int r10 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableStart
            boolean r11 = r4.hasValue(r10)
            if (r11 == 0) goto L_0x007a
            int r10 = r4.getResourceId(r10, r5)
            androidx.appcompat.widget.TintInfo r10 = createTintInfo(r2, r3, r10)
            r7.mDrawableStartTint = r10
        L_0x007a:
            int r10 = androidx.appcompat.R.styleable.AppCompatTextHelper_android_drawableEnd
            boolean r11 = r4.hasValue(r10)
            if (r11 == 0) goto L_0x008c
            int r10 = r4.getResourceId(r10, r5)
            androidx.appcompat.widget.TintInfo r10 = createTintInfo(r2, r3, r10)
            r7.mDrawableEndTint = r10
        L_0x008c:
            r4.recycle()
            android.widget.TextView r4 = r7.mView
            android.text.method.TransformationMethod r4 = r4.getTransformationMethod()
            boolean r4 = r4 instanceof android.text.method.PasswordTransformationMethod
            r11 = 23
            r12 = 26
            if (r6 == r8) goto L_0x010a
            int[] r14 = androidx.appcompat.R.styleable.TextAppearance
            androidx.appcompat.widget.TintTypedArray r6 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes((android.content.Context) r2, (int) r6, (int[]) r14)
            if (r4 != 0) goto L_0x00b3
            int r14 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r15 = r6.hasValue(r14)
            if (r15 == 0) goto L_0x00b3
            boolean r14 = r6.getBoolean(r14, r5)
            r15 = 1
            goto L_0x00b5
        L_0x00b3:
            r14 = 0
            r15 = 0
        L_0x00b5:
            r7.updateTypefaceAndStyle(r2, r6)
            if (r9 >= r11) goto L_0x00e5
            int r10 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            boolean r17 = r6.hasValue(r10)
            if (r17 == 0) goto L_0x00c7
            android.content.res.ColorStateList r10 = r6.getColorStateList(r10)
            goto L_0x00c8
        L_0x00c7:
            r10 = 0
        L_0x00c8:
            int r13 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            boolean r18 = r6.hasValue(r13)
            if (r18 == 0) goto L_0x00d5
            android.content.res.ColorStateList r13 = r6.getColorStateList(r13)
            goto L_0x00d6
        L_0x00d5:
            r13 = 0
        L_0x00d6:
            int r8 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            boolean r19 = r6.hasValue(r8)
            if (r19 == 0) goto L_0x00e3
            android.content.res.ColorStateList r8 = r6.getColorStateList(r8)
            goto L_0x00e8
        L_0x00e3:
            r8 = 0
            goto L_0x00e8
        L_0x00e5:
            r8 = 0
            r10 = 0
            r13 = 0
        L_0x00e8:
            int r11 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            boolean r20 = r6.hasValue(r11)
            if (r20 == 0) goto L_0x00f5
            java.lang.String r11 = r6.getString(r11)
            goto L_0x00f6
        L_0x00f5:
            r11 = 0
        L_0x00f6:
            if (r9 < r12) goto L_0x0105
            int r12 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            boolean r21 = r6.hasValue(r12)
            if (r21 == 0) goto L_0x0105
            java.lang.String r12 = r6.getString(r12)
            goto L_0x0106
        L_0x0105:
            r12 = 0
        L_0x0106:
            r6.recycle()
            goto L_0x0111
        L_0x010a:
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x0111:
            int[] r6 = androidx.appcompat.R.styleable.TextAppearance
            androidx.appcompat.widget.TintTypedArray r6 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r2, r0, r6, r1, r5)
            if (r4 != 0) goto L_0x012c
            int r5 = androidx.appcompat.R.styleable.TextAppearance_textAllCaps
            boolean r22 = r6.hasValue(r5)
            if (r22 == 0) goto L_0x012c
            r22 = r8
            r8 = 0
            boolean r14 = r6.getBoolean(r5, r8)
            r5 = 23
            r15 = 1
            goto L_0x0130
        L_0x012c:
            r22 = r8
            r5 = 23
        L_0x0130:
            if (r9 >= r5) goto L_0x0157
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColor
            boolean r8 = r6.hasValue(r5)
            if (r8 == 0) goto L_0x013e
            android.content.res.ColorStateList r10 = r6.getColorStateList(r5)
        L_0x013e:
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorHint
            boolean r8 = r6.hasValue(r5)
            if (r8 == 0) goto L_0x014a
            android.content.res.ColorStateList r13 = r6.getColorStateList(r5)
        L_0x014a:
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textColorLink
            boolean r8 = r6.hasValue(r5)
            if (r8 == 0) goto L_0x0157
            android.content.res.ColorStateList r8 = r6.getColorStateList(r5)
            goto L_0x0159
        L_0x0157:
            r8 = r22
        L_0x0159:
            int r5 = androidx.appcompat.R.styleable.TextAppearance_textLocale
            boolean r16 = r6.hasValue(r5)
            if (r16 == 0) goto L_0x0165
            java.lang.String r11 = r6.getString(r5)
        L_0x0165:
            r5 = 26
            if (r9 < r5) goto L_0x0175
            int r5 = androidx.appcompat.R.styleable.TextAppearance_fontVariationSettings
            boolean r16 = r6.hasValue(r5)
            if (r16 == 0) goto L_0x0175
            java.lang.String r12 = r6.getString(r5)
        L_0x0175:
            r5 = 28
            if (r9 < r5) goto L_0x0192
            int r5 = androidx.appcompat.R.styleable.TextAppearance_android_textSize
            boolean r16 = r6.hasValue(r5)
            if (r16 == 0) goto L_0x0192
            r16 = r3
            r3 = -1
            int r5 = r6.getDimensionPixelSize(r5, r3)
            if (r5 != 0) goto L_0x0194
            android.widget.TextView r3 = r7.mView
            r5 = 0
            r0 = 0
            r3.setTextSize(r0, r5)
            goto L_0x0194
        L_0x0192:
            r16 = r3
        L_0x0194:
            r7.updateTypefaceAndStyle(r2, r6)
            r6.recycle()
            if (r10 == 0) goto L_0x01a1
            android.widget.TextView r0 = r7.mView
            r0.setTextColor(r10)
        L_0x01a1:
            if (r13 == 0) goto L_0x01a8
            android.widget.TextView r0 = r7.mView
            r0.setHintTextColor(r13)
        L_0x01a8:
            if (r8 == 0) goto L_0x01af
            android.widget.TextView r0 = r7.mView
            r0.setLinkTextColor(r8)
        L_0x01af:
            if (r4 != 0) goto L_0x01b6
            if (r15 == 0) goto L_0x01b6
            r7.setAllCaps(r14)
        L_0x01b6:
            android.graphics.Typeface r0 = r7.mFontTypeface
            if (r0 == 0) goto L_0x01cc
            int r3 = r7.mFontWeight
            r4 = -1
            if (r3 != r4) goto L_0x01c7
            android.widget.TextView r3 = r7.mView
            int r4 = r7.mStyle
            r3.setTypeface(r0, r4)
            goto L_0x01cc
        L_0x01c7:
            android.widget.TextView r3 = r7.mView
            r3.setTypeface(r0)
        L_0x01cc:
            if (r12 == 0) goto L_0x01d3
            android.widget.TextView r0 = r7.mView
            r0.setFontVariationSettings(r12)
        L_0x01d3:
            if (r11 == 0) goto L_0x01f7
            r0 = 24
            if (r9 < r0) goto L_0x01e3
            android.widget.TextView r0 = r7.mView
            android.os.LocaleList r3 = android.os.LocaleList.forLanguageTags(r11)
            r0.setTextLocales(r3)
            goto L_0x01f7
        L_0x01e3:
            r0 = 44
            int r0 = r11.indexOf(r0)
            r3 = 0
            java.lang.String r0 = r11.substring(r3, r0)
            android.widget.TextView r3 = r7.mView
            java.util.Locale r0 = java.util.Locale.forLanguageTag(r0)
            r3.setTextLocale(r0)
        L_0x01f7:
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r0 = r7.mAutoSizeTextHelper
            r3 = r24
            r0.loadFromAttributes(r3, r1)
            boolean r0 = androidx.core.widget.AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE
            if (r0 == 0) goto L_0x023d
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r0 = r7.mAutoSizeTextHelper
            int r0 = r0.getAutoSizeTextType()
            if (r0 == 0) goto L_0x023d
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r0 = r7.mAutoSizeTextHelper
            int[] r0 = r0.getAutoSizeTextAvailableSizes()
            int r1 = r0.length
            if (r1 <= 0) goto L_0x023d
            android.widget.TextView r1 = r7.mView
            int r1 = r1.getAutoSizeStepGranularity()
            float r1 = (float) r1
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0238
            android.widget.TextView r0 = r7.mView
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r1 = r7.mAutoSizeTextHelper
            int r1 = r1.getAutoSizeMinTextSize()
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r4 = r7.mAutoSizeTextHelper
            int r4 = r4.getAutoSizeMaxTextSize()
            androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper r5 = r7.mAutoSizeTextHelper
            int r5 = r5.getAutoSizeStepGranularity()
            r0.setAutoSizeTextTypeUniformWithConfiguration(r1, r4, r5, 0)
            goto L_0x023d
        L_0x0238:
            android.widget.TextView r1 = r7.mView
            r1.setAutoSizeTextTypeUniformWithPresetSizes(r0, 0)
        L_0x023d:
            int[] r0 = androidx.appcompat.R.styleable.AppCompatTextView
            androidx.appcompat.widget.TintTypedArray r8 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes((android.content.Context) r2, (android.util.AttributeSet) r3, (int[]) r0)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableLeftCompat
            r1 = -1
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x0254
            r3 = r16
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r4 = r0
            goto L_0x0257
        L_0x0254:
            r3 = r16
            r4 = 0
        L_0x0257:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTopCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x0265
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r5 = r0
            goto L_0x0266
        L_0x0265:
            r5 = 0
        L_0x0266:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableRightCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x0274
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r6 = r0
            goto L_0x0275
        L_0x0274:
            r6 = 0
        L_0x0275:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableBottomCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x0283
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r9 = r0
            goto L_0x0284
        L_0x0283:
            r9 = 0
        L_0x0284:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableStartCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x0292
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r10 = r0
            goto L_0x0293
        L_0x0292:
            r10 = 0
        L_0x0293:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableEndCompat
            int r0 = r8.getResourceId(r0, r1)
            if (r0 == r1) goto L_0x02a1
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r2, r0)
            r11 = r0
            goto L_0x02a2
        L_0x02a1:
            r11 = 0
        L_0x02a2:
            r0 = r23
            r1 = r4
            r2 = r5
            r3 = r6
            r4 = r9
            r5 = r10
            r6 = r11
            r0.setCompoundDrawables(r1, r2, r3, r4, r5, r6)
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTint
            boolean r1 = r8.hasValue(r0)
            if (r1 == 0) goto L_0x02be
            android.content.res.ColorStateList r0 = r8.getColorStateList(r0)
            android.widget.TextView r1 = r7.mView
            androidx.core.widget.TextViewCompat.setCompoundDrawableTintList(r1, r0)
        L_0x02be:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_drawableTintMode
            boolean r1 = r8.hasValue(r0)
            if (r1 == 0) goto L_0x02d6
            r1 = -1
            int r0 = r8.getInt(r0, r1)
            r2 = 0
            android.graphics.PorterDuff$Mode r0 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r0, r2)
            android.widget.TextView r2 = r7.mView
            androidx.core.widget.TextViewCompat.setCompoundDrawableTintMode(r2, r0)
            goto L_0x02d7
        L_0x02d6:
            r1 = -1
        L_0x02d7:
            int r0 = androidx.appcompat.R.styleable.AppCompatTextView_firstBaselineToTopHeight
            int r0 = r8.getDimensionPixelSize(r0, r1)
            int r2 = androidx.appcompat.R.styleable.AppCompatTextView_lastBaselineToBottomHeight
            int r2 = r8.getDimensionPixelSize(r2, r1)
            int r3 = androidx.appcompat.R.styleable.AppCompatTextView_lineHeight
            int r3 = r8.getDimensionPixelSize(r3, r1)
            r8.recycle()
            if (r0 == r1) goto L_0x02f3
            android.widget.TextView r4 = r7.mView
            androidx.core.widget.TextViewCompat.setFirstBaselineToTopHeight(r4, r0)
        L_0x02f3:
            if (r2 == r1) goto L_0x02fa
            android.widget.TextView r0 = r7.mView
            androidx.core.widget.TextViewCompat.setLastBaselineToBottomHeight(r0, r2)
        L_0x02fa:
            if (r3 == r1) goto L_0x0301
            android.widget.TextView r0 = r7.mView
            androidx.core.widget.TextViewCompat.setLineHeight(r0, r3)
        L_0x0301:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    public void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    public void onSetTextAppearance(Context context, int i) {
        String string;
        ColorStateList colorStateList;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        int i2 = R.styleable.TextAppearance_textAllCaps;
        if (obtainStyledAttributes.hasValue(i2)) {
            setAllCaps(obtainStyledAttributes.getBoolean(i2, false));
        }
        int i3 = Build.VERSION.SDK_INT;
        if (i3 < 23) {
            int i4 = R.styleable.TextAppearance_android_textColor;
            if (obtainStyledAttributes.hasValue(i4) && (colorStateList = obtainStyledAttributes.getColorStateList(i4)) != null) {
                this.mView.setTextColor(colorStateList);
            }
        }
        int i5 = R.styleable.TextAppearance_android_textSize;
        if (obtainStyledAttributes.hasValue(i5) && obtainStyledAttributes.getDimensionPixelSize(i5, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes);
        if (i3 >= 26) {
            int i6 = R.styleable.TextAppearance_fontVariationSettings;
            if (obtainStyledAttributes.hasValue(i6) && (string = obtainStyledAttributes.getString(i6)) != null) {
                this.mView.setFontVariationSettings(string);
            }
        }
        obtainStyledAttributes.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void runOnUiThread(@NonNull Runnable runnable) {
        this.mView.post(runnable);
    }

    public void setAllCaps(boolean z) {
        this.mView.setAllCaps(z);
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] iArr, int i) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    public void setAutoSizeTextTypeWithDefaults(int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i);
    }

    public void setCompoundDrawableTintList(@Nullable ColorStateList colorStateList) {
        boolean z;
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintList = colorStateList;
        if (colorStateList != null) {
            z = true;
        } else {
            z = false;
        }
        tintInfo.mHasTintList = z;
        setCompoundTints();
    }

    public void setCompoundDrawableTintMode(@Nullable PorterDuff.Mode mode) {
        boolean z;
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintMode = mode;
        if (mode != null) {
            z = true;
        } else {
            z = false;
        }
        tintInfo.mHasTintMode = z;
        setCompoundTints();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setTextSize(int i, float f) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(i, f);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setTypefaceByCallback(@NonNull Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mView.setTypeface(typeface);
            this.mFontTypeface = typeface;
        }
    }
}
