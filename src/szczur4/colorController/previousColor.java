package szczur4.colorController;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.Main;

public class previousColor extends JButton{
	public Color color=Main.editor.primary;
	public int id;
	public previousColor(){
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(Main.colorController.secondary)Main.editor.secondary=color;
			else Main.editor.primary=color;
			Main.colorController.colorDisplay.repaint();
			Main.colorController.channels[0].setText(color.getRed()+"");
			Main.colorController.channels[1].setText(color.getGreen()+"");
			Main.colorController.channels[2].setText(color.getBlue()+"");
			Main.colorController.channels[3].setText(color.getAlpha()+"");
		}});
		setBorder(Main.border);
		setSize(16,16);
		setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("background.png"))));
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(color);
		g.fillRect(1,1,14,14);
	}
}
