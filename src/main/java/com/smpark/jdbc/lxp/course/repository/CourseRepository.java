package com.smpark.jdbc.lxp.course.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.util.QueryUtil;

public class CourseRepository {

	private final Connection connection;

	public CourseRepository(Connection connection) {
		this.connection = connection;
	}

	public long courseCreate(Course course) {
		String sql = QueryUtil.getQuery("course.create");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, course.getTitle());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.setLong(3, course.getPrice());
			preparedStatement.setString(4, course.getLevel().name());
			preparedStatement.setLong(5, course.getInstructorId());

			// 인서트, 업데이트는 executeUpdate 해당 메소드 사용.
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				// id 값 자동으로 받음
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						long generatedId = generatedKeys.getLong(1);
						return generatedId;
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return 0L;

	}
}
