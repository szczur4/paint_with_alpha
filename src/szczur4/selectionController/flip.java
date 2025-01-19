package szczur4.selectionController;

import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.Main;

public class flip extends JPanel{
	boolean vertical;
	AbstractAction flipNS=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		BufferedImage tmp=new BufferedImage(Main.selection.w,Main.selection.h,BufferedImage.TYPE_INT_ARGB);
		tmp.createGraphics().drawImage(Main.selection.image,0,Main.selection.h,Main.selection.w,-Main.selection.h,null);
		Main.selection.image.getGraphics().clearRect(0,0,Main.selection.w,Main.selection.h);
		Main.selection.image.getGraphics().drawImage(tmp,0,0,Main.selection.w,Main.selection.h,null);
	}},flipEW=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		BufferedImage tmp=new BufferedImage(Main.selection.w,Main.selection.h,BufferedImage.TYPE_INT_ARGB);
		tmp.createGraphics().drawImage(Main.selection.image,Main.selection.w,0,-Main.selection.w,Main.selection.h,null);
		Main.selection.image.getGraphics().clearRect(0,0,Main.selection.w,Main.selection.h);
		Main.selection.image.getGraphics().drawImage(tmp,0,0,Main.selection.w,Main.selection.h,null);
	}},switchFlips=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(vertical){
			flip.setAction(flipNS);
			label.setText("Mode: NS");
		}
		else{
			flip.setAction(flipEW);
			label.setText("Mode: EW");
		}
		flip.setText("Flip");
		vertical=!vertical;
	}};
	JLabel label=new JLabel("Mode: NS");
	JButton flip=new JButton(flipNS),switchMode=new JButton(switchFlips);
	public flip(){
		setLayout(null);
		setBounds(1,1,100,41);
		setBackground(Main.back);
		label.setBounds(3,2,90,16);
		label.setForeground(Main.fore);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		flip.setText("Flip");
		flip.setBounds(3,22,42,16);
		flip.setBackground(Main.back);
		flip.setForeground(Main.fore);
		flip.setBorder(Main.border);
		flip.setFocusable(false);
		switchMode.setText("switch");
		switchMode.setBounds(47,22,42,16);
		switchMode.setBackground(Main.back);
		switchMode.setForeground(Main.fore);
		switchMode.setBorder(Main.border);
		switchMode.setFocusable(false);
		add(label);
		add(flip);
		add(switchMode);
	}
}
