package com.mehmet6parmak.transitiondrawables;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RepeatingTransitionDrawableFragment extends Fragment implements View.OnClickListener {

	EditText edtRepeatCount;
	EditText edtDuration;
	ViewGroup layoutContainer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);

		view.findViewById(R.id.btn_start).setOnClickListener(this);
		view.findViewById(R.id.btn_reset).setOnClickListener(this);

		edtRepeatCount = (EditText) view.findViewById(R.id.edt_repeat_count);
		edtDuration = (EditText) view.findViewById(R.id.edt_duration);
		layoutContainer = (ViewGroup) view.findViewById(R.id.layout_main);

		Drawable[] drawables = new Drawable[] { getResources().getDrawable(R.drawable.wallpaper1), getResources().getDrawable(R.drawable.wallpaper2) };
		RepeatableTransitionDrawable drawable = new RepeatableTransitionDrawable(drawables);

		layoutContainer.setBackground(drawable);

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_start) {
			int repeatCount = Integer.valueOf(edtRepeatCount.getText().toString());
			int duration = Integer.valueOf(edtDuration.getText().toString());

			RepeatableTransitionDrawable drawable = (RepeatableTransitionDrawable) layoutContainer.getBackground();
			drawable.setRepeatCount(repeatCount);
			drawable.startTransition(duration);
		} else if (v.getId() == R.id.btn_reset) {
			RepeatableTransitionDrawable drawable = (RepeatableTransitionDrawable) layoutContainer.getBackground();
			drawable.resetTransition();
		}
	}

}
