package Bombercraft2.Bombercraft2.gui2.components;

import Bombercraft2.Bombercraft2.gui2.GuiManager;
import Bombercraft2.Bombercraft2.gui2.core.ColorBox;
import Bombercraft2.Bombercraft2.gui2.core.Drawable;
import Bombercraft2.Bombercraft2.gui2.core.DrawableComponent;
import Bombercraft2.Bombercraft2.gui2.layouts.Layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends DrawableComponent {
    protected final List<Drawable> components       = new ArrayList<>();
    private         int            verticalOffset   = 10;
    private         int            horizontalOffset = 10;
    protected       ColorBox       colorBox         = new ColorBox();
    private         Layout         layout           = null;

    public Panel() { }

    public Panel(ColorBox colorBox) {
        this.colorBox = colorBox;
    }

    public List<Drawable> getComponents() {
        return components;
    }

    @Override
    public void render(Graphics2D g2) {
        if (!visible) {
            return;
        }
        if (colorBox != null) {
            colorBox.render(g2, this);
        }
        components.forEach((component) -> component.render(g2));
    }

    @Override
    public void setManager(GuiManager manager) {
        super.setManager(manager);
        components.forEach((component) -> component.setManager(manager));
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
        layout.setTarget(this);
    }

    public void addComponent(Drawable component) {
        components.add(component);
        component.setManager(getManager());
        getLayout().resize();
    }


    public ColorBox getColorBox() {return colorBox;}

    public void setColorBox(ColorBox colorBox) {this.colorBox = colorBox;}

    public Layout getLayout() {return layout;}

    public int getVerticalOffset() {return verticalOffset;}

    public void setVerticalOffset(int verticalOffset) {this.verticalOffset = verticalOffset;}

    public int getHorizontalOffset() {return horizontalOffset;}

    public void setHorizontalOffset(int horizontalOffset) {this.horizontalOffset = horizontalOffset;}
}
