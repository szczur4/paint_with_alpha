package szczur4.selectionController;

import java.awt.*;
import java.awt.datatransfer.*;
import szczur4.Main;

public class copy{
	public void execute(){
		Transferable t=new Transferable(){
			@Override public DataFlavor[]getTransferDataFlavors(){return new DataFlavor[]{DataFlavor.imageFlavor};}
			@Override public boolean isDataFlavorSupported(DataFlavor flavor){return flavor==DataFlavor.imageFlavor;}
			@Override public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException{
				if(isDataFlavorSupported(flavor))return Main.selection.image;
				else throw new UnsupportedFlavorException(flavor);
			}
		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t,null);
	}
}