package szczur4.paint.center;
import java.awt.*;
import javax.swing.*;
import szczur4.paint.paint;
public class fixer extends JPanel{
	fixer(){
		setLayout(null);
		setLocation(11,11);
		setBackground(paint.back);
		setBorder(paint.border);
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		paint.center.editor.repaint();
		paintBorder(g);
		g.dispose();
	}
}
