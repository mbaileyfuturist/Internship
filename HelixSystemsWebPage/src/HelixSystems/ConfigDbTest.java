package HelixSystems;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gov.nih.hpc.configdb.ConfigDb;
import gov.nih.hpc.configdb.ConfigDbClientFactory;

public class ConfigDbTest {

	public static final ConfigDbClientFactory clientFactory = new ConfigDbClientFactory();
	
	
	public static void main(String[] args){

	}
	
	/**
	 * Will attempt to establish a connection with the configdb database and use the gov.nih.hpc.config 
	 * API to exctract the set of Helixnet Addresses for the coresponding hostname passed into the parameter. 
	 * @param helixHostname: The hostname.
	 * @return Set<InetAddresses>
	 */
	public static Set<InetAddress>  hostToHelixnet(String helixHostname){
		
		Set<InetAddress> helixnetAddresses = new HashSet<InetAddress>();
		
		clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		try {
			ConfigDb cdb = clientFactory.getConfigDbClient();
			String realHostname = "";
			for (String configDBHostname: cdb.getHostNames()){ 
				
				if(helixHostname.contains(".")){
						String[] hostnameElements = helixHostname.split("\\.");
						realHostname = hostnameElements[0];
					}else{
						realHostname = helixHostname;
					}
				
					if(configDBHostname.equals(realHostname)){
						helixnetAddresses = cdb.getHelixnetAddresses(configDBHostname);
					}
				}
		
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException e) {
			e.printStackTrace();
		}
		return helixnetAddresses;
	}
	
	/**
	 * Will attempt to establish a connection with the configdb database and use the gov.nih.hpc.config 
	 * API to exctract the set of Clusternet Addresses for the coresponding hostname passed into the parameter. 
	 * @param helixHostname
	 * @return Set<InetAddresses>
	 */
	public static Set<InetAddress>  hostToClusternet(String helixHostname){
		Set<InetAddress> clusternetAddresses = new HashSet<InetAddress>();
		
		clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		try {
			ConfigDb cdb = clientFactory.getConfigDbClient();
			String realHostname = "";
			for (String configDBHostname: cdb.getHostNames()){ 
				if(helixHostname.contains(".")){
						String[] hostnameElements = helixHostname.split("\\.");
						realHostname = hostnameElements[0];
					}else{
						realHostname = helixHostname;
					}
				
					if(configDBHostname.equals(realHostname)){
						clusternetAddresses = cdb.getClusternetAddresses(configDBHostname);
						Collection<Inet6Address> ipv6Addresses = getIpv6Addresses(configDBHostname);
					}
				}
		
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException e) {
			e.printStackTrace();
		}
		return clusternetAddresses;
	}

	/**
	 * Will attempt to establish a connection with the configdb database and use the gov.nih.hpc.config 
	 * API to exctract the set of Inet6Addresses Addresses for the coresponding hostname passed into the parameter. 
	 * @param helixHostname: 
	 * @return Ipv6Addresses: A collection<Inet6Address> of ipv6 addresses for the corresponding hostname.
	 */
    public static Collection<Inet6Address> getIpv6Addresses(String helixHostname){
    	Collection<Inet6Address> Ipv6Addresses = new HashSet<Inet6Address>();
		
		clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		
			try {
				ConfigDb cdb = clientFactory.getConfigDbClient();
				String realHostname;
				for(gov.nih.hpc.configdb.HostRecord hostRecord: cdb.getHostRecords()){ 
						if(helixHostname.contains(".")){
								String[] hostnameElements = helixHostname.split("\\.");
								realHostname = hostnameElements[0];
							}else{
								realHostname = helixHostname;
							}
						
							if(hostRecord.getHostname().equals(realHostname)){
								Ipv6Addresses = hostRecord.getIpv6Addresses();
							}
						}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
				e.printStackTrace();
			}
			return Ipv6Addresses;
    }
    
    public static String getDescription(String helixHost){
    	String description = "";
    	
    	clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		
			try {
				ConfigDb cdb = clientFactory.getConfigDbClient();
				
				String realHostname;
				for(gov.nih.hpc.configdb.HostRecord hostRecord: cdb.getHostRecords()){
						if(helixHost.contains(".")){
							String[] hostnameElements = helixHost.split("\\.");
							realHostname = hostnameElements[0];
						}else{
							realHostname = helixHost;
						}
					
						if(hostRecord.getHostname().equals(realHostname)){
							description = cdb.getDescription(hostRecord);
							}
					}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
				e.printStackTrace();
			}
    	return description;
    }
    
    public static String getPerosonOfReference(String helixHost){
    	String person = "";
    	
    	clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		
			try {
				ConfigDb cdb = clientFactory.getConfigDbClient();
				
				String realHostname;
				for(gov.nih.hpc.configdb.HostRecord hostRecord: cdb.getHostRecords()){
						if(helixHost.contains(".")){
							String[] hostnameElements = helixHost.split("\\.");
							realHostname = hostnameElements[0];
						}else{
							realHostname = helixHost;
						}
					
						if(hostRecord.getHostname().equals(realHostname)){
							person = cdb.getResponsiblePerson(hostRecord);
						}
					}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
				e.printStackTrace();
			}
    	return person;
    }
}
