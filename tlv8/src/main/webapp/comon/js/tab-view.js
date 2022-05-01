/*创建标签参数说明  页面主要调用：
 * initTabs(mainContainerID,tabTitles,activeTab,width,height,actionArray,closeButtonArray,additionalTab)
 * mainContainerID:定义主外层DIV的id;
 * tabTitles：定义标签名数组；且需要相对应标签数组个数的div；例如有三给标签名 Array('客户信息','客户关系','客户投诉')；页面需三个div
 * activeTab:默认显示的tab，从0开始计算，默认值是：0；
 * width：主层的宽度
 * height：主层的高度
 * actionArray:动作数组Array('loadMainInfo','loadSecondInfo','loadThirdInfo');自定义事件名称用数组传进来，给标签添加单击事件
 * closeButtonArray：设置标签是否可以关闭；例如Array(true,true,true,true);false时，可以省略不写
 * additionalTab：判断是否使用标签
 * 在用initTabs时 主层div里面的div需调用tab-view.css 下的 dhtmlgoodies_aTab样式 ;
 * */

var textPadding = 3; // Padding at the left of tab text - bigger value gives you wider tabs
var strictDocType = true;

/* Don't change anything below here */
var dhtmlgoodies_tabObj = new Array();
var activeTabIndex = new Array();
var MSIE = navigator.userAgent.indexOf('MSIE') >= 0 ? true : false;

var regExp = new RegExp(".*MSIE ([0-9]\.[0-9]).*", "g");//创建正则表达式
var navigatorVersion = navigator.userAgent.replace(regExp, '$1');

var tabView_countTabs = new Array();
var tabViewHeight = new Array();
var tabDivCounter = 0;
var closeImageHeight = 8; // Pixel height of close buttons
var closeImageWidth = 8; // Pixel height of close buttons

function setPadding(obj, padding) {
	var span = obj.getElementsByTagName('SPAN')[0];
	span.style.paddingLeft = padding + 'px';
	span.style.paddingRight = padding + 'px';
}

function showTab(parentId, tabIndex, titlClick) {
	var parentId_div = parentId + "_";
	if (!document.getElementById('tabView' + parentId_div + tabIndex)) {
		return;
	}
	if (activeTabIndex[parentId] >= 0) {
		if (activeTabIndex[parentId] == tabIndex) {
			return;
		}

		var obj = document.getElementById('tabTab' + parentId_div
				+ activeTabIndex[parentId]);

		obj.className = 'tabInactive';
		document.getElementById('tabView' + parentId_div
				+ activeTabIndex[parentId]).style.display = 'none';
	}

	var thisObj = document.getElementById('tabTab' + parentId_div + tabIndex);

	thisObj.className = 'tabActive';

	document.getElementById('tabView' + parentId_div + tabIndex).style.display = 'block';
	activeTabIndex[parentId] = tabIndex;

	var parentObj = thisObj.parentNode;
	var aTab = parentObj.getElementsByTagName('DIV')[0];
	//countObjects = 0;
	var startPos = 2;
	var previousObjectActive = false;
	while (aTab) {
		if (aTab.tagName == 'DIV') {
			if (previousObjectActive) {
				previousObjectActive = false;
				startPos -= 2;
			}
			if (aTab == thisObj) {
				startPos -= 2;
				previousObjectActive = true;
				setPadding(aTab, textPadding + 1);
			} else {
				setPadding(aTab, textPadding);
			}

			aTab.style.left = startPos + 'px';
			//countObjects++;
			startPos += 2;
		}
		aTab = aTab.nextSibling;
	}
	try {
		if (titlClick != true)
			thisObj.click();
	} catch (e) {
	}
	return;
}

function tabClick() {
	var idArray = this.id.split('_');
	showTab(this.parentNode.parentNode.id, idArray[idArray.length - 1].replace(
			/[^0-9]/gi, ''), true);
}

function rolloverTab() {
	if (this.className.indexOf('tabInactive') >= 0) {
		this.className = 'inactiveTabOver';
	}

}
function rolloutTab() {
	if (this.className == 'inactiveTabOver') {
		this.className = 'tabInactive';
	}

}

