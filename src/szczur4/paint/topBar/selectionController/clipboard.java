package szczur4.paint.topBar.selectionController;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import szczur4.paint.paint;
public class clipboard extends JPanel{
	final AbstractAction ctrlX=new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
		if(!paint.center.editor.selected)System.out.println("Nothing to cut");
		else{
			copy();
			delete.execute(paint.center.editor.fId);
		}
	}},ctrlC=new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
		if(!paint.center.editor.selected)System.out.println("Nothing to cut");
		else copy();
	}},ctrlV=new AbstractAction(){@Override public void actionPerformed(ActionEvent ev){
		Transferable t=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		try{paint.selection.img=(BufferedImage)t.getTransferData(DataFlavor.imageFlavor);}catch(Exception ex){System.err.println("Something went wrong");return;}
		paint.selection.x=0;
		paint.selection.y=0;
		int w=paint.selection.img.getWidth(),h=paint.selection.img.getHeight();
		paint.selection.w=w;
		paint.selection.h=h;
		paint.selection.setBounds(0,0,paint.selection.w,paint.selection.h);
		if(w>paint.center.editor.w||h>paint.center.editor.h){
			BufferedImage tmp=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			tmp.createGraphics().drawImage(paint.center.editor.img.get(paint.center.editor.fId),0,0,null);
			paint.center.editor.img.set(paint.center.editor.fId,tmp);
			paint.center.editor.updateLocations();
		}
		paint.center.editor.selected=true;
		paint.center.editor.pasted=true;
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
		setBackground(paint.back);
		setBounds(210,1,63,41);
		cut.setText("Cut");
		cut.setBounds(2,4,25,16);
		cut.setBackground(paint.back);
		cut.setForeground(paint.fore);
		cut.setBorder(paint.border);
		cut.setFocusable(false);
		cut.setFont(paint.f);
		copy.setText("Copy");
		copy.setBounds(29,4,33,16);
		copy.setBackground(paint.back);
		copy.setForeground(paint.fore);
		copy.setBorder(paint.border);
		copy.setFocusable(false);
		copy.setFont(paint.f);
		paste.setText("Paste");
		paste.setBounds(2,22,60,16);
		paste.setBackground(paint.back);
		paste.setForeground(paint.fore);
		paste.setBorder(paint.border);
		paste.setFocusable(false);
		paste.setFont(paint.f);
		add(cut);
		add(copy);
		add(paste);
	}
	private void copy(){
		final BufferedImage tmp=new BufferedImage(paint.selection.w,paint.selection.h,BufferedImage.TYPE_INT_ARGB);
		tmp.createGraphics().drawImage(paint.selection.img,0,0,null);
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
