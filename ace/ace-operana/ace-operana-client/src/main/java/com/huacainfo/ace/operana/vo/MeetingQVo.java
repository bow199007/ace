package com.huacainfo.ace.operana.vo;

import com.huacainfo.ace.operana.model.Meeting;


public class MeetingQVo extends Meeting {
	private static final long serialVersionUID = 1L;

	private String startDate;
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
