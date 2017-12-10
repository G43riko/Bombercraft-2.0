package Bombercraft2.Bombercraft2.gui2;

import Bombercraft2.Bombercraft2.gui2.components.Button;
import Bombercraft2.Bombercraft2.gui2.components.Checkbox;
import Bombercraft2.Bombercraft2.gui2.components.Panel;
import Bombercraft2.Bombercraft2.gui2.components.VerticalScrollPanel;
import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnectAble;
import Bombercraft2.Bombercraft2.gui2.core.GuiConnector;
import Bombercraft2.Bombercraft2.gui2.core.PositionableComponent;
import Bombercraft2.Bombercraft2.gui2.layouts.DividedVerticalLayout;
import Bombercraft2.Bombercraft2.gui2.layouts.Layout;
import Bombercraft2.Bombercraft2.gui2.layouts.VerticalLayout;
import Bombercraft2.engine.Input;

import java.awt.Graphics2D;
import java.awt.Color;


public class GuiTester {
    public static GuiManager manager = new GuiManager();
    private static GuiTester tester = new GuiTester();
    private Checkbox checkbox = new Checkbox("Checkbox");
    private GuiTester() {
        GuiConnector.setConnector(new GuiConnectAble() {
            @Override
            public int getMouseX() {
                return Input.getMousePosition().getXi();
            }

            @Override
            public int getMouseY() {
                return Input.getMousePosition().getYi();
            }

            @Override
            public boolean isButtonDown() {
                return Input.isButtonDown(Input.BUTTON_LEFT);
            }

        });
        // testPanel();
        testScrollPanel();
        // testLayout();
    }
    private void testBox(Graphics2D g2) {
        PositionableComponent window = new PositionableComponent();
        window.setX(5);
        window.setX(5);
        window.setHeight(390);
        window.setWidth(390);

        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(2);
        colorBox.render(g2, window);
    }
    private void testButton(Graphics2D g2) {
        Button button = new Button("Tlacitko");
        button.setX(10);
        button.setY(10);
        button.setHeight(40);
        button.setWidth(100);
        button.getColorBox().setBackgroundColor(Color.cyan);

        button.render(g2);
    }
    private void testScrollPanel() {
        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(1);

        VerticalScrollPanel panel = new VerticalScrollPanel();
        panel.setColorBox(colorBox);
        panel.setX(5);
        panel.setY(5);
        panel.setWidth(290);
        panel.setHeight(490);

        final int items = 15;

        for (int i=1 ; i<=items ;  i++) {
            Button button = new Checkbox("Tlacitko " + i);
            button.setHeight(40);
            button.setWidth(100);
            button.getColorBox().setBackgroundColor(new Color(255 / items * i, 255, 255));
            button.setHoverColorBox(new ColorBox(new Color(255,
                                                           255 - 255 / items * i,
                                                           255), Color.blue, 5));
            panel.addComponent(button);
        }
        panel.getLayout().resize();
        manager.add(panel);

    }
    private void testLayout() {
        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(2);
        Panel panel = new Panel();
        panel.setColorBox(colorBox);
        panel.setX(5);
        panel.setY(5);
        panel.setWidth(290);
        panel.setHeight(490);

        Panel panel2 = new Panel(new ColorBox(Color.RED));
        Panel panel3 = new Panel(new ColorBox(Color.BLUE));
        Panel panel4 = new Panel(new ColorBox(Color.GRAY));

        Layout layout = new DividedVerticalLayout();
        layout.setGap(10);
        panel.setLayout(layout);
        panel.addComponent(panel2);
        panel.addComponent(panel3);
        panel.addComponent(panel4);
        layout.resize();
        manager.add(panel);
    }
    private void testPanel() {
        Button button = new Button("Tlacitko");
        button.getColorBox().setBackgroundColor(Color.cyan);
        button.setActiveColorBox(new ColorBox(Color.RED, Color.blue, 5));
        /*
        button.setX(10);
        button.setY(10);
        */
        button.setHeight(40);
        button.setWidth(100);

        Button button2 = new Button("Tlacitko2");
        button2.getColorBox().setBackgroundColor(Color.green);
        button2.setHoverColorBox(new ColorBox(Color.MAGENTA, Color.blue, 2));
        /*
        button2.setX(10);
        button2.setY(60);
        */
        button2.setHeight(40);
        button2.setWidth(100);

        checkbox.setWidth(100);
        checkbox.setHeight(40);

        checkbox.setActiveColorBox(new ColorBox(Color.GRAY, Color.blue, 5));
        checkbox.getColorBox().setBackgroundColor(Color.YELLOW);

        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(2);

        Panel panel = new Panel();
        panel.setColorBox(colorBox);
        panel.setX(5);
        panel.setY(5);
        panel.setWidth(290);
        panel.setHeight(490);

        panel.addComponent(button);
        panel.addComponent(button2);
        panel.addComponent(checkbox);

        VerticalLayout layout = new VerticalLayout();
        layout.setAlign(VerticalLayout.ALIGN_UP);
        panel.setLayout(layout);
        layout.resize();

        manager.add(panel);
    }

}
