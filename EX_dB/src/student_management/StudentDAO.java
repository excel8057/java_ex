package student_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class StudentDAO {
	private static final String JDBC_URL = "jdbc:oracle:thin:@127.0.0.1:1521/xepdb1";
	private static final String USER = "javauser";
	private static final String PASSWD = "java1234";
	
	private static StudentDAO instance = null;
	
	public static StudentDAO getInstance() {
		if(instance == null) {
			instance = new StudentDAO();
		}
		return instance;
	}

	private StudentDAO() {
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

	public ArrayList<StudentVO> getStudentTotal() {
	    StudentVO svo = null;
	    ArrayList<StudentVO> list = new ArrayList<StudentVO>();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT no, sd_num, sd_name, sd_id, sd_passwd, ");
	    sql.append("s_num, sd_birth, sd_phone, sd_address, sd_email, ");
	    sql.append("TO_CHAR(sd_date, 'YY/MM/DD') AS sd_date "); // 날짜 형식 변환
	    sql.append("FROM student ");
	    sql.append("ORDER BY no");

	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            svo = new StudentVO();

	            svo.setNo(rs.getInt("no"));
	            svo.setSd_num(rs.getString("sd_num"));
	            svo.setSd_name(rs.getString("sd_name"));
	            svo.setSd_id(rs.getString("sd_id"));
	            svo.setSd_passwd(rs.getString("sd_passwd"));
	            svo.setS_num(rs.getString("s_num"));
	            svo.setSd_birth(rs.getString("sd_birth"));
	            svo.setSd_phone(rs.getString("sd_phone"));
	            svo.setSd_address(rs.getString("sd_address"));
	            svo.setSd_email(rs.getString("sd_email"));
	            svo.setSd_date(rs.getString("sd_date")); // 변환된 날짜 가져오기

	            list.add(svo);
	        }
	    } catch (SQLException se) {
	        System.err.println("잠시 후 다시 시도해주세요");
	        se.printStackTrace();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    return list;
	}

	

	public boolean studentInsert(StudentVO svo) {
		boolean success = false;

		StringBuffer sql = new StringBuffer();
		sql.append("insert into student ");
		sql.append("values(student_seq.nextval,?, ?, ?, ?, ?");
		sql.append(", ?, ?, ?, ?, ?)");

		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());){
			
			pstmt.setString(1, svo.getSd_num());
			pstmt.setString(2, svo.getSd_name());
			pstmt.setString(3, svo.getSd_id());
			pstmt.setString(4, svo.getSd_passwd());
			pstmt.setString(5, svo.getS_num());
			pstmt.setString(6, svo.getSd_birth());
			pstmt.setString(7, svo.getSd_phone());
			pstmt.setString(8, svo.getSd_address());
			pstmt.setString(9, svo.getSd_email());
			pstmt.setString(10, svo.getSd_date());
			
			if(svo.getS_num() == null) {
				return success;
			}
			
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

    public boolean studentUpdate(StudentVO svo) {
        boolean success = false;

        StringBuffer sql = new StringBuffer();
        sql.append("update student set sd_name = ?, sd_id = ?, ");
        sql.append("SD_PASSWD = ?, S_NUM = ?, SD_BIRTH = ?,");
        sql.append("SD_PHONE = ?, SD_ADDRESS = ?, SD_EMAIL = ?,SD_DATE = ?");
        sql.append("where sd_num = ?");

        try (Connection conn = getConnection();
        	 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	){

			pstmt.setString(1, svo.getSd_num());
			pstmt.setString(2, svo.getSd_name());
			pstmt.setString(3, svo.getSd_id());
			pstmt.setString(4, svo.getSd_passwd());
			pstmt.setString(5, svo.getS_num());
			pstmt.setString(6, svo.getSd_birth());
			pstmt.setString(7, svo.getSd_phone());
			pstmt.setString(8, svo.getSd_address());
			pstmt.setString(9, svo.getSd_email());
			pstmt.setString(10, svo.getSd_date());

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
    

    public boolean studentDelete(StudentVO svo) {
        boolean success = false;

        StringBuffer sql = new StringBuffer();
        sql.append("delete from student where sd_num = ?");
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	) {

            pstmt.setString(1, svo.getSd_num());

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
    
    public ArrayList<StudentVO> studentSearch(StudentVO svo) {
		StudentVO result = null;
		ArrayList<StudentVO> list = new ArrayList<StudentVO>();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from student where sd_name like ?");
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	) {

        	pstmt.setString(1, "%" + svo.getSd_name() + "%");

            try (ResultSet rs = pstmt.executeQuery()) { // 이후에 실행
                while (rs.next()) { // rs.next()를 반복 처리
                    result = new StudentVO();
                    result.setNo(rs.getInt("no"));
                    result.setSd_num(rs.getString("sd_num"));
                    result.setSd_name(rs.getString("sd_name"));
                    result.setSd_id(rs.getString("sd_id"));
                    result.setSd_passwd(rs.getString("sd_passwd"));
                    result.setS_num(rs.getString("s_num"));
                    result.setSd_birth(rs.getString("sd_birth"));
                    result.setSd_phone(rs.getString("sd_phone"));
                    result.setSd_address(rs.getString("sd_address"));
                    result.setSd_email(rs.getString("sd_email"));
                    result.setSd_date(rs.getString("sd_date"));

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
