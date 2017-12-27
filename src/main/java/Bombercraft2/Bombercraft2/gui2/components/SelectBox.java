package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.GuiManager;
import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.Bombercraft2.gui2.core.UpdateData;
import Bombercraft2.Bombercraft2.gui2.core.translation.TranslateInt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectBox extends Button {
    private final static String DEFAULT_TEXT = "";
    public class Item {
        public Button key;
        public String value;
    }

    private Item selectedItem;
    private boolean             visibleBody = false;
    private VerticalScrollPanel body        = new VerticalScrollPanel();
    private boolean             multiSelect = false;
    private String              placeHolder = "";
    private TranslateInt        translation = new TranslateInt(0, 300, 200);
    private List<Item>          items       = new ArrayList<>();

    public SelectBox() {
        super(DEFAULT_TEXT);
        setHeight(40);
        setWidth(100);
        getColorBox().setBorderWidth(1);
        getColorBox().setBorderColor(Color.black);

        hoverColorBox = new ColorBox(Color.RED);
        activeColorBox = new ColorBox(Color.MAGENTA);
        body.colorBox = new ColorBox(Color.GRAY, Color.darkGray, 1);
        body.setVerticalOffset(0);
        body.setHorizontalOffset(0);
        body.getLayout().setGap(1);
    }

    @Override
    public void update(UpdateData data) {
        super.update(data);
        if (visibleBody) {
            body.update(data);
        }
        final boolean isMouseOn = GuiConnector.isMouseOn(this, data);
        if (isMouseOn) {
            getManager().setHoverCursor();
        }
        if (data.isMouseDown) {
            if (isMouseOn) {
                if (!visibleBody) {
                    showBody();
                }
            }
            else if (!GuiConnector.isMouseOn(body.getX(), body.getY(), body.getRealWidth(), body.getHeight(), data)) {
                translation.setValue(false);
            }
        }

    }

    private void showBody() {
        visibleBody = true;
        translation.setValue(true);
        body.scrollToTop();
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);

        if (visibleBody) {
            final int height = translation.getActualValue();
            if (height <= 0) {
                visibleBody = false;
            }
            body.setHeight(height);
            getManager().addToPostRender(body);
        }
    }

    private void setSelectedItem(Item item) {
        selectedItem = item;
        getTextField().setText(item.key.getTextField().getText());
    }

    public void addItem(String key, String value) {
        Item item = new Item();
        item.value = value;
        item.key = new Button(key);
        item.key.setHeight(getHeight());
        item.key.setHoverColorBox(new ColorBox(Color.BLUE));
        items.add(item);
        item.key.setClickHandler(() -> {
            visibleBody = false;
            setSelectedItem(item);
        });

        if (body.getComponents().isEmpty()) {
            setSelectedItem(item);
        }
        body.addComponent(item.key);
    }

    public String getSelectedValue() {
        return selectedItem == null ? "" : selectedItem.value;
    }

    @Override
    public void setManager(GuiManager manager) {
        super.setManager(manager);
        body.setManager(manager);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        body.setY(getY() + getHeight());
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        body.setWidth(getWidth());
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        body.setY(getY() + getHeight());
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        body.setX(getX());
    }
}
