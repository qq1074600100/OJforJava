import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCase1 {
    @Test
    public void testSolute() throws Exception {
        Solution solution = new Solution();
        Map<Integer, Integer> datas = new HashMap<Integer, Integer>();
        datas.put(1, 1);
        datas.put(-3, 3);
        datas.put(0, 0);
        for (Integer key : datas.keySet()) {
            Integer rst = solution.solute(key);
            Assert.assertEquals(rst, datas.get(key));
        }
    }
}
