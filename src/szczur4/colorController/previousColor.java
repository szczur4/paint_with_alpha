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
			if(Main.colorCore.secondary)Main.editor.secondary=color;
			else Main.editor.primary=color;
			Main.colorCore.colorDisplay.repaint();
			Main.colorCore.channels[0].setText(color.getRed()+"");
			Main.colorCore.channels[1].setText(color.getGreen()+"");
			Main.colorCore.channels[2].setText(color.getBlue()+"");
			Main.colorCore.channels[3].setText(color.getAlpha()+"");
		}});
		setBorder(Main.border);
		setFocusable(false);
		setSize(16,16);
		setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("background.png"))));
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(color);
		g.fillRect(1,1,14,14);
	}
}
