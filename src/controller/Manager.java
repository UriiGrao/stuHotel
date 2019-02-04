package controller;

import utils.*;

public class Manager {
    public static void checkMoney(int money) {
        if (0 >= money) {
            System.out.println(Colors.PURPLE + "================================");
            System.out.println("==> MONEY : " + money + " $  <==");
            System.out.println("================================" + Colors.RESET);
            System.exit(0);
        }
    }
}
