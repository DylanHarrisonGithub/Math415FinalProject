import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;


public class test extends JFrame {

	BufferedJPanel panel;
	ArrayList<Polygon> zSorted;
	Basis B;
	Model M;
	Edge xAxis = new Edge(new Vertex(0, 0, 0), new Vertex(1, 0, 0));
	Edge yAxis = new Edge(new Vertex(0, 0, 0), new Vertex(0, 1, 0));
	Edge zAxis = new Edge(new Vertex(0, 0, 0), new Vertex(0, 0, 1));
	double tt;
	double minV, maxV;
	double initMinV, initMaxV;
	double tf, n;
	int iteration;
	double time;
	int numSamples;
	
	public test() {
		
		panel = new BufferedJPanel();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(512, 512);
		panel.backgroundColor = 0xffddeeff;
		add(panel);
		setVisible(true);
		
		// iterate from time t = 0 to tf
		// in n steps
		tf = 1.0;
		n = 1000;
		M = Model.generateNNCellCube(8);
		time = 0;
		iteration = 0;
		numSamples = 20;

		//M.printAdjacencyMatrix();
		
		initMinV = 99999;
		initMaxV = -99999;
		for (int i = 0; i < M.polygons.size(); i++) {
			
			Polygon p = M.polygons.get(i);
			
			if (p.value > initMaxV) {
				initMaxV = p.value;
			}
			
			if (p.value < initMinV) {
				initMinV = p.value;
			}
			
		}
		zSorted = new ArrayList<Polygon>();
		
		B = new Basis();
		B.setOrigin(new Vertex(0, 0, -100.0));
				
		Timer animationTimer = new Timer(10, animate);
		animationTimer.setRepeats(true);
		animationTimer.start();
		
	}
	
	public static void main(String[] args) {

		new test();
		
	}

	ActionListener animate = new ActionListener() {
		
		Polygon p;
		
		@Override
		public void actionPerformed(ActionEvent e) {
						
			M.rotate(zAxis, Math.PI/256.0);
			M.rotate(yAxis, Math.PI/150.0);
			B.transform(M);
			panel.computeScreenCoords(M);
			
			if (time < tf) {
				M.computeAx(tf/n);
				System.out.println("iteration: " + iteration + ", time: " + time + ", (min, max): " + "(" +minV + ", " + maxV +")");
				time = ((double)iteration)*tf/n;
				iteration++;
				
				if (time >= tf) {
					for (int i = 0; i < M.polygons.size(); i++) {
						System.out.println("v{"+(i+1)+"}" + " = [" + M.polygons.get(i).history + "];");						
					}
				}
			}

			
			minV = 999999;
			maxV = -999999;
			zSorted.clear();
			for (int j = 0; j < M.polygons.size(); j++) {
				
				p = M.polygons.get(j);
				
				if (p.value > maxV) {
					maxV = p.value;
				}
				
				if (p.value < minV) {
					minV = p.value;
				}
				
				int k = 0;
				while ((k < zSorted.size()) && (p.midPoint().TZ < zSorted.get(k).midPoint().TZ)) {
					k++;
				}
				
				if ((iteration % (n / numSamples)) == 0) {
					p.history += " " + p.value;
				}
				
				zSorted.add(k, p);							
			}

			panel.cls();
			for (int j = 0; j < zSorted.size(); j++) {
				
				int tempC = zSorted.get(j).color;
				double t = (zSorted.get(j).value - initMinV) / (initMaxV - initMinV);

				zSorted.get(j).color = BufferedJPanel.generateInterpolatedColor(0xff0000ff, 
						0xffff0000,
						t);

				panel.fillPolygon(zSorted.get(j));
				zSorted.get(j).color = tempC;
				
			}
			
			panel.repaint();
			
						
		}

	};
}
