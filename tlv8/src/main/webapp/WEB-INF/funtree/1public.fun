<?xml version="1.0" encoding="UTF-8"?>

<root> 
  <item display="solid" icon="oa.png" label="个人办公" layuiIcon="layui-icon layui-icon-username"> 
    <item activity="mainActivity" icon="t.png" label="我的待办" layuiIcon="fa fa-tasks" process="/SA/task/taskCenter/process" url="/SA/task/taskCenter/mainActivity.html?opation=waitting"></item>  
    <item activity="mainActivity" icon="rc.png" label="日程安排" layuiIcon="fa fa-calendar" process="/SA/personal/schedule/process" url="/SA/personal/schedule/mainActivity.html"></item>  
    <item activity="mainActivity" icon="email.png" label="电子邮件" layuiIcon="fa fa-envelope-o" process="/OA/email/process" url="/OA/email/mainActivity.html"></item>  
    <item activity="mainActivity" icon="shenpi.png" label="审批意见设置" layuiIcon="fa fa-check-square-o" process="/OA/flowset/myOpinion/process" url="/OA/flowset/myOpinion/mainActivity.html"></item>  
    <item activity="mainActivity" icon="jisuanqi.png" label="计算器" layuiIcon="fa fa-fax" process="/system/accessory/process" url="/system/accessory/counter.html"></item>  
    <item label="工作日志" layuiIcon="fa fa-building-o"> 
      <item activity="WorkLog" icon="notboke.png" label="写日志" process="/system/workLog/process" url="/system/workLog/WorkLog.html"></item>  
      <item activity="WorkLogList" icon="worklog.png" label="查看日志" process="/system/workLog/process" url="/system/workLog/WorkLogList.html"></item> 
    </item>  
    <item display="" label="日报" layuiIcon="layui-icon layui-icon-date"> 
      <item activity="reportList" icon="gerenrb.png" label="个人日报" process="/OA/Report/DayReport/process" url="/OA/Report/DayReport/reportList.html"></item>  
      <item activity="reportLookList" icon="gerenrzck.png" label="日报查看" process="/OA/Report/DayReport/process" url="/OA/Report/DayReport/reportLookList.html"></item> 
    </item>  
    <item display="" label="周报" layuiIcon="layui-icon layui-icon-table"> 
      <item activity="reportList" icon="zhoubao.png" label="个人周报" process="/OA/Report/WeekReport/process" url="/OA/Report/WeekReport/reportList.html"></item>  
      <item activity="reportLookList" icon="zhoubaock.png" label="周报查看" process="/OA/Report/WeekReport/process" url="/OA/Report/WeekReport/reportLookList.html"></item> 
    </item>  
    <item display="" label="月报" layuiIcon="layui-icon layui-icon-chart-screen"> 
      <item activity="reportList" icon="yuebao.png" label="个人月报" process="/OA/Report/MonthReport/process" url="/OA/Report/MonthReport/reportList.html"></item>  
      <item activity="reportLookList" icon="yuebaock.png" label="月报查看" process="/OA/Report/MonthReport/process" url="/OA/Report/MonthReport/reportLookList.html"></item> 
    </item> 
  </item>  
  <item display="solid" icon="oa.png" label="员工自助" layuiIcon="layui-icon layui-icon-template"> 
    <item activity="PersonData" icon="mybaseinfo.png" label="信息修改" layuiIcon="fa fa-info" process="/SA/personal/cnttSrc/process" url="/SA/personal/cnttSrc/PersonData.html"></item>  
    <item activity="mainActivity" icon="pass.png" label="密码修改" layuiIcon="layui-icon layui-icon-password" process="/SA/password/process" url="/SA/password/mainActivity.html"></item>  
    <item activity="mainActivity" icon="rszz.png" label="人事自助" layuiIcon="layui-icon layui-icon-user" process="/SA/personal/personInfo/process" url="/SA/personal/personInfo/mainActivity.html"></item>  
    <item activity="listActivity" icon="tgroup.png" label="我的组群" layuiIcon="layui-icon layui-icon-group" process="/OA/PersonUse/MYGROUP/process" url="/OA/PersonUse/MYGROUP/listActivity.html"></item> 
  </item> 
</root>
