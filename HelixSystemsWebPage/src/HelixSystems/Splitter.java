package HelixSystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Splitter {

	private HostRecord helixSystems = new HostRecord();
	
	public ArrayList<HostRecord> readFromCSV(String fileName) {

    	ArrayList<HostRecord> listFromCSV = new ArrayList<>();
    	String row = "";
		String[] columns;
		
		File cvsFile = new File(fileName);
		Scanner readFromCSV = null;
		try {
			readFromCSV = new Scanner(cvsFile);
		}catch (FileNotFoundException e) {
			System.out.println("File Error: " + e.getMessage());
		}
			
		readFromCSV.nextLine();
		while(readFromCSV.hasNext()){
			row = readFromCSV.nextLine();
			columns = row.split("\",\"");		
			helixSystems.setHostname(columns[0].replace("\"", ""));
			helixSystems.setOs(columns[1]);
			helixSystems.setKernel(columns[2]);
			helixSystems.setIp_clusternet(columns[3]);
			helixSystems.setIp_helixnet(columns[4]);
			helixSystems.setRepos(columns[5]);
			helixSystems.setGpfs_configured(Integer.parseInt(columns[6]));
			helixSystems.setGpfs_mounted(Integer.parseInt(columns[7]));
			helixSystems.setUpdate_packages(Integer.parseInt(columns[8]));
			helixSystems.setSecurity_packages(Integer.parseInt(columns[9]));
			helixSystems.setLast_boot(columns[10]);
			helixSystems.setLast_checkin(columns[11]);
			helixSystems.setLast_yum_update(columns[12]);
			helixSystems.setHosttype(columns[13]);
			helixSystems.setVirt_guests(columns[14]);
			helixSystems.setUpdate_exception(Integer.parseInt(columns[15].replace("\"", "")));
			
			listFromCSV.add(new HostRecord(helixSystems.getHostname(), helixSystems.getOs(), helixSystems.getKernel(), helixSystems.getIp_clusternet(), 
				helixSystems.getIp_helixnet(), helixSystems.getRepos(), helixSystems.getGpfs_configured(), helixSystems.getGpfs_mounted(),
				helixSystems.getUpdate_packages(), helixSystems.getSecurity_packages(), helixSystems.getLast_boot(), helixSystems.getLast_checkin(),
				helixSystems.getLast_yum_update(), helixSystems.getHosttype(), helixSystems.getVirt_guests(), helixSystems.getUpdate_exception()));
		}
		readFromCSV.close();
		return listFromCSV;
	}

	public static ArrayList<String> getRepos(String repos){
		ArrayList<String> reposList = new ArrayList<>();
		String values[] = repos.split(", ");
		for(int index = 0; index <= values.length-1; index++){
			reposList.add(values[index]);
		}
		return reposList;
	}
	
	public static void main(String[] args){
		Map<String, String> guestToStatus = new HashMap<String, String>();
		String guests = "{\"hmail\": \"running\", \"hma\": \"running\", \"helixdev\": \"running\"}";
		guestToStatus = getGuests(guests);
		for(String values: guestToStatus.keySet()){
			System.out.println(values);
		}
	}
	
	public static Map<String, String> getGuests(String guestsStr){
	Map<String, String> guestToStatus = new HashMap<String, String>();
	if(!guestsStr.equals("{}")){
		String bracketsRemoved = chomp(guestsStr, '{', '}');
		String[] virtualGuests = bracketsRemoved.split(",\\s");
		String[] guestElements;
 		for(String virtualGuest: virtualGuests){
			guestElements = virtualGuest.split(":\\s");
			String guestName = chomp(guestElements[0], '"', '"');
			String guestStatus = chomp(guestElements[1], '"', '"');
			guestToStatus.put(guestName, guestStatus);
		}	
	}
 		return guestToStatus;
	}
	
	/**
	 * Takes off the first and last characters of the given string.
	 * @param str: string that will be manipulated.
	 * @param beginingChar: First character in the string that will be removed.
	 * @param endingChar: Second character in the string that will be removed.
	 * @return returns the new string.
	 */
	public static String chomp(String str, char beginingChar, char endingChar){
		StringBuilder newString = new StringBuilder(str);
		if(newString.charAt(newString.length() - 1) == endingChar){
			newString.deleteCharAt(newString.length() - 1);
		}
		if(newString.charAt(0) == beginingChar){
			newString.deleteCharAt(0);
		}
		return newString.toString();
	}	
}
