package com.kyler.hangouts.ui.demo.activities;

import com.kyler.hangouts.ui.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Invites extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		SpannableString s = new SpannableString("Invites");
		s.setSpan(new TypefaceSpan("sans-serif-thin"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		getActionBar().setTitle(s);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.invites);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.hangouts_demo, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;

		default:

		}
		;

		return super.onOptionsItemSelected(item);
	}	

}
