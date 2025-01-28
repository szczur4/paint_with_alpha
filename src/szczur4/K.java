package szczur4;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import szczur4.colorController.colorCore;
import szczur4.fileManager.fileCore;
import szczur4.selectionController.selCore;

public class K{
	public static final Color back=new Color(0x171717),medium=new Color(0x3d3d3d),fore=new Color(0xb0b0b0);
	public static final Border border=new LineBorder(fore,1);
	public static final FileDialog opener=new FileDialog((Frame)null,"Open File",FileDialog.LOAD),saver=new FileDialog((Frame)null,"Save As",FileDialog.SAVE);
	public static editor editor;
	public static colorCore colorCore;
	public static toolBar toolBar;
	public static fileCore fileCore;
	public static szczur4.selectionController.selCore selCore;
	public static infoBar infoBar;
	public static final frame frame=new frame();
	public static void main(String[]args)throws Exception{
		editor=new editor();
		colorCore=new colorCore();
		toolBar=new toolBar();
		fileCore=new fileCore();
		selCore=new selCore();
		infoBar=new infoBar();
		editor.add(selCore);
		for(String arg:args)try{K.editor.images.add(ImageIO.read(new File(arg)));}catch(Exception ex){System.err.println("File not found: "+arg);}
		frame.add(editor);
		frame.add(colorCore);
		frame.add(toolBar);
		frame.add(fileCore);
		frame.add(infoBar);
		frame.addMouseWheelListener(editor);
		frame.addComponentListener(fileCore);
		frame.addComponentListener(infoBar);
		frame.addComponentListener(editor);
		frame.setVisible(true);
		opener.setMultipleMode(true);
	}
}