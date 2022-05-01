var setting = {
	databaseName : "oa",
	tableName : "OA_NOTICE_PERSON_VIEW",
	idcolumn : "FID",
	title : {
		label : "",
		column : "FTITLE"
	},
	texts : {
		label : "",
		column : "FCREATENAME"
	},
	ellips : {
		label : "",
		column : "FPUSHDATETIME"
	},
	staticfilter : "",
	orderby : "",
	pagelimit : 10,
	onclick : function(id) {
		var url = cpath+"/mobileUI/oa/notice/publicnotice/show_view.html?rowid="
				+ id;
		window.open(url, "_self");
	}
};

var moblist;
$(document).ready(
		function() {
			moblist = new tlv8.MobileList(document
					.getElementById("contenView"), setting);
			moblist.init();
		});

function doSearch(searchText) {
	moblist.doSearch(searchText);
}