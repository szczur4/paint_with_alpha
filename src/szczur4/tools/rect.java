package szczur4.tools;

import java.awt.*;

public class rect{
	public static Rectangle execute(int x1,int y1,int x2,int y2){
		if(x2<0){
			x1+=x2;
			x2*=-1;
		}
		if(y2<0){
			y1+=y2;
			y2*=-1;
		}
		return new Rectangle(x1,y1,x2,y2);
	}
}
