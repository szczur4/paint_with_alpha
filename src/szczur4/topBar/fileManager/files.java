package szczur4.topBar.fileManager;

import java.util.Vector;
import javax.swing.*;
import szczur4.K;

public class files extends JPanel{
	int cBId,id,width;
	public final Vector<file>files=new Vector<>();
	files(){
		setLocation(1,1);
		setBackground(K.back);
		setLayout(null);
	}
	public void fix(int n){
		try{
			files.get(cBId).setBackground(K.back);
			files.get(cBId).setEnabled(true);
		}catch(Exception ignored){}
		files.get(n).setBackground(K.mid);
		files.get(n).setEnabled(false);
		cBId=n;
		K.editor.fId=n;
	}
	public final Runnable close=()->{
		K.top.files.files.files.remove(id);
		K.editor.img.remove(id);
		K.editor.files.remove(id);
		if(K.editor.img.isEmpty())id=0;
		else id=Math.clamp(id,0,K.editor.img.size()-1);
		if(!K.editor.img.isEmpty())fix(id);
		else K.editor.addStarter();
		K.editor.fId=id;
		K.editor.selected=false;
		K.top.files.updateUI();
	};
}
