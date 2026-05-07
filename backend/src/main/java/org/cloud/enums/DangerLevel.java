package org.cloud.enums;

public enum DangerLevel {

	LOW(1), // 위험도 낮음
	MEDIUM(2), // 위험도 중간
	HIGH(3); // 위험도 높음
	
	private final int level;
	
	DangerLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
}
