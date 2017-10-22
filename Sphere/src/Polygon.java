import java.util.ArrayList;

public class Polygon {

	ArrayList<Edge> edges;
	int color;
	double value;
	String history;
	
	public Polygon(ArrayList<Edge> e, int c) {
		
		edges = e;
		color = c;
		history = "";
		
	}
	
	public Vertex midPoint() {
		
		Vertex m = new Vertex(0,0,0);
		
		for (int i = 0; i < edges.size(); i++) {
			m.TX += edges.get(i).v1.TX;
			m.TY += edges.get(i).v1.TY;
			m.TZ += edges.get(i).v1.TZ;
		}
		
		m.TX /= edges.size();
		m.TY /= edges.size();
		m.TZ /= edges.size();
		
		return m;
		
	}
	
	public boolean isAdjacent(Polygon p) {
		
		boolean adj = false;
		
		for (int i = 0; i < edges.size(); i++) {
			
			for (int j = 0; j < p.edges.size(); j++) {
				
				if (edges.get(i).undirectedEquals(p.edges.get(j))) {
					adj = true;
				}
				
			}
		}
		
		return adj;
		
	}
	
}
