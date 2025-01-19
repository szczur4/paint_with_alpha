package szczur4.tools;

import java.awt.image.*;
import szczur4.Main;

public class replaceAll{
	public void execute(BufferedImage img){
		int a=Main.editor.primary.getRGB(),b=Main.editor.secondary.getRGB();
		for(int y=0;y<img.getHeight();y++)for(int x=0;x<img.getWidth();x++)if(img.getRGB(x,y)==a)img.setRGB(x,y,b);
	}
}
