var cpath="/tlv8";

layui.config({
  version: true
}).use('mobile', function(){
  var mobile = layui.mobile
  ,layim = mobile.layim
  ,layer = mobile.layer;
  
  //演示自动回复
  var autoReplay = [
    '您好，我现在有事不在，一会再和您联系。', 
    '你没发错吧？face[微笑] ',
    '洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！face[哈哈] ',
    '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
    'face[威武] face[威武] face[威武] face[威武] ',
    '<（@￣︶￣@）>',
    '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
    'face[黑线]  你慢慢说，别急……',
    '(*^__^*) face[嘻嘻] ，是贤心吗？'
  ];
  
  layer.murldat = new Map();
  
  var MymoreList = [];
  $.ajax({
		type : "post",
		async : false,
		url : cpath+"/getmobileFuncTree",
		dataType: 'json',
		success : function(result, textStatus) {
			//console.log(result);
			if (result.status = "SUCCESS") {
				var data = result.data;
				if(data && data != ""){
					var menusdatas = data.childNodes[0].childNodes;
					for (var i = 0; i < menusdatas.length; i++) {
						var mudat = menusdatas[i];
						MymoreList.push({
							alias: mudat.id,
							title: mudat.label,
							iconUnicode: mudat.iconUnicode||'&#xe653;',
							iconClass: ''
						});
						layer.murldat.put(mudat.id, mudat);
					}
				}
			}
		},
		error : function() {
			// 请求出错处理
		}
	});
  
   var imconfig = {
    //上传图片接口
    uploadImage: {
      url: 'imUploadImageAction' //（返回的数据格式见下文）
      ,type: '' //默认post
    }
    
    //上传文件接口
    ,uploadFile: {
      url: 'imUploadFileAction' //（返回的数据格式见下文）
      ,type: '' //默认post
    }
    
    //,brief: true
    
    //,init: res.data
    
    //扩展聊天面板工具栏
    ,tool: [{
      alias: 'code'
      ,title: '代码'
      ,iconUnicode: '&#xe64e;'
    }]
    
    //扩展更多列表
    ,moreList: MymoreList
    /*
    ,moreList: [{
      alias: 'find'
      ,title: '发现'
      ,iconUnicode: '&#xe628;' //图标字体的unicode，可不填
      ,iconClass: '' //图标字体的class类名
    },{
      alias: 'share'
      ,title: '分享与邀请'
      ,iconUnicode: '&#xe641;' //图标字体的unicode，可不填
      ,iconClass: '' //图标字体的class类名
    }]
    */
    
    //,tabIndex: 1 //用户设定初始打开的Tab项下标
    ,isNewFriend: false //是否开启“新的朋友”
    ,isgroup: true //是否开启“群聊”
    ,chatTitleColor: '#3098d8' //顶部Bar颜色
    //,title: 'TLv8IM -' + datainfo.personName //应用名，默认：我的IM
    ,voice: 'default.mp3' //声音提醒
    ,copyright: true
  };
  $.ajax({
		url : cpath+"/system/User/initPortalInfo",
		async : false,
		type : "POST",
		dataFilter : function(data, type) {
			var items = window["eval"]("(" + data + ")");
			return items;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("error");
		},
		success : function(data, textStatus) {
			var datainfo = window["eval"]("(" + data[0].data + ")");
			layim.config($.extend(imconfig,{title: 'TLv8IM -' + datainfo.personName}));
		}
  });
  
  $.get(cpath+'/getUserListAction',function(res){
	//console.log(res.data);
	layim.config($.extend(imconfig,{init: res.data}));
	//记录好友在线状态
    layim.userSatusmap = new Map();
    layui.mine = res.data.mine;
    
    var lockReconnect = false;//避免重复连接
    var proto = window.location.protocol;
	var shost = window.location.host; //IE\u4e0d\u5141\u8bb8\u8de8\u57df
	var wsUrl = shost+cpath+'/IM/websocket/'+res.data.mine.id;
	if(proto=="https:"){
		wsUrl = "wss://" + wsUrl;
	}else{
		wsUrl = "ws://" + wsUrl;
	}
	var websocket;
	
	function createWebSocket() {
      try {
    	  websocket = new WebSocket(wsUrl);
          init();
      } catch(e) {
        //console.log('catch');
        reconnect(wsUrl);
      }
    }
	
	function init() {
		//连接发生错误的回调方法
		websocket.onerror = function () {
			//layer.msg("WebSocket连接发生错误");
			reconnect(wsUrl);
		};
		
		//连接成功建立的回调方法
		websocket.onopen = function () {
			//layer.msg("WebSocket连接成功");
			heartCheck();
		};
		
		//接收到消息的回调方法
		websocket.onmessage = function (event) {
			var res = event.data;
			//console.log(res);
			//layer.msg(res);
			res = JSON.parse(res);
			if(res.emit === 'chatMessage'){
			   layim.getMessage(res.data); //res.data即你发送消息传递的数据（阅读：监听发送的消息）
			}else if(res.emit === 'userstatus'){
				layim.userSatusmap.put(res.data.id, res.data.status);
				layim.setFriendStatus(res.data.id, res.data.status);
				if(layim.currentchatid==res.data.id){
					if(res.data.status=="online"){
			    		layim.setChatStatus('<span style="color:green;">在线</span>');
			    	}else{
			    		layim.setChatStatus('<span style="color:#FF5722;">离线</span>');
			    	}
				}
			}
		};
		
		//连接关闭的回调方法
		websocket.onclose = function () {
			//layer.msg("WebSocket连接关闭");
			reconnect(wsUrl);
		};
		
		//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		window.onbeforeunload = function () {
			websocket.close();
		}
	
		layim.websocket = websocket;
	}
	
	var tt,chtt;
	function reconnect(url) {
      if(lockReconnect) {
        return;
      };
      lockReconnect = true;
      //没连接上会一直重连，设置延迟避免请求过多
      tt && clearTimeout(tt);
      tt = setTimeout(function () {
        createWebSocket(url);
        lockReconnect = false;
      }, 4000);
    }
	
	createWebSocket(wsUrl);
	
	function heartCheck(){
		layim.websocket.send(JSON.stringify({type:'heartCheck', data: new Date().getTime()}));
		chtt && clearTimeout(chtt);
		chtt = setTimeout(heartCheck,3000);
	}
  
  //创建一个会话
  /*
  layim.chat({
    id: 111111
    ,name: '许闲心'
    ,type: 'kefu' //friend、group等字符，如果是group，则创建的是群聊
    ,avatar: 'http://tp1.sinaimg.cn/1571889140/180/40030060651/1'
  });
  */

  
  //监听点击“新的朋友”
  layim.on('newFriend', function(){
    layim.panel({
      title: '新的朋友' //标题
      ,tpl: '<div style="padding: 10px;">自定义模版，{{d.data.test}}</div>' //模版
      ,data: { //数据
        test: '么么哒'
      }
    });
  });
  
  //查看聊天信息
  layim.on('detail', function(data){
	  //console.log(data); //获取当前会话对象
	  $.get('getMessageHistoryAction',{id:data.id,type:data.type}, function(redata){
			//console.log(redata.data);
		    layim.panel({
		      title: '群组 '+ data.name +' 的聊天记录' //标题
		      ,tpl: ['<div class="layim-chat-main" style="bottom:0px;"><ul>{{# layui.each(d.data, function(index, item){ }}'
		    	  	,'<li class="layim-chat-system"><span>{{layui.data.date(item.timestamp)}}</span></li>'
		    	    ,'<li class="layim-chat-li{{ (item.id == layui.mine.id) ? " layim-chat-mine" : "" }}">'
		    	    ,'<div class="layim-chat-user"><img src="{{ item.avatar }}"><cite>'
		    	      ,'{{ item.username||"佚名" }}'
		    	    ,'</cite></div>'
		    	    ,'<div class="layim-chat-text">{{ layui.data.content(item.content||"&nbsp;") }}</div>'
		    	  ,'</li> {{# }); }}</ul></div>'
		    	  ].join('') //模版
		      ,data: redata.data
		    });
		  },'json');
  });
  
  //监听点击更多列表
  layim.on('moreList', function(obj){
	  var smurldat = layer.murldat.get(obj.alias);
	  window.open(smurldat.url, '_self');
	  /*
    switch(obj.alias){
      case 'find':
        layer.msg('自定义发现动作');
        
        //模拟标记“发现新动态”为已读
        layim.showNew('More', false);
        layim.showNew('find', false);
      break;
      case 'share':
        layim.panel({
          title: '邀请好友' //标题
          ,tpl: '<div style="padding: 10px;">自定义模版，{{d.data.test}}</div>' //模版
          ,data: { //数据
            test: '么么哒'
          }
        });
      break;
    }
    */
  });
  
  //监听返回
  layim.on('back', function(){
    //如果你只是弹出一个会话界面（不显示主面板），那么可通过监听返回，跳转到上一页面，如：history.back();
  });
  
  //扫码回调
  layui.scanCallback = function(text){
	  var anys = false;
	  try{
		  anys = justepYnApp.contentAnalysis(text);
	  }catch (e) {
	  }
	  if(!anys){
		  alert(text);
	  }
  };
  
  //监听扫描动作
  layim.on('scanBarcode', function(){
	 try{
		 justepYnApp.scanCode('layui.scanCallback');
	 }catch (e) {
	 }
  });
  
  //监听自定义工具栏点击，以添加代码为例
  layim.on('tool(code)', function(insert, send){
    layer.prompt({
        title: '插入代码'
        ,formType: 2 //2：Area文本框  1：Input框
        ,shade: 0
        ,area:['300px','90px']//输入框大小
      }, function(text, index){
        layer.close(index);
        insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
        send();
      });
  });
  
  //监听发送消息
  layim.on('sendMessage', function(data){
	  layim.websocket.send(JSON.stringify({type:'chatMessage', data: data}));
  });
  
  //模拟收到一条好友消息
  /*
  setTimeout(function(){
    layim.getMessage({
      username: "贤心"
      ,avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1"
      ,id: "100001"
      ,type: "friend"
      ,cid: Math.random()*100000|0 //模拟消息id，会赋值在li的data-cid上，以便完成一些消息的操作（如撤回），可不填
      ,content: "嗨，欢迎体验LayIM。演示标记："+ new Date().getTime()
    });
  }, 3000);
  )
  */
  
  //监听查看更多记录
  layim.on('chatlog', function(data, ul){
	  //console.log(data);
	  $.get('getMessageHistoryAction',{id:data.id,type:data.type}, function(redata){
		//console.log(redata.data);
	    layim.panel({
	      title: '与 '+ data.name +' 的聊天记录' //标题
	      ,tpl: ['<div class="layim-chat-main" style="bottom:0px;"><ul>{{# layui.each(d.data, function(index, item){ }}'
	    	  	,'<li class="layim-chat-system"><span>{{layui.data.date(item.timestamp)}}</span></li>'
	    	    ,'<li class="layim-chat-li{{ (item.id == layui.mine.id) ? " layim-chat-mine" : "" }}">'
	    	    ,'<div class="layim-chat-user"><img src="{{ item.avatar }}"><cite>'
	    	      ,'{{ item.username||"佚名" }}'
	    	    ,'</cite></div>'
	    	    ,'<div class="layim-chat-text">{{ layui.data.content(item.content||"&nbsp;") }}</div>'
	    	  ,'</li> {{# }); }}</ul></div>'
	    	  ].join('') //模版
	      ,data: redata.data
	    });
	  },'json');
  });
  
  //退出登录
  layim.on('logOut',function(){
	  if (!confirm("确认要退出系统吗？")) return;
	  $.ajax({
		  url:cpath+"/system/User/logout",
		  type : "post",
		  async : true,
		  success : function(data, textStatus) {
			  window.location.href = window.location.href.replace(/index.*\.html.*/, 'login.html');
		  }
	  });
  });
  
  //模拟"更多"有新动态
  //layim.showNew('More', true);
  //layim.showNew('find', true);
  
  loadWaitCount(layim);//加载状态
  },'json');
});

