package org.bombercraft2.gui.components;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.core.MenuAble;
import org.bombercraft2.core.Texts;
import org.bombercraft2.gui.ClickAble;
import org.bombercraft2.gui.menus.JoinMenu;
import org.bombercraft2.multiplayer.RemoteGameData;
import org.jetbrains.annotations.NotNull;
import utils.math.BVector2f;

import java.awt.*;

public class RemoteGamePanel extends GuiComponent implements ClickAble {
    private final RemoteGameData data;
    private final int            localOffset = 8;
    private final Button         joinButton;
    private final MenuAble       coreGame;

    public RemoteGamePanel(MenuAble coreGame, JoinMenu parent, RemoteGameData data, int order) {
        super(parent);
        this.data = data;
        this.coreGame = coreGame;
        init();

        topCousePrevButtons = order * (size.getYi() + 30) + 80;
        calcPosAndSize();


        joinButton = new Button(this, getLabelOf(Texts.CONNECT));
        joinButton.size.setX(200);
        joinButton.position.set(position.getAdd(size).getSub(localOffset).getSub(joinButton.size));
    }

    private String getLabelOf(String key) {
        return coreGame.getGuiManager().getLabelOf(key);
    }

    @Override
    protected void init() {
        disabledColor = Color.LIGHT_GRAY;
        backgroundColor = new Color(0, 255, 0, 100);
        hoverColor = Color.DARK_GRAY;
        borderColor = Color.black;
        borderWidth = 5;
        textColor = Color.BLACK;
        textOffset = new BVector2f();
        offset = new BVector2f(40, 5);
        round = StaticConfig.DEFAULT_ROUND;
        font = "Monospaced";
        size = new BVector2f(600, 150);
        textSize = 32;
    }

    public void rePing() {
        data.reping();
    }

    @Override
    public void render(@NotNull Graphics2D g2) {
//		if(disable){
//			g2.setColor(disabledColor);
//		}
//		else if(hover){
//			g2.setColor(hoverColor);
//		}
//		else{
        g2.setColor(backgroundColor);
//		}

        g2.fillRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);

        if (borderWidth > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth));
            g2.drawRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);
        }

        g2.setColor(textColor);
        g2.setFont(new Font(font, Font.BOLD | Font.ITALIC, textSize));


        g2.drawString(getLabelOf(Texts.IP) + ": " + data.getIp(),
                      position.getX() + localOffset,
                      position.getY() + (localOffset + textSize));

        g2.drawString(getLabelOf(Texts.HOST) + ": " + data.getHostName(),
                      position.getX() + localOffset,
                      position.getY() + (localOffset + textSize) * 2);

        g2.drawString(getLabelOf(Texts.LEVEL) + ": " + data.getLevel(),
                      position.getX() + localOffset,
                      position.getY() + (localOffset + textSize) * 3);


        String capacity = getLabelOf(Texts.PLAYERS_NUMBER);
        capacity += ": " + data.getPlayers() + "/" + data.getMaxPlayers();

        g2.drawString(capacity,
                      position.getX() + size.getX() - g2.getFontMetrics().stringWidth(capacity) - localOffset,
                      position.getY() + (localOffset + textSize));

        String ping = getLabelOf(Texts.PING) + ": " + data.getPing();
        g2.drawString(ping,
                      position.getX() + size.getX() - g2.getFontMetrics().stringWidth(ping) - localOffset,
                      position.getY() + (localOffset + textSize) * 2);

        joinButton.render(g2);
    }

    @Override
    public void update(float delta) {
        joinButton.update(delta);
    }


    @Override
    public void doAct(BVector2f click) {
        if (joinButton.isClickIn(click)) {
            ((JoinMenu) getParent()).stopSearch();
            coreGame.connectToGame(data.getIp());
        }
    }
}
