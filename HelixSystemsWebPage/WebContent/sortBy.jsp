<%@ page import="HelixSystems.HostRecordDatabaseManager" %>
<%@ page import="HelixSystems.SortFilter" %>
<%@ page import="HelixSystems.ReportTable" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="HelixSystems.HostRecord" %>
<%@ page import="java.util.Iterator" %>
<html>
	<head>
	<meta charset = "UTF-8">
	<link rel="stylesheet" type="text/css" href="Style.css">
	<title>Helix Systems</title>
	<script src="functionality.js"></script>
	</head>
	
	<body>
		<% List<HostRecord> currentList = new ArrayList<HostRecord>();%> 
		<% String sortBy = request.getParameter("sortBy");%>
		<% String filterBy = request.getParameter("filterBy");%>
		<% StringBuilder query = new StringBuilder(""); %>
		<% query = SortFilter.filterBy(filterBy, query);%>
		<% currentList = SortFilter.sortBy(sortBy, query, currentList);%>
		<%= ReportTable.key()%>
		<%= ReportTable.header()%>
		<%= ReportTable.columnNames()%>
		<%= ReportTable.fullTable(currentList)%>
		<%= ReportTable.footer()%>
		<% Iterator<HostRecord> iterate = currentList.iterator();%>
			<%  while(iterate.hasNext()){ %>
			<%=	   ReportTable.popUp(currentList, iterate.next().getHostname())%>
			<%}%>
	</body>
</html>