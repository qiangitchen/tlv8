if (!justep)
	var justep = {};
if (!tlv8)
	tlv8 = {};
tlv8.docTemplete = {
	/**
	 * 打开文档模板
	 * 
	 * @docDiv 打开文档模板后显示在什么位置
	 * @FileID 文件名
	 */
	openDocTemplete : function(docDiv, fileName) {
		if ((typeof $OV) != "function") {
			alert("请先引入/comon/doc_ocx/docUtil.js");
			return "";
		}
		var docURL = "http://localhost:8080/DocServer/repository/file/cache/office"
				+ "?bsessionid=" + 12345678;

		$OV(docDiv).CreateOfficeViewer("100%", "100%");
		$OV(docDiv).HttpInit();
		$OV(docDiv).HttpAddpostString("FileID", "54-DOC");
		$OV(docDiv).HttpAddpostString("FileExt", ".doc");
		$OV(docDiv).HttpOpenFileFromStream(docURL);
		return $OV(docDiv).Application;
	},
	addRen : function(fType, address, fid, wdapp) {
		var list = justep.Request.querySql("pamis.printCloseQueryAssign", [
				fid, fType ]);
		for ( var i = 0; i < list.length; i++) {
			var node = list[i];
			this.insertValueAtBookMark007(node[0] + "  ", address, wdapp);
		}

	},
	createChr : function(wdapp, chr) {
		var selection = wdapp.Selection;
		selection.Font.Name = "Lucida Sans Unicode";
		selection.TypeText(chr);
		selection.ToggleCharacterCode();
		selection.MoveLeft(1, 1, 1);
		selection.Font.Name = "Lucida Sans Unicode";
		selection.Font.Size = 14;
		wdapp.Selection.MoveRight(12, 1, 0);
	},
	/**
	 * 在书签位置插入一个字符
	 */

	insertValueAtBookMark007 : function(value, bookMarkName, wdapp) {
		var selection = wdapp.Selection;
		selection.GoTo(-1, 0, 0, bookMarkName);
		selection.TypeText(value);
	},
	/**
	 * 先输入字符信息然后向右移动一个单元格
	 * 
	 */
	moveRightCell007 : function(value, wdCharacter, wdapp) {
		var selection = wdapp.Selection;
		selection.TypeText(value);
		selection.MoveRight(wdCharacter, 1, 0);
	},
	/**
	 * 在列表的结尾的地方（表格外面），利用回车插入一行
	 */
	newLine007 : function(wdapp) {
		var selection = wdapp.Selection;
		selection.InsertRows(1);
		selection.Collapse(1);
	}
}