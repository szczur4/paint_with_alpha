package szczur4.paint.tools;

import java.awt.*;
import java.awt.image.*;

public class pencil{
	public static void execute(int x,int y,Color c,BufferedImage img){
		try{img.setRGB(x,y,c.getRGB());}catch(Exception ignored){}
	}
}
