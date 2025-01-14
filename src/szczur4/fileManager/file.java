package szczur4.fileManager;

import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.Main;

public class file extends JButton{
	int id;
	JButton close=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		Main.fileCore.files.id=id;
		SwingUtilities.invokeLater(Main.fileCore.files.close);
	}});
	public file(int n){
		id=n;
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			Main.fileCore.files.fix(id);
			Main.editor.fileId=id;
			Main.editor.updateLocations();
		}});
		setLayout(null);
		setSize(128,18);
		setBackground(Main.back);
		setForeground(Main.fore);
		setFocusable(false);
		setText(Main.editor.files.get(id).getName());
		close.setBackground(Main.medium);
		close.setBounds(0,0,18,18);
		close.setBorder(null);
		close.setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/szczur4/icons/close.png"))));
		add(close);
	}
}
