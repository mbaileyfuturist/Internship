package HelixSystems;
import java.util.List;

public class SortFilter {

	public static StringBuilder filterBy(String filterBy, StringBuilder query){
		if(filterBy.equals("none")){
			query.append("Select * FROM helixsystems");
		}else if(filterBy.equals("upToDate")){
			query.append("Select * FROM helixsystems WHERE update_packages = 0");
		}else if(filterBy.equals("needsUpdates")){
			query.append("Select * FROM helixsystems WHERE update_packages > 0");
		}else if(filterBy.equals("GPFSNodes")){
			query.append("Select * FROM helixsystems WHERE gpfs_configured = 1 OR gpfs_mounted = 1");
		}
		return query;
	}
	
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
