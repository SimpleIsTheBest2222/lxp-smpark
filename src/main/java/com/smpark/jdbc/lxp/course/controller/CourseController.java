package com.smpark.jdbc.lxp.course.controller;

import java.sql.Connection;
import java.util.List;

import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.model.CourseDetailDTO;
import com.smpark.jdbc.lxp.course.service.CourseService;

public class CourseController {

	private final CourseService courseService;

	public CourseController(Connection connection) {
		this.courseService = new CourseService(connection);
	}

	public long courseCreate(CourseDTO dto, List<ContentDTO> contents) {
		return courseService.create(dto, contents);
	}

	public List<Course> findAllCourses() {
		return courseService.findAll();
	}

	public Course findCourse(long id) {
		return courseService.findById(id);
	}

	public void updateCourse(CourseDTO dto) {
		courseService.update(dto);
	}

	public void deleteCourse(long id) {
		courseService.delete(id);
	}

	public CourseDetailDTO findCourseDetail(long id) {
		return courseService.findCourseDetail(id);
	}
}