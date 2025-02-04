package szczur4.topBar.colorController;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;
public class previous extends JButton{
	public Color c=K.editor.primary;
	public previous(){
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(K.top.colors.secondary)K.editor.secondary=c;
			else K.editor.primary=c;
			K.top.colors.colorDisplay.repaint();
			K.top.colors.fix(c);
		}});
		setBorder(K.border);
		setFocusable(false);
		setSize(16,16);
		setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("background.png"))));
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(c);
		g.fillRect(1,1,14,14);
	}
}
