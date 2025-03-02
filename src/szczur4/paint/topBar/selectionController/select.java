package szczur4.paint.topBar.selectionController;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.paint.paint;
public class select extends JPanel{
	static final ImageIcon chkOn=new ImageIcon(Objects.requireNonNull(paint.class.getResource("icons/chkOn.png"))),chkOff=new ImageIcon(Objects.requireNonNull(paint.class.getResource("icons/chkOff.png")));
	static final Border yellow=new LineBorder(Color.yellow);
	final JLabel label=new JLabel("Clear?");
	public static final JButton sel=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(paint.center.editor.selecting){
			paint.center.editor.selecting=false;
			sel.setBorder(paint.border);
			return;
		}
		paint.center.editor.selecting=true;
		paint.center.editor.selected=false;
		select.execute(0,0,0,0,0);
		paint.selection.setBounds(0,0,0,0);
		sel.setBorder(yellow);
	}}),clear=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		paint.center.editor.doClear=!paint.center.editor.doClear;
		if(paint.center.editor.doClear)clear.setIcon(chkOn);
		else clear.setIcon(chkOff);
	}});
	public static void execute(int x,int y,int w,int h,int id){
		rotate.rotation=0;
		if(w<0){
			x+=w;
			w*=-1;
		}
		if(h<0){
			y+=h;
			h*=-1;
		}
		try{
			paint.selection.img=paint.center.editor.img.get(paint.center.editor.fId).getSubimage(x,y,w,h);
			paint.selection.x=x;
			paint.selection.y=y;
			paint.selection.x1=x;
			paint.selection.y1=y;
			paint.selection.w=w;
			paint.selection.h=h;
			paint.selection.id=id;
			paint.selection.setBounds((int)(x*paint.center.editor.m+paint.center.editor.lx),(int)(y*paint.center.editor.m+paint.center.editor.ly),(int)(w*paint.center.editor.m),(int)(h*paint.center.editor.m));
		}catch(Exception ignored){}
		sel.setBorder(paint.border);
	}
	select(){
		setLayout(null);
		setBackground(paint.back);
		setBounds(1,1,60,41);
		label.setBounds(1,4,40,16);
		label.setForeground(paint.fore);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(paint.f);
		clear.setBounds(42,4,16,16);
		clear.setBackground(paint.back);
		clear.setBorder(paint.border);
		clear.setIcon(chkOn);
		sel.setBounds(3,22,55,16);
		sel.setBackground(paint.back);
		sel.setForeground(paint.fore);
		sel.setBorder(paint.border);
		sel.setFocusable(false);
		sel.setText("select");
		sel.setFont(paint.f);
		add(label);
		add(clear);
		add(sel);
	}
}
