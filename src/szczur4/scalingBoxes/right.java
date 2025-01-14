package szczur4.scalingBoxes;

import java.awt.*;
import java.awt.event.*;
import szczur4.Main;
import szczur4.scalingBox;

public class right extends scalingBox implements MouseListener,MouseMotionListener{
	int width,lastX;
	float multiplier;
	boolean moved,pressed;
	public right(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){
		width=Main.editor.width;
		pressed=true;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved){
			Main.editor.width=(int)(lastX/multiplier)+width;
			Main.editor.setSize((int)(((int)(lastX/multiplier)+width)*multiplier),(int)(Main.editor.height*multiplier));
			Main.editor.resizeImage();
		}
		setCursor(Cursor.getDefaultCursor());
		pressed=false;
		moved=false;
	}
	@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));}
	@Override public void mouseExited(MouseEvent ev){if(!pressed)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=Main.editor.scales[Main.editor.zoom];
		lastX=ev.getX();
	}
	@Override public void mouseMoved(MouseEvent ev){}
}
