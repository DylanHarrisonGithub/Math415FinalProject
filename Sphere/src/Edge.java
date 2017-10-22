
public class Edge {

	public Vertex v1, v2;
	
	public Edge(Vertex vi, Vertex vf) {
		v1 = vi;
		v2 = vf;
	}
	
	public void transpose(Vertex v) {
		
		v1.WX += v.WX;
		v1.WY += v.WY;
		v1.WZ += v.WZ;

		v2.WX += v.WX;
		v2.WY += v.WY;
		v2.WZ += v.WZ;

	}
	
	public Vertex v2minusv1() {
		return new Vertex(v2.WX - v1.WX, v2.WY - v1.WY, v2.WZ - v1.WZ);
	}
	
	public double magnitude() {
		
		Vertex v = v2minusv1();
		
		return Math.sqrt(Math.pow(v.WX, 2) + Math.pow(v.WY, 2) + Math.pow(v.WZ, 2));
		
	}
	
	public void normalize() {
		
		Vertex v = v2minusv1();
		
		v.scale(1/magnitude());
		
		v.add(v1);
		
		v2.WX = v.WX;
		v2.WY = v.WY;
		v2.WZ = v.WZ;
		
	}
	
	public double dot(Edge e) {
		
		Vertex v1 = this.v2minusv1();
		Vertex v2 = e.v2minusv1();
		
		return v1.WX*v2.WX + v1.WY*v2.WY + v1.WZ*v2.WZ;
		
	}
	
	public Edge cross(Edge e) {
		
		Vertex v1 = this.v2minusv1();
		Vertex v2 = e.v2minusv1();
		
		Vertex u1 = new Vertex(0,0,0);
		Vertex u2 = new Vertex(0,0,0);
		
		u2.WX = v1.WY*v2.WZ - v1.WZ*v2.WY;
		u2.WY = v1.WZ*v2.WX - v1.WX*v2.WZ;
		u2.WZ = v1.WX*v2.WY - v1.WY*v1.WX;
		
		return new Edge(u1, u2);
		
	}
	
	public Vertex interpolatedVertex(double t) {
		
		Vertex v = new Vertex(0,0,0);
		
			v.WX = v1.WX + t*(v2.WX - v1.WX);
			v.WY = v1.WY + t*(v2.WY - v1.WY);
			v.WZ = v1.WZ + t*(v2.WZ - v1.WZ);

			v.TX = v1.TX + t*(v2.TX - v1.TX);
			v.TY = v1.TY + t*(v2.TY - v1.TY);
			v.TZ = v1.TZ + t*(v2.TZ - v1.TZ);
			
			v.SX = (int)( v1.SX + (t*(double)(v2.SX - v1.SX)));
			v.SY = (int)( v1.SY + (t*(double)(v2.SY - v1.SY)));
			
			v.c = BufferedJPanel.generateInterpolatedColor(v1.c, v2.c, t);

		return v;
		
	}
	
	public boolean undirectedEquals(Edge e) {
		
		boolean equals = false;
		
		if ((v1.equals(e.v1)) & (v2.equals(e.v2))) {
			equals = true;
		}

		if ((v1.equals(e.v2)) & (v2.equals(e.v1))) {
			equals = true;
		}

		return equals;
		
	}
	
}
