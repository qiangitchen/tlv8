justep.Doc = {
	/* 附件权限枚举值 
	 * 列表(list) : l ; 读取(read) : r ; 下载(download) : d ; 下载上传 (download upload) : du ; 
	 * 下载上传修改(download upload update) : duu ; 下载上传修改删除(download upload update delete) : duud  */	
	accessEnum : {
		l : 1,
		r : 3,
		d : 7,
		du : 263,
		duu : 775,
		duud : 1799
	},	
	
	isHttps : function(){
		return window.location.protocol == "https:";
	},	
	
	getdocServerAction : function(docPath, urlPattern, isFormAction) {
		if(!isFormAction)
			isFormAction = false;
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setBoolean("isHttps", this.isHttps());
		sendParam.setString('docPath',docPath);
		sendParam.setString('urlPattern',urlPattern);
		sendParam.setBoolean('isFormAction',isFormAction);
		
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";

		options.dataType = "json";
		options.parameters = sendParam;
		options.action = "queryHostAction";
		options.directExecute = true;

		try{
			var res = justep.Request.sendBizRequest2(options);
		}catch(e){
			throw new Error("juestp.Doc.getdocServerAction失败,请确认文档服务配置是否正确！");
		}
		
		if(!justep.Request.isBizSuccess(res,'json')){
			throw new Error("juestp.Doc.getdocServerAction失败,请确认文档服务配置是否正确！！");
		}
		var docUrl= justep.Request.responseParseJSON(res).data.value;
		if(docUrl=='#'){
			alert('您没有相应操作的权限');
		}
		if(!docUrl.indexOf(window.location.protocol) < 1 && docUrl!='#'){
			docUrl = justep.Request.BASE_URL4NORESOURCE + docUrl;
		}
		return docUrl+"&"+this.getBSessionIDParam();
	},
		
	getBSessionIDParam : function() {
		return "bsessionid=" + justep.Request.URLParams["bsessionid"];	
	},
	
	getCookie : function(cookieName) {
		var data = this.getShareData();
		if(!data) return "";
		return data["__docShareData__" + cookieName];
	},

	getShareData: function(){
		var currentWindow = window;
		var parentWindow = currentWindow.parent;
		while (parentWindow && currentWindow!=parentWindow) {
			currentWindow = parentWindow;
			parentWindow = window.parent;
		}
		if(!parentWindow) return this.__docShareData;
		if(parentWindow.__docShareData) return parentWindow.__docShareData;
		parentWindow.__docShareData = {};
		return parentWindow.__docShareData;
	},
	
	setCookie : function(cookieName, value) {
		var data = this.getShareData();
		if(!data) return;
		
		data["__docShareData__" + cookieName] = value;		
	},
	
	getAuthList : function(person, personId){
		if(typeof person == "undefined"){
			person = justep.Context.getCurrentPersonMemberFID();
		}
		if(typeof personId == "undefined"){
			personId = justep.Context.getCurrentPersonCode();
		}
		var deptPath =justep.Context.getCurrentPersonMemberFID();
	    var personId = justep.Context.getCurrentPersonCode();
		
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString('deptPath', deptPath);
		sendParam.setString('personId', personId);
		response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "queryPermissionAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			return response;
		}else{
			throw new Error("juestp.Doc.getAuthList失败！");
		}
	},

	queryNameSpaces : function() {
		var options = {};
		var param = this.createParam("queryNameSpace", [], []);		
		var sendParam = new justep.Request.ActionParam();
		sendParam.setXml('param',new justep.Request.XMLParam(param));
		
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";

		options.dataType = "json";
		options.parameters = sendParam;
		options.action = "dispatchOptAction";
		options.directExecute = true;
		
		var resQN = justep.Request.sendBizRequest2(options);
		if(!justep.Request.isBizSuccess(resQN,'json')){
			throw new Error("juestp.Doc.queryNameSpaces失败！");
		}
		var nameSpaces = justep.Request.responseParseJSON(resQN).data.value.rows;
		var oNameSpaces = {};
		for (var i = 0; i < nameSpaces.length; i++) {
			var nameSpace = nameSpaces[i];
			var rootID = nameSpace.SA_DocNode.value;
			var url = nameSpace.sUrl.value;
			oNameSpaces[rootID] = {url : url};
		}
		this.setCookie("docNameSpaces", JSON.stringify(oNameSpaces));
		return oNameSpaces;
	},

	getDocServerByDocPath : function(fullPath) {
		if (!fullPath) {
			alert("justep.Doc.getDocServerByDocPath Error : 文档信息全路径不正确");
			return;
		}
		fullPath = fullPath.substring(1);
		var rootID = fullPath.indexOf("/") == -1 ? fullPath : fullPath.substring(0, fullPath.indexOf("/"));
		var sNameSpaces = this.getCookie("docNameSpaces");
		if (sNameSpaces) {
			var oNameSpaces = JSON.parse(sNameSpaces);
		} else {
			var oNameSpaces = this.queryNameSpaces();
		}
		return oNameSpaces[rootID];
	},
	
	queryDefine : function(process, activity, keyId, linkDefineOnly) {
		var linkAll;
		if (this.attmentLinkDefines) {
			linkAll = this.attmentLinkDefines;
		} else {
			var options = {};
			var param = new justep.Request.ActionParam();
			param.setString('linkProcess', process);
			param.setString('linkActivity', activity);
			options.process = "/SA/doc/system/systemProcess";
			options.activity = "mainActivity";

			options.dataType = "json";
			options.parameters = param;
			options.action = "queryLinkDefineMap";
			options.directExecute = true;
			options.callback = function(data) {
			};
			var response = justep.Request.sendBizRequest2(options);
			if(!justep.Request.isBizSuccess(response,'json')){
					throw new Error("juestp.Doc.queryDefine失败！");
			}
			linkAll = justep.Request.responseParseJSON(response).data.value;
			this.attmentLinkDefines = linkAll;
		}
		
		var define = linkAll;
		
		if (keyId && !!linkAll.keys[keyId]) {
			define = linkAll.keys[keyId];
			//define.hostInfo = linkAll.hostInfo;
		}

		return define;
	},
	/**
	 * @description: 查询当前应显示的目录
	 * @param: billID - 业务ID
	 * @param: process - 过程名
	 * @param: activity - 活动名	
	 * @param: rootPath - 文档关联定义根目录
	 * @param: subPath - 文档关联定义子目录
	 * @return: loader - 结构是tree的目录信息
	 */
	
	queryLinkedDir : function(billID, process, activity, rootPath, subPath) {		
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString("rootPath", rootPath);
		sendParam.setString("subPath", subPath);
		sendParam.setString("billID", billID);
		sendParam.setString("process", process);
		sendParam.setString("activity", activity);
		sendParam.setBoolean("isTree", true);
		
		var tp= new justep.Request.TranslateParam();
		tp.dataType = justep.Request.TranslateParam.DATATYPE_ROW_TREE ;
		tp.setTreeOption("tree-parent-relation", "sParentID"); 
		
		options.translateParam = tp;
		
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";
		
		options.dataType = "xml";
		options.parameters = sendParam;
		options.action = "queryLinkDirAction";
		options.directExecute = true;
		options.callback = function(data) {
		};
		var response = justep.Request.sendBizRequest2(options);
		if(justep.Request.isBizSuccess(response)){
			return response;
		}else{
			throw new Error("juestp.Doc.queryLinkedDir失败！");
		}
	},
	
	/**
	 * @description: 查询当前应显示的文件
	 * @param: billID - 业务ID
	 * @param: process - 过程名
	 * @param: activity - 活动名	
	 * @param: pattern - 返回结果列表的字段名数组	
	 * @param: orderBy - 排序字段名
	 * @return: loader - 结构是list的文件信息
	 */
	
	queryLinkedDoc : function(billID,rootPath,subPath) {
		var param = this.createParam("queryLinkedDoc", ["bill-id","rootPath","subPath"], [billID,rootPath,subPath]);
		var sendParam = new justep.Request.ActionParam();
		sendParam.setXml('param',new justep.Request.XMLParam(param));
		var response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "dispatchOptAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			return response;
		}else{
			throw new Error("juestp.Doc.queryLinkedDoc失败！");
		}
	},
	
	/**
	 * @description: 获取文档信息
	 * @param: docID - 文档ID
	 * @param: docPath - 文档docPath
	 * @param: pattern - 返回结果列表的字段名数组	
	 * @param: orderBy - 排序字段名
	 * @return: loader - 结构是list的文件信息 
	 */
	queryDoc : function(docID, docPath, pattern, orderBy, custom , single) {
		if(typeof docID == "undefined"){
			docID = "";
		}
		if(typeof docPath == "undefined"){
			docPath = "";
		}
		if(typeof pattern == "undefined"){
			pattern = "";
		}
		if(typeof orderBy == "undefined"){
			orderby = "";
		}
		if(typeof custom == "undefined"){
			custom = "";
		}
		if(pattern != ""){
			pattern=pattern.join(",");
		}
		
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString('docID', docID);
		sendParam.setString('docPath', docPath);
		sendParam.setString('pattern', pattern);
		sendParam.setString('orderBy',orderBy);
		sendParam.setString('custom',custom);
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";

		options.dataType = "json";
		options.parameters = sendParam;
		options.action = "queryDocAction";
		options.directExecute = true;
		
		var response = justep.Request.sendBizRequest2(options);
		if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("juestp.Doc.queryDoc失败！");
		}
		var rows = justep.Request.responseParseJSON(response).data.value.rows;		
		/*返回单行数据*/
		if(single == "single"){
			if(rows.length != 1){
				throw new Error("juestp.Doc.queryDoc失败！");
			}
			return rows[0];
		}		
		/*返回多行数据*/
		return rows;
	 },
	 
	 getUploader: function(containerID, docPath, limitSize, uploadResponse, click, width, height, zIndex, filter, multiFiles, caller,selected,completed){
		 return this.getUploader2({containerID:containerID, docPath:docPath, limitSize:limitSize, uploadResponse:uploadResponse, click:click, width:width, height:height, zIndex:zIndex, filter:filter, multiFiles:multiFiles, caller:caller,selected:selected,completed:completed});
	 },
	 
	 getUploader2: function(o) {
		 var containerID=o.containerID;
		 var docPath=o.docPath;
		 var limitSize =o.limitSize;
		 var uploadResponse =o. uploadResponse;
		 var click =o.click;
		 var width =o. width;
		 var height=o.height;
		 var zIndex =o.zIndex;
		 var filter=o. filter;
		 var multiFiles=o.multiFiles;
		 var caller=o.caller;
		 var selected=o.selected;
		 var completed=o.completed;
		YAHOO.widget.Uploader.SWFURL = justep.Request.convertURL("/UI/system/service/doc/transport/uploader.swf", true);
		var overlay = YAHOO.util.Dom.get(containerID);
		YAHOO.util.Dom.setStyle(overlay, 'width', width);
		YAHOO.util.Dom.setStyle(overlay, 'height', height);
		YAHOO.util.Dom.setStyle(overlay, 'z-index', zIndex);
		var uploader = new YAHOO.widget.Uploader(containerID);
		uploader.setDocPath = function(docPath){
			this.docPath = docPath;
		};
		uploader.setDocPath(docPath);
		uploader.allCount = 0;
		uploader.finishCount = 0;
		uploader.fileList;
		uploader.click = function() {
			if (click != undefined) {
				click.call(caller);
			}
			uploader.clearFileList();
		};
		uploader.contentReady = function() {
			uploader.setAllowMultipleFiles(multiFiles ? true : false);
			if (filter) {
				uploader.setFileFilters(filter);
			}
		};
		uploader.fileSelect = function(event) {
			if (selected != undefined) {
				var cancel = selected.call(caller,event.fileList,this);
				if(cancel){
					event.fileList={};
					uploader.clearFileList();
				}
			}
			uploader.allCount = 0;
			uploader.finishCount = 0;
			uploader.completedCount = 0;
			uploader.fileList = event.fileList;
			
			var fileData = [];
			for (var p in uploader.fileList) {
				var file = uploader.fileList[p];
				if ((file.size > limitSize) && (limitSize > 0)) {
					alert("上传的附件不允许大于 " + justep.Doc.formatSize(limitSize));
					uploader.removeFile(file.id);
					continue;
				}
				fileData.push({id : file.id, name : file.name, size : file.size, progress : "0"});
				uploader.allCount++;
			};
			if(uploader.allCount == 0) return;
			this.openUploadProgressDialog(fileData);
			this.currentUploader = uploader;
			/*uploader.uploadAll(uploader.host + "/repository/file/cache/upload", "POST", "uploadInfo", "Filedata");*/
			try{				
				var host = justep.Doc.getdocServerAction(uploader.docPath, "/repository/file/cache/upload");
			}catch(e){				
				alert("justep.Doc.getUploader.uploader.fileSelect：获取文档服务器host失败！");
				throw e;
			}
			try{
				/*if(host.indexOf("uploadDoc.j")==-1){
					uploader.loadAppPolicy(host.substr(0,host.indexOf("repository"))+"crossdomain.xml");
				}*/
				uploader.uploadAll(host, "POST", "uploadInfo", "filedata");
			}catch(e){				
				alert("justep.Doc.getUploader.uploader.fileSelect：上传文件超时！");
				throw e;
			}
			
		};
		uploader.uploadStart = function(event) {
		};
		uploader.uploadProgress = function(event) {
			var prog = Math.round(100 * (event["bytesLoaded"] / event["bytesTotal"]));
			if(this.uploadPprogressTable){
				var idx = this.uploadPprogressTable.find("id",event["id"]);
				this.uploadPprogressTable.setValue(idx+1,"progress",prog);
				this.uploadPprogressTable.dm[idx].prog = prog;
			}
		};
		uploader.uploadCancel = function(event) {
		};
		uploader.uploadComplete = function(event) {
			uploader.completedCount++;
			if(uploader.completedCount==uploader.allCount){
				if (completed != undefined) {
					completed.call(caller,uploader.fileList,this);
				}
			}
		};
		uploader.uploadResponse = function(event) {
			uploader.finishCount++;
			var eventData = justep.XML.fromString(event.data);
			var docName = uploader.fileList[event.id].name;
			var kind = justep.XML.getNodeText(justep.XML.eval(eventData, "//file/@mediatype")[0]);
			var size = uploader.fileList[event.id].size;
			cacheName = justep.XML.getNodeText(justep.XML.eval(eventData, "//file/@file-name")[0]);
			uploadResponse.call(caller, docName, kind, size, cacheName);
			if (uploader.finishCount == uploader.allCount) {
				if(this.uploadPprogressTable){
					this.uploadPprogressTable.deleteAllRow();
				    this.uploadPprogressTable.deleteQueue = null;
				}
				this.getUploadProgressDialog().close();
			}
		};
		uploader.uploadError = function(event) {
			alert('文档服务器上传文件失败，错误信息：' + event.status);
		};
		uploader.addListener('click', uploader.click);
		uploader.addListener('contentReady', uploader.contentReady);
		uploader.addListener('fileSelect', uploader.fileSelect, null, this);
		uploader.addListener('uploadStart', uploader.uploadStart);
		uploader.addListener('uploadProgress', uploader.uploadProgress, null, this);
		uploader.addListener('uploadCancel', uploader.uploadCancel);
		uploader.addListener('uploadComplete', uploader.uploadComplete, null, this);
		uploader.addListener('uploadCompleteData', uploader.uploadResponse, null, this);
		uploader.addListener('uploadError', uploader.uploadError);
		return uploader;
	},
	
	trim :function(value){
		return value||"";
	},
	
	addCreateVersionLog:function(changeLog,attachmentValue,billID){
		var item={};
		item.billID=billID;
		item.attachmentValue=attachmentValue;
		changeLog.createVersionLogs.push(item);
	},
	
	addChangeLog : function(changeLog, operate, values, filePorps, billID){
		var item = {};
		item.operation_type = operate;
		/*当前环境信息 */
		item.process = justep.Context.getCurrentProcess();
		item.activity = justep.Context.getCurrentActivity();
		item.person = justep.Context.getCurrentPersonMemberFID();
		item.person_name = justep.Context.getCurrentPersonName();
		item.dept_name = justep.Context.getCurrentDeptName()?justep.Context.getCurrentDeptName():"";
		item.bill_id = this.trim(billID);
		
		/*文档属性 */
		item.doc_id = this.trim(values[0]);
		item.version = this.trim(values[1]+"");
		item.file_id = this.trim(values[2]);
		item.doc_version_id = this.trim(values[3]);
		item.doc_name = this.trim(values[4]);
		item.kind = this.trim(values[5]);
		item.size = this.trim(values[6]);
		item.parent_id = this.trim(values[7]);
		item.doc_path = this.trim(values[8]);
		item.doc_display_path = this.trim(values[9]);
		item.description = this.trim(values[10]);
		item.classification = this.trim(values[11]);
		item.keywords = this.trim(values[12]);
		item.finish_time = this.trim(values[13]);
		item.serial_number = this.trim(values[14]);
		/*文档服务器文件属性 */
		if (typeof filePorps != "undefined") {
			item.doc_type = this.trim(filePorps[0]);
			item.cache_name = this.trim(filePorps[1]);
			item.revision_cache_name = this.trim(filePorps[2]);
			item.comment_file_content = this.trim(filePorps[3]);			
		}else{
			item.doc_type = "";
			item.cache_name = "";
			item.revision_cache_name = "";
			item.comment_file_content = "";
		}

		/* 存储时需要的辅助信息 */
		if(("new"==operate) || ("link"==operate) || ("newDir"==operate)){
			item.link_id = (new justep.UUID()).valueOf();
		}else
			item.link_id = "";
		item.access_record_id = (new justep.UUID()).valueOf();
				
		changeLog.items.push(item);
	},
	
	updateChangeLog : function(changeLog,docId,fileId,docVersionId){
		var items = changeLog.items;
		for(var i=0; i < items.length ; i++){
			var item = items[i];
			if(item.doc_id == docId){
				item.file_id = fileId;
				item.doc_version_id = docVersionId;
				return;
			}
		}
	},
	
	removeChangeLog : function(changeLog,docID){
		var items = changeLog.items;
		for (var item in items) {
			var item = items[item];
			if (item.doc_id == docID){
				changeLog.items.pop(item);	
			}
		}
	},
	/*
	向changeLog中的row修改记录，需保证values和filePorps顺序
	values为[doc-id, version, file-id, doc-version-id, doc-name, kind, size, parent-id, doc-path, doc-display-path, description, classification, keywords, finish-time, serial-number].
	filePorps为[doc-type, cache-name, revision-cache-name, comment-file-content]
	*/
	modifyChangeLog : function(item, values, filePorps) {
		item.version = values[0];
		item.file_id = values[1];
		item.doc_version_id = values[2] == null?"":values[2];
		item.doc_name = values[3];
		item.kind = values[4];
			
		item.size = values[5];
		item.parent_id = values[6];
		item.doc_path =	values[7];
		item.doc_display_path = values[8];
		item.description =  values[9] == null?"":values[9];
		
		item.classification = values[10] == null?"":values[10];
		item.keywords = values[11] == null?"":values[11];
		item.finish_time = values[12] == null?"":values[12];
		item.serial_number = values[13] == null?"":values[13];
		if(item.operation_type=="editInfo"&&values[14]){
			item.operation_type = values[14];
		}
		
		/*文档服务器文件属性 */
		if (typeof filePorps != "undefined") {
			item.doc_type = filePorps[0];
			item.cache_name = filePorps[1];
			item.revision_cache_name =	filePorps[2];
			item.comment_file_content =	filePorps[3];			
		}else{
			item.doc_type = "";
			item.cache_name = "";
			item.revision_cache_name =	"";
			item.comment_file_content =	"";
		}
	},

	evalChangeLog : function(changeLog,docId) {
		var items = changeLog.items;
		for(var item in items){
			if(items[item].doc_id==docId)
				return items[item];
		}
		return null;
	},
	
	addAccessRecord : function(changeLog) {
		var sendParam = new justep.Request.ActionParam();	
	    sendParam.setString('param', JSON.stringify(changeLog));
		response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "addAccessRecordAction", sendParam, null, null, false);
		if(!justep.Request.isBizSuccess(response)){
		    throw new Error("juestp.Doc.addAccessRecord失败！");
	    }
	},
	
	createElement : function(changeLog, name, value){
		var element = changeLog.createElement(name);
		if(value != null && value !== ""){
			justep.XML.setNodeText(element,'.', value);
		}
		return element;
	},
	createVersion : function(sDocID,isSaveDocLink){
		var options = {};
	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('sDocID', sDocID);
	    sendParam.setBoolean("isSaveDocLink", isSaveDocLink?isSaveDocLink:false);
	    sendParam.setBoolean("isHttps", this.isHttps());
		var response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "createVersionAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			var createSucess =justep.Request.transform(justep.Request.getData(response.responseXML));
			return createSucess;
		}else{
			throw new Error("juestp.Doc.createVersion失败！");
		}
	},
	deleteVersion :function(docPath, fileID,LogID, docVersion){
		var options = {};
	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('sDocPath', docPath);
	    sendParam.setString('sFileID', fileID);
	    sendParam.setString('sLogID', LogID);
	    sendParam.setString('sDocVersion', docVersion);
	    sendParam.setBoolean("isHttps", this.isHttps());
		var response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "deleteVersionAction", sendParam, null, null, true);
		if(!justep.Request.isBizSuccess(response)){
			throw new Error("juestp.Doc.deleteVersion失败！");
		}
	},
	createVersionFromJsonStr : function(billID,jsonStr,isHttps){
		var options = {};
	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('billID', billID);
	    sendParam.setString('jsonStr', jsonStr);
	    sendParam.setBoolean("isHttps", this.isHttps());
		var response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "createVersionFromJsonStrAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			return true;
		}else{
			throw new Error("juestp.Doc.createVersionFromJsonStr失败！");
		}
	},
	
	lockDoc : function(sDocID) {
		var sendParam = new justep.Request.ActionParam();
		sendParam.setBoolean('isLockDoc', true);
		sendParam.setString('sDocID', sDocID);
		var response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "changeDocStateAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			var affactRow =justep.Request.transform(justep.Request.getData(response.responseXML));
			if(affactRow&&affactRow=='0'){
				alert('文件已经被别人锁定,您不能再锁定');
				return false;
			}
			return true;
		}else{
			throw new Error("juestp.Doc.lockDoc失败！");
		}
	},
	unLockDoc : function(sDocID) {
		var sendParam = new justep.Request.ActionParam();
		sendParam.setBoolean('isLockDoc', false);
		sendParam.setString('sDocID', sDocID);
		var response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "changeDocStateAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			var affactRow =justep.Request.transform(justep.Request.getData(response.responseXML));
			if(affactRow&&affactRow=='0'){
				alert('文档锁定人不是您或者着文档没有锁定，您不能解锁');
					return false;
			}
			return true;
		}else{
			throw new Error("juestp.Doc.unLockDoc失败！");
		}
	},
	
	/**
	 * @description: 下载文档
	 * @param: docID - 文档ID
	 * @param: host - 文档服务器地址ID
	 */	
	downloadDoc : function(docPath, docID) {
		var row = this.queryDoc(docID, undefined, ["sFileID"],undefined,undefined,"single");
		var fileID =  row.sFileID.value;
		this.downloadDocByFileID(docPath, fileID);
	},
	
	downloadDocByFileID : function(docPath, fileID, versionID, partType) {
		window.open(this.getURLByFileID(docPath, fileID, versionID, partType),"_self");
	},
	
	getURL : function(docPath, docID) {
		var row = this.queryDoc(docID, undefined, ["sFileID"],undefined,undefined,"single");
		var fileID =  row.sFileID.value;
		return this.getURLByFileID(docPath, fileID);
	},
	
	getURLByFileID : function(docPath, fileID, versionID, partType) {
		var versionID = versionID ? versionID : "last";
		var partType = partType ? partType : "content";
		return this.getdocServerAction(docPath, "/repository/file/download/" + fileID + "/" + versionID + "/" + partType );
	},	

	browseFileComment : function(docPath,fileID,docVersionID){
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setBoolean("isHttps", this.isHttps());
		sendParam.setString('docPath',docPath);
		sendParam.setString('fileID',fileID);
		sendParam.setString('docVersionID',docVersionID);
		
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";

		options.dataType = "json";
		options.parameters = sendParam;
		options.action = "queryCommentFileContent";
		options.directExecute = true;

		try{
			var res = justep.Request.sendBizRequest2(options);
		}catch(e){
			throw new Error("juestp.Doc.browseFileComment失败,查询修订内容失败！");
		}
		
		if(!justep.Request.isBizSuccess(res,'json')){
			throw new Error("juestp.Doc.browseFileComment失败,查询修订内容失败！");
		}
		var v = justep.Request.responseParseJSON(res).data.value;
		return JSON.parse(v);
	},
	
	browseDoc : function(docPath, docID){
		var row = this.queryDoc(docID, undefined, ["sDocName,sFileID"],undefined,undefined,"single");
		var docName = row.sDocName.value;
		var fileID = row.sFileID.value;
		this.browseDocByFileID(docPath, docName, fileID);		
	},
	
	browseDocByFileID : function(docPath, docName, fileID, versionID, partType, programID, isPrint){
		var versionID = versionID ? versionID : "last";
		var partType = partType ? partType : "content";
		if(!fileID){
	         alert('文档不能浏览，数据未提交！');
	         return;
		}
		var fileinfo = this.queryDocByFileId(docPath,fileID,docName,versionID);
		if ($.browser.msie && '.doc.docx.xls.xlsx.ppt.mpp.vsd.'.indexOf(String(/\.[^\.]+$/.exec(docName)) + '.') >= 0) {
			var OVP = {};
			OVP.host = docPath;	
			OVP.programID = programID;
			OVP.versionID = versionID;

			if(fileinfo.length < 1) 
			   throw new Error("文档服务器不存在名称为"+ docName + "的office文件！");
			if(partType=='revision'){
				OVP.partType = !fileinfo.parts.part_3 ? "content" : "revision";
			}else
				OVP.partType= partType;
					
			OVP.fileID = fileID;
			OVP.fileExt = String(/\.[^\.]+$/.exec(docName));
			OVP.filename = docName.substr(0, docName.lastIndexOf('.'));
			OVP.filename = escape(OVP.filename);
			if(typeof isPrint === "undefined" || isPrint == null) isPrint = true;
			OVP.isPrint = isPrint ? true : false;			
			var param = "<data>" + unescape(OV.JSON.stringify(OVP)) + "</data>";
			justep.Request.callURL("/system/service/doc/office/officeViewerWindow.w?process=" 
		      + justep.Context.getCurrentProcess()
		      + "&activity=" + justep.Context.getCurrentActivity(), null, param);
		}
		else if($.browser.msie && _read_file_type &&_read_file_type.indexOf((String(/\.[^\.]+$/.exec(docName)).toLowerCase())) >= 0){
			var url = this.getdocServerAction(docPath, "/repository/file/view/" + fileID + "/" + versionID + "/" + partType ); 
			window.open(url);
		}else{
			alert("浏览器不支持在线浏览此格式的文件");
		}		
	},	
	
	/**
	 * @description: 构造请求参数
	 * @param: operation - 操作类型
	 * @param: nodes - 节点名数组与值对应
	 * @param: values - 节点值数组与名对应
	 * @return: param - 参数字符串
	 * 
	 */	
	createParam : function(operate, nodes, values){
		var items = [];
		items.push("<data>");
		items.push("<operate>");
		items.push(operate);
		items.push("</operate>");
		for(i=0;i<nodes.length;i++){
			items.push("<");
			items.push(nodes[i]);
			items.push(">");
			items.push(values[i]);
			items.push("</");
			items.push(nodes[i]);
			items.push(">");
		}
		items.push("</data>");
		return items.join("");			
	},

	/**
	 * @description: 构造请求参数
	 * @param: pattern - 条件模板节点名数组
	 * @return: param - 条件模板
	 * 
	 */
	createQueryPattern : function(pattern){
		var items = [];
		for(i=0;i<pattern.length;i++){
			items.push("<");
			items.push(pattern[i]);
			items.push("/>");
		}
		return items.join("");
	},
	
	transB2KB : function(v) {
		if (v.value == '') {
			return;
		}
		var tempValue = v.value;
		var resultValue = "";
		var tempValueStr = new String(tempValue);
		if ((tempValueStr.indexOf('E') != -1) || (tempValueStr.indexOf('e') != -1)) {
			var regExp = new RegExp('^((\\d+.?\\d+)[Ee]{1}(\\d+))$', 'ig');
			var result = regExp.exec(tempValue);
			var power = "";
			if (result != null) {
				resultValue = result[2];
				power = result[3];
				result = regExp.exec(tempValueStr);
			}
			if (resultValue != "") {
				if (power != "") {
					var powVer = Math.pow(10, power);
					resultValue = resultValue * powVer / 1000;
				}
			}
			return parseInt(resultValue) + 1;
		} else {
			return parseInt(tempValue / 1000) + 1;
		}
	},
	
	formatSize : function(size) {
		var format = "";
		if(size == 0) {
			format = "0.0 KB";
		} else {
			if(size < 1048576){
				format = (Math.ceil(size / 1024 * 10) / 10).toFixed(1) + " KB";
			} else{
				format = (Math.ceil(size / 1048576 * 10) / 10).toFixed(1) + " MB";
			}
		}
		return format;
	},
	
	getDocFullPath : function(docID, docPath) {
		if(docPath == "/"){
			return docPath.concat(docID);
		}else{
			return docPath.concat("/").concat(docID);
		}
	},
	
	
	getDocFullDisplayPath : function(docName, docDisplayPath) {
		if(docDisplayPath == "/"){
			return docDisplayPath.concat(docName);
		}else{
			return docDisplayPath.concat("/").concat(docName);
		}
	},
	
	/**
	 * @description: 获取文档操作记录
	 * @param: docID - 文档ID
	 * @param: opaerationTypes - 是否包含此操作
	 * @return: resopnse - 操作记录dom
	 * 
	 */
	
	getAccessRecord : function(docID, hasDownload, hasNew, hasEdit ){
		if (typeof hasDownload == "undefined") {
			hasDownload = true;
		}
		if (typeof hasNew == "undefined") {
			hasNew = true;
		}
		if (typeof hasEdit == "undefined") {
			hasEdit = true;
		}
		var param = this.createParam("queryAccessRecord", ["doc-id", "has-new", "has-download", "has-edit"], [docID, hasNew.toString(),hasDownload.toString() , hasEdit.toString()]);
	    var sendParam = new justep.Request.ActionParam();		
		sendParam.setXml('param',new justep.Request.XMLParam(param));
	    response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "dispatchOptAction", sendParam, null, null, true);		
	    
		if(justep.Request.isBizSuccess(response)){
			return response;
		}else{
			alert("getAccessRecord Error :查询操作记录失败！");
		}
	},
	
	openOfficeDialog : function(docExtDivID, docExtObjID, OVP, afterOfficeViewerDialogSelect, caller) {
		justep.Doc.afterOfficeViewerDialogSelect=afterOfficeViewerDialogSelect;
		justep.Doc.caller=caller;
		if(!document.getElementById(docExtObjID)){
			var docExtDiv = document.getElementById(docExtDivID);
		    docExtDiv.outerHTML = '<object id="'+ docExtObjID +'" classid="clsid:4771E057-4202-4F93-8F73-4C6654A9573D" style="width:100%"' +
				'codebase='+justep.Request.convertURL(cpath+"/comon/doc_ocx/office/office.cab#version="+_ocx_version, true)+' style="display:none;" >' +
				'</object><SCRIPT LANGUAGE=javascript FOR='+docExtObjID+' EVENT=OnExcuteJS(id,param)> justep.Doc.execOfficeAction(id,param);</SCRIPT>';
		}
		var docOcx = document.getElementById(docExtObjID);
		OVP.filename=escape(OVP.filename);
		docOcx.OpenWebForm(justep.Request.addBsessionid(window.location.protocol+"//"+self.location.host+justep.Request.convertURL("/system/service/doc/office/officeViewerDialog.w?process=/SA/doc/system/systemProcess&activity=mainActivity")), unescape(OV.JSON.stringify(OVP)));
		
	},
	
	execOfficeAction : function(id,param){
		if(id=='officeAction'){
			if(param){
				var data = OV.JSON.parse(param);
				justep.Doc.afterOfficeViewerDialogSelect.call(justep.Doc.caller, data);
			}else{
				//点编辑进去，点关闭出来需要解锁
				justep.Doc.afterOfficeViewerDialogSelect.call(justep.Doc.caller, "");
			}
		}else if(id == "undefined"){
			if(typeof officeAutomation == "function"){
				officeAutomation.call(justep.Doc.caller,id,param);
			}
		}
	},
	
	docInfoDialog : null,
	
	openDocInfoDialog : function(data, afterEnsureFun, caller){
		if(!this.docInfoDialog){
			this.docInfoDialog = new justep.WindowDialog("docInfoDialog","/system/service/doc/dialog/docInfoDialog.w","文件属性",true,null,544,545,0,0,true);
			this.docInfoDialog.attachEvent("onSend",function(event){return this.data;} , this.docInfoDialog);
			this.docInfoDialog.attachEvent("onReceive",function(event){afterEnsureFun.call(caller, event);}, this.docInfoDialog);
		}
		this.docInfoDialog.data = data;
		this.docInfoDialog.open();
	},
	
	dirInfoDialog : null,
	
	openDirInfoDialog : function(data, afterEnsureFun, caller){
		if(!this.dirInfoDialog){
			this.dirInfoDialog = new justep.WindowDialog("docInfoDialog","/system/service/doc/dialog/dirInfoDialog.w","文件夹属性",true,null,440,525,0,0,true);
			this.dirInfoDialog.attachEvent("onSend",function(event){return this.data;} , this.dirInfoDialog);
			this.dirInfoDialog.attachEvent("onReceive",function(event){afterEnsureFun.call(caller, event);}, this.dirInfoDialog);
		}
		this.dirInfoDialog.data = data;
		this.dirInfoDialog.open();
	},
	
	historyDialog: null,
	
	openDocHistoryDialog: function(docID, fileID, docPath,access,isPrint)	{
		if(!this.historyDialog){
			this.historyDialog = new justep.WindowDialog("historyDialog","/system/service/doc/dialog/docHistoryDialog.w","历史版本",true,true,652,485,0,0,true);
			this.historyDialog.attachEvent("onSend",function(event){return this.data;} , this.historyDialog);
		}
		this.historyDialog.data = {
			docID: docID,
			fileID: fileID, 
			docPath: docPath,
			access:access,
			isPrint:isPrint
		};
		this.historyDialog.open();
	},
	
	downloadHistoryDialog: null,
	
	openDocDownloadHistoryDialog: function(docID){       
		if(!this.downloadHistoryDialog){
			this.downloadHistoryDialog = new justep.WindowDialog("docDownloadHistoryDialog","/system/service/doc/dialog/docDownloadHistoryDialog.w","下载记录",true,true,644,445,0,0,true);
			this.downloadHistoryDialog.attachEvent("onSend",function(event){return this.data;} , this.downloadHistoryDialog);
		}
		this.downloadHistoryDialog.data = {
			docID: docID
		};
		this.downloadHistoryDialog.open();
	},
	
	templateDialog: null,
	
	openDocTemplateDialog : function(data, afterSelectFun, caller){
		if(!this.templateDialog){
			this.templateDialog = new justep.WindowDialog("templateDialog","/system/service/doc/dialog/docTemplateDialog.w","选择模板",true,null,500,400,0,0,false);
		}
		if(this.templateDialog.onSendId) this.templateDialog.detachEvent(this.templateDialog.onSendId);
		if(this.templateDialog.onReciveId ) this.templateDialog.detachEvent(this.templateDialog.onReciveId);
		this.templateDialog.onSendId = this.templateDialog.attachEvent("onSend",function(event){return this.data;} , this.templateDialog);
		this.templateDialog.onReciveId = this.templateDialog.attachEvent("onReceive",function(event){afterSelectFun.call(caller, event);}, this.templateDialog);
		this.templateDialog.data = data;
		this.templateDialog.open();
	},
	
	officeFieldDialog:null,
	
	openOfficeFieldDialog : function(data, afterSelectFun, caller){
		if(!this.officeFieldDialog){
			this.officeFieldDialog = new justep.WindowDialog("officeFieldDialog","/system/service/doc/office/officeViewerFieldDialog.w","文档域编辑",true,null,550,350,0,0,true);
			this.officeFieldDialog.attachEvent("onSend",function(event){return this.data;} , this.officeFieldDialog);
			this.officeFieldDialog.attachEvent("onReceive",function(event){afterSelectFun.call(caller, event);}, this.officeFieldDialog);
		}
		this.officeFieldDialog.data = data;
		this.officeFieldDialog.open();
	},
	
	getImage:function() {
		return '/form/dhtmlx/dhtmlxGrid/imgs/folderClosed.gif';	
	},
	
	getUploadProgressDialog:function(){
		var self = this;
		if(!this.uploadProgressDialog){ 
			this.uploadProgressDialog = new justep.Dialog("docUploadProgressDialog", "正在上传...",true,null,550,215,null,null,function(){
				var str = "<div id='progressTable'></div>";
				
				self.uploadProgressDialog.setContentHTML(str);
				
				self.uploadPprogressTable = new justep.Doc.ProgressTable({renderTo:"progressTable",width:"550px",columns:[
						{text:"名称",width:"270",name:"name"},
						{text:"大小(byte)",width:"100",name:"size"},
						{text:"上传进度",name:"progress",width:"150",render:function(prog,record){
					if(prog == '0'){
					return "<div style='height:5px;width:150px;background-color:#CCC;'></div>";
							   	  }else if(prog !='100'){
							   	  	return "<div style='height:12px;width:150px;background-color:#CCC;'><div style='height:5px;background-color:#0EBE0E;width:" + (prog/100)*150 + "px;'></div></div>";
							   	  }else if(prog == '100'){  
							   	  	return "<div style='height:12px;background-color:#0EBE0E;width:150px;'></div>";
							   	  }
							  }},
						{text:"",name:"cancal",width:"24"}
						 ]});
			}
			);
			this.uploadProgressDialog.setMinmaxable(false);
			this.uploadProgressDialog.setResizable(false);		 
			
		}
	    return this.uploadProgressDialog;
	},
	
	openUploadProgressDialog:function(data){
		if(!data) return;
		var dlg = this.getUploadProgressDialog();
		if(null!=dlg){
			dlg.open();
			if(this.uploadPprogressTable){
				if(this.uploadPprogressTable){
					this.uploadPprogressTable.deleteAllRow();
				    this.uploadPprogressTable.deleteQueue = null;
				}
			    for (i = 0; i < data.length; i++) {
			       var record = data[i];	
				   if(!this.uploadPprogressTable.dm){
					this.uploadPprogressTable.dm = [];
					}
					if(this.uploadPprogressTable.deleteQueue){
						this.uploadPprogressTable.deleteByField("id",this.uploadPprogressTable.deleteQueue.shift());
					}
					record.cancal = justep.Doc.ProgressTable.createCancalButton(record.id);
					this.uploadPprogressTable.dm.push(record);
					this.uploadPprogressTable.appendRow(record);
			    }
			}
			
		}
	},
	queryDocCache:function(docID){
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString('docID', docID);
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";

		options.dataType = "json";
		options.parameters = sendParam;
		options.action = "queryDocCacheAction";
		options.directExecute = true;
		options.callback = function(data) {
		};
		var response = justep.Request.sendBizRequest2(options);
		if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("juestp.Doc.queryDocCache失败！");
		}
		var fileinfo = justep.Request.responseParseJSON(response).data;
		return fileinfo;
	},	
	queryDocByFileId : function(docPath,fileid,filename,version){
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setBoolean('isHttps', this.isHttps());
		sendParam.setString('host', docPath);
		sendParam.setString('fileId', fileid);
	    sendParam.setString('docVersion', !version?"last":version);
		// /options.contentType = 'json';
		options.process = "/SA/doc/system/systemProcess";
		options.activity = "mainActivity";

		options.dataType = "json";
		options.parameters = sendParam;
		options.action = "queryDocInfoByIdAction";
		options.directExecute = true;
		options.callback = function(data) {
		};
		var response = justep.Request.sendBizRequest2(options);		
		if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("juestp.Doc.queryDocInfoById失败！");
		}
		var fileinfo = justep.Request.responseParseJSON(response).data;
		if(fileinfo.queryFlag == "false"){
			throw new Error('提示：'+(!filename ? '此文件不存在，可能文档服务配置存在问题，请联系系统管理员！' : '“'+ filename + '”文件不存在，可能文档服务配置存在问题，请联系系统管理员！'));
		}
		return fileinfo;			
	},
	syncCustomFileds:function(docID){
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString('sDocID', docID);
		sendParam.setBoolean('isHttps', this.isHttps());
		response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "syncCustomFiledsAction", sendParam, null, null, true);
		if(!justep.Request.isBizSuccess(response)){
			throw new Error("juestp.Doc.syncCustomFileds失败！");
		}
		
	},
	commitDocCache:function(docID,changeLog){
		var node = justep.Doc.evalChangeLog(changeLog, docID);
		var options = {};
	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('changeLog', JSON.stringify(node));
	    options.process = "/SA/doc/system/systemProcess";
	    options.activity = "mainActivity";
	    options.dataType = "json";
	    options.parameters = sendParam;
	    options.action = "commitDocCacheAction";
	    options.directExecute = true;
	    var response = justep.Request.sendBizRequest2(options);
	    if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("commitError: 数据保存失败！");
		}
	},
	//知识中心新建word文件的时候要提交flag的
	commitDocFlag:function(docID){
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString('deptPath', deptPath);
		sendParam.setString('personId', personId);
		response = justep.Request.sendBizRequest("/SA/doc/system/systemProcess", "mainActivity", "commitDocFlagAction", sendParam, null, null, true);
		if(justep.Request.isBizSuccess(response)){
			return response;
		}else{
			throw new Error("juestp.Doc.commitDocFlag失败！");
		}
	},
	
	
	/*
	 *  @param: 知识中心使用commitDocAction
	 *  		附件使用commitAttachAction
	 */
	commitDoc : function(changeLog,rootPath,action){
		//changeLog.url = host+"/repository/file/cache/commit";
		changeLog.isHttps = this.isHttps();
		changeLog.operate = "commitDoc";		
		var options = {};
	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('changeLog', JSON.stringify(changeLog));
	    options.process = "/SA/doc/system/systemProcess";
	    options.activity = "mainActivity";
	    options.dataType = "json";
	    options.parameters = sendParam;
	    options.action = action?action:"commitDocAction";
	    options.directExecute = true;    
	    var response = justep.Request.sendBizRequest2(options);
	    if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("commitError: 数据提交失败！");
		}
	    return justep.Request.responseParseJSON(response).data;
	},
	
	saveDoc : function(changeLog,isSaveDocLink){
		changeLog.operate = "saveDoc";
		var options = {};

	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('changeLog', JSON.stringify(changeLog));
	    sendParam.setBoolean("isSaveDocLink", isSaveDocLink?isSaveDocLink:false);
	    
	    options.process = "/SA/doc/system/systemProcess";
	    options.activity = "mainActivity";
	    options.dataType = "json";
	    options.parameters = sendParam;
	    options.action = "saveAttachAction";
	    options.directExecute = false;    
	    var response = justep.Request.sendBizRequest2(options);
		
		if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("saveDocError: 数据保存失败！");
		}
		return response;
	},
	
	deleteDocAction : function(changeLog,rootPath){
		changeLog.isHttps = this.isHttps();
		changeLog.operate = "deleteDocAction";		
		var options = {};
	    var sendParam = new justep.Request.ActionParam();
	    sendParam.setString('changeLog', JSON.stringify(changeLog));
	    options.process = "/SA/doc/system/systemProcess";
	    options.activity = "mainActivity";
	    options.dataType = "json";
	    options.parameters = sendParam;
	    options.action = "deleteDocAction";
	    options.directExecute = true;    
	    var response = justep.Request.sendBizRequest2(options);
	    if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("commitError: 数据提交失败！");
		}
	},
	
	getDocAuthList : function(){
		var options = {};
		var sendParam = new justep.Request.ActionParam();
		sendParam.setString('deptPath', justep.Context.getCurrentPersonMemberFID());
		sendParam.setString('personId', justep.Context.getCurrentPersonCode());
	    options.process = justep.Context.getCurrentProcess();
	    options.activity = justep.Context.getCurrentActivity();
	    options.dataType = "json";
	    options.parameters = sendParam;
	    options.action = "queryPermissionAction";
	    options.directExecute = false;    
	    var response = justep.Request.sendBizRequest2(options);
		
		if(!justep.Request.isBizSuccess(response,'json')){
			throw new Error("getDocAuthListError: 获取文档权限列表失败！");
		}
		return justep.Request.responseParseJSON(response).data;
	}
};


