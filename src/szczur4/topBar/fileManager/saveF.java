package szczur4.topBar.fileManager;
import javax.imageio.ImageIO;
import szczur4.K;

public class saveF{
	public static void execute(int id){
		try{
			if(K.editor.files.get(id).createNewFile())System.out.print("created & ");
			ImageIO.write(K.editor.img.get(id),"png",K.editor.files.get(id));
		}catch(Exception ex){System.err.println("no file found");}
		System.out.print("saved: "+K.editor.files.get(id).getAbsolutePath()+"\n");
		K.editor.removeStarter();
	}
}
