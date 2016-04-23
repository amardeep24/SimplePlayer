package com.amardeep.simpleplayer.component.activity.widget;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.MediaController;

public class MusicController extends MediaController {

	public MusicController(Context context) {
		super(context);
	}
	@Override
	public void hide() {
	}
	 public boolean dispatchKeyEvent(KeyEvent event)
     {
         if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
             ((Activity) getContext()).finish();

         return super.dispatchKeyEvent(event);
     }

}
