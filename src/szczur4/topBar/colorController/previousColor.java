package szczur4.topBar.colorController;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;

public class previousColor extends JButton{
	public Color color=K.editor.primary;
	public previousColor(){
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(K.top.colors.secondary) K.editor.secondary=color;
			else K.editor.primary=color;
			K.top.colors.colorDisplay.repaint();
			K.top.colors.channels[0].setText(color.getRed()+"");
			K.top.colors.channels[1].setText(color.getGreen()+"");
			K.top.colors.channels[2].setText(color.getBlue()+"");
			K.top.colors.channels[3].setText(color.getAlpha()+"");
		}});
		setBorder(K.border);
		setFocusable(false);
		setSize(16,16);
		setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("background.png"))));
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(color);
		g.fillRect(1,1,14,14);
	}
}
