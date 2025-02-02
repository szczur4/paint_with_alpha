package szczur4.topBar.selectionController;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.K;

public class clipboard extends JPanel{
	final AbstractAction ctrlX=new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
		if(!K.editor.selected)System.out.println("Nothing to cut");
		else{
			copy();
			delete.execute(K.editor.fileId);
		}
	}},ctrlC=new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
		if(!K.editor.selected)System.out.println("Nothing to cut");
		else copy();
	}},ctrlV=new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
		Transferable t=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		try{K.selection.img=(BufferedImage)t.getTransferData(DataFlavor.imageFlavor);}catch(Exception ex){System.err.println("Something went wrong");return;}
		K.selection.x=0;
		K.selection.y=0;
		K.selection.w=K.selection.img.getWidth();
		K.selection.h=K.selection.img.getHeight();
		K.selection.setBounds(0,0,K.selection.w,K.selection.h);
		K.editor.selected=true;
		K.editor.pasted=true;
	}};
	final JButton cut=new JButton(ctrlX),copy=new JButton(ctrlC),paste=new JButton(ctrlV);
	final InputMap in=getInputMap(WHEN_IN_FOCUSED_WINDOW);
	final ActionMap am=getActionMap();
	clipboard(){
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK),"cut");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK),"copy");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK),"paste");
		am.put("cut",ctrlX);
		am.put("copy",ctrlC);
		am.put("paste",ctrlV);
		setLayout(null);
		setBackground(K.back);
		setBounds(210,1,63,41);
		cut.setText("Cut");
		cut.setBounds(2,4,25,16);
		cut.setBackground(K.back);
		cut.setForeground(K.fore);
		cut.setBorder(K.border);
		cut.setFocusable(false);
		cut.setFont(K.f);
		copy.setText("Copy");
		copy.setBounds(29,4,33,16);
		copy.setBackground(K.back);
		copy.setForeground(K.fore);
		copy.setBorder(K.border);
		copy.setFocusable(false);
		copy.setFont(K.f);
		paste.setText("Paste");
		paste.setBounds(2,22,60,16);
		paste.setBackground(K.back);
		paste.setForeground(K.fore);
		paste.setBorder(K.border);
		paste.setFocusable(false);
		paste.setFont(K.f);
		add(cut);
		add(copy);
		add(paste);
	}
	private void copy(){
		final BufferedImage tmp=new BufferedImage(K.selection.w,K.selection.h,BufferedImage.TYPE_INT_ARGB);
		tmp.createGraphics().drawImage(K.selection.img,0,0,null);
		Transferable t=new Transferable(){
			@Override public DataFlavor[]getTransferDataFlavors(){return new DataFlavor[]{DataFlavor.imageFlavor};}
			@Override public boolean isDataFlavorSupported(DataFlavor flavor){return flavor==DataFlavor.imageFlavor;}
			@Override public Object getTransferData(DataFlavor flavor)throws UnsupportedFlavorException{
				if(isDataFlavorSupported(flavor))return tmp;
				else throw new UnsupportedFlavorException(flavor);
			}
		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t,null);
	}
}
