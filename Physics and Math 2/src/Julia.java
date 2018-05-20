import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

public class Julia extends Graph {
	//List<Point> points;
	boolean ready;
	int[] image;
	final int MAX = 500;
	final Color[] COLORS = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA.darker()};
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"MM-dd yyyy 'at' HH'h'mm'm'ss");
	
	
	Julia(double x1, double x2, double y1, double y2) {
		super(x1, x2, y1, y2);
		ready = false;
		
		image = new int[dimension.width * dimension.height];

		calculatePoints();
		ready = true;
		repaint(0);

		// Draw
		createImage();
	}
	
	
	public void setSize(double x1, double x2, double y1, double y2){
		super.setValues(x1, x2, y1, y2);
		
		image = new int[dimension.width * dimension.height];
		calculatePoints();
		ready = true;
		repaint(0);
		
	}

	public void createImage() {
		BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);

		image.setRGB(0, 0, dimension.width, dimension.height, this.image, 0, dimension.width);
		try {
			ImageIO.write(image, "png", new File("C:\\Users\\Will\\Pictures\\Fractals\\" + DATE_FORMAT.format(new Date()) + ".png"));
		} catch (IOException e) {
			System.out.println("ERROR!");
			e.printStackTrace();
			System.out.println("ERROR!");
		}

	}

	@Override
	public double function(double x) {
		return Math.exp(-1 * Math.pow(4 * (x), 2));
	}
	
	public double gradient(double x){
		return 1.0;
		
//		if(x < 0.9)
//			return 1;
//		else
//			return Math.sqrt(6000*(1.0/3.0*x*x*x-0.95*x*x+0.9*x)-1700);
	}
	
	
	

	public int calculate(double a, double b, double c, double d, int count) {

		while (a * a + b * b < 4) {
			if (count > MAX)
				return -1;
			double aNew = a * a - b * b + c;
			double bNew = 2 * a * b + d;
			a = aNew;
			b = bNew;
			count++;
		}
		return count;

		/**
		 * else { if ((a) * (a) + (b) * (b) >= 4) return -50000; else if (count
		 * > 500) { // if ((a + c) * (a + c) + (b + d) * (b + d) < 4 && (a + c)
		 * * (a // + // c) + (b + d) * (b + d) > 3.99999999) // return 10;
		 * return 0; } // return one more count (iteration) return 1 +
		 * calculate(a * a - b * b + c, 2 * a * b + d, c, d, count + 1); }
		 */
	}

	public Point[] calculatePoints() {
		boolean mandel = true;
		// mandel = false;
		if (mandel)
			calculateMandelbrot();
		else {
			//points = new ArrayList<Point>();
//			double c = 0.3;// -0.12;//0
//			double d = -0.126;// 0.75;//0.64
//			for (int a = 0; a < dimension.width; a++) {
//				for (int b = 0; b < dimension.height; b++) {
//					double x = x1 + (x2 - x1) / dimension.width * a;
//					double y = y1 + (y2 - y1) / dimension.height * b;
//					if (calculate(x, y, c, d, 0) > 0)
//						points.add(new Point(x, y));
//				}
//			}
		}
		return null;
	}

	public void calculateMandelbrot() {
		//points = new ArrayList<Point>();
		int index = 0;
		// Start at bottom left corner
		for (int a = dimension.height-1; a>=0; a--) {
			// Go left to right (then up in rows)
			for (int b = 0; b < dimension.width; b++) {
				double x = x1 + (x2 - x1) / dimension.width * b;
				double y = y1 + (y2 - y1) / dimension.height * a;
				int red, gre, blu;
				int iter = calculate(0, 0, x, y, -1);
				//if it converges (=max)
				if (iter < 0) {
					//Black
					red = 0;
					gre = 0;
					blu = 0;
					//points.add(new Point(x, y));
				} else {
					double fraction = 1-4.0/5.0*Math.pow(4.0/5.0, iter);//Math.pow(iter/((double)MAX),2);
					red = (int)(255*gradient(fraction)*function(fraction-0.25));
					gre = (int)(255*gradient(fraction)*function(fraction-0.5));
					blu = (int)(255*gradient(fraction)*function(fraction-0.75));
				}
				image[index] = (red << 16) | (gre << 8) | (blu);
				
//				if(iter >= 0 && iter <= 5)
//					image[index] = COLORS[iter].getRGB();
				index++;
			}
			if(a % (dimension.height/10) == 0)
				System.out.printf("%.1f%% complete.%n", 100-100*a/((double)dimension.height));
		}
	}
	
	public void draw(){
		ready = true;
		repaint(0);
	}

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Trying to draw...");
		if (ready) {
			System.out.println("Drawing");
			// Draw the points
			int index = 0;
			for (int i = 0; i < dimension.height; i++) {
				for(int j = 0; j < dimension.width; j++){
					g.setColor(new Color(image[index]));
					g.drawRect(j, i, 1, 1);
					index++;
//				g.drawRect((int) ((p.x - x1) / (x2 - x1) * dimension.width),
//						(int) ((y2 - p.y) / (y2 - y1) * dimension.height), 1, 1);
				}
			}

			// Draw the axes
			g.setColor(Color.BLACK);
			// Draw y axis
			g.drawLine((int) (-x1 / dx), 0, (int) (-x1 / dx), dimension.height);
			// Draw x axis
			g.drawLine(0, (int) (y2 / dy), dimension.width, (int) (y2 / dy));
		}
	}
}
