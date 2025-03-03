package szczur4.paint;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;
public class frame extends JFrame{
	frame(){
		super("szczur4.paint with alpha v1.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(804,500));
		setIconImage(Objects.requireNonNull(resourceLoader.load("window.png")).getImage());
		setLocationRelativeTo(null);
	}
}
