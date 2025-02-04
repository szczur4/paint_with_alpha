package szczur4.topBar.selectionController;
import java.awt.event.*;
import javax.swing.*;
import szczur4.K;
public class options extends JPanel implements ComponentListener{
	/// tools --------------------
	final select select=new select();
	final flip flip=new flip();
	final rotate rotate=new rotate();
	final clipboard clipboard=new clipboard();
	/// --------------------------
	options(){
		setLayout(null);
		setBackground(K.back);
		setBorder(K.border);
		setLocation(476,19);
		add(select);
		add(flip);
		add(rotate);
		add(clipboard);
	}
	@Override public void componentResized(ComponentEvent e){}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}
