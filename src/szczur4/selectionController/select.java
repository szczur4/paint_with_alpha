package szczur4.selectionController;

import szczur4.Main;

public class select{
	public void execute(int x,int y,int w,int h){
		try{
			Main.selection.image=Main.editor.images.get(Main.editor.fileId).getSubimage(x,y,w,h);
			Main.selection.x=x;
			Main.selection.y=y;
			Main.selection.x1=x;
			Main.selection.y1=y;
			Main.selection.w=w;
			Main.selection.h=h;
		}catch(Exception ignored){}
	}
}
