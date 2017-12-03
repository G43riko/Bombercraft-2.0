package Bombercraft2.Bombercraft2.multiplayer;

import utils.Utils;

public class RemoteGameData {
    private String hostName;
    private String ip;
    private String level;
    private int    maxPlayers;
    private int    players;
    private long   ping;

    public RemoteGameData(String hostName, String ip, String level, int maxPlayers, int players) {
        this.maxPlayers = maxPlayers;
        this.hostName = hostName;
        this.players = players;
        this.level = level;
        this.ip = ip;
        reping();
    }

    public void reping() {
        ping = Utils.ping(ip);
    }

    public int getMaxPlayers() {return maxPlayers;}

    public String getHostName() {return hostName;}

    public int getPlayers() {return players;}

    public String getLevel() {return level;}

    public long getPing() {return ping;}

    public String getIp() {return ip;}


}
