A small project demonstrating some `TransitionDrawable` implementations. 

#### **RepeatableTransitionDrawable** ####
Normally `TransitionDrawable` does not support repeating the transition. It just makes the transition once and stops there. If you would like to use TransitionDrawable to take attention of the user with a repeating transition you need to implement the repeating logic by yourself. `RepeatableTransitionDrawable` supports repeating the transition between two drawables supplied to its constructor. 

**Note:** One downside of custom `Drawable` implementations is that you cannot use them via xml. 

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

### **TransitionDrawableExt** ###
Normally `TransitionDrawable` supports only two `Drawables` and makes a smooth transition between these two. If you want to use more than two images, `TransitionDrawableExt` may help you. Just pass the array of drawables to the constructor of `TransitionDrawableExt` and use methods `startTransition, reverseTransition` to see it how smoothly moves between all the drawables. Use `resetTransition` method to reset its state to initial drawable.

	layoutContainer = (ViewGroup) view.findViewById(R.id.layout_main);
	Resources res = getResources();
	Drawable[] drawables = new Drawable[] { res.getDrawable(R.drawable.wallpaper1), res.getDrawable(R.drawable.wallpaper2),
				res.getDrawable(R.drawable.wallpaper3), res.getDrawable(R.drawable.wallpaper4), res.getDrawable(R.drawable.wallpaper5),
				res.getDrawable(R.drawable.wallpaper6), res.getDrawable(R.drawable.wallpaper7) };
	TransitionDrawableExt drawable = new TransitionDrawableExt(drawables);
	layoutContainer.setBackground(drawable);