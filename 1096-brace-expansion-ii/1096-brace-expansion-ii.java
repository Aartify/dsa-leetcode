import java.util.*;

class Solution {

    int index = 0;
    String expression;

    public List<String> braceExpansionII(String expression) {

        this.expression = expression;

        Set<String> result = parse();

        List<String> answer = new ArrayList<>(result);

        Collections.sort(answer);

        return answer;
    }

    Set<String> parse() {

        Set<String> result = new HashSet<>();
        result.add("");

        while (index < expression.length()
                && expression.charAt(index) != '}') {

            char ch = expression.charAt(index);

            Set<String> current;

            // Letter
            if (ch >= 'a' && ch <= 'z') {

                current = new HashSet<>();

                current.add(String.valueOf(ch));

                index++;
            }

            // {
            else if (ch == '{') {

                index++; // skip {

                current = parse();

                index++; // skip }
            }

            // ,
            else if (ch == ',') {

                index++;

                result.addAll(parse());

                break;
            }

            else {
                index++;
                continue;
            }

            // Concatenation
            Set<String> newResult = new HashSet<>();

            for (String a : result) {
                for (String b : current) {

                    newResult.add(a + b);
                }
            }

            result = newResult;
        }

        return result;
    }
}