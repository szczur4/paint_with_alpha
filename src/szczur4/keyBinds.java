package szczur4;

import java.awt.event.*;
import javax.swing.*;
import szczur4.tools.replace;
import szczur4.tools.replaceAll;
import szczur4.topBar.fileManager.newF;
import szczur4.topBar.fileManager.openF;
import szczur4.topBar.fileManager.saveAll;
import szczur4.topBar.fileManager.saveF;
import szczur4.topBar.selectionController.delete;
import szczur4.topBar.selectionController.select;
public class keyBinds{
	public keyBinds(){
		InputMap in=K.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_DOWN_MASK),"replace");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK),"replaceAll");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK),"save");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK),"saveAll");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK),"open");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK),"new");
		in.put(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_DOWN_MASK),"clear");
		in.put(KeyStroke.getKeyStroke("ENTER"),"confirm");
		in.put(KeyStroke.getKeyStroke("DELETE"),"del");
		ActionMap am=K.frame.getRootPane().getActionMap();
		am.put("replace",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){replace.execute(K.editor.img.get(K.editor.fId));}});
		am.put("replaceAll",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){replaceAll.execute();}});
		am.put("save",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){saveF.execute(K.editor.fId);}});
		am.put("saveAll",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){saveAll.execute();}});
		am.put("open",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){openF.execute();}});
		am.put("new",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){newF.execute();}});
		am.put("clear",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){select.clear.getAction().actionPerformed(null);}});
		am.put("confirm",K.editor.confirm);
		am.put("del",new AbstractAction(){@Override public void actionPerformed(ActionEvent e){if(K.editor.selected) delete.execute(K.editor.fId);}});
	}
}
