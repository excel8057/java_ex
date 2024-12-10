package course_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import course_management.CourseDAO;

public class CourseDAO {
	private static final String JDBC_URL = "jdbc:oracle:thin:@127.0.0.1:1522/xepdb1";
	private static final String USER = "javauser";
	private static final String PASSWD = "java1234";
	
	private static CourseDAO instance = null;
	
	public static CourseDAO getInstance() {
		if(instance == null) {
			instance = new CourseDAO();
		}
		return instance;
	}

	private CourseDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException{
		Connection con = DriverManager.getConnection(JDBC_URL,USER,PASSWD);
		return con;
	}

	public ArrayList<CourseVO> getCourseTotal(){
		CourseVO cvo = null;
		ArrayList<CourseVO> list = new ArrayList<CourseVO>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select no, c_num, c_name, c_credit, c_section from course ");
		sql.append("order by no");
		
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();){
			
			while (rs.next()) {
				cvo = new CourseVO();
				
				cvo.setNo(rs.getInt("no"));
				cvo.setC_num(rs.getString("c_num"));
				cvo.setC_name(rs.getString("c_name"));
				cvo.setC_credit(rs.getInt("c_credit"));
				cvo.setC_section(rs.getString("c_section"));
				
				list.add(cvo);
			}
		}
		catch(SQLException se) {
			System.err.println("잠시후 다시 시도해주세요");
			se.printStackTrace();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return list;
	}

	public boolean courseInsert(CourseVO svo) {
		boolean success = false;

		StringBuffer sql = new StringBuffer();
		sql.append("insert into course(no, c_num, c_name, c_credit, c_section) ");
		sql.append("values(course_seq.nextval, ?, ?, ?, ?)");

		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());){
			
			pstmt.setString(1, svo.getC_num());
			pstmt.setString(2, svo.getC_name());
			pstmt.setInt(3, svo.getC_credit());
			pstmt.setString(4, svo.getC_section());
			
			int i = pstmt.executeUpdate();
			if(i == 1) {
				success =true;
			}
		}
		catch(SQLException se) {
			System.err.println("잠시후 다시 시도해주세요");
			se.printStackTrace();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return success;
	}

    public boolean courseUpdate(CourseVO svo) {
        boolean success = false;

        StringBuffer sql = new StringBuffer();
        sql.append("update course set c_name = ?, c_credit = ?, c_section = ? ");
        sql.append("where c_num = ?");

        try (Connection conn = getConnection();
        	 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	){
 
            pstmt.setString(1, svo.getC_name());
            pstmt.setInt(2, svo.getC_credit());
            pstmt.setString(3, svo.getC_section());
            pstmt.setString(4, svo.getC_num());

            int i = pstmt.executeUpdate();
            if(i == 1) {
                success = true;
            }
        }catch(SQLException se) {
            System.out.println("수정에 문제가 있어 잠시 후에 다시 진행해 주세요.");
            se.printStackTrace();
        }catch (Exception e){
            System.out.println("error = [ "+e+" ]");
        } 
        return success;
    }
    

    public boolean courseDelete(CourseVO svo) {
        boolean success = false;

        StringBuffer sql = new StringBuffer();
        sql.append("delete from course where c_num = ?");
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	) {

            pstmt.setString(1, svo.getC_num());

            int i = pstmt.executeUpdate();
            if(i == 1) {
                success = true;
            }
        }catch(SQLException se) {
            System.out.println("삭제에 문제가 있어 잠시 후에 다시 진행해 주세요.");
            se.printStackTrace();
        }catch (Exception e){
            System.out.println("error = [ "+e+" ]");
        } 
        return success;
    }
    
    public ArrayList<CourseVO> courseSearch(CourseVO svo) {
		CourseVO result = null;
		ArrayList<CourseVO> list = new ArrayList<CourseVO>();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from course where c_name like ?");
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	) {

        	pstmt.setString(1, "%" + svo.getC_name() + "%");

            try (ResultSet rs = pstmt.executeQuery()) { // 이후에 실행
                while (rs.next()) { // rs.next()를 반복 처리
                    result = new CourseVO();
                    result.setNo(rs.getInt("no"));
                    result.setC_num(rs.getString("c_num"));
                    result.setC_name(rs.getString("c_name"));
                    result.setC_credit(rs.getInt("c_credit"));
                    result.setC_section(rs.getString("c_section"));

                    list.add(result); // 결과 리스트에 추가
                }
            }
        }
		catch (SQLException se) {
            System.out.println("쿼리 studentDataCheck error = [ "+se+" ]");
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("error = [ "+e+" ]");
        }
        return list;
    }
}
