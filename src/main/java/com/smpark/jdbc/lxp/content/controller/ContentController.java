package com.smpark.jdbc.lxp.content.controller;

import java.sql.Connection;
import java.util.List;

import com.smpark.jdbc.lxp.content.model.Content;
import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.content.service.ContentService;

public class ContentController {

	private final ContentService contentService;

	public ContentController(Connection connection) {
		this.contentService = new ContentService(connection);
	}

	public long create(ContentDTO contentDTO) {
		return contentService.create(contentDTO);
	}

	public List<Content> findByCourseId(long courseId) {
		return contentService.findByCourseId(courseId);
	}

	public Content findById(long id) {
		return contentService.findById(id);
	}

	public void update(ContentDTO contentDTO) {
		contentService.update(contentDTO);
	}

	public void delete(long id) {
		contentService.delete(id);
	}
}


