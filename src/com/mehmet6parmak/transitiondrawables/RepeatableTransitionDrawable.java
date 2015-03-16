package com.mehmet6parmak.transitiondrawables;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SystemClock;
import android.util.Log;

public class RepeatableTransitionDrawable extends LayerDrawable {

	private static final String TAG = "RepeatableTransitionDrawable";

	public RepeatableTransitionDrawable(Drawable[] layers) {
		super(layers);
	}

	/**
	 * A transition is about to start.
	 */
	private static final int TRANSITION_STARTING = 0;

	/**
	 * The transition has started and the animation is in progress
	 */
	private static final int TRANSITION_RUNNING = 1;

	/**
	 * No transition will be applied
	 */
	private static final int TRANSITION_NONE = 2;

	/**
	 * The current state of the transition. One of {@link #TRANSITION_STARTING}, {@link #TRANSITION_RUNNING} and {@link #TRANSITION_NONE}
	 */
	private int mTransitionState = TRANSITION_NONE;

	private boolean mReverse;
	private long mStartTimeMillis;
	private int mFrom;
	private int mTo;
	private int mDuration;
	private int mOriginalDuration;
	private int mAlpha = 0;
	private boolean mCrossFade;

	private int currentRepeatedCount = 0;
	private int repeatCount = 1;

	/**
	 * Begin the second layer on top of the first layer.
	 *
	 * @param durationMillis The length of the transition in milliseconds
	 */
	public void startTransition(int durationMillis) {
		if(mTransitionState == TRANSITION_RUNNING)
			return;
		
		Log.d(TAG, "startTransition");
		mFrom = 0;
		mTo = 255;
		mAlpha = 0;
		mDuration = mOriginalDuration = durationMillis;
		mReverse = false;
		mTransitionState = TRANSITION_STARTING;
		currentRepeatedCount = 0;
		invalidateSelf();
	}

	/**
	 * Show only the first layer.
	 */
	public void resetTransition() {
		Log.d(TAG, "resetTransition");
		mAlpha = 0;
		mTransitionState = TRANSITION_NONE;
		currentRepeatedCount = 0;
		invalidateSelf();
	}

	/**
	 * Reverses the transition, picking up where the transition currently is. If the transition is not currently running, this will start the transition with the specified duration. If the transition
	 * is already running, the last known duration will be used.
	 *
	 * @param duration The duration to use if no transition is running.
	 */
	public void reverseTransition(int duration) {
		final long time = SystemClock.uptimeMillis();
		// Animation is over
		if (time - mStartTimeMillis >= mDuration) {
			Log.d(TAG, "Animation is over, reversing");
			if (mTo == 0) {
				mFrom = 0;
				mTo = 255;
				mAlpha = 0;
				mReverse = false;
			} else {
				mFrom = 255;
				mTo = 0;
				mAlpha = 255;
				mReverse = true;
			}
			mDuration = mOriginalDuration = duration;
			mTransitionState = TRANSITION_STARTING;
			invalidateSelf();
			return;
		}
		Log.d(TAG, "Animation is not over yet, changing animation direction. Time elapsed :  " + (time - mStartTimeMillis) );

		mReverse = !mReverse;
		mFrom = mAlpha;
		mTo = mReverse ? 0 : 255;
		mDuration = (int) (mReverse ? time - mStartTimeMillis : mOriginalDuration - (time - mStartTimeMillis));
		mTransitionState = TRANSITION_STARTING;
	}

	@Override
	public void draw(Canvas canvas) {
		boolean done = true;

		switch (mTransitionState) {
		case TRANSITION_STARTING:
			mStartTimeMillis = SystemClock.uptimeMillis();
			done = false;
			mTransitionState = TRANSITION_RUNNING;
			break;

		case TRANSITION_RUNNING:
			if (mStartTimeMillis >= 0) {
				float normalized = (float) (SystemClock.uptimeMillis() - mStartTimeMillis) / mDuration;
				done = normalized >= 1.0f;
				normalized = Math.min(normalized, 1.0f);
				mAlpha = (int) (mFrom + (mTo - mFrom) * normalized);
			}
			break;
		}

		final int alpha = mAlpha;
		final boolean crossFade = mCrossFade;

		if (done) {
			Log.d(TAG, "done inside onDraw");
			// the setAlpha() calls below trigger invalidation and redraw. If we're done, just draw
			// the appropriate drawable[s] and return
			if (!crossFade || alpha == 0) {
				getDrawable(0).draw(canvas);
			}
			if (alpha == 0xFF) {
				getDrawable(1).draw(canvas);
			}

			currentRepeatedCount++;
			Log.d(TAG, "currentRepeatedCount: " + currentRepeatedCount + ", repeatCount: " + repeatCount + ", mTransitionState: " + mTransitionState);
			if (mTransitionState == TRANSITION_RUNNING && currentRepeatedCount < repeatCount) {
				mTransitionState = TRANSITION_NONE;
				reverseTransition(mDuration);
			} else if (mTransitionState == TRANSITION_NONE) {
				resetTransition();
			} else {
				currentRepeatedCount = 0;
				mTransitionState = TRANSITION_NONE;
			}
			return;
		}

		Drawable d;
		d = getDrawable(0);
		if (crossFade) {
			d.setAlpha(255 - alpha);
		}
		d.draw(canvas);
		if (crossFade) {
			d.setAlpha(0xFF);
		}

		if (alpha > 0) {
			d = getDrawable(1);
			d.setAlpha(alpha);
			d.draw(canvas);
			d.setAlpha(0xFF);
		}

		if (!done) {
			invalidateSelf();
		}
	}

	/**
	 * Enables or disables the cross fade of the drawables. When cross fade is disabled, the first drawable is always drawn opaque. With cross fade enabled, the first drawable is drawn with the
	 * opposite alpha of the second drawable. Cross fade is disabled by default.
	 *
	 * @param enabled True to enable cross fading, false otherwise.
	 */
	public void setCrossFadeEnabled(boolean enabled) {
		mCrossFade = enabled;
	}

	/**
	 * Indicates whether the cross fade is enabled for this transition.
	 *
	 * @return True if cross fading is enabled, false otherwise.
	 */
	public boolean isCrossFadeEnabled() {
		return mCrossFade;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

}
