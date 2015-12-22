package com.grupo2tbd.thinkink.Utilities;

/**
 * Created by cris_ on 22/12/2015.
 */
    import android.content.Context;
    import android.content.res.ColorStateList;
    import android.content.res.TypedArray;
    import android.util.AttributeSet;
    import android.widget.ImageView;

    import com.grupo2tbd.thinkink.R;

/**
     * https://gist.github.com/tylerchesley/5d15d859be4f3ce31213
     */
     public class TintImage extends ImageView {

        private ColorStateList tint;

        public TintImage(Context context) {
            super(context);
        }

        public TintImage(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs, 0);
        }

        public TintImage(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context, attrs, defStyle);
        }

        private void init(Context context, AttributeSet attrs, int defStyle) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.ActionBar, defStyle, 0);
            tint = a.getColorStateList(
                    R.styleable.CardView_cardBackgroundColor);
            a.recycle();
        }

        @Override
        protected void drawableStateChanged() {
            super.drawableStateChanged();
            if (tint != null && tint.isStateful()) {
                updateTintColor();
            }
        }

        public void setColorFilter(ColorStateList tint) {
            this.tint = tint;
            super.setColorFilter(tint.getColorForState(getDrawableState(), 0));
        }

        private void updateTintColor() {
            int color = tint.getColorForState(getDrawableState(), 0);
            setColorFilter(color);
        }

    }
