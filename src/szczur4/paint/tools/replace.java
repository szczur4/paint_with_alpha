package szczur4.paint.tools;
import java.awt.image.*;
import szczur4.paint.paint;
public class replace{
	public static void execute(BufferedImage img){
		int a=paint.center.editor.primary.getRGB(),b=paint.center.editor.secondary.getRGB();
		for(int y=0;y<img.getHeight();y++)for(int x=0;x<img.getWidth();x++)if(img.getRGB(x,y)==a)img.setRGB(x,y,b);
	}
}
