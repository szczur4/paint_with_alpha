package szczur4.tools;

import java.awt.*;
import szczur4.Main;

public class colorExtractor{
	public void execute(int x,int y){
		Color c;
		try{c=new Color(Main.editor.images.get(Main.editor.fileId).getRGB(x,y),true);}catch(Exception ignored){return;}
		for(int i=0;i<8;i++)if(Main.colorCore.previousColors[i].color.getRGB()==c.getRGB()){
			setColor(c);
			return;
		}
		for(int i=6;i>=0;i--)Main.colorCore.previousColors[i+1].color=Main.colorCore.previousColors[i].color;
		Main.colorCore.previousColors[0].color=c;
		for(int i=0;i<8;i++)Main.colorCore.previousColors[i].repaint();
	}
	void setColor(Color c){
		Main.colorCore.channels[0].setText(c.getRed()+"");
		Main.colorCore.channels[1].setText(c.getGreen()+"");
		Main.colorCore.channels[2].setText(c.getBlue()+"");
		Main.colorCore.channels[3].setText(c.getAlpha()+"");
		if(Main.colorCore.secondary)Main.editor.secondary=c;
		else Main.editor.primary=c;
		Main.colorCore.colorDisplay.repaint();
	}
}
