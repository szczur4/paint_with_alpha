package szczur4.selectionController;

import szczur4.K;

public class select{
	public static void execute(int x,int y,int w,int h,int id){
		try{
			K.selCore.img=K.editor.images.get(K.editor.fileId).getSubimage(x,y,w,h);
			K.selCore.x=x;
			K.selCore.y=y;
			K.selCore.x1=x;
			K.selCore.y1=y;
			K.selCore.w=w;
			K.selCore.h=h;
			K.selCore.id=id;
			K.selCore.setBounds((int)(x*K.editor.multiplier+K.editor.lX),(int)(y*K.editor.multiplier+K.editor.lY),(int)(w*K.editor.multiplier),(int)(h*K.editor.multiplier));
		}catch(Exception ignored){}
	}
}
