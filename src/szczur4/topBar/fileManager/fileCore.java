package szczur4.topBar.fileManager;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import szczur4.K;
public class fileCore extends JPanel implements ComponentListener,MouseWheelListener{
	public int width,x;
	final JButton left=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		x+=120;
		fixLocation();
	}}),right=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		x-=120;
		fixLocation();
	}}),open=new JButton(K.editor.openFile),create=new JButton(K.editor.saveAs);
	public final files files=new files();
	public fileCore(){
		setLocation(78,0);
		setLayout(null);
		setBorder(K.border);
		setBackground(K.back);
		add(files);
		left.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/left.png"))));
		left.addMouseWheelListener(this);
		left.setBackground(K.back);
		left.setBounds(78,0,12,20);
		left.setFocusable(false);
		left.setBorder(null);
		right.setIcon(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/right.png"))));
		right.addMouseWheelListener(this);
		right.setBackground(K.back);
		right.setSize(12,20);
		right.setFocusable(false);
		right.setBorder(null);
		open.setText("Open");
		open.setBackground(K.back);
		open.setForeground(K.fore);
		open.setBorder(K.border);
		open.setFocusable(false);
		open.setBounds(0,0,40,20);
		open.setFont(K.f);
		create.setText("New");
		create.setBackground(K.back);
		create.setForeground(K.fore);
		create.setBorder(K.border);
		create.setFocusable(false);
		create.setBounds(39,0,40,20);
		create.setFont(K.f);
		K.frame.add(left);
		K.frame.add(right);
		K.frame.add(open);
		K.frame.add(create);
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
				K.editor.img.clear();
				K.editor.files.clear();
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
				setLocation(90,0);
				fixLocation();
				setSize(width-24,20);
				addMouseWheelListener(this);
				left.setVisible(true);
				right.setVisible(true);
			}
			else{
				setLocation(78,0);
				files.setLocation(1,1);
				setSize(width,20);
				removeMouseWheelListener(this);
				left.setVisible(false);
				right.setVisible(false);
			}
		}catch(Exception ignored){}
	}
	@Override public void componentResized(ComponentEvent ev){
		width=K.frame.getContentPane().getWidth()-78;
		right.setLocation(width+66,0);
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
