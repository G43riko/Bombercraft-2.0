package Bombercraft2.Bombercraft2.gui2.layouts;

import Bombercraft2.Bombercraft2.gui2.components.Panel;

public abstract class Layout {
    protected Panel target = null;
    protected int   gap    = 10;
    protected byte  align  = 0;

    public abstract void resize();

    public Panel getTarget() {return target;}

    public int getGap() {return gap;}

    public byte getAlign() {return align;}

    public void setGap(int gap) { this.gap = gap;}

    public void setTarget(Panel panel) {this.target = panel;}

    public void setAlign(byte align) {this.align = align;}
}
