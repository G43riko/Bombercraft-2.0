package Bombercraft2.Bombercraft2.multiplayer.core;

import Bombercraft2.Bombercraft2.Bombercraft;
import Bombercraft2.Bombercraft2.core.Texts;
import org.json.JSONException;
import org.json.JSONObject;

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
//			GLog.write(GLog.SITE, "S: Profile hráèa " + name + " bol vytvorený");
        }
        catch (IOException e) {
            e.printStackTrace();
//			GLog.write(GLog.SITE, "S: Nepodarilo sa vytvori profil hráèovy " + name); 
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
//			GLog.write(GLog.SITE, "S: Úspešne sa zmazal hráè: " + name);
        }
        catch (IOException e) {
            e.printStackTrace();
//			GLog.write(GLog.SITE, "S: Nepodarilo sa zmaza hráèa menom: " + name);
        }
    }

    public void write(Serializable o, String type) {
        Bombercraft.sendMessages++;
        JSONObject object = new JSONObject();
        try {
            object.put(Texts.TYPE, type);
            object.put(Texts.MESSAGE, o.toString());
        }
        catch (JSONException e1) {
            e1.printStackTrace();
        }
        try {
            objectWriter.writeObject(object.toString());
            objectWriter.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
//			GLog.write(GLog.SITE_MESSAGES, "S: Nepodailo sa odosla správu " + o + " hráèovy: " + name);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

}
