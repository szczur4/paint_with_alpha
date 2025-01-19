package szczur4.scalingBoxes;

import java.awt.*;
import java.awt.event.*;
import szczur4.Main;
import szczur4.scalingBox;

public class corner extends scalingBox implements MouseListener,MouseMotionListener{
	int width,height,lastX,lastY;
	float multiplier;
	boolean moved,pressed;
	public corner(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){
		width=Main.editor.width;
		height=Main.editor.height;
		pressed=true;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved){
			Main.editor.width=(int)(lastX/multiplier)+width;
			Main.editor.height=(int)(lastY/multiplier)+height;
			Main.editor.setSize((int)(((int)(lastX/multiplier)+width)*multiplier),(int)(((int)(lastY/multiplier)+height)*multiplier));
			Main.editor.resizeImage();
		}
		setCursor(Cursor.getDefaultCursor());
		pressed=false;
		moved=false;
	}
	@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));}
	@Override public void mouseExited(MouseEvent ev){if(!pressed)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=Main.editor.scales[Main.editor.zoom];
		lastX=ev.getX();
		lastY=ev.getY();
		Main.infoBar.w=(int)(lastX/multiplier)+width;
		Main.infoBar.h=(int)(lastY/multiplier)+height;
	}
	@Override public void mouseMoved(MouseEvent ev){}
}
