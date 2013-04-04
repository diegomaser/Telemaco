package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;

public class TripTabActivity extends TabActivity {
	private Bundle extra;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.triptab);
	    
	    Resources res = getResources();
	    this.extra = getIntent().getExtras();
	    
	    this.addTab(InfoListActivity.class, getText(R.string.info), res.getDrawable(R.drawable.tab_info));
	    this.addTab(PlacesListActivity.class, getText(R.string.places), res.getDrawable(R.drawable.tab_places));
	    this.addTab(PlanListActivity.class, getText(R.string.plan), res.getDrawable(R.drawable.tab_plan));

	    //TabHost tabHost = getTabHost();
	    //tabHost.setCurrentTab(2);
	}
	
	public void addTab(Class<?> cls, CharSequence text, Drawable drawing){
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;

		Intent intent = new Intent().setClass(this, cls);
	    intent.putExtras(this.extra);
	    spec = tabHost.newTabSpec(text.toString()).setIndicator(text, drawing).setContent(intent);
	    tabHost.addTab(spec);
	}
}