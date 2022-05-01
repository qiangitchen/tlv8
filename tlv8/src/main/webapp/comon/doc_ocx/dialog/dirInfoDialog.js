var gridData;
var getUrlParam = function(data) {
	gridData = data;
	var docInfo = new tlv8.Data();
	docInfo.setFormId("docInfo_form");
	document.getElementById("docInfo_form").reset();

	docInfo.setValueByName("sDocName", gridData.get("sDocName"));
	docInfo.setValueByName("sKind", gridData.get("sKind"));
	docInfo.setValueByName("sSize", gridData.get("sSize"));
	docInfo.setValueByName("sDocDisplayPath", gridData.get("sDocDisplayPath"));
	docInfo.setValueByName("sCreatorName", gridData.get("sCreatorName"));
	docInfo.setValueByName("sCreateTime", gridData.get("sCreateTime"));
	docInfo
			.setValueByName("sCreatorDeptName", gridData
					.get("sCreatorDeptName"));
	docInfo.setValueByName("sEditorName", gridData.get("sEditorName"));
	docInfo.setValueByName("sEditorDeptName", gridData.get("sEditorDeptName"));
	docInfo.setValueByName("sLastWriterDeptName", gridData
			.get("sLastWriterDeptName"));
	docInfo.setValueByName("sLastWriterName", gridData.get("sLastWriterName"));
	docInfo.setValueByName("sLastWriteTime", gridData.get("sLastWriteTime"));
	docInfo
			.setValueByName("sDocSerialNumber", gridData
					.get("sDocSerialNumber"));
	docInfo.setValueByName("sFinishTime", gridData.get("sFinishTime"));
	docInfo.setValueByName("sClassification", gridData.get("sClassification"));
	docInfo.setValueByName("sKeywords", gridData.get("sKeywords"));
	docInfo.setValueByName("sDescription", gridData.get("sDescription"));
}

function getDocInfoData() {
	document.getElementById("docInfo_form").blur(true);
	var docInfo = new tlv8.Data();
	docInfo.setFormId("docInfo_form");
	if (docInfo.isChanged) {
		gridData.setValueByName("sDocName", docInfo.get("sDocName"));
		gridData.setValueByName("sKind", docInfo.get("sKind"));
		gridData.setValueByName("sSize", docInfo.get("sSize"));
		gridData.setValueByName("sDocDisplayPath", docInfo
				.get("sDocDisplayPath"));
		gridData.setValueByName("sCreatorName", docInfo.get("sCreatorName"));
		gridData.setValueByName("sCreateTime", docInfo.get("sCreateTime"));
		gridData.setValueByName("sCreatorDeptName", docInfo
				.get("sCreatorDeptName"));
		gridData.setValueByName("sEditorName", docInfo.get("sEditorName"));
		gridData.setValueByName("sEditorDeptName", docInfo
				.get("sEditorDeptName"));
		gridData.setValueByName("sLastWriterDeptName", docInfo
				.get("sLastWriterDeptName"));
		gridData.setValueByName("sLastWriterName", docInfo
				.get("sLastWriterName"));
		gridData
				.setValueByName("sLastWriteTime", docInfo.get("sLastWriteTime"));
		gridData.setValueByName("sDocSerialNumber", docInfo
				.get("sDocSerialNumber"));
		gridData.setValueByName("sFinishTime", docInfo.get("sFinishTime"));
		gridData.setValueByName("sClassification", docInfo
				.get("sClassification"));
		gridData.setValueByName("sKeywords", docInfo.get("sKeywords"));
		gridData.setValueByName("sDescription", docInfo.get("sDescription"));
	}
}