package Bombercraft2.Bombercraft2.multiplayer.core;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Texts;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class Client implements Writable {
    private ObjectInputStream  objectReader;
    private ObjectOutputStream objectWriter;

    private Socket socket;

    private boolean readerIsRunning = true;

    protected Client(String ip) {

        try {
            socket = new Socket(ip, Config.SERVER_PORT);
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.flush();
            objectReader = new ObjectInputStream(socket.getInputStream());
//			GLog.write(GLog.SITE, "C: Client sa pripojil");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        listen();
    }

    public abstract void sendPlayerInfo();

    protected abstract void processMessage(String txt);

    private void listen() {
        Thread listenThread = new Thread(() -> {
            while (readerIsRunning) {
                Object o = read();
                if (o != null) {
                    Bombercraft.receiveMessages++;
//						GLog.write(GLog.SITE_MESSAGES, "C: Client prijal správu: " + o);
                    processMessage(String.valueOf(o));
                }

            }
        });
        listenThread.start();
    }

    private Object read() {
        try {
            return objectReader.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }

    /**
     * Tato funkcia je zavolana ak sa bezdovodne prerusi spojenie zo serverom
     */
    protected abstract void onConnectionBroken();

    public void write(String o, String type) {
        if (!socket.isConnected()) {
            onConnectionBroken();
        }
        JSONObject object = new JSONObject();
        try {
            object.put(Texts.TYPE, type);
            object.put(Texts.MESSAGE, o);
        }
        catch (JSONException e1) {
            e1.printStackTrace();
        }
        try {
            objectWriter.writeObject(object.toString());
            Bombercraft.sendMessages++;
        }
        catch (IOException e) {
            onConnectionBroken();
        }
    }


    public void cleanUp() {
        readerIsRunning = false;

        try {
            if (socket != null) {
                socket.close();
            }

            objectReader.close();
            objectWriter.close();
        }
        catch (IOException e) {
//			e.printStackTrace();
        }
    }
}