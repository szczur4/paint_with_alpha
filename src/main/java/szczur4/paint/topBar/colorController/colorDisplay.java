package szczur4.paint.topBar.colorController;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.paint.paint;
public class colorDisplay extends JLabel implements MouseListener{
	public colorDisplay(){
		setBorder(paint.border);
		setBounds(187,5,46,34);
		setIcon(new ImageIcon(Objects.requireNonNull(paint.class.getResource("background.png"))));
		addMouseListener(this);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(paint.center.editor.primary);
		g.fillRect(1,1,44,19);
		g.fillRect(1,20,27,13);
		g.setColor(paint.center.editor.secondary);
		g.fillRect(29,21,16,12);
		g.setColor(Color.yellow);
		g.drawRect(28,20,17,13);
		if(!paint.top.colors.secondary){
			g.drawRect(0,0,45,33);
			g.setColor(paint.fore);
			g.drawLine(29,33,45,33);
			g.drawLine(45,21,45,33);
		}
	}
	@Override public void mouseClicked(MouseEvent ev){
		int x=ev.getX(),y=ev.getY();
		paint.top.colors.secondary=x>29&&y>21;
		Color color;
		if(paint.top.colors.secondary)color=paint.center.editor.secondary;
		else color=paint.center.editor.primary;
		paint.top.colors.fix(color);
		repaint();
	}
	@Override public void mousePressed(MouseEvent ev){}
	@Override public void mouseReleased(MouseEvent ev){}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){}
}
