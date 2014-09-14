package com.kyler.hangouts.ui.demo.adapter;

import com.kyler.hangouts.ui.demo.fragments.Messages;
import com.kyler.hangouts.ui.demo.fragments.People;
import com.kyler.hangouts.ui.demo.fragments.Dialer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			People people = new People();
			return people;
			
		case 1:
			Messages messages = new Messages();
			return messages;
			
		case 2:
			Dialer dialer = new Dialer();
			return dialer;
		}

		return null;
	}

	@Override
	public int getCount() {
		
		return 3;
	}

}
