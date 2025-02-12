package main.java.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.controller.admin.AdminController;
import main.java.controller.program.ProgramController;
import main.java.controller.user.UserController;
import main.java.model.program.dto.ProgramDTO;
import main.java.model.program.dto.ProgramRequestDTO;
import main.java.model.user.dto.UserResponseDTO;
import main.java.service.user.UserService;

public class StartView {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		while (true) {
			System.out.println("\n메뉴를 선택하세요");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 종료");
			int num = scanner.nextInt();
			scanner.nextLine();
			switch (num) {
			case 1:
				loginView();
				break;
			case 2:
				signUpView();
				break;
			case 3:
				return;
			}
		}
	}

	private static void loginView() throws SQLException {
		System.out.println("로그인을 시도합니다");
		System.out.print("아이디 : ");
		String userId = scanner.nextLine();
		System.out.print("비밀번호 : ");
		String password = scanner.nextLine();
		UserResponseDTO user_info = UserController.login(userId, password);
		if (user_info != null) {
			EndView.successMessage("로그인 성공");
			loginSuccessView(user_info.getRole().name());
		} else {
			FailView.failMessage("로그인 실패");
		}
	}

	private static void signUpView() throws SQLException {
		System.out.println("회원가입을 시작합니다");
		System.out.print("아이디 : ");
		String userId = scanner.nextLine();
		System.out.print("비밀번호 : ");
		String password = scanner.nextLine();

		if (UserController.signUp(userId, password)) {
			EndView.successMessage("회원가입 성공");
		} else {
			FailView.failMessage("중복된 아이디 입니다. 다시 시도해주세요.");
		}
	}

	private static void deleteUser() {
		System.out.println("삭제할 유저의 Id를 입력해주세요");
		String deleteId = scanner.nextLine();
		UserResponseDTO user = AdminController.getUserById(deleteId);
		if (user != null) {
			if (UserService.deleteUser(deleteId)) {
				EndView.successMessage("삭제 성공했습니다.");
			}
		} else {
			FailView.failMessage("삭제 실패했습니다.");
		}
	}

	private static void loginSuccessView(String role) throws SQLException {
		while (true) {
			// 검색, 여러 검색
			System.out.println("1. 제목 검색");
			System.out.println("2. 감독 검색");
			System.out.println("3. 나라 검색");
			System.out.println("4. 출시연도 검색");
			System.out.println("5. 장르 검색");
			System.out.println("6. 영화 목록 조회");
			System.out.println("7. 추천");
			System.out.println("8. 로그아웃");
			if (role.equals("ADMIN")) {
				System.out.println("---------어드민 기능---------");
				System.out.println("9. 프로그램 추가");
				System.out.println("10. 프로그램 수정");
				System.out.println("11. 프로그램 삭제");
				System.out.println("12. 전체 유저 조회");
				System.out.println("13. 유저 계정 삭제");
			}

			int num = scanner.nextInt();
			scanner.nextLine();
			if (num > 8 && !role.equals("ADMIN")) {
				System.out.println("1~8 중 하나를 입력하세요");
			} else {
				switch (num) {
				case 1:
					//수정필수
					String title = scanner.nextLine();
					EndView.printMovies(ProgramController.getProgramByTitle(title));
					break;
				case 2:
					String director = scanner.nextLine();
					EndView.printMovies(ProgramController.getProgramByDirector(director));
					break;
				case 3:
					String country = scanner.nextLine();
					EndView.printMovies(ProgramController.getProgramByCountry(country));
					break;
				case 4:
					int year = Integer.parseInt(scanner.nextLine());
					EndView.printMovies(ProgramController.getProgramByReleaseYear(year));
					break;
				case 5:
					String genre = scanner.nextLine();
					EndView.printMovies(ProgramController.getProgramByGenre(genre));
					break;
				case 6:
					EndView.printMovies(ProgramController.getAllProgram());
					break;
				case 7:
					String genre2 = scanner.nextLine();
					EndView.printMovies(ProgramController.getRecommendProgramByGenre(genre2));
					break;
				case 8:
					return;
				case 9:
					String showId = scanner.nextLine();
					String types = scanner.nextLine();
					String title3 = scanner.nextLine();
					String director3 = scanner.nextLine();
					String country3 = scanner.nextLine();
					int releaseYear = Integer.parseInt(scanner.nextLine());
					int duration = Integer.parseInt(scanner.nextLine());
					String listedIn = scanner.nextLine();
					String description = scanner.nextLine();
					ProgramController.addProgram(showId, types, title3, director3, country3, releaseYear, duration, listedIn, description);
					break;
				case 10:
					String showId2 = scanner.nextLine();
					String title2 = scanner.nextLine();
					ProgramController.updateProgramTitle(showId2, title2);
					break;
				case 11:
					String showId5 = scanner.nextLine();
					ProgramController.deleteProgram(showId5);
					break;
				case 12:
					EndView.printUsers(AdminController.getUsers());
					break;
				case 13:
					deleteUser();
					break;
				}
			}
		}
	}
}
