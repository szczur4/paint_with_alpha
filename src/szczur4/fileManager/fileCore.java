package szczur4.fileManager;

import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;

public class fileCore extends JPanel implements ComponentListener,MouseWheelListener{
	public int width,x;
	final JButton left,right;
	public final files files=new files();
	public fileCore(){
		setLocation(0,0);
		setLayout(null);
		setBorder(K.border);
		setBackground(K.back);
		add(files);
		left=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			x+=120;
			fixLocation();
		}});
		left.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/left.png"))));
		left.addMouseWheelListener(this);
		left.setBackground(K.back);
		left.setBounds(0,0,12,20);
		left.setFocusable(false);
		left.setBorder(null);
		K.frame.add(left);
		right=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			x-=120;
			fixLocation();
		}});
		right.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/right.png"))));
		right.addMouseWheelListener(this);
		right.setBackground(K.back);
		right.setBounds(0,0,12,20);
		right.setFocusable(false);
		right.setBorder(null);
		K.frame.add(right);
	}
	void updateIds(){for(int i=0;i<files.files.size();i++)files.files.get(i).id=i;}
	void fixLocation(){
		x=Math.clamp(x,width-files.width-10,0);
		files.setLocation(x,1);
	}
	public void updateUI(){
		try{
			files.removeAll();
			int n=files.files.size();
			if(n==0){
				K.editor.images.clear();
				K.editor.files.clear();
				K.editor.addStarterThings();
			}
			for(int i=0;i<n;i++){
				files.files.get(i).setLocation(i<<7,0);
				files.add(files.files.get(i));
				files.files.get(i).setBorder(null);
				files.repaint();
			}
			try{files.fix(K.editor.fileId);}catch(Exception ignored){}
			updateIds();
			files.width=(files.files.size())<<7;
			files.setSize(files.width+130,18);
			if(files.width>width){
				setLocation(12,0);
				fixLocation();
				setSize(width-24,20);
				addMouseWheelListener(this);
				left.setVisible(true);
				right.setVisible(true);
			}
			else{
				setLocation(0,0);
				files.setLocation(1,1);
				setSize(width,20);
				removeMouseWheelListener(this);
				left.setVisible(false);
				right.setVisible(false);
			}
		}catch(Exception ignored){}
	}
	@Override public void componentResized(ComponentEvent ev){
		width=K.frame.getContentPane().getWidth();
		right.setLocation(width-12,0);
		updateUI();
	}
	@Override public void componentMoved(ComponentEvent ev){}
	@Override public void componentShown(ComponentEvent ev){}
	@Override public void componentHidden(ComponentEvent ev){}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		x-=ev.getWheelRotation()<<5;
		fixLocation();
	}
}
