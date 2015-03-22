package TestApi;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.lang.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue; 
import org.json.simple.parser.JSONParser;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.google.gson.Gson;

public class mainTest {

	public static void main(String[] args) throws Exception,TwitterException, IOException {
		
		//=========================ACCES A L'API TWITTER=================================//
		//codes d'acc�s : 
		 	ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setDebugEnabled(true);
	        cb.setOAuthConsumerKey("eRGzpaBXXhnjvEb5kPzrwZK6E");
	        cb.setOAuthConsumerSecret("QtAkRRwYAAjM9cNPfyGjai9TfgjZjcMUQcdPTGUVH29LZQwpP3");
	        cb.setOAuthAccessToken("2994875859-7KHIot0XzW20h6dl2hbDcvvzwxGJoewL8nkxjdN");
	        cb.setOAuthAccessTokenSecret("v8wOJNGoQR6fiJGSaocgu74WTZD3B93Z6QBMRFEIy7DMV");

	        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	     //===================================================================================//   
	        
	     //===========================RECUPERATION DES TWEETS=================================//  
		
		StatusListener listener = new StatusListener(){
			
			public void onStatus(Status status) {
				System.out.println(status.getUser().getName() + " : " + status.getText());
				//System.out.println("RAW JSON: " + TwitterObjectFactory.getRawJSON(status));
			}
			
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
			
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
			
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
			
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
			}
		};
		
		twitterStream.addListener(listener);
		
		//==============================================================================//
		
		//============================RECUPERATION DES CRITERES=========================//
		
		Criteres critere = new Criteres();		
		critere.parsing();
		
//		twitterStream.sample();
		/*=====mots-cl�s
		separer d'une virgule --> OU
		separe du'n espace --> ET , ils seront tous dans la phrase mais pas forc�ment coll�
		expression entre guillement --> il cherche avec les "" aussi...
		le "-" pour la negation ne marche pas*/
		
		
		System.out.println(critere.kw);
		
	    String motscles[] = new String [critere.kw.size()];
	    motscles = critere.kw.toArray(motscles);
		
		FilterQuery fq = new FilterQuery();
		fq.track(motscles);
		//new String[] { "\"good morning\"" }
		twitterStream.filter(fq);
		
		//===========Hashtag====================================
		String hashtag[] = new String [critere.ht.size()];
		hashtag = critere.ht.toArray(motscles);
		 twitterStream.filter(fq.track(hashtag));
		 

		//===============langue==================================
		java.lang.String langue[] = new java.lang.String[1];
		langue[0] = critere.lang;
		fq.language(langue);
		
		//=================geolocalisation =======================
		String bbox[] = new String [critere.bounding_box.size()];
		
		
	   // double[][] locations = { { -122.75d ,36.8d }, { -121.75d, 37.8d } }; // San Francisco
	    //fq.locations(locations);
	    
	    twitterStream.filter(fq);
	    
	    //===================nb de tweet � retouner=================
	    int nbTweets = critere.number_of_tweet;
	    fq.count(nbTweets);
		
			}	

		
		
		
		}
		
	


