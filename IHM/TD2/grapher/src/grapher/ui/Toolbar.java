package grapher.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class Toolbar extends JPanel
implements ActionListener {

	
	protected JTextArea textArea;
    protected String newline = "\n";
    static final private String CREATE = "create a new function";
    static final private String DELETE = "delete an existing function";

    
    public Toolbar(){
    	super(new BorderLayout());
    	JToolBar toolBar = new JToolBar("Input");
    	textArea = new JTextArea(2, 1);
        textArea.setEditable(false);
        add(toolBar, BorderLayout.PAGE_START);
    }
    
    protected void addButtons(JToolBar toolBar) {
        JButton button1 = new JButton(CREATE);
        button1.setActionCommand(CREATE);
        button1.setToolTipText(CREATE);
        button1.addActionListener(this);
        toolBar.add(button1);
 
        //second button
        JButton button2 = new JButton(DELETE);
        button2.setActionCommand(DELETE);
        button2.setToolTipText(DELETE);
        button2.addActionListener(this);
        toolBar.add(button2);
        }
        
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 String cmd = e.getActionCommand();
	     String description = "Execute";
	     if (CREATE.equals(cmd)) { //first button clicked
	            description = "create a new function";
	        } else if (DELETE.equals(cmd)) { // second button clicked
	            description = "delete an existing function";
	        }
	}
}
