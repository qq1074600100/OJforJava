import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase3 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Map<List<Integer>, Integer> datas = new HashMap<>();
        List<Integer> input1 = Arrays.asList(1, 2, 3);
        datas.put(input1, 6);
        List<Integer> input2 = Arrays.asList(-1, 2, 3);
        datas.put(input2, 4);
        List<Integer> input3 = Arrays.asList(1, 20, 3);
        datas.put(input3, 24);
        List<Integer> input4 = Arrays.asList(-1, -12, 3);
        datas.put(input4, -10);
        int count = 0;
        int msOfRun = 0;
        for (Map.Entry<List<Integer>, Integer> data : datas.entrySet()) {
            long begin = System.currentTimeMillis();
            Integer rst = solution.solute(data.getKey());
            long end = System.currentTimeMillis();
            if (!rst.equals(data.getValue())) {
                StringBuilder input = new StringBuilder();
                for (Integer num : data.getKey()) {
                    input.append(num + ",");
                }
                System.err.println("结果错误\n  输入:" + input
                                   + "\n  输出:" + rst + "\n  答案:" + data.getValue());
                return;
            }
            msOfRun += end - begin;
            count++;
        }
        System.out.println((msOfRun / count));
    }
}