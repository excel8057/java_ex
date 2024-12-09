package subject_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/* DAO(Data Access Object) 클래스가 데이터 처리의 궁극적인 단계이다.
 *
 * CRUD 프로그램 구현
 * 기본적인 데이터 처리 기능인 입력(Create, insert), 조회(Read(또는 Retrieve), Select), 
 * 수정(Update), 삭제(Delete) 기능을 구현한 데이터베이스 프로그램.
 * */

public class SubjectDAO {
    // 데이터베이스 연결 관련 상수 선언
    private static final String JDBC_URL = "jdbc:oracle:thin:@127.0.0.1:1522/xepdb1";
    private static final String USER = "javauser";
    private static final String PASSWD = "java1234";

    // 클래스 자신의 타입으로 정적 필드 선언
    private static SubjectDAO instance = null;

    // 외부에서 호출할 수 있는 정적 메소드인 getInstance() 선언하여 인스턴스를 반환.
    public static SubjectDAO getInstance() {
        if(instance == null) {
            instance = new SubjectDAO();
        }
        return instance;
    }

    // 외부에서 new 연산자로 생성자를 호출할 수 없도록 막기 위해 접근 제한자(private) 설정
    private SubjectDAO(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException{
        Connection con = DriverManager.getConnection(JDBC_URL, USER, PASSWD);
        return con;
    }

    /***********************************************************
     * getSubjectTotal() 메서드: 학과 테이블에서 모든 레코드를 반환 메서드
     * @return ArrayList<SubjectVO> 자료형 리턴.
     ***********************************************************/
    public ArrayList<SubjectVO> getSubjectTotal(){
        SubjectVO svo  = null;
        ArrayList<SubjectVO> list = new ArrayList<SubjectVO>();
        
        StringBuffer sql = new StringBuffer();
        sql.append("select no, s_num, s_name from subject ");
        sql.append("order by no");
        
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());	
        	ResultSet rs = pstmt.executeQuery();){

            //ResultSet의 결과에서 모든 행을 각각의 SubjectVO 객체에 저장
            while(rs.next()) {
                //한 행의 학과 정보를 저장할 VO 객체 생성
                svo = new SubjectVO();
                //한 행의 학과 정보를 VO 객체에  저장
                svo.setNo(rs.getInt("no"));
                svo.setS_num(rs.getString("s_num"));
                svo.setS_name(rs.getString("s_name"));

                // ArrayList 객체에 원소로 추가
                list.add(svo);
            }
        }catch(SQLException se) {
            System.out.println("조회에 문제가 있어 잠시 후에 다시 진행해 주세요.");
            se.printStackTrace();
        }catch (Exception e){
            System.err.println("error = [ "+e.getMessage()+" ]");
        }
        
        return list;
    }
    public String getSubjectNum() {
    	int subjectNum = 0;
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("select max(s_num)+1 maxNum from subject ");
 
    	try (Connection conn = getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
    			ResultSet rs = pstmt.executeQuery();){
    		
    		rs.next();
    		subjectNum = rs.getInt("maxNum");

		} catch (Exception e) {
			System.out.println(e.toString());
		}
    	return String.format("%02d", subjectNum);
    }
    public boolean subjectInsert(SubjectVO subjectVo) {
    	boolean success = false;
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("insert into subject(no, s_num, s_name) ");
    	sql.append("values (subject_seq.nextval, ?, ?) " );
    	
    	try (Connection conn = getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
			pstmt.setString(1, subjectVo.getS_num());
			pstmt.setString(2, subjectVo.getS_name());
			
			int i = pstmt.executeUpdate();
			if(i == 1) success = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return success;
    }
    public boolean subjectUpdate(SubjectVO svo) {
        boolean success = false;

        StringBuffer sql = new StringBuffer();
        sql.append("update subject set s_name = ? ");
        sql.append("where s_num = ?");

        try (Connection conn = getConnection();
        	 PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	){
 
            pstmt.setString(1, svo.getS_name());
            pstmt.setString(2, svo.getS_num());

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
    public int studentDataCheck(SubjectVO svo) {
        StringBuffer  sql    = new StringBuffer();
        sql.append("select count(sd_num) from student ");
        sql.append("where s_num = ?");

        ResultSet rs = null;
        int data = 0;
        try (Connection con = getConnection();
        	 PreparedStatement pstmt = con.prepareStatement(sql.toString());
        	){

            pstmt.setString(1, svo.getS_num());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                data = rs.getInt(1);
            }
        } catch (SQLException se) {
            System.out.println("쿼리 studentDataCheck error = [ "+se+" ]");
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("error = [ "+e+" ]");
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException se) {
                System.out.println("디비 연동 해제 error = [ "+se+" ]");
            }
        }
        return data;
    }
    public boolean subjectDelete(SubjectVO svo) {
        boolean success = false;

        StringBuffer sql = new StringBuffer();
        sql.append("delete from subject where s_num = ?");
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	) {

            pstmt.setString(1, svo.getS_num());

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
   
    public ArrayList<SubjectVO> subjectSearch(SubjectVO svo) {
		SubjectVO result = null;
		ArrayList<SubjectVO> list = new ArrayList<SubjectVO>();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from subject where s_name like ?");
        try(Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        	) {

        	pstmt.setString(1, "%" + svo.getS_name() + "%");

            try (ResultSet rs = pstmt.executeQuery()) { 
                while (rs.next()) { // rs.next() result = new SubjectVO();
                    result.setNo(rs.getInt("no"));
                    result.setS_num(rs.getString("s_num"));
              result.setS_name(rs.getString("s_name"));
                    list.add(result); 
                }
            }
        }
		catch (SQLException se) {
            System.out.println("쿼리 studentDataCheck error = [ "+se+" ]");
            se.printStackTrace();
        }        
        return list;
    }
}