function hoverTabViewCloseButton() {
	this.src = this.src.replace('close.gif', '../image/tab/close_over.gif');
}

function stopHoverTabViewCloseButton() {
	this.src = this.src.replace('close_over.gif', 'close.gif');
}

function initTabs(mainContainerID, tabTitles, activeTab, width, height,
		actionArray, closeButtonArray, additionalTab) {
	if (!closeButtonArray)
		closeButtonArray = new Array();
	if (!additionalTab || additionalTab == 'undefined') {
		dhtmlgoodies_tabObj[mainContainerID] = document
				.getElementById(mainContainerID);
		width = width + '';
		if (width.indexOf('%') < 0)
			width = width + 'px';
		dhtmlgoodies_tabObj[mainContainerID].style.width = width;
		height = height + '';
		if (height.length > 0) {
			if (height.indexOf('%') < 0)
				height = height + 'px';
			dhtmlgoodies_tabObj[mainContainerID].style.height = height;
		}

		tabViewHeight[mainContainerID] = height;
		var tabDiv = document.createElement('DIV');
		var firstDiv = dhtmlgoodies_tabObj[mainContainerID]
				.getElementsByTagName('DIV')[0];

		dhtmlgoodies_tabObj[mainContainerID].insertBefore(tabDiv, firstDiv);
		tabDiv.className = 'dhtmlgoodies_tabPane';
		tabView_countTabs[mainContainerID] = 0;

	} else {
		var tabDiv = dhtmlgoodies_tabObj[mainContainerID]
				.getElementsByTagName('DIV')[0];
		var firstDiv = dhtmlgoodies_tabObj[mainContainerID]
				.getElementsByTagName('DIV')[1];
		height = tabViewHeight[mainContainerID];
		activeTab = tabView_countTabs[mainContainerID];
	}

	for ( var no = 0; no < tabTitles.length; no++) {
		var aTab = document.createElement('DIV');
		aTab.id = 'tabTab' + mainContainerID + "_"
				+ (no + tabView_countTabs[mainContainerID]);
		aTab.onmouseover = rolloverTab;
		aTab.onmouseout = rolloutTab;
		aTab.onclick = tabClick;
		aTab.className = 'tabInactive';
		if (actionArray) {
			addEvent(aTab, 'click', actionArray[no], false);
		}

		tabDiv.appendChild(aTab);
		var span = document.createElement('SPAN');
		span.innerHTML = tabTitles[no];
		span.style.position = 'relative';

		aTab.appendChild(span);
		if (closeButtonArray[no]) {
			var closeButton = document.createElement('IMG');
			closeButton.src = '../image/tab/close.gif';
			closeButton.height = closeImageHeight + 'px';
			closeButton.width = closeImageHeight + 'px';
			closeButton.setAttribute('height', closeImageHeight);
			closeButton.setAttribute('width', closeImageHeight);
			closeButton.style.position = 'absolute';
			closeButton.style.top = '6px';
			closeButton.style.right = '0px';
			closeButton.onmouseover = hoverTabViewCloseButton;
			closeButton.onmouseout = stopHoverTabViewCloseButton;

			span.innerHTML = span.innerHTML + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';

			var deleteTxt = span.innerHTML + '';

			closeButton.onclick = function() {
				deleteTab(this.parentNode.innerHTML);
			};
			span.appendChild(closeButton);
		}
	}

	var tabs = dhtmlgoodies_tabObj[mainContainerID].getElementsByTagName('DIV');
	var divCounter = 0;
	for ( var no = 0; no < tabs.length; no++) {
		if (tabs[no].className == 'dhtmlgoodies_aTab'
				&& tabs[no].parentNode.id == mainContainerID) {
			if (height.length > 0)
				tabs[no].style.height = height;
			tabs[no].style.display = 'none';
			tabs[no].id = 'tabView' + mainContainerID + "_" + divCounter;
			divCounter++;
		}
	}
	tabView_countTabs[mainContainerID] = tabView_countTabs[mainContainerID]
			+ tabTitles.length;
	showTab(mainContainerID, activeTab);

	return activeTab;

}

if (!checkPathisHave($dpcsspath + "tab-view.css")) {
	createStyleSheet($dpcsspath + "tab-view.css");
}
