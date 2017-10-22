import java.util.ArrayList;

public class Model {
	
	ArrayList<Vertex> vertices;
	ArrayList<Edge> edges;
	ArrayList<Polygon> polygons;
	double[][] adjacencyMatrix;
	Basis B;
	
	public Model() {
		
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		polygons = new ArrayList<Polygon>();
		B = new Basis();
		
	}
	
	public void translate(Vertex v) {
		
		B.translate(v);
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).add(v);
		}
		
	}
	
	public void rotate(Edge e, double theta) {
		
		B.rotate(e, theta);
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).rotate(e, theta);
		}		
		
	}
	
	public void iRotate(double theta) {		
		for (int i = 0; i < vertices.size(); i++) {
			B.iRotateVertex(vertices.get(i), theta);
		}		
	}	
	public void jRotate(double theta) {		
		for (int i = 0; i < vertices.size(); i++) {
			B.jRotateVertex(vertices.get(i), theta);
		}		
	}	
	public void kRotate(double theta) {		
		for (int i = 0; i < vertices.size(); i++) {
			B.kRotateVertex(vertices.get(i), theta);
		}		
	}
	
	public void addPolygon(Polygon P) {
		
		ArrayList<Vertex> newVertices = new ArrayList<Vertex>();
		ArrayList<Edge> newEdges = new ArrayList<Edge>();
		Vertex newV1, newV2;
		Edge newEdge;
		Polygon newP;
		
		for (int e = 0; e < P.edges.size(); e++) {

			newV1 = null;
			newV2 = null;

			for (int v = 0; v < vertices.size(); v++) {
				
				
				if (P.edges.get(e).v1.equals(vertices.get(v), 0.05)) {
					newV1 = vertices.get(v);
				}
				if (P.edges.get(e).v2.equals(vertices.get(v), 0.05)) {
					newV2 = vertices.get(v);
				}


			}
			
			if (newV1 == null) {
				newV1 = new Vertex(P.edges.get(e).v1.WX, 
									P.edges.get(e).v1.WY,
									P.edges.get(e).v1.WZ,
									P.edges.get(e).v1.c);
				vertices.add(newV1);
			}
			if (newV2 == null) {
				newV2 = new Vertex(P.edges.get(e).v2.WX, 
						P.edges.get(e).v2.WY,
						P.edges.get(e).v2.WZ,
						P.edges.get(e).v2.c);
				vertices.add(newV2);
			}
			newEdges.add(new Edge(newV1, newV2));			
		}
		
		polygons.add(new Polygon(newEdges, P.color));
		edges.addAll(newEdges);
		
	}
	
	public void union(Model M) {
				
		for (int p = 0; p < M.polygons.size(); p++) {
			
			addPolygon(M.polygons.get(p));

		}
		
	}
	
	private void generateAdjacencyMatrix(double self, double adjacent) {
		
		adjacencyMatrix = new double[polygons.size()][polygons.size()];
		double value;
		
		for (int i = 0; i < polygons.size(); i++) {
			
			for (int j = 0; j < polygons.size(); j++) {
				
				value = 0;
				if (polygons.get(i).isAdjacent(polygons.get(j))) {
					
					if (polygons.get(i).equals(polygons.get(j))) {
						value = self;
					} else {
						value = adjacent;
					}
				}
				adjacencyMatrix[i][j] = value;
				
			}
			
		}
		
	}
	
	public void computeAx(double timeStep) {
		
		double[] tempX = new double[polygons.size()];
		double v;
		
		for (int i = 0; i < polygons.size(); i++) {
			v = 0;			
			for (int j = 0; j < polygons.size(); j++) {
				v += adjacencyMatrix[i][j]*polygons.get(j).value;
			}			
			tempX[i] = v*timeStep;
		}
		
		for (int i = 0; i < polygons.size(); i++) {
			polygons.get(i).value += tempX[i];
		}
		
		//polygons.get(15).value = 500;
	}

	public static Model generateCube() {
		
		Model M = new Model();
		
		M.vertices.add(new Vertex(20, 20, -20, 0xffffffff));
		M.vertices.add(new Vertex(-20, 20, -20, 0xffffffff));
		M.vertices.add(new Vertex(-20, -20, -20, 0xffffffff));
		M.vertices.add(new Vertex(20, -20, -20, 0xffffffff));

		M.vertices.add(new Vertex(20, 20, 20, 0xffffffff));
		M.vertices.add(new Vertex(-20, 20, 20, 0xffffffff));
		M.vertices.add(new Vertex(-20, -20, 20, 0xffffffff));
		M.vertices.add(new Vertex(20, -20, 20, 0xffffffff));

		ArrayList<Edge> edgesA = new ArrayList<Edge>();
		edgesA.add(new Edge(M.vertices.get(0), M.vertices.get(1)));
		edgesA.add(new Edge(M.vertices.get(1), M.vertices.get(2)));
		edgesA.add(new Edge(M.vertices.get(2), M.vertices.get(3)));
		edgesA.add(new Edge(M.vertices.get(3), M.vertices.get(0)));		
		M.polygons.add(new Polygon(edgesA, 0xffff0000));

		ArrayList<Edge> edgesB = new ArrayList<Edge>();
		edgesB.add(new Edge(M.vertices.get(5), M.vertices.get(4)));
		edgesB.add(new Edge(M.vertices.get(4), M.vertices.get(7)));
		edgesB.add(new Edge(M.vertices.get(7), M.vertices.get(6)));
		edgesB.add(new Edge(M.vertices.get(6), M.vertices.get(5)));
		M.polygons.add(new Polygon(edgesB, 0xff0000ff));
		
		ArrayList<Edge> edgesC = new ArrayList<Edge>();
		edgesC.add(new Edge(M.vertices.get(4), M.vertices.get(0)));
		edgesC.add(new Edge(M.vertices.get(0), M.vertices.get(3)));
		edgesC.add(new Edge(M.vertices.get(3), M.vertices.get(7)));
		edgesC.add(new Edge(M.vertices.get(7), M.vertices.get(4)));
		M.polygons.add(new Polygon(edgesC, 0xffff00ff));

		ArrayList<Edge> edgesD = new ArrayList<Edge>();
		edgesD.add(new Edge(M.vertices.get(5), M.vertices.get(1)));
		edgesD.add(new Edge(M.vertices.get(1), M.vertices.get(2)));
		edgesD.add(new Edge(M.vertices.get(2), M.vertices.get(6)));
		edgesD.add(new Edge(M.vertices.get(6), M.vertices.get(5)));
		M.polygons.add(new Polygon(edgesD, 0xff00ffff));

		ArrayList<Edge> edgesE = new ArrayList<Edge>();
		edgesE.add(new Edge(M.vertices.get(4), M.vertices.get(5)));
		edgesE.add(new Edge(M.vertices.get(5), M.vertices.get(1)));
		edgesE.add(new Edge(M.vertices.get(1), M.vertices.get(0)));
		edgesE.add(new Edge(M.vertices.get(0), M.vertices.get(4)));
		M.polygons.add(new Polygon(edgesE, 0xff00ff00));

		ArrayList<Edge> edgesF = new ArrayList<Edge>();
		edgesF.add(new Edge(M.vertices.get(3), M.vertices.get(2)));
		edgesF.add(new Edge(M.vertices.get(2), M.vertices.get(6)));
		edgesF.add(new Edge(M.vertices.get(6), M.vertices.get(7)));
		edgesF.add(new Edge(M.vertices.get(7), M.vertices.get(3)));
		M.polygons.add(new Polygon(edgesF, 0xffffff00));

		for (int i = 0; i < edgesA.size(); i ++) {
			M.edges.add(edgesA.get(i));
		}
		for (int i = 0; i < edgesB.size(); i ++) {
			M.edges.add(edgesB.get(i));
		}
		for (int i = 0; i < edgesC.size(); i ++) {
			M.edges.add(edgesC.get(i));
		}
		for (int i = 0; i < edgesD.size(); i ++) {
			M.edges.add(edgesD.get(i));
		}
		for (int i = 0; i < edgesE.size(); i ++) {
			M.edges.add(edgesE.get(i));
		}
		for (int i = 0; i < edgesF.size(); i ++) {
			M.edges.add(edgesF.get(i));
		}
		
		return M;		
	}
	
	public static Model generateNNCellCube(int n) {
		
		Model M;
		Model Mwhole = new Model();
		
		if (n > 0) {
			
			ArrayList<Edge> e = new ArrayList<Edge>();
			ArrayList<Edge> eClone;
			double nInv = 1.0/((double) n);
			double x, y;
			Vertex NE, NW, SW, SE;
			ArrayList<Edge> edges = new ArrayList<Edge>();
			int offset;
			
			
			for (int face = 0; face < 6; face++) {
				
				M = new Model();
				
				for (int v = 0; v <= n; v++) {
					
					y = -(2*v*nInv - 1);
					
					for (int u = 0; u <= n; u++) {
						
						x = 2*u*nInv - 1;
						
						if (face == 0) {
							M.vertices.add(new Vertex(x, y, -1.0));							
						} else if (face == 1) {
							M.vertices.add(new Vertex(1.0, y, x));														
						} else if (face == 2) {
							M.vertices.add(new Vertex(-x, y, 1.0));														
						} else if (face == 3) {							
							M.vertices.add(new Vertex(-1.0, y, -x));							
						} else if (face == 4) {							
							M.vertices.add(new Vertex(x, -1.0, -y));							
						} else if (face == 5) {
							M.vertices.add(new Vertex(x, 1.0, y));
						}

					}
				}
				
				for (int v = 0; v < n; v++) {
					for (int u = 0; u < n; u++) {
						
						offset = v*(n+1)+u;
						
						int o1 = offset+1;
						int o2 = offset;
						int o3 = offset+n+1;
						int o4 = offset+n+2;
												
						NE = M.vertices.get(o1);
						NW = M.vertices.get(o2);
						SW = M.vertices.get(o3);
						SE = M.vertices.get(o4);
						
						e.clear();
						e.add(new Edge(NE, NW));
						e.add(new Edge(NW, SW));
						e.add(new Edge(SW, SE));
						e.add(new Edge(SE, NE));
						eClone = (ArrayList<Edge>) e.clone();
						M.edges.addAll(eClone);
						//M.polygons.add(new Polygon(eClone, 
						//		BufferedJPanel.generateInterpolatedColor(0xffff0000, 0xff0000ff,  Math.random())));
						int c = (int) (16777215.0*Math.random());
						//int c = 0xffaaaaaa;
						
						M.polygons.add(new Polygon(eClone, 0xff000000 | c));
					}
				}
				
				Mwhole.union(M);
				
			}
				
		}
		
		for (int i = 0; i < Mwhole.polygons.size(); i++) {
			Mwhole.polygons.get(i).value = Math.random()*1000 - 500;
		}
		
		for (int i = 0; i < Mwhole.vertices.size(); i++) {
			Mwhole.vertices.get(i).normalize();
			Mwhole.vertices.get(i).scale(40.0);			
		}
		
		Mwhole.generateAdjacencyMatrix(-4.0, 1.0);
		return Mwhole;
	}
	
	public void printAdjacencyMatrix() {
		
		for (int i = 0; i < adjacencyMatrix.length; i++){
			for (int j = 0; j < adjacencyMatrix.length; j++){
				System.out.print("" + adjacencyMatrix[i][j] + " & ");
			}
			System.out.println("");
			
		}
	}
}
