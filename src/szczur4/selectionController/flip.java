package szczur4.selectionController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import szczur4.K;

public class flip extends JPanel{
	public static int flipDir;
	final AbstractAction flipNS=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){switch(flipDir){
		case(0)->flipDir=1;
		case(1)->flipDir=0;
		case(2)->flipDir=3;
		case(3)->flipDir=2;
	}}},flipEW=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){switch(flipDir){
		case(0)->flipDir=2;
		case(1)->flipDir=3;
		case(2)->flipDir=0;
		case(3)->flipDir=1;
	}}},switchFlips=new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(flip.getAction()==flipNS){
			flip.setAction(flipEW);
			label.setText("Mode: EW");
		}
		else{
			flip.setAction(flipNS);
			label.setText("Mode: NS");
		}
		flip.setText("Flip");
	}};
	final JLabel label=new JLabel("Mode: NS");
	final JButton flip=new JButton(flipNS),switchMode=new JButton(switchFlips);
	public static void execute(Graphics2D tmp){
		switch(flipDir){
			case(0)->tmp.drawImage(K.selCore.img,0,0,K.selCore.w,K.selCore.h,null);
			case(1)->tmp.drawImage(K.selCore.img,0,K.selCore.h,K.selCore.w,-K.selCore.h,null);
			case(2)->tmp.drawImage(K.selCore.img,K.selCore.w,0,-K.selCore.w,K.selCore.h,null);
			case(3)->tmp.drawImage(K.selCore.img,K.selCore.w,K.selCore.h,-K.selCore.w,-K.selCore.h,null);
		}
	}
	public static void painter(Graphics2D g){
		float m=K.editor.multiplier;
		switch(flipDir){
			case(0)->g.drawImage(K.selCore.img,(int)(K.editor.lX+K.selCore.x*m),(int)(K.editor.lY+K.selCore.y*m),(int)(K.selCore.w*m),(int)(K.selCore.h*m),null);
			case(1)->g.drawImage(K.selCore.img,(int)(K.editor.lX+K.selCore.x*m),(int)(K.editor.lY+(K.selCore.y+K.selCore.h)*m),(int)(K.selCore.w*m),(int)(-K.selCore.h*m),null);
			case(2)->g.drawImage(K.selCore.img,(int)(K.editor.lX+(K.selCore.x+K.selCore.w)*m),(int)(K.editor.lY+K.selCore.y*m),(int)(-K.selCore.w*m),(int)(K.selCore.h*m),null);
			case(3)->g.drawImage(K.selCore.img,(int)(K.editor.lX+(K.selCore.x+K.selCore.w)*m),(int)(K.editor.lY+(K.selCore.y+K.selCore.h)*m),(int)(-K.selCore.w*m),(int)(-K.selCore.h*m),null);
		}
	}
	public flip(){
		setLayout(null);
		setBounds(1,1,100,41);
		setBackground(K.back);
		label.setBounds(3,2,90,16);
		label.setForeground(K.fore);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		flip.setText("Flip");
		flip.setBounds(3,22,42,16);
		flip.setBackground(K.back);
		flip.setForeground(K.fore);
		flip.setBorder(K.border);
		flip.setFocusable(false);
		switchMode.setText("switch");
		switchMode.setBounds(47,22,42,16);
		switchMode.setBackground(K.back);
		switchMode.setForeground(K.fore);
		switchMode.setBorder(K.border);
		switchMode.setFocusable(false);
		add(label);
		add(flip);
		add(switchMode);
	}
}
