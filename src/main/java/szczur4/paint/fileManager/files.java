package szczur4.paint.fileManager;

import java.util.Vector;
import javax.swing.*;
import szczur4.paint.paint;

public class files extends JPanel{
	int cBId,id,width;
	public final Vector<file>files=new Vector<>();
	files(){
		setLocation(1,1);
		setBackground(paint.back);
		setLayout(null);
	}
	public void fix(int n){
		try{
			files.get(cBId).setBackground(paint.back);
			files.get(cBId).setEnabled(true);
		}catch(Exception ignored){}
		files.get(n).setBackground(paint.medium);
		files.get(n).setEnabled(false);
		cBId=n;
		paint.center.editor.fId=n;
	}
	public final Runnable close=()->{
		paint.top.files.files.files.remove(id);
		paint.center.editor.img.remove(id);
		paint.center.editor.files.remove(id);
		if(paint.center.editor.img.isEmpty())id=0;
		else id=Math.clamp(id,0,paint.center.editor.img.size()-1);
		if(!paint.center.editor.img.isEmpty())fix(id);
		else paint.center.editor.addStarter();
		paint.center.editor.fId=id;
		paint.center.editor.selected=false;
		paint.top.files.updateUI();
	};
}
