package com.smpark.jdbc.lxp;

import java.sql.Connection;
import java.util.Scanner;

import com.smpark.jdbc.config.JDBCConnection;

public class Main {

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in);
		     // Connection connection = JDBCConnection.getConnection();
		) {

			while (true) {
				// System.out.println("DB 연결 성공 : " + connection);

				// 메인 화면 호출 메서드 생성함.
				printMainMenu(sc);

				// 메인 화면 호출하고 나서 입력받기.
				// 입력값을 int 로 바로 받기.
				int selectedNum = sc.nextInt();
				// 버퍼 비우기
				sc.nextLine();

				// 만약에 입력한 값이 1번이면 강의관리 화면 띄우고
				if (selectedNum == 1) {
					// System.out.println("강의 관리");
					// 강의 관리 호출하는 메서드 생성함.
					// sc 를 한군데 선언하고 쓰려고 sc 를 매개변수로 넘김
					showCourseMenu(sc);

					break;
				}
				// 만약에 입력한 값이 2번이면 강사 관리 화면 띄우고
				else if (selectedNum == 2) {
					System.out.println("강사 관리");
					break;
				}
				// 만약에 입력한 값이 3번이면 프로그램을 종료해라.
				else if (selectedNum == 3) {
					// 이거 요구사항 확인 필요함
					System.out.println("종료합니다.");
					break;
				} else {
					System.out.println("잘못된 번호입니다.");
					break;
				}

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void printMainMenu(Scanner sc) {

		// 메인 화면 창 띄우기.
		// 메인
		System.out.println("============================================================");
		System.out.println("============================================================");
		System.out.println();
		System.out.println("  1. 강의 관리");
		System.out.println("  2. 강사 관리");
		System.out.println("  3. 종료");
		System.out.println();
		System.out.println("------------------------------------------------------------");
		System.out.print("> ");

	}

	public static void showCourseMenu(Scanner sc) {

		while (true) {
			// 강의 관리 창 띄우기
			// 강의 관리
			System.out.println("============================================================");
			System.out.println("                          강의 관리 		                     ");
			System.out.println("============================================================");
			System.out.println();
			System.out.println("  1. 강의 등록");
			System.out.println("  2. 강사 조회");
			System.out.println("  3. 뒤로 가기");
			System.out.println();
			System.out.println("------------------------------------------------------------");
			System.out.print("> ");

			int selectedNum = sc.nextInt();
			sc.nextLine();

			if (selectedNum == 1) {
				System.out.println("1. 강의 등록입니다.");
				showCourseRegisterMenu(sc);
				break;
			} else if (selectedNum == 2) {
				System.out.println("2. 강사 조회입니다.");
				break;
			} else if (selectedNum == 3) {
				System.out.println("3. 뒤로 가기입니다.");
				break;
			} else {
				System.out.println("잘못된 번호입니다.");
			}

		}

	}

	public static void showCourseRegisterMenu(Scanner sc) {

		// 1. 강의 등록입니다.
		System.out.println("============================================================");
		System.out.println("                           강의 등록                            ");
		System.out.println("============================================================");
		System.out.println();
		System.out.println("  강의 정보를 입력하세요.");
		System.out.println();

		System.out.print("  강의 제목    : ");
		String title = sc.nextLine();

		System.out.print("  가격    : ");
		int price = sc.nextInt();
		sc.nextLine();

		System.out.print("  강사 ID    : ");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.print("  난이도    : ");
		String level = sc.nextLine();

		System.out.print("  강의 설명    : ");
		String description = sc.nextLine();

/*
		컨텐츠 생성 부분

		System.out.println();
		System.out.println("------------------------------------------------------------");
		System.out.println("  콘텐츠를 추가하세요. (제목에 0 입력 시 완료)");
		System.out.println("------------------------------------------------------------");
*/

	}

}