package szczur4.tools;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayDeque;
import java.util.Queue;
import szczur4.Main;

public class fill{
	boolean[][]visited;
	int width,height,before;
	BufferedImage tmp;
	final Queue<Point>queue=new ArrayDeque<>();
	public void execute(int x,int y,Color c){
		tmp=Main.editor.images.get(Main.editor.fileId);
		width=Main.editor.width;
		height=Main.editor.height;
		visited=new boolean[width][height];
		try{before=tmp.getRGB(x,y);}catch(Exception ignored){return;}
		if(before==c.getRGB())return;
		queue.add(new Point(x,y));
		int a,b;
		while(!queue.isEmpty()){
			a=queue.peek().x;
			b=queue.poll().y;
			visited[a][b]=true;
			tmp.setRGB(a,b,c.getRGB());
			if(a+1<width&&b<height&&!visited[a+1][b]&&tmp.getRGB(a+1,b)==before){
				Point p=new Point(a+1,b);
				if(!queue.contains(p))queue.add(p);
			}
			if(a-1<width&&b<height&&a>0&&!visited[a-1][b]&&tmp.getRGB(a-1,b)==before){
				Point p=new Point(a-1,b);
				if(!queue.contains(p))queue.add(p);
			}
			if(a<width&&b+1<height&&!visited[a][b+1]&&tmp.getRGB(a,b+1)==before){
				Point p=new Point(a,b+1);
				if(!queue.contains(p))queue.add(p);
			}
			if(a<width&&b-1<height&&b>0&&!visited[a][b-1]&&tmp.getRGB(a,b-1)==before){
				Point p=new Point(a,b-1);
				if(!queue.contains(p))queue.add(p);
			}
		}
		Main.editor.images.set(Main.editor.fileId,tmp);
	}
}