package szczur4;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import szczur4.fileManager.file;
import szczur4.selectionController.select;
import szczur4.tools.*;

public class editor extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener,Runnable,ComponentListener{
	/// tools --------------------
	final fill fill=new fill();
	final pencil pencil=new pencil();
	final eraser eraser=new eraser();
	final colorExtractor colorExtractor=new colorExtractor();
	final line line=new line();
	final emptyRect emptyRect=new emptyRect();
	final fillRect fillRect=new fillRect();
	final emptyElipse emptyElipse=new emptyElipse();
	final fillElipse fillElipse=new fillElipse();
	/// --------------------------
	public Robot robot;
	public final Thread thread=new Thread(this);
	public int width=100,height=48,toolId,x1,y1,x2,y2,mouseX,mouseY,button,fileId,strokeSize=1, lX, lY,boxId=8;
	public float multiplier=1;
	public boolean grid,pressed,selecting=true,selected,listenForMouse,duplicated,moving;
	public Color primary=new Color(0x0,true),secondary=new Color(0x0,true);
	public final ArrayList<File>files=new ArrayList<>();
	public final ArrayList<BufferedImage>images=new ArrayList<>();
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
				images.add(new BufferedImage(tmpImg.getWidth(),tmpImg.getHeight(),BufferedImage.TYPE_INT_ARGB));
				images.getLast().createGraphics().drawImage(tmpImg,0,0,null);
			}catch(Exception ignored){}
			width=images.getLast().getWidth();
			height=images.getLast().getHeight();
			updateLocations();
			fileId=images.size()-1;
			K.fileCore.files.files.add(new file(fileId));
			updateLocations();
		}
		K.fileCore.updateUI();
		if(!images.isEmpty())removeStarterThings();
		else addStarterThings();
		updateLocations();
	}},saveFile=new AbstractAction("saveFile"){@Override public void actionPerformed(ActionEvent e){
		try{ImageIO.write(images.get(fileId),"png",files.get(fileId));}catch(Exception ex){System.err.println("no file found");}
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
			images.add(ImageIO.read(tmp));
			width=images.getLast().getWidth();
			height=images.getLast().getHeight();
		}catch(Exception ex){
			System.err.println("no file found");
			return;
		}
		files.add(tmp);
		fileId=images.size()-1;
		K.fileCore.files.files.add(new file(fileId));
		K.fileCore.updateUI();
		updateLocations();
	}},confirm=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(!selected)return;
		if(!duplicated)images.get(fileId).createGraphics().clearRect(K.selCore.x1,K.selCore.y1,K.selCore.w,K.selCore.h);
		images.get(fileId).createGraphics().drawImage(K.selCore.execute(),K.selCore.x,K.selCore.y,null);
		duplicated=false;
		selected=false;
		select.execute(0,0,0,0,0);
		K.selCore.setBounds(0,0,0,0);
		K.selCore.img=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
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
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_DOWN_MASK),"dupe");
		in.put(KeyStroke.getKeyStroke("ENTER"),"confirm");
		am.put("save",saveFile);
		am.put("open",openFile);
		am.put("new",saveAs);
		am.put("dupe",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){duplicated=true;}});
		am.put("confirm",confirm);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setVerticalAlignment(SwingConstants.CENTER);
		info.setForeground(K.fore);
		info.setBackground(K.back);
		info.setBounds(0,0,width,height>>1);
		openButton.setText("Open");
		openButton.setBackground(K.back);
		openButton.setForeground(K.fore);
		openButton.setBorder(K.border);
		openButton.setFocusable(false);
		openButton.setBounds(4,24,44,20);
		newButton.setText("New");
		newButton.setBackground(K.back);
		newButton.setForeground(K.fore);
		newButton.setBorder(K.border);
		newButton.setFocusable(false);
		newButton.setBounds(52,24,44,20);
		setLayout(null);
		setLocation(0,62);
		setBackground(K.back);
		add(info);
		add(openButton);
		add(newButton);
		addMouseListener(this);
		addMouseMotionListener(this);
		for(int i=0;i<8;i++){
			boxes[i]=new scalingBox(i);
			K.frame.add(boxes[i]);
		}
		thread.start();
	}
	public void resizeImage(){
		BufferedImage tmp=new BufferedImage(K.infoBar.w,K.infoBar.h,BufferedImage.TYPE_INT_ARGB);
		int x=0,y=0;
		switch(boxId){
			case(0)->{
				x=K.infoBar.w-width;
				y=K.infoBar.h-height;
			}
			case(1),(2)->y=K.infoBar.h-height;
			case(3),(5)->x=K.infoBar.w-width;
		}
		tmp.createGraphics().drawImage(images.get(fileId),x,y,null);
		images.set(fileId,tmp);
		updateLocations();
	}
	public void updateLocations(){
		if(!images.isEmpty()){
			width=images.get(fileId).getWidth();
			height=images.get(fileId).getHeight();
		}
		setSize(K.frame.getContentPane().getWidth(),K.frame.getContentPane().getHeight()-80);
		lX=(getWidth()>>1)-(int)(width*multiplier/2f);
		lY=(getHeight()>>1)-(int)(height*multiplier/2f);
		K.selCore.setBounds((int)(lX+K.selCore.x*multiplier),(int)(lY+K.selCore.y*multiplier),(int)(K.selCore.w*multiplier),(int)(K.selCore.h*multiplier));
		if(listenForMouse){
			for(int i=0;i<8;i++)boxes[i].updateLocation(width,height,multiplier);
			K.infoBar.w=width;
			K.infoBar.h=height;
		}
	}
	public void removeStarterThings(){
		info.setVisible(false);
		openButton.setVisible(false);
		newButton.setVisible(false);
		listenForMouse=true;
	}
	public void addStarterThings(){
		for(int i=0;i<8;i++)boxes[i].setLocation(-10,-10);
		width=100;
		height=48;
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
				x=lX+(int)((width-K.infoBar.w)*multiplier);
				y=lY+(int)((height-K.infoBar.h)*multiplier);
			}
			case(1),(2)->{
				x=lX;
				y=lY+(int)((height-K.infoBar.h)*multiplier);
			}
			case(3),(5)->{
				x=lX+(int)((width-K.infoBar.w)*multiplier);
				y=lY;
			}
			case(4),(6),(7)->{
				x=lX;
				y=lY;
			}
		}
		g.drawRect(x,y,(int)Math.ceil(K.infoBar.w*multiplier)-1,(int)Math.ceil(K.infoBar.h*multiplier)-1);
	}
	public void paint(Graphics graphics){
		Graphics2D g=(Graphics2D)graphics;
		super.paint(g);
		if(images.isEmpty())return;
		int wr=(int)(width*multiplier/3f)>>4;
		int hr=(int)(height*multiplier)>>5;
		g.setClip(new Rectangle(Math.max(lX,0),Math.max(lY,0),Math.clamp((int)(width*multiplier),0,getWidth()),Math.clamp((int)(height*multiplier),0,getHeight())));
		for(int y=0;y<hr+1;y++)for(int x=0;x<wr+1;x++)g.drawImage(background,lX+x*48,lY+(y<<5),null);
		g.setClip(0,0,getWidth(),getHeight());
		g.drawImage(images.get(fileId),lX,lY,(int)(width*multiplier),(int)(height*multiplier),null);
		g.setStroke(dash1);
		g.setColor(Color.black);
		g.drawRect(lX,lY,(int)(width*multiplier),(int)(height*multiplier));
		g.setStroke(dash2);
		g.setColor(Color.yellow);
		g.drawRect(lX,lY,(int)(width*multiplier),(int)(height*multiplier));
		g.setStroke(normal);
		g.setColor(Color.white);
		for(int i=0;i<8;i++)g.fillRect(boxes[i].getX(),boxes[i].getY()-61,5,5);
		g.setColor(Color.black);
		for(int i=0;i<8;i++)g.drawRect(boxes[i].getX(),boxes[i].getY()-61,5,5);
		if(grid){
			float tmp=multiplier;
			if(tmp<2)tmp=2;
			g.setColor(Color.black);
			for(int x=0;x<width;x++)g.drawLine((int)(x*tmp),0,(int)(x*tmp),(int)(height*tmp));
			for(int y=0;y<height;y++)g.drawLine(0,(int)(y*tmp),(int)(width*tmp),(int)(y*tmp));
		}
		BufferedImage tmpImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D tmp=(Graphics2D)tmpImage.getGraphics();
		if(button==1)tmp.setColor(primary);
		else if(button==3)tmp.setColor(secondary);
		int X=lX+(int)(mouseX*multiplier),Y=lY+(int)(mouseY*multiplier);
		switch(toolId){
			case(0)->{
				g.setColor(primary);
				g.fillRect(X,Y,(int)multiplier,(int)multiplier);
			}
			case(2)->{
				g.clipRect(X,Y,(int)multiplier,(int)multiplier);
				g.clearRect(0,0,getWidth(),getHeight());
				for(int y=0;y<=hr;y++)for(int x=0;x<=wr;x++)g.drawImage(background,x*48,y<<5,null);
				g.setColor(secondary);
				g.fillRect(X,Y,(int)multiplier,(int)multiplier);
				g.setColor(Color.black);
				g.drawRect(X,Y,(int)multiplier,(int)multiplier);
			}
			case(3)->{
				g.setColor(Color.black);
				g.drawRect(X,Y,(int)multiplier,(int)multiplier);
			}
			case(4)->{if(pressed)tmp.drawLine(lX+x1,lY+y1,lX+mouseX,lY+mouseY);}
			case(5)->{if(pressed)tmp.draw(rect.execute(lX+x1,lY+y1,x2,y2));}
			case(6)->{if(pressed)tmp.fill(rect.execute(lX+x1,lY+y1,x2+1,y2+1));}
			case(7)->{if(pressed)tmp.draw(elipse.execute(lX+x1,lY+y1,x2,y2));}
			case(8)->{if(pressed)tmp.fill(elipse.execute(lX+x1,lY+y1,x2+1,y2+1));}
		}
		g.drawImage(tmpImage,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
		if(selecting&&pressed){
			g.setStroke(dash1);
			g.setColor(Color.black);
			g.draw(rect.execute((int)(lX+x1*multiplier),(int)(lY+y1*multiplier),(int)((mouseX-x1)*multiplier),(int)((mouseY-y1)*multiplier)));
			g.setStroke(dash2);
			g.setColor(Color.yellow);
			g.draw(rect.execute((int)(lX+x1*multiplier),(int)(lY+y1*multiplier),(int)((mouseX-x1)*multiplier),(int)((mouseY-y1)*multiplier)));
		}
		if(selected){
			int W=(int)(K.selCore.w*multiplier),H=(int)(K.selCore.h*multiplier);
			if(!duplicated&&K.selCore.id==fileId){
				g.setClip((int)(K.selCore.x1*multiplier)+lX,(int)(K.selCore.y1*multiplier)+lY,W,H);
				for(int y=0;y<=hr;y++)for(int x=0;x<=wr;x++)g.drawImage(background,lX+x*48,lY+(y<<5),null);
			}
			g.setClip(lX+1,lY+1,(int)(width*multiplier)-1,(int)(height*multiplier)-1);
			K.selCore.painter(g,(int)(K.selCore.x*multiplier)+lX,(int)(K.selCore.y*multiplier)+lY,W-1,H-1);
			g.setClip(0,0,getWidth(),getHeight());
		}
		if(boxId!=8){
			g.setStroke(dash1);
			g.setColor(Color.black);
			drawPreview(g);
			g.setStroke(dash2);
			g.setColor(K.fore);
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
		int x=(int)((ev.getX()-lX)/multiplier),y=(int)((ev.getY()-lY)/multiplier);
		button=ev.getButton();
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(0)->pencil.execute(x,y,tmp,images.get(fileId));
			case(1)->fill.execute(x,y,tmp);
			case(2)->eraser.execute(x,y);
			case(3)->colorExtractor.execute(x,y);
		}
	}
	@Override public void mousePressed(MouseEvent ev){
		if(!listenForMouse)return;
		button=ev.getButton();
		pressed=true;
		x1=mouseX=(int)((ev.getX()-lX)/multiplier);
		y1=mouseY=(int)((ev.getY()-lY)/multiplier);
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
			if(w<0){
				x1+=w;
				w*=-1;
			}
			if(h<0){
				y1+=h;
				h*=-1;
			}
			select.execute(x1,y1,w,h,fileId);
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
		int x=(int)((ev.getX()-lX)/multiplier),y=(int)((ev.getY()-lY)/multiplier);
		mouseX=x;
		mouseY=y;
		if(selecting)return;
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(0)->pencil.execute(x,y,tmp,images.get(fileId));
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
		mouseX=(int)((ev.getX()-lX)/multiplier);
		mouseY=(int)((ev.getY()-lY)/multiplier);
		if(mouseX<0||mouseY<0||mouseX>width||mouseY>height) K.infoBar.labels[0].setText("x: NaN, y: NaN");
		else K.infoBar.labels[0].setText("x: "+mouseX+", y: "+mouseY);
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		if(!listenForMouse||ev.getModifiersEx()!=InputEvent.CTRL_DOWN_MASK)return;
		multiplier=Math.clamp(multiplier-=ev.getWheelRotation()/15f,0.1f,10);
		K.infoBar.labels[4].setText("zoom: "+(int)(multiplier*100)+"%");
		try{updateLocations();}catch(Exception ignored){}
	}
	@Override public void componentResized(ComponentEvent ev){dispatchEvent(ev);updateLocations();}
	@Override public void componentMoved(ComponentEvent ev){}
	@Override public void componentShown(ComponentEvent ev){}
	@Override public void componentHidden(ComponentEvent ev){}
}
