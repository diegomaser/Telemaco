package com.diegomartin.telemaco.test.unit;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.diegomartin.telemaco.view.TripActivity;

public class TripActivityTest extends ActivityInstrumentationTestCase2<TripActivity> {
    private TripActivity activity;
    private EditText name;
    private EditText description;
    private Button startDate;
    private Button endDate;
    private Button save;
	
	public TripActivityTest() {
		super("com.diegomartin.view.TripListActivity", TripActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
        this.activity = this.getActivity();
        this.name = (EditText) this.activity.findViewById(com.diegomartin.telemaco.R.id.name);
        this.description = (EditText) this.activity.findViewById(com.diegomartin.telemaco.R.id.description);
        this.startDate = (Button) this.activity.findViewById(com.diegomartin.telemaco.R.id.startDate);
        this.endDate = (Button) this.activity.findViewById(com.diegomartin.telemaco.R.id.endDate);
        this.save = (Button) this.activity.findViewById(com.diegomartin.telemaco.R.id.save);
	}
	
    public void testPreconditions() {
        assertNotNull(name);
        assertNotNull(description);
        assertNotNull(startDate);
        assertNotNull(endDate);
        assertNotNull(save);
    }

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/*public void testAddDelete(){
		int id;
		int size = TripDAO.read().size();
		this.name.setText("TripActivityTest");
		this.description.setText("TripActivityTest2");
		this.startDate.setText("2011-05-14");
		this.endDate.setText("2011-05-20");
		this.save.performClick();
		assertEquals(size+1, TripDAO.read().size());
		//TripDAO.delete(id);
		//assertEquals(size, TripDAO.read().size());
	}*/
}