justep.Doc.ProgressTable = function(config){ 
  this.columns = config.columns;
  this.tbObj = document.createElement("table"); 
  this.tbObj.border = "1px";
  this.tbObj.style.borderColor = "#DDDDDD";
  this.tbObj.cellpadding = "0";
  this.tbObj.cellspacing="0";
  this.tbObj.style.fontSize = "9pt";
  this.tbObj.style.borderCollapse = "collapse";
  document.getElementById(config.renderTo).appendChild(this.tbObj);
 
  this.hd = this.tbObj.insertRow(0);
  for(var i=0;i<this.columns.length;i++){
  	if(!this.columns[i].hide){
	  	var cell = this.hd.insertCell(i);
	  	cell.style.backgroundColor="#EDEEF0";
	  	cell.valign = "center";
	  	cell.width = this.columns[i].width || "100";
	  	cell.innerHTML=this.columns[i].text;
  	}
  }
};

justep.Doc.ProgressTable.prototype.appendRow = function(record){ 
	var rIdx = this.tbObj.rows.length;
	var r = this.tbObj.insertRow(rIdx);
	r.style.backgroundColor= "#FAFAFA";
	for(var i=0;i<this.columns.length;i++){
		var cell = r.insertCell(i);
		var render = this.columns[i].render;
		var value = record[this.columns[i].name];
		if(render){
			value = render.call(this,value,record);
		}
		cell.innerHTML=value ||'&nbsp;';
		/*
		cell.style.borderLeft="1px solid #CCCCCC";
		cell.style.borderRight="1px solid #CCCCCC";
		cell.style.borderBottom="1px solid #CCCCCC";
		*/
	}
};

