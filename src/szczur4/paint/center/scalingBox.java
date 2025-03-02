package szczur4.paint.center;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.paint.paint;
public class scalingBox extends JPanel implements MouseListener,MouseMotionListener{
	int width,height,lastX,lastY;
	final int id;
	float multiplier;
	boolean moved;
	protected scalingBox(int n){
		id=n;
		setSize(6,6);
		setBackground(Color.white);
		setBorder(new LineBorder(Color.black));
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void updateLocation(){
		int x=paint.center.editor.getX()-3,y=paint.center.editor.getY()-3,w=(int)(paint.center.editor.w*paint.center.editor.m),h=(int)(paint.center.editor.h*paint.center.editor.m);
		switch(id){
			case(0)->setLocation(x,y);
			case(1)->setLocation(x+(w>>1),y);
			case(2)->setLocation(x+w,y);
			case(3)->setLocation(x,y+(h>>1));
			case(4)->setLocation(x+w,y+(h>>1));
			case(5)->setLocation(x,y+h);
			case(6)->setLocation(x+(w>>1),y+h);
			case(7)->setLocation(x+w,y+h);
		}
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){
		width=paint.center.editor.w;
		height=paint.center.editor.h;
		paint.center.editor.bId=id;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved)moved=false;
		setCursor(Cursor.getDefaultCursor());
		paint.center.editor.resizeImage();
		paint.center.editor.bId=8;
	}
	@Override public void mouseEntered(MouseEvent ev){
		switch(id){
			case(0),(7)->setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			case(1),(6)->setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			case(2),(5)->setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			case(3),(4)->setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		}
	}
	@Override public void mouseExited(MouseEvent ev){if(paint.center.editor.bId!=8)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=paint.center.editor.m;
		lastX=ev.getX();
		lastY=ev.getY();
		switch(id){
			case(0)->{
				nd();
				wd();
			}
			case(1)->nd();
			case(2)->{
				nd();
				ed();
			}
			case(3)->wd();
			case(4)->ed();
			case(5)->{
				sd();
				wd();
			}
			case(6)->sd();
			case(7)->{
				sd();
				ed();
			}
		}
		paint.center.editor.repaint();
	}
	@Override public void mouseMoved(MouseEvent ev){}
	public void nd(){paint.info.h=Math.max(height-(int)(lastY/multiplier),1);}
	public void sd(){paint.info.h=Math.max(height+(int)(lastY/multiplier),1);}
	public void wd(){paint.info.w=Math.max(width-(int)(lastX/multiplier),1);}
	public void ed(){paint.info.w=Math.max(width+(int)(lastX/multiplier),1);}
}
