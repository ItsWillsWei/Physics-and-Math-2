import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Draw extends Graph{
	Font font1 = new Font("Cambria", Font.PLAIN, 20);
	
	
	public Draw(double x1, double x2, double y1, double y2){
		super(x1,x2,y1,y2);
		calculatePoints();
		
	}
	
	public Point[] calculatePoints(){
		return null;
	}
	
	@Override
	public void paintComponent(Graphics gOld) {
		Graphics2D g = (Graphics2D)gOld;
		drawRuler(g);
		
		
		
		
		
	}
	
	public void drawRectOutline(Graphics2D g, int x, int y,  int width, int height, int thick){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.fillRect(x+thick/2, y+thick/2, width-thick, height-thick);
		g.setColor(Color.BLACK);
	}
	
	
	public void drawRuler(Graphics2D g){
		g.setFont(font1);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1000);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		
		drawRectOutline(g, 50, 50, 800, 100, 5);
		int count = 1;
		
		for(int i = 90; i < 50+800; i+=80){
			g.drawLine(i, 52, i, 60);

			g.drawString(""+count, i-3, 85);
			g.drawString("cm", 88, 115);
			count++;
		}
	}

}
