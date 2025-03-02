package szczur4.paint.tools;
import szczur4.paint.paint;
public class eraser{
	public static void execute(int x,int y){
		try{paint.center.editor.img.get(paint.center.editor.fId).setRGB(x,y,paint.center.editor.secondary.getRGB());}catch(Exception ignored){}
	}
}
