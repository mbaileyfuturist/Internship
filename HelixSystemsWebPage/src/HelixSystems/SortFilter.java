package HelixSystems;
import java.util.List;

public class SortFilter {

	/**
	 * Will filter the results of the table in the Helixmon database. this method
	 * can either filter hosts that are up to date or not up to date or where GPFS 
	 * the hastname has GPFS nodes running.
	 * @param filterBy
	 * @param query
	 * @return
	 */
	public static StringBuilder filterBy(String filterBy, StringBuilder query){
		if(filterBy.equals("none")){
			query.append("Select * FROM helixsystems");
		}else if(filterBy.equals("upToDate")){
			query.append("Select * FROM helixsystems WHERE update_packages = 0");
		}else if(filterBy.equals("needsUpdates")){
			query.append("Select * FROM helixsystems WHERE update_packages > 0");
		}else if(filterBy.equals("GPFSNodes")){
			query.append("Select * FROM helixsystems WHERE gpfs_configured = 1 OR gpfs_mounted = 1");
		}else if(filterBy.equals("physical")){
			query.append("Select * FROM helixsystems WHERE hosttype ='physical'");
		}else if(filterBy.equals("virtual")){
			query.append("Select * FROM helixsystems WHERE hosttype='virtual'");
		}else if(filterBy.equals("hypervisor")){
			query.append("Select * FROM helixsystems WHERE hosttype='hypervisor'");
		}
		return query;
	}
	
	/**
	 * Will sort the results of the table in the Helixmon database, based on
	 * the hostnames time of last checkin acending/descending order, time of last boot,
	 * ascending/descending order. 
	 * @param sortBy
	 * @param query
	 * @param currentList
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<HostRecord> sortBy(String sortBy, StringBuilder query, List<HostRecord> currentList) throws ClassNotFoundException{
		if(sortBy.equals("none")){
			query.append(";");
			currentList = HostRecordDatabaseManager.getListFromQuery(query.toString());
		}else if(sortBy.equals("lastCheckin_ascending")){
			query.append(" ORDER BY last_checkin");
			currentList = HostRecordDatabaseManager.getListFromQuery(query.toString());
		}else if(sortBy.equals("lastboot_ascending")){
			query.append(" ORDER BY last_boot");
			currentList = HostRecordDatabaseManager.getListFromQuery(query.toString());
		}else if(sortBy.equals("lastCheckin_descending")){
			query.append(" ORDER BY last_boot desc");
			currentList = HostRecordDatabaseManager.getListFromQuery(query.toString());
		}else if(sortBy.equals("lastboot_descending")){
			query.append(" ORDER BY last_boot desc");
			currentList = HostRecordDatabaseManager.getListFromQuery(query.toString());
		}
		return currentList;
	}
}