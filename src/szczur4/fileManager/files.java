package szczur4.fileManager;

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
		files.get(n).setBackground(K.medium);
		files.get(n).setEnabled(false);
		cBId=n;
		K.editor.fileId=n;
	}
	final Runnable close=()->{
		K.fileCore.files.files.remove(id);
		K.editor.images.remove(id);
		K.editor.files.remove(id);
		if(K.editor.images.isEmpty())id=0;
		else id=Math.clamp(id,0,K.editor.images.size()-1);
		if(!K.editor.images.isEmpty())fix(id);
		else K.editor.addStarterThings();
		K.editor.fileId=id;
		K.editor.selected=false;
		K.fileCore.updateUI();
	};
}
