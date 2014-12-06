package com.shashwat.ai.game2048.model;

public enum StatusEnum {
	CONTINUE(0, "CORRECT MOVE"),
	INCORRECT_MOVE(1, "INCORRECT MOVE"),
	NO_MORE_MOVE(2, "N0 MORE MOVES"),
	WIN(4, "WIN");

	private final int code;
	private final String desc;
	
	private StatusEnum(final int code, final String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDesc() {
		return this.desc;
	}
}
