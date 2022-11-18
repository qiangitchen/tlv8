var TANGER_OCX_bDocOpen = false;
var TANGER_OCX_filename;
var TANGER_OCX_actionURL; // For auto generate form fiields
var TANGER_OCX_OBJ; // The Control
var TANGER_OCX_str;
var TANGER_OCX_obj;

function TANGER_OCX_Scanner() {
	if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType == 2) {

	} else {
		TANGER_OCX_OBJ.AddPicFromScanner();
	}
}

// 设定文档的编辑人员
function TANGER_OCX_SetDocUser(cuser) {
	if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
		with (TANGER_OCX_OBJ.ActiveDocument.Application) {
			UserName = cuser;
		}
	}
}

// 以下函数执行Form的Onsubmit函数，如果定义了的话
function TANGER_OCX_doFormOnSubmit() {
	var form = document.forms[0];
	if (form.onsubmit) {
		var retVal = form.onsubmit();
		if (typeof retVal == "boolean" && retVal == false)
			return false;
	}
	return true;
}

// 检查文件名。并调用保存编辑到服务器的函数
function TANGER_OCX_SaveEditToServer(url) {
	if (!TANGER_OCX_bDocOpen) {
		alert("没有打开的文档。");
		return;
	}
	TANGER_OCX_filename = document.all.item("filename").value;

	if ((!TANGER_OCX_filename)) {
		TANGER_OCX_filename = "";
		return;
	} else if (strtrim(TANGER_OCX_filename) == "") {
		alert("您必须输入文件名！");
		return;
	}
	// alert(TANGER_OCX_filename);
	TANGER_OCX_SaveDoc(url);
}
// 执行保存到服务器的代码。调用了控件的SaveToURL函数
function TANGER_OCX_SaveDoc(url) {
	var retStr = new String;
	var newwin, newdoc;
	if (!TANGER_OCX_bDocOpen) {
		alert("没有打开的文档。");
		return;
	}
	try {
		if (!TANGER_OCX_doFormOnSubmit())
			return;

		retStr = TANGER_OCX_OBJ.SaveToURL(url, "FILE1", "",
				TANGER_OCX_filename, 0);
		saveFormData();
	} catch (err) {
	} finally {
	}
}

// 以下为V1.7新增函数示例

// 从本地增加图片到文档指定位置
function AddPictureFromLocal() {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddPicFromLocal("", // 路径
		true,// 是否提示选择文件
		true,// 是否浮动图片
		100,// 如果是浮动图片，相对于左边的Left 单位磅
		100); // 如果是浮动图片，相对于当前段落Top
	}
}

// 从URL增加图片到文档指定位置
function AddPictureFromURL(URL) {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddPicFromURL(URL,// URL 注意；URL必须返回Word支持的图片类型。
		true,// 是否浮动图片
		150,// 如果是浮动图片，相对于左边的Left 单位磅
		150);// 如果是浮动图片，相对于当前段落Top
	}
}

// 从本地增加印章文档指定位置
function AddSignFromLocal() {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddSignFromLocal("匿名用户",// 当前登陆用户
		"",// 缺省文件
		true,// 提示选择
		0,// left
		0); // top
	}
}

// 从URL增加印章文档指定位置
function AddSignFromURL(URL) {
	if (TANGER_OCX_bDocOpen) {
		try {
			TANGER_OCX_OBJ.AddSignFromURL("匿名用户",// 当前登陆用户
			URL,// URL
			18,// left
			-75, '', 1, '', 0); // top
		} catch (error) {
		}
	}
}

