package szczur4.tools;

import java.awt.*;
import szczur4.K;

public class fillEli{
	public static void execute(int x1,int y1,int x2,int y2,Color c){
		Graphics2D g=(Graphics2D)K.editor.img.get(K.editor.fId).getGraphics();
		g.setColor(c);
		g.setStroke(new BasicStroke(K.editor.strokeSize));
		g.fill(elipse.execute(x1,y1,x2,y2));
	}
}
