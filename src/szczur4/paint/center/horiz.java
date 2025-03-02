package szczur4.paint.center;
import java.awt.*;
import java.awt.event.*;
import szczur4.paint.paint;
import szczur4.scrollLib.horizontal;
public class horiz extends horizontal{
	horiz(){
		super(paint.back,paint.medium,paint.border);
		offset=24;
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){
				paint.center.editor.xChanged=true;
				paint.center.editor.updateLocations();}
			@Override public void mouseMoved(MouseEvent ev){}
		});
		addMouseWheelListener(_->{
			paint.center.editor.xChanged=true;
			paint.center.editor.updateLocations();});
	}
	@Override public void update(){
		fix();
		m=paint.center.editor.w*paint.center.editor.m/(paint.center.fixer.getWidth());
		super.update();
		paint.center.editor.updateLocations();
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		paintBorder(g);
		g.dispose();
	}
}
