package szczur4.topBar.fileManager;
import szczur4.K;
public class saveAll{
	public static void execute(){
		int n=K.editor.files.size();
		for(int i=0;i<n;i++)saveF.execute(i);
	}
}