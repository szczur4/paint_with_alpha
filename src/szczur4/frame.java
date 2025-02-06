package szczur4;
import java.awt.*;
import java.util.Objects;
import javax.swing.*;
public class frame extends JFrame{
	frame(){
		super("paint with alpha v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(K.back);
		setLayout(null);
		setMinimumSize(new Dimension(804,500));
		setIconImage(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/window.png"))).getImage());
		setLocationRelativeTo(null);
	}
}
