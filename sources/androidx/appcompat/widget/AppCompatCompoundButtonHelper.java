package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;

class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private PorterDuff.Mode mButtonTintMode = null;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    private final CompoundButton mView;

    public interface DirectSetButtonDrawableInterface {
        void setButtonDrawable(Drawable drawable);
    }

    public AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
    }

    public void applyButtonTint() {
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
        if (buttonDrawable == null) {
            return;
        }
        if (this.mHasButtonTint || this.mHasButtonTintMode) {
            Drawable mutate = DrawableCompat.wrap(buttonDrawable).mutate();
            if (this.mHasButtonTint) {
                DrawableCompat.setTintList(mutate, this.mButtonTintList);
            }
            if (this.mHasButtonTintMode) {
                DrawableCompat.setTintMode(mutate, this.mButtonTintMode);
            }
            if (mutate.isStateful()) {
                mutate.setState(this.mView.getDrawableState());
            }
            this.mView.setButtonDrawable(mutate);
        }
    }

    public int getCompoundPaddingLeft(int i) {
        return i;
    }

    public ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        return this.mButtonTintMode;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002e A[SYNTHETIC, Splitter:B:12:0x002e] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0051 A[Catch:{ all -> 0x0075 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0062 A[Catch:{ all -> 0x0075 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadFromAttributes(android.util.AttributeSet r4, int r5) {
        /*
            r3 = this;
            android.widget.CompoundButton r0 = r3.mView
            android.content.Context r0 = r0.getContext()
            int[] r1 = androidx.appcompat.R.styleable.CompoundButton
            r2 = 0
            android.content.res.TypedArray r4 = r0.obtainStyledAttributes(r4, r1, r5, r2)
            int r5 = androidx.appcompat.R.styleable.CompoundButton_buttonCompat     // Catch:{ all -> 0x0075 }
            boolean r0 = r4.hasValue(r5)     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x002b
            int r5 = r4.getResourceId(r5, r2)     // Catch:{ all -> 0x0075 }
            if (r5 == 0) goto L_0x002b
            android.widget.CompoundButton r0 = r3.mView     // Catch:{ NotFoundException -> 0x002a }
            android.content.Context r1 = r0.getContext()     // Catch:{ NotFoundException -> 0x002a }
            android.graphics.drawable.Drawable r5 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r1, r5)     // Catch:{ NotFoundException -> 0x002a }
            r0.setButtonDrawable(r5)     // Catch:{ NotFoundException -> 0x002a }
            r5 = 1
            goto L_0x002c
        L_0x002a:
        L_0x002b:
            r5 = 0
        L_0x002c:
            if (r5 != 0) goto L_0x0049
            int r5 = androidx.appcompat.R.styleable.CompoundButton_android_button     // Catch:{ all -> 0x0075 }
            boolean r0 = r4.hasValue(r5)     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x0049
            int r5 = r4.getResourceId(r5, r2)     // Catch:{ all -> 0x0075 }
            if (r5 == 0) goto L_0x0049
            android.widget.CompoundButton r0 = r3.mView     // Catch:{ all -> 0x0075 }
            android.content.Context r1 = r0.getContext()     // Catch:{ all -> 0x0075 }
            android.graphics.drawable.Drawable r5 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r1, r5)     // Catch:{ all -> 0x0075 }
            r0.setButtonDrawable(r5)     // Catch:{ all -> 0x0075 }
        L_0x0049:
            int r5 = androidx.appcompat.R.styleable.CompoundButton_buttonTint     // Catch:{ all -> 0x0075 }
            boolean r0 = r4.hasValue(r5)     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x005a
            android.widget.CompoundButton r0 = r3.mView     // Catch:{ all -> 0x0075 }
            android.content.res.ColorStateList r5 = r4.getColorStateList(r5)     // Catch:{ all -> 0x0075 }
            androidx.core.widget.CompoundButtonCompat.setButtonTintList(r0, r5)     // Catch:{ all -> 0x0075 }
        L_0x005a:
            int r5 = androidx.appcompat.R.styleable.CompoundButton_buttonTintMode     // Catch:{ all -> 0x0075 }
            boolean r0 = r4.hasValue(r5)     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x0071
            android.widget.CompoundButton r0 = r3.mView     // Catch:{ all -> 0x0075 }
            r1 = -1
            int r5 = r4.getInt(r5, r1)     // Catch:{ all -> 0x0075 }
            r1 = 0
            android.graphics.PorterDuff$Mode r5 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r5, r1)     // Catch:{ all -> 0x0075 }
            androidx.core.widget.CompoundButtonCompat.setButtonTintMode(r0, r5)     // Catch:{ all -> 0x0075 }
        L_0x0071:
            r4.recycle()
            return
        L_0x0075:
            r5 = move-exception
            r4.recycle()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatCompoundButtonHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    public void onSetButtonDrawable() {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
            return;
        }
        this.mSkipNextApply = true;
        applyButtonTint();
    }

    public void setSupportButtonTintList(ColorStateList colorStateList) {
        this.mButtonTintList = colorStateList;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    public void setSupportButtonTintMode(@Nullable PorterDuff.Mode mode) {
        this.mButtonTintMode = mode;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }
}
