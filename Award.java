import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Award {
    Map <String,Integer> award = new HashMap<String,Integer>();
    public Award() {
        Scanner scan = new Scanner(System.in);
        int num;
        System.out.print("请设定“一秀”数量：");
        num = scan.nextInt();
        System.out.println("“一秀”数量设定为" + num);
        award.put("一秀", num);
        System.out.print("请设定“二举”数量：");
        num = scan.nextInt();
        System.out.println("“二举”数量设定为" + num);
        award.put("二举", num);
        System.out.print("请设定“三红”数量：");
        num = scan.nextInt();
        System.out.println("“三红”数量设定为" + num);
        award.put("三红", num);
        System.out.print("请设定“四进”数量：");
        num = scan.nextInt();
        System.out.println("“四进”数量设定为" + num);
        award.put("四进", num);
        System.out.print("请设定“对堂”数量：");
        num = scan.nextInt();
        System.out.println("“对堂”数量设定为" + num);
        award.put("对堂", num);
    }
}