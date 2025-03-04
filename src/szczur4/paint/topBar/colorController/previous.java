package szczur4.paint.topBar.colorController;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.paint.paint;
public class previous extends JButton{
	public Color c=paint.center.editor.primary;
	public previous(){
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(paint.top.colors.secondary) paint.center.editor.secondary=c;
			else paint.center.editor.primary=c;
			paint.top.colors.display.repaint();
			paint.top.colors.fix(c);
		}});
		setBorder(paint.border);
		setFocusable(false);
		setSize(16,16);
		setIcon(new ImageIcon(Objects.requireNonNull(paint.class.getResource("background.png"))));
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(c);
		g.fillRect(1,1,14,14);
	}
}
