package org.bombercraft2.gui2;

import org.bombercraft2.StaticConfig;
import org.bombercraft2.gui2.styles.ButtonStyles;
import org.engine.Input;
import org.gui.components.Button;
import org.gui.components.Checkbox;
import org.gui.components.Panel;
import org.gui.components.*;
import org.gui.core.GuiConnectAble;
import org.gui.core.GuiConnector;
import org.gui.core.components.PositionableComponent;
import org.gui.layouts.BorderLayout;
import org.gui.layouts.*;
import org.gui.utils.ColorBox;

import java.awt.*;


public class GuiTester {
    public static final  GuiManagerImpl manager  = new GuiManagerImpl();
    private static final GuiTester      tester   = new GuiTester();
    private final        Checkbox       checkbox = new Checkbox("Checkbox");

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

            @Override
            public int getScroll() { return Input.getScroll(); }
        });

        // testPanel();
        // testScrollPanel();
        // testLayout();

    }

    public static void init() {
        // manager.getMainPanel().setLayout(new BorderLayout(BorderLayout.NORTH));
        manager.getMainPanel().addComponent(tester.mainMenu());
        // manager.getMainPanel().addComponent(tester.getTopPanel());
        // manager.getMainPanel().addComponent(tester.getBottomPanel());
    }

    private Panel mainMenu() {
        Panel result = new Panel();
        result.setLayout(new VerticalLayout());
        result.setHorizontalOffset(150);
        ButtonStyles style = new ButtonStyles();
        style.backgroundColor = Color.GREEN;
        style.borderWidth = 5;
        style.fontColor = Color.BLACK;
        style.fontSize = 36;
        style.round = StaticConfig.DEFAULT_ROUND;
        style.fontName = "Monospaced";


        Button button = new Button("Nová hra");
        button.getStateHolder().getDefault().setBackgroundColor(Color.cyan);
        button.getStateHolder().setActive(new ColorBox(Color.RED, Color.blue, 5));
        button.setHeight(40);
        button.setWidth(100);
        style.setTo(button);
        result.addComponent(button);
        return result;
    }

    private Panel getTopPanel() {
        Panel panel = new Panel();
        panel.setHeight(manager.getMainPanel().getHeight() - 200);
        panel.getColorBox().setBackgroundColor(Color.BLUE);
        panel.setLayout(new BorderLayout(BorderLayout.WEST));
        panel.addComponent(testScrollPanel());
        return panel;
    }

    private Panel getBottomPanel() {
        Panel panel = new Panel();
        panel.getColorBox().setBackgroundColor(Color.GREEN);
        panel.setHeight(170);
        VerticalFlowLayout layout = new VerticalFlowLayout();
        panel.setLayout(layout);
        layout.setAutoFixGaps(true);
        layout.setChangeHeight(VerticalFlowLayout.AVERAGE);

        for (int i = 1; i <= 0; i++) {
            Button button = new Button("text-" + i);
            button.setHeight((int) (Math.random() * 20) + 20);
            button.setWidth((int) (Math.random() * 60) + 30);
            panel.addComponent(button);
        }


        return panel;
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
        button.getStateHolder().getDefault().setBackgroundColor(Color.cyan);

        button.render(g2);
    }

    private Panel testScrollPanel() {
        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(1);

        VerticalScrollPanel panel = new VerticalScrollPanel();
        panel.setColorBox(colorBox);
        panel.setX(5);
        panel.setY(5);
        panel.setWidth(290);
        panel.setHeight(490);

        final int items = 15;


        Button button = new Button("Tlacitko " + 0);
        button.setHeight(40);
        button.setWidth(100);
        button.getStateHolder().getDefault().setBackgroundColor(new Color(0, 255, 255));
        button.getStateHolder().setHover(new ColorBox(new Color(255,
                                                                255,
                                                                255), Color.blue, 5));
        panel.addComponent(button);


        SelectBox selectBox = new SelectBox();
        panel.addComponent(selectBox);
        selectBox.addItem("name", "Meno");
        selectBox.addItem("surName", "Priezvisko");
        selectBox.addItem("age", "Vek");

        for (int i = 1; i <= items; i++) {
            button = new Checkbox("Tlacitko " + i);
            button.setHeight(40);
            button.setWidth(100);
            button.getStateHolder().getDefault().setBackgroundColor(new Color(255 / items * i, 255, 255));
            button.getStateHolder().setHover(new ColorBox(new Color(255,
                                                           255 - 255 / items * i,
                                                           255), Color.blue, 5));
            panel.addComponent(button);
            selectBox.addItem("button" + i, "Tlačítko " + i);
        }
        panel.getLayout().resize();
        //manager.getAdd(panel);
        return panel;
    }

    private Panel testLayout() {
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
        return panel;
    }

    private Panel testPanel() {
        Button button = new Button("Tlacitko");
        button.getStateHolder().getDefault().setBackgroundColor(Color.cyan);
        button.getStateHolder().setHover(new ColorBox(Color.RED, Color.blue, 5));
        /*
        button.setX(10);
        button.setY(10);
        */
        button.setHeight(40);
        button.setWidth(100);

        Button button2 = new Button("Tlacitko2");
        button2.getStateHolder().getDefault().setBackgroundColor(Color.green);
        button2.getStateHolder().setHover(new ColorBox(Color.MAGENTA, Color.blue, 2));
        /*
        button2.setX(10);
        button2.setY(60);
        */
        button2.setHeight(40);
        button2.setWidth(100);

        checkbox.setWidth(100);
        checkbox.setHeight(40);

        button2.getStateHolder().getDefault().setBackgroundColor(Color.YELLOW);
        checkbox.getStateHolder().setActive(new ColorBox(Color.GRAY, Color.blue, 5));

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

        return panel;
    }
}
