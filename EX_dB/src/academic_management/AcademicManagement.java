package academic_management;

import course_management.CourseManagement;
import student_management.StudentManagement;
import subject_management.SubjectManagement;

public class AcademicManagement {

	public static void main(String[] args) {
		SubjectManagement subject = new SubjectManagement();
		CourseManagement course = new CourseManagement();
		StudentManagement student = new StudentManagement();
		int topMenuchoice, subMenuchoice;
		
		System.out.println("학사 관리 프로그램을 시작합니다..");
		while (true) {
			MenuViewer.showTopMenu();
			topMenuchoice = MenuViewer.keyboard.nextInt();
			MenuViewer.keyboard.nextLine();
			
			if(topMenuchoice < 1 || topMenuchoice > MenuViewer.MAXNUM) {
				System.out.println("메뉴 선택 다시");
				continue;
			}
			
			if(topMenuchoice == MenuViewer.MAXNUM) {
				System.out.println("프로그램 종료");
				return;
			}
			
			MenuViewer.showSubMenu();
			subMenuchoice = MenuViewer.keyboard.nextInt();
			MenuViewer.keyboard.nextLine();
			
			if(topMenuchoice == 1) {
				switch (subMenuchoice){
					case 1 -> {
						System.out.println("조회");
						subject.read();
					}
					case 2 -> {
						System.out.println("입력");
						subject.create();
					}
					case 3 -> {
						System.out.println("수정");
	    				subject.update();	
					}
					case 4 -> {
						System.out.println("삭제");
	    				subject.delete();	
					}
					case 5 -> {
						System.out.println("검색(학과명)");
	    				subject.search();	
					}
					default -> System.out.println("조회, 입력, 수정, 삭제, 검색 중에 하나를 선택해 주면 됩니다");
				}
			}
			else if(topMenuchoice == 2){
				switch (subMenuchoice) {
	                case 1 -> course.read();
	                case 2 -> course.create();
	                case 3 -> course.update();
	                case 4 -> course.delete();
	                case 5 -> course.search();
	                default -> System.out.println("조회, 입력, 수정, 삭제, 검색 중에 하나를 선택해 주면 됩니다.");
				}
			}
			else if(topMenuchoice == 3){
				switch (subMenuchoice) {
                case 1 -> student.read();
                case 2 -> student.create();
                case 3 -> student.update();
                case 4 -> student.delete();
                case 5 -> student.search();
                default -> System.out.println("조회, 입력, 수정, 삭제, 검색 중에 하나를 선택해 주면 됩니다.");
				}
			}
		}
	}

}
