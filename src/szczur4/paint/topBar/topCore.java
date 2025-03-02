package szczur4.paint.topBar;

import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.fileManager.fileCore;
import szczur4.paint.paint;
import szczur4.paint.topBar.colorController.colorCore;
import szczur4.paint.topBar.toolbar.toolBar;
public class topCore extends JPanel implements ComponentListener{
	public final fileCore files=new fileCore();
	public final colorCore colors=new colorCore();
	public final toolBar tools=new toolBar();
	public topCore(){
		setLayout(null);
		setBackground(paint.back);
		setBorder(paint.border);
		paint.frame.add(files);
		add(colors);
		add(tools);
		addComponentListener(files);
	}
	public void update(){
		int x=paint.left.isVisible()?paint.left.getWidth():0,w=paint.frame.getContentPane().getWidth();
		setBounds(x,19,w-x,43);
		paint.selection.options.setSize(w-512-x,43);
	}
	@Override public void componentResized(ComponentEvent e){update();}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
