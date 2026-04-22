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
				System.out.println("1. 강의 등록");
				showCourseRegisterMenu(sc);
			} else if (selectedNum == 2) {
				System.out.println("2. 강의 조회");
				break;
			} else if (selectedNum == 3) {
				System.out.println("3. 뒤로 가기");
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

		System.out.print("강의 제목    : ");
		String title = sc.nextLine();

		System.out.print("가격    : ");
		long price = sc.nextLong();
		sc.nextLine();

		System.out.print("강사 ID    : ");
		long instructorId = sc.nextLong();
		sc.nextLine();

		System.out.print("난이도    : ");
		String level = sc.nextLine();

		System.out.print("강의 설명    : ");
		String description = sc.nextLine();

		// 뷰에서 받은 값을 DTO에 담아 컨트롤러로 전달
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setTitle(title);
		courseDTO.setPrice(price);
		courseDTO.setInstructorId(instructorId);
		// courseDTO.setLevel(CourseLevel.valueOf(levelInput.toUpperCase()));
		courseDTO.setLevel(CourseLevel.valueOf(level.toUpperCase()));
		courseDTO.setDescription(description);

		courseController.courseCreate(courseDTO);

	}

}
