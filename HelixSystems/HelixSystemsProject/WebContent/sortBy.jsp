<%@ page import="classes.HelixSystemsManager"%>
<%@ page import="classes.HelixSystems"%>
<%@ page import="java.util.ArrayList"%>
<html>
<head>
<meta charset = "UTF-8">
<link rel="stylesheet" type="text/css" href="Style.css">
<title>Helix Systems</title>
</head>
<body>
<%
	HelixSystemsManager manager = new HelixSystemsManager();
	ArrayList<HelixSystems> currentList = new ArrayList<HelixSystems>(); 
	String sortBy = request.getParameter("sortBy");
	String filterBy = request.getParameter("filterBy");
	StringBuilder query = new StringBuilder("");
		
	if(filterBy.equals("none")){
		query.append("Select * FROM helixsystems");
	}else if(filterBy.equals("upToDate")){
		query.append("Select * FROM helixsystems WHERE update_packages = 0");
	}else if(filterBy.equals("needsUpdates")){
		query.append("Select * FROM helixsystems WHERE update_packages > 0");
	}else if(filterBy.equals("GPFSNodes")){
		query.append("Select * FROM helixsystems WHERE gpfs_configured = 1 OR gpfs_mounted = 1");
	}
	
	if(sortBy.equals("none")){
		query.append(";");
		currentList = manager.getListFromQuery(query.toString());
	}else if(sortBy.equals("lastCheckin_ascending")){
		query.append(" ORDER BY last_checkin");
		currentList = manager.getListFromQuery(query.toString());
	}else if(sortBy.equals("lastboot_ascending")){
		query.append(" ORDER BY last_boot");
		currentList = manager.getListFromQuery(query.toString());
	}else if(sortBy.equals("lastCheckin_descending")){
		query.append(" ORDER BY last_boot desc");
		currentList = manager.getListFromQuery(query.toString());
	}else if(sortBy.equals("lastboot_descending")){
		query.append(" ORDER BY last_boot desc");
		currentList = manager.getListFromQuery(query.toString());
	}
	
%>
	<h1>Helix Systems</h1>
	
	<table>
		<tr>
			<th colspan = "9">System Overview
				<form action = "sortBy.jsp">
					<label>Sort by</label>
						<select name = "sortBy">
								<option value = "none">none</option>
								<option value = "lastCheckin_ascending">last checkin</option>
								<option value = "lastboot_ascending">last boot</option>
								<option value = "lastCheckin_descending">last checkin descending</option>
								<option value = "lastboot_descending">last boot descending</option>
							</select>
					<label>Filter by</label>
						<select name = "filterBy">
							<option value = "none">none</option>
							<option value = "upToDate">up to date</option>
							<option value = "needsUpdates">needs updates</option>
							<option value = "GPFSNodes">running GPFS nodes</option>
						</select>
					<input type = "Submit" value = "Submit">
				</form></th>
				</tr>
		<tr class = "columnTitle">
			<td>Status</td>
			<td>Hostname</td>
			<td>Update Packages</td>
			<td>Operating System</td>
			<td>Kernel Version</td>
			<td>Machine Type</td>
			<td>Last Checkin</td>
			<td>Running GPFS nodes</td>
			<td>Person of Reference</td>
		</tr>
	<%for(int index = 0; index  < currentList.size(); index++){%>
		<tr>
			<td>Blank</td>
			<td><a href ="#"><%= currentList.get(index).getHostname() %></td>
			<td><%= currentList.get(index).getUpdate_packages() %></td>
			<td><%= currentList.get(index).getOs() %></td>
			<td><%= currentList.get(index).getKernel() %></td>
			<td><%= currentList.get(index).getHosttype() %></td>
			<td><%= currentList.get(index).getLast_checkin() %></td>
			<td><%= currentList.get(index).hasGPFSNodes() %></td>
			<td>Blank</td>
		</tr>
	<%}%>

</body>
</html>