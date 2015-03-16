A small project demonstrating two custom TransitionDrawable implementations. 

#### **RepeatableTransitionDrawable** ####
Supports repeating the transition between two drawables supplied tp its constructor. 

Initialization:

    Drawable[] drawables = new Drawable[] { getResources().getDrawable(R.drawable.wallpaper1), getResources().getDrawable(R.drawable.wallpaper2) };
	RepeatableTransitionDrawable drawable = new RepeatableTransitionDrawable(drawables);

	layoutContainer.setBackground(drawable);

Starting Transition:

    int repeatCount = Integer.valueOf(edtRepeatCount.getText().toString());
	int duration = Integer.valueOf(edtDuration.getText().toString());

	RepeatableTransitionDrawable drawable = (RepeatableTransitionDrawable) layoutContainer.getBackground();
	drawable.setRepeatCount(repeatCount);
	drawable.startTransition(duration);

****