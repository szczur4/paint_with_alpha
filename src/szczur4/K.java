package szczur4;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import javax.swing.border.*;
import szczur4.bottomBar.bottomCore;
import szczur4.topBar.selectionController.selCore;
import szczur4.topBar.topCore;
public class K{
	public static final Color back=new Color(0x171717),mid=new Color(0x3d3d3d),fore=new Color(0xb0b0b0);
	public static final Border border=new LineBorder(fore,1);
	public static final FileDialog opener=new FileDialog((Frame)null,"Open File",FileDialog.LOAD),saver=new FileDialog((Frame)null,"Save As",FileDialog.SAVE);
	public static editor editor;
	public static selCore selection;
	public static topCore top;
	public static bottomCore bottom;
	public static final frame frame=new frame();
	public static Font f;
	public static void main(String[]args)throws Exception{
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File(Objects.requireNonNull(K.class.getResource("JetBrainsMono-Regular.ttf")).toURI())));
		f=new Font("JetBrainsMono",Font.PLAIN,12);
		editor=new editor();
		top=new topCore();
		bottom=new bottomCore();
		selection=new selCore();
		editor.add(selection);
		frame.add(top);
		frame.add(bottom);
		frame.add(editor);
		frame.addMouseWheelListener(editor);
		frame.addComponentListener(editor);
		frame.addComponentListener(top);
		frame.addComponentListener(bottom);
		frame.setVisible(true);
		opener.setMultipleMode(true);
	}
}