package szczur4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class scalingBox extends JPanel implements MouseListener,MouseMotionListener{
	int id,width,lastX;
	float multiplier;
	boolean moved;
	protected scalingBox(int n){
		id=n;
		setLocation(-10,-10);
		setSize(5,5);
		setBorder(new LineBorder(Color.black,1));
		setBackground(Color.white);
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
		Main.editor.resizing=true;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved){
			Main.editor.width=(int)(lastX/multiplier)+width;
			Main.editor.setSize((int)(((int)(lastX/multiplier)+width)*multiplier),(int)(Main.editor.height*multiplier));
			Main.editor.resizeImage();
			moved=false;
		}
		setCursor(Cursor.getDefaultCursor());
		Main.editor.resizing=false;
	}
	@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));}
	@Override public void mouseExited(MouseEvent ev){if(!Main.editor.resizing)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=Main.editor.multiplier;
		lastX=ev.getX();
		Main.infoBar.w=(int)(lastX/multiplier)+width;
	}
	@Override public void mouseMoved(MouseEvent ev){}
}