function loadWaitCount(layim) {
	$.ajax({
		url : "getPortalCountAction",
		type : "post",
		async : true,
		dataType : "json",
		success : function(re, textStatus) {
			var showmk = false;
			if (re.data.flag == "true") {
				var taskcount = re.task_count;// 代办
				var maicount = re.mail_count;// 邮件
				var wpcount = re.wp_count;// 周计划
				var mpcount = re.mp_count;// 月计划
				var drcount = re.dr_count;// 日报
				var wrcount = re.wr_count;// 周报
				var mrcount = re.mr_count;// 月报
				var noticecount = re.notice_count;// 通知
				if (taskcount > 0) {
					showmk = true;
					layim.showNew('waittask', true);
				} else {
					layim.showNew('waittask', false);
				}
				if (maicount > 0) {
					showmk = true;
					layim.showNew('waitemail', true);
				}else{
					layim.showNew('waitemail', false);
				}
				if (wpcount > 0) {
					showmk = true;
					layim.showNew('weekplan', true);
				}else{
					layim.showNew('weekplan', false);
				}
				if (mpcount > 0) {
					showmk = true;
					layim.showNew('monthplan', true);
				}else{
					layim.showNew('monthplan', false);
				}
				if (drcount > 0) {
					showmk = true;
					layim.showNew('day_report',true);
				}else{
					layim.showNew('day_report',false);
				}
				if (wrcount > 0) {
					showmk = true;
					layim.showNew('week_report',true);
				}else{
					layim.showNew('week_report',false);
				}
				if (mrcount > 0) {
					showmk = true;
					layim.showNew('month_report',true);
				}else{
					layim.showNew('month_report',false);
				}
				if (noticecount > 0) {
					showmk = true;
					layim.showNew('waitNotice', true);
				}else{
					layim.showNew('waitNotice', false);
				}
			}
			if (showmk) {
				layim.showNew('More', true);
			} else {
				layim.showNew('More', false);
			}
		}
	});
}