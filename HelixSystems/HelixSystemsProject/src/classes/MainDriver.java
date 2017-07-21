package classes;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.apache.jasper.tagplugins.jstl.core.Set;

import gov.nih.hpc.configdb.ConfigDb;
import gov.nih.hpc.configdb.ConfigDbClientFactory;
import gov.nih.hpc.configdb.HostRecord;

public class MainDriver {
	
	public static final ConfigDbClientFactory clientFactory = new ConfigDbClientFactory();
	
	public static void main(String[] args) {
		clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		System.out.println("After clientFactory configuration");
		try {
			ConfigDb cdb = clientFactory.getConfigDbClient();
			for(String hostName: cdb.getHostNames()){
				System.out.println(hostName);
			}
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException e) {
			e.printStackTrace();
		}
	}
}
