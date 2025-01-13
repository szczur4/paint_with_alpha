package szczur4;

import java.awt.*;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.colorController.colorController;

public class Main{
	public static final Color back=new Color(0x171717);
	public static final Color fore=new Color(0xb0b0b0);
	public static final Border border=new LineBorder(fore,1);
	public static final JFrame frame=new JFrame("paint with alpha v1.0-beta");
	public static editor editor;
	public static opener opener;
	public static colorController colorController;
	public static toolBar toolBar;
	public static void main(String[]args)throws Exception{
		editor=new editor();
		try{editor.image=ImageIO.read(new File(args[0]));}catch(Exception ignored){}
		opener=new opener();
		colorController=new colorController();
		toolBar=new toolBar();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(back);
		frame.setLayout(null);
		frame.setSize(500,500);
		frame.add(editor);
		frame.add(colorController);
		frame.add(toolBar);
		frame.addMouseWheelListener(editor);
		frame.setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/windowIcon.png"))).getImage());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}