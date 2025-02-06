package szczur4.bottomBar;
import java.awt.event.*;
import javax.swing.*;
import szczur4.K;
import szczur4.bars.horizontal;
import szczur4.bars.vertical;
public class bottomCore extends JPanel implements ComponentListener{
	public final horizontal horiz=new horizontal();
	public final vertical vert=new vertical();
	public final info info;
	public bottomCore()throws Exception{
		info=new info();
		setLayout(null);
		setBorder(K.border);
		add(horiz);
		add(info);
		K.frame.add(vert);
	}
	@Override public void componentResized(ComponentEvent e){
		int w=K.frame.getContentPane().getWidth(),h=K.frame.getContentPane().getHeight()-31;
		setBounds(0,h,w,31);
		info.setSize(w,20);
		info.labels[5].setSize(w-685,20);
		horiz.setSize(w,12);
		horiz.right.setLocation(w-16,0);
		horiz.update(1);
		vert.setBounds(w-12,72,12,h-71);
		vert.down.setLocation(0,h-87);
		vert.update(1);
	}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
