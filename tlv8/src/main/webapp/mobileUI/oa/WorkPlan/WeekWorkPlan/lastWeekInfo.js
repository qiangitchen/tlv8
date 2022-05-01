function initBody(rowid, vdate, creatorid) {
	getDetailInfo("detailInfo", rowid, "last", vdate, creatorid);
}

$(document).ready(function() {
	var sData1 = getParamValueFromUrl("rowid");
	var vdate = getParamValueFromUrl("vdate");
	var creatorid = getParamValueFromUrl("creatorid");
	initBody(sData1, vdate, creatorid);
});
