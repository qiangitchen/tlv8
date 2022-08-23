<?xml version="1.0" encoding="UTF-8"?>

<root> 
  <item icon="xt.gif" label="任务中心" layuiIcon="layui-icon layui-icon-auz"> 
    <item activity="mainActivity" icon="workflow.png" label="任务列表" layuiIcon="fa fa-tasks" process="/SA/task/taskCenter/process" url="/SA/task/taskCenter/mainActivity.html"></item>  
    <item activity="monitorActivity" icon="lcjk.png" label="流程监控" layuiIcon="fa fa-desktop" process="/SA/task/taskCenter/process" url="/SA/process/monitor/mainActivity.html"></item>  
    <item activity="vml-dwr-editer" icon="lcsj.png" label="流程设计" layuiIcon="fa fa-dashboard" process="/flw/dwr/process" url="/flw/dwr/vml-dwr-editer.html"></item>  
    <item activity="reminActivity" icon="tixing.png" label="系统提醒" layuiIcon="fa fa-leaf" process="/SA/task/taskCenter/process" url="/SA/remind/mainActivity.html"></item>  
    <item activity="mainActivity" icon="tixing.png" label="任务时限配置" layuiIcon="layui-icon layui-icon-about" process="/SA/task/timeLimitset/process" url="/SA/task/timeLimitset/mainActivity.html"></item> 
  </item>  
  <item icon="xt.gif" label="系统管理" layuiIcon="layui-icon layui-icon-set"> 
    <item icon="org_manage003.png" label="组织机构" layuiIcon="fa fa-sitemap"> 
      <item activity="mainActivity" icon="org_manage003.png" label="机构管理" process="/SA/OPM/organization/organizationProcess" url="/SA/OPM/organization/mainActivity.html"></item>  
      <item activity="mainActivity" icon="rule003.png" label="角色管理" process="/SA/OPM/role/roleProcess" url="/SA/OPM/role/mainActivity.html"></item>  
      <item activity="mainActivity" icon="auther_manage.png" label="授权管理" process="/SA/OPM/authorization/authorizationProcess" url="/SA/OPM/authorization/mainActivity.html"></item>  
      <item activity="mainActivity" icon="managament.png" label="权限管理" process="/SA/OPM/management/managementProcess" url="/SA/OPM/management/mainActivity.html"></item>  
      <item activity="mainActivity" icon="huishouzhan.png" label="回收站" process="/SA/OPM/recycled/recycledProcess" url="/SA/OPM/recycled/mainActivity.html"></item>  
      <item activity="mainActivity" icon="fenji.png" icon32="/UI/SA/OPM/grade/images/icon32.png" icon64="/UI/SA/OPM/grade/images/icon64.png" label="分级管理" process="/SA/OPM/grade/gradeProcess" url="/SA/OPM/grade/mainActivity.html"></item>  
      <item activity="gradeActivity" icon="org_manage003.png" label="机构管理-分级" process="/SA/OPM/organization/organizationProcess" url="/SA/OPM/organization/gradeActivity.html"></item>  
      <item activity="gradeActivity" icon="auther_manage.png" label="授权管理-分级" process="/SA/OPM/authorization/authorizationProcess" url="/SA/OPM/authorization/gradeActivity.html"></item>  
      <item activity="gradeActivity" icon="managament.png" label="权限管理-分级" process="/SA/OPM/management/managementProcess" url="/SA/OPM/management/gradeActivity.html"></item>  
      <item activity="gradeRecycledActivity" icon="huishouzhan.png" label="回收站-分级" process="/SA/OPM/recycled/recycledProcess" url="/SA/OPM/recycled/gradeActivity.html"></item>  
      <item activity="gradeActivity" icon="fenji.png" icon32="/UI/SA/OPM/grade/images/icon32.png" icon64="/UI/SA/OPM/grade/images/icon64.png" label="分级管理-分级" process="/SA/OPM/grade/gradeProcess" url="/SA/OPM/grade/gradeActivity.html"></item> 
    </item>  
    <item icon="wdzx.png" label="文档管理" layuiIcon="layui-icon layui-icon-read"> 
      <item activity="docCenter" icon="wdzx.png" label="文档中心" process="/SA/doc/docCenter/docCenterProcess" url="/SA/doc/docCenter/mainActivity.html"></item>  
      <item activity="mainActivity" icon="wdjs.png" label="文档检索" process="/SA/doc/docSearch/docSearchProcess" url="/SA/doc/docSearch/docSearch.html"></item>  
      <!-- 
      <item activity="mainActivity" label="文档关联" process="/SA/doc/docPermission/docPermissionProcess" url="/SA/doc/docPermission/docPermission.w"></item>  
       -->  
      <item activity="mainActivity" icon="wdpz.png" label="文档配置" process="/SA/doc/docSetting/docSettingProcess" url="/SA/doc/docSetting/mainActivity.html"></item> 
    </item>  
    <item label="系统工具" layuiIcon="layui-icon layui-icon-util"> 
      <item activity="LoginLog" label="登录日志" process="/SA/log/LoginLogProcess" url="/SA/log/Login-Log.html"></item>
      <item activity="mainActivity" icon="jkc.png" icon32="/UI/SA/log/images/icon32.png" icon64="/UI/SA/log/images/icon64.png" label="操作日志" process="/SA/log/logProcess" url="/SA/log/mainActivity.html"></item>  
      <item activity="mainActivity" icon="zxyh.png" icon32="/UI/SA/online/images/icon32.png" icon64="/UI/SA/online/images/icon64.png" label="在线用户" process="/SA/online/onlineProcess" url="/SA/online/MySampleListPage.html"></item>  
      <item activity="gradeOnlineUserActivity" icon="zxyh.png" icon32="/UI/SA/online/images/icon32.png" icon64="/UI/SA/online/images/icon64.png" label="在线用户-分级" process="/SA/online/onlineProcess" url="/SA/online/gradeOnlineUserActivity.html"></item>  
      <item activity="mainActivity" icon="fwqjk.png" label="服务器监控" process="SA/services/pcProcess" url="/SA/services/servicesInfo.html"></item>  
      <item activity="cockpitActivity" icon="jiankongc.png" label="服务器监控舱" process="SA/services/pcProcess" url="/SA/cockpit/show.html"></item> 
    </item>  
    <item label="基础配置" layuiIcon="layui-icon layui-icon-util"> 
      <item activity="mailsetMain" display="" icon="" label="邮箱配置" process="/SA/mail/process" url="/SA/mail/mailsetMain.html"></item>
    </item>  
    <item label="报表工具" layuiIcon="fa fa-bar-chart"> 
    	<item activity="mainActivity" icon="baobiaosheji.png" label="报表设计" layuiIcon="layui-icon layui-icon-layouts" process="/ureport/designerProcess" url="/ureport/designer"></item>
    </item> 
  </item> 
</root>
