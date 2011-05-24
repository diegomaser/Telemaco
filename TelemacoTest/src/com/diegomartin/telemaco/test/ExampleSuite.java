package com.diegomartin.telemaco.test;

import com.diegomartin.telemaco.test.unit.*;

import junit.framework.TestSuite;

public class ExampleSuite extends TestSuite {
    public ExampleSuite() {
        addTestSuite( LoginTest.class );
        addTestSuite( TripTest.class );
        addTestSuite( TripActivityTest.class );
    }
}