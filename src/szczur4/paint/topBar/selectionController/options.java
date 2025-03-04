package szczur4.paint.topBar.selectionController;
import javax.swing.*;
import szczur4.paint.paint;
public class options extends JPanel{
	/// tools --------------------
	final select select=new select();
	final flip flip=new flip();
	final rotate rotate=new rotate();
	final clipboard clipboard=new clipboard();
	/// --------------------------
	options(){
		setLayout(null);
		setBackground(paint.back);
		setBorder(paint.border);
		setLocation(512,0);
		add(select);
		add(flip);
		add(rotate);
		add(clipboard);
	}
}
