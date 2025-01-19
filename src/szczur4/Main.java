package szczur4;

import java.awt.*;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.colorController.colorCore;
import szczur4.fileManager.fileCore;
import szczur4.selectionController.selection;

public class Main{
	public static final Color back=new Color(0x171717),medium=new Color(0x3d3d3d),fore=new Color(0xb0b0b0);
	public static final Border border=new LineBorder(fore,1);
	public static final JFrame frame=new JFrame("paint with alpha v1.0-beta");
	public static FileDialog opener=new FileDialog((Frame)null,"Open File",FileDialog.LOAD),saver=new FileDialog((Frame)null,"Save As",FileDialog.SAVE);
	public static editor editor;
	public static colorCore colorCore;
	public static toolBar toolBar;
	public static fileCore fileCore;
	public static selection selection;
	public static infoBar infoBar;
	public static void main(String[]args)throws Exception{
		editor=new editor();
		colorCore=new colorCore();
		toolBar=new toolBar();
		fileCore=new fileCore();
		selection=new selection();
		infoBar=new infoBar();
		for(String arg:args)try{Main.editor.images.add(ImageIO.read(new File(arg)));}catch(Exception ex){System.err.println("File not found: "+arg);}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(back);
		frame.setLayout(null);
		frame.setSize(600,500);
		frame.add(editor);
		frame.add(colorCore);
		frame.add(toolBar);
		frame.add(fileCore);
		frame.add(selection);
		frame.add(infoBar);
		frame.addMouseWheelListener(editor);
		frame.addComponentListener(fileCore);
		frame.addComponentListener(infoBar);
		frame.setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/windowIcon.png"))).getImage());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		opener.setMultipleMode(true);
	}
}