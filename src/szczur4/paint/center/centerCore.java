package szczur4.paint.center;
import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.paint;
public class centerCore extends JLayeredPane implements ComponentListener{
	horiz horiz=new horiz();
	vert vert=new vert();
	public fixer fixer=new fixer();
	public editor editor;
	public centerCore()throws Exception{
		editor=new editor();
		setLayout(null);
		setBackground(paint.back);
		add(horiz,JLayeredPane.DEFAULT_LAYER);
		add(vert,JLayeredPane.PALETTE_LAYER);
		vert.setLocation(0,11);
		fixer.add(editor);
		add(fixer,JLayeredPane.MODAL_LAYER);
	}
	public void update(){
		int x=paint.left.isVisible()?paint.left.getWidth():0,w=paint.frame.getWidth()-x-16,h=paint.frame.getContentPane().getHeight()-80;
		setBounds(x,61,w,h);
		fixer.setSize(w-22,h-22);
		horiz.setSize(w,h);
		vert.setSize(w,h-22);
		horiz.update();
		vert.update();
	}
	@Override public void componentResized(ComponentEvent e){update();}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
