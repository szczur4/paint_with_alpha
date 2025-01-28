package szczur4.selectionController;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.K;

public class selCore extends JPanel implements MouseListener,MouseMotionListener{
	Robot robot;
	public int x,y,w,h,x1,y1,id,mx,my;
	public BufferedImage img;
	final AbstractAction all=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){select.execute(0,0,K.editor.width,K.editor.height,K.editor.fileId);}}, left=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){x--;fix();}},right=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){x++;fix();}},up=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){y--;fix();}},down=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){y++;fix();}};
	final InputMap in=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	final ActionMap am=getActionMap();
	final options options=new options();
	public selCore()throws Exception{
		robot=new Robot();
		String[]s={"LEFT","RIGHT","UP","DOWN"};
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK),"all");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK),"copy");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK),"cut");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK),"paste");
		for(int i=0;i<4;i++)in.put(KeyStroke.getKeyStroke(s[i]),s[i]);
		am.put("all",all);
		am.put("LEFT",left);
		am.put("RIGHT",right);
		am.put("UP",up);
		am.put("DOWN",down);
		K.frame.add(options);
		setOpaque(false);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void painter(Graphics2D g,int x,int y,int w,int h){
		flip.painter(g);
		g.setStroke(K.editor.dash1);
		g.setColor(Color.black);
		g.drawRect(x,y,w,h);
		g.setStroke(K.editor.dash2);
		g.setColor(Color.yellow);
		g.drawRect(x,y,w,h);
	}
	public BufferedImage execute(){
		BufferedImage tmp=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		tmp.getGraphics().drawImage(img,0,0,null);
		Graphics2D g=tmp.createGraphics();
		flip.execute(g);
		return tmp;
	}
	void fix(){
		x=Math.clamp(x,-w,K.editor.width);
		y=Math.clamp(y,-h,K.editor.height);
	}
	@Override public void mouseClicked(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){}
	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mouseEntered(MouseEvent e){setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));}
	@Override public void mouseExited(MouseEvent e){K.editor.moving=false;}
	@Override public void mouseDragged(MouseEvent e){
		x+=(int)(e.getX()/K.editor.multiplier-mx);
		y+=(int)(e.getY()/K.editor.multiplier-my);
		fix();
		setLocation((int)(x*K.editor.multiplier+K.editor.lX),(int)(y*K.editor.multiplier+K.editor.lY));
	}
	@Override public void mouseMoved(MouseEvent e){
		K.editor.moving=true;
		mx=(int)(e.getX()/K.editor.multiplier);
		my=(int)(e.getY()/K.editor.multiplier);
	}
}
