package org.bombercraft2.multiplayer.core;

import org.bombercraft2.Bombercraft;
import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.Texts;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;

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
            socket = new Socket(ip, StaticConfig.SERVER_PORT);
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.flush();
            objectReader = new ObjectInputStream(socket.getInputStream());
            GLogger.log(GLogger.GLog.SUCCESSFULLY_CONNECTED_TO_THE_SERVER);
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.CLIENT_CANNOT_CONNECT_TO_SERVER, e);
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
        catch (JSONException e) {
            GLogger.error(GLogger.GError.CANNOT_PARSE_MESSAGE, e);
        }
        try {
            objectWriter.writeObject(object.toString());
            Bombercraft.sendMessages++;
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.CANNOT_SEND_MESSAGE, e);
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
            GLogger.error(GLogger.GError.CANNOT_CLEAN_CLIENT, e);
        }
    }
}