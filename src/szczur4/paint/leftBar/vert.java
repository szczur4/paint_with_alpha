package szczur4.paint.leftBar;
import java.awt.*;
import java.awt.event.*;
import szczur4.paint.paint;
import szczur4.scrollLib.vertical;
public class vert extends vertical{
	public vert(){
		super(paint.back,paint.medium,paint.border);
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){paint.left.explorer.updateUI();}
			@Override public void mouseMoved(MouseEvent ev){}
		});
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		super.mouseWheelMoved(ev);
		paint.left.explorer.updateUI();
	}
	@Override public void update(){
		fix();
		m=paint.left.explorer.h*M/(paint.left.explorer.getHeight());
		super.update();
		repaint();
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		paintBorder(g);
	}
}
