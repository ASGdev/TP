package grapher.fc;
import static java.lang.Math.*;
public class DynamicFunction implements Function {
	public String toString() { return "sin(x)"; }
	public double y(double x) { return sin(x); }
}
