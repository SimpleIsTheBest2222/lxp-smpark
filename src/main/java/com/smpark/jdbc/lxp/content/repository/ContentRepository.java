package com.smpark.jdbc.lxp.content.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.smpark.jdbc.lxp.content.model.Content;
import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.content.model.ContentType;
import com.smpark.jdbc.lxp.util.QueryUtil;

public class ContentRepository {

	private final Connection connection;

	public ContentRepository(Connection connection) {
		this.connection = connection;
	}

	public long create(Content content) {
		String sql = QueryUtil.getQuery("content.create");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, content.getTitle());
			preparedStatement.setString(2, content.getContent());
			preparedStatement.setInt(3, content.getSeq());
			preparedStatement.setLong(4, content.getCourseId());
			preparedStatement.setString(5, content.getContentType().name());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getLong(1);
					}
				}
			}
			return 0L;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Content> findByCourseId(long courseId) {
		String sql = QueryUtil.getQuery("content.findByCourseId");
		List<Content> contents = new ArrayList<Content>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, courseId);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					Content content = new Content();
					content.setId(rs.getLong("id"));
					content.setTitle(rs.getString("title"));
					content.setContent(rs.getString("content"));
					content.setSeq(rs.getInt("seq"));
					content.setCourseId(rs.getLong("course_id"));
					content.setContentType(ContentType.valueOf(rs.getString("content_type")));

					if (rs.getTimestamp("createdAt") != null) {
						content.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
					}
					if (rs.getTimestamp("updatedAt") != null) {
						content.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
					}

					contents.add(content);
				}
			}

			return contents;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Content findById(long id) {
		String sql = QueryUtil.getQuery("content.findById");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				if (rs.next()) {
					Content content = new Content();
					content.setId(rs.getLong("id"));
					content.setTitle(rs.getString("title"));
					content.setContent(rs.getString("content"));
					content.setSeq(rs.getInt("seq"));
					content.setCourseId(rs.getLong("course_id"));
					content.setContentType(ContentType.valueOf(rs.getString("content_type")));

					if (rs.getTimestamp("createdAt") != null) {
						content.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
					}
					if (rs.getTimestamp("updatedAt") != null) {
						content.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
					}

					return content;
				}
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int update(ContentDTO contentDTO) {
		String sql = QueryUtil.getQuery("content.update");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, contentDTO.getTitle());
			preparedStatement.setString(2, contentDTO.getContent());
			preparedStatement.setString(3, contentDTO.getContentType().name());
			preparedStatement.setLong(4, contentDTO.getId());

			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int softDeleteById(long id) {
		String sql = QueryUtil.getQuery("content.softDeleteById");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int softDeleteByCourseId(long courseId) {
		String sql = QueryUtil.getQuery("content.softDeleteByCourseId");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, courseId);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

