package szczur4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class scalingBox extends JPanel implements MouseListener,MouseMotionListener{
	int id,width,height,lastX,lastY;
	float multiplier;
	boolean moved;
	protected scalingBox(int n){
		id=n;
		setLocation(-10,-10);
		setSize(5,5);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void updateLocation(int w,int h,float multiplier){
		int W=Main.frame.getContentPane().getWidth()>>1,H=Main.frame.getContentPane().getHeight()>>1;
		w=(int)(w*multiplier);
		h=(int)(h*multiplier);
		switch(id){
			case(0)->setLocation(W-w/2-4,H-h/2+17);
			case(1)->setLocation(W-2,H-h/2+17);
			case(2)->setLocation(W+w/2-1,H-h/2+17);
			case(3)->setLocation(W-w/2-4,H+19);
			case(4)->setLocation(W+w/2-1,H+19);
			case(5)->setLocation(W-w/2-4,H+h/2+20);
			case(6)->setLocation(W-2,H+h/2+20);
			case(7)->setLocation(W+w/2-1,H+h/2+20);
		}
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){
		width=Main.editor.width;
		height=Main.editor.height;
		Main.editor.boxId=id;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved){
			switch(id){
				case(0)->{
					n();
					w();
				}
				case(1)->n();
				case(2)->{
					n();
					e();
				}
				case(3)->w();
				case(4)->e();
				case(5)->{
					s();
					w();
				}
				case(6)->s();
				case(7)->{
					s();
					e();
				}
			}
			Main.editor.resizeImage();
			moved=false;
		}
		setCursor(Cursor.getDefaultCursor());
		Main.editor.boxId=8;
	}
	@Override public void mouseEntered(MouseEvent ev){
		switch(id){
			case(0),(7)->setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			case(1),(6)->setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			case(2),(5)->setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			case(3),(4)->setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		}
	}
	@Override public void mouseExited(MouseEvent ev){if(Main.editor.boxId!=8)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=Main.editor.multiplier;
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
	public void n(){Main.editor.height=height-(int)(lastY/multiplier);}
	public void s(){Main.editor.height=height+(int)(lastY/multiplier);}
	public void w(){Main.editor.width=width-(int)(lastX/multiplier);}
	public void e(){Main.editor.width=width+(int)(lastX/multiplier);}
	public void nd(){Main.infoBar.h=height-(int)(lastY/multiplier);}
	public void sd(){Main.infoBar.h=height+(int)(lastY/multiplier);}
	public void wd(){Main.infoBar.w=width-(int)(lastX/multiplier);}
	public void ed(){Main.infoBar.w=width+(int)(lastX/multiplier);}
}
