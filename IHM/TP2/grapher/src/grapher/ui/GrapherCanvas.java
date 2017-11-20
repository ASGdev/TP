package grapher.ui;

import static java.lang.Math.*;

import java.awt.List;
import java.util.Vector;

import javafx.util.converter.DoubleStringConverter;

import javafx.application.Application.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;


import grapher.fc.*;


public class GrapherCanvas extends Canvas {
	private class Automate{
		public boolean click = false;
		public boolean drag = false;
		public boolean release = true;
		public boolean G = false;
		public boolean D = false;
		
		public void reset() {
			click = false;
			drag = false;
			release = true;
			G = false;
			D = false;
		}
	}
	
	private class Style{
		
	}
	
	private class Drag{
		public double x =0;
		public double y =0;
		
		public void reset() {
			x=0;
			y=0;
		}
	}
	private class Zoom{
		public double x1;
		public double y1;
		public double x2;
		public double y2;
		public double zoom_pos=5;
		public double zoom_neg=-5;
		
		public void reset() {
			x1=0;
			y1=0;
			x2=0;
			y2=0;
		}
	}
	static final double MARGIN = 40;
	static final double STEP = 5;

	static final double WIDTH = 400;
	static final double HEIGHT = 300;
	
	private static DoubleStringConverter d2s = new DoubleStringConverter();
	
	protected double W = WIDTH;
	protected double H = HEIGHT;
	protected Automate auto;
	protected Drag drag;
	protected Zoom zoom;
	protected double xmin, xmax;
	protected double ymin, ymax;
	
