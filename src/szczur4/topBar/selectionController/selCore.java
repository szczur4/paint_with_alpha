package szczur4.topBar.selectionController;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.K;
public class selCore extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener{
	public int x,y,w,h,x1,y1,id,mx,my;
	public BufferedImage img;
	final AbstractAction all=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){select.execute(0,0,K.editor.w,K.editor.h,K.editor.fId);}},left=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){fix(0);}},right=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){fix(1);}},up=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){fix(2);}},down=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){fix(3);}};
	final InputMap in=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	final ActionMap am=getActionMap();
	public final options options=new options();
	public selCore(){
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK),"all");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK),"copy");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK),"cut");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK),"paste");
		in.put(KeyStroke.getKeyStroke("LEFT"),"LEFT");
		in.put(KeyStroke.getKeyStroke("RIGHT"),"RIGHT");
		in.put(KeyStroke.getKeyStroke("UP"),"UP");
		in.put(KeyStroke.getKeyStroke("DOWN"),"DOWN");
		am.put("all",all);
		am.put("LEFT",left);
		am.put("RIGHT",right);
		am.put("UP",up);
		am.put("DOWN",down);
		K.top.add(options);
		setOpaque(false);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}
	public void painter(Graphics2D g,int x,int y){
		float m=K.editor.m;
		BufferedImage tmp=img;
		tmp=flip.execute(tmp);
		tmp=rotate.execute(tmp);
		g.translate(x+w*m/2-1,y+h*m/2-1);
		g.setClip(-(int)(x+w*m/2-1),-(int)(y+h*m/2-1),K.editor.getWidth()+(int)(x+w*m/2-1)<<1,K.editor.getHeight()+(int)(y+h*m/2-1)<<1);
		g.drawImage(tmp,-(int)(tmp.getWidth()*m/2),-(int)(tmp.getHeight()*m/2),(int)(tmp.getWidth()*m),(int)(tmp.getHeight()*m),null);
		g.setStroke(K.editor.dash1);
		g.setColor(Color.black);
		g.drawRect(-(int)(tmp.getWidth()*m/2),-(int)(tmp.getHeight()*m/2),(int)(tmp.getWidth()*m),(int)(tmp.getHeight()*m));
		g.setStroke(K.editor.dash2);
		g.setColor(Color.yellow);
		g.drawRect(-(int)(tmp.getWidth()*m/2),-(int)(tmp.getHeight()*m/2),(int)(tmp.getWidth()*m),(int)(tmp.getHeight()*m));
		setBounds((int)(x+w*m/2+K.editor.tx-tmp.getWidth()*m/2),(int)(y+h*m/2+K.editor.ty-tmp.getHeight()*m/2),(int)(tmp.getWidth()*m),(int)(tmp.getHeight()*m));
	}
	public BufferedImage execute(){
		BufferedImage tmp=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		tmp.getGraphics().drawImage(img,0,0,null);
		if(K.editor.doClear&&!K.editor.pasted){
			Graphics2D g=K.editor.img.get(K.editor.fId).createGraphics();
			g.setBackground(new Color(0x0,true));
			g.clearRect(x1,y1,w,h);
			g.dispose();
		}
		tmp=flip.execute(tmp);
		tmp=rotate.execute(tmp);
		return tmp;
	}
	void fix(int id){
		switch(id){
			case(0)->x--;
			case(1)->x++;
			case(2)->y--;
			case(3)->y++;
		}
		x=Math.clamp(x,-w,K.editor.w);
		y=Math.clamp(y,-h,K.editor.h);
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){}
	@Override public void mouseReleased(MouseEvent ev){}
	@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));}
	@Override public void mouseExited(MouseEvent ev){K.editor.moving=false;}
	@Override public void mouseDragged(MouseEvent ev){
		x+=(int)(ev.getX()/K.editor.m-mx);
		y+=(int)(ev.getY()/K.editor.m-my);
		fix(-1);
		setLocation((int)(x*K.editor.m+K.editor.lx),(int)(y*K.editor.m+K.editor.ly));
	}
	@Override public void mouseMoved(MouseEvent ev){
		K.editor.moving=true;
		mx=(int)(ev.getX()/K.editor.m);
		my=(int)(ev.getY()/K.editor.m);
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		if(ev.getModifiersEx()==InputEvent.CTRL_DOWN_MASK)return;
		if(ev.getModifiersEx()==InputEvent.SHIFT_DOWN_MASK)rotate.rotation+=ev.getWheelRotation();
		else rotate.rotation+=ev.getWheelRotation()*5;
	}
}
