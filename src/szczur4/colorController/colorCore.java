package szczur4.colorController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import szczur4.Main;

public class colorCore extends JPanel implements MouseListener,CaretListener{
	Robot robot;
	public final Border yellow=new LineBorder(Color.yellow);
	public final colorDisplay colorDisplay=new colorDisplay();
	public final JTextArea[]channels=new JTextArea[4];
	final JLabel[]labels={new JLabel("R:"),new JLabel("G:"),new JLabel("B:"),new JLabel("A:")};
	int r,g,b,a,id;
	public int cBId;
	public boolean secondary;
	final Runnable fix=()->{
		int number=0;
		try{number=Integer.parseInt(channels[id].getText());}catch(Exception ex){robot.keyPress(KeyEvent.VK_BACK_SPACE);}
		if(number>255)channels[id].setText("255");
	};
	final JButton add=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		try{r=Integer.parseInt(channels[0].getText());}catch(Exception ex){r=0;}
		try{g=Integer.parseInt(channels[1].getText());}catch(Exception ex){g=0;}
		try{b=Integer.parseInt(channels[2].getText());}catch(Exception ex){b=0;}
		try{a=Integer.parseInt(channels[3].getText());}catch(Exception ex){a=0;}
		Color tmp=new Color(r,g,b,a);
		for(int i=0;i<8;i++)if(previousColors[i].color.getRGB()==tmp.getRGB()){
			previousColors[i].setBorder(yellow);
			previousColors[cBId].setBorder(Main.border);
			cBId=i;
			colorDisplay.repaint();
			if(secondary)Main.editor.secondary=tmp;
			else Main.editor.primary=tmp;
			return;
		}
		if(secondary)Main.editor.secondary=tmp;
		else Main.editor.primary=tmp;
		for(int i=6;i>=0;i--)previousColors[i+1].color=previousColors[i].color;
		previousColors[0].color=tmp;
		for(int i=0;i<8;i++)previousColors[i].repaint();
		cBId=0;
		colorDisplay.repaint();
	}});
	public final previousColor[]previousColors=new previousColor[8];
	final CompoundBorder border=new CompoundBorder(Main.border,new EmptyBorder(-1,1,1,1));
	public colorCore()throws Exception{
		robot=new Robot();
		setBounds(0,19,245,43);
		setBorder(Main.border);
		setBackground(Main.back);
		setLayout(null);
		add(colorDisplay);
		add.setBackground(Main.back);
		add.setForeground(Main.fore);
		add.setText("Add Color");
		add.setBounds(44,23,77,16);
		add.setBorder(Main.border);
		add.setFocusable(false);
		add(add);
		for(int i=0;i<4;i++){
			channels[i]=new JTextArea();
			channels[i].setBackground(Main.back);
			channels[i].setForeground(Main.fore);
			channels[i].setBorder(border);
			channels[i].setCaretColor(Main.fore);
			channels[i].setColumns(3);
			channels[i].setBounds(16+40*(i%3),5+18*(i/3),25,16);
			channels[i].addCaretListener(this);
			channels[i].setText("0");
			channels[i].setFocusable(false);
			channels[i].addMouseListener(this);
			add(channels[i]);
			labels[i].setBackground(Main.back);
			labels[i].setForeground(Main.fore);
			labels[i].setBounds(3+40*(i%3),5+18*(i/3),15,16);
			add(labels[i]);
		}
		for(int i=0;i<8;i++){
			previousColors[i]=new previousColor();
			previousColors[i].setLocation(171+(i%4)*18,5+18*(i/4));
			previousColors[i].id=i;
			add(previousColors[i]);
		}
	}
	@Override public void caretUpdate(CaretEvent ev){
		if(ev.getSource().equals(channels[0]))id=0;
		else if(ev.getSource().equals(channels[1]))id=1;
		else if(ev.getSource().equals(channels[2]))id=2;
		else if(ev.getSource().equals(channels[3]))id=3;
		SwingUtilities.invokeLater(fix);
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){}
	@Override public void mouseReleased(MouseEvent ev){}
	@Override public void mouseEntered(MouseEvent ev){
		ev.getComponent().setFocusable(true);
	}
	@Override public void mouseExited(MouseEvent ev){
		ev.getComponent().setFocusable(false);
	}
}