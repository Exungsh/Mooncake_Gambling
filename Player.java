import java.util.Map;
import java.util.HashMap;

public class Player {
    Map<String, Integer> myaward = new HashMap<String, Integer>();

    public Player() {
        myaward.put("一秀", 0);
        myaward.put("二举", 0);
        myaward.put("三红", 0);
        myaward.put("四进", 0);
        myaward.put("对堂", 0);
        myaward.put("五子登科", 0);
        myaward.put("状元", 0);
        myaward.put("状元插金花", 0);
    }
}
