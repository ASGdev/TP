package jus.poc.prodcons.v3;

public class Display {
    boolean display;
    
    public Display(boolean display){
        this.display = display;
    }
    
    /* écrit le message si display vaut true, rien sinon */
    public void disp(String s){
        if(display)
        	System.out.println(s);
    }
}
