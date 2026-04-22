package com.smpark.jdbc.lxp.content.service;

import java.sql.Connection;
import java.util.List;

import com.smpark.jdbc.lxp.content.model.Content;
import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.content.repository.ContentRepository;

public class ContentService {

	private final ContentRepository contentRepository;

	public ContentService(Connection connection) {
		this.contentRepository = new ContentRepository(connection);
	}

	public long create(ContentDTO contentDTO) {
		validateContent(contentDTO);

		Content content = new Content();
		content.setTitle(contentDTO.getTitle());
		content.setContent(contentDTO.getContent());
		content.setSeq(contentDTO.getSeq());
		content.setCourseId(contentDTO.getCourseId());
		content.setContentType(contentDTO.getContentType());

		return contentRepository.create(content);
	}

	public List<Content> findByCourseId(long courseId) {
		return contentRepository.findByCourseId(courseId);
	}

	public Content findById(long id) {
		Content content = contentRepository.findById(id);
		if (content == null) {
			throw new IllegalArgumentException("해당 콘텐츠를 찾을 수 없습니다. (id: " + id + ")");
		}
		return content;
	}

	public void update(ContentDTO contentDTO) {
		validateContent(contentDTO);

		int updated = contentRepository.update(contentDTO);
		if (updated == 0) {
			throw new IllegalArgumentException("해당 콘텐츠를 찾을 수 없습니다. (id: " + contentDTO.getId() + ")");
		}
	}

	public void delete(long id) {
		int updated = contentRepository.softDeleteById(id);
		if (updated == 0) {
			throw new IllegalArgumentException("해당 콘텐츠를 찾을 수 없습니다. (id: " + id + ")");
		}
	}

	public void deleteByCourseId(long courseId) {
		contentRepository.softDeleteByCourseId(courseId);
	}

	private void validateContent(ContentDTO contentDTO) {
		if (contentDTO.getTitle() == null || contentDTO.getTitle().isBlank()) {
			throw new IllegalArgumentException("콘텐츠 제목은 필수입니다.");
		}
		if (contentDTO.getTitle().length() > 50) {
			throw new IllegalArgumentException("콘텐츠 제목은 50자 이하로 입력해 주세요.");
		}
		if (contentDTO.getContent() == null || contentDTO.getContent().isBlank()) {
			throw new IllegalArgumentException("콘텐츠 내용은 필수입니다.");
		}
		if (contentDTO.getContent().length() > 200) {
			throw new IllegalArgumentException("콘텐츠 내용은 200자 이하로 입력해 주세요.");
		}
		if (contentDTO.getContentType() == null) {
			throw new IllegalArgumentException("콘텐츠 타입은 VIDEO, TEXT, FILE 중 하나여야 합니다.");
		}
	}
}