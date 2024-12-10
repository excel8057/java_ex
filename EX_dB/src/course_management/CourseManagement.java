package course_management;

import java.util.ArrayList;
import java.util.Scanner;

public class CourseManagement {
    private CourseDAO dao = CourseDAO.getInstance();
    private Scanner scanner = new Scanner(System.in);

    // 전체 과목 조회
    public void read() {
        ArrayList<CourseVO> cvo = dao.getCourseTotal();
        System.out.println("\n**** Course 테이블 데이터 출력 ****");
        System.out.printf("%-5s%-10s%-20s\t%-8s%-5s\n", "번호", "학과번호", "학과명", "학점", "구분");
        System.out.println("------------------------------------------------------");

        if (cvo.size() > 0) {
            for (CourseVO course : cvo) {
                System.out.printf("%-5d%-10s%-20s\t%-8d%-5s\n", 
                    course.getNo(), 
                    course.getC_num(), 
                    course.getC_name(), 
                    course.getC_credit(), 
                    course.getC_section()
                );
            }
        } else {
        	System.out.println("과목 정보가 존재하지 않습니다.");
        }

    }

    // 새로운 과목 추가
    public void create() {
        System.out.println("\n**** 새로운 과목 추가 ****");
        CourseVO course = inputData("insert");
        if (dao.courseInsert(course)) {
            System.out.println("데이터 입력 성공");
        } else {
            System.out.println("데이터 입력 실패");
        }
    }

    // 기존 과목 수정
    public void update() {
        System.out.println("\n**** 과목 데이터 수정 ****");
        CourseVO course = inputData("update");
        if (dao.courseUpdate(course)) {
            System.out.println("데이터 수정 성공");
        } else {
            System.out.println("데이터 수정 실패");
        }
    }

    // 과목 삭제
    public void delete() {
        System.out.println("\n**** 과목 데이터 삭제 ****");
        System.out.print("삭제할 과목 번호를 입력하세요: ");
        String c_num = scanner.nextLine();
        CourseVO course = new CourseVO();
        course.setC_num(c_num);

        if (dao.courseDelete(course)) {
            System.out.println("데이터 삭제 성공");
        } else {
            System.out.println("데이터 삭제 실패");
        }
    }

    // 과목 검색
    public void search() {
        System.out.println("\n**** 과목명 검색 ****");
        System.out.print("검색할 과목명을 입력하세요: ");
        String c_name = scanner.nextLine();

        CourseVO course = new CourseVO();
        course.setC_name(c_name);

        ArrayList<CourseVO> results = dao.courseSearch(course);
        if (results.size() > 0) {
            System.out.println("번호\t학과번호\t학과명\t학점\t구분");
            for (CourseVO result : results) {
                System.out.print(result.getNo() + "\t");
                System.out.print(result.getC_num() + "\t");
                System.out.print(result.getC_name() + "\t");
                System.out.print(result.getC_credit() + "\t");
                System.out.println(result.getC_section());
            }
        } else {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    // 공통 입력 메서드
    private CourseVO inputData(String mode) {
        String c_num = "";
        String c_name = "";
        int c_credit = 0;
        String c_section = "";

        if (mode.equals("insert") || mode.equals("update")) {
        	System.out.print("학과번호명을 입력하세요: ");
            c_num = scanner.nextLine();
            System.out.print("과목명을 입력하세요: ");
            c_name = scanner.nextLine();
            System.out.print("학점을 입력하세요: ");
            c_credit = Integer.parseInt(scanner.nextLine());
            System.out.print("과목 구분(교양/전공)을 입력하세요: ");
            c_section = scanner.nextLine();
        }

        return new CourseVO(0, c_num, c_name, c_credit, c_section);
    }
}