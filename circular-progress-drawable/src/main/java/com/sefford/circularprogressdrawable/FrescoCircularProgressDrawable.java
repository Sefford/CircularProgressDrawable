package com.sefford.circularprogressdrawable;

import android.graphics.Canvas;

/**
 * Created by Ilya Eremin on 25.04.2015.
 */
public class FrescoCircularProgressDrawable extends CircularProgressDrawable {

    private final int size;

    /**
     * Creates a new CouponDrawable.
     *
     * @param ringWidth    Width of the filled ring
     * @param circleScale  Scale difference between the outer ring and the inner circle
     * @param outlineColor Color for the outline color
     * @param ringColor    Color for the filled ring
     * @param centerColor  Color for the center element
     */
    public FrescoCircularProgressDrawable(int ringWidth, float circleScale, int outlineColor, int ringColor, int centerColor, int size) {
        super(ringWidth, circleScale, outlineColor, ringColor, centerColor);
        this.size = size;
    }

    @Override protected boolean onLevelChange(int level) {
        if (indeterminate) {
            this.progress = (float)level/10000;
        } else {
            this.progress = PROGRESS_FACTOR * ((float)level/10000);
        }
        invalidateSelf();
        return super.onLevelChange(level);
    }

    @Override public void draw(Canvas canvas) {
        draw(canvas, size);
    }

    /**
     * Helper class to manage the creation of a CircularProgressDrawable
     *
     * @author Saul Diaz <sefford@gmail.com>
     */
    public static class Builder {

        /**
         * Witdh of the stroke of the filled ring
         */
        int ringWidth;
        /**
         * Color of the outline of the empty ring in #AARRGGBB mode.
         */
        int outlineColor;
        /**
         * Color of the filled ring in #AARRGGBB mode.
         */
        int ringColor;
        /**
         * Color of the inner circle in #AARRGGBB mode.
         */
        int centerColor;
        /**
         * Scale between the outer ring and the inner circle
         */
        float circleScale = 0.75f;

        /**
         * Circle size in pixels
         */
        int size;

        /**
         * Sets the ring width.
         *
         * @param ringWidth Default ring width
         * @return This builder
         */
        public Builder setRingWidth(int ringWidth) {
            this.ringWidth = ringWidth;
            return this;
        }

        /**
         * Sets the default empty outer ring outline color.
         *
         * @param outlineColor Outline color in #AARRGGBB format.
         * @return
         */
        public Builder setOutlineColor(int outlineColor) {
            this.outlineColor = outlineColor;
            return this;
        }

        /**
         * Sets the progress ring color.
         *
         * @param ringColor Ring color in #AARRGGBB format.
         * @returns This Builder
         */
        public Builder setRingColor(int ringColor) {
            this.ringColor = ringColor;
            return this;
        }


        /**
         * Sets the inner circle color.
         *
         * @param centerColor Inner circle color in #AARRGGBB format.
         * @return This builder
         */
        public Builder setCenterColor(int centerColor) {
            this.centerColor = centerColor;
            return this;
        }

        /**
         * Sets the inner circle scale. Defaults to 0.75.
         *
         * @param circleScale Inner circle scale.
         * @return This builder
         */
        public Builder setInnerCircleScale(float circleScale) {
            this.circleScale = circleScale;
            return this;
        }


        /**
         * Sets the size of the circle in pixels. Default fill whole view
         *
         * @param size in pixels
         * @return This builder
         */
        public Builder setSize(int size){
            this.size = size;
            return this;
        }

        /**
         * Creates a new CircularProgressDrawable with the requested parameters
         *
         * @return New CircularProgressDrawableInstance
         */
        public FrescoCircularProgressDrawable create() {
            return new FrescoCircularProgressDrawable(ringWidth, circleScale, outlineColor, ringColor, centerColor, size);
        }

    }
}
