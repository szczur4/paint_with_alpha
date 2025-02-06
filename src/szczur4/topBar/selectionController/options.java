package szczur4.topBar.selectionController;
import javax.swing.*;
import szczur4.K;
public class options extends JPanel{
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
		setLocation(512,19);
		add(select);
		add(flip);
		add(rotate);
		add(clipboard);
	}
}
