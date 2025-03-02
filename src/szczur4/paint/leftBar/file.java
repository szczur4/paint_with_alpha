package szczur4.paint.leftBar;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.paint.fileManager.openF;
import szczur4.paint.paint;
import szczur4.paint.resourceLoader;
public class file extends JButton implements MouseListener{
	public boolean extended;
	public JLabel icon;
	public popup popup;
	file(File f){
		icon=new JLabel(resourceLoader.load("file/"+(f.isDirectory()?"dir":"img")+".png"));
		popup=new popup(f);
		setLayout(null);
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(f.isDirectory()){
				paint.left.explorer.fillNode(paint.left.explorer.nodeMap.get(f),f);
				extended=!extended;
				paint.left.explorer.updateUI();
			}
			else openF.execute(f);
		}});
		String Fname=f.getAbsolutePath();
		int n=Fname.lastIndexOf(File.separator);
		if(n==Fname.length()-1)Fname=Fname.substring(0,Fname.lastIndexOf(File.separator));
		else Fname=Fname.substring(Fname.lastIndexOf(File.separator)+1);
		setText(Fname);
		FontMetrics fm=icon.getFontMetrics(paint.f);
		setSize(20+fm.stringWidth(Fname),16);
		setBackground(paint.back);
		setForeground(paint.fore);
		setBorder(new EmptyBorder(0,16,0,0));
		setHorizontalAlignment(LEFT);
		setFocusable(false);
		setFont(paint.f);
		addMouseListener(this);
		icon.setBounds(0,0,14,16);
		add(icon);
		setComponentPopupMenu(popup);
	}
	@Override public void mouseClicked(MouseEvent ev){}
	@Override public void mousePressed(MouseEvent ev){}
	@Override public void mouseReleased(MouseEvent ev){}
	@Override public void mouseEntered(MouseEvent ev){}
	@Override public void mouseExited(MouseEvent ev){}
}
