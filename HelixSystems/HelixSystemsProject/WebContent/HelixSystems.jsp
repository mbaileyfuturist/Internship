<%@ page import="classes.HelixSystemsManager"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="classes.HelixSystems"%>
<%@ page import="java.sql.*" %>
<%! String hostname; %>  

<html>
	<head>
		<meta charset = "UTF-8">
		<link rel="stylesheet" type="text/css" href="Style.css">
		<title>Helix System</title>
		<script type="text/javascript">
	    function toggle_visibility(id) {
	    	var t = document.getElementById("table"), 
	        r = t.getElementsByTagName("tr")[2],
	        d = r.getElementsByTagName("td")[1].textContent;
	    	document.getElementById("name").innerHTML = d; 
	    	
	       var e = document.getElementById(id);
	       if(e.style.display == 'block')
	          e.style.display = 'none';
	       else
	          e.style.display = 'block';
	    }
</script>
	</head>
	
	<body>
		<%String value = request.getParameter("sortBy");
		HelixSystemsManager manager = new HelixSystemsManager();
		ArrayList<HelixSystems> initialList = new ArrayList<HelixSystems>(); 
		Map<String, HelixSystems> hostToRecord = new LinkedHashMap<String, HelixSystems>();
		initialList = manager.readFromDatabase();
		hostToRecord = manager.getMapFromList(initialList);
		%>
		
		<h1>Helix Systems</h1>	
		
			<table id = "table">
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
			<%for(Map.Entry<String, HelixSystems> entry : hostToRecord.entrySet()){%>
				<tr>
					<td>Blank</td>
					<td><a href ="javascript:void(0)" onclick="toggle_visibility('popup-box');"><%= entry.getValue().getHostname() %></a></td>
					<td><%= entry.getValue().getUpdate_packages() %></td>
					<td><%= entry.getValue().getOs() %></td>
					<td><%= entry.getValue().getKernel() %></td>
					<td><%= entry.getValue().getHosttype() %></td>
					<td><%= entry.getValue().getLast_checkin() %></td>
					<td><%= entry.getValue().hasGPFSNodes() %></td>
					<td>Blank</td>
				</tr>	
			<%}%>
			</table>
			<!--Popup container-->
			<div id="popup-box" class="popup-position">
				<div id="popup-wrapper">
					<div id="popup-container" >
					<%hostname = "badmin";%>
					<h3><p id="name"></p></h3>
					<p>GPFS Configured: <%= hostToRecord.get(hostname).getGpfs_configured() %></p>
					<p>GPFS Mounted: <%= hostToRecord.get(hostname).getGpfs_mounted() %></p>
					<p>IP Clusternet:<%=hostToRecord.get(hostname).getIp_clusternet() %> </p>
					<p>IP Helixnet:<%=hostToRecord.get(hostname).getIp_helixnet() %> </p>	
					<p>Repos: <%=hostToRecord.get(hostname).getRepos() %></p>
					<p><a href="javascript:void(0)" onclick="toggle_visibility('popup-box');">Close popup box.</a></p>
					</div> <!--Popup-container end-->
				</div> <!--Popup-wrapper end-->
			</div> <!--Popup-box end-->
			 
			
	</body>
</html>