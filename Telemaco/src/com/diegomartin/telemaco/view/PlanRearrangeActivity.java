/* Source: http://code.google.com/p/android-drag-and-drop-listview/ */

package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.PlaceControl;
import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.Trip;

public class PlanRearrangeActivity extends ListActivity {
        private ListView mExampleList;
        ExampleArrayAdapter adapter;
        private ArrayList<Place> places;
        private Trip trip;

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.planlist);

                mExampleList = getListView();
                mExampleList.setOnCreateContextMenuListener(this);
                ((DDListView) mExampleList).setDropListener(mDropListener);
                
                Bundle extras = getIntent().getExtras();
                this.places =  (ArrayList<Place>) extras.get(ActionsFacade.EXTRA_PLACES);
                this.trip = (Trip) extras.get(ActionsFacade.EXTRA_TRIP);
                
                ArrayList<String> array = new ArrayList<String>();
                for (Place p: places) {
                        array.add(p.getName());
                }

                adapter = new ExampleArrayAdapter(getApplicationContext(), R.layout.row, array);
                mExampleList.setAdapter(adapter);
                Log.d("count = ", Integer.toString(mExampleList.getCount()));
        }

        // ArrayAdapter
        private class ExampleArrayAdapter extends ArrayAdapter<String> {
                private Context mContext;
                private int mLayoutId;
                private ArrayList<String> mListContent;

                public ExampleArrayAdapter(Context context, int textViewResourceId,
                                ArrayList<String> objects) {
                        super(context, textViewResourceId, objects);
                        mContext = context;
                        mLayoutId = textViewResourceId;
                        mListContent = objects;
                }

                public View getView(int position, View rowView, ViewGroup parent) {
                        //if (rowView != null) {
                        //      return rowView;
                        //}
                        LayoutInflater inflater = LayoutInflater.from(mContext);
                        View v = inflater.inflate(mLayoutId, null);

                        TextView rowTitle = (TextView) v.findViewById(R.id.text1);
                        rowTitle.setText(mListContent.get(position));

                        return v;
                }
        }

        // Drop Listener
        private DDListView.DropListener mDropListener = new DDListView.DropListener() {
                public void drop(int from, int to) {
                        Log.d("array", adapter.mListContent.toString());
                        String item = adapter.getItem(from);
                        Log.e("a", "from=" + from + " to=" + to + " item " + item);
                        adapter.remove(item);
                        adapter.insert(item, to);
                        
                        Place p = places.get(from);
                        places.remove(from);
                        places.add(to, p);
                }
        };
        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.rearrange_menu, menu);
            return true;
        }
        
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {
	            case R.id.save:
	            	this.save();
	            	finish();
	                return true;
	            case R.id.discard:
	            	finish();
	            	return true;
	            default:
	                return super.onOptionsItemSelected(item);
            }
        }
        
        private void save(){
        	int order = 0;
        	for (Place p: this.places){
        		order++;
        		PlaceControl.updateVisit(p.getId(), this.trip.getId(), order);
        	}
        }
}