package szczur4.tools;

import java.awt.*;
import szczur4.Main;

public class emptyElipse{
	public void execute(int x1,int y1,int x2,int y2,Color c){
		Graphics2D g=(Graphics2D)Main.editor.image.getGraphics();
		g.setColor(c);
		g.draw(elipse.execute(x1,y1,x2,y2));
	}
}
