package com.example.chatBackend.dto;

import java.util.List;

public class CreateRequestRoom {
	private String name;
	private boolean isGroup;
	private Long createdBy;
	private List<Long> memberIds;
	
	public CreateRequestRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateRequestRoom(String name, boolean isGroup, Long createdBy, List<Long> memberIds) {
		super();
		this.name = name;
		this.isGroup = isGroup;
		this.createdBy = createdBy;
		this.memberIds = memberIds;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public List<Long> getMemberIds() {
		return memberIds;
	}
	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}
	
}
