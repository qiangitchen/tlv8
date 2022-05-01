var portalConfig = {};

portalConfig.defaultsLid = "l2_0";

portalConfig.layouts = {
	l1_0 : [ '100%' ],
	l2_0 : [ '50%', '50%' ],
	l2_1 : [ '60%', '40%' ],
	l2_2 : [ '70%', '30%' ],
	l2_3 : [ '30%', '70%' ],
	l2_3 : [ '40%', '60%' ],
	l3_0 : [ '33%', '33%', '33%', ],
	l3_1 : [ '60%', '20%', '20%' ],
	l3_2 : [ '20%', '60%', '20%' ],
	l3_3 : [ '20%', '20%', '60%' ],
	l3_4 : [ '100%', '50%', '50%' ],
	l4_0 : [ '25%', '25%', '25%', '25%' ],
	l4_1 : [ '30%', '25%', '25%', '20%' ],
	l4_2 : [ '30%', '30%', '20%', '20%' ],
	l4_3 : [ '30%', '30%', '30%', '10%' ],
	l4_4 : [ '10%', '30%', '30%', '30%' ],
	l4_5 : [ '25%', '25%', '30%', '20%' ]
};

portalConfig.layoutsToolbar = {
	l1 : {
		category : "一个区域",
		layoutIDs : "l1"
	},
	l2 : {
		category : "二个区域",
		layoutIDs : "l2"
	},
	l3 : {
		category : "三个区域",
		layoutIDs : "l3"
	},
	l4 : {
		category : "四个区域",
		layoutIDs : "l4"
	}
};

var portal_index = {
	stic : {
		ids : 't001',
		title : '首页'
	}
};

var PortalLet_DATA = [ {
	id : 'TaskSubmit',
	title : '待办任务',
	thumbnail : 'x5/css/d/options/widgets/taskWaiting.png',
	refresh : 'true',
	height : 320,
	psmCount : 0,
	arguments : {
		url : cpath + '/flw/flwcommo/taskpoLet/wait_task_view.html',
		process : '/SA/task/taskView/taskViewProcess',
		activity : 'mainActivity',
		params : ''
	}
}, {
	id : 'DAYREMAND',
	title : '邮件提醒',
	thumbnail : '',
	refresh : 'true',
	height : 320,
	psmCount : 1,
	arguments : {
		url : cpath + '/OA/email/portalShow/Show.html',
		process : '',
		activity : '',
		params : ''
	}
}, {
	id : 'notes',
	title : '通知公告',
	thumbnail : '',
	refresh : 'true',
	height : 320,
	psmCount : 0,
	arguments : {
		url : cpath + '/OA/notice/publicnotice/shownews.html',
		process : '',
		activity : '',
		params : ''
	}
}/*
	 * , { id : 'news', title : '新闻', thumbnail : '', refresh : 'true', height :
	 * 230, psmCount : 0, arguments : { url :
	 * cpath+'/system/News/informationRelase/shownews.html', process : '',
	 * activity : '', params : '' } }
	 */, {
	id : 'forum',
	title : '讨论区',
	thumbnail : '',
	refresh : 'false',
	height : 320,
	psmCount : 1,
	arguments : {
		url : cpath + '/system/News/forum/BO_Shows.html',
		process : '',
		activity : '',
		params : ''
	}
}
/*
 * , { id : 'TaskWaiting', title : '日程安排', height : 230, refresh : 'true',
 * psmCount : 0, arguments : { url :
 * '/JBIZ/SA/personal/schedule/enterprise_affairsShow.html', process :
 * '/SA/task/taskView/taskViewProcess', activity : 'mainActivity', params : '' } }
 */
];