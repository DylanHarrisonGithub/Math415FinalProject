
public class M4D {

	//4x4 double matrices
	private double[][] M;
	
	public M4D() {
		//Initialize 4x4 0 Matrix
		M = new double[4][4];
	}
	
	public void set(int i, int j, double v) {
		//set i-th row and j-th column
		//to v, where rows and columns
		//are counted beginning with one.
		if ((i > 0) & (i < 5) & (j > 0) & (j < 5)) {
			M[i-1][j-1] = v;
		}
	}
	
	public double get(int i, int j) {
		//get entry from i-th row and 
		//j-th column, where rows and
		//columns are counted beginning
		//with one.
		if ((i > 0) & (i < 5) & (j > 0) & (j < 5)) {
			return M[i-1][j-1];
		} else {
			return M[0][0];
		}
	}
	
	public void scale(double s) {
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				M[i][j] *= s;
			}
		}
		
	}
	
	public double det() {		

		//determinant by expansion along top row
		//and top row of submatrices.
		double d = 0;
		double d22332332 = M[2][2]*M[3][3] - M[2][3]*M[3][2];
		double d21332331 = M[2][1]*M[3][3] - M[2][3]*M[3][1];
		double d21322231 = M[2][1]*M[3][2] - M[2][2]*M[3][1];
		double d20332330 = M[2][0]*M[3][3] - M[2][3]*M[3][0];
		double d20322230 = M[2][0]*M[3][2] - M[2][2]*M[3][0];
		double d20312130 = M[2][0]*M[3][1] - M[2][1]*M[3][0];
		
		d = M[0][0]*(
				M[1][1]*d22332332
				- M[1][2]*d21332331
				+ M[1][3]*d21322231);
		
		d -= M[0][1]*(
				M[1][0]*d22332332
				- M[1][2]*d20332330
				+ M[1][3]*d20322230);
		
		d += M[0][2]*(
				M[1][0]*d21332331
				- M[1][1]*d20332330
				+ M[1][3]*d20312130);
		
		d -= M[0][3]*(
				M[1][0]*d21322231
				- M[1][1]*d20322230
				+ M[1][2]*d20312130);
		
		return d;

	}
	
	public M4D inverse() {
		
		M4D t = null;
		double d = det();
		
		if (d != 0) {
			t = new M4D();
			t.M[0][0] = M[1][2]*M[2][3]*M[3][1] - M[1][3]*M[2][2]*M[3][1] + M[1][3]*M[2][1]*M[3][2] - M[1][1]*M[2][3]*M[3][2] - M[1][2]*M[2][1]*M[3][3] + M[1][1]*M[2][2]*M[3][3];
			t.M[0][1] = M[0][3]*M[2][2]*M[3][1] - M[0][2]*M[2][3]*M[3][1] - M[0][3]*M[2][1]*M[3][2] + M[0][1]*M[2][3]*M[3][2] + M[0][2]*M[2][1]*M[3][3] - M[0][1]*M[2][2]*M[3][3];
			t.M[0][2] = M[0][2]*M[1][3]*M[3][1] - M[0][3]*M[1][2]*M[3][1] + M[0][3]*M[1][1]*M[3][2] - M[0][1]*M[1][3]*M[3][2] - M[0][2]*M[1][1]*M[3][3] + M[0][1]*M[1][2]*M[3][3];
			t.M[0][3] = M[0][3]*M[1][2]*M[2][1] - M[0][2]*M[1][3]*M[2][1] - M[0][3]*M[1][1]*M[2][2] + M[0][1]*M[1][3]*M[2][2] + M[0][2]*M[1][1]*M[2][3] - M[0][1]*M[1][2]*M[2][3];
			t.M[1][0] = M[1][3]*M[2][2]*M[3][0] - M[1][2]*M[2][3]*M[3][0] - M[1][3]*M[2][0]*M[3][2] + M[1][0]*M[2][3]*M[3][2] + M[1][2]*M[2][0]*M[3][3] - M[1][0]*M[2][2]*M[3][3];
			t.M[1][1] = M[0][2]*M[2][3]*M[3][0] - M[0][3]*M[2][2]*M[3][0] + M[0][3]*M[2][0]*M[3][2] - M[0][0]*M[2][3]*M[3][2] - M[0][2]*M[2][0]*M[3][3] + M[0][0]*M[2][2]*M[3][3];
			t.M[1][2] = M[0][3]*M[1][2]*M[3][0] - M[0][2]*M[1][3]*M[3][0] - M[0][3]*M[1][0]*M[3][2] + M[0][0]*M[1][3]*M[3][2] + M[0][2]*M[1][0]*M[3][3] - M[0][0]*M[1][2]*M[3][3];
			t.M[1][3] = M[0][2]*M[1][3]*M[2][0] - M[0][3]*M[1][2]*M[2][0] + M[0][3]*M[1][0]*M[2][2] - M[0][0]*M[1][3]*M[2][2] - M[0][2]*M[1][0]*M[2][3] + M[0][0]*M[1][2]*M[2][3];
			t.M[2][0] = M[1][1]*M[2][3]*M[3][0] - M[1][3]*M[2][1]*M[3][0] + M[1][3]*M[2][0]*M[3][1] - M[1][0]*M[2][3]*M[3][1] - M[1][1]*M[2][0]*M[3][3] + M[1][0]*M[2][1]*M[3][3];
			t.M[2][1] = M[0][3]*M[2][1]*M[3][0] - M[0][1]*M[2][3]*M[3][0] - M[0][3]*M[2][0]*M[3][1] + M[0][0]*M[2][3]*M[3][1] + M[0][1]*M[2][0]*M[3][3] - M[0][0]*M[2][1]*M[3][3];
			t.M[2][2] = M[0][1]*M[1][3]*M[3][0] - M[0][3]*M[1][1]*M[3][0] + M[0][3]*M[1][0]*M[3][1] - M[0][0]*M[1][3]*M[3][1] - M[0][1]*M[1][0]*M[3][3] + M[0][0]*M[1][1]*M[3][3];
			t.M[2][3] = M[0][3]*M[1][1]*M[2][0] - M[0][1]*M[1][3]*M[2][0] - M[0][3]*M[1][0]*M[2][1] + M[0][0]*M[1][3]*M[2][1] + M[0][1]*M[1][0]*M[2][3] - M[0][0]*M[1][1]*M[2][3];
			t.M[3][0] = M[1][2]*M[2][1]*M[3][0] - M[1][1]*M[2][2]*M[3][0] - M[1][2]*M[2][0]*M[3][1] + M[1][0]*M[2][2]*M[3][1] + M[1][1]*M[2][0]*M[3][2] - M[1][0]*M[2][1]*M[3][2];
			t.M[3][1] = M[0][1]*M[2][2]*M[3][0] - M[0][2]*M[2][1]*M[3][0] + M[0][2]*M[2][0]*M[3][1] - M[0][0]*M[2][2]*M[3][1] - M[0][1]*M[2][0]*M[3][2] + M[0][0]*M[2][1]*M[3][2];
			t.M[3][2] = M[0][2]*M[1][1]*M[3][0] - M[0][1]*M[1][2]*M[3][0] - M[0][2]*M[1][0]*M[3][1] + M[0][0]*M[1][2]*M[3][1] + M[0][1]*M[1][0]*M[3][2] - M[0][0]*M[1][1]*M[3][2];
			t.M[3][3] = M[0][1]*M[1][2]*M[2][0] - M[0][2]*M[1][1]*M[2][0] + M[0][2]*M[1][0]*M[2][1] - M[0][0]*M[1][2]*M[2][1] - M[0][1]*M[1][0]*M[2][2] + M[0][0]*M[1][1]*M[2][2];
			t.scale(1/d);
		}
		
		return t;
		
	}
	
	public M4D transpose() {
		
		M4D T = new M4D();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				T.M[i][j] = M[j][i];
			}
		}		
		
		return T;
				
	}
	
	public M4D rMul(M4D B) {
		//returns product M*B
		M4D P = new M4D();
				
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				P.M[i][j] = M[i][0]*B.M[0][j] + M[i][1]*B.M[1][j]
							+ M[i][2]*B.M[2][j] + M[i][3]*B.M[3][j];
			}
		}
		return P;
	}
	
	public void rMul(Vertex v) {
		
		v.TX = v.WX*M[0][0] + v.WY*M[0][1] + v.WZ*M[0][2] + M[0][3];
		v.TY = v.WX*M[1][0] + v.WY*M[1][1] + v.WZ*M[1][2] + M[1][3];
		v.TZ = v.WX*M[2][0] + v.WY*M[2][1] + v.WZ*M[2][2] + M[2][3];
		
	}
	
	public M4D lMul(M4D B) {
		//returns product B*M
		M4D P = new M4D();
				
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				P.M[i][j] = B.M[i][0]*M[0][j] + B.M[i][1]*M[1][j]
							+ B.M[i][2]*M[2][j] + B.M[i][3]*M[3][j];
			}
		}
		return P;
	}
	
	public String toString() {
		
		String temp = "";
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp += Double.toString(M[i][j]) + " ";
			}
			temp += "\n";
		}
		
		return temp;
	}
	
	public double detDEPRECATED() {		
		//delete me
		//determinant by expansion along top row
		//and top row of submatrices.
		double d = 0;
		double d22332332 = M[2][2]*M[3][3] - M[2][3]*M[3][2];
		
		d = M[0][0]*(
				M[1][1]*(
						M[2][2]*M[3][3] - M[2][3]*M[3][2])
				- M[1][2]*(
						M[2][1]*M[3][3] - M[2][3]*M[3][1])
				+ M[1][3]*(
						M[2][1]*M[3][2] - M[2][2]*M[3][1]));
		
		d -= M[0][1]*(
				M[1][0]*(
						M[2][2]*M[3][3] - M[2][3]*M[3][2])
				- M[1][2]*(
						M[2][0]*M[3][3] - M[2][3]*M[3][0])
				+ M[1][3]*(
						M[2][0]*M[3][2] - M[2][2]*M[3][0]));
		
		d += M[0][2]*(
				M[1][0]*(
						M[2][1]*M[3][3] - M[2][3]*M[3][1])
				- M[1][1]*(
						M[2][0]*M[3][3] - M[2][3]*M[3][0])
				+ M[1][3]*(
						M[2][0]*M[3][1] - M[2][1]*M[3][0]));
		
		d -= M[0][3]*(
				M[1][0]*(
						M[2][1]*M[3][2] - M[2][2]*M[3][1])
				- M[1][1]*(
						M[2][0]*M[3][2] - M[2][2]*M[3][0])
				+ M[1][2]*(
						M[2][0]*M[3][1] - M[2][1]*M[3][0]));
		
		return d;

	}
	
	public static M4D iR(double iTheta) {
		
		//[1   0      0     0]
		//[0 cos(t) -sin(t) 0]
		//[0 sin(t) cos(t)  0]
		//[0   0      0     1]
		M4D R = new M4D();
		
		R.set(1, 1, 1.0);
		R.set(2, 2, Math.cos(iTheta));
		R.set(2, 3, -Math.sin(iTheta));
		R.set(3, 2, Math.sin(iTheta));
		R.set(3, 3, Math.cos(iTheta));
		R.set(4, 4, 1.0);
		
		return R;
		
	}
	
	public static M4D jR(double jTheta) {
		M4D R = new M4D();

		R.set(1, 1, Math.cos(jTheta));
		R.set(1, 3, Math.sin(jTheta));
		R.set(2, 2, 1.0);
		R.set(3, 1, -Math.sin(jTheta));
		R.set(3, 3, Math.cos(jTheta));
		R.set(4, 4, 1.0);
		
		return R;
		
	}
	
	public static M4D kR(double kTheta) {
		
		M4D R = new M4D();
		
		R.set(1, 1, Math.cos(kTheta));
		R.set(1, 2, -Math.sin(kTheta));
		R.set(2, 1, Math.sin(kTheta));
		R.set(2, 2, Math.cos(kTheta));
		R.set(3, 3, 1.0);
		R.set(4, 4, 1.0);
		
		return R;
		
	}
}
