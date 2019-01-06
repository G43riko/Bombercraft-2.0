package org.bombercraft2.multiplayer.core;

import org.bombercraft2.Bombercraft;
import org.bombercraft2.core.Texts;
import org.json.JSONException;
import org.json.JSONObject;
import org.utils.logger.GError;
import org.utils.logger.GLog;
import org.utils.logger.GLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientPlayer {
    private final Socket             socket;
    private final int                id;
    private       ObjectInputStream  objectReader;
    private       ObjectOutputStream objectWriter;
    private       String             name;

    public ClientPlayer(Socket socket, int id) {
        this.id = id;
        this.socket = socket;
        try {
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.flush();
            objectReader = new ObjectInputStream(socket.getInputStream());
            GLogger.log(GLog.CLIENT_PLAYER_SUCCESSFULLY_INITIALIZED);
        }
        catch (IOException e) {
            GLogger.error(GError.CANNOT_INITIAL_CLIENT_PLAYER, e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Object read() {
        try {
            return objectReader.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
//			GLog.write(GLog.SITE, "S: Nepodarila sa preèíta správa pri hráèovy menom: " + name);
        }
        return null;
    }

    public void cleanUp() {
        try {
            socket.close();

            objectReader.close();
            objectWriter.close();
            GLogger.log(GLog.CLIENT_PLAYER_CLEANED);
        }
        catch (IOException e) {
            GLogger.error(GError.CANNOT_CLEAN_CLIENT_PLAYER, e);
        }
    }

    public void write(Serializable o, String type) {
        Bombercraft.sendMessages++;
        JSONObject object = new JSONObject();
        try {
            object.put(Texts.TYPE, type);
            object.put(Texts.MESSAGE, o.toString());
        }
        catch (JSONException e) {
            GLogger.error(GError.CANNOT_PARSE_MESSAGE, e);
        }
        try {
            objectWriter.writeObject(object.toString());
            objectWriter.flush();
        }
        catch (IOException e) {
            GLogger.error(GError.CANNOT_SEND_MESSAGE, e);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

}
