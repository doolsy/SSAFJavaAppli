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
public class JDBCExample {

	public static void main(String[] argv) throws TwitterException {


//		try {
//
//			Class.forName("org.postgresql.Driver");
//
//		} catch (ClassNotFoundException e) {
//
//			System.out.println("Where is your PostgreSQL JDBC Driver? "
//					+ "Include in your library path!");
//			e.printStackTrace();
//			return;
//
//		}
//
//		Connection connexion = null;
//		Statement stmt = null;
//
//		try {
//
//			connexion = DriverManager.getConnection(
//					"jdbc:postgresql://localhost:5432/extractstweet", "postgres",
//					"s");
//			stmt = connexion.createStatement();
//			String sql = "SELECT longitude1, longitude2, latitude1, latitude2 FROM \"Bounding_box\" WHERE title = 'Paris'";
//			ResultSet rs = stmt.executeQuery(sql);
//			//Extract data from result set
//			while(rs.next()){
//				//Retrieve by column name
//				double lg1 = rs.getDouble("longitude1");
//				double lg2 = rs.getDouble("longitude2");
//				double lt1 = rs.getDouble("latitude1");
//				double lt2 = rs.getDouble("latitude2");
//
//				//Display values
//				System.out.print("longitude1: " + lg1);
//				System.out.print(", longitude2: " + lg2);
//				System.out.print(", latitude1: " + lt1);
//				System.out.println(", latitude2: " + lt2);
//			}
//			rs.close();
//
//			System.out.println("Connexion effective !");
//		}catch(SQLException se){
//			//Handle errors for JDBC
//			se.printStackTrace();
//		}catch(Exception e){
//			//Handle errors for Class.forName
//			e.printStackTrace();
//		}finally{
//			//finally block used to close resources
//			try{
//				if(stmt!=null)
//					connexion.close();
//			}catch(SQLException se){
//			}// do nothing
//			try{
//				if(connexion!=null)
//					connexion.close();
//			}catch(SQLException se){
//				se.printStackTrace();
//			}//end finally try
//		}//end try
		
		final String CONSUMER_KEY = "79Le46VDtImQBogLVkr97dmQr";
		final String CONSUMER_SECRET = "7v6CkcUkcP7Ra9OiDOz84dKANkE9DrPIZbmtL2WCqvQdedDMtg";
 
		final String ACCESS_TOKEN = "950171118-poLCn3nOTIRtb83KJjLS4zA4VVhyR0QSqpl72nVI";
		final String ACCESS_TOKEN_SECRET = "tqzL8zmvE1VzYIDP5F7q3IUEOVOTm6k2vpTXN6Me4Y8Bt";
 
		//String message = "Statut de test envoy� via la biblioth�que #Twitter4j.";
// 
		AccessToken aToken;
// 
//		// Creation d'un objet Twitter
// 
		Twitter t = new TwitterFactory().getInstance();
 
		// Connexion OAuth (http://oauth.net/) au compte Twitter
 
		t.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		aToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		t.setOAuthAccessToken(aToken);
// 
//	    Query query = new Query("lezybeatz");
//	    QueryResult result = t.search(query);
//	    for (Status status : result.getTweets()) {
//	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
//	    }
		//Twitter twitter = TwitterFactory.getSingleton();
//		Paging page = new Paging ();
//		page.count(200);
//	    List<Status> statuses = t.getUserTimeline("bastokova",page);
//	    System.out.println(statuses.size());
//	    for (Status status : statuses) {
//	        System.out.println(status.getUser().getName() + ":" +
//	                           status.getText());
//	    }

	}

}