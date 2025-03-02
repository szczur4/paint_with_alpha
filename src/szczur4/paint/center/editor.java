package szczur4.paint.center;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import szczur4.paint.paint;
import szczur4.paint.tools.*;
import szczur4.paint.topBar.selectionController.flip;
import szczur4.paint.topBar.selectionController.select;
public class editor extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener{
	public int w=100,h=48,tId,x1,y1,x2,y2,mx,my,button,fId,strokeSize=1,lx,ly,tx,ty,bId=8;
	public float m=1;
	public boolean grid,pressed,selecting,selected,listenForMouse,doClear=true,moveSel,pasted,newColor,yChanged,xChanged;
	public Color primary=new Color(0x0,true),secondary=new Color(0x0,true);
	public final ArrayList<File>files=new ArrayList<>();
	public final ArrayList<BufferedImage>img=new ArrayList<>();
	public final scalingBox[]boxes=new scalingBox[8];
	public final BufferedImage background=ImageIO.read(Objects.requireNonNull(paint.class.getResource("background.png")));
	public final BasicStroke dash1=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[]{4,4},0),dash2=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,new float[]{4},4),normal=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	public final AbstractAction confirm=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(!selected)return;
		BufferedImage tmp=paint.selection.execute();
		img.get(fId).createGraphics().drawImage(tmp,paint.selection.x-(tmp.getWidth()-paint.selection.w)/2,paint.selection.y-(tmp.getHeight()-paint.selection.h)/2,null);
		flip.flipDir=0;
		selected=false;
		pasted=false;
		select.execute(0,0,0,0,0);
		paint.selection.setBounds(0,0,0,0);
	}};
	final JLabel info=new JLabel("No files open");
	public editor()throws Exception{
		info.setForeground(paint.fore);
		info.setBackground(paint.back);
		info.setBounds(5,0,100,20);
		info.setFont(paint.f);
		setLayout(null);
		setOpaque(false);
		setFont(paint.f);
		add(info);
		addMouseListener(this);
		addMouseMotionListener(this);
		for(int i=0;i<8;i++)boxes[i]=new scalingBox(i);
	}
	public void resizeImage(){
		BufferedImage tmp=new BufferedImage(paint.info.w,paint.info.h,BufferedImage.TYPE_INT_ARGB);
		int x=0,y=0;
		switch(bId){
			case(0)->{
				x=paint.info.w-w;
				y=paint.info.h-h;
			}
			case(1),(2)->y=paint.info.h-h;
			case(3),(5)->x=paint.info.w-w;
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
		setBounds(paint.center.horiz.x+10,paint.center.vert.y+10,(int)(w*m),(int)(h*m));
		if(paint.center.horiz.getLimit()<=0)setLocation((paint.center.fixer.getWidth()-getWidth())>>1,getY());
		if(paint.center.vert.getLimit()<=0)setLocation(getX(),(paint.center.fixer.getHeight()-getHeight())>>1);
		lx=0;
		ly=0;
		if(getX()<0)lx=-getX();
		if(getY()<0)ly=-getY();
		if(listenForMouse){
			for(int i=0;i<8;i++)boxes[i].updateLocation();
			paint.info.w=w;
			paint.info.h=h;
		}
		paint.center.repaint();
	}
	public void removeStarter(){
		long n=files.get(fId).length();
		paint.info.labels[5].setText("Size: "+((n>>60>0)?(n>>60)+"EiB":(n>>50>0)?(n>>50)+"PiB":(n>>40>0)?(n>>40)+"TiB":(n>>30>0)?(n>>30)+"GiB":(n>>20>0)?(n>>20)+"MiB":(n>>10>0)?(n>>10)+"KiB":n+"B"));
		info.setVisible(false);
		listenForMouse=true;
		for(int i=0;i<8;i++) paint.center.fixer.add(boxes[i]);
	}
	public void addStarter(){
		for(int i=0;i<8;i++)boxes[i].setLocation(-10,-10);
		m=1;
		w=100;
		h=20;
		paint.info.labels[5].setText("Size: NaN");
		info.setVisible(true);
		listenForMouse=false;
		updateLocations();
	}
	public void drawPreview(Graphics2D g){
		int x=0,y=0;
		switch(bId){
			case(0)->{
				x=(int)((w-paint.info.w)*m);
				y=(int)((h-paint.info.h)*m);
			}
			case(1),(2)->y=(int)((h-paint.info.h)*m);
			case(3),(5)->x=(int)((w-paint.info.w)*m);
		}
		g.drawRect(x,y,(int)Math.ceil(paint.info.w*m)-1,(int)Math.ceil(paint.info.h*m)-1);
	}
	@Override public void paint(Graphics graphics){
		int W=(int)(w*m),H=(int)(h*m);
		Graphics2D g=(Graphics2D)graphics;
		super.paint(g);
		if(img.isEmpty())return;
		int wr=(int)(w*m/3f)>>4,hr=H>>5;
		g.clipRect(lx,ly,paint.center.fixer.getWidth(),paint.center.fixer.getHeight());
		for(int y=0;y<hr+1;y++)for(int x=0;x<wr+1;x++)g.drawImage(background,x*48,(y<<5),null);
		g.drawImage(img.get(fId),0,0,W,H,null);
		if(grid){
			float tmp=m;
			if(tmp<3)tmp=3;
			g.setColor(Color.black);
			for(int x=1;x<w/tmp*m;x++)g.drawLine((int)(x*tmp),0,(int)(x*tmp),(int)Math.min(h*m,h*tmp));
			for(int y=1;y<h/tmp*m;y++)g.drawLine(0,(int)(y*tmp),(int)Math.min(w*m,w*tmp),(int)(y*tmp));
		}
		g.setStroke(dash1);
		g.setColor(Color.black);
		g.drawRect(0,0,W-1,H-1);
		g.setStroke(dash2);
		g.setColor(Color.yellow);
		g.drawRect(0,0,W-1,H-1);
		g.setStroke(normal);
		BufferedImage tmpImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D tmp=(Graphics2D)tmpImage.getGraphics();
		if(button==1)tmp.setColor(primary);
		else if(button==3)tmp.setColor(secondary);
		int X=(int)(mx*m),Y=(int)(my*m);
		if(selected){
			W=(int)(paint.selection.w*m);
			H=(int)(paint.selection.h*m);
			if(doClear&&paint.selection.id==fId&&!pasted){
				g.setClip(Math.max((int)(paint.selection.x1*m),lx),Math.max((int)(paint.selection.y1*m),ly),Math.min(W,paint.center.fixer.getWidth()),Math.min(H,paint.center.fixer.getHeight()));
				for(int y=0;y<=hr;y++)for(int x=0;x<=wr;x++)g.drawImage(background,x*48,(y<<5),null);
			}
			g.setClip(lx,ly,paint.center.fixer.getWidth(),paint.center.fixer.getHeight());
			paint.selection.painter(g,(int)(paint.selection.x*m)+1,(int)(paint.selection.y*m)+1);
		}
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
			case(4)->{if(pressed)tmp.drawLine(x1,y1,mx,my);}
			case(5)->{if(pressed)tmp.draw(rect.execute(x1,y1,x2,y2));}
			case(6)->{if(pressed)tmp.fill(rect.execute(x1,y1,x2,y2));}
			case(7)->{if(pressed)tmp.draw(elipse.execute(x1,y1,x2,y2));}
			case(8)->{if(pressed)tmp.fill(elipse.execute(x1,y1,x2,y2));}
		}
		g.drawImage(tmpImage,0,0,(int)((w)*m),(int)((h)*m),null);
		if(selecting&&pressed){
			g.setStroke(dash1);
			g.setColor(Color.black);
			g.draw(rect.execute((int)(x1*m),(int)(y1*m),(int)((mx-x1)*m),(int)((my-y1)*m)));
			g.setStroke(dash2);
			g.setColor(Color.yellow);
			g.draw(rect.execute((int)(x1*m),(int)(y1*m),(int)((mx-x1)*m),(int)((my-y1)*m)));
		}
		if(bId!=8){
			g.setStroke(dash1);
			g.setColor(Color.black);
			drawPreview(g);
			g.setStroke(dash2);
			g.setColor(Color.yellow);
			drawPreview(g);
		}
		g.setColor(paint.fore);
		g.dispose();
	}
	@Override public void mouseClicked(MouseEvent ev){
		if(selecting||!listenForMouse)return;
		if(newColor) paint.top.colors.add();
		int x=Math.round(ev.getX()/m-0.5f),y=Math.round(ev.getY()/m-0.5f);
		button=ev.getButton();
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(tId){
			case(0)-> pencil.execute(x,y,tmp,img.get(fId));
			case(1)-> fill.execute(x,y,tmp);
			case(2)-> eraser.execute(x,y);
			case(3)->colorExtractor.execute(x,y);
		}
		repaint();
	}
	@Override public void mousePressed(MouseEvent ev){
		if(!listenForMouse)return;
		if(newColor) paint.top.colors.add();
		button=ev.getButton();
		pressed=true;
		x1=mx=Math.round(ev.getX()/m-0.5f);
		y1=my=Math.round(ev.getY()/m-0.5f);
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
		repaint();
	}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
	@Override public void mouseDragged(MouseEvent ev){
		if(!listenForMouse||moveSel)return;
		int x=(int)(ev.getX()/m),y=(int)(ev.getY()/m),X=ev.getX()+10,Y=ev.getY()+10;
		if(ev.getModifiersEx()-1024==InputEvent.CTRL_DOWN_MASK||ev.getModifiersEx()-2048==InputEvent.CTRL_DOWN_MASK||ev.getModifiersEx()-4096==InputEvent.CTRL_DOWN_MASK){
			paint.center.horiz.x+=X-tx;
			paint.center.vert.y+=Y-ty;
			tx=X;
			ty=Y;
			paint.center.horiz.update();
			paint.center.vert.update();
			xChanged=true;
			yChanged=true;
			return;
		}
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
		repaint();
	}
	@Override public void mouseMoved(MouseEvent ev){
		if(!listenForMouse)return;
		tx=ev.getX()+10;
		ty=ev.getY()+10;
		mx=Math.round(ev.getX()/m-0.5f);
		my=Math.round(ev.getY()/m-0.5f);
		if(ev.getModifiersEx()==InputEvent.CTRL_DOWN_MASK)setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		else setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		if(mx<0||my<0||mx>w||my>h){
			paint.info.labels[0].setText("x: NaN, y: NaN");
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		else if(selecting)setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		paint.info.labels[0].setText("x: "+mx+", y: "+my);
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		if(!listenForMouse)return;
		switch(ev.getModifiersEx()){
			case(InputEvent.CTRL_DOWN_MASK)->{
				m=Math.clamp(m-=ev.getWheelRotation()*m/15,0.1f,15);
				paint.info.labels[4].setText("zoom: "+(int)(m*100)+"%");
				float M=(float)paint.center.horiz.x/paint.center.horiz.getLimit();
				paint.center.horiz.setLimit(-(int)(paint.center.fixer.getWidth()-w*m-20));
				paint.center.horiz.x=(int)(M*paint.center.horiz.getLimit());
				M=(float)paint.center.vert.y/paint.center.vert.getLimit();
				paint.center.vert.setLimit(-(int)(paint.center.fixer.getHeight()-h*m-20));
				paint.center.vert.y=(int)(M*paint.center.vert.getLimit());
				if(!xChanged) paint.center.horiz.x=-paint.center.horiz.getLimit()>>1;
				if(!yChanged) paint.center.vert.y=-paint.center.vert.getLimit()>>1;
				if(paint.center.fixer.getWidth()>getWidth()+20)xChanged=false;
				if(paint.center.fixer.getHeight()>getHeight()+20)yChanged=false;
				paint.center.horiz.update();
				paint.center.vert.update();
			}
			case(InputEvent.SHIFT_DOWN_MASK)->{
				paint.center.horiz.x-=ev.getWheelRotation()<<5;
				xChanged=true;
				paint.center.horiz.update();
			}
			default->{
				paint.center.vert.y-=ev.getWheelRotation()<<5;
				yChanged=true;
				paint.center.vert.update();
			}
		}
	}
}