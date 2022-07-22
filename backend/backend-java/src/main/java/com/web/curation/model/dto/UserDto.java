package com.web.curation.model.dto;

import lombok.Data;

@Data
public class UserDto {
	private int userId;
	private String id;
	private String password;
	private String nickname;
	private String email;
	private String userImg;
}
