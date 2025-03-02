package szczur4.paint.topBar.selectionController;
import java.awt.*;
import szczur4.paint.paint;
public class delete{
	public static void execute(int id){
		if(!paint.center.editor.pasted){
			Graphics2D g=paint.center.editor.img.get(paint.center.editor.fId).createGraphics();
			g.setBackground(new Color(0x0,true));
			g.clearRect(paint.selection.x1,paint.selection.y1,paint.selection.w,paint.selection.h);
			g.dispose();
			paint.center.editor.pasted=false;
		}
		paint.center.editor.selected=false;
		select.execute(0,0,0,0,0);
		paint.selection.setBounds(0,0,0,0);
	}
}
