/**
 * 
 */
// 获取机构序号
function getOrgIndex(pID) {
	try {
		var sql = "select count(*) CUN from SA_OPORG where SPARENT = '" + pID
				+ "'";
		var result = tlv8.sqlQueryAction("system", sql, null, false);
		var count = result.getValueByName("CUN");
		return parseInt(count) + 1;
	} catch (e) {
		return 1;
	}
}
// 获取人员序号
function getPsnIndex(pID) {
	try {
		var sql = "select count(*) CUN from SA_OPPERSON where SMAINORGID = '"
				+ pID + "'";
		var result = tlv8.sqlQueryAction("system", sql, null, false);
		var count = result.getValueByName("CUN");
		return parseInt(count) + 1;
	} catch (e) {
		return 1;
	}
}
// 获取机构层级
function getOrgLEVEL(pID) {
	if (!pID || pID == "")
		return 1;
	try {
		var sql = "select SLEVEL from SA_OPORG where SID = '" + pID + "'";
		var result = tlv8.sqlQueryAction("system", sql, null, false);
		var count = result.getValueByName("SLEVEL");
		if (count == "null" || count == "") {
			count = 0;
		}
		return parseInt(count) + 1;
	} catch (e) {
		return 1;
	}
}