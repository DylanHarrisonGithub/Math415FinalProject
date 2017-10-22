import java.util.ArrayList;

public class EdgeMesh {
	
	ArrayList<ArrayList<Vertex>> mesh;
	
	public EdgeMesh() {
		mesh = new ArrayList<ArrayList<Vertex>>();
	}

	private void insert(Vertex vert) {
		
		int u = 0;
		int v = 0;
		
		while ((v < mesh.size()) && (mesh.get(v).get(0).SY < vert.SY)) {
			v++;
		}
		if (v == mesh.size()) {
			mesh.add(new ArrayList<Vertex>());
			mesh.get(v).add(vert);
		} else {
			if (mesh.get(v).get(0).SY > vert.SY) {
				mesh.add(v, new ArrayList<Vertex>());
				mesh.get(v).add(vert);
			} else {
				while ((u < mesh.get(v).size()) && (mesh.get(v).get(u).SX <= vert.SX)) {
					u++;
				}
				mesh.get(v).add(u, vert);
			}
		}
	}
	
	public void scanEdges(ArrayList<Edge> edgeList) {
		
		int dy, dyPrevious;
		double dyInv;
		Vertex v;
		Edge e;
		double t;
		
		mesh.clear();
				
		if ((edgeList != null) && (edgeList.size() > 2)) {
			
			e = edgeList.get(edgeList.size()-1);
			dyPrevious = e.v2.SY - e.v1.SY;
			
			for (int i = 0; i < edgeList.size(); i++) {
				
				e = edgeList.get(i);
				dy = e.v2.SY - e.v1.SY;
				
				if (dyPrevious*dy < 0) {
					v = e.interpolatedVertex(0.0);
					v.SY = e.v1.SY;
					insert(v);
					//System.out.println("flip" + i);
				}
				
				if (dy == 0) {
					insert(e.interpolatedVertex(1.0));
				} else {
					
					if (dy > 0) {
						dyInv = 1.0 / dy;
						for (int y = e.v1.SY + 1; y <= e.v2.SY; y++) {
							t = (y - e.v1.SY)*dyInv;
							v = e.interpolatedVertex(t);
							v.SY = y;
							insert(v);
						}
					} else {
						dyInv = 1.0 / dy;
						for (int y = e.v1.SY - 1; y >= e.v2.SY; y--) {
							t = (y - e.v1.SY)*dyInv;
							v = e.interpolatedVertex(t);
							v.SY = y;
							insert(v);
						}						
					}
				}
			
				dyPrevious = dy;
				
			}		
		}
		
		//System.out.println(toString());
		
	}
	

	public int height() {
		return mesh.size();
	}
	
	public int width(int v) {
		// TODO Auto-generated method stub
		if (v < mesh.size()) {
			return mesh.get(v).size();
		} else {
			return -1;
		}
	}
	
	public Vertex get(int u, int v) {
		if ((v < mesh.size()) && (u < mesh.get(v).size())) {
			return mesh.get(v).get(u);
		} else {
			return null;
		}
	}
	
	public String toString() {
		
		String rs;
		
		rs = "";
		
		for (int v = 0; v < height(); v++) {
			for (int u = 0; u < mesh.get(v).size(); u++) {
				rs += "" + mesh.get(v).get(u).SX + ", ";
			}
			rs += "\n";
		}
		rs += "*";
		
		return rs;
	}
	
}
