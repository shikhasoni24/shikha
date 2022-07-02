package com.shikha.core.entity;

public class Room {
	private int roomNo;
	private String roomName;
	private String roomStatus;
	private String city;
	
	
	public Room(int roomNo, String roomName, String roomStatus, String city) {
		super();
		this.roomNo = roomNo;
		this.roomName = roomName;
		this.roomStatus = roomStatus;
		this.city = city;
	}
	public Room(int roomNo, String roomName, String roomStatus) {
		super();
		this.roomNo = roomNo;
		this.roomName = roomName;
		this.roomStatus = roomStatus;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Room [roomNo=" + roomNo + ", roomName=" + roomName + ", roomStatus=" + roomStatus + ", city=" + city
				+ "]";
	}

}
