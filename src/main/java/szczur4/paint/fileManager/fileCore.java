package szczur4.paint.fileManager;
import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.paint;
import szczur4.paint.resourceLoader;

public class fileCore extends JPanel implements ComponentListener,MouseWheelListener{
	public int width,x;
	final JButton left=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){fixLocation(0);}}),right=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){fixLocation(1);}});
	public final files files=new files();
	public fileCore(){
		setLocation(0,0);
		setLayout(null);
		setBackground(paint.back);
		setBorder(paint.border);
		add(files);
		left.setIcon(resourceLoader.load("left.png"));
		left.addMouseWheelListener(this);
		left.setBackground(paint.back);
		left.setSize(12,20);
		left.setFocusable(false);
		left.setBorder(null);
		right.setIcon(resourceLoader.load("right.png"));
		right.addMouseWheelListener(this);
		right.setBackground(paint.back);
		right.setSize(12,20);
		right.setFocusable(false);
		right.setBorder(null);
		paint.frame.add(left);
		paint.frame.add(right);
	}
	void updateIds(){for(int i=0;i<files.files.size();i++)files.files.get(i).id=i;}
	void fixLocation(int b){
		x=Math.clamp(x+(b==1?-60:(b==0?60:0)),width-files.width-10,0);
		files.setLocation(x,1);
	}
	@Override public void updateUI(){
		super.updateUI();
		try{
			files.removeAll();
			int n=files.files.size();
			if(n==0){
				paint.center.editor.img.clear();
				paint.center.editor.files.clear();
			}
			for(int i=0;i<n;i++){
				files.files.get(i).setLocation(i<<7,0);
				files.add(files.files.get(i));
				files.files.get(i).setBorder(null);
				files.repaint();
			}
			try{files.fix(paint.center.editor.fId);}catch(Exception ignored){}
			updateIds();
			files.width=(files.files.size())<<7;
			files.setSize(files.width+130,18);
			if(files.width>width){
				setLocation(12,0);
				fixLocation(-1);
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
		width=paint.frame.getContentPane().getWidth();
		right.setLocation(width-12,0);
		updateUI();
	}
	@Override public void componentMoved(ComponentEvent ev){}
	@Override public void componentShown(ComponentEvent ev){}
	@Override public void componentHidden(ComponentEvent ev){}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		x-=ev.getWheelRotation()<<5;
		fixLocation(-1);
	}
}
