package szczur4.paint.leftBar;

import java.awt.event.*;
import szczur4.paint.paint;
import szczur4.scrollLib.horizontal;
public class horiz extends horizontal{
	public horiz(){
		super(paint.back,paint.medium,paint.border);
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){paint.left.explorer.updateUI();}
			@Override public void mouseMoved(MouseEvent ev){}
		});
		addMouseWheelListener(_->paint.left.explorer.updateUI());
	}
	@Override public void update(){
		fix();
		m=(float)paint.left.explorer.w/paint.left.explorer.getWidth();
		super.update();
		repaint();
	}
}
