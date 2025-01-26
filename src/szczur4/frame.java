package szczur4;

import java.util.Objects;
import javax.swing.*;

public class frame extends JFrame{
	frame(){
		super("paint with alpha v1.0-beta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Main.back);
		setLayout(null);
		setSize(600,500);
		setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getResource("icons/windowIcon.png"))).getImage());
		setLocationRelativeTo(null);
	}
}
