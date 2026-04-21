package com.smpark.jdbc.lxp.course.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.smpark.jdbc.config.JDBCConnection;
import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.repository.CourseRepository;

public class CourseService {

	private final CourseRepository courseRepository;
	private final Connection connection;

	public CourseService() {
		try {
			this.connection = JDBCConnection.getConnection();
			this.courseRepository = new CourseRepository(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int courseCreate(CourseDTO courseDTO) throws Exception {

		Course course = new Course();
		// (title, description, price, level, instructor_id, createdAt, updatedAt)
		course.setTitle(courseDTO.getTitle());
		course.setDescription(courseDTO.getDescription());
		course.setPrice(courseDTO.getPrice());
		course.setLevel(courseDTO.getLevel());
		course.setInstructorId(courseDTO.getInstructorId());

		int result = courseRepository.courseCreate(course);
		System.out.println("강의 저장 결과: " + result);

		return result;

	}

}
