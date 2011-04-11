package com.diegomartin.telemaco.control;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class TTSFacade {
	private static TTSFacade instance;
	private TextToSpeech tts;
	
	private TTSFacade(){}
	
	public static TTSFacade getInstance(){
		if (instance==null) instance = new TTSFacade();
		return instance;
	}

	public void speak(String txt){
		tts.speak(txt, TextToSpeech.QUEUE_ADD, null);
	}
	
	public void stopAndSpeak(String txt){
		tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	public void install(Context c){
		this.tts = new TextToSpeech(c, (OnInitListener) c);
		
		Locale defaultLocale = Locale.getDefault();
		String country = defaultLocale.getCountry();
		String lang = defaultLocale.getLanguage();
		String variant = defaultLocale.getVariant();
		
		int result = tts.isLanguageAvailable(defaultLocale);
		switch(result){
			case TextToSpeech.LANG_AVAILABLE:
				tts.setLanguage(new Locale(lang));
				break;
			case TextToSpeech.LANG_COUNTRY_AVAILABLE:
				tts.setLanguage(new Locale(lang, country));
				break;
			case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
				tts.setLanguage(new Locale(lang, country, variant));
				break;
			case TextToSpeech.LANG_MISSING_DATA:
				// missing data, install it
	            Intent installIntent = new Intent();
	            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	            c.startActivity(installIntent);
				break;
			case TextToSpeech.LANG_NOT_SUPPORTED:
				tts.setLanguage(Locale.US);
				break;
			default:
				tts.setLanguage(Locale.US);
		}
	}
	
	public void stop(){
		this.tts.shutdown();
	}
}
