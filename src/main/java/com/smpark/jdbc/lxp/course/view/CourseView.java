package com.smpark.jdbc.lxp.course.view;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.content.model.ContentType;
import com.smpark.jdbc.lxp.content.view.ContentView;
import com.smpark.jdbc.lxp.course.controller.CourseController;
import com.smpark.jdbc.lxp.course.model.Course;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.model.CourseDetailDTO;
import com.smpark.jdbc.lxp.course.model.CourseLevel;

public class CourseView {

	private final CourseController courseController;
	private final ContentView contentView;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	public CourseView(Connection connection) {
		this.courseController = new CourseController(connection);
		this.contentView = new ContentView(connection);
	}

	public void showCourseMenu(Scanner sc) {
		while (true) {
			System.out.println("""
============================================================
                           강의 관리
============================================================

  1. 강의 등록
  2. 강의 조회
  3. 뒤로 가기

------------------------------------------------------------""");
			System.out.print("> ");

			int selectedNum = readMenuNumber(sc);

			if (selectedNum == 1) {
				showCourseRegisterMenu(sc);
			} else if (selectedNum == 2) {
				showCourseListMenu(sc);
			} else if (selectedNum == 3) {
				return;
			} else {
				System.out.println("[오류] 올바른 번호를 입력해 주세요.");
			}
		}
	}

