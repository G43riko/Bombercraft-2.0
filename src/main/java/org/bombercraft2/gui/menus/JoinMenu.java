package org.bombercraft2.gui.menus;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.GameStateType;
import org.bombercraft2.core.MenuAble;
import org.bombercraft2.core.Texts;
import org.bombercraft2.gui.components.GuiComponent;
import org.bombercraft2.gui.components.RemoteGamePanel;
import org.bombercraft2.multiplayer.RemoteGameData;
import org.glib2.math.vectors.GVector2f;
import org.glib2.network.InternetUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.utils.logger.GError;
import org.utils.logger.GLogger;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class JoinMenu extends Menu {
    private final java.util.List<RemoteGameData> games    = new ArrayList<>();
    private final java.util.List<RemoteGameData> gamesNew = new ArrayList<>();
    private       long                           lastPing = 0;
    private       int                            scanned  = 0;

    private final Thread t = new Thread(() -> {
        scanned = 0;
        try {
            System.out.println(InternetUtils.getLocalIP());
            // InetAddress localhost = InetAddress.getByName(Utils.getLocalIP());
            InetAddress localhost = InetAddress.getByName("localhost");
            byte[] ip = localhost.getAddress();
            for (int i = 0; i < StaticConfig.SCANNING_RANGE; i++) {
                ip[3] = (byte) i;
                InetAddress address = InetAddress.getByAddress(ip);

                if (address.isReachable(StaticConfig.SCANNING_TIMEOUT)) {
                    Socket socket = null;
                    ObjectInputStream objectReader = null;
                    ObjectOutputStream objectWriter = null;
                    try {
                        socket = new Socket(address, StaticConfig.SERVER_PORT);

                        objectReader = new ObjectInputStream(socket.getInputStream());
                        objectWriter = new ObjectOutputStream(socket.getOutputStream());
                        objectWriter.flush();

                        String result = String.valueOf(objectReader.readObject());
                        JSONObject data = new JSONObject(result);
                        String message = String.valueOf(data.get(Texts.MESSAGE));

                        JSONObject msg = new JSONObject(message);
                        addRemoteData(new RemoteGameData(msg.getString(Texts.NAME),
                                                         address.toString().replace("/", ""),
                                                         msg.getString(Texts.LEVEL),
                                                         msg.getInt(Texts.MAX_PLAYERS),
                                                         msg.getInt(Texts.PLAYERS_NUMBER)));
                    }
                    catch (Exception ee) {
                        GLogger.error(GError.CANNOT_CREATE_SOCKET, ee);
                    }
                    if (socket != null) {
                        socket.close();
                    }
                    if (objectReader != null) {
                        objectReader.close();
                    }
                    if (objectWriter != null) {
                        objectWriter.close();
                    }
                }
                else if (!address.getHostAddress().equals(address.getHostName())) {

                }
                else {}
                scanned++;
            }
        }
        catch (Exception e) {
            GLogger.error(GError.CANNOT_SCAN_LOCALHOST, e);
        }
    });

    public JoinMenu(MenuAble parent) {
        super(parent, GameStateType.JoinMenu);
        this.parent = parent;
        position.setY(100);
        init();
    }

    private void addRemoteData(RemoteGameData data) {
        gamesNew.add(data);
    }

    private void init() {
        setItem(Texts.BACK);

        t.start();
        lastPing = System.currentTimeMillis();
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
        g2.clearRect(0, 0, size.getXi(), size.getYi());
        super.render(g2);
    }

    @Override
    public void update(float delta) {
        gamesNew.forEach(a -> {
            addComponent(Texts.GAME + (games.size() + 1), new RemoteGamePanel(parent, this, a, games.size()));
            games.add(a);
        });
        gamesNew.clear();

        super.update(delta);
        if (lastPing + StaticConfig.PING_REFRESH_TIME < System.currentTimeMillis()) {
            lastPing = System.currentTimeMillis();
            games.forEach(RemoteGameData::reping);
        }
    }

    @Override
    public void doAct(GVector2f click) {

        if (components.get(Texts.BACK).isClickIn(click)) {
            parent.showMainMenu();
        }
        int counter = 1;
        GuiComponent panel = components.get(Texts.GAME + counter++);
        while (panel != null) {
            if (panel.isClickIn(click)) {
                ((RemoteGamePanel) panel).doAct(click);
            }
            panel = components.get(Texts.GAME + counter++);
        }
    }

    @Override
    public void calcPosition() {
        // TODO Auto-generated method stub

    }

    public void stopSearch() {
        t.interrupt();
    }

}
