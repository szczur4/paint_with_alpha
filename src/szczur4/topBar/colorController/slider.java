package szczur4.topBar.colorController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.K;
public class slider extends JPanel implements MouseWheelListener{
	public int v;
	int x;
	private final JLabel vl=new JLabel();
	public slider(int n){
		v=n;
		fix();
		vl.setSize(26,16);
		vl.setBackground(K.mid);
		vl.setForeground(K.fore);
		vl.setBorder(new CompoundBorder(K.border,new EmptyBorder(1,1,1,1)));
		vl.setFont(K.f);
		vl.setOpaque(true);
		vl.addMouseListener(new MouseListener(){
			@Override public void mouseClicked(MouseEvent e){}
			@Override public void mousePressed(MouseEvent e){vl.setBackground(K.mid.darker());}
			@Override public void mouseReleased(MouseEvent e){vl.setBackground(K.mid);}
			@Override public void mouseEntered(MouseEvent e){}
			@Override public void mouseExited(MouseEvent e){}
		});
		vl.addMouseMotionListener(new MouseMotionListener(){
			@Override public void mouseDragged(MouseEvent e){
				v=Math.clamp(v+e.getX()-x,0,255);
				fix();
				repaint();
				K.top.colors.set();
				K.editor.newColor=true;
			}
			@Override public void mouseMoved(MouseEvent e){x=e.getX();}
		});
		add(vl);
		setLayout(null);
		setSize(77,16);
		setBackground(K.back);
		setForeground(K.fore);
		setBorder(K.border);
		addMouseWheelListener(this);
	}
	public int value(){return v;}
	public void fix(){
		vl.setText(v+"");
		vl.setLocation(v*51/255,0);
	}
	@Override public void paint(Graphics gr){
		Graphics2D g=(Graphics2D)gr;
		super.paint(g);
		g.setColor(K.mid.brighter());
		for(int i=1;i<19;i++)g.drawLine(i<<2,4,i<<2,12);
		paintChildren(g);
		g.dispose();
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		v=Math.clamp(v+ev.getWheelRotation(),0,255);
		fix();
		repaint();
		K.top.colors.set();
		K.editor.newColor=true;
	}
}
