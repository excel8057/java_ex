package subject_management;

import java.util.ArrayList;

import academic_management.MenuViewer;

public class SubjectManagement {
    private SubjectDAO dao = SubjectDAO.getInstance();
    
    public void read(){
    	ArrayList<SubjectVO> svo = dao.getSubjectTotal();
    	  System.out.println("\n**** subject 테이블 데이터 출력 ****");
          System.out.println("번호\t학과번호\t학과명");
          if(svo.size() > 0) {

              for(SubjectVO sub : svo) {
                  System.out.print(sub.getNo()+"\t");
                  System.out.print(sub.getS_num()+"\t");
                  System.out.println(sub.getS_name()+"\t");
              }
          } else {
              System.out.println("학과 정보가 존재하지 않습니다.");
          }
    }
    private SubjectVO inputData(String mode) {
        String s_num=null, s_name=null;

        if(mode.equals("insert")) {
            System.out.print("학과코드 입력 : ");
            s_num = dao.getSubjectNum();
            System.out.println(s_num);
        }
        		
        if(!mode.equals("delete")) {
            System.out.print("학과명 입력 : ");
            s_name = MenuViewer.keyboard.nextLine();
        }
        
        if(mode.equals("update") || mode.equals("delete")) {
        	System.out.print("학과코드 입력 : ");
        	s_num = MenuViewer.keyboard.nextLine();
        }

        SubjectVO sub = new SubjectVO(0, s_num, s_name);
        return sub;
    }

    public void create(){
        SubjectVO svo = inputData("insert");
        boolean result = dao.subjectInsert(svo);
        if(result) {
            System.out.println("학과 데이터 입력 성공.");
        } else {
            System.out.println("학과 데이터 입력 실패.");
        }
    }
    public void update(){
        SubjectVO svo = inputData("update");

        boolean result = dao.subjectUpdate(svo);
        if(result) {
            System.out.println("학과 데이터 수정 성공.");
        } else {
            System.out.println("학과 데이터 수정 실패.");
        }
    }
    public void delete() {
        SubjectVO vo = inputData("delete");
        int data = dao.studentDataCheck(vo);

        if(data != 0) {
            System.out.println("소속된 학생이 존재함으로 학과데이터를 삭제할 수 없습니다.");
        } else {
        	System.out.print("입력하신 학과번호로 삭제하시겠습니까?[삭제시 예, 취소시 아니오]");
        	String confirm = MenuViewer.keyboard.next();
        	if(confirm.equals("예")) {
	            boolean result = dao.subjectDelete(vo);
	            if (result) {
	                System.out.println("학과 데이터 삭제 성공.");
	            } else {
	                System.out.println("학과 데이터 삭제 실패.");
	            }
        	}
        }
    }
    public void search() {
    	SubjectVO svo = inputData("search");
        System.out.println("검색 단어 :"+svo.getS_name());

        ArrayList<SubjectVO> list = dao.getsubjectSearch(svo.getS_name());
        System.out.println("번호\t학과번호\t학과명");
        if(list.size()>0){
            for(SubjectVO sub : list){
                System.out.print(sub.getNo()+"\t");
                System.out.print(sub.getS_num()+"\t");
                System.out.println(sub.getS_name()+"\t");
            }
        } else {
            System.out.println("학과 정보가 존재하지 않습니다.");
        }
    }
}