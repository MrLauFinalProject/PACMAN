import javax.swing.*;

public class PacManDriver {


	public static void main(String [] args) {
		
		JFrame frame = new JFrame("Pac-Man!");
		frame.setVisible(true);
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		JPanel main = new JPanel();
		frame.add(main);
		
		JButton button = new JButton("CLick here to kys");
		button.setEnabled(false);
		main.add(button);
		
		Map map = new Map();
		
		map.drawMap();
		map.play();
		
	}
}
