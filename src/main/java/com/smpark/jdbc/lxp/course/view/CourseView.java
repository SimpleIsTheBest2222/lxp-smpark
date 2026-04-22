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

			String input = sc.nextLine();

			int selectedNum;
			try {
				selectedNum = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력해주세요.");
				continue;
			}

			if (selectedNum == 1) {
				showCourseRegisterMenu(sc);
			} else if (selectedNum == 2) {
				System.out.println("강의 조회 기능 구현 예정입니다.");
				continue;
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

		String title;
		while (true) {
			System.out.print("강의 제목    : ");
			title = sc.nextLine();

			// 낮은 버전 자바 생각해서 isBlank() 대신 trim().isEmpty() 사용
			if (title == null || title.trim().isEmpty()) {
				System.out.println("[오류] 제목은 필수입니다.");
				continue;
			}
			break;
		}

		// price 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		long price;
		while (true) {
			System.out.print("가격    : ");
			String priceInput = sc.nextLine();

			try {
				price = Long.parseLong(priceInput);
				break;
			} catch (Exception e) {
				System.out.println("[오류] 가격은 숫자로 입력해 주세요.");
			}
		}

		// instructorId 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		long instructorId;
		while (true) {
			System.out.print("강사 ID    : ");
			String instructorIdInput = sc.nextLine();

			try {
				instructorId = Long.parseLong(instructorIdInput);
				break;
			} catch (Exception e) {
				System.out.println("[오류] 강사 ID는 숫자로 입력해 주세요.");
			}
		}

		// courseLevel 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		CourseLevel courseLevel;
		while (true) {
			System.out.print("난이도(LOW/MIDDLE/HIGH)    : ");
			String level = sc.nextLine();
			try {
				courseLevel = CourseLevel.valueOf(level.trim().toUpperCase());
				break;
			} catch (Exception e) {
				System.out.println("[오류] 난이도는 LOW, MIDDLE, HIGH 중 하나여야 합니다.");
			}
		}

		// 강의 설명 유효성 검사는 CRUD 뼈대 생성이후에 할 예정
		System.out.print("강의 설명    : ");
		String description = sc.nextLine();

		// 뷰에서 받은 값을 DTO에 담아 컨트롤러로 전달
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setTitle(title);
		courseDTO.setPrice(price);
		courseDTO.setInstructorId(instructorId);
		courseDTO.setLevel(courseLevel);
		courseDTO.setDescription(description);

		courseController.courseCreate(courseDTO);

	}

}
