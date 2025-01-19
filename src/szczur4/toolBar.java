package szczur4;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.tools.invert;
import szczur4.tools.replaceAll;

public class toolBar extends JPanel implements MouseWheelListener{
	final JButton[]tools=new JButton[12];
	final JButton add=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		Main.editor.strokeSize++;
		Main.editor.strokeSize=Math.clamp(Main.editor.strokeSize,1,10);
		sizeLabel.setText("Size: "+Main.editor.strokeSize);
	}}),subtract=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		Main.editor.strokeSize--;
		Main.editor.strokeSize=Math.clamp(Main.editor.strokeSize,1,10);
		sizeLabel.setText("Size: "+Main.editor.strokeSize);
	}});
	final Border yellow=new LineBorder(Color.yellow);
	final JLabel sizeLabel=new JLabel();
	int cBId;
	ImageIcon gridOn,gridOff;
	invert invert=new invert();
	replaceAll replaceAll=new replaceAll();
	toolBar(){
		setBorder(Main.border);
		setBounds(243,19,170,43);
		setBackground(Main.back);
		setLayout(null);
		for(int i=0;i<12;i++){
			int I=i;
			tools[i]=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
				fixBorder(I);
				Main.editor.toolId=I;
			}});
			tools[i].setBounds(5+(i%6)*18,5+(i/6)*18,16,16);
			tools[i].setBorder(Main.border);
			tools[i].setBackground(Main.back);
			tools[i].setForeground(Main.fore);
			tools[i].setFocusable(false);
			try{tools[i].setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/icon"+i+".png"))));}catch(Exception ignored){}
			add(tools[i]);
		}
		fixBorder(0);
		tools[9].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(Main.editor.selected)invert.execute(Main.selection.image);
			else invert.execute(Main.editor.images.get(Main.editor.fileId));
		}});
		tools[10].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(Main.editor.selected)replaceAll.execute(Main.selection.image);
			else replaceAll.execute(Main.editor.images.get(Main.editor.fileId));
		}});
		tools[11].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			Main.editor.grid=!Main.editor.grid;
			if(Main.editor.grid){
				tools[11].setIcon(gridOn);
				tools[11].setBorder(yellow);
			}
			else{
				tools[11].setIcon(gridOff);
				tools[11].setBorder(Main.border);
			}
		}});
		SwingUtilities.invokeLater(()->{
			gridOn=new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/gridOn.png")));
			gridOff=new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/gridOff.png")));
			tools[9].setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/icon9.png"))));
			tools[10].setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/icon10.png"))));
			tools[11].setIcon(gridOff);
		});
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sizeLabel.setVerticalAlignment(SwingConstants.CENTER);
		sizeLabel.setForeground(Main.fore);
		sizeLabel.setBackground(Main.back);
		sizeLabel.setText("Size: "+Main.editor.strokeSize);
		sizeLabel.setBounds(116,5,50,16);
		add.setFocusable(false);
		add.setForeground(Main.fore);
		add.setBackground(Main.back);
		add.setBorder(Main.border);
		add.setText("+");
		add.setBounds(116,23,24,16);
		subtract.setFocusable(false);
		subtract.setForeground(Main.fore);
		subtract.setBackground(Main.back);
		subtract.setBorder(Main.border);
		subtract.setText("-");
		subtract.setBounds(142,23,24,16);
		add(sizeLabel);
		add(add);
		add(subtract);
	}
	void fixBorder(int n){
		tools[n].setBorder(yellow);
		if(cBId!=n)tools[cBId].setBorder(Main.border);
		cBId=n;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent e){

	}
}