// 开始手写签名
function DoHandSign() {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.DoHandSign2("匿名用户", ""); // top//可选参数
	}
}
// 开始手工绘图，可用于手工批示
function DoHandDraw() {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.DoHandDraw2();
	}
}
// 检查签名结果
function DoCheckSign() {
	if (TANGER_OCX_bDocOpen) {
		/*
		 * 可选参数 IsSilent 缺省为FAlSE，表示弹出验证对话框,否则，只是返回验证结果到返回值
		 */
		var ret = TANGER_OCX_OBJ.DoCheckSign(false, TANGER_OCX_key);// 返回值，验证结果字符串
		// alert(ret);
	}
}
// 此函数用来加入一个自定义的文件头部
function TANGER_OCX_AddDocHeader(strHeader) {
	var i, cNum = 30;
	var lineStr = "";
	try {
		for (i = 0; i < cNum; i++)
			lineStr += "_"; // 生成下划线
		with (TANGER_OCX_OBJ.ActiveDocument.Application) {
			Selection.HomeKey(6, 0); // go home
			Selection.TypeText(strHeader);
			Selection.TypeParagraph(); // 换行
			Selection.TypeText(lineStr); // 插入下划线
			// Selection.InsertSymbol(95,"",true); //插入下划线
			Selection.TypeText("★");
			Selection.TypeText(lineStr); // 插入下划线
			Selection.TypeParagraph();
			// Selection.MoveUp(5, 2, 1); //上移两行，且按住Shift键，相当于选择两行
			Selection.HomeKey(6, 1); // 选择到文件头部所有文本
			Selection.ParagraphFormat.Alignment = 1; // 居中对齐
			with (Selection.Font) {
				NameFarEast = "宋体";
				Name = "宋体";
				Size = 12;
				Bold = false;
				Italic = false;
				Underline = 0;
				UnderlineColor = 0;
				StrikeThrough = false;
				DoubleStrikeThrough = false;
				Outline = false;
				Emboss = false;
				Shadow = false;
				Hidden = false;
				SmallCaps = false;
				AllCaps = false;
				Color = 255;
				Engrave = false;
				Superscript = false;
				Subscript = false;
				Spacing = 0;
				Scaling = 100;
				Position = 0;
				Kerning = 0;
				Animation = 0;
				DisableCharacterSpaceGrid = false;
				EmphasisMark = 0;
			}
			Selection.MoveDown(5, 3, 0); // 下移3行
		}
	} catch (err) {
		// alert("错误：" + err.number + ":" + err.description);
	} finally {
	}
}
function strtrim(value) {
	return value.replace(/^\s+/, '').replace(/\s+$/, '');
}

// 允许或禁止显示修订工具栏和工具菜单（保护修订）
function TANGER_OCX_EnableReviewBar(boolvalue) {
	if (TANGER_OCX_OBJ.DocType == 1) {
		if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
			TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = boolvalue;
			TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = boolvalue;
			TANGER_OCX_OBJ.IsShowToolMenu = boolvalue; // 关闭或打开工具菜单
		}
	} else {
		// 审阅工具栏
		// TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled =
		// boolvalue;
		// 修 订工具栏
		// TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled =
		// boolvalue;
		//
		// TANGER_OCX_OBJ.IsShowToolMenu = boolvalue; //关闭或打开工具菜单
	}
}

// 打开或者关闭修订模式
function TANGER_OCX_SetReviewMode(boolvalue) {
	if (TANGER_OCX_OBJ.DocType == 1) {
		if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
			TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = boolvalue;
		}

	} else {
		TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = boolvalue;
	}

}

function TANGER_OCX_SetIsReadOnly(boolvalue) {
	if (boolvalue == true) {
		if (TANGER_OCX_OBJ.DocType == 1) {
			if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
				TANGER_OCX_OBJ.SetReadOnly(true, "", 2);
			} else {
				try {
					TANGER_OCX_OBJ.ActiveDocument.Unprotect();
					TANGER_OCX_OBJ.SetReadOnly(true, "", 2);
					TANGER_OCX_OBJ.ActiveDocument.Protect(2, true, "");
				} catch (error) {
				}
			}
		} else {
			TANGER_OCX_OBJ.SetReadOnly(true, "", 2);

		}
	} else {
		if (TANGER_OCX_OBJ.DocType == 1) {
			if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
				TANGER_OCX_OBJ.SetReadOnly(false, "", 2);
			} else {
				TANGER_OCX_OBJ.SetReadOnly(false, "", 2);
				try {
					TANGER_OCX_OBJ.ActiveDocument.Unprotect();

					TANGER_OCX_OBJ.ActiveDocument.Protect(2, true, "");
				} catch (error) {
				}
			}
		} else {
			TANGER_OCX_OBJ.SetReadOnly(false, "", 2);
		}
	}

}

