package szczur4.tools;

import java.awt.*;
import szczur4.Main;

public class pencil{
	public void execute(int x,int y,Color c){
		try{Main.editor.image.setRGB(x,y,c.getRGB());}catch(Exception ignored){}
	}
}
