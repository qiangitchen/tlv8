/**
 * 
 */
var upload = layui.upload;
var demoListView = $('#demoList')
  	,uploadListIns = upload.render({
    elem: '#testList'
	,url: cpath+'/utils/layuiFileUploadAction' //上传接口
	,accept: 'file'
	,multiple: true
	,dataType: 'json'
	,data: {dbkey:dbkey,rowid:fid,tablename:table,cellname:cellname,docPath:docPath}
	,bindAction: '#testListAction'
	,choose: function(obj){   
	  var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	  //读取本地文件
	  obj.preview(function(index, file, result){
	    var tr = $(['<tr id="upload-'+ index +'">'
	      ,'<td>'+ file.name +'</td>'
	      ,'<td>'+ (file.size/1024).toFixed(1) +'kb</td>'
	      ,'<td>等待上传</td>'
	      ,'<td>'
	        ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
	        ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
	      ,'</td>'
	    ,'</tr>'].join(''));
	    
	    //单个重传
	    tr.find('.demo-reload').on('click', function(){
	      obj.upload(index, file);
	    });
	    
	    //删除
	    tr.find('.demo-delete').on('click', function(){
	      delete files[index]; //删除对应的文件
	      tr.remove();
	      uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	    });
	    
	    demoListView.append(tr);
	  });
	},
	before: function(obj){
		layui.layer.load();
	}
	,done: function(res, index, upload){
		layui.layer.closeAll('loading');
		if(res.code != '-1'){ //上传成功
		    var tr = demoListView.find('tr#upload-'+ index)
		    ,tds = tr.children();
		    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
		    tds.eq(3).html(''); //清空操作
		    return delete this.files[index]; //删除文件队列已经上传成功的文件
		}
		this.error(index, upload);
	}
	,error: function(index, upload){
	  layui.layer.closeAll('loading');
	  var tr = demoListView.find('tr#upload-'+ index)
	  ,tds = tr.children();
	  tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
	  tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
	}
});
  