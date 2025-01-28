package szczur4.colorController;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;

public class colorDisplay extends JLabel implements MouseListener{
	public colorDisplay(){
		setBorder(K.border);
		setBounds(123,5,46,34);
		setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("background.png"))));
		addMouseListener(this);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(K.editor.primary);
		g.fillRect(1,1,44,19);
		g.fillRect(1,20,27,13);
		g.setColor(K.editor.secondary);
		g.fillRect(29,21,16,12);
		g.setColor(Color.yellow);
		g.drawRect(28,20,17,13);
		if(!K.colorCore.secondary){
			g.drawRect(0,0,45,33);
			g.setColor(K.fore);
			g.drawLine(29,33,45,33);
			g.drawLine(45,21,45,33);
		}
	}
	@Override public void mouseClicked(MouseEvent ev){
		int x=ev.getX(),y=ev.getY();
		K.colorCore.secondary=x>29&&y>21;
		Color color;
		if(K.colorCore.secondary)color=K.editor.secondary;
		else color=K.editor.primary;
		K.colorCore.channels[0].setText(color.getRed()+"");
		K.colorCore.channels[1].setText(color.getGreen()+"");
		K.colorCore.channels[2].setText(color.getBlue()+"");
		K.colorCore.channels[3].setText(color.getAlpha()+"");
		repaint();
	}
	@Override public void mousePressed(MouseEvent ev){}
	@Override public void mouseReleased(MouseEvent ev){}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){}
}
