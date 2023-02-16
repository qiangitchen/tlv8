function getData() {
	var columns = [{"field":"sname","width":"100","title":"sname"}];
	var data = new tlv8.Data();
	data.setDbkey("system");
	data.setTable("sa_oporg");
	var treegrid = new tlv8.treeGrid("#treeGridView", "sid", "sname",
			"sparent", columns, "100%", "100%", data, "#tb");
	treegrid.refreshData();
}