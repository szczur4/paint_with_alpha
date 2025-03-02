package szczur4.paint.center;
import java.awt.*;
import java.awt.event.*;
import szczur4.paint.paint;
import szczur4.scrollLib.vertical;
public class vert extends vertical{
	vert(){
		super(paint.back,paint.medium,paint.border);
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){
				paint.center.editor.yChanged=true;
				paint.center.editor.updateLocations();}
			@Override public void mouseMoved(MouseEvent ev){}
		});
		addMouseWheelListener(_->{
			paint.center.editor.yChanged=true;
			paint.center.editor.updateLocations();});
	}
	@Override public void update(){
		fix();
		m=paint.center.editor.h*paint.center.editor.m/(paint.center.fixer.getHeight());
		super.update();
		paint.center.editor.updateLocations();
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		paintBorder(g);
		g.dispose();
	}
}
