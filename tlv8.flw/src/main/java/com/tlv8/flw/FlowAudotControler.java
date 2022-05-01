package com.tlv8.flw;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.flw.base.TaskData;
import com.tlv8.flw.bean.FlowDataBean;

@Controller
@Scope("prototype")
public class FlowAudotControler extends FlowDataBean {
	private Data data = new Data();

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * 根据流程标识获取流程环节
	 */
	@ResponseBody
	@RequestMapping("/getActivityByTaskID")
	public Object loadActivityByTaskID() {
		try {
			String Activity = TaskData.getCurrentActivity(taskID);
			data.setFlag("true");
			data.setData(Activity);
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}
}
