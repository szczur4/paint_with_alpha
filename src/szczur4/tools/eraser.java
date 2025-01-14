package szczur4.tools;

import szczur4.Main;

public class eraser{
	public void execute(int x,int y){
		try{Main.editor.images.get(Main.editor.fileId).setRGB(x,y,Main.editor.secondary.getRGB());}catch(Exception ignored){}
	}
}
