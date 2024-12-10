package course_management;

public class CourseVO {
    private int no;              //번호(테이블의 컬럼명과 동일하게 명시.)
    private String c_num;        //subjectNumber, sNum //학과번호
    private String c_name;       //subjectName, sName  //학과명
    private int c_credit;		//학점
    private String c_section;	//구분?( 교양 전공 일반 )
    private String deleteable;   //삭제여부
	public CourseVO(int no, String c_num, String c_name, int c_credit, String c_section) {
		super();
		this.no = no;
		this.c_num = c_num;
		this.c_name = c_name;
		this.c_credit = c_credit;
		this.c_section = c_section;
	}
	public CourseVO() {
		super();
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getC_num() {
		return c_num;
	}
	public void setC_num(String c_num) {
		this.c_num = c_num;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public int getC_credit() {
		return c_credit;
	}
	public void setC_credit(int c_credit) {
		this.c_credit = c_credit;
	}
	public String getC_section() {
		return c_section;
	}
	public void setC_section(String c_section) {
		this.c_section = c_section;
	}
	public String getDeleteable() {
		return deleteable;
	}
	public void setDeleteable(String deleteable) {
		this.deleteable = deleteable;
	}
	@Override
	public String toString() {
		return "CourseVO [no=" + no + ", c_num=" + c_num + ", c_name=" + c_name + ", c_credit=" + c_credit
				+ ", c_section=" + c_section + ", deleteable=" + deleteable + "]";
	}
    
    
}
