<%@page import="com.tlv8.base.db.DBUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.tlv8.system.bean.ContextBean"%>
<html>
  <head>
	<title>任务提醒</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<link rel="stylesheet" href="css/task_remind.css" type="text/css"></link>
	<%
	String dataTableRead = "";
	int count = 0;
	ContextBean context = ContextBean.getContext(request);
	String personID = context.getCurrentPersonID();
	String personFID = context.getCurrentPersonFullID();
	String sql = "select SID,SNAME,SCDEPTNAME,SCPERSONNAME,SCREATETIME,SLOCK from " 
		+ "(select SID,SNAME,SCDEPTNAME,SCPERSONNAME,"
		+ "SCREATETIME,SLOCK from SA_TASK where (SEPERSONID = '"
		+ personID
		+ "' or ('"
		+ personFID
		+ "' like nvl(SEFID,'TASKTEMP/')||'%' and STYPEID is null) "
		+ " or EXISTS(select t.spersonid "
		+ "	from sa_opagent t "
		+ " where t.sagentid = '"
		+ personID
		+ "'"
		+ " and t.sactive = 1 and t.spersonid = SA_TASK.SEPERSONID "
		+ " and trunc(t.sstarttime) <= trunc(sysdate) "
		+ " and trunc(t.sfinishtime) >= trunc(sysdate))"
		+ ") and SSTATUSID = 'tesReady' and "
		+ " (STYPEID is null or (STYPEID is not null and SLOCK is null))"
		+ " and screatetime >= sysdate - 100 and SLOCK is null "
		+ " order by SCREATETIME desc ) where  rownum<=5";
	if(DBUtils.IsMySQLDB("system")){
		sql = "select SID,SNAME,SCDEPTNAME,SCPERSONNAME,SCREATETIME,SLOCK from " 
				+ "(select SID,SNAME,SCDEPTNAME,SCPERSONNAME,"
				+ "SCREATETIME,SLOCK from SA_TASK t1 where (SEPERSONID = '"
				+ personID
				+ "' or ('"
				+ personFID
				+ "' like concat(ifnull(SEFID,'TASKTEMP/'),'%') and STYPEID is null) "
				+ " or EXISTS(select t.spersonid "
				+ "	from sa_opagent t "
				+ " where t.sagentid = '"
				+ personID
				+ "'"
				+ " and t.sactive = 1 and t.spersonid = t1.SEPERSONID "
				+ " and date(t.sstarttime) <= date(now()) "
				+ " and date(t.sfinishtime) >= date(now()))"
				+ ") and SSTATUSID = 'tesReady' and "
				+ " (STYPEID is null or (STYPEID is not null and SLOCK is null))"
				+ " and screatetime >= now() - 100 and SLOCK is null "
				+ " order by SCREATETIME desc )a limit 0,5";
	}else if(DBUtils.IsMSSQLDB("system")){
		sql = "select top SID,SNAME,SCDEPTNAME,SCPERSONNAME,SCREATETIME,SLOCK from " 
				+ "(select SID,SNAME,SCDEPTNAME,SCPERSONNAME,"
				+ "SCREATETIME,SLOCK from SA_TASK t1 where (SEPERSONID = '"
				+ personID
				+ "' or ('"
				+ personFID
				+ "' like isnull(SEFID,'TASKTEMP/')+'%' and STYPEID is null) "
				+ " or EXISTS(select t.spersonid "
				+ "	from sa_opagent t "
				+ " where t.sagentid = '"
				+ personID
				+ "'"
				+ " and t.sactive = 1 and t.spersonid = t1.SEPERSONID "
				+ " and CONVERT(varchar(100), t.sstarttime, 23) <= CONVERT(varchar(100), GETDATE(), 23) "
				+ " and CONVERT(varchar(100), t.sfinishtime, 23) >= CONVERT(varchar(100), GETDATE(), 23) )"
				+ ") and SSTATUSID = 'tesReady' and "
				+ " (STYPEID is null or (STYPEID is not null and SLOCK is null))"
				+ " and screatetime >= GETDATE() - 100 and SLOCK is null "
				+ " order by SCREATETIME desc )a";
	}
	try{
		List<Map<String, String>> list = DBUtils.execQueryforList("system",sql);
		dataTableRead = "<table width='100%' style='border-collapse: collapse;table-layout: fixed; cursor:normal;'>";
		for(int i=0;i<list.size();i++){
			Map m = list.get(i);
			dataTableRead += "<tr id='"
				+ m.get("SID")
				+ "'><td title='"
				+ m.get("SNAME")
				+ "' width='300'><a >"
				+ "<img src='/tlv8/flw/flwcommo/taskpoLet/image/symbol.png'/>"
				+ "<a href='javascript:void(0);' id='"
				+ m.get("SID")
				+ "' name='taskLink' onclick='reMindTaskOpen(this.id)'>"
				+ " "
				+ m.get("SNAME")
				+ "</a></td><td title='"
				+ m.get("SCDEPTNAME")
				+ "' width='80'>"
				+ m.get("SCDEPTNAME")
				+ "</td><td title='"
				+ m.get("SCPERSONNAME")
				+ "' width='60'>"
				+ m.get("SCPERSONNAME")
				+ "</td><td align='center' title='"
				+ ((String)m.get("SCREATETIME")).substring(0, 19)
				+ "'>"
				+ ((String)m.get("SCREATETIME")).substring(0, 19)
				+ "</td><tr>";
			count++;
		}
		dataTableRead += "<tr><td colspan='4'/></tr></table>";
	}catch(Exception e){
		e.printStackTrace();
	}
	%>
  <script type="text/javascript" src="../../comon/js/jquery/jquery.min.js"></script>
  <script type="text/javascript" src="../../comon/js/comon.main.js"></script>
  <script type="text/javascript" src="../../comon/js/flow.main.js"></script>
  <script type="text/javascript">
 	//var pWindow = window.dialogArguments;
 	var pWindow = window.opener;
  	function reMindTaskOpen(taskID){
  		pWindow.reMindTaskOpen(taskID);
  		pWindow.p = null;
  		pWindow.focus();
  		closeself();
  	}
  	window.onunload = function(){
  		pWindow.p = null;
  	}
  </script>
  </head>
  
  <body>
  	<center><b>你还未查看的任务:</b></center><hr/>
  	<div id="taskList">loading... ...</div>
	<script type="text/javascript">
		var tbstr = "<%=dataTableRead%>";
		var count = "<%=count%>";
		if(!tbstr || tbstr=="" || count==0){
			pWindow.p = null;
			closeself();
		}
		document.getElementById("taskList").innerHTML = (tbstr);
	</script>
  </body>
</html>
