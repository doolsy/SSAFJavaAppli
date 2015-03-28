package JavaApp;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import twitter4j.*;
import twitter4j.auth.AccessToken;

@SuppressWarnings("unused")
public class Rest_search extends Search {

	private ArrayList<Bounding_box_rest> bb;

	private String date;
	private int hour_min;
	private int hour_max;

	public Rest_search() {
		super();
		bb = new ArrayList<Bounding_box_rest>();
		date = "";
		hour_min = 0;
		hour_max = 0;
		nb_tweet = 0;
	}



	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getHour_min() {
		return hour_min;
	}




	public void setHour_min(int hour_min) {
		this.hour_min = hour_min;
	}




	public int getHour_max() {
		return hour_max;
	}




	public void setHour_max(int hour_max) {
		this.hour_max = hour_max;
	}

	public ArrayList<Bounding_box_rest> getBb() {
		return bb;
	}

	public void setBb(ArrayList<Bounding_box_rest> bb) {
		this.bb = bb;
	}

	public void add_bounding_box(Bounding_box_rest bb)
	{
		this.bb.add(bb);
	}
	public boolean containHashtag(String text)
	{
		String[] s = text.toLowerCase().split(" ");
		for(int i=0;i<hashtags.size();i++)
		{
			for(int j=0;j<s.length;j++)
				if(s[j].equals(hashtags.get(i).toLowerCase()))
					return true;
		}
//		for(int i=0;i<hashtags.size();i++)
//			if(text.toLowerCase().contains(hashtags.get(i).toLowerCase()))
//				return true;
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
	public String build_kw_and_ht()
	{
		String kw_ht = "";
		if(keywords.size() != 0)
		{
			for(int i=0;i < keywords.size()-1;i++)
			{
				if(keywords.get(i).indexOf(" ") != -1)
					kw_ht += "\""+keywords.get(i)+"\"" + " OR ";
				else
					kw_ht += keywords.get(i)+" OR ";
			}
			if(keywords.get(keywords.size()-1).indexOf(" ") != -1)
				kw_ht += "\""+keywords.get(keywords.size()-1)+"\"";
		}

		if(hashtags.size() != 0 && !(kw_ht.equals("")))
			kw_ht += " OR ";
		if(hashtags.size() != 0)
		{
			for(int i=0;i < hashtags.size()-1;i++)
			{
				kw_ht += hashtags.get(i)+" OR ";
			}
			kw_ht += hashtags.get(hashtags.size()-1);
		}
		return kw_ht;
	}

	public void parse()
	{
		String API="", account_name="",language="",date="";

		ArrayList<String> bounding_box =new ArrayList<String>();
		int hr[] = {0,0};
		//hr ={0,0};
		int nb_rt[] = {0,0};


		/*****PARSER DU FICHIER DE RECHERCHE****/
		JSONParser parser = new JSONParser();

		String ap="", account="",lang="",dat="",search_title="",search_timestamp="",number_of_tweet="";
		Date search_time;
		JSONArray keywords,retweets, hour,answer_to,users,bb;

		try {

			Object obj = parser.parse(new FileReader(
					"C:/xampp/htdocs/SSAFProject/Controlers/recherche.json"));

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
			if(hour != null)
			{
				Iterator<Long> it = hour.iterator();
				int i=0;
				while (it.hasNext()) {
					hr[i]= it.next().intValue();
					//System.out.println(it.next());
					i++;
				}
				this.setHour_min(hr[0]);
				this.setHour_max(hr[1]);
			}
			if (language != null)
				this.setLanguage(language);
			if(date != null)
				this.setDate(date);
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
//			while(rs.next()){
//				//Retrieve by column name
//				int id = rs.getInt("id_iua");
//				String ck = rs.getString("consumer_key");
//				String cks = rs.getString("consumer_secret");
//				String at = rs.getString("access_token");
//				String ats = rs.getString("access_token_secret");
//
//
//				User_info ui = new User_info(id,ck,cks,at,ats);
//				this.setUi(ui);
//			}
			for(int i=0;i<bounding_box.size();i++)
			{
				//recuperation des valeurs de chaque bounding box � travers leur nom
				sql = "SELECT * FROM \"Bounding_box\" WHERE title = '"+bounding_box.get(i)+"'";
				rs = stmt.executeQuery(sql);

				while(rs.next()){
					int id = rs.getInt("id_bb");
					double clong = rs.getDouble("center_longitude");
					double clat = rs.getDouble("center_latitude");
					double radius = rs.getDouble("radius");
					Bounding_box_rest bbr = new Bounding_box_rest(id,clong,clat,(radius)/1000,"km");
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



	@SuppressWarnings("deprecation")
	public void search_and_insert() throws TwitterException, Exception{
		String kw_ht = "";

		if(nb_tweet == 0)
			setNb_tweet(500);// = 500;
		AccessToken aToken;

		// Creation d'un objet Twitter

		Twitter t = new TwitterFactory().getInstance();

		t.setOAuthConsumer(ui.getConsumer_key(), ui.getConsumer_secret());
		aToken = new AccessToken(ui.getAccess_token(), ui.getAccess_token_secret());
		t.setOAuthAccessToken(aToken);

		if(bb.size() == 0)
		{
			if(users.size() != 0)
			{
				for(int i=0;i<users.size();i++)
				{	
					Paging page = new Paging ();
					page.setCount(200);
					List<Status> statuses = t.getUserTimeline(users.get(i),page);
					//System.out.println(statuses.size());
					for (Status status : statuses) {

						//System.out.println("omg");
						if((in_reply_to.size() != 0 && contain_an_user(status.getText())) || in_reply_to.size() == 0)
						{
							if(hashtags.size() == 0 && keywords.size() == 0)
							{
								if(date != "")
								{
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String d1 = date;
									String d2 = sdf.format(status.getCreatedAt());
									if(sdf.parse(d1).before(sdf.parse(d2)))
										if(hour_min != 0 && hour_max != 0)
										{
											if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)

												if(language.equals("") || status.getLang().equals(language))
													if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
														System.out.println(status.getUser().getName() + ":" +
																status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
										}
										else //pas de filtre par heure
											if(language.equals("") || status.getLang().equals(language))
												if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
													System.out.println(status.getUser().getName() + ":" +
															status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
								else
								{
									if(hour_min != 0 && hour_max != 0)
									{
										if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)
											if(language.equals("") || status.getLang().equals(language))
												if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
													System.out.println(status.getUser().getName() + ":" +
															status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
									}
									else //pas de filtre par heure
										if(language.equals("") || status.getLang().equals(language))
											if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
												System.out.println(status.getUser().getName() + ":" +
														status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
							}
							if(containKeywords(status.getText()))
							{
								if(date != "")
								{
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String d1 = date;
									String d2 = sdf.format(status.getCreatedAt());
									if(sdf.parse(d1).before(sdf.parse(d2)))
										if(hour_min != 0 && hour_max != 0)
										{
											if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)
												if(language.equals("") || status.getLang().equals(language))
													if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
														System.out.println(status.getUser().getName() + ":" +
																status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
										}
										else //pas de filtre par heure
											if(language.equals("") || status.getLang().equals(language))
												if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
													System.out.println(status.getUser().getName() + ":" +
															status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
								else
								{
									if(hour_min != 0 && hour_max != 0)
									{
										if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)
											if(language.equals("") || status.getLang().equals(language))
												if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
													System.out.println(status.getUser().getName() + ":" +
															status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
									}
									else //pas de filtre par heure
										if(language.equals("") || status.getLang().equals(language))
											if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
												System.out.println(status.getUser().getName() + ":" +
														status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()) );//inserer dans la bdd

								}
							}
							if(!containKeywords(status.getText()) && containHashtag(status.getText()))
							{
								if(date != "")
								{
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String d1 = date;
									String d2 = sdf.format(status.getCreatedAt());
									if(sdf.parse(d1).before(sdf.parse(d2)))
										if(hour_min != 0 && hour_max != 0)
										{
											if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)
												if(language.equals("") || status.getLang().equals(language))
													if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
														System.out.println(status.getUser().getName() + ":" +
																status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
										}
										else //pas de filtre par heure
											if(language.equals("") || status.getLang().equals(language))
												if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
													System.out.println(status.getUser().getName() + ":" +
															status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
								else
								{
									if(hour_min != 0 && hour_max != 0)
									{
										if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)

											if(language.equals("") || status.getLang().equals(language))
												if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
													System.out.println(status.getUser().getName() + ":" +
															status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
									}
									else //pas de filtre par heure
										if(language.equals("") || status.getLang().equals(language))
											if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
												System.out.println(status.getUser().getName() + ":" +
														status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
							}
						}
					}
				}
			}

			else{

				
				kw_ht = build_kw_and_ht();
				System.out.println(kw_ht);
				Query query = new Query(kw_ht);
				if(language != "")
					query.setLang(language);
				if(date != "")
					query.setSince(date);
				if(language != "")
					query.setLang(language);
				query.setCount(100);
				int i=0;
				QueryResult result=t.search(query);
				do{
					i++;
					List<Status> tweets = result.getTweets();
					for(Status status: tweets){
						if(hour_min != 0 && hour_max != 0)
						{
							if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)
								if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )

									System.out.println(status.getUser().getName() + ":" +
											status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd
						}
						else //pas de filtre par heure
							if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
								System.out.println(status.getUser().getName() + ":" +
										status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt())+ " "+
										status.getLang());//inserer dans la bdd

					}
					
					query=result.nextQuery();
					if(query!=null)
						result=t.search(query);
				}while(query!=null && i<(nb_tweet/100));
			}
		}
		else
		{
			//System.out.println("omg");
			for(Bounding_box_rest b : bb)
			{
				//System.out.println(b.getRadius());
				kw_ht = build_kw_and_ht();
				Query query = new Query(kw_ht);
				if(language != "")
					query.setLang(language);
				if(date != "")
					query.setSince(date);
				if(language != "")
					query.setLang(language);
				query.setCount(200);
				int i=0;
				GeoLocation location = new GeoLocation(b.getCenter_latitude(),b.getCenter_longitude());
				query.geoCode(location,b.getRadius(), b.getUnit());
				QueryResult result = t.search(query);
				
				do{
					i++;
					List<Status> tweets = result.getTweets();
					System.out.println(tweets.size());
					for(Status status: tweets){
						if(hour_min != 0 && hour_max != 0)
						{
							if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)
								if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )

									System.out.println(status.getUser().getName() + ":" +
											status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd
						}
						else //pas de filtre par heure
							if(nb_rt_max ==0 || (status.getRetweetCount() >= nb_rt_min && status.getRetweetCount() <= nb_rt_max) )
								System.out.println(status.getUser().getName() + ":" +
										status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt())+ " "+
										status.getLang());//inserer dans la bdd

					}
					query=result.nextQuery();
					if(query!=null)
						result=t.search(query);
				}while(query!=null && i<(nb_tweet/100));
				
				
			}
		}



	}
}
