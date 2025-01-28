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
		K.editor.strokeSize++;
		K.editor.strokeSize=Math.clamp(K.editor.strokeSize,1,10);
		sizeLabel.setText("Size: "+K.editor.strokeSize);
	}}),subtract=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.editor.strokeSize--;
		K.editor.strokeSize=Math.clamp(K.editor.strokeSize,1,10);
		sizeLabel.setText("Size: "+K.editor.strokeSize);
	}});
	final Border yellow=new LineBorder(Color.yellow);
	final JLabel sizeLabel=new JLabel();
	int cBId;
	ImageIcon gridOn,gridOff;
	final invert invert=new invert();
	final replaceAll replaceAll=new replaceAll();
	toolBar(){
		setBorder(K.border);
		setBounds(243,19,170,43);
		setBackground(K.back);
		setLayout(null);
		for(int i=0;i<12;i++){
			int I=i;
			tools[i]=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
				fixBorder(I);
				K.editor.toolId=I;
			}});
			tools[i].setBounds(5+(i%6)*18,5+(i/6)*18,16,16);
			tools[i].setBorder(K.border);
			tools[i].setBackground(K.back);
			tools[i].setForeground(K.fore);
			tools[i].setFocusable(false);
			try{tools[i].setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/icon"+i+".png"))));}catch(Exception ignored){}
			add(tools[i]);
		}
		fixBorder(0);
		tools[9].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(K.editor.images.isEmpty())return;
			if(K.editor.selected)invert.execute(K.selCore.img);
			else invert.execute(K.editor.images.get(K.editor.fileId));
		}});
		tools[10].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(K.editor.images.isEmpty())return;
			if(K.editor.selected)replaceAll.execute(K.selCore.img);
			else replaceAll.execute(K.editor.images.get(K.editor.fileId));
		}});
		tools[11].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			K.editor.grid=!K.editor.grid;
			if(K.editor.grid){
				tools[11].setIcon(gridOn);
				tools[11].setBorder(yellow);
			}
			else{
				tools[11].setIcon(gridOff);
				tools[11].setBorder(K.border);
			}
		}});
		SwingUtilities.invokeLater(()->{
			gridOn=new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/gridOn.png")));
			gridOff=new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/gridOff.png")));
			tools[9].setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/icon9.png"))));
			tools[10].setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/icon10.png"))));
			tools[11].setIcon(gridOff);
		});
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sizeLabel.setVerticalAlignment(SwingConstants.CENTER);
		sizeLabel.setForeground(K.fore);
		sizeLabel.setBackground(K.back);
		sizeLabel.setText("Size: "+K.editor.strokeSize);
		sizeLabel.setBounds(116,5,50,16);
		add.setFocusable(false);
		add.setForeground(K.fore);
		add.setBackground(K.back);
		add.setBorder(K.border);
		add.setText("+");
		add.setBounds(116,23,24,16);
		subtract.setFocusable(false);
		subtract.setForeground(K.fore);
		subtract.setBackground(K.back);
		subtract.setBorder(K.border);
		subtract.setText("-");
		subtract.setBounds(142,23,24,16);
		add(sizeLabel);
		add(add);
		add(subtract);
	}
	void fixBorder(int n){
		tools[n].setBorder(yellow);
		if(cBId!=n)tools[cBId].setBorder(K.border);
		cBId=n;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent e){

	}
}