justep.Doc.ProgressTable.prototype.loadData = function(dm){
	if(!dm) return;
	this.deleteAllRow();
	this.dm = dm;
	for(var i =0 ;i<this.dm.length;i++){
		this.appendRow(this.dm[i]);
	}
};

justep.Doc.ProgressTable.prototype.reload = function(){
	this.loadData(this.dm);
};

justep.Doc.ProgressTable.prototype.find = function(fieldName,value){ 
	for(var i = 0;i<this.dm.length;i++){
		if(this.dm[i][fieldName] == value){
			return i;
		}
	}
	return -1;
};

justep.Doc.ProgressTable.prototype.setValue = function(idx,fieldName,value){
	var cRow = this.tbObj.rows[idx];
	var cellIdx = -1;
	for(var i=0;i<this.columns.length;i++){
		if(this.columns[i].name == fieldName){
			var render = this.columns[i].render;
			if(render){ 
				value = render.call(this,value);
			}
			cRow.cells[i].innerHTML = value;
			break;
		}
	}
};


justep.Doc.ProgressTable.prototype.deleteAllRow = function(){ 
	var length = this.tbObj.rows.length;
	for (i=1;i<length;i++) {
         this.tbObj.deleteRow(1);
	}
	this.dm = [];
};

justep.Doc.ProgressTable.prototype.deleteByField = function(fieldName,value){ 
	var idx = -1;
	for(var i=0;i<this.dm.length;i++){
		if(this.dm[i][fieldName]== value){
			idx = i;
			break;
		}
	}
	if(idx != -1){
		 this.tbObj.deleteRow(idx+1);
		 this.dm.splice(idx,1);
	}
};

justep.Doc.ProgressTable.createCancalButton = function (id) {
    return "<img id=" + id + " src='" + justep.Request.convertURL('/UI/system/images/doc/deletefile.gif',true) + "' onclick='justep.Doc.ProgressTable.cancal(this.id);' alt='取消上传'/>"; 
};

justep.Doc.ProgressTable.cancal = function(id){
	if(justep.Doc.uploadPprogressTable){
		var uploader = justep.Doc.currentUploader;
		uploader.cancel(id);
		uploader.removeFile(id);
		--uploader.allCount;
		if(uploader.allCount==0) justep.Doc.getUploadProgressDialog().close();
  	    justep.Doc.uploadPprogressTable.deleteByField("id",id);
	}
};
