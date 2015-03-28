package JavaApp;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;


public class Streaming_search extends Search{

	private ArrayList<Bounding_box_stream> bb;


	public Streaming_search() {
		super();
		this.bb = new ArrayList<Bounding_box_stream>();
	}

	public ArrayList<Bounding_box_stream> getBb() {
		return bb;
	}

	public void setBb(ArrayList<Bounding_box_stream> bb) {
		this.bb = bb;
	}



	@SuppressWarnings("unchecked")
	public void parse()
	{
		@SuppressWarnings("unused")
		String API="", account_name="",language="";

		ArrayList<String> bounding_box =new ArrayList<String>();
		//hr ={0,0};
		int nb_rt[] = {0,0};


		/*****PARSER DU FICHIER DE RECHERCHE****/
		JSONParser parser = new JSONParser();

		String ap="", account="",search_timestamp="",number_of_tweet="";
		Date search_time;
		JSONArray keywords,retweets,answer_to,users,bb;

		try {

			Object obj = parser.parse(new FileReader(
					"C:/xampp/htdocs/SSAFProject/Controlers/recherche.json"));

			JSONObject jsonObject = (JSONObject) obj;

			ap = (String) jsonObject.get("API");
			account = (String) jsonObject.get("compte_API");
			keywords = (JSONArray) jsonObject.get("keywords");
			language = (String) jsonObject.get("language");
			retweets = (JSONArray) jsonObject.get("retweets_from_to");
			answer_to = (JSONArray) jsonObject.get("answer_to");
			users = (JSONArray) jsonObject.get("users");
			bb = (JSONArray) jsonObject.get("bounding_box");

			search_title = (String) jsonObject.get("search_name"); // �  traiter

			search_timestamp = (String) jsonObject.get("search_timestamp");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			search_time = sdf.parse(search_timestamp);
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
			search_time = sdf.parse(sdf.format(search_time));

			number_of_tweet = (String) jsonObject.get("result_limit"); //� traiter

			//System.out.println(search_time);
			if(keywords != null)
			{
				Iterator<String> it = keywords.iterator();
				while (it.hasNext()) {
					//System.out.println((String)it.next());
					String wd = it.next();
					if(wd.indexOf("#") == 0)
						this.add_ht(wd);
					else 
						this.add_kw(wd);
					//System.out.print(it1.next() + " ");
				}
			}

			if(users != null)
			{
				Iterator<String> it = users.iterator();
				while (it.hasNext()) {
					this.add_user(it.next());
				}
			}
			if(answer_to != null)
			{
				Iterator<String> it = answer_to.iterator();
				while (it.hasNext()) {
					this.add_in_reply(it.next());
				}
			}
			if(bb != null)
			{
				Iterator<String> it = bb.iterator();
				while (it.hasNext()) {
					bounding_box.add(it.next());//a changer
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
				this.setNb_rt_min(nb_rt[0]);
				this.setNb_rt_max(nb_rt[1]);
			}

			if (language != null)
				this.setLanguage(language);
			if(number_of_tweet != null)
				this.setNb_tweet(Integer.parseInt(number_of_tweet));
			API = ap;
			account_name = account;
			this.setSearch_title(search_title);
			this.setSearch_time(search_time);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		/*********************************END PARSER**************************/

		/*********************************CONNEXION A LA BDD & RECHERCHES******************************/
		Connection connexion = null;
		Statement stmt = null;

		//Rest_search r = new Rest_search();
		try {

			connexion = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/extractstweet", "postgres",
					"s");
			stmt = connexion.createStatement();

			String sql ="SELECT * FROM \"Info_user_app\" WHERE account_name = '"+account_name+"'";
			ResultSet rs = stmt.executeQuery(sql);
			int iua_id = 0;
			String ck="",cks="",at="",ats="";
			while(rs.next()){
				//Retrieve by column name
				iua_id = rs.getInt("id_iua");
				ck = rs.getString("consumer_key");
				cks = rs.getString("consumer_secret");
				at = rs.getString("access_token");
				ats = rs.getString("access_token_secret");

			}
			
			sql = "SELECT * FROM \"Has1\" WHERE id_iua="+iua_id;
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				//Retrieve by column name
				int ut_id = rs.getInt("id_ut");


				User_info ui = new User_info(ut_id,iua_id,ck,cks,at,ats);
				this.setUi(ui);
			}
			for(int i=0;i<bounding_box.size();i++)
			{
				//recuperation des valeurs de chaque bounding box � travers leur nom
				sql = "SELECT * FROM \"Bounding_box\" WHERE title = '"+bounding_box.get(i)+"'";
				rs = stmt.executeQuery(sql);

				while(rs.next()){
					int id = rs.getInt("id_bb");
					double long1 = rs.getDouble("longitude1");
					double lat1 = rs.getDouble("latitude1");
					double long2 = rs.getDouble("longitude2");
					double lat2 = rs.getDouble("latitude2");
					//double radius = rs.getDouble("radius");
					//System.out.println(id+" "+long1+" "+long2+" "+lat1+" "+lat2);
					Bounding_box_stream bbr = new Bounding_box_stream(id,long1,lat1,long2,lat2);

					this.add_bounding_box(bbr);
				}
			}
			rs.close();
			System.out.println("Connexion effective !");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					connexion.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(connexion!=null)
					connexion.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
	}

	private void add_bounding_box(Bounding_box_stream bbr) {
		this.getBb().add(bbr);

	}

	public boolean containHashtag(String text)
	{
		String[] s = text.toLowerCase().split(" ");
		for(int i=0;i<hashtags.size();i++)
			//if(text.toLowerCase().contains(hashtags.get(i).toLowerCase()))
			//return true;
		{
			for(int j=0;j<s.length;j++)
				if(s[j].equals(hashtags.get(i).toLowerCase()))
					return true;
		}
		return false;
	}
	public boolean containKeywords(String text)
	{
		for(int i=0;i<keywords.size();i++)
			if(text.toLowerCase().contains(keywords.get(i).toLowerCase()))
				return true;
		return false;
	}

	public boolean contain_an_user(String text)
	{
		String[] s = text.toLowerCase().split(" ");
		for(int i=0;i<in_reply_to.size();i++)
		{
			for(int j=0;j<s.length;j++)
				if(s[j].equals(in_reply_to.get(i).toLowerCase()))
					return true;
		}
		//		for(int i=0;i<in_reply_to.size();i++)
		//			if(text.contains(in_reply_to.get(i)))
		//				return true;
		return false;
	}
	public void search_and_insert()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(this.getUi().getConsumer_key());
		cb.setOAuthConsumerSecret(this.getUi().getConsumer_secret());
		cb.setOAuthAccessToken(this.getUi().getAccess_token());
		cb.setOAuthAccessTokenSecret(this.getUi().getAccess_token_secret());

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		//===================================================================================//   

		//===========================RECUPERATION DES TWEETS=================================//  

		StatusListener listener = new StatusListener(){

			public void onStatus(Status status) {
				if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max))
					if(users.size() == 0 || 
					(users.size() != 0 && (containKeywords(status.getText()) || containHashtag(status.getText()))))
						if(in_reply_to.size() == 0 || contain_an_user(status.getText()))
							if (bb.size() == 0 || (bb.size()!=0 && (containKeywords(status.getText()) || containHashtag(status.getText()))))
								System.out.println(status.getUser().getName() + " : " + status.getText() + " langue :"+status.getLang());
				//System.out.println("RAW JSON: " + TwitterObjectFactory.getRawJSON(status));
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}


			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
			}
			public void onStallWarning(StallWarning arg0) {
			}
		};

		twitterStream.addListener(listener);

		Twitter t = new TwitterFactory().getInstance();
		t.setOAuthConsumer(this.getUi().getConsumer_key(), this.getUi().getConsumer_secret());
		AccessToken aToken = new AccessToken(this.getUi().getAccess_token(), this.getUi().getAccess_token_secret());
		t.setOAuthAccessToken(aToken);
		User user = null;


		long[] follow = new long[this.getusers().size()];
		for(int i=0;i<this.getusers().size();i++)
		{
			try {
				user = t.showUser(this.getusers().get(i));
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			follow[i] = user.getId();
		}
		FilterQuery fq = new FilterQuery();
		fq.follow(follow);
		if(this.getLanguage() != "")
		{
			java.lang.String langue[] = new java.lang.String[1];
			langue[0] = this.language;
			fq.language(langue);
		}
		if(nb_tweet != 0)
			fq.count(nb_tweet);

		if(this.getusers().size() == 0)
		{
			if(this.getBb().size() != 0)
			{
				//								double[][] locations = { { -124.2718505859375 ,29.2571470753506 }, 
				//						{ -75.60778808593727,50.736455137010715 } };
				double[][] locations = new double[(this.getBb().size())*2][2];
				int i =0;
				for(Bounding_box_stream b : this.getBb())
				{
					System.out.println(b.getLongitude1()+","+b.getLatitude1());
					locations[i][0] = b.getLongitude1();
					locations[i][1] = b.getLatitude1();
					locations[i+1][0] = b.getLongitude2();
					locations[i+1][1] = b.getLatitude2();
					i+=2;
				}		
				fq.locations(locations);
				twitterStream.filter(fq);
			}
			else
			{
				String[] kw_ht = build_kw_and_ht();
				twitterStream.filter(fq.track(kw_ht));
			}
		}
		else
		{
			twitterStream.filter(fq);
		}

	}

	public String [] build_kw_and_ht(){
		String[] kw_ht = new String[this.hashtags.size() + this.keywords.size()];
		for(int i=0;i<hashtags.size();i++)
			kw_ht[i]=hashtags.get(i);
		for(int j=0;j<keywords.size();j++)
			kw_ht[hashtags.size()+j]=keywords.get(j);
		return kw_ht;
	}


}
