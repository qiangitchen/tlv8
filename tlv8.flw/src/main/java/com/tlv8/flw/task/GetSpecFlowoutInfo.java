package com.tlv8.flw.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.flw.base.FlowActivity;
import com.tlv8.flw.base.FlowProcess;
import com.tlv8.flw.base.TaskData;
import com.tlv8.flw.bean.FlowDataBean;
import com.tlv8.flw.expression.BooleanExpression;

/**
 * 特送
 */
@Controller
@Scope("prototype")
public class GetSpecFlowoutInfo extends FlowDataBean {
	private Data data = new Data();

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/GetSpecFlowoutInfoAction")
	public Object execute() throws Exception {
		Sys.printMsg("特送...");
		try {
			String processID = TaskData.getCurrentProcessID(taskID);
			Sys.printMsg("processID:" + processID);
			flowID = TaskData.getFlowID(taskID);
			Sys.printMsg("flowID:" + flowID);
			sdata1 = TaskData.getCurrentActivitysData1(taskID);
			Sys.printMsg("sData1:" + sdata1);
			List<FlowActivity> aftAList = new ArrayList<FlowActivity>();
			aftAList = new FlowProcess(processID).getProcessActivitys();
			StringBuffer afterActList = new StringBuffer();
			afterActList.append("[");
			for (int i = 0; i < aftAList.size(); i++) {
				if (i > 0)
					afterActList.append(",");
				String exeGroup = aftAList.get(i).getExcutorGroup();
				if (exeGroup != null) {
					exeGroup = exeGroup.replaceAll("getProcessID\\(\\)", processID);
					exeGroup = exeGroup.replaceAll("getFlowID\\(\\)", flowID);
					exeGroup = exeGroup.replaceAll("getTaskID\\(\\)", taskID);
					exeGroup = exeGroup.replaceAll("getProcesssData1\\(\\)", sdata1);
				}
				String excutorGroup = "";
				if (aftAList.get(i).getExcutorGroup() == null || "".equals(aftAList.get(i).getExcutorGroup())) {
					excutorGroup = BooleanExpression.getScriptExpressionVal(
							BooleanExpression.resolutionExpression("getOrgUnitHasActivity(\"" + processID + "\",\""
									+ aftAList.get(i).getId() + "\",\"FALSE\",\"FALSE\")", request));
				} else {
					excutorGroup = BooleanExpression
							.getScriptExpressionVal(BooleanExpression.resolutionExpression(exeGroup, request));
				}
				afterActList.append("{id:\"" + aftAList.get(i).getId() + "\",name:\""
						+ aftAList.get(i).getActivityname() + "\",type:\"" + aftAList.get(i).getType()
						+ "\",excutorGroup:\"" + excutorGroup + "\",excutorIDs:\"" + aftAList.get(i).getExcutorIDs()
						+ "\",excutorNames:\"" + aftAList.get(i).getExcutorNames() + "\"}");
			}
			afterActList.append("]");
			// System.out.println(afterActList);

			data.setData("{activityListStr:'" + afterActList.toString() + "',flowID:'" + flowID + "',taskID:'" + taskID
					+ "',sData1:'" + sdata1 + "'}");
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("true");
			data.setMessage(e.toString());
			System.out.println(e.toString());
		}
		return AjaxResult.success(data);
	}

}
