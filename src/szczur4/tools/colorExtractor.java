package szczur4.tools;
import java.awt.*;
import szczur4.K;
public class colorExtractor{
	public static void execute(int x,int y){
		Color c;
		try{c=new Color(K.editor.img.get(K.editor.fId).getRGB(x,y),true);}catch(Exception ignored){return;}
		for(int i=0;i<8;i++)if(K.top.colors.colors[i].c.getRGB()==c.getRGB()){
			setColor(c);
			return;
		}
		for(int i=10;i>=0;i--)K.top.colors.colors[i+1].c=K.top.colors.colors[i].c;
		K.top.colors.colors[0].c=c;
		setColor(c);
		for(int i=0;i<12;i++)K.top.colors.colors[i].repaint();
	}
	private static void setColor(Color c){
		K.top.colors.fix(c);
		if(K.top.colors.secondary)K.editor.secondary=c;
		else K.editor.primary=c;
		K.top.colors.display.repaint();
	}
}
