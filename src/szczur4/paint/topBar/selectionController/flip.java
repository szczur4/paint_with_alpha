package szczur4.paint.topBar.selectionController;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.paint.paint;
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
			label.setText("Flip EW");
		}
		else{
			flip.setAction(flipNS);
			label.setText("Flip NS");
		}
		flip.setText("Flip");
	}};
	final JLabel label=new JLabel("Flip NS");
	final JButton flip=new JButton(flipNS),switchMode=new JButton(switchFlips);
	public static BufferedImage execute(BufferedImage tmp){
		BufferedImage img=new BufferedImage(tmp.getWidth(),tmp.getHeight(),BufferedImage.TYPE_INT_ARGB);
		switch(flipDir){
			case(0)->img.createGraphics().drawImage(tmp,0,0,paint.selection.w,paint.selection.h,null);
			case(1)->img.createGraphics().drawImage(tmp,0,paint.selection.h,paint.selection.w,-paint.selection.h,null);
			case(2)->img.createGraphics().drawImage(tmp,paint.selection.w,0,-paint.selection.w,paint.selection.h,null);
			case(3)->img.createGraphics().drawImage(tmp,paint.selection.w,paint.selection.h,-paint.selection.w,-paint.selection.h,null);
		}
		return img;
	}
	public flip(){
		setLayout(null);
		setBackground(paint.back);
		setBounds(59,1,76,41);
		label.setBounds(0,4,78,16);
		label.setForeground(paint.fore);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(paint.f);
		flip.setText("Flip");
		flip.setBounds(3,22,28,16);
		flip.setBackground(paint.back);
		flip.setForeground(paint.fore);
		flip.setBorder(paint.border);
		flip.setFocusable(false);
		flip.setFont(paint.f);
		switchMode.setText("switch");
		switchMode.setBounds(34,22,41,16);
		switchMode.setBackground(paint.back);
		switchMode.setForeground(paint.fore);
		switchMode.setBorder(paint.border);
		switchMode.setFocusable(false);
		switchMode.setFont(paint.f);
		add(label);
		add(flip);
		add(switchMode);
	}
}
