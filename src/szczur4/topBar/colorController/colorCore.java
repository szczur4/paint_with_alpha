package szczur4.topBar.colorController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.K;
public class colorCore extends JPanel{
	public final Border yellow=new LineBorder(Color.yellow);
	public final colorDisplay colorDisplay=new colorDisplay();
	final JLabel[]labels={new JLabel("R:"),new JLabel("G:"),new JLabel("B:"),new JLabel("A:")};
	public final slider[]sliders=new slider[]{new slider(0),new slider(0),new slider(0),new slider(255)};
	public int cBId;
	public boolean secondary;
	final JButton add=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		Color tmp=new Color(sliders[0].value(),sliders[1].value(),sliders[1].value(),sliders[1].value());
		for(int i=0;i<8;i++)if(colors[i].c.getRGB()==tmp.getRGB()){
			colors[i].setBorder(yellow);
			colors[cBId].setBorder(K.border);
			cBId=i;
			colorDisplay.repaint();
			if(secondary)K.editor.secondary=tmp;
			else K.editor.primary=tmp;
			return;
		}
		if(secondary)K.editor.secondary=tmp;
		else K.editor.primary=tmp;
		for(int i=6;i>=0;i--)colors[i+1].c=colors[i].c;
		colors[0].c=tmp;
		for(int i=0;i<8;i++)colors[i].repaint();
		cBId=0;
		colorDisplay.repaint();
	}});
	public final previous[]colors=new previous[8];
	public colorCore(){
		setBounds(0,19,309,43);
		setBorder(K.border);
		setBackground(K.back);
		setLayout(null);
		add(colorDisplay);
		add.setBackground(K.back);
		add.setForeground(K.fore);
		add.setText("Add Color");
		add.setBounds(44,23,77,16);
		add.setBorder(K.border);
		add.setFocusable(false);
		add.setFont(K.f);
		//add(add);
		for(int i=0;i<4;i++){
			labels[i].setBackground(K.back);
			labels[i].setForeground(K.fore);
			labels[i].setBounds(3+92*(i%2),5+18*(i>>1),15,16);
			labels[i].setFont(K.f);
			add(labels[i]);
			sliders[i].setLocation(16+92*(i%2),5+18*(i>>1));
			add(sliders[i]);
		}
		for(int i=0;i<8;i++){
			colors[i]=new previous();
			colors[i].setLocation(235+(i%4)*18,5+18*(i/4));
			add(colors[i]);
		}
	}
	public void fix(Color c){
		sliders[0].v=c.getRed();
		sliders[1].v=c.getGreen();
		sliders[2].v=c.getBlue();
		sliders[3].v=c.getAlpha();
		for(int i=0;i<4;i++)K.top.colors.sliders[i].fix();
	}
}