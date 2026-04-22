package com.smpark.jdbc.lxp.course.model;

public class CourseDTO {

	private String title;
	private long price;
	private CourseLevel level;
	private String description;
	private long instructorId;

	public CourseDTO() {
	}

	public String getDescription() {
		return description;
	}

	public long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(long instructorId) {
		this.instructorId = instructorId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CourseLevel getLevel() {
		return level;
	}

	public void setLevel(CourseLevel level) {
		this.level = level;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
