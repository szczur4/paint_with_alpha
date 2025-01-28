package szczur4.tools;

import szczur4.K;

public class eraser{
	public void execute(int x,int y){
		try{K.editor.images.get(K.editor.fileId).setRGB(x,y,K.editor.secondary.getRGB());}catch(Exception ignored){}
	}
}
