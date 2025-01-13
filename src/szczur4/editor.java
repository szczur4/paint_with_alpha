package szczur4;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import szczur4.scalingBoxes.corner;
import szczur4.scalingBoxes.down;
import szczur4.scalingBoxes.right;
import szczur4.tools.*;

import static java.lang.Math.ceil;

public class editor extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener,Runnable{
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
	public final right right=new right();
	public final corner corner=new corner();
	public final down down=new down();
	public int zoom=7,width=64,height=64,toolId,x1,y1,x2,y2,mouseX,mouseY,button;
	public float multiplier;
	public final float[]scales={8,7,6,5,4,3,2,1,0.5f,0.25f,0.125f};
	public boolean grid,pressed;
	public Color primary=new Color(0x0,true),secondary=new Color(0x0,true);
	public BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	public final BufferedImage background=ImageIO.read(Objects.requireNonNull(Main.class.getResource("background.png")));
	final InputMap inputMap=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	final ActionMap actionMap=getActionMap();
	File file;
	editor()throws Exception{
		robot=new Robot();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK),"save");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK),"open");
		actionMap.put("save",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			try{ImageIO.write(image,"png",file);}catch(Exception ex){System.err.println("no file found");}
			System.out.println("saved to "+file.getAbsolutePath());
		}});
		actionMap.put("open",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			Main.opener.setVisible(true);
			file=new File(Main.opener.getDirectory()+Main.opener.getFile());
			try{
				image=ImageIO.read(file);
				width=image.getWidth();
				height=image.getHeight();
				updateLocations();
				setSize((int)(width*multiplier),(int)(height*multiplier));
			}catch(Exception ex){System.err.println("no file found");}
			updateLocations();
		}});
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBounds(5,48,width,height);
		multiplier=scales[zoom];
		updateLocations();
		Main.frame.add(right);
		Main.frame.add(corner);
		Main.frame.add(down);
		thread.start();
	}
	public void updateLocations(){
		float multiplier=scales[zoom];
		right.setLocation((int)(width*multiplier)-1+5,(int)(height*multiplier/2)-3+48);
		corner.setLocation((int)(width*multiplier)-1+5,(int)(height*multiplier)-1+48);
		down.setLocation((int)(width*multiplier/2)-3+5,(int)(height*multiplier)-1+48);
		BufferedImage tmp=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		tmp.getGraphics().drawImage(image,0,0,null);
		image=tmp;
	}
	public void paint(Graphics graphics){
		Graphics2D g=(Graphics2D)graphics;
		super.paint(g);
		int wr=(int)(width*(multiplier+1)/48);
		int hr=(int)(height*(multiplier+1))>>5;
		for(int y=0;y<hr+1;y++)for(int x=0;x<wr+1;x++)g.drawImage(background,x*48,y<<5,null);
		g.drawImage(image,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
		if(grid){
			int tmp=(int)ceil(multiplier);
			if(tmp<3)tmp=4;
			g.setColor(Color.black);
			for(int x=0;x<width;x++)g.drawLine(x*tmp,0,x*tmp,height*tmp);
			for(int y=0;y<height;y++)g.drawLine(0,y*tmp,width*tmp,y*tmp);
		}
		BufferedImage tmpImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D tmp=null;
		if(pressed){
			tmp=(Graphics2D)tmpImage.getGraphics();
			if(button==1)tmp.setColor(primary);
			else if(button==3)tmp.setColor(secondary);
		}
		switch(toolId){
			case(0)->{
				g.setColor(primary);
				g.fillRect((int)(mouseX*multiplier),(int)(mouseY*multiplier),(int)multiplier,(int)multiplier);
			}
			case(2)->{
				g.clipRect((int)(mouseX*multiplier),(int)(mouseY*multiplier),(int)multiplier,(int)multiplier);
				g.clearRect(0,0,width,height);
				for(int y=0;y<=hr;y++)for(int x=0;x<=wr;x++)g.drawImage(background,x*48,y<<5,null);
				g.setColor(secondary);
				g.fillRect((int)(mouseX*multiplier),(int)(mouseY*multiplier),(int)multiplier,(int)multiplier);
				g.setColor(Color.black);
				g.drawRect((int)(mouseX*multiplier),(int)(mouseY*multiplier),(int)multiplier-1,(int)multiplier-1);
			}
			case(3)->{
				g.setColor(Color.black);
				g.drawRect((int)(mouseX*multiplier),(int)(mouseY*multiplier),(int)multiplier,(int)multiplier);
			}
			case(4)->{
				if(pressed){
					tmp.drawLine(x1,y1,mouseX,mouseY);
					g.drawImage(tmpImage,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
				}
			}
			case(5)->{
				if(pressed&&(x1!=mouseX||y1!=mouseY)){
					tmp.draw(rect.execute(x1,y1,x2,y2));
					g.drawImage(tmpImage,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
				}
			}
			case(6)->{
				if(pressed&&(x1!=mouseX||y1!=mouseY)){
					tmp.fill(rect.execute(x1,y1,x2+1,y2+1));
					g.drawImage(tmpImage,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
				}
			}
			case(7)->{
				if(pressed&&(x1!=mouseX||y1!=mouseY)){
					tmp.draw(elipse.execute(x1,y1,x2,y2));
					g.drawImage(tmpImage,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
				}
			}
			case(8)->{
				if(pressed&&(x1!=mouseX||y1!=mouseY)){
					tmp.fill(elipse.execute(x1,y1,x2+1,y2+1));
					g.drawImage(tmpImage,0,0,(int)(width*multiplier),(int)(height*multiplier),null);
				}
			}
		}
	}
	@Override public void run(){while(thread.isAlive()){
		repaint();
		robot.delay(15);
	}}
	@Override public void mouseClicked(MouseEvent ev){
		int x=(int)(ev.getX()/multiplier),y=(int)(ev.getY()/multiplier);
		button=ev.getButton();
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(0)->pencil.execute(x,y,tmp);
			case(1)->fill.execute(x,y,tmp);
			case(2)->eraser.execute(x,y);
			case(3)->colorExtractor.execute(x,y);
		}
	}
	@Override public void mousePressed(MouseEvent ev){
		button=ev.getButton();
		pressed=true;
		mouseX=(int)(ev.getX()/multiplier);
		mouseY=(int)(ev.getY()/multiplier);
		switch(toolId){
			case(4),(5),(6),(7),(8)->{
				x1=(int)(ev.getX()/multiplier);
				y1=(int)(ev.getY()/multiplier);
			}
		}
	}
	@Override public void mouseReleased(MouseEvent ev){
		pressed=false;
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(4)->line.execute(x1,y1,x2,y2);
			case(5)->emptyRect.execute(x1,y1,x2,y2,tmp);
			case(6)->fillRect.execute(x1,y1,x2,y2,tmp);
			case(7)->emptyElipse.execute(x1,y1,x2,y2,tmp);
			case(8)->fillElipse.execute(x1,y1,x2,y2,tmp);
		}
	}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){}
	@Override public void mouseDragged(MouseEvent ev){
		int x=(int)(ev.getX()/multiplier),y=(int)(ev.getY()/multiplier);
		mouseX=x;
		mouseY=y;
		Color tmp=null;
		if(button==1)tmp=primary;
		else if(button==3)tmp=secondary;
		switch(toolId){
			case(0)->pencil.execute(x,y,tmp);
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
		mouseX=(int)(ev.getX()/multiplier);
		mouseY=(int)(ev.getY()/multiplier);
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		if(ev.getModifiersEx()!=InputEvent.CTRL_DOWN_MASK)return;
		if(ev.getWheelRotation()>0)zoom++;
		else zoom--;
		zoom=Math.max(Math.min(zoom,10),0);
		multiplier=scales[zoom];
		setSize((int)(width*multiplier),(int)(height*multiplier));
		updateLocations();
	}
}
