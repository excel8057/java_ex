package exam_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
//	public static void main(String[] args) {
//
//		String url = "jdbc:oracle:thin:@127.0.0.1:1522/xepdb1";
//	    Connection conn = null;
//	    try {
//	        Class.forName("oracle.jdbc.driver.OracleDriver"); 
//	        System.out.println("드라이버 적재 성공.");
//
//	        conn = DriverManager.getConnection(url, "javauser", "java1234");
//	        System.out.println("데이터베이스 연결 성공.");
//	    } catch (ClassNotFoundException e) {
//	        System.out.println("드라이버를 찾을 수 없습니다.");
//	        e.printStackTrace();
//	    } catch (SQLException e) {
//	        System.out.println("연결에 실패하였습니다");
//	        e.printStackTrace();
//	    }finally {
//	    	try {
//				if(conn != null) {
//					conn.close();
//				}
//	    	}catch(Exception e) {
//	    		System.out.println("해제에 실패하였습니다");
//	    	}
//		
//	    }
//	}
    public static Connection getConnection(String serviceName, String id, String password) throws Exception{
        String url = "jdbc:oracle:thin:@127.0.0.1:1522/"+serviceName;
        
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        Connection conn = DriverManager.getConnection(url, id, password);

        return conn;
    }
}