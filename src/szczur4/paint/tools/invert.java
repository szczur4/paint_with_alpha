package szczur4.paint.tools;

import java.awt.image.*;

public class invert{
	public static void execute(BufferedImage img){
		int h=img.getHeight(),w=img.getWidth();
		for(int y=0;y<h;y++)for(int x=0;x<w;x++){
			int k=img.getRGB(x,y),a=(k>>24)&0xff,r=(k>>16)&0xff,g=(k>>8)&0xff,b=k&0xff;
			r=255-r;
			g=255-g;
			b=255-b;
			img.setRGB(x,y,(a<<24)|(r<<16)|(g<<8)|b);
		}
	}
}
