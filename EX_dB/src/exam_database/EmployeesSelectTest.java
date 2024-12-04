package exam_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeesSelectTest {
	public static void main(String[] args) {
		int employee_id, salary;
		String first_name, hire_date;

		
		try (Connection conn = ConnectionDatabase.getConnection("xe", "hr", "hr1234");
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(
	                     "SELECT employee_id, first_name, salary, " +
	                     "TO_CHAR(hire_date, 'YYYY.MM.DD') hire_date FROM employees")) {

	            System.out.println("데이터 출력");
	            System.out.printf("%s\t%s\t%6s\t%8s\n", "사원번호", "사원이름", "급여", "입사일");

			while(rs.next()) {
				employee_id = rs.getInt("employee_id");
				first_name = rs.getString("first_name");
				salary = rs.getInt("salary");
				hire_date = rs.getString("hire_date");//
				
				System.out.printf("%-7d %-11s %-6d %s\n", employee_id, first_name, salary, hire_date);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}