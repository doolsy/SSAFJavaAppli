package JavaAoo;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {

	public static void main(String[] argv) {
		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		Connection connexion = null;
		Statement stmt = null;

		try {

			connexion = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/extractstweet", "postgres",
					"s");
			stmt = connexion.createStatement();
			String sql = "SELECT longitude1, longitude2, latitude1, latitude2 FROM \"Bounding_box\" WHERE title = 'Paris'";
			//sql = "SELECT * FROM \"User_app\"";
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				double lg1 = rs.getDouble("longitude1");
				double lg2 = rs.getDouble("longitude2");
				double lt1 = rs.getDouble("latitude1");
				double lt2 = rs.getDouble("latitude2");

				//Display values
				System.out.print("longitude1: " + lg1);
				System.out.print(", longitude2: " + lg2);
				System.out.print(", latitude1: " + lt1);
				System.out.println(", latitude2: " + lt2);
			}
			rs.close();

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

}