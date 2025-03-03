package szczur4.paint;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class info extends JPanel implements Runnable,ComponentListener{
	final Thread thread=new Thread(this);
	Robot robot;
	public int w,h;
	final String[]tools={"pencil","fill","eraser","color picker","line","empty rectangle","full rectangle","empty elipse","full elipse"};
	public final JLabel[]labels={new JLabel("x: NaN, y: NaN"),new JLabel("x: NaN, y: NaN, w: NaN, h: NaN"),new JLabel("w: NaN, h: NaN"),new JLabel("tool: pencil"),new JLabel("zoom: 100%"),new JLabel("Size: NaN")};
	public info()throws Exception{
		robot=new Robot();
		setLayout(null);
		setLocation(0,11);
		setBackground(paint.back);
		setBorder(paint.border);
		for(int i=0;i<6;i++){
			labels[i].setBackground(paint.back);
			labels[i].setForeground(paint.fore);
			labels[i].setBorder(paint.border);
			labels[i].setFont(paint.f);
			labels[i].setVerticalAlignment(SwingConstants.BOTTOM);
			labels[i].setIcon(resourceLoader.load("info/"+i+".png"));
			add(labels[i]);
		}
		labels[0].setBounds(0,0,130,20);
		labels[1].setBounds(129,0,200,20);
		labels[2].setBounds(328,0,130,20);
		labels[3].setBounds(457,0,130,20);
		labels[4].setBounds(586,0,100,20);
		labels[5].setLocation(685,0);
		thread.start();
	}
	public void update(){
		int x=paint.left.isVisible()?paint.left.getWidth():0,w=paint.frame.getContentPane().getWidth(),h=paint.frame.getContentPane().getHeight()-20;
		setBounds(x,h,w-x,20);
		labels[5].setSize(w-685-x,20);
	}
	@Override public void run(){
		while(thread.isAlive()){
			if(!paint.center.editor.img.isEmpty())labels[2].setText("w: "+w+", h: "+h);
			else labels[2].setText("w: NaN, h: NaN");
			if(paint.center.editor.selected)labels[1].setText("x: "+paint.selection.x+", y: "+paint.selection.y+", w: "+paint.selection.w+", h: "+paint.selection.h);
			else labels[1].setText("x: NaN, y: NaN, w: NaN, h: NaN");
			labels[3].setText("tool: "+tools[paint.center.editor.tId]);
			robot.delay(50);
		}
	}
	@Override public void componentResized(ComponentEvent e){update();}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