// 进入或退出痕迹保留状态，调用上面的两个函数
function TANGER_OCX_SetMarkModify(boolvalue) {
	if (TANGER_OCX_OBJ.DocType == 1) {
		if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
			TANGER_OCX_SetReviewMode(boolvalue);
			TANGER_OCX_EnableReviewBar(!boolvalue);
		} else {

		}
	} else {
		TANGER_OCX_SetReviewMode(boolvalue);
		TANGER_OCX_EnableReviewBar(!boolvalue);
	}
}

// 显示/不显示修订文字
function TANGER_OCX_ShowRevisions(boolvalue) {
	if (TANGER_OCX_OBJ.DocType == 1) {
		if (TANGER_OCX_OBJ.ActiveDocument.ProtectionType != 2) {
			TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = boolvalue;
		} else {
			try {
				TANGER_OCX_OBJ.ActiveDocument.Unprotect();
				TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = boolvalue;
				TANGER_OCX_OBJ.ActiveDocument.Protect(2, true, "");
			} catch (error) {
			}
		}
	} else {
		TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = boolvalue;
	}
}

// 打印/不打印修订文字
function TANGER_OCX_PrintRevisions(boolvalue) {
	TANGER_OCX_OBJ.ActiveDocument.PrintRevisions = boolvalue;
}

// 设置页面布局
function TANGER_OCX_ChgLayout() {
	try {
		TANGER_OCX_OBJ.showdialog(5); // 设置页面布局
	} catch (err) {
		alert("错误：" + err.number + ":" + err.description);
	} finally {
	}
}

// 打印文档
function TANGER_OCX_PrintDoc() {
	try {

		TANGER_OCX_OBJ.PrintOut(true);
	} catch (err) {
		alert("错误：" + err.number + ":" + err.description);
	} finally {
	}
}

function TANGER_OCX_SetNoCopy(boolvalue) {
	TANGER_OCX_OBJ.IsNoCopy = boolvalue;
}

// 允许或禁止文件－>新建菜单
function TANGER_OCX_EnableFileNewMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[0] = boolvalue;
}
// 允许或禁止文件－>打开菜单
function TANGER_OCX_EnableFileOpenMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[1] = boolvalue;
}
// 允许或禁止文件－>关闭菜单
function TANGER_OCX_EnableFileCloseMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[2] = boolvalue;
}
// 允许或禁止文件－>保存菜单
function TANGER_OCX_EnableFileSaveMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[3] = boolvalue;
}
// 允许或禁止文件－>另存为菜单
function TANGER_OCX_EnableFileSaveAsMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[4] = boolvalue;
}
// 允许或禁止文件－>打印菜单
function TANGER_OCX_EnableFilePrintMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[5] = boolvalue;
}
// 允许或禁止文件－>打印预览菜单
function TANGER_OCX_EnableFilePrintPreviewMenu(boolvalue) {
	TANGER_OCX_OBJ.EnableFileCommand[6] = boolvalue;
}

function TANGER_OCX_OnDocumentOpened(str, obj) {
	TANGER_OCX_bDocOpen = true;
}

function TANGER_OCX_OnDocumentClosed() {
	TANGER_OCX_bDocOpen = false;
}

function setToolBar() {
	TANGER_OCX_OBJ.ToolBars = !TANGER_OCX_OBJ.ToolBars;
}
//
function setMenubar() {
	TANGER_OCX_OBJ.Menubar = !TANGER_OCX_OBJ.Menubar;
}

function setInsertMemu() {
	TANGER_OCX_OBJ.IsShowInsertMenu = !TANGER_OCX_OBJ.IsShowInsertMenu;
}
//
function setEditMenu() {
	TANGER_OCX_OBJ.IsShowEditMenu = !TANGER_OCX_OBJ.IsShowEditMenu;
}

function setToolMenu() {
	TANGER_OCX_OBJ.IsShowToolMenu = !TANGER_OCX_OBJ.IsShowToolMenu;
}

//接受所有修订
function TANGER_OCX_AcceptAllRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
}