package org.bombercraft2.gui2.core;

import java.awt.*;

public class TextField {
    private String text;
    private String name  = "Garamond";
    private int    size  = 20;
    private Color  color = Color.BLACK;
    private int    style = Font.BOLD | Font.ITALIC;
    private Font   font  = new Font(name, style, size);

    public TextField(String text, Font font) {
        this.font = font;
    }

    public TextField(String text) {
        this.text = text;
    }

    public TextField(String fontName, int size, int style) {
        this.name = fontName;
        this.size = size;
        this.style = style;
    }

    public TextField(String text, String name, int size, int style) {
        this.text = text;
        this.name = name;
        this.size = size;
        this.style = style;
    }

    public void render(Graphics2D g2) {
        g2.setFont(font);
    }

    public Color getColor() {return color;}

    public void setColor(Color color) {this.color = color;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name; font = new Font(name, style, size);}

    public Font getFont() {return font;}

    public int getSize() {return size;}

    public void setSize(int size) {this.size = size; font = new Font(name, style, size);}

    public int getStyle() {return style;}

    public void setStyle(int style) {this.style = style; font = new Font(name, style, size);}
}
