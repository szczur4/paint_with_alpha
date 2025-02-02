package szczur4.tools;
import java.awt.*;
import szczur4.K;
public class colorExtractor{
	public static void execute(int x,int y){
		Color c;
		try{c=new Color(K.editor.img.get(K.editor.fileId).getRGB(x,y),true);}catch(Exception ignored){return;}
		for(int i=0;i<8;i++)if(K.top.colors.previousColors[i].color.getRGB()==c.getRGB()){
			setColor(c);
			return;
		}
		for(int i=6;i>=0;i--)K.top.colors.previousColors[i+1].color=K.top.colors.previousColors[i].color;
		K.top.colors.previousColors[0].color=c;
		setColor(c);
		for(int i=0;i<8;i++)K.top.colors.previousColors[i].repaint();
	}
	private static void setColor(Color c){
		K.top.colors.channels[0].setText(c.getRed()+"");
		K.top.colors.channels[1].setText(c.getGreen()+"");
		K.top.colors.channels[2].setText(c.getBlue()+"");
		K.top.colors.channels[3].setText(c.getAlpha()+"");
		if(K.top.colors.secondary) K.editor.secondary=c;
		else K.editor.primary=c;
		K.top.colors.colorDisplay.repaint();
	}
}
