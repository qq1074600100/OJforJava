import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestCase3 {
    @Test
    public void testSolute() throws Exception {
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

        for (List<Integer> key : datas.keySet()) {
            Integer rst = solution.solute(key);
            Assert.assertEquals(rst, datas.get(key));
        }
    }
}