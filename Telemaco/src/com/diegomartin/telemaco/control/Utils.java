package com.diegomartin.telemaco.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Utils {
	public static String read(InputStream is) {
		BufferedReader in = null;
	    String buffer = "";
		try { 
		    in = new BufferedReader(new InputStreamReader(is));
		    String line;
		    while ((line = in.readLine()) != null)
		    	buffer += line +"\n";
		} catch (IOException e) { } finally {
			if (in!=null)
				try { in.close(); }
				catch (IOException e) { }
		}
		return buffer;
	}
	
	
	public static void execute(Context context, SQLiteDatabase db, String asset) {
		BufferedReader in = null; 
		try { 
		    in = new BufferedReader(new InputStreamReader(context.getAssets().open(asset)));
		    String line;  
		    while ((line = in.readLine()) != null)
		    	db.execSQL(line); 
		} catch (IOException e) { } finally {
			if (in!=null)
				try { in.close(); }
				catch (IOException e) { }
		}
	}
}
