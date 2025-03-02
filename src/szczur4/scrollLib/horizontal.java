package szczur4.scrollLib;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class horizontal extends JPanel implements MouseWheelListener{
	public int mx,x,w,offset;
	private int lim;
	public float m=1,M=1;
	public final JPanel scroller=new JPanel();
	public horizontal(Color background,Color scrollerColor,Border border){
		setLayout(null);
		setBackground(background);
		setBorder(border);
		scroller.setBackground(scrollerColor);
		scroller.addMouseListener(new MouseListener(){
			@Override public void mouseClicked(MouseEvent ev){}
			@Override public void mousePressed(MouseEvent ev){}
			@Override public void mouseReleased(MouseEvent ev){}
			@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));}
			@Override public void mouseExited(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
		});
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){x-=(int)(ev.getX()*m-mx);update();}
			@Override public void mouseMoved(MouseEvent ev){mx=(int)(ev.getX()*m);}
		});
		add(scroller);
		addMouseWheelListener(this);
	}
	public void setLimit(int value){lim=value;lim*=-1;}
	public int getLimit(){return -lim;}
	public void update(){
		fix();
		w=(int)Math.min((getWidth())/m+offset,getWidth()-2);
		scroller.setBounds(1-(int)(x/m),1,w,getHeight()-2);
	}
	public void fix(){
		if(lim<0)x=Math.clamp(x,lim,0);
		else x=0;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		x-=(int)(ev.getWheelRotation()*M)<<4;
		update();
	}
}
