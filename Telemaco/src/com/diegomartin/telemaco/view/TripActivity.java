package com.diegomartin.telemaco.view;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.diegomartin.telemaco.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class TripActivity extends Activity {
    protected static final int START_DATE_DIALOG_ID = 0;
    protected static final int END_DATE_DIALOG_ID = 1;
    private static final int YEAR_OFFSET = 1900;
    
	private Button startDate;
    private Button endDate;
    private Button saveButton; 
    private Date start;
    private Date end;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip);
        
        this.startDate = (Button) findViewById(R.id.startDate);
        this.endDate = (Button) findViewById(R.id.endDate);
        this.saveButton = (Button) findViewById(R.id.save);

        // add a click listener to the button
        this.startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });
        
        this.endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });
        
        this.saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	// TODO: guardar datos
                startActivity(new Intent(getApplicationContext(), TripListActivity.class));
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        this.start = new Date(c.getTimeInMillis());
        this.end = new Date(c.getTimeInMillis());
        updateDisplay();
	}
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        	start = new Date(year-YEAR_OFFSET, monthOfYear, dayOfMonth);
            updateDisplay();
        }
    };
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    	end = new Date(year-YEAR_OFFSET, monthOfYear, dayOfMonth);
	    	updateDisplay();
	    }
    };
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case START_DATE_DIALOG_ID:
	        return new DatePickerDialog(this, startDateSetListener, this.start.getYear()+YEAR_OFFSET, this.start.getMonth(), this.start.getDate());
	    case END_DATE_DIALOG_ID:
	    	return new DatePickerDialog(this, endDateSetListener, this.end.getYear()+YEAR_OFFSET, this.end.getMonth(), this.end.getDate());
	    }
	    return null;
	}
	
    private void updateDisplay() {
    	String date = DateFormat.getDateInstance(DateFormat.LONG).format(this.start);
        startDate.setText(date);
    	date = DateFormat.getDateInstance(DateFormat.LONG).format(this.end);
        endDate.setText(date);
    }
}
