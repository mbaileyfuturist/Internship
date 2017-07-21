package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConfigDBManager {
	private ConfigDBHosts configDBHosts = new ConfigDBHosts();
	private ConfigDBInterfaces configDBInterfaces = new ConfigDBInterfaces();
	
	  public ArrayList<ConfigDBHosts> readFromConfigDBHosts(String fileName) {
			
		    ArrayList<ConfigDBHosts> rows = new ArrayList<>();	
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
						
				configDBHosts.setHostName(columns[0]);
				configDBHosts.setPhase(columns[1]);
				configDBHosts.setDescription(columns[2]);
				configDBHosts.setLastModified(columns[3]);
				configDBHosts.setModifiedBy(columns[4]);
				configDBHosts.setInstalled(columns[5]);
				configDBHosts.setUpdated(columns[6]);
				configDBHosts.setSerialNumber(columns[7]);
				configDBHosts.setLocation(columns[8]);
				configDBHosts.setHardware(columns[9]);
				configDBHosts.setAnsibleGroup(columns[10].replace("\"", ""));
		
				rows.add(new ConfigDBHosts(configDBHosts.getHostName(), configDBHosts.getPhase(), configDBHosts.getDescription(), configDBHosts.getLastModified(),
						configDBHosts.getModifiedBy(), configDBHosts.getInstalled(), configDBHosts.getUpdated(), configDBHosts.getSerialNumber(), configDBHosts.getLocation(),
						configDBHosts.getHardware(), configDBHosts.getAnsibleGroup()));
				}
				readFromCSV.close();
				return rows;
		}
	  
	  public ArrayList<ConfigDBInterfaces> readFromConfigDBInterfaces(String fileName) {
		  	InetAddress mac = null;
		  	Inet4Address Ipv4 = null;
		  	Inet6Address Ipv6 = null;
		  	ArrayList<ConfigDBInterfaces> rows = new ArrayList<>();	
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
						
				configDBInterfaces.setInterfaceID(Integer.parseInt(columns[0].replaceAll("\"", "")));
				configDBInterfaces.setHostName(columns[1]);
				configDBInterfaces.setPrivateName(columns[2]);
				configDBInterfaces.setPrimary(Integer.parseInt(columns[3]));
				configDBInterfaces.setInterface(columns[4]);
				configDBInterfaces.setDesignation(columns[5]);
				try {
					mac = InetAddress.getByName(columns[6]);
					Ipv4 = (Inet4Address) InetAddress.getByName(columns[7]);
					Ipv6 = (Inet6Address) InetAddress.getByName(columns[8]);
				} catch (UnknownHostException e) {
					System.out.println("UnkownHostException: " + e.getMessage());
				}
				configDBInterfaces.setMac(mac);
				configDBInterfaces.setIpv4(Ipv4);
				configDBInterfaces.setIpv6(Ipv6);
		
				rows.add(new ConfigDBInterfaces(configDBInterfaces.getInterfaceID(), configDBInterfaces.getHostName(), configDBInterfaces.getPrivateName(), 
						configDBInterfaces.getPrimary(), configDBInterfaces.getInterface(), configDBInterfaces.getDesignation(), configDBInterfaces.getMac(),
						configDBInterfaces.getIpv4(), configDBInterfaces.getIpv6()));
				}
				readFromCSV.close();
				return rows;
		}
	  
	  public void insertIntoHostsTable(ArrayList<ConfigDBHosts> csvValues){
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false", "root", "mikespasword123$");
				String sql = "INSERT INTO hosts(hostname, phase, description, last_modified, modified_by, installed, updated, serialnumber, location, hardware,"
						+ "ansible_group) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				for(int row = 0; row < csvValues.size(); row++ ){
					statement.setString(1, csvValues.get(row).getHostName());
					statement.setString(2, csvValues.get(row).getPhase());
					statement.setString(3, csvValues.get(row).getDescription());
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date getLastModified = null;
					java.util.Date installed = null;
					java.util.Date updated = null;
					try {
						getLastModified = dateFormat.parse(csvValues.get(row).getLastModified());
						installed = dateFormat.parse(csvValues.get(row).getInstalled());
						updated = dateFormat.parse(csvValues.get(row).getUpdated());
						
						statement.setTimestamp(4, new java.sql.Timestamp(getLastModified.getTime()));
						statement.setString(5, csvValues.get(row).getModifiedBy());
						statement.setTimestamp(6, new java.sql.Timestamp(installed.getTime()));
						statement.setTimestamp(7, new java.sql.Timestamp(updated.getTime()));
						} catch (ParseException e) {
						System.out.println("Error: " + e.getMessage());
						}	
				
				statement.setString(8, csvValues.get(row).getSerialNumber());
				statement.setString(9, csvValues.get(row).getLocation());
				if(csvValues.get(row).getHardware().equals("")){
					csvValues.get(row).getHardware().replace(csvValues.get(row).getHardware(), "None");
				}else{
				statement.setString(10, csvValues.get(row).getHardware());
				}
				if(csvValues.get(row).getAnsibleGroup().equals("NULL")){
					statement.setString(11, csvValues.get(row).getAnsibleGroup().replace(csvValues.get(row).getAnsibleGroup(), ""));
				}else{
				statement.setString(11, csvValues.get(row).getAnsibleGroup());
				}
				statement.executeUpdate();
					}
				connection.close();
			}catch (SQLException e) {
				System.out.println("SQException: " + e.getMessage());
				e.printStackTrace();
			}
		}
	  
	  public void insertIntoInterfacesTable(ArrayList<ConfigDBInterfaces> csvValues){
		  try {
				Context initialContext = new InitialContext();
	    		DataSource dataSource = (DataSource)
	    		initialContext.lookup("java:comp/env/jdbc/configdb");
	    		Connection connection = dataSource.getConnection();	
	    		
				String sql = "INSERT INTO interfaces(interface_id, hostname, private_name, primary, interface, designation, mac, ipv4, ipv6"
						+ "ansible_group) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				for(int row = 0; row < csvValues.size(); row++ ){
					statement.setInt(1, csvValues.get(row).getInterfaceID());
					statement.setString(2, csvValues.get(row).getHostName());
					statement.setString(3, csvValues.get(row).getPrivateName());					
					statement.setInt(4, csvValues.get(row).getPrimary());
					statement.setString(5, csvValues.get(row).getInterface());
					statement.setString(6, csvValues.get(row).getDesignation());
					statement.setString(7, csvValues.get(row).getMac().toString());
					statement.setString(8, csvValues.get(row).getIpv4().toString());
					statement.setString(9, csvValues.get(row).getIpv6().toString());
					} 
					statement.executeUpdate();
				connection.close();
			} catch (SQLException e) {
				System.out.println("SQException: " + e.getMessage());
			}catch(NamingException e){
				System.out.println("NamingException: " + e.getMessage());
			}
	  }
}
