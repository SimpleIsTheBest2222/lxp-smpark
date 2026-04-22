package com.smpark.jdbc.lxp;

import java.sql.Connection;
import java.util.Scanner;

import com.smpark.jdbc.config.JDBCConnection;
import com.smpark.jdbc.lxp.course.view.CourseView;

public class Main {

	public static void main(String[] args) {

		// 호출 흐름 순서 Main -> View -> Controller -> Service -> Repository

		try (Scanner sc = new Scanner(System.in);
		     Connection connection = JDBCConnection.getConnection();
		) {

			//Main에서 만든 connection 을 CouseView 생성자에게 넘김
			CourseView courseView = new CourseView(connection);

			while (true) {
				System.out.println("DB 연결 성공 : " + connection);

				// 메인 화면 호출 메서드
				printMainMenu();

				int selectedNum = sc.nextInt();
				sc.nextLine();

				if (selectedNum == 1) {
					//  강의 관리 창 띄우기
					courseView.showCourseMenu(sc);
				} else if (selectedNum == 2) {
					// 미구현
					break;
				} else if (selectedNum == 3) {
					// 미구현
					// System.out.println("종료합니다.");
					break;
				} else {
					System.out.println("잘못된 번호입니다.");
				}

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void printMainMenu() {

		// 메인 화면 창 띄우기.
		System.out.println("============================================================");
		System.out.println("                          강의 관리 콘솔                          ");
		System.out.println("============================================================");
		System.out.println();
		System.out.println("  1. 강의 관리");
		System.out.println("  2. 강사 관리");
		System.out.println("  3. 종료");
		System.out.println();
		System.out.println("------------------------------------------------------------");
		System.out.print("> ");

	}

}