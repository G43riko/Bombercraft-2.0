package Bombercraft2.Bombercraft2.gui.components;

import Bombercraft2.Bombercraft2.Config;
import Bombercraft2.Bombercraft2.core.Visible;
import utils.math.GVector2f;

import java.awt.*;

public class MiniSwitch extends GuiComponent {
    private final GVector2f indicatorSize = new GVector2f(24, 24);

    public MiniSwitch(Visible parent, String text) {
        this(parent, text, true, SIDEBAR_DEFAULT_BUTTON_HEIGHT);
    }

    public MiniSwitch(Visible parent, String text, boolean value) {
        this(parent, text, value, SIDEBAR_DEFAULT_BUTTON_HEIGHT);
    }

    private MiniSwitch(Visible parent, String text, boolean value, int height) {
        super(parent);
        this.size = new GVector2f(0, height);
        this.text = text;
        this.value = value;
        init();

        if (!buttons.containsKey(parent)) { buttons.put(parent, 0); }

        topCousePrevButtons = buttons.get(parent);
        buttons.put(parent, topCousePrevButtons + height);

        calcPosition();
    }

    protected void init() {
        offset = new GVector2f(2, 2);
        textSize = 20;
        textOffset = new GVector2f(4, 0);
        round = 10;
        borderWidth = 1;

        hoverColor = Color.GRAY;
        textColor = Color.WHITE;
        backgroundColor = Color.DARK_GRAY;
        borderColor = Color.LIGHT_GRAY;
    }

    @Override
    public void calcPosition() {
        position = getParent().getPosition().add(offset.add(new GVector2f(0, topCousePrevButtons)));
        size = new GVector2f(getParent().getSize().getXi() - 2 * offset.getX(), size.getY() - offset.getY());
    }

//	@Override
//	protected void clickIn() {
//		value = !value;
//		System.out.println(value);
//	}

    @Override
    public void render(Graphics2D g2) {
        if (hover) { g2.setColor(hoverColor); }
        else { g2.setColor(backgroundColor); }

        g2.fillRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);


        int indicatorOffset = (int) (size.getY() - indicatorSize.getY()) / 2;
        GVector2f indicatorPosition = new GVector2f(position.getX() + size.getX() - indicatorOffset - indicatorSize.getX(),
                                                    position.getY() + indicatorOffset);
        g2.setPaint(value ? Color.GREEN : Color.red);
        g2.fillRoundRect(indicatorPosition.getXi(),
                         indicatorPosition.getYi(),
                         indicatorSize.getXi(),
                         indicatorSize.getYi(),
                         round,
                         round);


        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));
        g2.drawRoundRect(position.getXi(), position.getYi(), size.getXi(), size.getYi(), round, round);

        g2.setColor(textColor);
        g2.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD | Font.ITALIC, textSize));
        g2.drawString(text, position.getX() + textOffset.getX(), position.getY() + textSize + textOffset.getY());
    }


}
