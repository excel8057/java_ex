package exam_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BooksTableTest {
    public static Scanner input = new Scanner(System.in);

    public static void showMenu() {
        String menu = """
                선택하세요...
                1. 데이터 입력
                2. 데이터 검색
                3. 데이터 삭제
                4. 프로그램 종료
                """;
        System.out.println(menu);
        System.out.print("선택: ");
    }

    public static void main(String[] args) {
        int choice;

        while (true) {
            showMenu();
            choice = input.nextInt();
            input.nextLine();

            switch(choice) {
            case 1:
                addBook();
                break;
            case 2:
                readBook();
                break;
            case 3:
                deleteBook();
                break;
            case 4:
                System.out.println("프로그램을 종료합니다.");
                return;
            }
        }
    }

    private static void addBook() {
        int price;
        String title, publisher, year;

        System.out.println("책이름, 출판사, 출간연도, 가격을 순서대로 입력해 주세요");
        title = input.next();
        publisher = input.next();
        year = input.next();
        price = input.nextInt();

        try (Connection conn = ConnectionDatabase.getConnection("xepdb1", "javauser", "java1234");
             Statement stmt = conn.createStatement()) {

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO books (book_id, title, publisher, ");
            sb.append("year, price) VALUES (books_seq.nextval, ");
            sb.append("'" + title + "','" + publisher + "','");
            sb.append(year + "'," + price + ")");

            System.out.println("쿼리문 확인: " + sb);

            int insertCount = stmt.executeUpdate(sb.toString());

            if (insertCount == 1) {
                System.out.println("레코드 추가 성공");
            } else {
                System.out.println("레코드 추가 실패");
            }
        } catch (Exception e) {
            System.err.println("[쿼리문 ERROR] \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void readBook() {
        System.out.println("검색할 책 제목을 입력하세요: ");
        String title = input.nextLine();

        try (Connection conn = ConnectionDatabase.getConnection("xepdb1", "javauser", "java1234");
             Statement stmt = conn.createStatement()) {

            // 책 제목에 대한 LIKE 검색
            String query = "SELECT book_id, title, publisher, year, price FROM books WHERE title LIKE '%" + title + "%'";
            ResultSet rs = stmt.executeQuery(query);

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("책 ID: " + rs.getInt("book_id"));
                System.out.println("책 제목: " + rs.getString("title"));
                System.out.println("출판사: " + rs.getString("publisher"));
                System.out.println("출간 연도: " + rs.getString("year"));
                System.out.println("가격: " + rs.getInt("price"));
                System.out.println("----------------------");
            }

            // 검색 결과가 없을 경우
            if (!found) {
                System.out.println("검색 결과가 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("[쿼리문 ERROR] \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void deleteBook() {
        System.out.println("삭제할 책의 ID를 입력하세요: ");
        int bookId = input.nextInt();

        try (Connection conn = ConnectionDatabase.getConnection("xepdb1", "javauser", "java1234");
             Statement stmt = conn.createStatement()) {

            // 주어진 ID에 해당하는 책 삭제
            String query = "DELETE FROM books WHERE book_id = " + bookId;
            int deleteCount = stmt.executeUpdate(query);

            if (deleteCount > 0) {
                System.out.println("책이 삭제되었습니다.");
            } else {
                System.out.println("해당 ID의 책을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("[쿼리문 ERROR] \n" + e.getMessage());
            e.printStackTrace();
        }
    }
}
