package szczur4.paint.tools;

import java.awt.*;
import szczur4.paint.paint;

public class fillRect{
	public static void execute(int x1,int y1,int x2,int y2,Color c){
		Graphics2D g=(Graphics2D)paint.center.editor.img.get(paint.center.editor.fId).getGraphics();
		g.setColor(c);
		g.setStroke(new BasicStroke(paint.center.editor.strokeSize));
		g.fill(rect.execute(x1,y1,x2+1,y2+1));
	}
}
