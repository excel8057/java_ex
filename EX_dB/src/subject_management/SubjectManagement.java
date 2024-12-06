package subject_management;

import java.util.ArrayList;

public class SubjectManagement {
    private SubjectDAO dao = SubjectDAO.getInstance();
    
    public void read(){
    	ArrayList<SubjectVO> svo = dao.getSubjectTotal();
    	  System.out.println("\n**** subject 테이블 데이터 출력 ****");
          System.out.println("번호\t학과번호\t학과명");
          if(svo.size() > 0) {
              //for(int i = 0; i < svo.size(); i++){
              // SubjectVO sub = svo.get(i);
              for(SubjectVO sub : svo) {
                  System.out.print(sub.getNo()+"\t");
                  System.out.print(sub.getS_num()+"\t");
                  System.out.println(sub.getS_name()+"\t");
              }
          } else {
              System.out.println("학과 정보가 존재하지 않습니다.");
          }
    }
}