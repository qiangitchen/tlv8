package com.tlv8.flw;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Data;
import com.tlv8.flw.base.FlowFile;
import com.tlv8.flw.base.TaskData;
import com.tlv8.flw.bean.FlowBotBean;
import com.tlv8.flw.bean.FlowBotXBean;
import com.tlv8.flw.bean.FlowDataBean;

/**
 * @author ChenQian @category
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class FlowBotControler extends FlowDataBean {
	Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	/*
	 * 加载波特图
	 */
	@ResponseBody
	@RequestMapping("/flowloadbotAction")
	public Object loadbot() {
		try {
			FlowBotBean bean = new FlowBotBean(flowID);
			data.setData(bean.toJsonStr());
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			System.out.println(e.toString());
		}
		return this;
	}

	/*
	 * 加载波特图X
	 */
	@ResponseBody
	@RequestMapping("/flowloadbotXAction")
	public Object loadbotX() {
		try {
			FlowBotXBean bean = new FlowBotXBean(flowID);
			data.setData(bean.toJSONStr());
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			System.out.println(e.toString());
		}
		return this;
	}

	/*
	 * 加载流程图
	 */
	@ResponseBody
	@RequestMapping("/flowloadIocusAction")
	public Object loadIocus() {
		try {
			processID = TaskData.getProcessID(flowID);
			if (processID == null || "".equals(processID)) {
				processID = FlowControler.seachProcessID(currentUrl, request);
			}
			Map m = FlowFile.getFlowDraw(processID);// 获取流程图
			// String sdrawlg = (String) m.get("SDRAWLG");
			String SPROCESSID = (String) m.get("SPROCESSID");
			String SPROCESSNAME = (String) m.get("SPROCESSNAME");
			String SPROCESSACTY = (String) m.get("SPROCESSACTY");
			Map<String, String> reD = new HashMap<String, String>();
			reD.put("id", SPROCESSID);
			reD.put("name", SPROCESSNAME);
			reD.put("jsonStr", SPROCESSACTY);
			data.setData(JSON.toJSONString(reD));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/*
	 * 加载流程图
	 */
	@ResponseBody
	@RequestMapping("/flowloadIocusXAction")
	public Object loadIocusX() {
		try {
			Map m = FlowFile.getFlowDraw(processID);// 获取流程图
			String SPROCESSID = (String) m.get("SPROCESSID");
			String SPROCESSNAME = (String) m.get("SPROCESSNAME");
			String SPROCESSACTY = (String) m.get("SPROCESSACTY");
			Map<String, String> reD = new HashMap<String, String>();
			reD.put("id", SPROCESSID);
			reD.put("name", SPROCESSNAME);
			reD.put("jsonStr", SPROCESSACTY);
			data.setData(JSON.toJSONString(reD));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	/*
	 * 检测流程是否已经结束
	 */
	@ResponseBody
	@RequestMapping("/flowcheckfinishAction")
	public Object checkfinish() {
		try {
			boolean isfinish = TaskData.checkisfinished(flowID);
			data.setData(String.valueOf(isfinish));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

}
