package szczur4.paint.tools;
import java.awt.*;
import szczur4.paint.paint;
public class colorExtractor{
	public static void execute(int x,int y){
		Color c;
		try{c=new Color(paint.center.editor.img.get(paint.center.editor.fId).getRGB(x,y),true);}catch(Exception ignored){return;}
		for(int i=0;i<8;i++)if(paint.top.colors.colors[i].c.getRGB()==c.getRGB()){
			setColor(c);
			return;
		}
		for(int i=10;i>=0;i--) paint.top.colors.colors[i+1].c=paint.top.colors.colors[i].c;
		paint.top.colors.colors[0].c=c;
		setColor(c);
		for(int i=0;i<12;i++) paint.top.colors.colors[i].repaint();
	}
	private static void setColor(Color c){
		paint.top.colors.fix(c);
		if(paint.top.colors.secondary) paint.center.editor.secondary=c;
		else paint.center.editor.primary=c;
		paint.top.colors.display.repaint();
	}
}
