package szczur4.paint.topBar.toolbar;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import szczur4.paint.paint;
import szczur4.paint.resourceLoader;
import szczur4.paint.tools.invert;
import szczur4.paint.tools.replace;
public class toolBar extends JPanel implements MouseWheelListener{
	public final tool[]tools=new tool[12];
	final JButton add=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		paint.center.editor.strokeSize++;
		paint.center.editor.strokeSize=Math.clamp(paint.center.editor.strokeSize,1,10);
		sizeLabel.setText("Size: "+paint.center.editor.strokeSize);
	}}),subtract=new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		paint.center.editor.strokeSize--;
		paint.center.editor.strokeSize=Math.clamp(paint.center.editor.strokeSize,1,10);
		sizeLabel.setText("Size: "+paint.center.editor.strokeSize);
	}});
	final Border yellow=new LineBorder(Color.yellow);
	final JLabel sizeLabel=new JLabel();
	int cBId;
	ImageIcon gridOn,gridOff;
	public toolBar(){
		setBorder(paint.border);
		setBounds(343,0,170,43);
		setBackground(paint.back);
		setLayout(null);
		for(int i=0;i<12;i++){
			int I=i;
			tools[i]=new tool(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
				fixBorder(I);
				paint.center.editor.tId=I;
			}},i);
			tools[i].setBounds(5+(i%6)*18,5+(i/6)*18,16,16);
			tools[i].setBorder(paint.border);
			tools[i].setBackground(paint.back);
			tools[i].setForeground(paint.fore);
			tools[i].setFocusable(false);
			tools[i].setIcon(resourceLoader.load("tools/"+i+".png"));
			add(tools[i]);
		}
		fixBorder(0);
		tools[9].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(paint.center.editor.img.isEmpty())return;
			if(paint.center.editor.selected)invert.execute(paint.selection.img);
			else invert.execute(paint.center.editor.img.get(paint.center.editor.fId));
		}});
		tools[10].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			if(paint.center.editor.img.isEmpty())return;
			if(paint.center.editor.selected)replace.execute(paint.selection.img);
			else replace.execute(paint.center.editor.img.get(paint.center.editor.fId));
		}});
		tools[11].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
			paint.center.editor.grid=!paint.center.editor.grid;
			if(paint.center.editor.grid){
				tools[11].setIcon(gridOn);
				tools[11].setBorder(yellow);
			}
			else{
				tools[11].setIcon(gridOff);
				tools[11].setBorder(paint.border);
			}
		}});
		SwingUtilities.invokeLater(()->{
			gridOn=resourceLoader.load("gridOn.png");
			gridOff=resourceLoader.load("gridOff.png");
			tools[9].setIcon(resourceLoader.load("tools/9.png"));
			tools[10].setIcon(resourceLoader.load("tools/10.png"));
			tools[11].setIcon(gridOff);
		});
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sizeLabel.setVerticalAlignment(SwingConstants.CENTER);
		sizeLabel.setForeground(paint.fore);
		sizeLabel.setBackground(paint.back);
		sizeLabel.setText("Size: "+paint.center.editor.strokeSize);
		sizeLabel.setBounds(116,3,50,16);
		sizeLabel.setFont(paint.f);
		add.setFocusable(false);
		add.setForeground(paint.fore);
		add.setBackground(paint.back);
		add.setBorder(paint.border);
		add.setText("+");
		add.setBounds(116,23,24,16);
		add.setFont(paint.f);
		subtract.setFocusable(false);
		subtract.setForeground(paint.fore);
		subtract.setBackground(paint.back);
		subtract.setBorder(paint.border);
		subtract.setText("-");
		subtract.setBounds(142,23,24,16);
		subtract.setFont(paint.f);
		add(sizeLabel);
		add(add);
		add(subtract);
	}
	void fixBorder(int n){
		tools[n].setBorder(yellow);
		if(cBId!=n)tools[cBId].setBorder(paint.border);
		cBId=n;
	}
	@Override public void mouseWheelMoved(MouseWheelEvent e){}
}
