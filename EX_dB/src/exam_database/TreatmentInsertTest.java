package exam_database;

import java.sql.Connection;
import java.sql.Statement;

public class TreatmentInsertTest {
    public static void main(String[] args) {

        try (Connection conn = ConnectionDatabase.getConnection("xe", "hr", "hr1234");
             Statement stmt = conn.createStatement()) {

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO treatment(t_no, t_course_abbr, t_course, t_tel) ");
            sb.append("VALUES(1004, 'GS', '일반외과', '02-3452-4001')");

            int insertCount = stmt.executeUpdate(sb.toString());

            if (insertCount == 1) {
                System.out.println("레코드 추가 성공");
            } else {
                System.out.println("레코드 추가 실패");
            }
        } catch (Exception e) {
            System.err.println("[쿼리문 ERROR] \n" + e.getMessage());
        }
    }
}
