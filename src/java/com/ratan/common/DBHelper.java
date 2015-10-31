package com.ratan.common;

/**
 * 
 * @author Sanjay.Ratan
 * 
 */
 
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.lang.ClassNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBHelper
{
	/*
	 * getMYSQLConnection : gets database connection based on the provided 
	 * database properties in database.properties and returns the connection object
	 */
	public Connection getMYSQLConnection()		
 	{
		Connection conn=null;				
		InputStream inputStream = null;
		Properties prop = new Properties();
		String propFileName = "database.properties";
		try 
		{
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null)
				prop.load(inputStream);
			else 
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");			
		} 
		catch (Exception e) 
		{
			System.out.println("Exception: " + e);
		} 
		finally 
		{
			try 
			{
				if(inputStream != null) 
					inputStream.close();
			} 
			catch (IOException ioe)
			{
				System.out.println("Exception: " + ioe);
			}
		}
		
		String strDriverName = prop.getProperty("mysql_driver_name"), strConnectionString = prop.getProperty("mysql_connection_string"),				
		strDatabaseName = prop.getProperty("mysql_db_name"), strUserName = prop.getProperty("mysql_db_username"),
		strPassword = "";				
		
		if(prop.getProperty("mysql_db_password") != null && prop.getProperty("mysql_db_password").trim() != "")
			strPassword = "&password="+prop.getProperty("mysql_db_password");
		
		String url=strConnectionString+"/"+strDatabaseName+"?user="+strUserName+strPassword;		
		try
  		{
			Class.forName(strDriverName);
	  		conn = DriverManager.getConnection(url);		  			  		 			  	
        }        
        catch(SQLException sq)
  		{
	  		sq.printStackTrace();
	  		System.out.println("MySQL Exception Occured! - "+sq.getMessage());
  		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception Occured! - "+e.getMessage());
		}		
		return conn; 
	}
	
	/*
	 * getResult : gets the results from database and returns a vector of requested values
	 */
 	public Vector getResult(String name)
 	{	 	
	 	ResultSet rst=null;
		Statement stmt=null;
		Connection conn=null;
		Vector vResult=new Vector();
		
		DBHelper dbh = new DBHelper();	 	
		
	 	conn = dbh.getMYSQLConnection(); // for MySQL	
	 	
	 	try
	 	{		 	
		 	stmt=conn.createStatement();	  		
		
		 	rst=stmt.executeQuery("select * from tblemployee where empname like '%"+name+"%'"); // for MySQL				
									
			while(rst.next())
			{				
				vResult.add(rst.getString(1));
				vResult.add(rst.getString(2));								
				vResult.add(rst.getString(3));
				vResult.add(rst.getString(4));				
			}
		}
		catch(SQLException sq)
  		{
	  		sq.printStackTrace();
	  		System.out.println("SQL Exception Occured! - "+sq.getMessage());
  		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception Occured! - "+e.getMessage());
		}
		finally
		{
			try
			{	
				rst.close();			
				stmt.close();
				conn.close();	 	
			}
			catch(Exception ee)
			{
				System.out.println("Problem in Closing connection!"+ee.getMessage());	
			}				
		}
		return vResult;		
 	} 		
}
