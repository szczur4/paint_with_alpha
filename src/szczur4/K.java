package szczur4;
import java.awt.*;
import java.util.Objects;
import javax.swing.*;
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
	public static final Toolkit tk=Toolkit.getDefaultToolkit();
	public static final Cursor[]cursors=new Cursor[9];
	public static void main(String[]args)throws Exception{
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,Objects.requireNonNull(K.class.getResource("JetBrainsMono-Regular.ttf")).openStream()));
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
		for(int i=0;i<9;i++)cursors[i]=tk.createCustomCursor(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/icon"+i+".png"))).getImage(),new Point(3,27),"tool"+i);
		new keyBinds();
	}
}