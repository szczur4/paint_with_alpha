package szczur4.paint.leftBar;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.paint;
public class resizer extends JPanel implements MouseListener,MouseMotionListener{
	int x,mx;
	resizer(){
		setSize(7,48);
		setLocation(50,300);
		setBackground(paint.back);
		setBorder(paint.border);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void paint(Graphics g){
		super.paint(g);
		g.setColor(paint.fore);
		for(int i=1;i<3;i++){
			g.drawLine(i<<1,20,i<<1,28);
		}
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){}
	@Override public void mouseReleased(MouseEvent ev){}
	@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));}
	@Override public void mouseExited(MouseEvent ev){setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		x=ev.getX()-mx;
		paint.left.setSize(paint.left.getWidth()+x,paint.left.getHeight());
		paint.left.update();
	}
	@Override public void mouseMoved(MouseEvent ev){mx=ev.getX();}
}
