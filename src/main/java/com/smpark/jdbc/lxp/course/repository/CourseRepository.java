package com.smpark.jdbc.lxp.course.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.util.QueryUtil;

public class CourseRepository {

	private final Connection connection;

	public CourseRepository(Connection connection) {
		this.connection = connection;
	}

	public int courseCreate(Course course) {
		String sql = QueryUtil.getQuery("course.create");

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			// (title, description, price, level, instructor_id, createdAt, updatedAt)

			preparedStatement.setString(1, course.getTitle());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.setInt(3, course.getPrice());
			preparedStatement.setString(4, course.getLevel());
			preparedStatement.setInt(5, course.getInstructorId());
			// 인서트, 업데이트는 무조건 해당 메소드 사용. 결과값은 int 로 받는데, 변수는 affectedRows 으로 받음.
			int affectedRows = preparedStatement.executeUpdate();
			System.out.println("1인지 아닌지 :  " + affectedRows);

			if (affectedRows > 0) {
				// 이거 써야지 id 값 자동으로 받음
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						long generatedId = generatedKeys.getLong(1);
						System.out.println("생성된 강의 ID : " + generatedId);
						return (int)generatedId;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return 0;

	}
}