	public void showCourseRegisterMenu(Scanner sc) {
		try {
			System.out.println("""
============================================================
                           강의 등록
============================================================

  강의 정보를 입력하세요.
""");

			CourseDTO courseDTO = new CourseDTO();

			System.out.print("  강의 제목    : ");
			courseDTO.setTitle(sc.nextLine());

			System.out.print("  가격         : ");
			courseDTO.setPrice(Long.parseLong(sc.nextLine()));

			System.out.print("  강사 ID      : ");
			courseDTO.setInstructorId(Long.parseLong(sc.nextLine()));

			System.out.print("  난이도       : ");
			courseDTO.setLevel(parseCourseLevel(sc.nextLine()));

			System.out.print("  강의 설명    : ");
			courseDTO.setDescription(sc.nextLine());

			System.out.println("""
 
------------------------------------------------------------
  콘텐츠를 추가하세요. (제목에 0 입력 시 완료)
------------------------------------------------------------""");

			List<ContentDTO> contentDTOList = new ArrayList<ContentDTO>();

			while (true) {
				System.out.print("\n  콘텐츠 제목  : ");
				String title = sc.nextLine();

				if ("0".equals(title)) {
					break;
				}

				ContentDTO contentDTO = new ContentDTO();
				contentDTO.setTitle(title);

				System.out.print("  콘텐츠 타입  : ");
				contentDTO.setContentType(parseContentType(sc.nextLine()));

				System.out.print("  " + getContentLabel(contentDTO.getContentType()) + "  : ");
				contentDTO.setContent(sc.nextLine());

				contentDTOList.add(contentDTO);
			}

			long id = courseController.courseCreate(courseDTO, contentDTOList);

			System.out.println("------------------------------------------------------------");
			System.out.println("  강의가 등록되었습니다. id: " + id);
			System.out.println("------------------------------------------------------------");

		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private void showCourseListMenu(Scanner sc) {
		try {
			List<Course> courses = courseController.findAllCourses();

			if (courses == null || courses.isEmpty()) {
				System.out.println("[오류] 등록된 강의가 없습니다.");
				return;
			}

			while (true) {
				System.out.println("""
============================================================
                           강의 목록
============================================================""");

				for (Course course : courses) {
					System.out.println("  " + course.getId() + ". " + course.getTitle());
				}

				System.out.println("""
------------------------------------------------------------
  1. 강의 선택
  2. 뒤로 가기
------------------------------------------------------------""");
				System.out.print("> ");

				int selectedNum = readMenuNumber(sc);

				if (selectedNum == 1) {
					System.out.print("조회할 강의 id를 입력하세요: ");
					long courseId = Long.parseLong(sc.nextLine());
					showCourseDetailMenu(sc, courseId);

					courses = courseController.findAllCourses();
					if (courses == null || courses.isEmpty()) {
						return;
					}
				} else if (selectedNum == 2) {
					return;
				} else {
					System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				}
			}
		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private void showCourseDetailMenu(Scanner sc, long courseId) {
		while (true) {
			try {
				CourseDetailDTO detailDTO = courseController.findCourseDetail(courseId);
				printCourseDetail(detailDTO);

				int selectedNum = readMenuNumber(sc);

				if (selectedNum == 1) {
					showCourseEditMenu(sc, detailDTO);
				} else if (selectedNum == 2) {
					System.out.print("삭제할 강의 id 입력: ");
					long deleteCourseId = Long.parseLong(sc.nextLine());
					courseController.deleteCourse(deleteCourseId);
					System.out.println("------------------------------------------------------------");
					System.out.println("  삭제가 완료되었습니다. id: " + deleteCourseId);
					System.out.println("------------------------------------------------------------");
					return;
				} else if (selectedNum == 3) {
					contentView.showContentSelectMenu(sc, detailDTO);
				} else if (selectedNum == 4) {
					return;
				} else {
					System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				}
			} catch (Exception e) {
				System.out.println("[오류] " + e.getMessage());
				return;
			}
		}
	}

	private void showCourseEditMenu(Scanner sc, CourseDetailDTO detailDTO) {
		while (true) {
			System.out.println("""
============================================================
                           강의 수정
============================================================

  1. 강의 상세 정보 수정
  2. 콘텐츠 수정
  3. 뒤로 가기

------------------------------------------------------------""");
			System.out.print("> ");

			int selectedNum = readMenuNumber(sc);

			if (selectedNum == 1) {
				showCourseInfoEditMenu(sc, detailDTO);
				return;
			} else if (selectedNum == 2) {
				contentView.showContentEditMenu(sc, detailDTO);
				return;
			} else if (selectedNum == 3) {
				return;
			} else {
				System.out.println("[오류] 올바른 번호를 입력해 주세요.");
			}
		}
	}

	private void showCourseInfoEditMenu(Scanner sc, CourseDetailDTO detailDTO) {
		try {
			Course currentCourse = detailDTO.getCourse();
			CourseDTO updateDTO = new CourseDTO();
			updateDTO.setId(currentCourse.getId());

			System.out.println("""
============================================================
                        강의 상세 정보 수정
============================================================

  빈 값 입력 시 기존 값이 유지됩니다.
------------------------------------------------------------""");

			System.out.print("\n  강의 제목       : ");
			String title = sc.nextLine();
			updateDTO.setTitle(title.isBlank() ? currentCourse.getTitle() : title);

			System.out.print("  가격           : ");
			String price = sc.nextLine();
			updateDTO.setPrice(price.isBlank() ? currentCourse.getPrice() : Long.parseLong(price));

			System.out.print("  강사 ID        : ");
			String instructorId = sc.nextLine();
			updateDTO.setInstructorId(instructorId.isBlank() ? currentCourse.getInstructorId() : Long.parseLong(instructorId));

			System.out.print("  난이도         : ");
			String level = sc.nextLine();
			updateDTO.setLevel(level.isBlank() ? currentCourse.getLevel() : parseCourseLevel(level));

			System.out.print("  강의 설명       : ");
			String description = sc.nextLine();
			updateDTO.setDescription(description.isBlank() ? currentCourse.getDescription() : description);

			courseController.updateCourse(updateDTO);

			System.out.println("------------------------------------------------------------");
			System.out.println("  수정되었습니다.");
			System.out.println("------------------------------------------------------------");

		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private void printCourseDetail(CourseDetailDTO detailDTO) {
		Course course = detailDTO.getCourse();
		String instructorName = detailDTO.getInstructorName();
		String instructorIntroduction = detailDTO.getInstructorIntroduction();

		if (instructorName == null) {
			instructorName = "null";
		}
		if (instructorIntroduction == null) {
			instructorIntroduction = "";
		}

		System.out.println("""
============================================================
                           강의 상세
============================================================""");
		System.out.println();
		System.out.println("  강의 id      : " + course.getId());
		System.out.println("  강의 제목    : " + course.getTitle());
		System.out.println("  강사         : " + instructorName);
		System.out.println("  가격         : " + String.format("%,d", course.getPrice()) + "원");
		System.out.println("  난이도       : " + course.getLevel().getDisplayName());
		System.out.println("  강의 설명    : " + course.getDescription());

		System.out.println("""
------------------------------------------------------------
  강사 소개
------------------------------------------------------------""");
		System.out.println("  강사         : " + instructorName);
		System.out.println("  강사 소개    : " + instructorIntroduction);

		System.out.println("""
------------------------------------------------------------
  콘텐츠 목록
------------------------------------------------------------""");

		if (detailDTO.getContents() == null || detailDTO.getContents().isEmpty()) {
			System.out.println("  등록된 콘텐츠가 없습니다.");
		} else {
			for (int i = 0; i < detailDTO.getContents().size(); i++) {
				System.out.println("  " + (i + 1) + ". " + detailDTO.getContents().get(i).getTitle());
			}
		}

		System.out.println("------------------------------------------------------------");
		System.out.println("  게시일         : " + (course.getCreatedAt() == null ? "" : course.getCreatedAt().format(formatter)));
		System.out.println("  마지막 수정일  : " + (course.getUpdatedAt() == null ? "" : course.getUpdatedAt().format(formatter)));
		System.out.println("""
------------------------------------------------------------

  1. 강의 수정
  2. 강의 삭제
  3. 콘텐츠 선택
  4. 뒤로 가기

------------------------------------------------------------""");
		System.out.print("> ");
	}

	private int readMenuNumber(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}

	private CourseLevel parseCourseLevel(String input) {
		String value = input.trim();
		if ("입문".equals(value)) {
			return CourseLevel.LOW;
		}
		if ("중급".equals(value)) {
			return CourseLevel.MIDDLE;
		}
		if ("고급".equals(value)) {
			return CourseLevel.HIGH;
		}
		return CourseLevel.valueOf(value.toUpperCase());
	}

	private ContentType parseContentType(String input) {
		return ContentType.valueOf(input.trim().toUpperCase());
	}

	private String getContentLabel(ContentType contentType) {
		if (contentType == ContentType.VIDEO) {
			return "재생 URL";
		}
		if (contentType == ContentType.FILE) {
			return "파일 경로";
		}
		return "콘텐츠 내용";
	}
}