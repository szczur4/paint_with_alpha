package szczur4.tools;

import java.awt.*;
import szczur4.Main;

public class line{
	public void execute(int x1,int y1,int x2,int y2){
		Graphics g=Main.editor.images.get(Main.editor.fileId).getGraphics();
		g.setColor(Main.editor.primary);
		try{g.drawLine(x1,y1,x1+x2,y1+y2);}catch(Exception ignored){}
	}
}
