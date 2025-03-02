package szczur4.paint;
import java.awt.*;
import java.util.Objects;
import javax.swing.border.*;
import szczur4.paint.center.centerCore;
import szczur4.paint.leftBar.leftCore;
import szczur4.paint.topBar.selectionController.selCore;
import szczur4.paint.topBar.topCore;
public class paint{
	public static Color back,medium,fore;
	public static Border border;
	public static final FileDialog opener=new FileDialog((Frame)null,"Open File",FileDialog.LOAD);
	public static selCore selection;
	public static topCore top;
	public static info info;
	public static leftCore left;
	public static centerCore center;
	public static final frame frame=new frame();
	public static Font f;
	public static final boolean windows=System.getProperty("os.name").toLowerCase().startsWith("windows");
	public static void main(String[]args)throws Exception{
		new configReader();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,Objects.requireNonNull(paint.class.getResource("JetBrainsMono-Regular.ttf")).openStream()));
		f=new Font("JetBrainsMono",Font.PLAIN,12);
		center=new centerCore();
		top=new topCore();
		info=new info();
		left=new leftCore();
		selection=new selCore();
		center.editor.add(selection);
		frame.add(top);
		frame.add(info);
		frame.add(center);
		frame.add(left);
		frame.addMouseWheelListener(center.editor);
		frame.addComponentListener(top);
		frame.addComponentListener(info);
		frame.addComponentListener(left);
		frame.addComponentListener(center);
		frame.setVisible(true);
		opener.setMultipleMode(true);
		new keyBinds();
	}
}