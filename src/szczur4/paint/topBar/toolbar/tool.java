package szczur4.paint.topBar.toolbar;

import java.awt.*;
import javax.swing.*;
import szczur4.paint.paint;
public class tool extends JButton{
	int n;
	public tool(AbstractAction a,int id){
		super(a);
		n=id;
	}
	@Override public void paint(Graphics graphics){
		Graphics2D g=(Graphics2D)graphics;
		super.paint(g);
		g.setColor(paint.center.editor.primary);
		switch(n){
			case(4)->g.drawLine(2,2,13,13);
			case(5)->g.drawRect(2,4,11,7);
			case(6)->g.fillRect(2,4,12,8);
			case(7)->g.drawOval(2,4,11,7);
			case(8)->{
				g.drawOval(2,4,11,7);
				g.drawOval(3,5,9,5);
				g.fillRect(4,6,8,4);
			}
		}
		g.dispose();
	}
}
