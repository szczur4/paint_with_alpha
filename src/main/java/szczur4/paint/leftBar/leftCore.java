package szczur4.paint.leftBar;
import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.paint;
import szczur4.paint.resourceLoader;
public class leftCore extends JPanel implements ComponentListener{
	private final ImageIcon show=resourceLoader.load("left/show.png"),hide=resourceLoader.load("left/hide.png");
	public JButton showHide=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		setVisible(!isVisible());
		showHide.setIcon(isVisible()?hide:show);
		update();
	}});
	public explorer explorer=new explorer();
	public leftCore(){
		showHide.setSize(12,32);
		showHide.setBackground(paint.back);
		showHide.setForeground(paint.fore);
		showHide.setBorder(paint.border);
		showHide.setFont(paint.f);
		showHide.setFocusable(false);
		showHide.setIcon(show);
		setLayout(null);
		setLocation(0,19);
		setSize(201,0);
		setBackground(paint.back);
		setBorder(paint.border);
		setVisible(false);
		add(explorer);
		paint.center.fixer.add(showHide);
	}
	public void update(){
		int h=paint.frame.getContentPane().getHeight()-19;
		setSize(Math.clamp(getWidth(),102,350),h);
		explorer.setBounds(12,1,getWidth()-12,getHeight()-13);
		showHide.setLocation(0,(h>>1)-57);
		explorer.horiz.setBounds(0,h-12,getWidth()+1,12);
		explorer.horiz.update();
		explorer.vert.setBounds(0,0,12,explorer.getHeight()+2);
		explorer.vert.update();
		explorer.resizer.setLocation(explorer.getWidth()-6,(explorer.getHeight()>>1)-24);
		paint.top.update();
		paint.center.update();
		paint.info.update();
		paint.left.explorer.updateUI();
	}
	@Override public void componentResized(ComponentEvent e){update();}
	@Override public void componentMoved(ComponentEvent e){}
	@Override public void componentShown(ComponentEvent e){}
	@Override public void componentHidden(ComponentEvent e){}
}