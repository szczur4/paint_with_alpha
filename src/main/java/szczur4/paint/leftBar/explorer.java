package szczur4.paint.leftBar;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.tree.*;
import szczur4.paint.paint;
public class explorer extends JPanel implements MouseMotionListener,MouseWheelListener{
	final Map<File,DefaultMutableTreeNode>nodeMap=new TreeMap<>();
	final Map<DefaultMutableTreeNode,file>fileMap=new HashMap<>();
	final DefaultMutableTreeNode root=new DefaultMutableTreeNode("This PC");
	final horiz horiz=new horiz();
	final vert vert=new vert();
	final resizer resizer=new resizer();
	int Y,x,y,w,h,mx,my;
	public explorer(){
		fileMap.put(root,new file(new File("This PC")));
		file rootFile=fileMap.get(root);
		rootFile.setComponentPopupMenu(null);
		rootFile.icon.setIcon(null);
		rootFile.extended=true;
		rootFile.setBorder(null);
		rootFile.setSize(rootFile.getWidth()-16,rootFile.getHeight());
		File[]drives=File.listRoots();
		for(int i=1;i<=drives.length;i++){
			DefaultMutableTreeNode dir=new DefaultMutableTreeNode(drives[i-1]);
			nodeMap.put(drives[i-1],dir);
			fileMap.put(dir,new file(drives[i-1]));
			file tmp=fileMap.get(dir);
			for(int n=3;n<7;n++)tmp.popup.remove(tmp.popup.items[n]);
			root.add(dir);
		}
		setLayout(null);
		setLocation(0,1);
		setBackground(paint.back);
		setForeground(paint.fore);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		horiz.offset=12;
	}
	@Override public void updateUI(){
		super.updateUI();
		removeAll();
		try{
			x=horiz.x+1;
			y=vert.y;
			w=0;
			Y=0;
			addCell(root);
			h=Y<<4;
			horiz.setLimit(w-getWidth());
			vert.setLimit(h-getHeight());
			horiz.update();
			vert.update();
			getComponent(0).setLocation(x,y);
			paint.left.add(horiz);
			paint.left.add(vert);
			add(resizer);
		}catch(Exception _){}
		repaint();
	}
	void addCell(DefaultMutableTreeNode node){
		file f=fileMap.get(node);
		f.setLocation(node.getLevel()*10+x,Y*16+y);
		w=Math.max(w,f.getWidth()+node.getLevel()*10);
		if(f.getY()>-32&&f.getY()<getHeight()||!node.isLeaf())add(f);
		Y++;
		int n=node.getChildCount();
		for(int i=0;i<n;i++){
			DefaultMutableTreeNode child=(DefaultMutableTreeNode)node.getChildAt(i);
			if(f.extended)addCell(child);
		}
	}
	@Override public void paint(Graphics graphics){
		Graphics2D g=(Graphics2D)graphics;
		super.paint(g);
		g.setColor(paint.fore);
		try{painter(g,root);}catch(Exception _){}
		g.translate(0,0);
		g.dispose();
	}
	void painter(Graphics2D g,DefaultMutableTreeNode node){
		int n=node.getChildCount(),x=fileMap.get(node).getX()+4,y=fileMap.get(node).getY()+18;
		for(int i=0;i<n;i++){
			DefaultMutableTreeNode child=(DefaultMutableTreeNode)node.getChildAt(i);
			int Y=fileMap.get(child).getY()+10;
			g.drawLine(x,y,x,Y);
			g.drawLine(x,Y,x+4,Y);
			if(fileMap.get(child).extended)painter(g,child);
		}
	}
	public void fillNode(DefaultMutableTreeNode node,File dir){
		File[]files=dir.listFiles();
		DefaultMutableTreeNode file;
		ArrayList<DefaultMutableTreeNode>addLater=new ArrayList<>();
		if(files!=null){
			for(File f:files){
				if(f.isDirectory()||f.getName().lastIndexOf(".png")>-1){
					file=new DefaultMutableTreeNode(f);
					if(!nodeMap.containsKey(f)){
						nodeMap.put(f,file);
						if(f.isDirectory())node.add(file);
						else addLater.add(file);
					}
					if(!fileMap.containsKey(file))fileMap.put(file,new file(f));
				}
			}
			for(DefaultMutableTreeNode f:addLater)node.add(f);
		}
	}
	@Override public void mouseDragged(MouseEvent ev){
		horiz.x+=ev.getX()-mx;
		vert.y+=ev.getY()-my;
		mx=ev.getX();
		my=ev.getY();
		horiz.update();
		vert.update();
		updateUI();
	}
	@Override public void mouseMoved(MouseEvent ev){
		mx=ev.getX();
		my=ev.getY();
	}
	@Override public void mouseWheelMoved(MouseWheelEvent ev){
		vert.y-=(int)(ev.getWheelRotation()*vert.M)<<4;
		vert.update();
		updateUI();
	}

}
