package com.smpark.jdbc.lxp.course.model;

import java.util.ArrayList;
import java.util.List;

import com.smpark.jdbc.lxp.content.model.Content;

public class CourseDetailDTO {

	private Course course;
	private String instructorName;
	private String instructorIntroduction;
	private List<Content> contents = new ArrayList<Content>();

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getInstructorIntroduction() {
		return instructorIntroduction;
	}

	public void setInstructorIntroduction(String instructorIntroduction) {
		this.instructorIntroduction = instructorIntroduction;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
}

