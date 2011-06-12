package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.NoteControl;
import com.diegomartin.telemaco.model.Note;
import com.diegomartin.telemaco.model.Trip;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NoteActivity extends Activity{
	private Trip trip;
	
    private Button saveButton;
    
    private long id;
    private EditText name;
    private EditText text;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.note);
	    
        this.saveButton = (Button) findViewById(R.id.save);
        this.name = (EditText) findViewById(R.id.name);
        this.text = (EditText) findViewById(R.id.description);

        Bundle received = getIntent().getExtras();
	    this.trip = (Trip) received.get(ActionsFacade.EXTRA_TRIP);
	    Note note = (Note) received.get(ActionsFacade.EXTRA_NOTE);

        if(note == null) {
        	// New note
            this.saveButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	NoteControl.newNote(name.getText().toString(), text.getText().toString(), trip.getId());
                    finish();
                }
            });
        } else {
        	// Edit note
        	this.id = trip.getId();
        	this.name.setText(trip.getName());
        	this.text.setText(trip.getDescription());
            
            this.saveButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	NoteControl.updateNote(id, name.getText().toString(), text.getText().toString(), trip.getId());
                    finish();
                }
            });
        }
	}
}
