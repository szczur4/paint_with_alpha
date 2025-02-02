package szczur4.topBar.selectionController;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.K;
public class rotate extends JPanel{
	Robot robot;
	public static double rotation;
	final JLabel label=new JLabel("Rotate by:");
	final JTextArea amount=new JTextArea("0");
	final JButton rotate=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		try{rotation+=Double.parseDouble(amount.getText());}catch(Exception ex){amount.setText(rotation+"");}
		rotation%=360;
	}});
	public static BufferedImage execute(BufferedImage tmp){
		int w=(int)Math.floor(tmp.getWidth()*Math.abs(Math.cos(Math.toRadians(rotation)))+tmp.getHeight()*Math.abs(Math.sin(Math.toRadians(rotation)))),h=(int)Math.floor(tmp.getHeight()*Math.abs(Math.cos(Math.toRadians(rotation)))+tmp.getWidth()*Math.abs(Math.sin(Math.toRadians(rotation))));
		BufferedImage img=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		g.translate((w-tmp.getWidth())/2,(h-tmp.getHeight())/2);
		g.rotate(Math.toRadians(rotation),tmp.getWidth()/2.0,tmp.getHeight()/2.0);
		g.drawRenderedImage(tmp,new AffineTransform());
		g.dispose();
		return img;
	}
	public rotate(){
		try{robot=new Robot();}catch(Exception _){}
		setLayout(null);
		setBackground(K.back);
		setBounds(134,1,76,41);
		label.setBounds(0,4,75,16);
		label.setForeground(K.fore);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(K.f);
		rotate.setText("Rotate");
		rotate.setBounds(3,22,40,16);
		rotate.setBackground(K.back);
		rotate.setForeground(K.fore);
		rotate.setBorder(K.border);
		rotate.setFocusable(false);
		rotate.setFont(K.f);
		amount.setBounds(45,22,30,16);
		amount.setBackground(K.back);
		amount.setForeground(K.fore);
		amount.setCaretColor(K.fore);
		amount.setBorder(new CompoundBorder(K.border,new EmptyBorder(-1,1,1,1)));
		amount.addMouseListener(new MouseListener(){
			@Override public void mouseClicked(MouseEvent e){}
			@Override public void mousePressed(MouseEvent e){}
			@Override public void mouseReleased(MouseEvent e){}
			@Override public void mouseEntered(MouseEvent e){amount.setFocusable(true);}
			@Override public void mouseExited(MouseEvent e){amount.setFocusable(false);}
		});
		amount.addCaretListener(_->{try{Double.parseDouble(amount.getText());}catch(Exception _){robot.keyPress('\b');}});
		amount.setFont(K.f);
		add(label);
		add(rotate);
		add(amount);
	}
}