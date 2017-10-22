
public class Vertex {
	
	public double WX, WY, WZ;
	public double TX, TY, TZ;
	public int SX, SY;
	public int c;
	
	
	public Vertex(double x, double y, double z) {		
		WX = x;
		WY = y;
		WZ = z;		
	}
	
	public Vertex(double x, double y, double z, int color) {
		WX = x;
		WY = y;
		WZ = z;		
		c = color;
	}
	
	public void add(Vertex v) {
		WX += v.WX;
		WY += v.WY;
		WZ += v.WZ;
	}
	
	public void scale(double s) {
		WX *= s;
		WY *= s;
		WZ *= s;			
	}
	
	public double magnitude() {
		
		return Math.sqrt(Math.pow(WX, 2) + Math.pow(WY, 2) + Math.pow(WZ, 2));
		
	}
	
	public void normalize() {
		
		double rm = 1.0/magnitude();
		
		WX = WX*rm;
		WY = WY*rm;
		WZ = WZ*rm;
		
	}
		
	public void rotate(Edge e, double theta) {
		
		Vertex dv = e.v2minusv1();
		double cosTheta = Math.cos(theta);
		double sinTheta = Math.sin(theta);
		
		double L = Math.pow(dv.WX, 2) + Math.pow(dv.WY, 2) + Math.pow(dv.WZ,  2);
		double Lsqrt = Math.sqrt(L);
		double Lrcp = 1/L;
		
		double a = e.v1.WX;
		double b = e.v1.WY;
		double c = e.v1.WZ;
		
		double x = WX;
		double y = WY;
		double z = WZ;
		
		double u = dv.WX;
		double v = dv.WY;
		double w = dv.WZ;
		
		WX = ((a*(v*v+w*w)-u*(b*v+c*w-u*x-v*y-w*z))*(1-cosTheta)) + L*x*cosTheta + Lsqrt*(-c*v+b*w-w*y+v*z)*sinTheta;
		WY = ((b*(u*u+w*w)-v*(a*u+c*w-u*x-v*y-w*z))*(1-cosTheta)) + L*y*cosTheta + Lsqrt*(c*u-a*w+w*x-u*z)*sinTheta;
		WZ = ((c*(u*u+v*v)-w*(a*u+b*v-u*x-v*y-w*z))*(1-cosTheta)) + L*z*cosTheta + Lsqrt*(-b*u+a*v-v*x+u*y)*sinTheta;
		
		WX *= Lrcp;
		WY *= Lrcp;
		WZ *= Lrcp;
		
	}

	public String toString() {
		return "<" + Double.toString(TX) + ", "
					+ Double.toString(TY) + ", "
					+ Double.toString(TZ) + ">";
	}
	
	public boolean equals(Vertex v, double tolerance) {
		
		boolean e = true;
		
		if (Math.abs(WX - v.WX) > tolerance) {
			e = false;
		}
		if (Math.abs(WY - v.WY) > tolerance) {
			e = false;
		}
		if (Math.abs(WZ - v.WZ) > tolerance) {
			e = false;
		}
		
		return e;
		
	}

}
