package szczur4.paint.leftBar;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import szczur4.paint.paint;
import szczur4.paint.resourceLoader;
public class popup extends JPopupMenu implements ActionListener{
	JMenuItem[]items={new JMenuItem("new folder"),new JMenuItem("new file"),new JMenuItem("paste"),new JMenuItem("copy"),new JMenuItem("cut"),new JMenuItem("rename"),new JMenuItem("delete")};
	File f;
	popup(File f){
		this.f=f;
		setBackground(paint.back);
		setBorder(paint.border);
		for(int i=f.isDirectory()?0:3;i<7;i++){
			items[i].setBackground(paint.back);
			items[i].setForeground(paint.fore);
			items[i].setBorder(null);
			items[i].setIcon(resourceLoader.load("file/"+i+".png"));
			items[i].addActionListener(this);
			add(items[i]);
		}
	}
	@Override public void actionPerformed(ActionEvent e){
		String cmd=e.getActionCommand();
		try{switch(cmd){
			case("new folder")->new dialog(0,f);
			case("new file")->new dialog(1,f);
			case("rename")->new dialog(2,f);
			case("delete")->new dialog(3,f);
		}}catch(Exception ex){throw new RuntimeException(ex);}
	}
}
