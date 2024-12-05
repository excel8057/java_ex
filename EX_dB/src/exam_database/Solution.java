package exam_database;

import java.util.*;

class Solution {
    public String[] solution(String[] expressions) {
        List<String> result = new ArrayList<>();

        // 수식 처리
        for (String expression : expressions) {
            String[] parts = expression.split(" ");
            String A = parts[0]; // 첫 번째 숫자
            String operator = parts[1]; // 연산자 (+ 또는 -)
            String B = parts[2]; // 두 번째 숫자
            String C = parts[4]; // 결과 (X 또는 숫자)

            // 'X'가 아니면 결괏값이므로 넘어감
            if (!C.equals("X")) {
                result.add(expression);
                continue;
            }

            // 가능한 진법을 추론하기 위한 최소 진법 계산
            int minBase = Math.max(maxDigit(A), maxDigit(B)) + 1; // 최소 진법은 가장 큰 숫자의 값 + 1

            Set<String> possibleResults = new HashSet<>();
            
            // 6 ~ 9진법으로 계산 시도
            for (int base = 6; base <= 9; base++) {
                if (base < minBase) continue; // 최소 진법보다 작은 진법은 패스

                try {
                    int numA = Integer.parseInt(A, base);
                    int numB = Integer.parseInt(B, base);
                    int resultValue;

                    // 덧셈 또는 뺄셈을 수행
                    if (operator.equals("+")) {
                        resultValue = numA + numB;
                    } else if (operator.equals("-")) {
                        resultValue = numA - numB;
                    } else {
                        throw new IllegalArgumentException("Invalid operator");
                    }

                    String resultStr = Integer.toString(resultValue, base).toUpperCase();
                    possibleResults.add(resultStr);

                } catch (NumberFormatException e) {
                    // 해당 진법에서 숫자 변환 불가 시, 그냥 넘어감
                }
            }

            // 가능한 결과가 하나만 있다면 그 값으로 대체, 여러 개일 경우 '?' 출력
            if (possibleResults.size() == 1) {
                result.add(A + " " + operator + " " + B + " = " + possibleResults.iterator().next());
            } else {
                result.add(A + " " + operator + " " + B + " = ?");
            }
        }

        return result.toArray(new String[0]);
    }

    // 문자열에서 가장 큰 숫자를 찾아서 진법 계산을 위한 최소값을 구함
    private int maxDigit(String num) {
        int maxDigit = 0;
        for (char c : num.toCharArray()) {
            maxDigit = Math.max(maxDigit, c - '0');
        }
        return maxDigit;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 테스트 케이스 1
        String[] expressions1 = {"14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"};
        System.out.println(Arrays.toString(solution.solution(expressions1))); // ["13 - 6 = 5"]

        // 테스트 케이스 2
        String[] expressions2 = {"1 + 1 = 2", "1 + 3 = 4", "1 + 5 = X", "1 + 2 = X"};
        System.out.println(Arrays.toString(solution.solution(expressions2))); // ["1 + 5 = ?", "1 + 2 = 3"]

        // 테스트 케이스 3
        String[] expressions3 = {"10 - 2 = X", "30 + 31 = 101", "3 + 3 = X", "33 + 33 = X"};
        System.out.println(Arrays.toString(solution.solution(expressions3))); // ["10 - 2 = 4", "3 + 3 = 10", "33 + 33 = 110"]

        // 테스트 케이스 4
        String[] expressions4 = {"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "5 - 5 = X"};
        System.out.println(Arrays.toString(solution.solution(expressions4))); // ["2 + 2 = 4", "7 + 4 = ?", "5 - 5 = 0"]

        // 테스트 케이스 5
        String[] expressions5 = {"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "8 + 4 = X"};
        System.out.println(Arrays.toString(solution.solution(expressions5))); // ["2 + 2 = 4", "7 + 4 = 12", "8 + 4 = 13"]
    }
}
