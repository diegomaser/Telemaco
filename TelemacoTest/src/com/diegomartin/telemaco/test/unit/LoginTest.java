package com.diegomartin.telemaco.test.unit;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.diegomartin.telemaco.Telemaco;

public class LoginTest extends ActivityInstrumentationTestCase2<Telemaco> {
    private Telemaco activity;
    private TextView userTextView;
    private TextView passwordTextView;
    private String user;
    private String password;
	
	public LoginTest() {
		super("com.diegomartin.telemaco", Telemaco.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
        this.activity = this.getActivity();
        this.userTextView = (TextView) activity.findViewById(com.diegomartin.telemaco.R.id.user);
        this.passwordTextView = (TextView) activity.findViewById(com.diegomartin.telemaco.R.id.password);
        this.user = activity.getString(com.diegomartin.telemaco.R.string.user);
        this.password = activity.getString(com.diegomartin.telemaco.R.string.password);
	}
	
    public void testPreconditions() {
        assertNotNull(userTextView);
        assertNotNull(passwordTextView);
    }

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testUser() {
		assertEquals(user, userTextView.getText().toString());
	}
	
	public void testPassword() {
		assertEquals(password, passwordTextView.getText().toString());
	}
}