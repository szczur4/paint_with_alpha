package szczur4.paint.fileManager;
import szczur4.paint.paint;
public class saveAll{
	public static void execute(){
		int n=paint.center.editor.files.size();
		for(int i=0;i<n;i++)saveF.execute(i);
	}
}