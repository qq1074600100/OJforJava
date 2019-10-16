import java.util.HashMap;
import java.util.Map;

public class TestCase1 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Map<Integer, Integer> datas = new HashMap<Integer, Integer>();
        datas.put(1, 1);
        datas.put(-3, 3);
        datas.put(0, 0);
        int count = 0;
        int msOfRun = 0;
        for (Map.Entry<Integer, Integer> data : datas.entrySet()) {
            long begin = System.currentTimeMillis();
            Integer rst = solution.solute(data.getKey());
            long end = System.currentTimeMillis();
            if (!rst.equals(data.getValue())) {
                System.err.println("结果错误\n  输入:" + data.getKey() + "\n  输出:" + rst + "\n  答案:" + data.getValue());
                return;
            }
            msOfRun += end - begin;
            count++;
        }
        System.out.println((msOfRun / count));
    }
}