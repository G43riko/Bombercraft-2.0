package Bombercraft2.Bombercraft2.multiplayer.core;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.core.Texts;
import org.json.JSONException;
import org.json.JSONObject;
import utils.GLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientPlayer {
    private final Socket             socket;
    private       ObjectInputStream  objectReader;
    private       ObjectOutputStream objectWriter;
    private       String             name;
    private final int                id;

    public String getName() {
        return name;
    }

    public ClientPlayer(Socket socket, int id) {
        this.id = id;
        this.socket = socket;
        try {
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.flush();
            objectReader = new ObjectInputStream(socket.getInputStream());
            GLogger.log(GLogger.GLog.CLIENT_PLAYER_SUCCESSFULLY_INITIALIZED);
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.CANNOT_INITIAL_CLIENT_PLAYER, e);
        }
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
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
            GLogger.log(GLogger.GLog.CLIENT_PLAYER_CLEANED);
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.CANNOT_CLEAN_CLIENT_PLAYER, e);
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
            GLogger.error(GLogger.GError.CANNOT_PARSE_MESSAGE, e);
        }
        try {
            objectWriter.writeObject(object.toString());
            objectWriter.flush();
        }
        catch (IOException e) {
            GLogger.error(GLogger.GError.CANNOT_SEND_MESSAGE, e);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

}
