var staticGrid;
function initPage() {
	staticGrid = new StaticGrid("fieldGrid"); //实例化类 
	var staticgridconfig = [ {
		id : 'sFieldID',
		name : '域标识',
		width : 100
	}, {
		id : 'sFieldName',
		name : '域名称',
		width : 80
	} ];
	staticGrid.init(staticgridconfig); //初始化 
}
