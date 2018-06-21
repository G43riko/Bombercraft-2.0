package org.bombercraft2.gui2.layouts;

import org.bombercraft2.gui2.components.Panel;

public abstract class Layout {
    Panel target = null;
    int   gap    = 10;
    byte  align  = 0;

    public abstract void resize();

    public Panel getTarget() {return target;}

    public void setTarget(Panel panel) {this.target = panel;}

    public int getGap() {return gap;}

    public void setGap(int gap) { this.gap = gap;}

    public byte getAlign() {return align;}

    public void setAlign(byte align) {this.align = align;}
}
