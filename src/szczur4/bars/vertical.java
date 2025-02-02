package szczur4.bars;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;
public class vertical extends JPanel implements MouseWheelListener{
	int y,my;
	float m;
	public final JButton up=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.editor.ty+=(int)(20*K.editor.m);
		fix();
	}}),down=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.editor.ty-=(int)(20*K.editor.m);
		fix();
	}});
	public final JPanel scroller=new JPanel();
	public vertical(){
		setLayout(null);
		setBackground(K.back);
		setBorder(K.border);
		up.setSize(12,16);
		up.setBackground(K.back);
		up.setBorder(K.border);
		up.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("/szczur4/bars/icons/up.png"))));
		down.setSize(12,16);
		down.setBackground(K.back);
		down.setBorder(K.border);
		down.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("/szczur4/bars/icons/down.png"))));
		scroller.setBackground(K.mid);
		scroller.addMouseListener(new MouseListener(){
			@Override public void mouseClicked(MouseEvent ev){}
			@Override public void mousePressed(MouseEvent ev){}
			@Override public void mouseReleased(MouseEvent ev){}
			@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));}
			@Override public void mouseExited(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
		});
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){K.editor.ty-=(int)(ev.getY()*m-my);update(0);update(1);}
			@Override public void mouseMoved(MouseEvent ev){my=(int)(ev.getY()*m);}
		});
		add(up);
		add(down);
		add(scroller);
		addMouseWheelListener(this);
	}
	public void update(int k){
		fix();
		y=K.editor.ly-10;
		m=K.editor.h*K.editor.m/(getHeight()-32);
		int h=Math.min((int)((getHeight()+32)/m),getHeight()-32);
		scroller.setBounds(1,(getHeight()-h)/2-(int)(K.editor.ty/m),10,h);
		switch(k){
			case(0)->K.bottom.vert.update(-1);
			case(1)->K.top.vert.update(-1);
		}
	}
	public void fix(){
		if(y<0)K.editor.ty=Math.clamp(K.editor.ty,y,-y);
		else K.editor.ty=0;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		K.editor.ty-=(int)(ev.getWheelRotation()*K.editor.m*5);
		update(0);
		for(int i=0;i<8;i++)K.editor.boxes[i].updateLocation();
	}
}
