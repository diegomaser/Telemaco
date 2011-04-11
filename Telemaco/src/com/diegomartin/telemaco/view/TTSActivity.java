package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.control.TTSFacade;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;


public class TTSActivity extends Activity implements OnInitListener {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TTSFacade.getInstance().install(this);
	}
	
	@Override
	public void onInit(int status) {
		TTSFacade.getInstance().speak("Esto es una prueba");
	}
}
