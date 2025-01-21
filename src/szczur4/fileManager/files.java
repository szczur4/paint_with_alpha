package szczur4.fileManager;

import java.util.Vector;
import javax.swing.*;
import szczur4.Main;

public class files extends JPanel{
	int cBId,id,width;
	public final Vector<file>files=new Vector<>();
	files(){
		setLocation(1,1);
		setBackground(Main.back);
		setLayout(null);
	}
	public void fix(int n){
		try{
			files.get(cBId).setBackground(Main.back);
			files.get(cBId).setEnabled(true);
		}catch(Exception ignored){}
		files.get(n).setBackground(Main.medium);
		files.get(n).setEnabled(false);
		cBId=n;
		Main.editor.fileId=n;
	}
	final Runnable close=()->{
		Main.fileCore.files.files.remove(id);
		Main.editor.images.remove(id);
		Main.editor.files.remove(id);
		if(Main.editor.images.isEmpty())id=0;
		else id=Math.clamp(id,0,Main.editor.images.size()-1);
		if(!Main.editor.images.isEmpty())fix(id);
		else Main.editor.addStarterThings();
		Main.editor.fileId=id;
		Main.editor.selected=false;
		Main.fileCore.updateUI();
	};
}
