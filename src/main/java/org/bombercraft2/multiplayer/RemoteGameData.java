package org.bombercraft2.multiplayer;

import org.glib2.network.InternetUtils;
import org.jetbrains.annotations.NotNull;

public class RemoteGameData {
    @NotNull
    private final String hostName;
    @NotNull
    private final String ip;
    @NotNull
    private final String level;
    private final int    maxPlayers;
    private final int    players;
    private       long   ping;

    public RemoteGameData(@NotNull String hostName,
                          @NotNull String ip,
                          @NotNull String level,
                          int maxPlayers,
                          int players
                         ) {
        this.maxPlayers = maxPlayers;
        this.hostName = hostName;
        this.players = players;
        this.level = level;
        this.ip = ip;
        reping();
    }

    public void reping() {
        ping = InternetUtils.ping(ip);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPlayers() {
        return players;
    }

    public String getLevel() {
        return level;
    }

    public long getPing() {
        return ping;
    }

    public String getIp() {
        return ip;
    }


}