	protected double zoomvaluex, zoomvaluey;
	protected Point2D center=new Point2D(WIDTH/2,HEIGHT/2);
	private Vector<Function> functions = new Vector<Function>();
	protected ObservableList<Function> observableFunctions =  FXCollections.observableList(functions);
	protected String boldFunction;
	protected double Bold = 5.0;
	protected double Default = 1.0;
	
	
	
	
	
	
	
	
	public GrapherCanvas(Parameters params) {
		super(WIDTH, HEIGHT);
		xmin = -PI/2.; xmax = 3*PI/2;
		ymin = -1.5;   ymax = 1.5;
		
		for(String param: params.getRaw()) {
			functions.add(FunctionFactory.createFunction(param));
		}
		auto = new Automate();
		drag = new Drag();
		zoom =new Zoom();
		GraphicsContext g=getGraphicsContext2D();
		
		
		this.setEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	zoom.x1=me.getX();
		    	zoom.y1=me.getY();  	
		    	drag.x = me.getX();
		    	drag.y = me.getY();

		    }
	
		});
		
		this.setEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {		    	
		    	if(!auto.drag && me.getButton() == MouseButton.PRIMARY) {
		    		System.out.println("Passe1");
		    		zoom(center,zoom.zoom_pos);
		    		auto.click=true;
		    	}else if(!auto.drag && me.getButton() == MouseButton.SECONDARY) {
		    		System.out.println("Passe2");
		    		zoom(center,zoom.zoom_neg);
		    		auto.click=true;
	//EQUIVALENT DE RELEASE POUR LES DEUX CAS EN DESSOUS (click precede d'un drag)
		    	}else if(auto.drag && auto.G ){
		    		System.out.println("Passe3");
		    		changeCursor(Cursor.DEFAULT);
		    		auto.reset();
		    		drag.reset();
		    	}else if(auto.drag && auto.D) {
		    		auto.release=true;
		    		System.out.println("Passe4");
		    		Point2D z1=new Point2D(zoom.x1,zoom.y1);
		    		Point2D z2=new Point2D(zoom.x2,zoom.y2);
		    		zoom(z1,z2);
		    		auto.reset();
		    		drag.reset();
		    		
		    	}
		    	else{
		    		
		    	}
		    }
	
		});
		this.setEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	if(!auto.drag) auto.drag=true;
		    	if(me.getButton() == MouseButton.PRIMARY) {
		    		if(!auto.G) auto.G = true;
		    		 changeCursor(Cursor.HAND);

		    		 translate(me.getX()-drag.x,me.getY()-drag.y);
		    		 drag.x=me.getX();
		    		 drag.y=me.getY();
		    		 
		    	}else if(me.getButton() == MouseButton.SECONDARY) {
		    		if(!auto.D) auto.D = true;
		    		auto.release=false;
		    		zoom.x2=me.getX();
		    		zoom.y2=me.getY();
		    		redraw();
		    		System.out.println("Mouse Drag for zoom"); 

		    	}else {
		    		  System.out.println("Mouse Drag");
		    		  
		    	} 
		    	
		    }
		    
		});
		
		this.setEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
		    public void handle(ScrollEvent me) {
		    	System.out.println("Mouse wheel"); 
	            Point2D point = new Point2D(me.getX(),me.getY());
	            double z= me.getDeltaY();
	            if(z>20.0) {
	            	z=z/(me.getDeltaY()/10);
	            }else if(z<-20) {
	            	z=-(z/(me.getDeltaY()/10));
	            }
	            System.out.println(z); 
	            zoom(point,z);
		    }
		});
		
	
	}
	
	public double minHeight(double width)  { return HEIGHT;}
	public double maxHeight(double width)  { return Double.MAX_VALUE; }
	public double minWidth(double height)  { return WIDTH;}
	public double maxWidth(double height)  { return Double.MAX_VALUE; }

	public boolean isResizable() { return true; }
	public void resize(double width, double height) {
		super.setWidth(width);
		super.setHeight(height);
		redraw();
	}	
	
	public void redraw() {
		GraphicsContext gc = getGraphicsContext2D();
		W = getWidth();
		H = getHeight();
		
		// background
		gc.clearRect(0, 0, W, H);
		
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);

		// box
		gc.save();
		gc.translate(MARGIN, MARGIN);
		W -= 2*MARGIN;
		H -= 2*MARGIN;
		if(W < 0 || H < 0) {
			return;
		}
		gc.strokeRect(0, 0, W, H);
		gc.fillText("x", W, H+10);
		gc.fillText("y", -10, 0);
		gc.beginPath();
		gc.rect(0, 0, W, H);
		gc.closePath();
		gc.clip();

	
		// plot		
		gc.translate(-MARGIN, -MARGIN);

		// x values
		final int N = (int)(W/STEP + 1);
		final double dx = dx(STEP);
		double xs[] = new double[N];
		double Xs[] = new double[N];
		for(int i = 0; i < N; i++) {
			double x = xmin + i*dx;
			xs[i] = x;
			Xs[i] = X(x);
		}
		//TRACE LE RECTANGLE DE ZOOM
	

		for(Function f: functions) {
			// y values
			double Ys[] = new double[N];
			for(int i = 0; i < N; i++) {
				Ys[i] = Y(f.y(xs[i]));
			}
			if( boldFunction == f.toString()) {
				gc.setLineWidth(Bold);
				gc.strokePolyline(Xs, Ys, N);
				gc.setLineWidth(Default);
			}
			gc.strokePolyline(Xs, Ys, N);
			
		}
		
		gc.restore(); // restoring no clipping
		
		
		// axes
		drawXTick(gc, 0);
		drawYTick(gc, 0);
		
		double xstep = unit((xmax-xmin)/10);
		double ystep = unit((ymax-ymin)/10);

		gc.setLineDashes(new double[]{ 4.f, 4.f });
		for(double x = xstep; x < xmax; x += xstep)  { drawXTick(gc, x); }
		for(double x = -xstep; x > xmin; x -= xstep) { drawXTick(gc, x); }
		for(double y = ystep; y < ymax; y += ystep)  { drawYTick(gc, y); }
		for(double y = -ystep; y > ymin; y -= ystep) { drawYTick(gc, y); }
		if(auto.drag==true && auto.D==true && auto.release==false){
			rectangle(gc,zoom.x2-zoom.x1,zoom.y2-zoom.y1);
		}
		gc.setLineDashes(null);
	
	}
	
	protected double dx(double dX) { return  (double)((xmax-xmin)*dX/W); }
	protected double dy(double dY) { return -(double)((ymax-ymin)*dY/H); }

	protected double x(double X) { return xmin+dx(X-MARGIN); }
	protected double y(double Y) { return ymin+dy((Y-MARGIN)-H); }
	
	protected double X(double x) {
		double Xs = (x-xmin)/(xmax-xmin)*W;
		return Xs + MARGIN;
	}
	protected double Y(double y) {
		double Ys = (y-ymin)/(ymax-ymin)*H;
		return (H - Ys) + MARGIN;
	}
		
	protected void drawXTick(GraphicsContext gc, double x) {
		if(x > xmin && x < xmax) {
			final double X0 = X(x);
			gc.strokeLine(X0, MARGIN, X0, H+MARGIN);
			gc.fillText(d2s.toString(x), X0, H+MARGIN+15);
		}
	}
	
	protected void drawYTick(GraphicsContext gc, double y) {
		if(y > ymin && y < ymax) {
			final double Y0 = Y(y);
			gc.strokeLine(0+MARGIN, Y0, W+MARGIN, Y0);
			gc.fillText(d2s.toString(y), 5, Y0);
		}
	}
	
	protected static double unit(double w) {
		double scale = pow(10, floor(log10(w)));
		w /= scale;
		if(w < 2)      { w = 2; }
		else if(w < 5) { w = 5; }
		else           { w = 10; }
		return w * scale;
	}
	
	
	protected void translate(double dX, double dY) {
		double dx = dx(dX);
		double dy = dy(dY);
		xmin -= dx; xmax -= dx;
		ymin -= dy; ymax -= dy;
		redraw();
	}
	
	protected void zoom(Point2D center, double dz) {
		double x = x(center.getX());
		double y = y(center.getY());
		double ds = exp(dz*.01);
		xmin = x + (xmin-x)/ds; xmax = x + (xmax-x)/ds;
		ymin = y + (ymin-y)/ds; ymax = y + (ymax-y)/ds;
		redraw();
	}
	
	protected void zoom(Point2D p0, Point2D p1) {
		double x0 = x(p0.getX());
		double y0 = y(p0.getY());
		double x1 = x(p1.getX());
		double y1 = y(p1.getY());
		xmin = min(x0, x1); xmax = max(x0, x1);
		ymin = min(y0, y1); ymax = max(y0, y1);
		redraw();
	}
	
	protected void changeCursor(Cursor c) {
		this.setCursor(c);
	}
	
	//Public part for call
	
	public ObservableList<Function> getFunctionList() {
		return observableFunctions;
	}
	
	public void setBold(Function newValue) {
		boldFunction = newValue.toString();
		redraw();
	}

	protected void rectangle(GraphicsContext g,double x,double y){
		g.strokeRect(zoom.x1,zoom.y1, x, y);
		

	}
}
