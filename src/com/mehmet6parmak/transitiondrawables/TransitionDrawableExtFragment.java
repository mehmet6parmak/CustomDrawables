package com.mehmet6parmak.transitiondrawables;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class TransitionDrawableExtFragment extends Fragment implements OnClickListener {

	private ViewGroup layoutContainer;
	private EditText edtDuration;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.transition_drawable_ext, container, false);

		view.findViewById(R.id.btn_start).setOnClickListener(this);
		view.findViewById(R.id.btn_reset).setOnClickListener(this);
		view.findViewById(R.id.btn_reverse).setOnClickListener(this);

		edtDuration = (EditText) view.findViewById(R.id.edt_duration);
		layoutContainer = (ViewGroup) view.findViewById(R.id.layout_main);
		Resources res = getResources();
		Drawable[] drawables = new Drawable[] { res.getDrawable(R.drawable.wallpaper1), res.getDrawable(R.drawable.wallpaper2),
				res.getDrawable(R.drawable.wallpaper3), res.getDrawable(R.drawable.wallpaper4), res.getDrawable(R.drawable.wallpaper5),
				res.getDrawable(R.drawable.wallpaper6), res.getDrawable(R.drawable.wallpaper7) };
		TransitionDrawableExt drawable = new TransitionDrawableExt(drawables);

		layoutContainer.setBackground(drawable);

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start) {
			int duration = Integer.valueOf(edtDuration.getText().toString());
			TransitionDrawableExt drawable = (TransitionDrawableExt) layoutContainer.getBackground();
			drawable.startTransition(duration);
		} else if (v.getId() == R.id.btn_reset) {
			TransitionDrawableExt drawable = (TransitionDrawableExt) layoutContainer.getBackground();
			drawable.resetTransition();
		} else if (v.getId() == R.id.btn_reverse) {
			TransitionDrawableExt drawable = (TransitionDrawableExt) layoutContainer.getBackground();
			drawable.reverseTransition(1000);
		}

	}

}
