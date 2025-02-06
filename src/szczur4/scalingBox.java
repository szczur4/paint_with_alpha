package szczur4;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class scalingBox extends JPanel implements MouseListener,MouseMotionListener{
	int width,height,lastX,lastY;
	final int id;
	float multiplier;
	boolean moved;
	protected scalingBox(int n){
		id=n;
		setLocation(-10,-10);
		setSize(6,6);
		addMouseListener(this);
		addMouseMotionListener(this);
		setOpaque(false);
	}
	public void updateLocation(){
		int tx=K.editor.lx+K.editor.tx-3,ty=K.editor.ly+K.editor.ty-3,w=(int)(K.editor.w*K.editor.m+1)>>1,h=(int)(K.editor.h*K.editor.m+1)>>1;
		switch(id){
			case(0)->setLocation(tx,ty);
			case(1)->setLocation(tx+w,ty);
			case(2)->setLocation(tx+(w<<1),ty);
			case(3)->setLocation(tx,ty+h);
			case(4)->setLocation(tx+(w<<1),ty+h);
			case(5)->setLocation(tx,ty+(h<<1));
			case(6)->setLocation(tx+w,ty+(h<<1));
			case(7)->setLocation(tx+(w<<1),ty+(h<<1));
		}
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){
		width=K.editor.w;
		height=K.editor.h;
		K.editor.bId=id;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved)moved=false;
		setCursor(Cursor.getDefaultCursor());
		K.editor.resizeImage();
		K.editor.bId=8;
	}
	@Override public void mouseEntered(MouseEvent ev){
		switch(id){
			case(0),(7)->setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			case(1),(6)->setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			case(2),(5)->setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			case(3),(4)->setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		}
	}
	@Override public void mouseExited(MouseEvent ev){if(K.editor.bId!=8)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=K.editor.m;
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
	}
	@Override public void mouseMoved(MouseEvent ev){}
	public void nd(){K.bottom.info.h=Math.max(height-(int)(lastY/multiplier),1);}
	public void sd(){K.bottom.info.h=Math.max(height+(int)(lastY/multiplier),1);}
	public void wd(){K.bottom.info.w=Math.max(width-(int)(lastX/multiplier),1);}
	public void ed(){K.bottom.info.w=Math.max(width+(int)(lastX/multiplier),1);}
}
