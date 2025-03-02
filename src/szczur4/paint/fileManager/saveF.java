package szczur4.paint.fileManager;
import javax.imageio.ImageIO;
import szczur4.paint.paint;
public class saveF{
	public static void execute(int id){
		try{
			if(paint.center.editor.files.get(id).createNewFile())System.out.print("created & ");
			ImageIO.write(paint.center.editor.img.get(id),"png",paint.center.editor.files.get(id));
		}catch(Exception ex){System.err.println("no file found");}
		System.out.print("saved: "+paint.center.editor.files.get(id).getAbsolutePath()+"\n");
		paint.center.editor.removeStarter();
	}
}
