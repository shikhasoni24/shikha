package com.shikha.core.entity;

public class UserRoom  {
    	
		private int user_id;
		private int room_no;
		
		public int getUser_id() {
			return user_id;
		}
		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}
		public int getRoom_no() {
			return room_no;
		}
		public void setRoom_no(int room_no) {
			this.room_no = room_no;
		}
		
		public UserRoom(int user_id, int room_no) {
			super();
			this.user_id = user_id;
			this.room_no = room_no;
		}
		
		@Override
		public String toString() {
			return "User_Room [user_id=" + user_id + ", room_no=" + room_no + "]";
		}	
}
