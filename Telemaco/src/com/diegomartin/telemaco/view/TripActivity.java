package com.diegomartin.telemaco.view;

import java.text.DateFormat;
import java.util.Calendar;
import java.sql.Date;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.TripControl;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class TripActivity extends Activity {
    protected static final int START_DATE_DIALOG_ID = 0;
    protected static final int END_DATE_DIALOG_ID = 1;
    private static final int YEAR_OFFSET = 1900;

	private Button startDateButton;
    private Button endDateButton;
    private Button saveButton;
    
    private EditText name;
    private EditText description;
    private Date startDate;
    private Date endDate;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip);
        
        this.startDateButton = (Button) findViewById(R.id.startDate);
        this.endDateButton = (Button) findViewById(R.id.endDate);
        this.saveButton = (Button) findViewById(R.id.save);

        // add a click listener to the button
        this.startDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });
        
        this.endDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });
        
        this.saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	TripControl.newTrip(name.getText().toString(), description.getText().toString(), startDate, endDate);
                finish();
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        this.startDate = new Date(c.getTimeInMillis());
        this.endDate = new Date(c.getTimeInMillis());
        updateDisplay();
	}
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        	startDate = new Date(year-YEAR_OFFSET, monthOfYear, dayOfMonth);
            updateDisplay();
        }
    };
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    	endDate = new Date(year-YEAR_OFFSET, monthOfYear, dayOfMonth);
	    	updateDisplay();
	    }
    };
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    	case START_DATE_DIALOG_ID:
	    		return new DatePickerDialog(this,
	    									startDateSetListener,
	    									this.startDate.getYear()+YEAR_OFFSET,
	    									this.startDate.getMonth(),
	    									this.startDate.getDate());
	    	case END_DATE_DIALOG_ID:
	    		return new DatePickerDialog(this,
	    									endDateSetListener,
	    									this.endDate.getYear()+YEAR_OFFSET,
	    									this.endDate.getMonth(),
	    									this.endDate.getDate());
	    }
	    return null;
	}
	
    private void updateDisplay() {
    	String date = DateFormat.getDateInstance(DateFormat.LONG).format(this.startDate);
        startDateButton.setText(date);
    	date = DateFormat.getDateInstance(DateFormat.LONG).format(this.endDate);
        endDateButton.setText(date);
    }
}
