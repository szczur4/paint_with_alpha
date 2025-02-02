package szczur4.topBar.selectionController;
import java.awt.*;
import szczur4.K;
public class delete{
	public static void execute(int id){
		if(!K.editor.pasted){
			Graphics2D g=K.editor.img.get(K.editor.fileId).createGraphics();
			g.setBackground(new Color(0x0,true));
			g.clearRect(K.selection.x1,K.selection.y1,K.selection.w,K.selection.h);
			g.dispose();
			K.editor.pasted=false;
		}
		K.editor.selected=false;
		select.execute(0,0,0,0,0);
		K.selection.setBounds(0,0,0,0);
	}
}
