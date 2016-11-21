package grapher.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class Main extends JFrame {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		String[] func={};
		Grapher grapher = new Grapher();
		int i=0;
		for(String expression : expressions) {
			grapher.add(expression);
			func[i]=expression;
			i++;
		}
		JList list=new JList(func);
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,list,grapher);
		add(splitPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		list.setMinimumSize(minimumSize);
		grapher.setMinimumSize(minimumSize);

		pack();
		
	}

	public static void main(String[] argv) {
		final String[] expressions = argv;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				new Main("grapher", expressions).setVisible(true); 
			}
		});
	}
}
