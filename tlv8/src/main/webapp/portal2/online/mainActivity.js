$(document).ready(
		function() {
			var re = tlv8.XMLHttpRequest("system/User/getOnlineUserInfo",
					null, "POST", false);
			if (typeof re == "string") {
				re = window.eval("(" + re + ")");
			}
			re = re[0];
			var data;
			if (re.status == "SUCCESS") {
				data = re.data;
			}
			if (typeof data == "string") {
				data = window.eval("(" + data + ")");
			}
			data = data.data;
			var filter = [];
			for (var i = 0; i < data.length; i++) {
				filter.push(" '" + data[i].userfullid
						+ "' like concat(SFID,'%') ");
			}
			/* =========创建树========== */
			var param = {
				cell : {
					databaseName : "system",// 数据库
					tableName : "SA_OPORG_VIEW",// 对应的表名
					id : "SID",// 设置构建树的id
					name : "SNAME",// 树显示的名称
					parent : "SPARENT",// 表示树的层级
					other : "SORGKINDID,SFCODE,SFNAME",// 树中所带字段信息
					rootFilter : " SPARENT is null", // 跟节点条件
					filter : filter.join(" or "),
					orderby : "SSEQUENCE asc" // 排序字段
				}
			};
			var setting = {
				view : {
					selectedMulti : false, // 设置是否允许同时选中多个节点。默认值: true
					autoCancelSelected : false,
					dblClickExpand : true
				// 双击展开
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				async : {
					enable : true, // 异步加载
					url : "TreeSelectAction",// 加载数据的Action,可以自定义
					autoParam : [ "id=currenid" ]
				},
				isquickPosition : {
					enable : true, // 是否有快速查询框
					url : "QuickTreeAction",
					quickCells : "SNAME",// 用于快速查询的字段
					path : "SFID"// 查询路径字段
				}
			};

			var MainJtree = new Jtree();
			MainJtree.init("JtreeView", setting, param);
		});