package grapher.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;


public class Main extends JFrame implements MouseListener {
	Main(String title, String[] expressions) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		Vector func= new Vector();
		Grapher grapher = new Grapher();
		for(String expression : expressions) {
			grapher.add(expression);
			func.addElement(expression);
		}
		sidePanel list=new sidePanel(func, grapher);
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,list,grapher);
		add(splitPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		
		JToolBar toolbar = new JToolBar("Expression");
		JButton button1 = new JButton("create");
		button1.setToolTipText("create");
		button1.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getButton()==MouseEvent.BUTTON1){
					JOptionPane FunctionToImplement= new JOptionPane("Create a new function");
					FunctionToImplement.showInputDialog("Write your function");
					FunctionToImplement.getInputValue();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		JButton button2 = new JButton("delete");
		button2.setActionCommand("delete");
		button2.setToolTipText("delete");
		button2.addActionListener(null);
		toolbar.add(button1);
		toolbar.add(button2);
		add(toolbar, BorderLayout.NORTH);

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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
