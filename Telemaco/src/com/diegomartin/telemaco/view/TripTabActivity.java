package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.R.drawable;
import com.diegomartin.telemaco.R.layout;
import com.diegomartin.telemaco.R.string;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TripTabActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.triptab);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, InfoListActivity.class);
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("info").setIndicator(getText(R.string.info), res.getDrawable(R.drawable.tab_info)).setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, PlanListActivity.class);
	    spec = tabHost.newTabSpec("plan").setIndicator(getText(R.string.plan), res.getDrawable(R.drawable.tab_plan)).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, PlacesListActivity.class);
	    spec = tabHost.newTabSpec("places").setIndicator(getText(R.string.places), res.getDrawable(R.drawable.tab_places)).setContent(intent);
	    tabHost.addTab(spec);

	    //tabHost.setCurrentTab(2);
	}
}