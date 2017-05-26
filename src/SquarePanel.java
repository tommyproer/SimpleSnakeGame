import java.awt.Color;
import javax.swing.JPanel;

public class SquarePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public SquarePanel(Color d){
		this.setBackground(d);
	}
	
	public void changeColor(Color d){
		this.setBackground(d);
		this.repaint();
	}
	
}

