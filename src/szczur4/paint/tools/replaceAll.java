package szczur4.paint.tools;
import szczur4.paint.paint;
public class replaceAll{
	public static void execute(){
		int n=paint.center.editor.img.size();
		for(int i=0;i<n;i++)replace.execute(paint.center.editor.img.get(i));
	}
}
