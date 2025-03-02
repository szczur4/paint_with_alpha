package szczur4.paint;

import java.awt.event.*;
import javax.swing.*;
import szczur4.paint.fileManager.saveAll;
import szczur4.paint.fileManager.saveF;
import szczur4.paint.tools.replace;
import szczur4.paint.tools.replaceAll;
import szczur4.paint.topBar.selectionController.delete;
import szczur4.paint.topBar.selectionController.select;
public class keyBinds{
	public keyBinds(){
		InputMap in=paint.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_DOWN_MASK),"replace");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK),"replaceAll");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK),"save");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK),"saveAll");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_DOWN_MASK),"clear");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_DOWN_MASK),"close");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK),"closeAll");
		in.put(KeyStroke.getKeyStroke("ENTER"),"confirm");
		in.put(KeyStroke.getKeyStroke("DELETE"),"del");
		in.put(KeyStroke.getKeyStroke("H"),"h");
		ActionMap am=paint.frame.getRootPane().getActionMap();
		am.put("replace",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){replace.execute(paint.center.editor.img.get(paint.center.editor.fId));}});
		am.put("replaceAll",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){replaceAll.execute();}});
		am.put("save",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){saveF.execute(paint.center.editor.fId);}});
		am.put("saveAll",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){saveAll.execute();}});
		am.put("clear",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){select.clear.getAction().actionPerformed(null);}});
		am.put("close",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){paint.top.files.files.close.run();}});
		am.put("closeAll",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){while(!paint.center.editor.files.isEmpty())
			paint.top.files.files.close.run();}});
		am.put("confirm",paint.center.editor.confirm);
		am.put("del",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){if(paint.center.editor.selected) delete.execute(paint.center.editor.fId);}});
		am.put("h",paint.left.showHide.getAction());
	}
}
