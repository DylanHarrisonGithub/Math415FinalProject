
public class Basis {
	
	private Edge i, j, k;
	private M4D T, B, BT;
	
	public Basis() {
		
		Vertex origin = new Vertex(0,0,0);
		Vertex vI = new Vertex(1, 0, 0);
		Vertex vJ = new Vertex(0, 1, 0);
		Vertex vK = new Vertex(0, 0, 1);
				
		i = new Edge(origin, vI);
		j = new Edge(origin, vJ);
		k = new Edge(origin, vK);

		T = new M4D();
		B = new M4D();
		BT = new M4D();
		
		T.set(1, 1, 1.0);
		T.set(2, 2, 1.0);
		T.set(3, 3, 1.0);
		T.set(4, 4, 1.0);

		T.set(1, 1, 1.0);
		T.set(2, 2, 1.0);
		T.set(3, 3, 1.0);
		T.set(4, 4, 1.0);

		computeBT();
		
	}

	public Vertex getOrigin() {
		return i.v1;
	}

	public void setOrigin(Vertex o) {
		
		Edge dv = new Edge(i.v1, o);
		Vertex v = dv.v2minusv1();
		
		i.v1 = o;
		j.v1 = o;
		k.v1 = o;
		
		i.v2.add(v);
		j.v2.add(v);
		k.v2.add(v);
		
		computeBT();
	}
	
	public void translate(Vertex t) {
		i.v1.add(t);
		i.v2.add(t);
		j.v2.add(t);
		k.v2.add(t);
		computeBT();
	}
	
	public void normalize() {
		i.normalize();
		j.normalize();
		k.normalize();
		computeBT();
	}
	
	public void rotate(Edge e, double theta) {
		
		i.v1.rotate(e, theta);
		i.v2.rotate(e, theta);
		j.v2.rotate(e, theta);
		k.v2.rotate(e, theta);
		
	}
	
	public void iRotate(double theta) {
		j.v2.rotate(i, theta);
		k.v2.rotate(i, theta);
		computeBT();
	}
	public void jRotate(double theta) {
		i.v2.rotate(j, theta);
		k.v2.rotate(j, theta);
		computeBT();
	}
	public void kRotate(double theta) {
		i.v2.rotate(k, theta);
		j.v2.rotate(k, theta);
		computeBT();
	}
	
	public void iRotateVertex(Vertex v, double theta) {
		v.rotate(i, theta);
	}
	public void jRotateVertex(Vertex v, double theta) {
		v.rotate(j, theta);
	}
	public void kRotateVertex(Vertex v, double theta) {
		v.rotate(k, theta);
	}

	public void computeBT() {
		
		Vertex iVector = i.v2minusv1();
		Vertex jVector = j.v2minusv1();
		Vertex kVector = k.v2minusv1();
		
		T.set(1, 4, -i.v1.WX);
		T.set(2, 4, -i.v1.WY);
		T.set(3, 4, -i.v1.WZ);

		B.set(1, 1, iVector.WX);
		B.set(1, 2, iVector.WY);
		B.set(1, 3, iVector.WZ);
		
		B.set(2, 1, jVector.WX);
		B.set(2, 2, jVector.WY);
		B.set(2, 3, jVector.WZ);
		
		B.set(3, 1, kVector.WX);
		B.set(3, 2, kVector.WY);
		B.set(3, 3, kVector.WZ);
		
		BT = B.rMul(T);
		
	}
	
	public void transform(Vertex v) {
		BT.rMul(v);
	}
	
	public void transform(Model M) {
		
		for (int i = 0; i < M.vertices.size(); i++) {
			BT.rMul(M.vertices.get(i)); 
		}
		BT.rMul(M.B.i.v1);
		BT.rMul(M.B.i.v2);
		BT.rMul(M.B.j.v2);
		BT.rMul(M.B.k.v2);
		
	}
	
	public boolean isVisible(Polygon p) {
		
		//Edge pNorm = p.edges.get(0).cross(p.edges.get(1));		
		//double dProduct = pNorm.dot(k);
		
		//return dProduct > 0;
		
		boolean visible = true;
		
		for (int i = 0; i < p.edges.size(); i++) {
			if (p.edges.get(i).v1.TZ < 0) {
				visible = false;
			}
		}
		
		return visible;
	}
	
}
