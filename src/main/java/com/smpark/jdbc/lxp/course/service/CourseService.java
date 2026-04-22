package com.smpark.jdbc.lxp.course.service;

import java.sql.Connection;
import java.util.List;

import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.content.service.ContentService;
import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.model.CourseDetailDTO;
import com.smpark.jdbc.lxp.course.repository.CourseRepository;

public class CourseService {

	private final CourseRepository courseRepository;
	private final ContentService contentService;

	public CourseService(Connection connection) {
		this.courseRepository = new CourseRepository(connection);
		this.contentService = new ContentService(connection);
	}

	public long create(CourseDTO dto, List<ContentDTO> contents) {

		validateCourse(dto);

		Course course = new Course();
		course.setTitle(dto.getTitle());
		course.setDescription(dto.getDescription());
		course.setPrice(dto.getPrice());
		course.setLevel(dto.getLevel());
		course.setInstructorId(dto.getInstructorId());

		long courseId = courseRepository.create(course);

		int seq = 1;
		for (ContentDTO c : contents) {
			c.setCourseId(courseId);
			c.setSeq(seq++);
			contentService.create(c);
		}

		return courseId;
	}

	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	public Course findById(long id) {
		Course c = courseRepository.findById(id);
		if (c == null) {
			throw new IllegalArgumentException("해당 id의 강의를 찾을 수 없습니다. (id: " + id + ")");
		}
		return c;
	}

	public void update(CourseDTO dto) {
		validateCourse(dto);
		courseRepository.update(dto);
	}

	public void delete(long id) {
		courseRepository.softDelete(id);
		contentService.deleteByCourseId(id);
	}

	private void validateCourse(CourseDTO dto) {
		if (dto.getTitle() == null || dto.getTitle().isBlank())
			throw new IllegalArgumentException("제목은 필수입니다.");

		if (dto.getTitle().length() > 50)
			throw new IllegalArgumentException("제목은 50자 이하로 입력해 주세요.");

		if (dto.getPrice() < 0)
			throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");

		if (dto.getLevel() == null)
			throw new IllegalArgumentException("난이도는 필수입니다.");

		if (dto.getDescription() == null || dto.getDescription().isBlank())
			throw new IllegalArgumentException("설명은 필수입니다.");
	}

	public CourseDetailDTO findCourseDetail(long courseId) {
		return courseRepository.findCourseDetail(courseId);
	}

}