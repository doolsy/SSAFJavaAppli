package TestApi;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue; 
import org.json.simple.parser.JSONParser;


public class Criteres {
	
	ArrayList <String> kw = new ArrayList<String>();//liste de mots clés
	ArrayList <String> ht = new ArrayList<String>();//liste de hashtag
	ArrayList <String> usr = new ArrayList<String>();//liste d'utilisateurs
	ArrayList <String> reply_to = new ArrayList<String>(); //liste de réponses
	ArrayList <String> bounding_box =new ArrayList<String>();//liste de bounding box
	int hr[] = {0,0}; //tableau de plages horaires
	//hr ={0,0};
	int nb_rt[] = {0,0}; //tableau de retweets
	
	public void parsing () throws Exception {

		String API="", account_name="",language="",date="";
	
	JSONParser parser = new JSONParser();

	String ap="", account="",lang="",dat="",search_title="",search_timestamp="";
	Date search_time;
	JSONArray keywords,retweets, hour,answer_to,users,bb;
	int number_of_tweet = 0;

	try {

		Object obj = parser.parse(new FileReader("C:\\xampp\\htdocs\\SSAFProject\\Controlers\\recherche.json"));

		JSONObject jsonObject = (JSONObject) obj;

		ap = (String) jsonObject.get("API");
		account = (String) jsonObject.get("compte_API");
		keywords = (JSONArray) jsonObject.get("keywords");
		language = (String) jsonObject.get("language");
		retweets = (JSONArray) jsonObject.get("retweets_from_to");
		date = (String)jsonObject.get("date");
		hour = (JSONArray) jsonObject.get("hour_from_to");
		answer_to = (JSONArray) jsonObject.get("answer_to");
		users = (JSONArray) jsonObject.get("users");
		bb = (JSONArray) jsonObject.get("bounding_box");
		
		//search_title = (String) jsonObject.get("search_title"); // à  traiter
		
		search_timestamp = (String) jsonObject.get("search_timestamp");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	//	search_time = sdf.parse(search_timestamp);
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		//search_time = sdf.parse(sdf.format(search_time));//à traiter
		
//		number_of_tweet = Integer.parseInt((String) jsonObject.get("result_limit")); //à traiter

		//System.out.println(search_time);
		if(keywords != null)
		{
			Iterator<String> it = keywords.iterator();
			while (it.hasNext()) {
				//System.out.println((String)it.next());
				String wd = it.next();
				if(wd.indexOf("#") == 0)
					ht.add(wd);
				else 
					kw.add(wd);
				//System.out.print(it1.next() + " ");
			}
		}

		if(users != null)
		{
			Iterator<String> it = users.iterator();
			while (it.hasNext()) {
				usr.add(it.next());
			}
		}
		if(answer_to != null)
		{
			Iterator<String> it = answer_to.iterator();
			while (it.hasNext()) {
				reply_to.add(it.next());
			}
		}
		if(bb != null)
		{
			Iterator<String> it = bounding_box.iterator();
			while (it.hasNext()) {
				bounding_box.add(it.next());
			}
		}
		if(retweets != null)
		{
			Iterator<Long> it = retweets.iterator();
			int i=0;
			while (it.hasNext()) {
				nb_rt[i]= it.next().intValue();
				i++;
			}
		}
		if(hour != null)
		{
			Iterator<Long> it = hour.iterator();
			int i=0;
			while (it.hasNext()) {
				hr[i]= it.next().intValue();
				//System.out.println(it.next());
				i++;
			}
		}
		if (! (lang.equals("")))
			language = lang;
		if(! (dat.equals("")))
			date = dat;
		API = ap;
		account_name = account;

	} 
	catch (Exception e) {
		e.printStackTrace();
	}

	}
}
	
	
	
	
	
	


