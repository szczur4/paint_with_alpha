package szczur4.paint.leftBar;

import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import org.apache.commons.io.FileUtils;
import szczur4.paint.paint;
public class dialog extends javax.swing.JFrame implements WindowFocusListener{
	JTextArea name=new JTextArea();
	JLabel[]labels=new JLabel[2];
	JButton[]buttons=new JButton[]{new JButton(),new JButton(new AbstractAction(){@Override public void actionPerformed(ActionEvent e){
		System.out.println(buttons[0].getText()+" canceled");
		dispose();
	}})};
	File f;
	dialog(int type,File F){
		f=F;
		init(type);
	}
	private void init(int type){
		BufferedImage tmp=new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		setIconImage(tmp);
		setLayout(null);
		setLocationRelativeTo(null);
		setSize(250,100);
		setResizable(false);
		getContentPane().setBackground(paint.back);
		name.setBounds(130,20,100,16);
		name.setBackground(paint.back);
		name.setForeground(paint.fore);
		name.setBorder(new CompoundBorder(paint.border,new EmptyBorder(-1,1,0,0)));
		name.setCaretColor(paint.fore);
		name.setFont(paint.f);
		add(name);
		for(int i=0;i<2;i++){
			labels[i]=new JLabel();
			labels[i].setBounds(5,2+i*18,225-i*102,16);
			labels[i].setForeground(paint.fore);
			labels[i].setFont(paint.f);
			add(labels[i]);
			buttons[i].setBounds(97+i*69,41,64,16);
			buttons[i].setBackground(paint.medium.darker());
			buttons[i].setForeground(paint.fore);
			buttons[i].setBorder(paint.border);
			buttons[i].setFont(paint.f);
			InputMap in=buttons[i].getInputMap(JComponent.WHEN_FOCUSED);
			in.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"enter");
			in.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,0),"tab");
			in.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,KeyEvent.SHIFT_DOWN_MASK),"tab");
			int I=i;
			buttons[i].getActionMap().put("tab",new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){buttons[I==1?0:1].requestFocus();}});
			add(buttons[i]);
		}
		labels[1].setHorizontalAlignment(SwingConstants.RIGHT);
		buttons[1].getActionMap().put("enter",buttons[1].getAction());
		buttons[1].setText("cancel");
		switch(type){
			case(0)->{
				setTitle("create folder");
				labels[0].setText("set folder name");
				labels[1].setText(f.getAbsolutePath()+"\\");
				buttons[0].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
					File file=new File(f.getAbsolutePath()+"\\"+name.getText());
					try{if(!file.mkdir()){System.err.println("creation failed");return;}}catch(Exception _){}
					paint.left.explorer.fillNode(paint.left.explorer.nodeMap.get(f),f);
					paint.left.explorer.fileMap.get(paint.left.explorer.nodeMap.get(f)).extended=true;
					paint.left.explorer.updateUI();
					System.out.println("created "+file.getAbsolutePath());
					dispose();
				}});
				buttons[0].getActionMap().put("enter",buttons[0].getAction());
				buttons[0].setText("create");
			}
			case(1)->{
				setTitle("create file");
				labels[0].setText("set file name");
				labels[1].setText(f.getAbsolutePath()+"\\");
				buttons[0].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
					File file=new File(f.getAbsolutePath()+"\\"+name.getText());
					if(!file.getAbsolutePath().endsWith(".png"))file=new File(file.getAbsolutePath()+".png");
					try{if(!file.createNewFile()){System.err.println("creation failed");dispose();}}catch(Exception _){}
					BufferedImage tmp=new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
					try{ImageIO.write(tmp,"png",file);}catch(Exception _){
						System.err.println("creation failed");
						if(!file.delete()){
							System.err.println("failed to delete unfinished file: "+file.getAbsolutePath());
							System.exit(1);
						}
						dispose();
					}
					paint.left.explorer.fillNode(paint.left.explorer.nodeMap.get(f),f);
					paint.left.explorer.fileMap.get(paint.left.explorer.nodeMap.get(f)).extended=true;
					paint.left.explorer.updateUI();
					System.out.println("created "+file.getAbsolutePath());
					dispose();
				}});
				buttons[0].getActionMap().put("enter",buttons[0].getAction());
				buttons[0].setText("create");
			}
			case(2)->{
				setTitle("rename");
				labels[0].setText("set new name");
				labels[1].setText(f.getParent()+"\\");
				name.setText(f.getName());
				buttons[0].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
					if(!f.renameTo(new File(f.getParent()+"\\"+name.getText())))System.err.println("rename failed");
					else{
						DefaultMutableTreeNode node=paint.left.explorer.nodeMap.remove(f);
						paint.left.explorer.nodeMap.get(f.getParentFile()).remove(node);
						paint.left.explorer.fileMap.remove(node);
						paint.left.explorer.fillNode(paint.left.explorer.nodeMap.get(f.getParentFile()),f.getParentFile());
						paint.left.explorer.updateUI();
					}
					dispose();
				}});
			}
			case(3)->{
				setTitle("delete");
				labels[0].setText("are you sure you want to delete this file?");
				labels[1].setText(f.getParent()+"\\");
				name.setText(f.getName());
				name.setFocusable(false);
				buttons[0].setAction(new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
					if(FileUtils.deleteQuietly(f)){
						DefaultMutableTreeNode toRemove=paint.left.explorer.nodeMap.get(f);
						DefaultMutableTreeNode parent=(DefaultMutableTreeNode)toRemove.getParent();
						parent.remove(toRemove);
						paint.left.explorer.fileMap.remove(toRemove);
						paint.left.explorer.nodeMap.remove(f);
						paint.left.explorer.updateUI();
						System.out.println("deleted "+f.getAbsolutePath());
					}
					else System.err.println("failed to delete "+f.getAbsolutePath());
					dispose();
				}});
				buttons[0].getActionMap().put("enter",buttons[0].getAction());
				buttons[0].setText("delete");
			}
		}
		addWindowFocusListener(this);
		setVisible(true);
	}
	@Override public void windowGainedFocus(WindowEvent e){}
	@Override public void windowLostFocus(WindowEvent e){dispose();}
}
