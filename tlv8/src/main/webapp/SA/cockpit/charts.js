$(document).ready(function(){
	function loadFilesystemInfo(){
		$("#filesystemchart").removeAttr("_echarts_instance_");
		var myChart = echarts.init(document.getElementById("filesystemchart"), "dark");
		$.get('/tlv8/monitor/FileSystemUsage', function (data) {
			 data = data.data;
 			 if(typeof data == "string"){
 			 	data = window.eval("("+data+")");
 			 }
			 var source1 = ['product'];
			 var source2 = ['未使用(GB)'];
			 var source3 = ['已使用(GB)'];
			 var seriesS = [];
			 var left = 21, top = 30;
			 var legends=['已使用(GB)','未使用(GB)'];
			 var yAxisdatas = [];
			 var seriesdatas = [];
			 var frees = [];
			 var useds = [];
			 for(var m = 0; m < data.length; m++){
			 	var fser = data[m];
			 	source1.push(fser.dirName);
			 	yAxisdatas.push(fser.dirName);
			 	source2.push(parseFloat(fser.Free));
			 	frees.push(parseFloat(fser.Free));
			 	source3.push(parseFloat(fser.Used));
			 	useds.push(parseFloat(fser.Used));
//			 	seriesS.push({
//			        name: fser.dirName,
//			        type: 'pie',
//			        radius: 30,
//			        center: [left+'%', top+'%'],
//			         encode: {
//			            itemName: 'product',
//			            value: fser.dirName
//			        }
//			    });
//			    if(m!=0 && m % 2 == 0){
//			    	left = 21;
//			    }else{
//			    	left += 30;
//			    }
//			    if(m!=0 && m % 2 == 0){
//			    	top += 45;
//			    }
			}
//			 myChart.setOption({
//				backgroundColor:"",
//				color: ['#009688','#1E9FFF'],
//				legend: {},
//			    tooltip: {},
//			 	dataset: {
//			        source: [source1,source2,source3]
//			    },
//			    series: seriesS
//		    });
			 
			 option = {
					    tooltip: {
					        trigger: 'axis',
					        axisPointer: {            // Use axis to trigger tooltip
					            type: 'shadow'        // 'shadow' as default; can also be 'line' or 'shadow'
					        }
					    },
					    backgroundColor:"",
						color: ['#26a0da','#009688'],
					    legend: {
					        data: legends
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    xAxis: {
					        type: 'value'
					    },
					    yAxis: {
					        type: 'category',
					        data: yAxisdatas
					    },
					    series: [
				        {
				            name: '已使用(GB)',
				            type: 'bar',
				            stack: 'total',
				            label: {
				                show: true
				            },
				            emphasis: {
				                focus: 'series'
				            },
				            data: useds
				        },{
				            name: '未使用(GB)',
				            type: 'bar',
				            stack: 'total',
				            label: {
				                show: true
				            },
				            emphasis: {
				                focus: 'series'
				            },
				            data: frees
				        }]
					};
			 myChart.setOption(option);
			delete data;
			setTimeout(loadFilesystemInfo, 60 * 60 * 1000);
		},"json");
	}
	loadFilesystemInfo();
	function loadserverNetInfo(){
		$("#netInfochart").removeAttr("_echarts_instance_");
		var myChart = echarts.init(document.getElementById("netInfochart"), "dark");
		var netoption = {
			backgroundColor:"",
			color: ['#2acff9'],
		    title: {
		    	textStyle: {
		        	fontWeight: 'normal',
			        color: '#666'
			    },
		    	x: 'center',
		        text: '',
		        subtext: '收发数据包'
	    	},
	    	tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: {
		        type: 'value',
		        boundaryGap: [0, 0.01]
		    },
		    yAxis: {
		        type: 'category',
		        data: ['接收','发送']
		    },
		    series: [
		        {
		            name: '总字包数',
		            type: 'bar',
		            data: [0, 0]
		        }
		    ]
		};
		myChart.setOption(netoption);
		$.get('/tlv8/monitor/NetInterfaceStat', function (data) {
			 if(typeof data.data == "string"){
  			 	data = window.eval("("+data.data+")");
  			 }
 			 myChart.setOption({
		        series: [{
		            data: [data.rxPackets,data.txPackets]
		        }]
		    });
		    delete data;
		    setTimeout(loadserverNetInfo, 60 * 1000);
 		},"json");
	}
	loadserverNetInfo();
	var cpuUpxChart,cpuLineChart,memLineChart;
	var cpudata = {x:[],y:[]}, memdata = {x:[],y:[]};
	function initCPUMemCharts(){
		$("#CPUuxpInfochart").removeAttr("_echarts_instance_");
		cpuUpxChart = echarts.init(document.getElementById("CPUuxpInfochart"), "dark");
		$("#CPUlineInfochart").removeAttr("_echarts_instance_");
		cpuLineChart = echarts.init(document.getElementById("CPUlineInfochart"), "dark");
		$("#MemlineInfochart").removeAttr("_echarts_instance_");
		memLineChart = echarts.init(document.getElementById("MemlineInfochart"), "dark");
	}
	initCPUMemCharts();
	function refreshMonitorInfo(){
		 var cpuusxp,memusxp;
		  $.ajax({
				type : "post",
				async : false,
				dataType : "json",
				url : "/tlv8/monitor/CPUPerc",
				success : function(data, textStatus) {
					try {
						data = data.data;
			  			if(typeof data == "string"){
			  			 	data = window.eval("("+data+")");
			  			}
						var usedpex = parseFloat(data.usedpex)||0;
						var now = new Date();
						if(cpudata.x.length>60){
							cpudata.x.shift();
							cpudata.y.shift();
						}
						cpudata.x.push(now.getMinutes()+"′"+now.getSeconds()+"″");
						cpudata.y.push(usedpex);
						cpuLineChart.setOption({
							backgroundColor:"",
							color: ['#1E9FFF'],
						    tooltip: {
						          trigger: 'axis',
						          axisPointer: {
						              animation: false
						          }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: cpudata.x
						    },
						    yAxis: {
						        type: 'value',
						        boundaryGap: [0, '100%'],
						        splitLine: {
						            show: false
						        }
						    },
						    series: [{
						        data: cpudata.y,
						        type: 'line',
						        smooth: true,
						        showSymbol: false,
						        hoverAnimation: true,
						        areaStyle: {}
						    }]
						});
						cpuusxp = usedpex;
					} catch (e) {
					}
					delete data;
				}
			});
		  $.ajax({
				type : "post",
				async : false,
				dataType : "json",
				url : "/tlv8/monitor/MemoryInfo",
				success : function(data, textStatus) {
					try {
						data = data.data;
			  			if(typeof data == "string"){
			  			 	data = window.eval("("+data+")");
			  			}
						var usedpex = parseFloat(data.usedpex)||0;
						var now = new Date();
						if(memdata.x.length>10){
							memdata.x.shift();
							memdata.y.shift();
						}
						memdata.x.push(now.getMinutes()+"′"+now.getSeconds()+"″");
						memdata.y.push(usedpex);
						memLineChart.setOption({
							backgroundColor:"",
							color: ['#009688'],
						    tooltip: {
						          trigger: 'axis',
						          axisPointer: {
						              animation: false
						          }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: memdata.x
						    },
						    yAxis: {
						        type: 'value',
						        boundaryGap: [0, '100%'],
						        splitLine: {
						            show: false
						        }
						    },
						    series: [{
						        data: memdata.y,
						        type: 'line',
						        smooth: true,
						        showSymbol: false,
						        hoverAnimation: true,
						        areaStyle: {}
						    }]
						});
						memusxp = usedpex;
					} catch (e) {
					}
					delete data;
				}
			});
		  cpuUpxChart.setOption({
				backgroundColor:"",
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    series: [
			        {
			            name: '当前使用率',
			            type: 'gauge',
			            center : ['50%', '35%'],    // 默认全局居中
			            radius: '50%',
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: [[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']],
			                    width: 3,
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            axisLabel: {            // 坐标轴小标记
			                textStyle: {       // 属性lineStyle控制线条样式
			                    fontWeight: 'bolder',
			                    color: '#fff',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length :15,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            splitLine: {           // 分隔线
			                length :25,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    width:3,
			                    color: '#fff',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            pointer: {           // 分隔线
			                shadowColor : '#fff', //默认透明
			                shadowBlur: 5
			            },
			            title : {
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder',
			                    fontSize: 20,
			                    fontStyle: 'italic',
			                    color: '#fff',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            detail: {formatter:'{value}%'},
			            data: [{value: cpuusxp, name: 'CPU使用率'}]
			        },
					{
			            name:'当前使用率',
			            type:'gauge',
			            center : ['50%', '75%'],    // 默认全局居中
			            radius : '40%',
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: [[0.29, 'lime'],[0.86, '#1e90ff'],[1, '#ff4500']],
			                    width: 2,
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            axisLabel: {            // 坐标轴小标记
			                textStyle: {       // 属性lineStyle控制线条样式
			                    fontWeight: 'bolder',
			                    color: '#fff',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length :12,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            splitLine: {           // 分隔线
			                length :20,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    width:3,
			                    color: '#fff',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            pointer: {
			                width:5,
			                shadowColor : '#fff', //默认透明
			                shadowBlur: 5
			            },
			            title : {
			                offsetCenter: [0, '-30%'],       // x, y，单位px
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder',
			                    fontStyle: 'italic',
			                    color: '#fff',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            detail: {formatter:'{value}%'},
			            data:[{value: memusxp, name: '内存使用率'}]
			        }
			    ]
			});
		  setTimeout(refreshMonitorInfo,1000);
	}
	refreshMonitorInfo();
	$(window).resize(function(){
		loadFilesystemInfo();
		loadserverNetInfo();
		initCPUMemCharts();
	});
});