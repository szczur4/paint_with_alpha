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
	public final JLabel[]labels={new JLabel("x: NaN, y: NaN"),new JLabel("x: NaN, y: NaN, w: NaN, h: NaN"),new JLabel("w: NaN, h: NaN"),new JLabel("tool: pencil"),new JLabel("zoom: 100%")};
	final JLabel[]icons={new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/mouse.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/selection.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/editor.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/tool.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/zoom.png"))))};
	final Border border=new CompoundBorder(Main.border,new EmptyBorder(0,18,0,0));
	public infoBar()throws Exception{
		robot=new Robot();
		setLayout(null);
		setBackground(Main.back);
		setBorder(Main.border);
		for(int i=0;i<5;i++){
			labels[i].setBackground(Main.back);
			labels[i].setForeground(Main.fore);
			labels[i].setBorder(border);
			add(labels[i]);
			icons[i].setBackground(Main.back);
			icons[i].setSize(18,18);
			add(icons[i]);
		}
		icons[0].setLocation(1,1);
		icons[1].setLocation(130,1);
		icons[2].setLocation(329,1);
		icons[3].setLocation(458,1);
		icons[4].setLocation(587,1);
		labels[0].setBounds(0,0,130,18);
		labels[1].setBounds(129,0,200,18);
		labels[2].setBounds(328,0,130,18);
		labels[3].setBounds(457,0,130,18);
		labels[4].setBounds(586,0,100,18);
		thread.start();
	}
	@Override public void run(){
		while(thread.isAlive()){
			if(!Main.editor.images.isEmpty())labels[2].setText("w: "+w+", h: "+h);
			else labels[2].setText("w: NaN, h: NaN");
			if(Main.editor.selected)labels[1].setText("x: "+Main.selection.x+", y: "+Main.selection.y+", w: "+Main.selection.w+", h: "+Main.selection.h);
			else labels[1].setText("x: NaN, y: NaN, w: NaN, h: NaN");
			labels[3].setText("tool: "+tools[Main.editor.toolId]);
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
