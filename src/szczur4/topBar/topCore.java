package szczur4.topBar;
import java.awt.event.*;
import javax.swing.*;
import szczur4.K;
import szczur4.bars.horizontal;
import szczur4.bars.vertical;
import szczur4.topBar.colorController.colorCore;
import szczur4.topBar.fileManager.fileCore;
public class topCore extends JPanel implements ComponentListener{
	public final fileCore files=new fileCore();
	public final colorCore colors=new colorCore();
	public final toolBar tools=new toolBar();
	public final horizontal horiz=new horizontal();
	public final vertical vert=new vertical();
	public topCore(){
		setLayout(null);
		setBackground(K.back);
		setBorder(K.border);
		horiz.setLocation(0,61);
		add(files);
		add(colors);
		add(tools);
		add(horiz);
		addComponentListener(files);
		K.frame.add(vert);
	}
	@Override public void componentResized(ComponentEvent e){
		int w=K.frame.getContentPane().getWidth(),h=K.frame.getContentPane().getHeight()-102;
		setSize(w,73);
		K.selection.options.setSize(w-476,43);
		horiz.setSize(w,12);
		horiz.right.setLocation(w-16,0);
		horiz.update(0);
		vert.setBounds(0,72,12,h);
		vert.down.setLocation(0,h-16);
		vert.update(1);
	}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
