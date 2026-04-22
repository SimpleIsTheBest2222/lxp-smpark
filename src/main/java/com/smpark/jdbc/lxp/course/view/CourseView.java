package com.smpark.jdbc.lxp.course.view;

import java.sql.Connection;
import java.util.Scanner;

import com.smpark.jdbc.lxp.course.controller.CourseController;
import com.smpark.jdbc.lxp.course.model.CourseDTO;
import com.smpark.jdbc.lxp.course.model.CourseLevel;

public class CourseView {

	private CourseController courseController;

	public CourseView(Connection connection) {
		this.courseController = new CourseController(connection);
	}

	public void showCourseMenu(Scanner sc) {

		while (true) {
			// 강의 관리 창 띄우기
			System.out.println("============================================================");
			System.out.println("                          강의 관리 		                     ");
			System.out.println("============================================================");
			System.out.println();
			System.out.println("  1. 강의 등록");
			System.out.println("  2. 강의 조회");
			System.out.println("  3. 뒤로 가기");
			System.out.println();
			System.out.println("------------------------------------------------------------");
			System.out.print("> ");

			long selectedNum = sc.nextLong();
			sc.nextLine();

			if (selectedNum == 1) {
				showCourseRegisterMenu(sc);
			} else if (selectedNum == 2) {
				break;
			} else if (selectedNum == 3) {
				break;
			} else {
				System.out.println("잘못된 번호입니다.");
			}

		}

	}

	public void showCourseRegisterMenu(Scanner sc) {

		System.out.println("============================================================");
		System.out.println("                           강의 등록                            ");
		System.out.println("============================================================");
		System.out.println();
		System.out.println("  강의 정보를 입력하세요.");
		System.out.println();

		// 유효성 검사는 CRUD 뼈대 생성이후에 할 예정.
		String title;
		while (true) {
			System.out.print("강의 제목    : ");
			title = sc.nextLine();

			// 낮은 버전 자바 생각해서 isBlank() 대신 trim().isEmpty() 사용
			if (title == null || title.trim().isEmpty()) {
				System.out.println("[오류] 제목은 필수입니다.");
				continue;
			}

			if (title.length() > 50) {
				System.out.println("[오류] 제목은 50자 이하로 입력해 주세요.");
				continue;
			}

			break;
		}

		// 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		System.out.print("가격    : ");
		long price = sc.nextLong();
		sc.nextLine();

		// 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		System.out.print("강사 ID    : ");
		long instructorId = sc.nextLong();
		sc.nextLine();

		// 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		System.out.print("난이도    : ");
		String level = sc.nextLine();

		// 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		System.out.print("강의 설명    : ");
		String description = sc.nextLine();

		// 뷰에서 받은 값을 DTO에 담아 컨트롤러로 전달
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setTitle(title);
		courseDTO.setPrice(price);
		courseDTO.setInstructorId(instructorId);
		courseDTO.setLevel(CourseLevel.valueOf(level.toUpperCase()));
		courseDTO.setDescription(description);

		courseController.courseCreate(courseDTO);

	}

}
