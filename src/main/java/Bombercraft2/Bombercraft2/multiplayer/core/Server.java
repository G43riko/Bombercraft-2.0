package Bombercraft2.Bombercraft2.multiplayer.core;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import utils.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class Server implements Writable {
    public static final String BASIC_INFO       = "LEVEL INFO";
    public static final String GAME_INFO        = "GAME INFO";
    public static final String PLAYER_CHANGE    = "PLAYER MOVE";
    public static final String PLAYER_DATA      = "PLAYER NAME";
    public static final String PLAYER_IS_READY  = "PLAYER IS READY";
    public static final String PUT_HELPER       = "PUT HELPER";
    public static final String HIT_BLOCK        = "BLOCK CHANGE";
    public static final String PUT_BOMB         = "PUT BOMB";
    public static final String CLOSE_CONNECTION = "CLOSE_CONNECTION";
    public static final String BUILD_BLOCK      = "BUILD_BLOCK";
    public static final String REMOVE_BLOCK     = "REMOVE_BLOCK";
    public static final String BOMB_EXPLODE     = "BOMB_EXPLODE";
    public static final String PUT_BULLET       = "PUB_BULLET";

    private ServerSocket serverSocket;
    private boolean readerIsRunning   = true;
    private boolean accepterIsRunning = true;
    private Thread acceptThread;
    private Thread listenThread;
    private HashMap<String, ClientPlayer> clients = new HashMap<>();


    protected void removeClient(String name) {
        clients.remove(name);
    }

    public Server() {
        try {
            serverSocket = new ServerSocket(Config.SERVER_PORT);
//			GLog.write(GLog.SITE, "S: Server sa vytvoril");
        }
        catch (IOException e) {
            e.printStackTrace();
//			GLog.write(GLog.SITE, "S: Server sa nepodarilo vytvoriť");
        }

        listen();
        accept();
    }

    protected abstract void processMessage(String txt, ClientPlayer client);

    private void listen() {
        listenThread = new Thread(() -> {
            while (readerIsRunning) {
                new HashMap<>(clients).entrySet()
                                      .stream()
                                      .map(Map.Entry::getValue)
                                      .forEach(this::read);

                Utils.sleep(1);
            }
        });
        listenThread.start();
    }

    private void read(ClientPlayer a) {
        Object o = a.read();
        if (o == null) { return; }
        Bombercraft.receiveMessages++;
        if (o instanceof String) { processMessage(String.valueOf(o), a); }
//		GLog.write(GLog.SITE_MESSAGES, "S: server prijal správu " + o);
    }

    /**
     * Funkcia odosle zakladne udaje o hre
     * <p>
     * --nazov levelu
     * -pocet hracov
     * -public/private
     *
     * @return String
     */
    protected abstract String getBasicInfo();

    private void accept() {
        acceptThread = new Thread(() -> {
            while (accepterIsRunning) {
                try {
                    Socket client = serverSocket.accept();

                    ClientPlayer c = new ClientPlayer(client, client.getInetAddress().toString().hashCode());
                    if (clients.containsKey(String.valueOf(c.getId()))) {
                        clients.get(String.valueOf(c.getId())).cleanUp();
                    }
                    clients.put(c.getId() + "", c);
                    c.write(getBasicInfo(), BASIC_INFO);

//						GLog.write(GLog.SITE, "S: Client sa pripojil");

                }
                catch (IOException e) {
//						e.printStackTrace();
//						GLog.write(GLog.SITE, "S: Server bol ukončený");
                }
            }
        });
        acceptThread.start();
    }

    protected void renameClient(String oldName, String newName) {
        ClientPlayer temp = clients.get(oldName);
        temp.setName(newName);
        clients.remove(oldName);
        clients.put(newName, temp);
    }

    public void write(String o, String type) {
        new HashMap<>(clients).forEach((key, value) -> value.write(o, type));
    }


    protected void writeExcept(String o, String type, ClientPlayer client) {
        new HashMap<>(clients).entrySet()
                              .stream()
                              .filter(a -> a.getValue().getId() != client.getId())
                              .forEach(a -> a.getValue().write(o, type));
    }

    public void cleanUp() {
        readerIsRunning = false;
        accepterIsRunning = false;

        clients.forEach((key, value) -> value.cleanUp());

        clients.clear();

        try {
            if (serverSocket != null) { serverSocket.close(); }
        }
        catch (IOException e) {
            System.out.println("nepodarilo sa zavrie server");
        }
        serverSocket = null;
    }

    public int getNumberOfClients() {
        return clients.size();
    }
}