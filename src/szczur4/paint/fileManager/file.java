package szczur4.paint.fileManager;

import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.paint;
import szczur4.paint.resourceLoader;

public class file extends JButton{
	int id;
	final JButton close=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		paint.top.files.files.id=id;
		SwingUtilities.invokeLater(paint.top.files.files.close);
	}});
	final JLabel name=new JLabel("");
	public file(int n){
		id=n;
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			paint.top.files.files.fix(id);
			paint.center.editor.fId=id;
			paint.center.editor.updateLocations();
			paint.center.editor.removeStarter();
		}});
		setLayout(null);
		setSize(128,18);
		setBackground(paint.back);
		setForeground(paint.fore);
		setFocusable(false);
		name.setBounds(18,0,110,18);
		name.setBackground(paint.back);
		name.setForeground(paint.fore);
		name.setText(paint.center.editor.files.get(id).getName());
		name.setFont(paint.f);
		close.setBackground(paint.medium);
		close.setBounds(0,0,18,18);
		close.setBorder(null);
		close.setIcon(resourceLoader.load("close.png"));
		close.setFocusable(false);
		add(name);
		add(close);
	}
}
