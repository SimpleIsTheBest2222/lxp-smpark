package com.smpark.jdbc.lxp;

import java.sql.Connection;
import java.util.Scanner;

import com.smpark.jdbc.config.JDBCConnection;

public class Main {

	public static void main(String[] args) {

		try (
			// Connection connection = JDBCConnection.getConnection();
			Scanner sc = new Scanner(System.in);
		) {
			// System.out.println("DB 연결 성공 : " + connection);
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
			// 입력값을 int 로 바로 받기.
			int courseNum = sc.nextInt();
			// 버퍼 비우기
			sc.nextLine();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}