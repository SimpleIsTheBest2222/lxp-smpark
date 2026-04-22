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

	// 강의 등록 + 콘텐츠 등록
	public long courseCreate(CourseDTO courseDTO, List<ContentDTO> contentDTOList) {
		return courseService.create(courseDTO, contentDTOList);
	}

	// 강의 전체 목록 조회
	public List<Course> findAllCourses() {
		return courseService.findAll();
	}

	// 강의 상세 조회
	public CourseDetailDTO findCourseDetail(long courseId) {
		return courseService.findCourseDetail(courseId);
	}

	// 강의 정보 수정
	public void updateCourse(CourseDTO courseDTO) {
		courseService.update(courseDTO);
	}

	// 강의 삭제
	public void deleteCourse(long courseId) {
		courseService.delete(courseId);
	}
}


