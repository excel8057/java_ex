package exam_database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

/* 진료과목 테이블에 데이터 입력. 사용자로 하여금 데이터를 입력받아 데이터 입력. */
public class TreatmentInsertTest {
    public static void main(String arg[]) {
        String courseAbbr, course, tel;
        int no;
        
        // 사용자로부터 데이터 입력 받기
        Scanner input = new Scanner(System.in);
        System.out.println("진료번호, 진료과목약어, 진료과목, 전화번호를 순서대로 입력해 주세요");
        no = input.nextInt();
        courseAbbr = input.next();
        course = input.next();
        tel = input.next();
        input.close();
        
        // try-with-resources를 사용하여 리소스 자동 관리
        try (Connection conn = ConnectionDatabase.getConnection("xe", "hr", "hr1234");
             Statement stmt = conn.createStatement()) {

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO treatment(t_no, t_course_abbr, t_course, t_tel) ");
            sb.append("VALUES (" + no + ", '" + courseAbbr + "','" + course );
            sb.append("','" + tel + "')");
            
            int insertCount = stmt.executeUpdate(sb.toString()); // 쿼리 실행

            if (insertCount == 1) { // 입력이 정상적으로 완료되면 반환값 1
                System.out.println("레코드 추가 성공");
            } else {
                System.out.println("레코드 추가 실패");
            }
        } catch (Exception e) {
            System.err.println("[쿼리문 ERROR] \n" + e.getMessage());
        }
    }
}
