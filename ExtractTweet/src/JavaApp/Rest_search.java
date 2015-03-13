package JavaApp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue; 

import twitter4j.*;
import twitter4j.auth.AccessToken;

public class Rest_search extends Search {

	private Bounding_box_rest bb;
	private User_info ui;
	private String from_user;


	public Rest_search() {
		super();
		bb = null;
		ui = new User_info();
		from_user = "";
	}



	public Rest_search(ArrayList<String> k, ArrayList<String> h, String l) {
		super(k, h, l);
		// TODO Auto-generated constructor stub
	}



	public String getFrom_user() {
		return from_user;
	}



	public void setFrom_user(String from_user) {
		this.from_user = from_user;
	}



	public User_info getUi() {
		return ui;
	}



	public void setUi(User_info ui) {
		this.ui = ui;
	}

	public Bounding_box_rest getBb() {
		return bb;
	}

	public void setBb(Bounding_box_rest bb) {
		this.bb = bb;
	}

	public boolean containHashtag(String text)
	{
		for(int i=0;i<hashtags.size();i++)
			if(text.contains(hashtags.get(i)))
				return true;
		return false;
	}
	public boolean containKeywords(String text)
	{
		for(int i=0;i<keywords.size();i++)
			if(text.contains(keywords.get(i)))
				return true;
		return false;
	}
	
	public void build_kw_and_ht_to_search(String kw_ht)
	{
		if(keywords.size() != 0)
		{
			for(int i=0;i < keywords.size()-1;i++)
			{
				if(keywords.get(i).indexOf(" ") != -1)
					kw_ht += "\""+keywords.get(i)+"\"" + " OR ";
				else
					kw_ht += keywords.get(i)+" OR ";
			}
			kw_ht += keywords.get(keywords.size()-1);
		}

		if(hashtags.size() != 0 && !kw_ht.equals(""))
			kw_ht += " OR ";
		if(hashtags.size() != 0)
		{
			for(int i=0;i < hashtags.size()-1;i++)
			{
				kw_ht += hashtags.get(i)+" OR ";
			}
			kw_ht += hashtags.get(hashtags.size()-1);
		}
	}
	public void search_and_insert() throws TwitterException{
		String kw_ht = "";

		AccessToken aToken;

		// Creation d'un objet Twitter

		Twitter t = new TwitterFactory().getInstance();

		t.setOAuthConsumer(ui.getConsumer_key(), ui.getConsumer_secret());
		aToken = new AccessToken(ui.getAccess_token(), ui.getAccess_token_secret());
		t.setOAuthAccessToken(aToken);

		if(bb.equals(null))
		{
			if(!from_user.equals(""))
			{
				Paging page = new Paging ();
				page.count(200);
				List<Status> statuses = t.getUserTimeline(from_user,page);
				System.out.println(statuses.size());
				for (Status status : statuses) {

					if(containKeywords(status.getText()))
						//inserer dans la bdd
						return;
					if(!containKeywords(status.getText()) && containHashtag(status.getText()))
						System.out.println(status.getUser().getName() + ":" +
								status.getText());//inserer dans la bdd

				}
			}

			else{
				
				build_kw_and_ht_to_search(kw_ht);
				Query query = new Query(kw_ht);
				QueryResult result = t.search(query);
				for (Status status : result.getTweets()) {
					//inserer dans la bbb
					System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
				}
			}
		}



	}
}
