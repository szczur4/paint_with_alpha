package szczur4.bottomBar;
import java.awt.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.K;

public class infoBar extends JPanel implements Runnable{
	final Thread thread=new Thread(this);
	Robot robot;
	public int w,h;
	final String[]tools={"pencil","fill","eraser","color picker","line","empty rectangle","full rectangle","empty elipse","full elipse"};
	public final JLabel[]labels={new JLabel("x: NaN, y: NaN"),new JLabel("x: NaN, y: NaN, w: NaN, h: NaN"),new JLabel("w: NaN, h: NaN"),new JLabel("tool: pencil"),new JLabel("zoom: 100%")};
	final JLabel[]icons={new JLabel(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/mouse.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/selection.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/editor.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/tool.png")))),new JLabel(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/zoom.png"))))};
	final Border border=new CompoundBorder(K.border,new EmptyBorder(0,20,0,0));
	public infoBar()throws Exception{
		robot=new Robot();
		setLayout(null);
		setLocation(0,11);
		setBackground(K.back);
		setBorder(K.border);
		for(int i=0;i<5;i++){
			labels[i].setBackground(K.back);
			labels[i].setForeground(K.fore);
			labels[i].setBorder(border);
			labels[i].setFont(K.f);
			add(labels[i]);
			icons[i].setBackground(K.back);
			icons[i].setSize(20,20);
			add(icons[i]);
		}
		icons[0].setLocation(1,1);
		icons[1].setLocation(130,1);
		icons[2].setLocation(329,1);
		icons[3].setLocation(458,1);
		icons[4].setLocation(587,1);
		labels[0].setBounds(0,0,130,20);
		labels[1].setBounds(129,0,200,20);
		labels[2].setBounds(328,0,130,20);
		labels[3].setBounds(457,0,130,20);
		labels[4].setLocation(586,0);
		thread.start();
	}
	@Override public void run(){
		while(thread.isAlive()){
			if(!K.editor.img.isEmpty())labels[2].setText("w: "+w+", h: "+h);
			else labels[2].setText("w: NaN, h: NaN");
			if(K.editor.selected)labels[1].setText("x: "+K.selection.x+", y: "+K.selection.y+", w: "+K.selection.w+", h: "+K.selection.h);
			else labels[1].setText("x: NaN, y: NaN, w: NaN, h: NaN");
			labels[3].setText("tool: "+tools[K.editor.toolId]);
			robot.delay(50);
		}
	}
}
