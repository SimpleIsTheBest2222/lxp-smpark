package com.smpark.jdbc.lxp.course.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.model.CourseLevel;
import com.smpark.jdbc.lxp.util.QueryUtil;

public class CourseRepository {

	private final Connection connection;

	public CourseRepository(Connection connection) {
		this.connection = connection;
	}

	public long create(Course course) {
		String sql = QueryUtil.getQuery("course.create");

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, course.getTitle());
			ps.setString(2, course.getDescription());
			ps.setLong(3, course.getPrice());
			ps.setString(4, course.getLevel().name());
			ps.setLong(5, course.getInstructorId());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next()) return rs.getLong(1);
			}

			return 0L;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Course> findAll() {
		String sql = QueryUtil.getQuery("course.findAll");
		List<Course> list = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Course c = new Course();
				c.setId(rs.getLong("id"));
				c.setTitle(rs.getString("title"));
				list.add(c);
			}

			return list;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Course findById(long id) {
		String sql = QueryUtil.getQuery("course.findById");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Course c = new Course();
					c.setId(rs.getLong("id"));
					c.setTitle(rs.getString("title"));
					c.setDescription(rs.getString("description"));
					c.setPrice(rs.getLong("price"));
					c.setLevel(CourseLevel.valueOf(rs.getString("level")));
					c.setInstructorId(rs.getLong("instructor_id"));

					if (rs.getTimestamp("createdAt") != null)
						c.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());

					if (rs.getTimestamp("updatedAt") != null)
						c.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());

					return c;
				}
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void update(CourseDTO dto) {
		String sql = QueryUtil.getQuery("course.update");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, dto.getTitle());
			ps.setLong(2, dto.getPrice());
			ps.setString(3, dto.getLevel().name());
			ps.setString(4, dto.getDescription());
			ps.setLong(5, dto.getInstructorId());
			ps.setLong(6, dto.getId());

			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void softDelete(long id) {
		String sql = QueryUtil.getQuery("course.delete");

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	// findCourseDetail 메서드 생성 DB 접근
	public void findCourseDetail () {

	}
}