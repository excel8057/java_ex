package student_management;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import subject_management.SubjectDAO;
import subject_management.SubjectVO;


public class StudentManagement {

	private SubjectDAO subDao = SubjectDAO.getInstance();
    private StudentDAO dao = StudentDAO.getInstance();
    private Scanner scanner = new Scanner(System.in);

    // 전체 학생 조회
    public void read() {
    	// 학생 테이블 데이터 출력 메서드
    	ArrayList<StudentVO> svo = dao.getStudentTotal();
    	System.out.println("\n**** Student 테이블 데이터 출력 ****");
    	System.out.printf(
    	    "%-5s%-9s%-8s%-14s%-13s%-12s%-12s%-15s%-35s\t%-18s%-12s\n",
    	    "번호", "학번", "이름", "아이디", "비밀번호", "학과번호", "생년월일", "전화번호", "주소", "이메일", "등록일"
    	);
    	System.out.print("---------------------------------------------------------------------------------------------------------------");
    	System.out.print("------------------------------------------------------------------\n");

    	if (svo.size() > 0) {
    	    for (StudentVO student : svo) {
    	        System.out.printf(
    	            "%-5d%-10s%-8s%-15s%-15s%-12s%-15s%-15s%-35s\t%-20s%-12s\n",
    	            student.getNo(),
    	            student.getSd_num(),
    	            student.getSd_name(),
    	            student.getSd_id(),
    	            student.getSd_passwd(),
    	            student.getS_num(),
    	            student.getSd_birth(),
    	            student.getSd_phone(),
    	            student.getSd_address(),
    	            student.getSd_email(),
    	            student.getSd_date()
    	        );
    	    }
    	} else {
    	    System.out.println("학생 정보가 존재하지 않습니다.");
    	}

    }

    // 새로운 학생 추가
    public void create() {
        System.out.println("\n**** 새로운 학생 추가 ****");
        StudentVO student = inputData("insert");
        if (dao.studentInsert(student)) {
            System.out.println("데이터 입력 성공");
        } else {
            System.out.println("데이터 입력 실패");
        }
    }

    // 기존 학생 수정
    public void update() {
        System.out.println("\n**** 학생 데이터 수정 ****");
        StudentVO student = inputData("update");
        if (dao.studentUpdate(student)) {
            System.out.println("데이터 수정 성공");
        } else {
            System.out.println("데이터 수정 실패");
        }
    }

    // 학생 삭제
    public void delete() {
        System.out.println("\n**** 학생 데이터 삭제 ****");
        System.out.print("삭제할 학생 번호를 입력하세요: ");
        String sd_num = scanner.nextLine();
        StudentVO student = new StudentVO();
        student.setSd_num(sd_num);

        if (dao.studentDelete(student)) {
            System.out.println("데이터 삭제 성공");
        } else {
            System.out.println("데이터 삭제 실패");
        }
    }

    // 학생 검색
    public void search() {
        System.out.println("\n**** 학생명 검색 ****");
        System.out.print("검색할 학생명을 입력하세요: ");
        String sd_name = scanner.nextLine();

        StudentVO studentSearch = new StudentVO();
        studentSearch.setSd_name(sd_name);

        ArrayList<StudentVO> results = dao.studentSearch(studentSearch);

    	System.out.println("\n**** Student 테이블 데이터 출력 ****");
    	System.out.printf(
    	    "%-5s%-10s%-20s%-15s%-15s%-10s%-12s%-15s%-20s%-25s%-20s\n",
    	    "번호", "학번", "이름", "아이디", "비밀번호", "학과번호", "생년월일", "전화번호", "주소", "이메일", "등록일"
    	);
    	System.out.println("---------------------------------------------------------------------------------------------------------------");

    	if (results.size() > 0) {
    	    for (StudentVO student : results) {
    	        System.out.printf(
    	            "%-5d%-10s%-20s%-15s%-15s%-10s%-12s%-15s%-20s%-25s%-20s\n",
    	            student.getNo(),
    	            student.getSd_num(),
    	            student.getSd_name(),
    	            student.getSd_id(),
    	            student.getSd_passwd(),
    	            student.getS_num(),
    	            student.getSd_birth(),
    	            student.getSd_phone(),
    	            student.getSd_address(),
    	            student.getSd_email(),
    	            student.getSd_date()
    	        );
    	    }
    	} else {
    	    System.out.println("검색 결과가 없습니다.");
    	}
    }

    // 공통 입력 메서드
    private StudentVO inputData(String mode) {
        String sd_name = "";
        String sd_id = "";
        String sd_passwd = "";
        String s_num = "";
        String sd_birth = "";
        String sd_phone = "";
        String sd_address = "";
        String sd_email = "";
        String sd_date = "";
        String sd_num = "";
        
        if(mode.equals("update")) {
            System.out.print("학번을 입력하세요: ");
            sd_num = scanner.nextLine();
        }

        System.out.print("학생명을 입력하세요: ");
        sd_name = scanner.nextLine();
        System.out.print("ID를 입력하세요: ");
        sd_id = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        sd_passwd = scanner.nextLine();

        
        
        System.out.print("생년월일을 입력하세요(YYYYMMDD): ");
        sd_birth = scanner.nextLine();
        System.out.print("번호를 입력하세요: ");
        sd_phone = scanner.nextLine();
        System.out.print("주소를 입력하세요: ");
        sd_address = scanner.nextLine();
        System.out.print("이메일를 입력하세요: ");
        sd_email = scanner.nextLine();

		if(mode.equals("insert")) {
            System.out.print("학과번호를 입력하세요: ");
            s_num = scanner.nextLine();
            boolean subMatch = false;
            for(SubjectVO svo : subDao.getSubjectTotal()) {
            	if(svo.getS_num().equals(s_num)) {
            		subMatch = true;
            		break;
            	}
            }
            if(!subMatch) {
            	System.out.println("해당 학과번호는 존재하지 않습니다.");
                return new StudentVO();
            }
            System.out.print("날짜를 입력하세요(YY/MM/DD, 공백시 오늘날짜): ");
            sd_date = scanner.nextLine();

    		if (sd_date.isBlank()) {
    		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
    		    sd_date = LocalDate.now().format(formatter);
    			System.out.println("자동 입력된 날짜: " + sd_date);
    		}
    		
	    	System.out.println("학번을 생성합니다.");
	    	sd_num = createStudentNumber(s_num, sd_date);
	    	System.out.println("학번 생성 완료: "+sd_num);
		}
        
        return new StudentVO(0, sd_num, sd_name, sd_id, sd_passwd, s_num, sd_birth, sd_phone, sd_address, sd_email, sd_date);
    }
    
    private String createStudentNumber(String s_num, String sd_date) {
    	Integer index = 1;
    	ArrayList<StudentVO> svo = dao.getStudentTotal();
    	for(StudentVO sv : svo) {
    		if(sv.getS_num().equals(s_num)) {
    			index++;
    		}
    	}
        String year = sd_date.substring(0, 2);
        String formattedIndex = String.format("%04d", index);

        return year + s_num + formattedIndex;
    }
}
