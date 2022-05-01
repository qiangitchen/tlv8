package com.tlv8.flw.autor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tlv8.flw.base.TaskData;

public class TaskPageAutor {
	public static boolean isMyTask(HttpServletRequest request) {
		boolean isag = false;
		try {
			String taskID = request.getParameter("taskID");
			Map<String, String> flwAtask = TaskData.getTaskInfor(taskID, null);
			String seurl = flwAtask.get("url");
			String requestURI = request.getRequestURI();
			if (requestURI != null && requestURI.equals(seurl)) {
				isag = true;
			}
		} catch (Exception e) {
		}
		return isag;
	}
}
