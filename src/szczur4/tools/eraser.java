package szczur4.tools;

import szczur4.Main;

public class eraser{
	public void execute(int x,int y){
		try{Main.editor.image.setRGB(x,y,Main.editor.secondary.getRGB());}catch(Exception ignored){}
	}
}
