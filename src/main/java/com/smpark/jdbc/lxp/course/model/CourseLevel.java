package com.smpark.jdbc.lxp.course.model;

public enum CourseLevel {
	LOW("입문"),
	MIDDLE("중급"),
	HIGH("고급");

	private final String displayName;

	CourseLevel(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}