package com.smpark.jdbc.lxp.course.service;

import java.sql.Connection;

import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.repository.CourseRepository;

public class CourseService {

	private CourseRepository courseRepository;

	public CourseService(Connection connection) {
		this.courseRepository = new CourseRepository(connection);

	}

	public Long courseCreate(CourseDTO courseDTO) throws Exception {

		Course course = new Course();
		course.setTitle(courseDTO.getTitle());
		course.setDescription(courseDTO.getDescription());
		course.setPrice(courseDTO.getPrice());
		course.setLevel(courseDTO.getLevel());
		course.setInstructorId(courseDTO.getInstructorId());

		Long result = courseRepository.courseCreate(course);

		return result;

	}

}
