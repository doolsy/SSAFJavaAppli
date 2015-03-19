package JavaApp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

 


import twitter4j.*;
import twitter4j.auth.AccessToken;

@SuppressWarnings("unused")
public class Rest_search extends Search {

	private ArrayList<Bounding_box_rest> bb;
	private User_info ui;


	public Rest_search() {
		super();
		bb = new ArrayList<Bounding_box_rest>();
		ui = new User_info();
	}







	public Rest_search(ArrayList<String> keywords, ArrayList<String> hashtags,
			String language, ArrayList<String> users,
			ArrayList<String> in_reply_to,String date,int h_min,int h_max) {
		super(keywords, hashtags, language, users, in_reply_to,date,h_min,h_max);
		bb = new ArrayList<Bounding_box_rest>();
		ui = new User_info();
	}


	public User_info getUi() {
		return ui;
	}



	public void setUi(User_info ui) {
		this.ui = ui;
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

	public boolean contain_an_user(String text)
	{
		for(int i=0;i<in_reply_to.size();i++)
			if(text.contains(in_reply_to.get(i)))
				return true;
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
			kw_ht += keywords.get(keywords.size()-1);
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
	@SuppressWarnings("deprecation")
	public void search_and_insert() throws TwitterException, Exception{
		String kw_ht = "";

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

												System.out.println(status.getUser().getName() + ":" +
														status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
										}
										else //pas de filtre par heure
											System.out.println(status.getUser().getName() + ":" +
													status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
								else
								{
									if(hour_min != 0 && hour_max != 0)
									{
										if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)

											System.out.println(status.getUser().getName() + ":" +
													status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
									}
									else //pas de filtre par heure
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

												System.out.println(status.getUser().getName() + ":" +
														status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
										}
										else //pas de filtre par heure
											System.out.println(status.getUser().getName() + ":" +
													status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
								else
								{
									if(hour_min != 0 && hour_max != 0)
									{
										if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)

											System.out.println(status.getUser().getName() + ":" +
													status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
									}
									else //pas de filtre par heure
										System.out.println(status.getUser().getName() + ":" +
												status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

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

												System.out.println(status.getUser().getName() + ":" +
														status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
										}
										else //pas de filtre par heure
											System.out.println(status.getUser().getName() + ":" +
													status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

								}
								else
								{
									if(hour_min != 0 && hour_max != 0)
									{
										if(status.getCreatedAt().getHours() >= hour_min && status.getCreatedAt().getHours() <= hour_max)

											System.out.println(status.getUser().getName() + ":" +
													status.getText() +" "+status.getCreatedAt());//inserer dans la bdd
									}
									else //pas de filtre par heure
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
				Query query = new Query(kw_ht);
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

								System.out.println(status.getUser().getName() + ":" +
										status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd
						}
						else //pas de filtre par heure
							System.out.println(status.getUser().getName() + ":" +
									status.getText() +" "+ new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));//inserer dans la bdd

					}
					System.out.println(i);
					query=result.nextQuery();
					if(query!=null)
						result=t.search(query);
				}while(query!=null && i<=10);
			}
		}
		else
		{
			//			Query query = new Query();
			//			if(bb.getUnit().equals( "km"))
			//				query.setGeoCode(new GeoLocation(bb.getCenter_longitude(),bb.getCenter_latitude()),bb.getRadius(), Query.KILOMETERS);
			//			else
			//				query.setGeoCode(new GeoLocation(bb.getCenter_longitude(),bb.getCenter_latitude()),bb.getRadius(), Query.MILES);
			//			QueryResult result = t.search(query);
			//			for (Status status : result.getTweets()) {
			//				//inserer dans la bbb
			//				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			//			}
		}



	}
}
