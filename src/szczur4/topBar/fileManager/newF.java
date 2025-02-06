package szczur4.topBar.fileManager;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import szczur4.K;
public class newF{
	public static void execute(){
		K.saver.setVisible(true);
		File tmp;
		try{tmp=K.saver.getFiles()[0];}catch(Exception _){return;}
		try{
			String s=tmp.getAbsolutePath();
			if(!s.startsWith(".png",s.length()-4))tmp=new File(s+".png");
			if(tmp.createNewFile())System.out.println("created: "+tmp.getAbsolutePath());
			BufferedImage tmpImg=new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
			ImageIO.write(tmpImg,"png",tmp);
			K.editor.img.add(ImageIO.read(tmp));
			K.editor.w=K.editor.img.getLast().getWidth();
			K.editor.h=K.editor.img.getLast().getHeight();
		}catch(Exception ex){
			System.err.println("no file found");
			return;
		}
		K.editor.files.add(tmp);
		K.editor.fId=K.editor.img.size()-1;
		K.top.files.files.files.add(new file(K.editor.fId));
		K.top.files.updateUI();
		K.editor.removeStarter();
		K.editor.updateLocations();
	}
}
