package szczur4.paint;
import java.awt.image.*;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
public class resourceLoader{
	public static ImageIcon load(String path){
		BufferedImage img;
		try{img=ImageIO.read(Objects.requireNonNull(resourceLoader.class.getResource("icons/"+path)));}catch(Exception _){return null;}
		int w=img.getWidth(),h=img.getHeight();
		for(int y=0;y<h;y++)for(int x=0;x<w;x++){
			if(img.getRGB(x,y)==-16777216)img.setRGB(x,y,paint.back.getRGB());
			else if(img.getRGB(x,y)==-8421505)img.setRGB(x,y,paint.medium.getRGB());
			else if(img.getRGB(x,y)==-1)img.setRGB(x,y,paint.fore.getRGB());
		}
		return new ImageIcon(img);
	}
}
