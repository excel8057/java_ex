package subject_management;

/* VO(Value Object)클래스는 데이터를 담는 컨테이너 역할을 하는 클래스로 데이터 전달을
 * 목적으로 만들어진 클래스이다.
 * VO클래스는 어플리케이션 구조에서 각 단계의 입출력 정보를 전달하는 역할을 수행하며,
 * 속성(attribute: 필드), setter(설정자)와 getter(접근자)로 구성된다. */

public class SubjectVO {
    private int no;              //번호(테이블의 컬럼명과 동일하게 명시.)
    private String s_num;        //subjectNumber, sNum //학과번호
    private String s_name;       //subjectName, sName  //학과명
    private String deleteable;   //삭제여부

    public SubjectVO() { }

    public SubjectVO(String s_num, String s_name) {
        this(0, s_num, s_name);
    }

    public SubjectVO(int no, String s_num, String s_name) {
        this.no = no;
        this.s_num = s_num;
        this.s_name = s_name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getS_num() {
        return s_num;
    }

    public void setS_num(String s_num) {
        this.s_num = s_num;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

//    public String getDeleteable() {
//		return deleteable;
//	}
//
//	public void setDeleteable(String deleteable) {
//		this.deleteable = deleteable;
//	}

	@Override
    public String toString() {
        return "Subject [no=" + no + ", s_num=" + s_num + ", s_name=" + s_name + "]";
    }
}