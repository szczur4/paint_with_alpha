package szczur4.topBar.selectionController;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.K;
public class select extends JPanel{
	static final ImageIcon chkOn=new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/chkOn.png"))),chkOff=new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/chkOff.png")));
	static final Border yellow=new LineBorder(Color.yellow);
	final JLabel label=new JLabel("Clear?");
	public static final JButton sel=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		if(K.editor.selecting){
			K.editor.selecting=false;
			sel.setBorder(K.border);
			return;
		}
		K.editor.selecting=true;
		K.editor.selected=false;
		select.execute(0,0,0,0,0);
		K.selection.setBounds(0,0,0,0);
		sel.setBorder(yellow);
	}}),clear=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		K.editor.doClear=!K.editor.doClear;
		if(K.editor.doClear)clear.setIcon(chkOn);
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
			K.selection.img=K.editor.img.get(K.editor.fId).getSubimage(x,y,w,h);
			K.selection.x=x;
			K.selection.y=y;
			K.selection.x1=x;
			K.selection.y1=y;
			K.selection.w=w;
			K.selection.h=h;
			K.selection.id=id;
			K.selection.setBounds((int)(x*K.editor.m+K.editor.lx),(int)(y*K.editor.m+K.editor.ly),(int)(w*K.editor.m),(int)(h*K.editor.m));
		}catch(Exception ignored){}
		sel.setBorder(K.border);
	}
	select(){
		setLayout(null);
		setBackground(K.back);
		setBounds(1,1,60,41);
		label.setBounds(1,4,40,16);
		label.setForeground(K.fore);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(K.f);
		clear.setBounds(42,4,16,16);
		clear.setBackground(K.back);
		clear.setBorder(K.border);
		clear.setIcon(chkOn);
		sel.setBounds(3,22,55,16);
		sel.setBackground(K.back);
		sel.setForeground(K.fore);
		sel.setBorder(K.border);
		sel.setFocusable(false);
		sel.setText("select");
		sel.setFont(K.f);
		add(label);
		add(clear);
		add(sel);
	}
}
