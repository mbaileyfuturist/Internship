package classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;


public class HelixSystemsManager{
	private HelixSystems helixsystems = new HelixSystems();
	
	/**
	 * This method is responsible for reading a CSV file and returning the 
	 * contents of the file in an ArrayList<HelilxSystems>.
	 * @param fileName
	 * @return an ArrayList<HelixSystems> containing the contents of the CSV file.
	 */ 
    public ArrayList<HelixSystems> readFromCSV(String fileName) {
	
	    ArrayList<HelixSystems> rows = new ArrayList<>();	
		File cvsFile = new File(fileName);
		Scanner readFromCSV = null;
		try {
			readFromCSV = new Scanner(cvsFile);
		}catch (FileNotFoundException e) {
			System.out.println("File Error: " + e.getMessage());
		}
			
		String row = "";
		String[] columns;
		
		//Read one line initially to move the reader to the second row.
		readFromCSV.nextLine();
		while(readFromCSV.hasNext()){
			row = readFromCSV.nextLine();
			columns = row.split("\",\"");
					
			helixsystems.setHostname(columns[0]);
			helixsystems.setOs(columns[1]);
			helixsystems.setKernel(columns[2]);
			helixsystems.setIp_clusternet(columns[3]);
			helixsystems.setIp_helixnet(columns[4]);
			helixsystems.setRepos(columns[5]);
			helixsystems.setGpfs_configured(Integer.parseInt(columns[6]));
			helixsystems.setGpfs_mounted(Integer.parseInt(columns[7]));
			helixsystems.setUpdate_packages(Integer.parseInt(columns[8]));
			helixsystems.setSecurity_packages(Integer.parseInt(columns[9]));
			helixsystems.setLast_boot(columns[10]);
			helixsystems.setLast_checkin(columns[11]);
			helixsystems.setLast_yum_update(columns[12]);
			helixsystems.setHosttype(columns[13]);
			helixsystems.setVirt_guests(columns[14]);
			helixsystems.setUpdate_exception(Integer.parseInt(columns[15].replace("\"", "")));
	
			rows.add(new HelixSystems(helixsystems.getHostname(), helixsystems.getOs(), helixsystems.getKernel(), helixsystems.getIp_clusternet(), 
				helixsystems.getIp_helixnet(), helixsystems.getRepos(), helixsystems.getGpfs_configured(), helixsystems.getGpfs_mounted(),
				helixsystems.getUpdate_packages(), helixsystems.getSecurity_packages(), helixsystems.getLast_boot(), helixsystems.getLast_checkin(),
				helixsystems.getLast_yum_update(), helixsystems.getHosttype(), helixsystems.getVirt_guests(), helixsystems.getUpdate_exception()));
			}
			readFromCSV.close();
			return rows;
	}
	
	/**
	 * This method is responsible for storing the contents of an ArrayList<HelixSystems> 
	 * into a database.
	 * @param csvValues
	 * @param url
	 * @param username
	 * @param password
	 * @param x 
	 * @throws ParseException 
	 */
    public void insertIntoTable(ArrayList<HelixSystems> csvValues, String url, String username, String password){
		try {
		Connection connection = DriverManager.getConnection(url, username, password);	
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
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
 
    /**
     * This method is resopnsible for reading data from a database and storing it into an ArrayList<HelixSystems>
     * @return ArrayList<HelixSystems>
     * @throws ClassNotFoundException 
     */
    public ArrayList<HelixSystems> readFromDatabase() throws ClassNotFoundException{
		ArrayList<HelixSystems> tableContents = new ArrayList<HelixSystems>();
    	try{
    		Context initialContext = new InitialContext();
    		DataSource dataSource = (DataSource)
    		  initialContext.lookup("java:comp/env/jdbc/helixmon");

    		Connection connection = dataSource.getConnection();	
			String querry = "Select * FROM helixsystems";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(querry);
			
			while(resultSet.next()){
				tableContents.add(new HelixSystems(resultSet.getString("hostname"), resultSet.getString("os"), resultSet.getString("kernel"),
							resultSet.getString("ip_clusternet"), resultSet.getString("ip_helixnet"), resultSet.getString("repos"),
							resultSet.getInt("gpfs_configured"), resultSet.getInt("gpfs_mounted"), resultSet.getInt("update_packages"),
							resultSet.getInt("security_packages"), resultSet.getString("last_boot"), resultSet.getString("last_checkin"), 
							resultSet.getString("last_yum_update"), resultSet.getString("hosttype"), resultSet.getString("virt_guests"), 
							resultSet.getInt("update_exception")));
			}
			connection.close();
			resultSet.close();
		}
		catch(SQLException e){
			System.out.println("Error: " + e.getMessage());
		}catch(NamingException e){
    		System.out.println("Error: " + e.getMessage());
		}
    	return tableContents;
    }
    
    /**
     * This method is responsible for executing a given querry to the mySQL database. 
     * @param query
     * @return ArrayList<HelixSystems>
     * @throws ClassNotFoundException 
     */
    public ArrayList<HelixSystems> getListFromQuery(String query) throws ClassNotFoundException{
    	ArrayList<HelixSystems> list = new ArrayList<HelixSystems>();
    	try{
    		Context initialContext = new InitialContext();
    		Context envCtx = (Context) initialContext.lookup("java:comp/env");
    		DataSource dataSource = (DataSource)
    		  envCtx.lookup("jdbc/helixmon");

    		Connection connection = dataSource.getConnection();		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				list.add(new HelixSystems(resultSet.getString("hostname"), resultSet.getString("os"), resultSet.getString("kernel"),
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
		}catch(NamingException e){
			System.out.println("Error: " + e.getMessage());
		}
    	return list;
    }

    public Map<String, HelixSystems> getMapFromList(ArrayList<HelixSystems> helixList){
    	Map<String, HelixSystems> hostToRecord = new LinkedHashMap<String, HelixSystems>();
    	for(HelixSystems element: helixList){
    		hostToRecord.put(element.getHostname(), element);
    	}
    	return hostToRecord;
    }
}
