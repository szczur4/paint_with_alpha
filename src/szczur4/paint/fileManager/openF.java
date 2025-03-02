package szczur4.paint.fileManager;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import szczur4.paint.paint;
public class openF{
	public static void execute(File f){
		if(!f.exists())return;
		paint.center.editor.files.add(f);
		try{
			BufferedImage tmpImg=ImageIO.read(paint.center.editor.files.getLast());
			paint.center.editor.img.add(new BufferedImage(tmpImg.getWidth(),tmpImg.getHeight(),BufferedImage.TYPE_INT_ARGB));
			paint.center.editor.img.getLast().createGraphics().drawImage(tmpImg,0,0,null);
		}catch(Exception ignored){}
		paint.center.editor.w=paint.center.editor.img.getLast().getWidth();
		paint.center.editor.h=paint.center.editor.img.getLast().getHeight();
		paint.center.editor.fId=paint.center.editor.img.size()-1;
		paint.top.files.files.files.add(new file(paint.center.editor.fId));
		paint.top.files.updateUI();
		if(!paint.center.editor.img.isEmpty()) paint.center.editor.removeStarter();
		paint.center.editor.updateLocations();
	}
}
