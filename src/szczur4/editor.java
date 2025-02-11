package szczur4;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import szczur4.tools.*;
import szczur4.topBar.selectionController.flip;
import szczur4.topBar.selectionController.select;
public class editor extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener,Runnable,ComponentListener{
	public Robot robot;
	public final Thread thread=new Thread(this);
	public int w=100,h=48,tId,x1,y1,x2,y2,mx,my,button,fId,strokeSize=1,lx,ly,tx,ty,bId=8;
	public float m=1;
	public boolean grid,pressed,selecting,selected,listenForMouse,doClear=true,moving,pasted,newColor;
	public Color primary=new Color(0x0,true),secondary=new Color(0x0,true);
	public final ArrayList<File>files=new ArrayList<>();
	public final ArrayList<BufferedImage>img=new ArrayList<>();
	public final scalingBox[]boxes=new scalingBox[8];
	public final BufferedImage background=ImageIO.read(Objects.requireNonNull(K.class.getResource("background.png")));
	public final BasicStroke dash1=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[]{4,4},0),dash2=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,new float[]{4},4),normal=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	public final AbstractAction confirm=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(!selected)return;
		BufferedImage tmp=K.selection.execute();
		img.get(fId).createGraphics().drawImage(tmp,K.selection.x-(tmp.getWidth()-K.selection.w)/2,K.selection.y-(tmp.getHeight()-K.selection.h)/2,null);
		flip.flipDir=0;
		selected=false;
		pasted=false;
		select.execute(0,0,0,0,0);
		K.selection.setBounds(0,0,0,0);
	}};
	final JLabel info=new JLabel("No files open");
	editor()throws Exception{
		robot=new Robot();
		info.setForeground(K.fore);
		info.setBackground(K.back);
		info.setBounds(5,0,100,20);
		info.setFont(K.f);
		setLayout(null);
		setLocation(12,73);
		setBackground(K.back);
		setFont(K.f);
		add(info);
		addMouseListener(this);
		addMouseMotionListener(this);
		for(int i=0;i<8;i++){
			boxes[i]=new scalingBox(i);
			add(boxes[i]);
		}
		thread.start();
	}
	public void resizeImage(){
		BufferedImage tmp=new BufferedImage(K.bottom.info.w,K.bottom.info.h,BufferedImage.TYPE_INT_ARGB);
		int x=0,y=0;
		switch(bId){
			case(0)->{
				x=K.bottom.info.w-w;
				y=K.bottom.info.h-h;
			}
			case(1),(2)->y=K.bottom.info.h-h;
			case(3),(5)->x=K.bottom.info.w-w;
		}
		tmp.createGraphics().drawImage(img.get(fId),x,y,null);
		img.set(fId,tmp);
		updateLocations();
	}
	public void updateLocations(){
		if(!img.isEmpty()){
			w=img.get(fId).getWidth();
			h=img.get(fId).getHeight();
		}
		setSize(K.frame.getContentPane().getWidth()-24,K.frame.getContentPane().getHeight()-104);
		lx=(int)(getWidth()-w*m)>>1;
		ly=(int)(getHeight()-h*m)>>1;
		K.top.horiz.update(0);
		K.top.vert.update(0);
		if(listenForMouse){
			for(int i=0;i<8;i++)boxes[i].updateLocation();
			K.bottom.info.w=w;
			K.bottom.info.h=h;
		}
	}
	public void removeStarter(){
		long n=files.get(fId).length();
		K.bottom.info.labels[5].setText("Size: "+((n>>60>0)?(n>>60)+"EiB":(n>>50>0)?(n>>50)+"PiB":(n>>40>0)?(n>>40)+"TiB":(n>>30>0)?(n>>30)+"GiB":(n>>20>0)?(n>>20)+"MiB":(n>>10>0)?(n>>10)+"KiB":n+"B"));
		info.setVisible(false);
		listenForMouse=true;
	}
	public void addStarter(){
		for(int i=0;i<8;i++)boxes[i].setLocation(-10,-10);
		w=100;
		h=48;
		K.bottom.info.labels[5].setText("Size: NaN");
		info.setVisible(true);
		listenForMouse=false;
		updateLocations();
	}
	public void drawPreview(Graphics2D g){
		int x=0,y=0;
		switch(bId){
			case(0)->{
				x=lx+(int)((w-K.bottom.info.w)*m);
				y=ly+(int)((h-K.bottom.info.h)*m);
			}
			case(1),(2)->{
				x=lx;
				y=ly+(int)((h-K.bottom.info.h)*m);
			}
			case(3),(5)->{
				x=lx+(int)((w-K.bottom.info.w)*m);
				y=ly;
			}
			case(4),(6),(7)->{
				x=lx;
				y=ly;
			}
		}
		g.drawRect(x,y,(int)Math.ceil(K.bottom.info.w*m)-1,(int)Math.ceil(K.bottom.info.h*m)-1);
	}
	@Override public void paint(Graphics graphics){
		Graphics2D g=(Graphics2D)graphics;
		super.paint(g);
		if(img.isEmpty())return;
		int wr=(int)(w*m/3f)>>4;
		int hr=(int)(h*m)>>5;
		g.translate(tx,ty);
		g.setClip(lx,ly,(int)(w*m),(int)(h*m));
		for(int y=0;y<hr+1;y++)for(int x=0;x<wr+1;x++)g.drawImage(background,lx+x*48,ly+(y<<5),null);
		g.setClip(-tx,-ty,getWidth(),getHeight());
		g.drawImage(img.get(fId),lx,ly,(int)(w*m),(int)(h*m),null);
		if(grid){
			float tmp=m;
			if(tmp<3)tmp=3;
			g.setColor(Color.black);

			for(int x=1;x<w/tmp*m;x++)g.drawLine((int)(x*tmp)+lx-tx,ly-ty,(int)(x*tmp)+lx-tx,(int)Math.min(h*m,h*tmp)+ly-ty);
			for(int y=1;y<h/tmp*m;y++)g.drawLine(lx-tx,(int)(y*tmp)+ly-ty,(int)Math.min(w*m,w*tmp)+lx-tx,(int)(y*tmp)+ly-ty);
		}
		g.setStroke(dash1);
		g.setColor(Color.black);
		g.drawRect(lx,ly,(int)(w*m),(int)(h*m));
		g.setStroke(dash2);
		g.setColor(Color.yellow);
		g.drawRect(lx,ly,(int)(w*m),(int)(h*m));
		g.setStroke(normal);
		g.setColor(Color.white);
		for(int i=0;i<8;i++)g.fillRect(boxes[i].getX()-tx,boxes[i].getY()-ty,5,5);
		g.setColor(Color.black);
		for(int i=0;i<8;i++)g.drawRect(boxes[i].getX()-tx,boxes[i].getY()-ty,5,5);
		BufferedImage tmpImage=new BufferedImage(w+Math.abs(lx),h+Math.abs(ly),BufferedImage.TYPE_INT_ARGB);
		Graphics2D tmp=(Graphics2D)tmpImage.getGraphics();
		if(button==1)tmp.setColor(primary);
		else if(button==3)tmp.setColor(secondary);
		int X=lx+(int)(mx*m),Y=ly+(int)(my*m),x=(int)(lx/m)+x1+tx,y=(int)(ly/m)+y1+ty;
		switch(tId){
			case(0)->{
				g.setColor(primary);
				g.fillRect(X,Y,(int)m,(int)m);
			}
			case(2)->{
				g.clipRect(X,Y,(int)m,(int)m);
				g.clearRect(0,0,getWidth(),getHeight());
				for(int iy=0;iy<=hr;iy++)for(int ix=0;ix<=wr;ix++)g.drawImage(background,ix*48,iy<<5,null);
				g.setColor(secondary);
				g.fillRect(X,Y,(int)m,(int)m);
				g.setColor(Color.black);
				g.drawRect(X,Y,(int)m-1,(int)m-1);
			}
			case(3)->{
				g.setColor(Color.black);
				g.drawRect(X,Y,(int)m,(int)m);
			}
			case(4)->{if(pressed)tmp.drawLine(x,y,(int)(lx/m)+mx+tx,(int)(ly/m)+my+ty);}
			case(5)->{if(pressed)tmp.draw(rect.execute(x,y,x2,y2));}
			case(6)->{if(pressed)tmp.fill(rect.execute(x,y,x2,y2));}
			case(7)->{if(pressed)tmp.draw(elipse.execute(x,y,x2,y2));}
			case(8)->{if(pressed)tmp.fill(elipse.execute(x,y,x2,y2));}
		}
		g.drawImage(tmpImage,-(int)(tx*m),-(int)(ty*m),(int)((w+Math.abs(lx))*m),(int)((h+Math.abs(ly))*m),null);
		if(selecting&&pressed){
			g.setStroke(dash1);
			g.setColor(Color.black);
			g.draw(rect.execute((int)(lx+x1*m),(int)(ly+y1*m),(int)((mx-x1)*m),(int)((my-y1)*m)));
			g.setStroke(dash2);
			g.setColor(Color.yellow);
			g.draw(rect.execute((int)(lx+x1*m),(int)(ly+y1*m),(int)((mx-x1)*m),(int)((my-y1)*m)));
		}
		if(selected){
			int W=(int)(K.selection.w*m),H=(int)(K.selection.h*m);
			if(doClear&&K.selection.id==fId&&!pasted){
				g.setClip((int)(K.selection.x1*m)+lx,(int)(K.selection.y1*m)+ly,W,H);
				for(y=0;y<=hr;y++)for(x=0;x<=wr;x++)g.drawImage(background,lx+x*48,ly+(y<<5),null);
			}
			g.setClip(Math.clamp(lx,0,getWidth()),Math.clamp(ly,0,getWidth()),(int)(w*m)-1,(int)(h*m)-1);
			K.selection.painter(g,(int)(K.selection.x*m)+lx,(int)(K.selection.y*m)+ly);
			g.setClip(0,0,getWidth(),getHeight());
		}
		if(bId!=8){
			g.setStroke(dash1);
			g.setColor(Color.black);
			drawPreview(g);
			g.setStroke(dash2);
			g.setColor(Color.yellow);
			drawPreview(g);
		}
		g.dispose();
	}
	@Override public void run(){while(thread.isAlive()){
		repaint();
		robot.delay(20);
	}}
	@Override public void mouseClicked(MouseEvent ev){
		if(selecting||!listenForMouse)return;
		if(newColor)K.top.colors.add();
		int x=Math.round((ev.getX()-lx-tx)/m),y=Math.round((ev.getY()-ly-ty)/m);
		button=ev.getButton();
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(tId){
			case(0)->pencil.execute(x,y,tmp,img.get(fId));
			case(1)->fill.execute(x,y,tmp);
			case(2)->eraser.execute(x,y);
			case(3)->colorExtractor.execute(x,y);
		}
	}
	@Override public void mousePressed(MouseEvent ev){
		if(!listenForMouse)return;
		if(newColor)K.top.colors.add();
		button=ev.getButton();
		pressed=true;
		x1=mx=Math.round((ev.getX()-lx-tx)/m);
		y1=my=Math.round((ev.getY()-ly-ty)/m);
	}
	@Override public void mouseReleased(MouseEvent ev){
		if(!listenForMouse)return;
		pressed=false;
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		if(selecting){
			selecting=false;
			selected=true;
			int w=mx-x1,h=my-y1;
			if(w==0||h==0)select.sel.getAction().actionPerformed(null);
			else select.execute(x1,y1,w,h,fId);
		}
		switch(tId){
			case(4)->line.execute(x1,y1,x2,y2,tmp);
			case(5)->emptyRect.execute(x1,y1,x2,y2,tmp);
			case(6)->fillRect.execute(x1,y1,x2,y2,tmp);
			case(7)->emptyEli.execute(x1,y1,x2,y2,tmp);
			case(8)->fillEli.execute(x1,y1,x2,y2,tmp);
		}
	}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){}
	@Override public void mouseDragged(MouseEvent ev){
		if(!listenForMouse||moving)return;
		int x=(int)((ev.getX()-lx-tx)/m),y=(int)((ev.getY()-ly-ty)/m);
		mx=x;
		my=y;
		if(selecting)return;
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(tId){
			case(0)->pencil.execute(x,y,tmp,img.get(fId));
			case(1)->fill.execute(x,y,tmp);
			case(2)->eraser.execute(x,y);
			case(3)->colorExtractor.execute(x,y);
			case(4),(5),(6),(7),(8)->{
				x2=x-x1;
				y2=y-y1;
			}
		}
	}
	@Override public void mouseMoved(MouseEvent ev){
		if(!listenForMouse)return;
		mx=Math.round((ev.getX()-lx-tx)/m);
		my=Math.round((ev.getY()-ly-ty)/m);
		if(mx<0||my<0||mx>w||my>h){
			K.bottom.info.labels[0].setText("x: NaN, y: NaN");
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		else if(selecting)setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		else setCursor(K.cursors[tId]);
		K.bottom.info.labels[0].setText("x: "+mx+", y: "+my);
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		if(!listenForMouse||ev.getModifiersEx()!=InputEvent.CTRL_DOWN_MASK)return;
		m=Math.clamp(m-=ev.getWheelRotation()*m/20,0.1f,15);
		K.bottom.info.labels[4].setText("zoom: "+(int)(m*100)+"%");
		updateLocations();
	}
	@Override public void componentResized(ComponentEvent ev){dispatchEvent(ev);updateLocations();}
	@Override public void componentMoved(ComponentEvent ev){}
	@Override public void componentShown(ComponentEvent ev){}
	@Override public void componentHidden(ComponentEvent ev){}
}