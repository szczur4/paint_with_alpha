package szczur4;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;

public class infoBar extends JPanel implements ComponentListener,Runnable{
	final Thread thread=new Thread(this);
	Robot robot;
	public int x,y,w,h;
	final String[]tools={"pencil","fill","eraser","color picker","line","empty rectangle","full rectangle","empty elipse","full elipse"};
	public final JLabel mouse=new JLabel("x: NaN, y: NaN");
	public final JLabel selection=new JLabel("x: NaN, y: NaN, w: NaN, h: NaN");
	public final JLabel editor=new JLabel("w: NaN, h: NaN");
	public final JLabel tool=new JLabel("tool: pencil");
	final JLabel[]icons={new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/mouse.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/selection.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/editor.png"))))};
	final Border border=new CompoundBorder(Main.border,new EmptyBorder(0,18,0,0));
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
		tool.setBackground(Main.back);
		tool.setForeground(Main.fore);
		tool.setBorder(border);
		tool.setBounds(497,0,150,18);
		for(int i=0;i<3;i++){
			icons[i].setBackground(Main.back);
			icons[i].setSize(18,18);
			add(icons[i]);
		}
		icons[0].setLocation(1,1);
		icons[1].setLocation(150,1);
		icons[2].setLocation(349,1);
		add(mouse);
		add(selection);
		add(editor);
		add(tool);
		thread.start();
	}
	@Override public void run(){
		while(thread.isAlive()){
			if(!Main.editor.images.isEmpty())editor.setText("w: "+w+", h: "+h);
			else editor.setText("w: NaN, h: NaN");
			if(Main.editor.selected)selection.setText("x: "+Main.selection.x+", y: "+Main.selection.y+", w: "+Main.selection.w+", h: "+Main.selection.h);
			else selection.setText("x: NaN, y: NaN, w: NaN, h: NaN");
			tool.setText("tool: "+tools[Main.editor.toolId]);
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
