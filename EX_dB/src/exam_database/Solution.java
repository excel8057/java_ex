package exam_database;

import java.util.*;

public class Solution {
    // 숫자가 해당 진법에서 유효한지 검사
    public static boolean isValidNumberInBase(int num, int base) {
        if (num == 0) return true;
        while (num > 0) {
            if (num % 10 >= base) return false;
            num /= 10;
        }
        return true;
    }

    // 식에서 가능한 최소 진법 찾기
    public static int findMinimumBase(String expr) {
        String[] parts = expr.split(" ");
        int a = Integer.parseInt(parts[0]);
        String op = parts[1];
        int b = Integer.parseInt(parts[2]);
        String result = parts[4];

        int minBase = 2;

        // a의 각 자릿수 확인
        int temp = a;
        while (temp > 0) {
            int digit = temp % 10;
            minBase = Math.max(minBase, digit + 1);
            temp /= 10;
        }

        // b의 각 자릿수 확인
        temp = b;
        while (temp > 0) {
            int digit = temp % 10;
            minBase = Math.max(minBase, digit + 1);
            temp /= 10;
        }

        // 결과값이 X가 아닌 경우 확인
        if (!result.equals("X")) {
            int c = Integer.parseInt(result);
            temp = c;
            while (temp > 0) {
                int digit = temp % 10;
                minBase = Math.max(minBase, digit + 1);
                temp /= 10;
            }
        }

        return minBase;
    }

    // 10진수로 변환
    public static int convertToDecimal(int num, int base) {
        int result = 0;
        int power = 1;

        while (num > 0) {
            result += (num % 10) * power;
            num /= 10;
            power *= base;
        }
        return result;
    }

    // 특정 진법으로 변환
    public static int convertFromDecimal(int decimal, int base) {
        if (decimal == 0) return 0;

        int result = 0;
        int power = 1;

        while (decimal > 0) {
            result += (decimal % base) * power;
            decimal /= base;
            power *= 10;
        }
        return result;
    }

    // 식의 유효성 검사
    public static boolean isValidExpression(String expr, int base) {
        String[] parts = expr.split(" ");
        int a = Integer.parseInt(parts[0]);
        String op = parts[1];
        int b = Integer.parseInt(parts[2]);
        String result = parts[4];

        if (!isValidNumberInBase(a, base) || !isValidNumberInBase(b, base)) return false;

        if (!result.equals("X")) {
            int c = Integer.parseInt(result);
            if (!isValidNumberInBase(c, base)) return false;

            int decA = convertToDecimal(a, base);
            int decB = convertToDecimal(b, base);
            int decC = convertToDecimal(c, base);

            if (op.equals("+")) return decA + decB == decC;
            else return decA - decB == decC;
        }

        return true;
    }

    public static String[] solution(String[] expressions) {
        // X가 포함된 식의 개수 세기
        int xCount = 0;
        for (String expr : expressions) {
            if (expr.contains("= X")) xCount++;
        }

        // 결과 배열 할당
        String[] answer = new String[xCount];
        int answerIdx = 0;

        // 가능한 진법 찾기
        int minBase = 2;
        boolean[] possibleBases = new boolean[10];

        // 전체 식에서 필요한 최소 진법 찾기
        for (String expr : expressions) {
            int currMin = findMinimumBase(expr);
            minBase = Math.max(minBase, currMin);
        }

        // 가능한 진법 체크
        for (int base = minBase; base <= 9; base++) {
            boolean valid = true;
            for (String expr : expressions) {
                if (!isValidExpression(expr, base)) {
                    valid = false;
                    break;
                }
            }
            possibleBases[base] = valid;
        }

        // X가 포함된 식 처리
        for (String expr : expressions) {
            if (expr.contains("= X")) {
                String[] parts = expr.split(" ");
                int a = Integer.parseInt(parts[0]);
                String op = parts[1];
                int b = Integer.parseInt(parts[2]);

                boolean first = true;
                int firstResult = 0;
                boolean allSame = true;
                boolean hasValidBase = false;

                for (int base = minBase; base <= 9; base++) {
                    if (possibleBases[base]) {
                        hasValidBase = true;
                        int decA = convertToDecimal(a, base);
                        int decB = convertToDecimal(b, base);
                        int decResult;

                        if (op.equals("+"))
                            decResult = decA + decB;
                        else
                            decResult = decA - decB;

                        int baseResult = convertFromDecimal(decResult, base);

                        if (first) {
                            firstResult = baseResult;
                            first = false;
                        } else if (baseResult != firstResult) {
                            allSame = false;
                            break;
                        }
                    }
                }

                if (!hasValidBase) {
                    answer[answerIdx] = a + " " + op + " " + b + " = " + 0;
                } else if (allSame) {
                    answer[answerIdx] = a + " " + op + " " + b + " = " + firstResult;
                } else {
                    answer[answerIdx] = a + " " + op + " " + b + " = ?";
                }

                answerIdx++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        // 예시 입력
        String[] expressions = {"14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"};
        String[] result = solution(expressions);
        System.out.println(Arrays.toString(result));
    }
}
