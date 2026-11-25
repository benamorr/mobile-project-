package com.ironfit.app.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class AnimationUtilsHelper {

    /**
     * Adds an ultra-smooth tactile spring bounce effect when a button or card is touched.
     */
    @SuppressLint("ClickableViewAccessibility")
    public static void setupTouchBounce(View view) {
        if (view == null) return;
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate()
                            .scaleX(0.95f)
                            .scaleY(0.95f)
                            .setDuration(100)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(220)
                            .setInterpolator(new OvershootInterpolator(2.5f))
                            .start();
                    break;
            }
            return false; // let click listeners continue
        });
    }

    /**
     * Bouncy spring click animation triggered on a view programmatically.
     */
    public static void bounceClick(View view, Runnable onEnd) {
        if (view == null) return;
        view.animate()
                .scaleX(0.92f)
                .scaleY(0.92f)
                .setDuration(90)
                .withEndAction(() -> view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(200)
                        .setInterpolator(new OvershootInterpolator(3.0f))
                        .withEndAction(onEnd)
                        .start())
                .start();
    }

    /**
     * Subtle pulse animation for high priority action badges or buttons.
     */
    public static void startPulse(View view) {
        if (view == null) return;
        ObjectAnimator pulseAnim = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.04f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.04f, 1.0f)
        );
        pulseAnim.setDuration(1600);
        pulseAnim.setRepeatCount(ObjectAnimator.INFINITE);
        pulseAnim.setInterpolator(new DecelerateInterpolator());
        pulseAnim.start();
    }

    /**
     * Smooth fade & slide-in entrance for item cards.
     */
    public static void animateCardEntrance(View view, int position) {
        if (view == null) return;
        view.setAlpha(0f);
        view.setTranslationY(40f);
        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(320)
                .setStartDelay(Math.min(position * 45L, 300L))
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}
