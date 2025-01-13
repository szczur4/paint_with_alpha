package szczur4.tools;

import java.awt.*;
import szczur4.Main;

public class colorExtractor{
	public void execute(int x,int y){
		Color c;
		try{c=new Color(Main.editor.image.getRGB(x,y),true);}catch(Exception ignored){return;}
		for(int i=0;i<8;i++)if(Main.colorController.previousColors[i].color.getRGB()==c.getRGB()){
			setColor(c);
			return;
		}
		for(int i=6;i>=0;i--)Main.colorController.previousColors[i+1].color=Main.colorController.previousColors[i].color;
		Main.colorController.previousColors[0].color=c;
		for(int i=0;i<8;i++)Main.colorController.previousColors[i].repaint();
	}
	void setColor(Color c){
		Main.colorController.channels[0].setText(c.getRed()+"");
		Main.colorController.channels[1].setText(c.getGreen()+"");
		Main.colorController.channels[2].setText(c.getBlue()+"");
		Main.colorController.channels[3].setText(c.getAlpha()+"");
		if(Main.colorController.secondary)Main.editor.secondary=c;
		else Main.editor.primary=c;
		Main.colorController.colorDisplay.repaint();
	}
}
