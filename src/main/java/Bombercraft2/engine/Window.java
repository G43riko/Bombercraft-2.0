package Bombercraft2.engine;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Window extends JFrame implements ComponentListener, WindowListener{
	private static final long serialVersionUID = 1L;
	private CoreEngine parent;

	public Window(CoreEngine parent, String title, int width, int height){
		this.parent = parent;
		setTitle(title);
		setResizable(true);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setFullscreen(true);
		setVisible(true);
		
		addComponentListener(this);
		addWindowListener(this);
	}
	
	public void setFullscreen(boolean value){
		setVisible(false);
		setExtendedState(value ? MAXIMIZED_BOTH : NORMAL);
		
		setUndecorated(value);
		setVisible(true);
	}
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void windowDeactivated(WindowEvent e) {parent.onUnfocus();}
	public void windowDeiconified(WindowEvent e) {parent.onFocus();}
	public void windowActivated(WindowEvent e) {parent.onFocus();}
	public void windowIconified(WindowEvent e) {parent.onUnfocus();}
	public void windowClosing(WindowEvent e) {parent.onExit();}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void componentResized(ComponentEvent e) {parent.onResize();}
}
