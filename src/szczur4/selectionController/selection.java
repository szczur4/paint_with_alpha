package szczur4.selectionController;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.Main;

public class selection extends JPanel{
	/// tools --------------------
	public final select select=new select();
	final flip flip=new flip();
	/// --------------------------
	Robot robot;
	public int x,y,w,h,x1,y1,id;
	public BufferedImage image;
	final AbstractAction all=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){select.execute(0,0,Main.editor.width,Main.editor.height,Main.editor.fileId);}},
			copy=null,
			paste=null,
			left=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){x--;fix();}},right=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){x++;fix();}},up=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){y--;fix();}},down=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){y++;fix();}};
	final InputMap in=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	final ActionMap am=getActionMap();
	public selection()throws Exception{
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
