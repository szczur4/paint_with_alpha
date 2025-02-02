package szczur4.tools;

import java.awt.*;
import szczur4.K;

public class line{
	public static void execute(int x1,int y1,int x2,int y2,Color c){
		Graphics2D g=(Graphics2D)K.editor.img.get(K.editor.fileId).getGraphics();
		g.setColor(c);
		g.setStroke(new BasicStroke(K.editor.strokeSize));
		try{g.drawLine(x1,y1,x1+x2,y1+y2);}catch(Exception ignored){}
	}
}
