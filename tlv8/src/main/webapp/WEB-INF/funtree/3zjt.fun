<?xml version="1.0" encoding="UTF-8"?>

<root> 
  <item display="solid" icon="oa.png" label="日常办公" layuiIcon="layui-icon-date"> 
    <item label="通知公告" layuiIcon="layui-icon layui-icon-notice"> 
      <item activity="publicnoticeadd" icon="notify.png" label="通知发布" process="/OA/notice/publicnotice/process" url="/OA/notice/publicnotice/publicnoticeadd.html"></item>  
      <item activity="publicnoticepushlist" icon="notem.png" label="通知管理" process="/OA/notice/publicnotice/process" url="/OA/notice/publicnotice/publicnoticepushlist.html"></item>  
      <item activity="looknoticepushlist" icon="notelis.png" label="通知列表" process="/OA/notice/publicnotice/process" url="/OA/notice/publicnotice/looknoticepushlist.html"></item> 
    </item>  
    <item label="新闻发布" layuiIcon="fa fa-newspaper-o"> 
      <item activity="newcolumn" icon="newspsh.png" label="新闻发布" process="/system/News/informationRelase/process" url="/system/News/informationRelase/newcolumn.html"></item>  
      <item activity="newslist" icon="news.png" label="新闻管理" process="/system/News/informationRelase/process" url="/system/News/informationRelase/newslist.html"></item>  
      <item activity="newsManageLists" icon="newli.png" label="新闻列表" process="/system/News/informationRelase/process" url="/system/News/informationRelase/newsManageLists.html"></item> 
    </item>  
    <item label="文档资料" layuiIcon="layui-icon layui-icon-read"> 
      <item activity="PersonalDocNode" icon="myfiles.png" label="个人文件柜" process="/SA/docnode/process" url="/SA/docnode/PersonalDocNode.html"></item>  
      <item activity="PersonaList" icon="file.png" label="文件列表" process="/SA/docnode/process" url="/SA/docnode/PersonaList.html"></item> 
    </item> 
  </item>  
  <item display="solid" icon="oa.png" label="交流园地" layuiIcon="layui-icon layui-icon-chat"> 
    <item label="讨论区" layuiIcon="fa fa-comment-o"> 
      <item activity="Bo_category" icon="fenlei.png" label="分类设置" process="/system/News/forum/process" url="/system/News/forum/Bo_category.html"></item>  
      <item activity="BO_bloglist" icon="tlqsz.png" label="讨论区设置" process="/system/News/forum/process" url="/system/News/forum/BO_bloglist.html"></item>  
      <item activity="BO_blogManage" icon="tlqgl.png" label="讨论区管理" process="/system/News/forum/process" url="/system/News/forum/BO_blogManage.html"></item>  
      <item activity="BO_talkl" icon="bemail.png" label="发帖" process="/system/News/forum/process" url="/system/News/forum/BO_talkl.html"></item> 
    </item> 
  </item> 
</root>
