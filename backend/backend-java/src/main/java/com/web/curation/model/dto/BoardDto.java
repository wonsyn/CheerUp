package com.web.curation.model.dto;

import lombok.Data;

@Data
public class BoardDto {

	private int boardId;
	private int userId;
	private String boardName;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", userId=" + userId + ", boardName=" + boardName + "]";
	}
	
	
}
