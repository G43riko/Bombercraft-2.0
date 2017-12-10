package Bombercraft2.Bombercraft2;

import utils.Utils;

public class App {

    public static void main(String[] args) {
        Bombercraft game = new Bombercraft();
        game.run();
    }

    private static void testMultiPlayer() {
        Thread t1 = new Thread(() -> {
            Bombercraft server = new Bombercraft();
            server.initDefaultProfile();
            server.startNewGame();
            server.run();
        });
        Thread t2 = new Thread(() -> {
            Bombercraft client = new Bombercraft();
            client.initDefaultProfile();
            client.showJoinMenu();
            client.run();
        });
        t1.start();
        Utils.sleep(1000);
        t2.start();
    }
}
