import java.util.Scanner;
import java.util.Random;

public class Dice {
    //因为状元共三种等级，所以单独用变量来记录当前状元的状态
    int winner = -1;
    String winner_prize;
    int winner_rank = -1;
    //玩家总数
    int player_num;
    //奖品总表，记录剩余奖品数
    Award gameaward;
    //玩家列表，每个元素记录玩家当前得奖情况
    Player[] playerlist;

    //输入玩家编号，状元等级，状元名，判定是否追饼成功
    boolean update_winner(int round, int rank,String prize) {
        if (rank > winner_rank) {
            if (winner_rank != -1) {
                playerlist[winner].myaward.put(prize, 0);
            }
            winner_rank = rank;
            winner = round;
            winner_prize = prize;
            return true;
        } else {
            return false;
        }
    }
    //传入奖项名，将奖品总表中对应奖品的数量减一
    void update_award(String name) {
        gameaward.award.put(name, gameaward.award.get(name) - 1);
    }
    //传入玩家号和奖项，将玩家奖品表对应奖品的数量加一
    void insert_award(int round, String name) {
        playerlist[round].myaward.put(name, playerlist[round].myaward.get(name) + 1);
    }
    //游戏结束，打印每个玩家的获奖情况
    void print_result() {
        System.out.println("\n游戏结束");
        for (int i = 0; i < player_num; i++) {
            System.out.println("\n\n玩家" + i + "获得奖品:");
            for (String key : playerlist[i].myaward.keySet()) {
                if (playerlist[i].myaward.get(key) > 0) {
                    System.out.println(key + ":" + playerlist[i].myaward.get(key));
                }
            }
        }
        System.exit(0);
    }
    //六杯红，一个不太可能触发的函数
    void liubeihong(int round) {
        //夺走其他玩家的所有奖品
        for (int i = 0; i < player_num; i++) {
            if (i != round) {
                for (String key : playerlist[round].myaward.keySet()) {
                    playerlist[round].myaward.put(key,
                            playerlist[round].myaward.get(key) + playerlist[i].myaward.get(key));
                    playerlist[i].myaward.put(key, 0);

                }
            }
        }
        //结束游戏
        print_result();
    }
    //回合结束进行判定：是否能领奖、游戏是否结束
    void judge(int round, String name) {
        int flag = 1;
        for (String key : gameaward.award.keySet()) {
            if (gameaward.award.get(key) != 0) {
                flag = 0;
            }
        }
        if (gameaward.award.get(name) == 0) {
            System.out.println("，但是奖品已经被领光了555");
        }
        else {
            update_award(name);
            insert_award(round, name);
            System.out.println("，恭喜！！");
        }
        if (flag == 1 && winner != -1) {
            print_result();
        }
    }

    
    //main
    public static void main(String[] args) {
        Dice gamedice = new Dice();
        int round = 0;
        int num;
        // welcome
        System.out.println("-------------------------------");
        System.out.println("欢迎参加博饼游戏");
        System.out.println("-------------------------------");
        System.out.println();
        // new award
        System.out.println("*设置奖品数*");
        gamedice.gameaward = new Award();
        // new player
        // System.out.println("------------初始化玩家-----------");
        System.out.println("输入玩家数：");
        Scanner scan = new Scanner(System.in);
        num = scan.nextInt();
        gamedice.player_num = num;
        gamedice.playerlist = new Player[num];
        for (int i = 0; i < num; i++) {
            gamedice.playerlist[i] = new Player();
        }
        // start game
        System.out.println("------------游戏开始-----------");
        scan.nextLine();
        scan.nextLine();
        while (true) {
            System.out.println("\n\n现在是玩家 " + round + " 的回合：");
            //-----------------dice----------------

            //扔骰子，输出扔出点数，统计各点数出现次数
            int[] count = new int[7];
            for (int i = 0; i < 6; i++) {
                Random r = new Random();
                int result = r.nextInt(6) + 1;
                System.out.printf("%d ", result);
                count[result]++;
            }
            System.out.println();

            //判定得奖情况
            //先判定较为特殊的对堂和四进，然后根据四点的个数进行判定（考虑到对堂也是一秀，四进有机会拿二举）
            if (count[1] == 1 && count[2] == 1 && count[3] == 1 && count[4] == 1 && count[5] == 1 && count[6] == 1) {
                System.out.print("对堂");
                gamedice.judge(round, "对堂");
            }
            if (count[1] >= 4 || count[2] >= 4 || count[3] >= 4 || count[5] >= 4 || count[6] >= 4) {
                System.out.print("四进");
                gamedice.judge(round, "四进");
            } else {
                if (count[4] == 6) {
                    System.out.println("六杯红");
                    gamedice.liubeihong(round);

                } else if (count[4] == 5) {
                    if (gamedice.update_winner(round, 2, "五子登科")) {
                        System.out.println("五子登科,恭喜！！");
                        gamedice.insert_award(round, "五子登科");
                    } else {
                        System.out.println("五子登科,但追饼失败55");
                    }
                } else if (count[4] == 4) {
                    if (count[1] == 2) {
                        if (gamedice.update_winner(round, 3, "状元插金花")) {
                            System.out.println("状元插金花，恭喜！！");
                            gamedice.insert_award(round, "状元插金花");
                        } else {
                            System.out.println("状元插金花，但追饼失败55");
                        }
                    } else {
                        if (gamedice.update_winner(round, 1, "状元")) {
                            System.out.println("状元，恭喜！！");
                            gamedice.insert_award(round, "状元");
                        } else {
                            System.out.println("状元，但追饼失败55");
                        }
                    }
                } else if (count[4] == 3) {
                    System.out.print("三红");
                    gamedice.judge(round, "三红");
                } else if (count[4] == 2) {
                    System.out.print("二举");
                    gamedice.judge(round, "二举");
                } else if (count[4] == 1) {
                    System.out.print("一秀");
                    gamedice.judge(round, "一秀");
                } else {
                    System.out.println("啥也没中，逊啊hiahiahia");
                }
            }
            // -----------------dice----------------
            round++;
            if (round == gamedice.player_num) {
                round = 0;
            }
            System.out.print("剩余奖品数（状元除外）：");
            System.out.println(gamedice.gameaward.award);
            if (gamedice.winner == -1) {
                System.out.println("状元还没有产生");
            }
            else {
                System.out.printf("状元已经产生，状元玩家是玩家%d，状元等级为%s\n",gamedice.winner,gamedice.winner_prize);
            }
            // scan.nextLine();//注释掉这句可以快速游戏
        }
    }
}
