package com.kyler.hangouts.ui.demo;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kyler.hangouts.ui.demo.adapter.HangoutsDrawerAdapter;
import com.kyler.hangouts.ui.demo.adapter.TabsPagerAdapter;
import com.kyler.hangouts.ui.demo.ui.Icons;
import com.wisemandesigns.android.widgets.CircularImageView;

public class HangoutsDemo extends FragmentActivity implements
		ActionBar.TabListener {

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;

	private ActionBarDrawerToggle mDrawerToggle;

	@SuppressWarnings("unused")
	private CharSequence mDrawerTitle;

	private CharSequence mTitle;

	private ArrayList<Icons> icons;
	private HangoutsDrawerAdapter adapter;
	private String[] MDTitles;
	private TypedArray MDIcons;

	public CircularImageView iv;

	final Context context = this;

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;

	public ActionBar.Tab tab;

	@SuppressWarnings("unused")
	private String[] tabs = { "", "", "" };

	public static final StyleSpan STYLE_BOLD = new StyleSpan(Typeface.BOLD);

	SpannableStringBuilder buf = new SpannableStringBuilder();

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		SharedPreferences first = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (!first.getBoolean("firstTime", false)) {

			SharedPreferences.Editor editor = first.edit();

			editor.putBoolean("firstTime", true);
			editor.commit();

		}

		final ActionBar actionBar = getActionBar();

		getActionBar().setTitle(R.string.app_name);

		getActionBar().setIcon(R.drawable.ic_drawer);

		SpannableString s = new SpannableString("");
		s.setSpan(new TypefaceSpan("sans-serif-thin"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		actionBar.setTitle(s);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setHomeButtonEnabled(true);

		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(16, 0, 0, 0);

		setContentView(R.layout.hangouts_demo);

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		MDTitles = getResources().getStringArray(
				R.array.navigation_main_sections);

		MDIcons = getResources().obtainTypedArray(R.array.drawable_ids);

		icons = new ArrayList<Icons>();

		icons.add(new Icons(MDTitles[0], MDIcons.getResourceId(0, -1)));

		icons.add(new Icons(MDTitles[1], MDIcons.getResourceId(1, -2)));

		icons.add(new Icons(MDTitles[2], MDIcons.getResourceId(2, -3)));

		icons.add(new Icons(MDTitles[3], MDIcons.getResourceId(3, -4)));

		icons.add(new Icons(MDTitles[4], MDIcons.getResourceId(4, -5)));

		icons.add(new Icons(MDTitles[5], MDIcons.getResourceId(5, -6)));

		icons.add(new Icons(MDTitles[6], MDIcons.getResourceId(6, -7)));

		MDIcons.recycle();

		adapter = new HangoutsDrawerAdapter(getApplicationContext(), icons);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		LayoutInflater inflater = getLayoutInflater();
		final ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header,
				mDrawerList, false);

		mDrawerList.addHeaderView(header, null, false);

		mDrawerToggle = new ActionBarDrawerToggle(

		this, mDrawerLayout, android.R.color.transparent, R.string.drawer_open,
				R.string.drawer_close)

		{
			public void onDrawerClosed(View view) {

				getActionBar().setIcon(R.drawable.ic_drawer);

				invalidateOptionsMenu();

			}

			public void onDrawerOpened(View drawerView) {

				getActionBar().setIcon(R.drawable.ic_drawer);

				invalidateOptionsMenu();

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			selectItem(0);
		}
		viewPager = (ViewPager) findViewById(R.id.pager);

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_person_light)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_chat_light)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_dialpad_light)
				.setTabListener(this));

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
			setNavDrawerItemNormal(position);
			TextView txtview = ((TextView) view.findViewById(R.id.MDText));
			txtview.setTypeface(null, Typeface.BOLD);
		}

		public void setNavDrawerItemNormal(int position) {
			for (int i = position; i < mDrawerList.getChildCount(); i++) {
				View v = mDrawerList.getChildAt(i);
				TextView txtview = ((TextView) v.findViewById(R.id.MDText));
				Typeface font = Typeface.createFromAsset(context.getAssets(),
						"fonts/Roboto-Thin.ttf");
				txtview.setTypeface(font);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.hangouts_demo, menu);

		return super.onCreateOptionsMenu(menu);
	}

	public void onStart() {
		super.onStart();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {

			return true;
		}
		switch (item.getItemId()) {

		default:

		}
		;

		return super.onOptionsItemSelected(item);
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch (position) {

		case 0:

			break;

		case 1:

			overridePendingTransition(R.anim.activity_open_enter,
					R.anim.activity_open_exit);
			break;

		case 2:

			overridePendingTransition(R.anim.activity_open_enter,
					R.anim.activity_open_exit);
			break;

		case 3:
			break;

		case 4:
			break;

		case 5:

			overridePendingTransition(R.anim.activity_open_enter,
					R.anim.activity_open_exit);
			break;

		}

		ft.commit();

		mDrawerList.setItemChecked(position, true);

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public static class CategoriesFragment extends Fragment {

		public static final String ARG_CATEGORY = "category";

		public CategoriesFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_categories,
					container, false);

			int i = getArguments().getInt(ARG_CATEGORY);

			String List = getResources().getStringArray(
					R.array.navigation_main_sections)[i];

			getActivity().setTitle(List);

			return rootView;

		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}
