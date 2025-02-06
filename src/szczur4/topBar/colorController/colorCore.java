package szczur4.topBar.colorController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.K;
public class colorCore extends JPanel{
	public final Border yellow=new LineBorder(Color.yellow);
	public final colorDisplay display=new colorDisplay();
	final JLabel[]labels={new JLabel("R:"),new JLabel("G:"),new JLabel("B:"),new JLabel("A:")};
	public final slider[]sliders=new slider[4];
	public int cBId;
	public boolean secondary;
	public final previous[]colors=new previous[12];
	public colorCore(){
		setBounds(0,19,345,43);
		setBorder(K.border);
		setBackground(K.back);
		setLayout(null);
		add(display);
		for(int i=0;i<4;i++){
			labels[i].setBackground(K.back);
			labels[i].setForeground(K.fore);
			labels[i].setBounds(3+92*(i%2),5+18*(i>>1),15,16);
			labels[i].setFont(K.f);
			add(labels[i]);
			sliders[i]=new slider(0);
			sliders[i].setLocation(16+92*(i%2),5+18*(i>>1));
			add(sliders[i]);
		}
		for(int i=0;i<12;i++){
			colors[i]=new previous();
			colors[i].setLocation(235+(i%6)*18,5+18*(i/6));
			add(colors[i]);
		}
	}
	public void set(){
		Color tmp=new Color(sliders[0].value(),sliders[1].value(),sliders[2].value(),sliders[3].value());
		if(secondary)K.editor.secondary=tmp;
		else K.editor.primary=tmp;
		display.repaint();
	}
	public void add(){
		Color tmp=new Color(sliders[0].value(),sliders[1].value(),sliders[2].value(),sliders[3].value());
		for(int i=0;i<12;i++)if(colors[i].c.getRGB()==tmp.getRGB()){
			colors[i].setBorder(yellow);
			colors[cBId].setBorder(K.border);
			cBId=i;
			if(secondary)K.editor.secondary=tmp;
			else K.editor.primary=tmp;
			display.repaint();
			return;
		}
		if(secondary)K.editor.secondary=tmp;
		else K.editor.primary=tmp;
		for(int i=10;i>=0;i--)colors[i+1].c=colors[i].c;
		colors[0].c=tmp;
		for(int i=0;i<12;i++)colors[i].repaint();
		cBId=0;
		K.editor.newColor=false;
	}
	public void fix(Color c){
		sliders[0].v=c.getRed();
		sliders[1].v=c.getGreen();
		sliders[2].v=c.getBlue();
		sliders[3].v=c.getAlpha();
		for(int i=0;i<4;i++)K.top.colors.sliders[i].fix();
	}
}