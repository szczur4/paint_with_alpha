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
		setMinimumSize(new Dimension(768,400));
		setIconImage(new ImageIcon(Objects.requireNonNull(K.class.getResource("icons/windowIcon.png"))).getImage());
		setLocationRelativeTo(null);
	}
}
