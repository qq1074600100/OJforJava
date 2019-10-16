import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase4 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Map<List<Integer>, List<Integer>> datas = new HashMap<>();
        List<Integer> input1 = Arrays.asList(1, 2, 3);
        List<Integer> output1 = Arrays.asList(1, 2, 3);
        datas.put(input1, output1);
        List<Integer> input2 = Arrays.asList(-1, 3, 2, 100, -10);
        List<Integer> output2 = Arrays.asList(-10, -1, 2, 3, 100);
        datas.put(input2, output2);
        List<Integer> input3 = Arrays.asList(1, 20, 3, -100, 10);
        List<Integer> output3 = Arrays.asList(-100, 1, 3, 10, 20);
        datas.put(input3, output3);
        List<Integer> input4 = Arrays.asList(-1, -12, 3, 11, 111, 245, 10, 7, 8);
        List<Integer> output4 = Arrays.asList(-12, -1, 3, 7, 8, 10, 11, 111, 245);
        datas.put(input4, output4);
        int count = 0;
        long msOfRun = 0L;
        for (Map.Entry<List<Integer>, List<Integer>> data : datas.entrySet()) {
            long begin = System.currentTimeMillis();
            List<Integer> rst = solution.solute(data.getKey());
            long end = System.currentTimeMillis();
            if (!rst.equals(data.getValue())) {
                StringBuilder input = new StringBuilder();
                for (Integer num : data.getKey()) {
                    input.append(num + ",");
                }
                StringBuilder rstMsg = new StringBuilder();
                for (Integer num : rst) {
                    rstMsg.append(num + ",");
                }
                StringBuilder output = new StringBuilder();
                for (Integer num : data.getValue()) {
                    output.append(num + ",");
                }

                System.err.println("结果错误\n  输入:" + input
                                   + "\n  输出:" + rstMsg + "\n  答案:" + output);
                return;
            }
            msOfRun += end - begin;
            count++;
        }
        System.out.println((msOfRun / count));
    }
}