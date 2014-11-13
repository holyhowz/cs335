package alignDNA;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class AlignmentDisplay extends JFrame{
	private JTextField t1;
	private JTextField t2;
	
	public AlignmentDisplay(JTextField t1, JTextField t2){
		super("Alignment");
		this.t1= t1;
		this.t2= t2;
		add(t1);
		add(t2);
		setSize(480, 480);
		setVisible(true);
		return;
	}
}
