package com.shashwat.ai.game2048.model;

public enum DirectionEnum {
	UP(0, "UP"),
	DOWN(1, "DOWN"),
	RIGHT(2, "RIGHT"),
	LEFT(3, "RIGHT");
	
	private final int code;
	private final String desc;
	
	private DirectionEnum(final int code, final String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public String toString() {
		return this.desc;
	}
}
