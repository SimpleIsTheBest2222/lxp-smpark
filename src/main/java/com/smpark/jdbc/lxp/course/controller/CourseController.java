package com.smpark.jdbc.lxp.course.controller;

import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.service.CourseService;

public class CourseController {

	private CourseService courseService = new CourseService();

	public void courseCreate(CourseDTO courseDTO) {

		try {
			int result = courseService.courseCreate(courseDTO);
			System.out.println("강의 저장 결과: " + result);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
