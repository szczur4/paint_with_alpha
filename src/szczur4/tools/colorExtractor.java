package szczur4.tools;

import java.awt.*;
import szczur4.K;

public class colorExtractor{
	public void execute(int x,int y){
		Color c;
		try{c=new Color(K.editor.images.get(K.editor.fileId).getRGB(x,y),true);}catch(Exception ignored){return;}
		for(int i=0;i<8;i++)if(K.colorCore.previousColors[i].color.getRGB()==c.getRGB()){
			setColor(c);
			return;
		}
		for(int i=6;i>=0;i--) K.colorCore.previousColors[i+1].color=K.colorCore.previousColors[i].color;
		K.colorCore.previousColors[0].color=c;
		setColor(c);
		for(int i=0;i<8;i++) K.colorCore.previousColors[i].repaint();
	}
	void setColor(Color c){
		K.colorCore.channels[0].setText(c.getRed()+"");
		K.colorCore.channels[1].setText(c.getGreen()+"");
		K.colorCore.channels[2].setText(c.getBlue()+"");
		K.colorCore.channels[3].setText(c.getAlpha()+"");
		if(K.colorCore.secondary) K.editor.secondary=c;
		else K.editor.primary=c;
		K.colorCore.colorDisplay.repaint();
	}
}
