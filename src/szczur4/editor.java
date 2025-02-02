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
import szczur4.topBar.fileManager.file;
import szczur4.topBar.selectionController.delete;
import szczur4.topBar.selectionController.flip;
import szczur4.topBar.selectionController.select;
public class editor extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener,Runnable,ComponentListener{
	public Robot robot;
	public final Thread thread=new Thread(this);
	public int w=100,h=48,toolId,x1,y1,x2,y2,mouseX,mouseY,button,fileId,strokeSize=1, lx, ly, tx, ty,boxId=8;
	public float m=1;
	public boolean grid,pressed,selecting,selected,listenForMouse,doClear=true,moving,pasted;
	public Color primary=new Color(0x0,true),secondary=new Color(0x0,true);
	public final ArrayList<File>files=new ArrayList<>();
	public final ArrayList<BufferedImage>img=new ArrayList<>();
	public final scalingBox[]boxes=new scalingBox[8];
	public final BufferedImage background=ImageIO.read(Objects.requireNonNull(K.class.getResource("background.png")));
	public final BasicStroke dash1=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0,new float[]{4,4},0),dash2=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,new float[]{4},4),normal=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	public final AbstractAction openFile=new AbstractAction("openFile"){@Override public void actionPerformed(ActionEvent e){
		K.opener.setVisible(true);
		File[]tmp=K.opener.getFiles();
		if(tmp.length==0)return;
		for(File file:tmp){
			if(!file.exists()){
				System.err.println(file.getName()+" does not exist");
				continue;
			}
			if(!file.getName().substring(file.getName().lastIndexOf(".")+1).equalsIgnoreCase("png")){
				System.err.println("wrong extension");
				continue;
			}
			files.add(file);
			try{
				BufferedImage tmpImg=ImageIO.read(files.getLast());
				img.add(new BufferedImage(tmpImg.getWidth(),tmpImg.getHeight(),BufferedImage.TYPE_INT_ARGB));
				img.getLast().createGraphics().drawImage(tmpImg,0,0,null);
			}catch(Exception ignored){}
			w=img.getLast().getWidth();
			h=img.getLast().getHeight();
			updateLocations();
			fileId=img.size()-1;
			K.top.files.files.files.add(new file(fileId));
			updateLocations();
		}
		K.top.files.updateUI();
		if(!img.isEmpty()) removeStarter();
		else addStarter();
		updateLocations();
	}},saveFile=new AbstractAction("saveFile"){@Override public void actionPerformed(ActionEvent e){
		try{ImageIO.write(img.get(fileId),"png",files.get(fileId));}catch(Exception ex){System.err.println("no file found");}
		System.out.println("saved to "+files.get(fileId).getAbsolutePath());
	}},saveAs=new AbstractAction("saveAs"){@Override public void actionPerformed(ActionEvent e){
		K.saver.setVisible(true);
		File tmp=K.saver.getFiles()[0];
		try{
			String s=tmp.getAbsolutePath();
			if(!s.startsWith(".png",s.length()-4))tmp=new File(s+".png");
			if(tmp.createNewFile())System.out.println("created: "+tmp.getAbsolutePath());
			BufferedImage tmpImg=new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
			ImageIO.write(tmpImg,"png",tmp);
			img.add(ImageIO.read(tmp));
			w=img.getLast().getWidth();
			h=img.getLast().getHeight();
		}catch(Exception ex){
			System.err.println("no file found");
			return;
		}
		files.add(tmp);
		fileId=img.size()-1;
		K.top.files.files.files.add(new file(fileId));
		K.top.files.updateUI();
		removeStarter();
		updateLocations();
	}},confirm=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(!selected)return;
		BufferedImage tmp=K.selection.execute();
		img.get(fileId).createGraphics().drawImage(tmp,K.selection.x-(tmp.getWidth()-K.selection.w)/2,K.selection.y-(tmp.getHeight()-K.selection.h)/2,null);
		flip.flipDir=0;
		selected=false;
		pasted=false;
		select.execute(0,0,0,0,0);
		K.selection.setBounds(0,0,0,0);
	}};
	final JButton openButton=new JButton(openFile),newButton=new JButton(saveAs);
	final JLabel info=new JLabel("No files open");
	final InputMap in=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	final ActionMap am=getActionMap();
	editor()throws Exception{
		robot=new Robot();
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK),"save");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK),"open");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK),"new");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_DOWN_MASK),"toggleClear");
		in.put(KeyStroke.getKeyStroke("ENTER"),"confirm");
		in.put(KeyStroke.getKeyStroke("DELETE"),"del");
		am.put("save",saveFile);
		am.put("open",openFile);
		am.put("new",saveAs);
		am.put("toggleClear",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){select.clear.getAction().actionPerformed(null);}});
		am.put("confirm",confirm);
		am.put("del",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){if(selected)delete.execute(fileId);}});
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setVerticalAlignment(SwingConstants.CENTER);
		info.setForeground(K.fore);
		info.setBackground(K.back);
		info.setBounds(0,0,w,h>>1);
		info.setFont(K.f);
		setLayout(null);
		setLocation(12,73);
		setBackground(K.back);
		setFont(K.f);
		add(info);
		add(openButton);
		add(newButton);
		addMouseListener(this);
		addMouseMotionListener(this);
		for(int i=0;i<8;i++){
			boxes[i]=new scalingBox(i);
			add(boxes[i]);
		}
		thread.start();
	}
	public void resizeImage(){
		BufferedImage tmp=new BufferedImage(K.bottom.infoBar.w,K.bottom.infoBar.h,BufferedImage.TYPE_INT_ARGB);
		int x=0,y=0;
		switch(boxId){
			case(0)->{
				x=K.bottom.infoBar.w-w;
				y=K.bottom.infoBar.h-h;
			}
			case(1),(2)->y=K.bottom.infoBar.h-h;
			case(3),(5)->x=K.bottom.infoBar.w-w;
		}
		tmp.createGraphics().drawImage(img.get(fileId),x,y,null);
		img.set(fileId,tmp);
		updateLocations();
	}
	public void updateLocations(){
		if(!img.isEmpty()){
			w=img.get(fileId).getWidth();
			h=img.get(fileId).getHeight();
		}
		setSize(K.frame.getContentPane().getWidth()-24,K.frame.getContentPane().getHeight()-104);
		lx=(int)(getWidth()-w*m)>>1;
		ly=(int)(getHeight()-h*m)>>1;
		K.top.horiz.update(0);
		K.top.vert.update(0);
		K.selection.setBounds((int)(lx+K.selection.x*m),(int)(ly+K.selection.y*m),(int)(K.selection.w*m),(int)(K.selection.h*m));
		if(listenForMouse){
			for(int i=0;i<8;i++)boxes[i].updateLocation();
			K.bottom.infoBar.w=w;
			K.bottom.infoBar.h=h;
		}
	}
	public void removeStarter(){
		info.setVisible(false);
		openButton.setVisible(false);
		newButton.setVisible(false);
		listenForMouse=true;
	}
	public void addStarter(){
		for(int i=0;i<8;i++)boxes[i].setLocation(-10,-10);
		w=100;
		h=48;
		info.setVisible(true);
		openButton.setVisible(true);
		newButton.setVisible(true);
		listenForMouse=false;
		updateLocations();
	}
	public void drawPreview(Graphics2D g){
		int x=0,y=0;
		switch(boxId){
			case(0)->{
				x=lx+(int)((w-K.bottom.infoBar.w)*m);
				y=ly+(int)((h-K.bottom.infoBar.h)*m);
			}
			case(1),(2)->{
				x=lx;
				y=ly+(int)((h-K.bottom.infoBar.h)*m);
			}
			case(3),(5)->{
				x=lx+(int)((w-K.bottom.infoBar.w)*m);
				y=ly;
			}
			case(4),(6),(7)->{
				x=lx;
				y=ly;
			}
		}
		g.drawRect(x,y,(int)Math.ceil(K.bottom.infoBar.w*m)-1,(int)Math.ceil(K.bottom.infoBar.h*m)-1);
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
		g.drawImage(img.get(fileId),lx,ly,(int)(w*m),(int)(h*m),null);
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
		if(grid){
			float tmp=m;
			if(tmp<3)tmp=3;
			g.setColor(Color.black);
			for(int x=0;x<w;x++)g.drawLine((int)(x*tmp),0,(int)(x*tmp),(int)(h*tmp));
			for(int y=0;y<h;y++)g.drawLine(0,(int)(y*tmp),(int)(w*tmp),(int)(y*tmp));
		}
		BufferedImage tmpImage=new BufferedImage(w+Math.abs(lx),h+Math.abs(ly),BufferedImage.TYPE_INT_ARGB);
		Graphics2D tmp=(Graphics2D)tmpImage.getGraphics();
		if(button==1)tmp.setColor(primary);
		else if(button==3)tmp.setColor(secondary);
		int X=lx+(int)(mouseX*m),Y=ly+(int)(mouseY*m),x=(int)(lx/m+x1),y=(int)(ly/m+y1);
		switch(toolId){
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
				g.drawRect(X,Y,(int)m,(int)m);
			}
			case(3)->{
				g.setColor(Color.black);
				g.drawRect(X,Y,(int)m,(int)m);
			}
			case(4)->{if(pressed)tmp.drawLine(x,y,(int)(lx/m+mouseX),(int)(ly/m+mouseY));}
			case(5)->{if(pressed)tmp.draw(rect.execute(x,y,x2,y2));}
			case(6)->{if(pressed)tmp.fill(rect.execute(x,y,x2+1,y2+1));}
			case(7)->{if(pressed)tmp.draw(elipse.execute(x,y,x2,y2));}
			case(8)->{if(pressed)tmp.fill(elipse.execute(x,y,x2+1,y2+1));}
		}
		g.drawImage(tmpImage,0,0,(int)((w+Math.abs(lx))*m),(int)((h+Math.abs(ly))*m),null);
		if(selecting&&pressed){
			g.setStroke(dash1);
			g.setColor(Color.black);
			g.draw(rect.execute((int)(lx+x1*m),(int)(ly+y1*m),(int)((mouseX-x1)*m),(int)((mouseY-y1)*m)));
			g.setStroke(dash2);
			g.setColor(Color.yellow);
			g.draw(rect.execute((int)(lx+x1*m),(int)(ly+y1*m),(int)((mouseX-x1)*m),(int)((mouseY-y1)*m)));
		}
		if(selected){
			int W=(int)(K.selection.w*m),H=(int)(K.selection.h*m);
			if(doClear&&K.selection.id==fileId&&!pasted){
				g.setClip((int)(K.selection.x1*m)+lx,(int)(K.selection.y1*m)+ly,W,H);
				for(y=0;y<=hr;y++)for(x=0;x<=wr;x++)g.drawImage(background,lx+x*48,ly+(y<<5),null);
			}
			g.setClip(Math.clamp(lx,0,getWidth()),Math.clamp(ly,0,getWidth()),(int)(w*m)-1,(int)(h*m)-1);
			K.selection.painter(g,(int)(K.selection.x*m)+lx,(int)(K.selection.y*m)+ly);
			g.setClip(0,0,getWidth(),getHeight());
		}
		if(boxId!=8){
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
		Thread t=new Thread(this::repaint);
		t.start();
		robot.delay(20);
	}}
	@Override public void mouseClicked(MouseEvent ev){
		if(selecting||!listenForMouse)return;
		int x=(int)((ev.getX()-lx)/m),y=(int)((ev.getY()-ly)/m);
		button=ev.getButton();
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(0)->pencil.execute(x,y,tmp,img.get(fileId));
			case(1)->fill.execute(x,y,tmp);
			case(2)->eraser.execute(x,y);
			case(3)->colorExtractor.execute(x,y);
		}
	}
	@Override public void mousePressed(MouseEvent ev){
		if(!listenForMouse)return;
		button=ev.getButton();
		pressed=true;
		x1=mouseX=(int)((ev.getX()-lx)/m);
		y1=mouseY=(int)((ev.getY()-ly)/m);
		x1=Math.clamp(x1,0,w);
		y1=Math.clamp(y1,0,h);
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
			int w=mouseX-x1,h=mouseY-y1;
			w=Math.clamp(w,-x1,this.w-x1);
			h=Math.clamp(h,-y1,this.h-y1);
			if(w==0||h==0)select.sel.getAction().actionPerformed(null);
			else select.execute(x1,y1,w,h,fileId);
		}
		switch(toolId){
			case(4)->line.execute(x1,y1,x2,y2,tmp);
			case(5)->emptyRect.execute(x1,y1,x2,y2,tmp);
			case(6)->fillRect.execute(x1,y1,x2,y2,tmp);
			case(7)->emptyElipse.execute(x1,y1,x2,y2,tmp);
			case(8)->fillElipse.execute(x1,y1,x2,y2,tmp);
		}
	}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){}
	@Override public void mouseDragged(MouseEvent ev){
		if(!listenForMouse||moving)return;
		int x=(int)((ev.getX()-lx)/m),y=(int)((ev.getY()-ly)/m);
		mouseX=x;
		mouseY=y;
		if(selecting)return;
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(0)->pencil.execute(x,y,tmp,img.get(fileId));
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
		mouseX=(int)((ev.getX()-lx+m/4)/m);
		mouseY=(int)((ev.getY()-ly+m/4)/m);
		if(mouseX<0||mouseY<0||mouseX>w||mouseY>h){
			K.bottom.infoBar.labels[0].setText("x: NaN, y: NaN");
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else{
			K.bottom.infoBar.labels[0].setText("x: "+mouseX+", y: "+mouseY);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		if(!listenForMouse||ev.getModifiersEx()!=InputEvent.CTRL_DOWN_MASK)return;
		m=Math.clamp(m-=ev.getWheelRotation()*m/20,0.1f,10);
		K.bottom.infoBar.labels[4].setText("zoom: "+(int)(m*100)+"%");
		try{updateLocations();}catch(Exception ignored){}
	}
	@Override public void componentResized(ComponentEvent ev){dispatchEvent(ev);updateLocations();}
	@Override public void componentMoved(ComponentEvent ev){}
	@Override public void componentShown(ComponentEvent ev){}
	@Override public void componentHidden(ComponentEvent ev){}
}