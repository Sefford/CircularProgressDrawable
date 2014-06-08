/*
 * Copyright (C) 2014 Saúl Díaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sefford.circularprogressdrawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Circular Progress Drawable.
 * <p/>
 * This drawable will produce a circular shape with a ring surrounding it. The ring can appear
 * both filled and give a little cue when it is empty.
 * <p/>
 * The inner circle size, the progress of the outer ring and if it is loading parameters can be
 * controlled, as well the different colors for the three components.
 *
 * @author Saul Diaz <sefford@gmail.com>
 */
public class CircularProgressDrawable extends Drawable {
    /**
     * Factor to convert the factor to paint the arc.
     * <p/>
     * In this way the developer can use a more user-friendly [0..1f] progress
     */
    public static final int PROGRESS_FACTOR = -360;
    /**
     * Property Inner Circle Scale.
     * <p/>
     * The inner ring is supposed to stay 3/4 radius off the outer ring at 100% scale, but this
     * property can make it grow or shrink via this equation: InnerRadius * Scale.
     */
    public static final String CIRCLE_FILL_PROPERTY = "circleScale";
    /**
     * Property Progress of the outer circle.
     * <p/>
     * The progress of the circle. If {@link #setIndeterminate(boolean) indeterminate flag} is set
     * to FALSE, this property will be used to indicate the completion of the outer circle [0..1f].
     * <p/>
     * If set to TRUE, the drawable will activate the loading mode, where the drawable will
     * show a 90º arc which will be spinning around the outer circle as much as progress goes.
     */
    public static final String PROGRESS_PROPERTY = "progress";
    /**
     * Logger Tag for Logging purposes.
     */
    public static final String TAG = "CircularProgressDrawable";
    /**
     * Paint object to draw the element.
     */
    private final Paint paint;
    /**
     * Ring progress.
     */
    protected float progress;
    /**
     * Color for the empty outer ring.
     */
    protected int outlineColor;
    /**
     * Color for the completed ring.
     */
    protected int ringColor;
    /**
     * Color for the inner circle.
     */
    protected int centerColor;
    /**
     * Rectangle where the filling ring will be drawn into.
     */
    protected final RectF arcElements;
    /**
     * Width of the filling ring.
     */
    protected final int ringWidth;
    /**
     * Scale of the inner circle. It will affect the inner circle size on this equation:
     * ([Bigger length of the Drawable] / 2) * 0.75.
     */
    protected float circleScale;
    /**
     * Set if it is an indeterminate
     */
    protected boolean indeterminate;

    /**
     * Creates a new CouponDrawable.
     *
     * @param ringWidth    Width of the filled ring
     * @param outlineColor Color for the outline color
     * @param ringColor    Color for the filled ring
     * @param centerColor  Color for the center element
     */
    public CircularProgressDrawable(int ringWidth, int outlineColor, int ringColor, int centerColor) {
        this.progress = 0;
        this.outlineColor = outlineColor;
        this.ringColor = ringColor;
        this.centerColor = centerColor;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.ringWidth = ringWidth;
        this.arcElements = new RectF();
        this.circleScale = 1;
        this.indeterminate = false;
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();

        // Calculations on the different components sizes
        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = ((size / 2) * 0.75f) * 0.937f;
        float innerRadius = ((size / 2) * 0.75f) * 0.75f;
        float offsetX = (bounds.width() - outerRadius * 2) / 2;
        float offsetY = (bounds.height() - outerRadius * 2) / 2;

        // Outline Circle
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(outlineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius, paint);

        // Inner circle
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(centerColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), innerRadius * circleScale, paint);

        int halfRingWidth = ringWidth / 2;
        float arcX0 = offsetX + halfRingWidth;
        float arcY0 = offsetY + halfRingWidth;
        float arcX = offsetX + outerRadius * 2 - halfRingWidth;
        float arcY = offsetY + outerRadius * 2 - halfRingWidth;

        // Outer Circle
        paint.setColor(ringColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        arcElements.set(arcX0, arcY0, arcX, arcY);
        if (indeterminate) {
            canvas.drawArc(arcElements, progress, 90, false, paint);
        } else {
            canvas.drawArc(arcElements, 89, progress, false, paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // Empty
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }


    /**
     * Returns the progress of the outer ring.
     * <p/>
     * Will output a correct value only when the indeterminate mode is set to FALSE.
     *
     * @return Progress of the outer ring.
     */
    public float getProgress() {
        return progress / PROGRESS_FACTOR;
    }

    /**
     * Sets the progress [0..1f]
     *
     * @param progress Sets the progress
     */
    public void setProgress(float progress) {
        if (indeterminate) {
            this.progress = progress;
        } else {
            this.progress = PROGRESS_FACTOR * progress;
        }
        invalidateSelf();
    }

    /**
     * Returns the inner circle scale.
     *
     * @return Inner circle scale in float multiplier.
     */
    public float getCircleScale() {
        return circleScale;
    }

    /**
     * Sets the inner circle scale.
     *
     * @param circleScale Inner circle scale.
     */
    public void setCircleScale(float circleScale) {
        this.circleScale = circleScale;
        invalidateSelf();
    }

    /**
     * Get the indeterminate status of the Drawable
     *
     * @return TRUE if the Drawable is in indeterminate mode or FALSE if it is in progress mode.
     */
    public boolean isIndeterminate() {
        return indeterminate;
    }

    /**
     * Sets the indeterminate parameter.
     * <p/>
     * The indeterminate parameter will change the behavior of the Drawable. If the indeterminate
     * mode is set to FALSE, the outer ring will be able to be filled by using {@link #setProgress(float) setProgress}.
     * <p/>
     * Otherwise the drawable will enter "loading mode" and a 90º arc will be able to be spinned around
     * the inner circle.
     * <p/>
     * <b>By default, indeterminate mode is set to FALSE.</b>
     *
     * @param indeterminate TRUE to activate loading mode. FALSE to activate progress mode.
     */
    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    /**
     * Gets the outline color.
     *
     * @return Outline color of the empty ring.
     */
    public int getOutlineColor() {
        return outlineColor;
    }

    /**
     * Gets the filled ring color.
     *
     * @return Returns the filled ring color.
     */
    public int getRingColor() {
        return ringColor;
    }

    /**
     * Gets the color of the inner circle.
     *
     * @return Inner circle color.
     */
    public int getCenterColor() {
        return centerColor;
    }

    /**
     * Sets the empty progress outline color.
     *
     * @param outlineColor Outline color in Integer format.
     */
    public void setOutlineColor(int outlineColor) {
        this.outlineColor = outlineColor;
        invalidateSelf();
    }

    /**
     * Sets the progress ring  color.
     *
     * @param ringColor Ring color in Integer format.
     */
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
        invalidateSelf();
    }

    /**
     * Sets the inner circle color.
     *
     * @param centerColor Inner circle color in Integer format.
     */
    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
        invalidateSelf();
    }
}