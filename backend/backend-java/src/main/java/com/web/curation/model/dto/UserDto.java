package com.web.curation.model.dto;

import lombok.Data;

@Data
public class UserDto {

	private int userId;
	private String id;
	private String password;
	private String nickname;
	private String email;
	private String userImgUrl;
	private String userImgName;
	
	public UserDto() {
		super();
	}
	
	public UserDto(String password) {
		super();
		this.password = password;
	}

	public UserDto(String id, String nickname, String email, String userImgUrl, String userImgName) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.userImgUrl = userImgUrl;
		this.userImgName = userImgName;
	}
	
	public UserDto(String id, String password, String nickname, String email, String userImgUrl, String userImgName) {
		super();
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.userImgUrl = userImgUrl;
		this.userImgName = userImgName;
	}

	public UserDto(int userId, String id, String password, String nickname, String email, String userImgUrl,
			String userImgName) {
		super();
		this.userId = userId;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.userImgUrl = userImgUrl;
		this.userImgName = userImgName;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserImgUrl() {
		return userImgUrl;
	}
	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}
	public String getUserImgName() {
		return userImgName;
	}
	public void setUserImgName(String userImgName) {
		this.userImgName = userImgName;
	}
	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", id=" + id + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", userImgUrl=" + userImgUrl + ", userImgName=" + userImgName + "]";
	}
}
