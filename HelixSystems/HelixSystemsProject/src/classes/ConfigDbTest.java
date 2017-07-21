package classes;
import gov.nih.hpc.configdb.ConfigDbClientFactory;
import gov.nih.hpc.configdb.ConfigDb;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Collection;

public class ConfigDbTest {

	public static final ConfigDbClientFactory clientFactory = new ConfigDbClientFactory();
    
	
	public ConfigDbTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		clientFactory.setUrl("jdbc:mysql://localhost:3306/configdb?autoReconnect=true&useSSL=false");
		clientFactory.setUser("root");
		clientFactory.setPassword("mikespasword123$");
		System.out.println("After clientFactory configuration");
		try {
			ConfigDb cdb = clientFactory.getConfigDbClient();
			for (String hostname: cdb.getHostNames()){
				System.out.println(hostname);
			}
			System.out.println("HelixNetAddresses for Helix:");
			Collection<InetAddress> addrs = cdb.getClusternetAddresses("cn1134");
			for (InetAddress inet: addrs){
				System.out.println(inet.toString());
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | SQLException e) {
			e.printStackTrace();
		}
	}

}
