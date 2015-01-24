package com.itbox.forgetprice;


import com.itbox.forgetprice.widget.Shimmer;
import com.itbox.forgetprice.widget.ShimmerTextView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

@SuppressLint("NewApi")
public class SpalshActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spalsh);
		ShimmerTextView tv = (ShimmerTextView) findViewById(R.id.tv);
		final Shimmer shimmer = new Shimmer();
		shimmer.setRepeatCount(2).setDuration(800).setDirection(Shimmer.ANIMATION_DIRECTION_LTR).setAnimatorListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				shimmer.cancel();
				startActivity(new Intent(SpalshActivity.this, MainActivity.class));
				finish();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		}).start(tv);
	}
}
