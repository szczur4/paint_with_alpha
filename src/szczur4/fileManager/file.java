package szczur4.fileManager;

import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;

public class file extends JButton{
	int id;
	final JButton close=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.fileCore.files.id=id;
		SwingUtilities.invokeLater(K.fileCore.files.close);
	}});
	final JLabel name=new JLabel("");
	public file(int n){
		id=n;
		setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			K.fileCore.files.fix(id);
			K.editor.fileId=id;
			K.editor.updateLocations();
		}});
		setLayout(null);
		setSize(128,18);
		setBackground(K.back);
		setForeground(K.fore);
		setFocusable(false);
		name.setBounds(18,0,110,18);
		name.setBackground(K.back);
		name.setForeground(K.fore);
		name.setText(K.editor.files.get(id).getName());
		close.setBackground(K.medium);
		close.setBounds(0,0,18,18);
		close.setBorder(null);
		close.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("/szczur4/icons/close.png"))));
		close.setFocusable(false);
		add(name);
		add(close);
	}
}
