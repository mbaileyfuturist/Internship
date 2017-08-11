<%@ page import="HelixSystems.ReportTable" %>
<%@ page import="HelixSystems.HostRecord" %>
<%@ page import="HelixSystems.HostRecordDatabaseManager" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="HelixSystems.ConfigDbTest" %>
<%@ page import="gov.nih.hpc.configdb.ConfigDbClientFactory" %>
<%@ page import="gov.nih.hpc.configdb.ConfigDb" %>


<html>
	<head>
		<meta charset = "UTF-8">
		<link rel="stylesheet" type="text/css" href="Style.css">
		<title>Helix System</title>
		<script src="functionality.js"></script>
	</head>

	<body> 
		<%  List<HostRecord> listOfHosts = new ArrayList<>();%>
		<%  listOfHosts = HostRecordDatabaseManager.getListFromDataBase(); %>
		<%= ReportTable.key()%>
		<%= ReportTable.header()%>
		<%= ReportTable.columnNames() %>
		<%= ReportTable.fullTable(listOfHosts)%>
		<%= ReportTable.footer()%>
		<%  Iterator<HostRecord> iterate = listOfHosts.iterator();%>
		<%  while(iterate.hasNext()){ %>
		<%=	ReportTable.popUp(listOfHosts, iterate.next().getHostname())%>
		<%}%> 
	</body>
</html>