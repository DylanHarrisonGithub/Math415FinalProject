import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class BufferedJPanel extends JPanel {

	private BufferedImage img;
	private int[] pixBuffer;
	private int panelResX;
	private int panelResY;
	private int panelHalfResX;
	private int panelHalfResY;
	private int bufferResX;
	private int bufferResY;
	private int bufferHalfResX;
	private int bufferHalfResY;
	private double bufferUnitsPerPanelUnitsX;
	private double bufferUnitsPerPanelUnitsY;
	private int mx;
	private int my;
	private boolean fixedRes;
	public int backgroundColor = 0xff000000;
	public int foregroundColor = 0xff0000ff;
	private ComponentAdapter resizeListener;
	
	public BufferedJPanel() {
		
		fixedRes = false;
		img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		pixBuffer = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		updateBufferedJPanelResolutionInfo();

		addMouseMotionListener(new MouseAdapter() {
	        public void mouseMoved(MouseEvent e) {
	        	mx = (int) (e.getX()*bufferUnitsPerPanelUnitsX);
	        	my = (int) (e.getY()*bufferUnitsPerPanelUnitsY);
	        }
	        
	        public void mouseDragged(MouseEvent e) {
	        	
	        	mx = (int) (e.getX()*bufferUnitsPerPanelUnitsX);
	        	my = (int) (e.getY()*bufferUnitsPerPanelUnitsY);

	        	pSet(mx, my, foregroundColor);
	        	repaint();
	        	
	        }	        
		});
		
		addResizeListener();
		
	}
	
	public BufferedJPanel(int fixedResX, int fixedResY) {
		
		if (fixedResX < 1)
			fixedResX = 1;
		if (fixedResY < 1)
			fixedResY = 1;
		fixedRes = true;
		bufferResX = fixedResX;
		bufferResY = fixedResY;
		bufferHalfResX = bufferResX/2;
		bufferHalfResY = bufferResY/2;
		updateBufferedJPanelResolutionInfo();
		panelResX = 1;
		panelResY = 1;
		
		img = new BufferedImage(bufferResX, bufferResY, BufferedImage.TYPE_INT_ARGB);
		pixBuffer = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		addMouseMotionListener(new MouseAdapter() {
	        public void mouseMoved(MouseEvent e) {
	        	mx = (int) (e.getX()*bufferUnitsPerPanelUnitsX);
	        	my = (int) (e.getY()*bufferUnitsPerPanelUnitsY);
	        }
	        
	        public void mouseDragged(MouseEvent e) {

	        	mx = (int) (e.getX()*bufferUnitsPerPanelUnitsX);
	        	my = (int) (e.getY()*bufferUnitsPerPanelUnitsY);

	        	pSet(mx, my, foregroundColor);
	        	repaint();
	        	
	        }	        
		});
		
		addResizeListener();
				
	}
	
	private void addResizeListener() {
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				
				updateBufferedJPanelResolutionInfo();
				
				if (!fixedRes) {
					BufferedImage oldImg = img;
					img = new BufferedImage(panelResX, 
										panelResY, 
										BufferedImage.TYPE_INT_ARGB);
					pixBuffer = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
					cls();
					img.getGraphics().drawImage(oldImg, 0,  0,  null);
				}
	
			}			
		});
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (fixedRes) {
			g.drawImage(img.getScaledInstance(panelResX, panelResY, Image.SCALE_FAST), 0, 0, null);
		} else {
			g.drawImage(img, 0, 0, null);
		}		

	}

	public int[] getPixBuffer() {
		return pixBuffer;
	}

	public int getBufferResX() {
		return bufferResX;
	}

	public int getBufferResY() {
		return bufferResY;
	}

	public int getBufferHalfResX() {
		return bufferHalfResX;
	}

	public int getBufferHalfResY() {
		return bufferHalfResY;
	}

	public double getBufferUnitsPerPanelUnitsX() {
		return bufferUnitsPerPanelUnitsX;
	}

	public double getBufferUnitsPerPanelUnitsY() {
		return bufferUnitsPerPanelUnitsY;
	}

	public int getMx() {
		return mx;
	}

	public int getMy() {
		return my;
	}
	
	public void cls(int color) {
		
		Arrays.fill(pixBuffer, color);
		
	}
	
	public void cls() {
		
		Arrays.fill(pixBuffer, backgroundColor);
	
	}
	
	public void pSet(int x, int y, int c) {
		int offset = x + y*bufferResX;
		
		if (offset < (bufferResX*bufferResY)) {
			pixBuffer[offset] = c;
		}
	}
	
	public int pGet(int x, int y) {
		
		int offset = x + y*bufferResX;
		int pix = -1;
		
		if (offset < (bufferResX*bufferResY)) {
			pix = pixBuffer[offset];
		}
		
		return pix;
	}
	
	public void hLine(int y, int x1, int x2, int c) {
		
		if ((y >= 0) &(y < bufferResY)) {
			if (x1 > x2) {
				int tmp = x2;
				x2 = x1;
				x1 = tmp;
			}
			
			if (x1 < 0) {
				x1 = 0;
			}
			if (x2 > bufferResX) {
				x2 = bufferResX -1;
			}
			
			int offset = y*bufferResX;
			
			for (int x = x1; x <= x2; x++) {
				pixBuffer[offset + x] = c;
			}
		}
		
	}
	
	public void fillPolygon(Polygon p) {
		
		if (p != null) {
			EdgeMesh mesh = new EdgeMesh();
			mesh.scanEdges(p.edges);
			Vertex v1, v2;
			int u;
			
			for (int v = 0; v < mesh.height(); v++) {
				
				u = 0;
				if ((mesh.width(v) % 2) == 0) {
					while (u < mesh.width(v)) {
						
						v1 = mesh.get(u, v);
						v2 = mesh.get(u+1, v);
						
						hLine(v1.SY, v1.SX, v2.SX, p.color);
						//System.out.println("" + v1.SX + ", " + v2.SX);
						u += 2;
						
					}					
				}

			}			
		}
		
	}
	
	private void updateBufferedJPanelResolutionInfo() {
		
		panelResX = getWidth();
		panelResY = getHeight();
		panelHalfResX = panelResX / 2;
		panelHalfResY = panelResY / 2;
		
		if (!fixedRes) {
			bufferResX = panelResX;
			bufferResY = panelResY;
			bufferHalfResX = panelHalfResX;
			bufferHalfResY = panelHalfResY;			
		} 
		
		bufferUnitsPerPanelUnitsX = ((double) bufferResX) / ((double) panelResX);
		bufferUnitsPerPanelUnitsY = ((double) bufferResY) / ((double) panelResY);
		
	}
	
	public static int generateInterpolatedColor(int c1, int c2, double t) {
		
		int c1R = (c1 >> 16) & 0xFF;
		int c1G = (c1 >> 8) & 0xFF;
		int c1B = c1 & 0xFF;
		int c2R = (c2 >> 16) & 0xFF;
		int c2G = (c2 >> 8) & 0xFF;
		int c2B = c2 & 0xFF;
		int c1A = (c1 >> 24) & 0xFF;
		int c2A = (c2 >> 24) & 0xFF;
		
		int a;
		int r;
		int g;
		int b;
		
		a = c1A + (int) (t * (c2A - c2A));
		r = c1R + (int) (t * (c2R - c1R));
		g = c1G + (int) (t * (c2G - c1G));
		b = c1B + (int) (t * (c2B - c1B));
		
		return ((a & 0xFF) << 24) + ((r & 0xFF) << 16) + ((g & 0xFF) << 8) + (b & 0xFF);

	}
	
	
	public static int generateInterpolatedColor(int c1, int c2, double t, int alpha) {
		// explicit alpha
		
		int c1R = (c1 >> 16) & 0xFF;
		int c1G = (c1 >> 8) & 0xFF;
		int c1B = c1 & 0xFF;
		int c2R = (c2 >> 16) & 0xFF;
		int c2G = (c2 >> 8) & 0xFF;
		int c2B = c2 & 0xFF;
		int r;
		int g;
		int b;
		
		r = c1R + (int) (t * (c2R - c1R));
		g = c1G + (int) (t * (c2G - c1G));
		b = c1B + (int) (t * (c2B - c1B));
		
		return ((alpha & 0xFF) << 24) + ((r & 0xFF) << 16) + ((g & 0xFF) << 8) + (b & 0xFF);

	}

	
	public void computeScreenCoords(Vertex v) {

		double vzReciporocal;

		if (v.TZ > 10) {
			
			vzReciporocal = 256.0 / (v.TZ);
			
			v.SX = (int) (v.TX*vzReciporocal) + bufferHalfResX;
			v.SY = (int) (-v.TY*vzReciporocal) + bufferHalfResY;
			
		} else {
			v.SX = -1;
			v.SY = -1;
		}

	}
	
	public void computeScreenCoords(Model M) {

		for (int i = 0; i < M.vertices.size(); i++) {
			computeScreenCoords(M.vertices.get(i));
		}

	}

}
