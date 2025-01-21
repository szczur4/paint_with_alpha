package szczur4.fileManager;

import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.Main;

public class file extends JButton{
	int id;
	final JButton close=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		Main.fileCore.files.id=id;
		SwingUtilities.invokeLater(Main.fileCore.files.close);
	}});
	final JLabel name=new JLabel("");
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
		name.setBounds(18,0,110,18);
		name.setBackground(Main.back);
		name.setForeground(Main.fore);
		name.setText(Main.editor.files.get(id).getName());
		close.setBackground(Main.medium);
		close.setBounds(0,0,18,18);
		close.setBorder(null);
		close.setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/szczur4/icons/close.png"))));
		close.setFocusable(false);
		add(name);
		add(close);
	}
}
