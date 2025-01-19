package szczur4;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;

public class infoBar extends JPanel implements ComponentListener,Runnable{
	Thread thread=new Thread(this);
	Robot robot;
	public int x,y,w,h;
	public JLabel mouse=new JLabel("x: NaN, y: NaN"),selection=new JLabel("x: NaN, y: NaN, w: NaN, h: NaN"),editor=new JLabel("w: NaN, h: NaN");
	JLabel[]icons={new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/mouse.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/selection.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/editor.png"))))};
	Border border=new CompoundBorder(Main.border,new EmptyBorder(0,18,0,0));
	public infoBar()throws Exception{
		robot=new Robot();
		setLayout(null);
		setBackground(Main.back);
		setBorder(Main.border);
		mouse.setBackground(Main.back);
		mouse.setForeground(Main.fore);
		mouse.setBorder(border);
		mouse.setBounds(0,0,150,18);
		selection.setBackground(Main.back);
		selection.setForeground(Main.fore);
		selection.setBorder(border);
		selection.setBounds(149,0,200,18);
		editor.setBackground(Main.back);
		editor.setForeground(Main.fore);
		editor.setBorder(border);
		editor.setBounds(348,0,150,18);
		add(mouse);
		add(selection);
		add(editor);
		thread.start();
	}
	@Override public void run(){
		while(thread.isAlive()){
			if(!Main.editor.images.isEmpty())editor.setText("w: "+w+", h: "+h);
			else editor.setText("w: NaN, h: NaN");
			if(Main.editor.selected)selection.setText("x: "+Main.selection.x+", y: "+Main.selection.y+", w: "+Main.selection.w+", h: "+Main.selection.h);
			else selection.setText("x: NaN, y: NaN, w: NaN, h: NaN");
			robot.delay(50);
		}
	}
	@Override public void componentResized(ComponentEvent e){
		int w=Main.frame.getContentPane().getWidth(),h=Main.frame.getContentPane().getHeight();
		setBounds(0,h-18,w,18);
	}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
