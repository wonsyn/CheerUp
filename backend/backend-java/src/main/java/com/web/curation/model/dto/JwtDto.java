package com.web.curation.model.dto;


public class JwtDto {

	private int tokenId;
	private int userId;
	private String refreshTokenPeriod;
	
	
	public JwtDto(int tokenId, int userId, String refreshTokenPeriod) {
		super();
		this.tokenId = tokenId;
		this.userId = userId;
		this.refreshTokenPeriod = refreshTokenPeriod;
	}
	public JwtDto(int userId, String refreshTokenPeriod) {
		super();
		this.userId = userId;
		this.refreshTokenPeriod = refreshTokenPeriod;
	}
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRefreshTokenPeriod() {
		return refreshTokenPeriod;
	}
	public void setRefreshTokenPeriod(String refreshTokenPeriod) {
		this.refreshTokenPeriod = refreshTokenPeriod;
	}
	@Override
	public String toString() {
		return "TokenDto [tokenId=" + tokenId + ", userId=" + userId + ", refreshTokenPeriod=" + refreshTokenPeriod
				+ "]";
	}
	
	
}
