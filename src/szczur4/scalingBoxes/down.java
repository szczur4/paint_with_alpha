package szczur4.scalingBoxes;

import java.awt.*;
import java.awt.event.*;
import szczur4.Main;
import szczur4.scalingBox;

public class down extends scalingBox implements MouseListener,MouseMotionListener{
	int height,lastY;
	float multiplier;
	boolean moved,pressed;
	public down(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){
		height=Main.editor.height;
		pressed=true;
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(moved){
			Main.editor.height=(int)(lastY/multiplier)+height;
			Main.editor.setSize((int)(Main.editor.width*multiplier),(int)(((int)(lastY/multiplier)+height)*multiplier));
			Main.editor.updateLocations();
		}
		setCursor(Cursor.getDefaultCursor());
		pressed=false;
		moved=false;
	}
	@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));}
	@Override public void mouseExited(MouseEvent ev){if(!pressed)setCursor(Cursor.getDefaultCursor());}
	@Override public void mouseDragged(MouseEvent ev){
		moved=true;
		multiplier=Main.editor.scales[Main.editor.zoom];
		lastY=ev.getY();
	}
	@Override public void mouseMoved(MouseEvent ev){}
}
