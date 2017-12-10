package Bombercraft2.Bombercraft2.gui2;

import Bombercraft2.Bombercraft2.gui2.components.Button;
import Bombercraft2.Bombercraft2.gui2.components.Panel;
import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.PositionableComponent;
import Bombercraft2.Bombercraft2.gui2.layouts.VerticalLayout;

import java.awt.Graphics2D;
import java.awt.Color;


public class GuiTester {
    private static void testBox(Graphics2D g2) {
        PositionableComponent window = new PositionableComponent();
        window.setX(5);
        window.setX(5);
        window.setHeight(390);
        window.setWidth(390);

        ColorBox colorBox = new ColorBox();
        colorBox.setBorderWidth(2);
        colorBox.render(g2, window);
    }
    private static void testButton(Graphics2D g2) {
        Button button = new Button("Tlacitko");
        button.setX(10);
        button.setY(10);
        button.setHeight(40);
        button.setWidth(100);
        button.getColorBox().setBackgroundColor(Color.cyan);

        button.render(g2);
    }

    public static void testPanel(Graphics2D g2) {
        Button button = new Button("Tlacitko");
        button.getColorBox().setBackgroundColor(Color.cyan);
        /*
        button.setX(10);
        button.setY(10);
        */
        button.setHeight(40);
        button.setWidth(100);

        Button button2 = new Button("Tlacitko2");
        button2.getColorBox().setBackgroundColor(Color.green);
        /*
        button2.setX(10);
        button2.setY(60);
        */
        button2.setHeight(40);
        button2.setWidth(100);

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

        VerticalLayout layout = new VerticalLayout();
        layout.setAlign(VerticalLayout.ALIGN_UP);
        panel.setLayout(layout);
        layout.resize();


        panel.render(g2);
    }
    public static void renderTest(Graphics2D g2) {

        testPanel(g2);
        // System.out.println("kreslim");
    }
}
