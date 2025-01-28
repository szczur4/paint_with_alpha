package szczur4.selectionController;

import javax.swing.*;
import szczur4.K;

public class options extends JPanel{
	/// tools --------------------
	final flip flip=new flip();
	/// --------------------------
	options(){
		setLayout(null);
		setBackground(K.back);
		setBorder(K.border);
		setBounds(412,19,150,43);
		add(flip);
	}
}
