package Bombercraft2.Bombercraft2.gui2.core;

public class PositionableComponent extends Component {
    private int x = 0;
    private int y = 0;

    public PositionableComponent() { }

    public PositionableComponent(int width, int height) {
        super(width, height);
    }

    public PositionableComponent(int x, int y, int width, int height) {
        super(width, height);
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}

    public int getY() {return y;}

    public void setX(int x) {this.x = x;}

    public void setY(int y) { this.y = y;}
}
