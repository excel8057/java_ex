package academic_management;

import java.util.Scanner;

public class MenuViewer {
    public static Scanner keyboard = new Scanner(System.in);

    public static void showTopMenu(){
    	String topMenu = """
    			선택하세요...
    			1. 학과 관리
    			2. 학생 관리
    			3. 프로그램 종료
    			""";
        System.out.println(topMenu);
        System.out.print("선택>> ");
    }

    public static void showSubMenu(){
    	String subMenu = """
    			1. 데이터 조회
    			2. 데이터 입력
    			3. 데이터 수정
    			4. 데이터 삭제
    			5. 데이터 검색(학과명)
    			""";
        System.out.println(subMenu);
        System.out.print("선택>> ");
    }

}