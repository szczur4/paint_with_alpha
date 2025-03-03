package szczur4.scrollLib;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class vertical extends JPanel implements MouseWheelListener{
	public int my,y,h,offset;
	private int lim;
	public float m=1,M=1;
	public final JPanel scroller=new JPanel();
	public vertical(Color background,Color scrollerColor,Border border){
		setLayout(null);
		setBackground(background);
		setBorder(border);
		scroller.setBackground(scrollerColor);
		scroller.addMouseListener(new MouseListener(){
			@Override public void mouseClicked(MouseEvent ev){}
			@Override public void mousePressed(MouseEvent ev){}
			@Override public void mouseReleased(MouseEvent ev){}
			@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));}
			@Override public void mouseExited(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
		});
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){y-=(int)(ev.getY()*m-my);update();}
			@Override public void mouseMoved(MouseEvent ev){my=(int)(ev.getY()*m);}
		});
		add(scroller);
		addMouseWheelListener(this);
	}
	public void setLimit(int value){lim=value;lim*=-1;}
	public int getLimit(){return -lim;}
	public void update(){
		fix();
		h=(int)Math.min((getHeight())/m+offset,getHeight()-2);
		scroller.setBounds(1,1-(int)(y/m),getWidth()-2,h);
	}
	public void fix(){
		if(lim<0)y=Math.clamp(y,lim,0);
		else y=0;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		y-=(int)(ev.getWheelRotation()*M)<<4;
		update();
	}
}
