package grapher.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.BasicStroke;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import static java.lang.Math.*;

import grapher.fc.*;


public class sidePanel extends JList implements MouseListener,MouseMotionListener{

	Vector<String> func = new Vector ();
	DefaultListModel<String> model =new DefaultListModel<String>();
	Grapher graph;
	public sidePanel(Vector<String> v, Grapher g) {
		func=v;
		this.addMouseListener(this);
		for(int i=0;i<func.size();i++){
			model.addElement(func.get(i));
		}
		this.setModel(model);
		this.setVisible(true);
		graph =g;
		
	}
	
	void add (String expression){
		model.addElement(expression);
	}
	
	void remove (String expression){
		model.removeElement(expression);
	}
	
	private int Yolo;
	@Override
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getButton()==MouseEvent.BUTTON1){
			String item = (String) this.getSelectedValue();
			System.out.println(item);
			graph.bold(item);
		}
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
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}