package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CountryControl;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Currency;
import com.diegomartin.telemaco.model.Language;
import com.diegomartin.telemaco.model.Plug;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CountryActivity extends Activity {
	private Country country;
    private ListView lv;
    private TextView name;
    private TextView description;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.country);
	    
	    this.country = (Country) getIntent().getExtras().get(ActionsFacade.EXTRA_COUNTRY);
	
        this.name = (TextView) findViewById(R.id.name);
        this.description = (TextView) findViewById(R.id.description);
        this.lv = (ListView) findViewById(R.id.list);
        
        this.name.setText(this.country.getName());
        this.description.setText(this.country.getDescription());
        this.refresh();

        lv.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	if (position == 0) openWikipedia();
	        	else if (position == 1) openWikitravel();
	        	else if (position == 2) openOther();
	        }
	      });
	 }
	
	private void openWikipedia(){
		//String url = this.country.getWikipediaURL();
		String url = "http://en.m.wikipedia.org/wiki/" + this.country.getName().replace(' ', '_');
		if (url.length() > 0) startActivity(ActionsFacade.getInstance().launchBrowserURL(this, url));
	}
	
	private void openWikitravel(){
		//String url = this.country.getWikitravelURL();
		String url = "http://m.wikitravel.org/en/" + this.country.getName().replace(' ', '_');
		if (url.length() > 0) startActivity(ActionsFacade.getInstance().launchBrowserURL(this, url));
	}
	
	private void openOther(){
		String name = this.country.getName();
		
		ArrayList<Language> languages = CountryControl.getLanguages(this.country);
		String html = "<html><body bgcolor=\"#000000\" text=\"#FFFFFF\" >\n";
		html += "<h1>Other information</h1>\n";
		html += "<h2>Language information</h2>\n";
		html += "In " + name + " they speak the following languages:<br>";
		
		html += "<ul>";		
		for (Language language: languages){
			html += "<li>"+language.getName();
		}
		html += "</ul>";
		
		ArrayList<Plug> plugs = CountryControl.getPlugs(this.country);
		html += "<h2>Plug information</h2>\n";
		html += "In " + name + " they use the following plug setup:<br>";
		html += "Plug Voltage: " + country.getPlugVoltage() + "<br>";
		html += "Plug Frequency: " + country.getPlugFrequency() + "<br>";

		for (Plug plug: plugs){
			html += "Plug Type: " + plug.getName() + "<br>";
			html += "<center><img width=\"300px\" src=\"" + plug.getImage() + "\" alt=\"image\"/></center>" + "<br>";
			//html += "Description: " + plug.getDescription() + "<br>";
		}
		
		Currency currency = CountryControl.getCurrency(this.country);
		html += "<h2>Currency information</h2>\n";
		html += "To buy something in " + name + ", you need to change your money to " + currency.getName() + ":<br>";
		html += "1 Euro = " + String.valueOf(currency.getRate()*1) + " " + currency.getName() + "<br>";
		html += "2 Euros = " + String.valueOf(currency.getRate()*2) + " " + currency.getName() + "<br>";
		html += "5 Euros = " + String.valueOf(currency.getRate()*5) + " " + currency.getName() + "<br>";
		html += "10 Euros = " + String.valueOf(currency.getRate()*10) + " " + currency.getName() + "<br>";
		html += "20 Euros = " + String.valueOf(currency.getRate()*20) + " " + currency.getName() + "<br>";
		html += "50 Euros = " + String.valueOf(currency.getRate()*50) + " " + currency.getName() + "<br>";
		html += "100 Euros = " + String.valueOf(currency.getRate()*100) + " " + currency.getName() + "<br>";
		html += "200 Euros = " + String.valueOf(currency.getRate()*200) + " " + currency.getName() + "<br>";
		html += "</body></html>";
		startActivity(ActionsFacade.getInstance().launchBrowserHTML(this, html));
	}
	
	private void refresh(){
	    ArrayList<String> items = getItems();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	    this.lv.setAdapter(adapter);
	}
	
	private ArrayList<String> getItems() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getString(R.string.country_info));
		list.add(getString(R.string.tourism_info));
		list.add(getString(R.string.other_info));
		return list;
	}
}
