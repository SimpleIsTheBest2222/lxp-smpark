package com.smpark.jdbc.lxp.content.view;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.smpark.jdbc.lxp.content.controller.ContentController;
import com.smpark.jdbc.lxp.content.model.Content;
import com.smpark.jdbc.lxp.content.model.ContentDTO;
import com.smpark.jdbc.lxp.content.model.ContentType;
import com.smpark.jdbc.lxp.course.model.CourseDetailDTO;

public class ContentView {

	private final ContentController contentController;

	public ContentView(Connection connection) {
		this.contentController = new ContentController(connection);
	}

	public void showContentSelectMenu(Scanner sc, CourseDetailDTO detailDTO) {
		List<Content> contents = detailDTO.getContents();

		if (contents == null || contents.isEmpty()) {
			System.out.println("[오류] 등록된 콘텐츠가 없습니다.");
			return;
		}

		while (true) {
			System.out.println("""
============================================================
                           콘텐츠 선택
============================================================""");

			for (int i = 0; i < contents.size(); i++) {
				System.out.println("  " + (i + 1) + ". " + contents.get(i).getTitle());
			}

			System.out.println("------------------------------------------------------------");
			System.out.print("> ");

			int selectedNum = readMenuNumber(sc);

			if (selectedNum < 1 || selectedNum > contents.size()) {
				System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				continue;
			}

			showContentDetailMenu(sc, contents.get(selectedNum - 1));
			return;
		}
	}

	public void showContentDetailMenu(Scanner sc, Content content) {
		while (true) {
			System.out.println("""
============================================================
                           콘텐츠 상세
============================================================""");
			System.out.println();
			System.out.println("  콘텐츠 제목  : " + content.getTitle());
			System.out.println("  콘텐츠 타입  : " + content.getContentType().name());
			System.out.println("  콘텐츠 내용  : " + content.getContent());
			System.out.println("""
------------------------------------------------------------

  1. 콘텐츠 수정
  2. 콘텐츠 삭제
  3. 뒤로 가기

------------------------------------------------------------""");
			System.out.print("> ");

			int selectedNum = readMenuNumber(sc);

			if (selectedNum == 1) {
				showSingleContentEditMenu(sc, content);
				return;
			} else if (selectedNum == 2) {
				contentController.delete(content.getId());
				System.out.println("------------------------------------------------------------");
				System.out.println("  삭제가 완료되었습니다. id: " + content.getId());
				System.out.println("------------------------------------------------------------");
				return;
			} else if (selectedNum == 3) {
				return;
			} else {
				System.out.println("[오류] 올바른 번호를 입력해 주세요.");
			}
		}
	}

	public void showContentEditMenu(Scanner sc, CourseDetailDTO detailDTO) {
		List<Content> contents = detailDTO.getContents();

		if (contents == null || contents.isEmpty()) {
			System.out.println("[오류] 등록된 콘텐츠가 없습니다.");
			return;
		}

		while (true) {
			System.out.println("""
============================================================
                           콘텐츠 수정
============================================================

  수정할 콘텐츠 번호를 선택하세요.
""");

			for (int i = 0; i < contents.size(); i++) {
				System.out.println("  " + (i + 1) + ". " + contents.get(i).getTitle());
			}

			System.out.println("------------------------------------------------------------");
			System.out.print("> ");

			int selectedNum = readMenuNumber(sc);

			if (selectedNum < 1 || selectedNum > contents.size()) {
				System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				continue;
			}

			showSingleContentEditMenu(sc, contents.get(selectedNum - 1));
			return;
		}
	}

	public void showSingleContentEditMenu(Scanner sc, Content content) {
		try {
			ContentDTO contentDTO = new ContentDTO();
			contentDTO.setId(content.getId());

			System.out.print("  수정할 제목  : ");
			String title = sc.nextLine();
			contentDTO.setTitle(title.isBlank() ? content.getTitle() : title);

			System.out.print("  수정할 타입  : ");
			String type = sc.nextLine();
			contentDTO.setContentType(type.isBlank() ? content.getContentType() : ContentType.valueOf(type.trim().toUpperCase()));

			System.out.print("  수정할 내용  : ");
			String value = sc.nextLine();
			contentDTO.setContent(value.isBlank() ? content.getContent() : value);

			contentController.update(contentDTO);

			System.out.println("------------------------------------------------------------");
			System.out.println("  수정되었습니다.");
			System.out.println("------------------------------------------------------------");

		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private int readMenuNumber(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}
}