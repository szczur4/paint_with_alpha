package szczur4.bars;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;
public class horizontal extends JPanel implements MouseWheelListener{
	int x,mx;
	float m;
	public final JButton left=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.editor.tx+=(int)(20*K.editor.m);
		fix();
	}}),right=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.editor.tx-=(int)(20*K.editor.m);
		fix();
	}});
	public final JPanel scroller=new JPanel();
	public horizontal(){
		setLayout(null);
		setBackground(K.back);
		setBorder(K.border);
		left.setSize(16,12);
		left.setBackground(K.back);
		left.setBorder(K.border);
		left.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("/szczur4/bars/icons/left.png"))));
		right.setSize(16,12);
		right.setBackground(K.back);
		right.setBorder(K.border);
		right.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("/szczur4/bars/icons/right.png"))));
		scroller.setBackground(K.mid);
		scroller.addMouseListener(new MouseListener(){
			@Override public void mouseClicked(MouseEvent ev){}
			@Override public void mousePressed(MouseEvent ev){}
			@Override public void mouseReleased(MouseEvent ev){}
			@Override public void mouseEntered(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));}
			@Override public void mouseExited(MouseEvent ev){setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
		});
		scroller.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent ev){K.editor.tx-=(int)(ev.getX()*m-mx);update(0);update(1);}
			@Override public void mouseMoved(MouseEvent ev){mx=(int)(ev.getX()*m);}
		});
		add(left);
		add(right);
		add(scroller);
		addMouseWheelListener(this);
	}
	public void update(int k){
		fix();
		x=K.editor.lx-10;
		m=K.editor.w*K.editor.m/(getWidth()-32);
		int w=Math.min((int)((getWidth()+32)/m),getWidth()-32);
		scroller.setBounds((getWidth()-w)/2-(int)(K.editor.tx/m),1,w,10);
		switch(k){
			case(0)->K.bottom.horiz.update(-1);
			case(1)->K.top.horiz.update(-1);
		}
	}
	public void fix(){
		if(x<0)K.editor.tx=Math.clamp(K.editor.tx,x,-x);
		else K.editor.tx=0;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		K.editor.tx-=(int)(ev.getWheelRotation()*K.editor.m*5);
		update(0);
		K.editor.updateLocations();
	}
}
