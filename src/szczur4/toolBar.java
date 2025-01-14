package szczur4;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;

public class toolBar extends JPanel{
	final JButton[]tools=new JButton[10];
	final Border yellow=new LineBorder(Color.yellow);
	int cBId;
	ImageIcon gridOn,gridOff;
	toolBar(){
		setBorder(Main.border);
		setBounds(243,19,97,43);
		setBackground(Main.back);
		setLayout(null);
		for(int i=0;i<10;i++){
			int I=i;
			tools[i]=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
				fixBorder(I);
				Main.editor.toolId=I;
			}});
			tools[i].setBounds(5+(i%5)*18,5+(i/5)*18,16,16);
			tools[i].setBorder(Main.border);
			tools[i].setBackground(Main.back);
			tools[i].setForeground(Main.fore);
			tools[i].setFocusable(false);
			try{tools[i].setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/icon"+i+".png"))));}catch(Exception ignored){}
			add(tools[i]);
		}
		fixBorder(0);
		tools[9].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			Main.editor.grid=!Main.editor.grid;
			if(Main.editor.grid){
				tools[9].setIcon(gridOn);
				tools[9].setBorder(yellow);
			}
			else{
				tools[9].setIcon(gridOff);
				tools[9].setBorder(Main.border);
			}
		}});
		SwingUtilities.invokeLater(()->{
			gridOn=new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/gridOn.png")));
			gridOff=new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/gridOff.png")));
			tools[9].setIcon(gridOff);
		});
	}
	void fixBorder(int n){
		tools[n].setBorder(yellow);
		if(cBId!=n)tools[cBId].setBorder(Main.border);
		cBId=n;
	}
}
