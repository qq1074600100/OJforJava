import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestCase4 {
    @Test
    public void testSolute() throws Exception {
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

        for (List<Integer> key : datas.keySet()) {
            List<Integer> rst = solution.solute(key);
            Assert.assertEquals(rst, datas.get(key));
        }
    }
}