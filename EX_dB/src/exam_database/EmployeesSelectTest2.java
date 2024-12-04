package exam_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class EmployeesSelectTest2 {
    public static void main(String arg[]) {
        String department_name;
        int employee_number;

        try (Connection conn = ConnectionDatabase.getConnection("xe", "hr", "hr1234");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT department_name, COUNT(employee_id) AS employee_count " +
                                              "FROM employees e " +
                                              "JOIN departments d USING(department_id) " +
                                              "GROUP BY department_name")) {

            System.out.println("부서명\t\t\t사원수");
            System.out.println("-----------------------------");

            while (rs.next()) {
                department_name = rs.getString("department_name");
                employee_number = rs.getInt("employee_count");

                System.out.printf("%-20s %d\n", department_name, employee_number);
            }

        } catch (Exception e) {
            System.err.println("[쿼리문 ERROR] \n" + e.getMessage());
        }
    }
}