package com.diegomartin.telemaco.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FileUtils {
	public static String read(InputStream is, Context c) {
		BufferedReader in = null;
	    String buffer = "";
		try { 
		    in = new BufferedReader(new InputStreamReader(is));
		    String line;
		    while ((line = in.readLine()) != null)
		    	buffer += line +"\n";
		} catch (IOException e) {
			ToastFacade.show(c, e);
		} finally {
			if (in!=null)
				try { in.close(); }
				catch (IOException e) {
					ToastFacade.show(c, e);
				}
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
		} catch (IOException e) {
			ToastFacade.show(context, e);
		} finally {
			if (in!=null)
				try { in.close(); }
				catch (IOException e) {
					ToastFacade.show(context, e);
				}
		}
	}
	
	public static void write(String file, String text, Context c){
		BufferedWriter out = null;
		try {
			out =new BufferedWriter(new FileWriter(file));
			out.write(text);
			out.close();
		} catch (IOException e) {
			ToastFacade.show(c, e);
		}
		finally{
			if (out!=null)
				try { out.close(); }
				catch (IOException e) {
					ToastFacade.show(c, e);
				}
		}
	}
	
	public static void downloadAndSave(String file, String url, Context c){
		String text = RestMethod.get(c, url);
		write(file, text, c);
	}
}
