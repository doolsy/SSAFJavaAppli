package JavaApp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue; 

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.parser.JSONParser;

import twitter4j.*;
import twitter4j.auth.AccessToken;

@SuppressWarnings("unused")
public class Appli {

	
	@SuppressWarnings("unchecked")
	public static void main(String[] argv) throws Exception {

		String API="", account_name="",language="",date="";

		ArrayList <String> kw = new ArrayList<String>(),ht = new ArrayList<String>()
				,usr = new ArrayList<String>(),reply_to =new ArrayList<String>(),bounding_box =new ArrayList<String>();
		int hr[] = {0,0};
		//hr ={0,0};
		int nb_rt[] = {0,0};


		/*****PARSER DU FICHIER DE RECHERCHE****/
		JSONParser parser = new JSONParser();

		String ap="", account="",lang="",dat="",search_title="",search_timestamp="";
		Date search_time;
		JSONArray keywords,retweets, hour,answer_to,users,bb;
		int number_of_tweet = 0;

		try {

			Object obj = parser.parse(new FileReader(
					"C:/xampp/htdocs/SSAFProject/Controlers/recherche.json"));

			JSONObject jsonObject = (JSONObject) obj;

			ap = (String) jsonObject.get("API");
//			account = (String) jsonObject.get("compte_API");
//			keywords = (JSONArray) jsonObject.get("keywords");
//			language = (String) jsonObject.get("language");
//			retweets = (JSONArray) jsonObject.get("retweets_from_to");
//			date = (String)jsonObject.get("date");
//			hour = (JSONArray) jsonObject.get("hour_from_to");
//			answer_to = (JSONArray) jsonObject.get("answer_to");
//			users = (JSONArray) jsonObject.get("users");
//			bb = (JSONArray) jsonObject.get("bounding_box");
//			
//			search_title = (String) jsonObject.get("search_title"); // �  traiter
//			
//			search_timestamp = (String) jsonObject.get("search_timestamp");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//			search_time = sdf.parse(search_timestamp);
//			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
//			search_time = sdf.parse(sdf.format(search_time));//� traiter
//			
//			number_of_tweet = Integer.parseInt((String) jsonObject.get("result_limit")); //� traiter
//
//			//System.out.println(search_time);
//			if(keywords != null)
//			{
//				Iterator<String> it = keywords.iterator();
//				while (it.hasNext()) {
//					//System.out.println((String)it.next());
//					String wd = it.next();
//					if(wd.indexOf("#") == 0)
//						ht.add(wd);
//					else 
//						kw.add(wd);
//					//System.out.print(it1.next() + " ");
//				}
//			}
//
//			if(users != null)
//			{
//				Iterator<String> it = users.iterator();
//				while (it.hasNext()) {
//					usr.add(it.next());
//				}
//			}
//			if(answer_to != null)
//			{
//				Iterator<String> it = answer_to.iterator();
//				while (it.hasNext()) {
//					reply_to.add(it.next());
//				}
//			}
//			if(bb != null)
//			{
//				Iterator<String> it = bounding_box.iterator();
//				while (it.hasNext()) {
//					bounding_box.add(it.next());
//				}
//			}
//			if(retweets != null)
//			{
//				Iterator<Long> it = retweets.iterator();
//				int i=0;
//				while (it.hasNext()) {
//					nb_rt[i]= it.next().intValue();
//					i++;
//				}
//			}
//			if(hour != null)
//			{
//				Iterator<Long> it = hour.iterator();
//				int i=0;
//				while (it.hasNext()) {
//					hr[i]= it.next().intValue();
//					//System.out.println(it.next());
//					i++;
//				}
//			}
//			if (! (lang.equals("")))
//				language = lang;
//			if(! (dat.equals("")))
//				date = dat;
			API = ap;
			//account_name = account;

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		/*********************************END PARSER**************************/
		
		/*********************************CONNEXION A LA BDD & RECHERCHES******************************/
		Connection connexion = null;
		Statement stmt = null;
		if(API.equals("rest"))
		{
			Rest_search r = new Rest_search();
			r.parse();
			r.search_and_insert();
//			try {
//
//				connexion = DriverManager.getConnection(
//						"jdbc:postgresql://localhost:5432/extractstweet", "postgres",
//						"s");
//				stmt = connexion.createStatement();
//				
//				String sql ="SELECT * FROM \"Info_user_app\" WHERE account_name = '"+account_name+"'";
//				ResultSet rs = stmt.executeQuery(sql);
//
//				while(rs.next()){
//					//Retrieve by column name
//					int id = rs.getInt("id_iua");
//					String ck = rs.getString("consumer_key");
//					String cks = rs.getString("consumer_secret");
//					String at = rs.getString("access_token");
//					String ats = rs.getString("access_token_secret");
//
//
//					User_info ui = new User_info(id,ck,cks,at,ats);
//					r.setUi(ui);
//					//r.add_user("bastokova");
//					//r.add_user("lezybeatz");
//					//Display values
//				}
//				//recuperer les param�tres de chaque bounding box � travers son nom, cree une classe bounding box pour chak
//				// et inserer les classe bb dans la recherche
//				rs.close();
//				System.out.println("Connexion effective !");
//			}catch(SQLException se){
//				//Handle errors for JDBC
//				se.printStackTrace();
//			}catch(Exception e){
//				//Handle errors for Class.forName
//				e.printStackTrace();
//			}finally{
//				//finally block used to close resources
//				try{
//					if(stmt!=null)
//						connexion.close();
//				}catch(SQLException se){
//				}// do nothing
//				try{
//					if(connexion!=null)
//						connexion.close();
//				}catch(SQLException se){
//					se.printStackTrace();
//				}//end finally try
//			}//end try

//			for(int i=0;i<usr.size();i++)
//				r.add_user(usr.get(i));
//			for(int i=0;i<reply_to.size();i++)
//				r.add_in_reply(reply_to.get(i));
//			for(int i=0;i<kw.size();i++)
//				r.add_kw(kw.get(i));
//			for(int i=0;i<ht.size();i++)
//				r.add_ht(ht.get(i));
//			r.setLanguage(language);
//			r.setDate(date);
//			r.setHour_min(hr[0]);
//			r.setHour_max(hr[1]);
			//r.add_kw("good -morning");
			//r.search_and_insert();
			System.out.println((38284.3456)/1000);
			return;
		}



	}

}