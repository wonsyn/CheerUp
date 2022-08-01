package com.web.curation.model.dto;

import lombok.Data;
import lombok.ToString;

//@Data
public class WordDto {

	private int wordId;
	private String word;
	private String wordExp;
	
	
	public int getWordId() {
		return wordId;
	}


	public void setWordId(int wordId) {
		this.wordId = wordId;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public String getWordExp() {
		return wordExp;
	}


	public void setWordExp(String wordExp) {
		this.wordExp = wordExp;
	}


	@Override
	public String toString() {
		return "WordDto [wordId=" + wordId + ", word=" + word + ", wordExp=" + wordExp + "]";
	}
	
	
	
}
