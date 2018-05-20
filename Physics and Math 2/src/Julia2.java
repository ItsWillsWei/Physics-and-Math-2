import java.awt.Color;
import java.awt.Dimension;
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

public class Julia2 extends Graph {
	//List<Point> points;
	boolean ready;
	int[] image;
	final int MAX = 500;
	final Color[] COLORS = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA.darker()};
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"MM-dd yyyy 'at' HH'h'mm'm'ss");
	
	
	Julia2(double x1, double x2, double y1, double y2) {
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
	
	@Override
	public void setDimension(Dimension d){
		super.setDimension(d);
		image = new int[dimension.width * dimension.height];
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
		System.out.println("Export Finished\n");
	}

	public double function(double x, double shift) {
		//return Math.exp(-1 * Math.pow(2.5 * (x-shift), 2))*function2(x, shift);
		return Math.pow(Math.sin(Math.PI*x-shift), 2);
	}
	
	public double function2(double x, double shift){
		return (1.0/Math.exp(-1 * Math.pow(2.5 * (1-shift), 2))-1)*Math.pow(x, 20)+1;
	}
	
	public double gradient(double x){
		return 1.0;
		
//		if(x < 0.9)
//			return 1;
//		else
//			return Math.sqrt(6000*(1.0/3.0*x*x*x-0.95*x*x+0.9*x)-1700);
	}
	
	
	

	public double[] calculate(double a, double b, double c, double d, int count) {
		
		while (a * a + b * b < 256) {
			if (count > MAX)
				return new double[] {-1,-1};
			double aNew = a * a - b * b + c;
			double bNew = 2 * a * b + d;
			a = aNew;
			b = bNew;
			count++;
		}
		return new double[] {count,Math.sqrt(a*a+b*b)};

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

	public double r1(double x){
		if(x<0.5)
			return 0.25*Math.pow(Math.sin(Math.PI*x), 2)+0.5;
		else if(x<0.75)
			return 0.25*Math.pow(Math.sin(2*Math.PI*(x-0.5)), 2)+0.75;
		else return 1;
	}
	
	public double g1(double x){
		if(x<0.5)
			return 0.3*Math.pow(Math.sin(Math.PI*x), 2);
		else if(x<0.625)
			return 0.2*Math.pow(Math.sin(4*Math.PI*(x-0.5)), 2)+0.3;
		else if(x<0.75)
			return 0.5*Math.pow(Math.sin(4*Math.PI*(x-0.625)), 2)+0.5;
		return 1;
	}
	
	public double b1(double x){
		if(x<0.75)
			return 0;
		else if (x < 1)
			return Math.pow(Math.sin(2*Math.PI*(x-0.75)), 2);
		return 1;
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
				double[] iter = calculate(0, 0, x, y, 0);
				iter[0]--;
				//if it converges (=max)
				if (iter[0] < 0) {
					//Black
					red = 0;
					gre = 0;
					blu = 0;
					//points.add(new Point(x, y));
				} else {
					double mu = iter[0] - Math.log(Math.log(iter[1]))/Math.log(2);
					
					//
					double fraction;// = Math.pow(mu/MAX,0.25);
					fraction = (mu-((int)(mu/10.0))*10)/10.0;
					
					
					//fraction = 1-4.0/5.0*Math.pow(4.0/5.0, mu);//Math.pow(iter/((double)MAX),2);
					//Reverse direction
					if((int)(mu/10.0) %2 ==1)
						fraction = 1-fraction;//Reverse the gradient
					
//					red = (int)(255*gradient(fraction)*((fraction<0.25)?Math.pow(Math.cos(2*Math.PI*(fraction-0.25)), 2):Math.pow(Math.cos(2*Math.PI/3.0*(fraction-0.25)), 2)));//function(fraction,0.25));
//					gre = (int)(255*gradient(fraction)*((fraction<0.50)?Math.pow(Math.cos(Math.PI*(fraction-0.50)), 2):Math.pow(Math.cos(Math.PI*(fraction-0.50)), 2)));//function(fraction,0.5));
//					blu = (int)(255*gradient(fraction)*((fraction<0.75)?Math.pow(Math.cos(2*Math.PI/3.0*(fraction-0.75)), 2):Math.pow(Math.cos(2*Math.PI*(fraction-0.75)), 2)));//function(fraction,0.75));
//					
					red = (int)(255*gradient(fraction)*r1(fraction));//function(fraction,0.25));
					gre = (int)(255*gradient(fraction)*g1(fraction));//function(fraction,0.5));
					blu = (int)(255*gradient(fraction)*b1(fraction));//function(fraction,0.75));
					
//					if(fraction < 0.25 || fraction > 0.75)
//					{
//						int addition = (int)(255*Math.pow(Math.cos(2*Math.PI*fraction),2));
//						red += addition;
//						gre += addition;
//						blu += addition;
//					}
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
		if (ready && dimension.height <5000 && dimension.width < 5000) {
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
