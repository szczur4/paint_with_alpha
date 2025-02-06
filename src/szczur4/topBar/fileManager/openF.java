package szczur4.topBar.fileManager;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import szczur4.K;
public class openF{
	public static void execute(){
		K.opener.setVisible(true);
		File[]tmp=K.opener.getFiles();
		if(tmp.length==0)return;
		for(File file:tmp){
			if(!file.exists()){
				System.err.println("File "+file.getAbsolutePath()+" does not exist");
				continue;
			}
			if(!file.getName().substring(file.getName().lastIndexOf(".")+1).equalsIgnoreCase("png")){
				System.err.println("wrong extension");
				continue;
			}
			K.editor.files.add(file);
			try{
				BufferedImage tmpImg=ImageIO.read(K.editor.files.getLast());
				K.editor.img.add(new BufferedImage(tmpImg.getWidth(),tmpImg.getHeight(),BufferedImage.TYPE_INT_ARGB));
				K.editor.img.getLast().createGraphics().drawImage(tmpImg,0,0,null);
			}catch(Exception ignored){}
			K.editor.w=K.editor.img.getLast().getWidth();
			K.editor.h=K.editor.img.getLast().getHeight();
			K.editor.updateLocations();
			K.editor.fId=K.editor.img.size()-1;
			K.top.files.files.files.add(new file(K.editor.fId));
			K.editor.updateLocations();
		}
		K.top.files.updateUI();
		if(K.editor.img.isEmpty())K.editor.addStarter();
		else K.editor.removeStarter();
		K.editor.updateLocations();
	}
}
