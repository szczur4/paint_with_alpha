package szczur4.selectionController;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.Main;

public class selection extends JPanel{
	/// tools --------------------
	public select select=new select();
	flip flip=new flip();
	/// --------------------------
	Robot robot;
	public int x,y,w,h,x1,y1;
	public BufferedImage image=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
	AbstractAction all=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){image=Main.editor.images.get(Main.editor.fileId);}},
			copy,
			cut,
			paste,
			left=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){x--;fix();}},right=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){x++;fix();}},up=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){y--;fix();}},down=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){y++;fix();}};
	InputMap in=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	ActionMap am=getActionMap();
	public selection()throws Exception{
		robot=new Robot();
		String[]s={"LEFT","RIGHT","UP","DOWN","A","D","W","S"};
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_DOWN_MASK),"all");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK),"copy");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK),"cut");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK),"paste");
		for(int i=0;i<8;i++)in.put(KeyStroke.getKeyStroke(s[i]),s[i%4]);
		am.put("LEFT",left);
		am.put("RIGHT",right);
		am.put("UP",up);
		am.put("DOWN",down);
		am.put("all",left);
		am.put("copy",right);
		am.put("cut",up);
		am.put("paste",down);
		setLayout(null);
		setBackground(Main.back);
		setBorder(Main.border);
		setBounds(412,19,150,43);
		add(flip);
	}
	void fix(){
		x=Math.clamp(x,-w,Main.editor.width);
		y=Math.clamp(y,-h,Main.editor.height);
	}
}
