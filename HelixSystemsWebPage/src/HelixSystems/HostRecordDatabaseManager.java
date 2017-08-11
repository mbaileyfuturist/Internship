package HelixSystems;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class HostRecordDatabaseManager  {
	
    public static void insertIntoTable(ArrayList<HostRecord> csvValues){
		try {
			Context initialContext = new InitialContext();
    		DataSource dataSource = (DataSource)
    		initialContext.lookup("java:comp/env/jdbc/helixmon");
    		Connection connection = dataSource.getConnection();	
    		String sql = "INSERT INTO helixsystems(hostname, os, kernel, ip_clusternet, ip_helixnet, repos, gpfs_configured, gpfs_mounted,"
					+ "update_packages, security_packages, last_boot, last_checkin, last_yum_update, hosttype, virt_guests, update_exception) values"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			for(int row = 0; row < csvValues.size(); row++ ){
				statement.setString(1, csvValues.get(row).getHostname());
				statement.setString(2, csvValues.get(row).getOs());
				statement.setString(3, csvValues.get(row).getKernel());
				statement.setString(4, csvValues.get(row).getIp_clusternet());
				statement.setString(5, csvValues.get(row).getIp_helixnet());
				statement.setString(6, csvValues.get(row).getRepos());
				statement.setInt(7, csvValues.get(row).getGpfs_configured());
				statement.setInt(8, csvValues.get(row).getGpfs_mounted());
				statement.setInt(9, csvValues.get(row).getUpdate_packages());
				statement.setInt(10, csvValues.get(row).getSecurity_packages());
				
				/*Convert the dates from a String to a date in the format yyyy-MM-dd HH:mm:ss
				  to properly insert into an SQL table.*/
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
				java.util.Date last_boot = null;
				java.util.Date last_checkin = null;
				java.util.Date last_yum_update = null;
				try {
					last_boot = dateFormat.parse(csvValues.get(row).getLast_boot());
					last_checkin = dateFormat.parse(csvValues.get(row).getLast_checkin());
					last_yum_update = dateFormat.parse(csvValues.get(row).getLast_yum_update());
					} catch (ParseException e) {
					System.out.println("Error: " + e.getMessage());
					}		
				statement.setTimestamp(11, new java.sql.Timestamp(last_boot.getTime()));
				statement.setTimestamp(12, new java.sql.Timestamp(last_checkin.getTime()));
				statement.setTimestamp(13, new java.sql.Timestamp(last_yum_update.getTime()));
				statement.setString(14, csvValues.get(row).getHosttype());
				statement.setString(15, csvValues.get(row).getVirt_guests());
				statement.setInt(16, csvValues.get(row).getUpdate_exception());
				statement.executeUpdate();
			}
		connection.close();
		} catch (SQLException | NamingException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
 
    /**
     * Attempts to establish a connection to the Helixmon database, then 
     * creates and returns a LinkedHashMap maping from a hostname to its 
     * corresponding hostRecord from the database.
     * @return hostToRecord: A LinkedHashMap maping from the hostname to the coresponding hostRecord.
     * @throws ClassNotFoundException
     */
    public static Map<String, HostRecord> getMapFromDatabase() throws ClassNotFoundException{
    	Map<String, HostRecord> hostToRecord = new LinkedHashMap<String, HostRecord>();
    	try{
    		Context initialContext = new InitialContext();
    		DataSource dataSource = (DataSource)
    		initialContext.lookup("java:comp/env/jdbc/helixmon");
    		Connection connection = dataSource.getConnection();	
    		String querry = "Select * FROM helixsystems";
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(querry);
			while(resultSet.next()){
				hostToRecord.put(resultSet.getString("hostname"), new HostRecord(resultSet.getString("hostname"), resultSet.getString("os"), resultSet.getString("kernel"),
					resultSet.getString("ip_clusternet"), resultSet.getString("ip_helixnet"), resultSet.getString("repos"),
				 	resultSet.getInt("gpfs_configured"), resultSet.getInt("gpfs_mounted"), resultSet.getInt("update_packages"),
					resultSet.getInt("security_packages"), resultSet.getString("last_boot"), resultSet.getString("last_checkin"), 
					resultSet.getString("last_yum_update"), resultSet.getString("hosttype"), resultSet.getString("virt_guests"), 
					resultSet.getInt("update_exception")));
			}
			connection.close();
			resultSet.close();
		}
		catch(SQLException | NamingException e){
			System.out.println("Error: " + e.getMessage());
		} 
    	return hostToRecord;
    }
    
    /**
     * This method will attempt to establish a connection to the Helixmon database,
     * then stores the hostrecords from the database into an arraylist.
     * @return hostRecords: An arraylist of the hostrecords from the Helixmon database.
     */
    public static List<HostRecord> getListFromDataBase(){
    	List<HostRecord> hostRecords = new ArrayList<HostRecord>();
    	try{
    		Context initialContext = new InitialContext();
    		DataSource dataSource = (DataSource)
    		initialContext.lookup("java:comp/env/jdbc/helixmon");
    		Connection connection = dataSource.getConnection();	
    		String querry = "Select * FROM helixsystems";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(querry);
			while(resultSet.next()){
				hostRecords.add(new HostRecord(resultSet.getString("hostname"), resultSet.getString("os"), resultSet.getString("kernel"),
					resultSet.getString("ip_clusternet"), resultSet.getString("ip_helixnet"), resultSet.getString("repos"),
				 	resultSet.getInt("gpfs_configured"), resultSet.getInt("gpfs_mounted"), resultSet.getInt("update_packages"),
					resultSet.getInt("security_packages"), resultSet.getString("last_boot"), resultSet.getString("last_checkin"), 
					resultSet.getString("last_yum_update"), resultSet.getString("hosttype"), resultSet.getString("virt_guests"), 
					resultSet.getInt("update_exception")));
			}
			connection.close();
			resultSet.close();
		}catch(SQLException | NamingException e){
			System.out.println("Error: " + e.getMessage());
		} 
		return hostRecords;
    }
    
    /**
     * This method will attempt to establish a connection to the Helixmon database and will 
     * send the querry passed in the parameter to get different results from  the database. 
     * then store the results into a LinkedHashMap maping from the hostname to the hostRecord.
     * @param query
     * @return
     * @throws ClassNotFoundException
     */
    public static Map<String, HostRecord> getMapFromQuery(String query) throws ClassNotFoundException{
    	Map<String, HostRecord> hostToElement = new LinkedHashMap<String, HostRecord>();
    	try{
    		Context initialContext = new InitialContext();
    		DataSource dataSource = (DataSource)
    		initialContext.lookup("java:comp/env/jdbc/helixmon");
    		Connection connection = dataSource.getConnection();	
    		Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				hostToElement.put(resultSet.getString("hostname"), new HostRecord(resultSet.getString("hostname"), resultSet.getString("os"), resultSet.getString("kernel"),
					resultSet.getString("ip_clusternet"), resultSet.getString("ip_helixnet"), resultSet.getString("repos"),
					resultSet.getInt("gpfs_configured"), resultSet.getInt("gpfs_mounted"), resultSet.getInt("update_packages"),
					resultSet.getInt("security_packages"), resultSet.getString("last_boot"), resultSet.getString("last_checkin"), 
					resultSet.getString("last_yum_update"), resultSet.getString("hosttype"), resultSet.getString("virt_guests"), 
					resultSet.getInt("update_exception")));
			}
			connection.close();
			resultSet.close();
		}catch(SQLException e){
			System.out.println("Error: " + e.getMessage());
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		} 
    	return hostToElement;
    }

    /**
     * This method will attempt to establish a connection to the Helixmon database and will 
     * send the querry passed in the parameter to get different hostRecords from  the database. 
     * then store the hostRecords into an ArrayList.
     * @param query: The sql querry that will be given to the database.
     * @return hostRecords: An ArrayList of hostrecords from the Helixmon database. 
     * @throws ClassNotFoundException
     */
    public static List<HostRecord> getListFromQuery(String query) throws ClassNotFoundException{
    	List<HostRecord> hostRecords= new ArrayList<HostRecord>();
    	try{
    		Context initialContext = new InitialContext();
    		DataSource dataSource = (DataSource)initialContext.lookup("java:comp/env/jdbc/helixmon");
    		Connection connection = dataSource.getConnection();	
    		Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				hostRecords.add(new HostRecord(resultSet.getString("hostname"), resultSet.getString("os"), resultSet.getString("kernel"),
							resultSet.getString("ip_clusternet"), resultSet.getString("ip_helixnet"), resultSet.getString("repos"),
							resultSet.getInt("gpfs_configured"), resultSet.getInt("gpfs_mounted"), resultSet.getInt("update_packages"),
							resultSet.getInt("security_packages"), resultSet.getString("last_boot"), resultSet.getString("last_checkin"), 
							resultSet.getString("last_yum_update"), resultSet.getString("hosttype"), resultSet.getString("virt_guests"), 
							resultSet.getInt("update_exception")));
			}
			connection.close();
			resultSet.close();
		}catch(SQLException | NamingException e){
			System.out.println("Error: " + e.getMessage());
		} 
    	return hostRecords;
    }
}