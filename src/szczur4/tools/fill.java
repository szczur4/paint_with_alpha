package szczur4.tools;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Queue;
import szczur4.K;
public class fill{
	private static final Queue<Point>q=new ArrayDeque<>();
	private static final int[][]d={{1,0},{-1,0},{0,1},{0,-1}};
	public static void execute(int x,int y,Color c) {
		final int w=K.editor.w,h=K.editor.h;
		if(x<0||x>=w||y<0||y>=h)return;
		final int before=K.editor.img.get(K.editor.fileId).getRGB(x,y);
		if(before==c.getRGB())return;
		final boolean[][]visited=new boolean[w][h];
		q.add(new Point(x,y));
		while(!q.isEmpty()){
			Point p=q.poll();
			int a=p.x,b=p.y;
			if(visited[a][b])continue;
			visited[a][b]=true;
			K.editor.img.get(K.editor.fileId).setRGB(a,b,c.getRGB());
			for(int[]D:d){
				final int X=a+D[0],Y=b+D[1];
				if(X>=0&&X<w&&Y>=0&&Y<h&&!visited[X][Y]&&K.editor.img.get(K.editor.fileId).getRGB(X,Y)==before)q.add(new Point(X,Y));
			}
		}
	}
}