import java.awt.*;
import javax.swing.JFrame;

/**
 * Sets up a graph
 * @author Will
 *
 *
 */
public class DrawMain {
	
	public DrawMain(){
		JFrame frame = new JFrame("Sketch");
		frame.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("image.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set variables
		double x1 = -2;//= -0.00505;
		double x2 = 2;//-0.00500;
		double y1 = -2;//0.01497;
		double y2 = 2;//0.01502;		
		
		//Julia
		//i -0.01,0,0.01,0.02, c=0, d=0.64
		
		//Function contentPane = new Function(x1,x2,y1,y2);
		
		Draw content = new Draw(x1,x2,y1,y2);
		content.setPreferredSize(content.dimension);
		content.setVisible(true);
		//Function contentPane = new Function(x1,x2,y1,y2);
		content.setOpaque(true); // content panes must be opaque
		frame.add(content);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		//frame.setMinimumSize(new Dimension(contentPane.getDimension().width+20, contentPane.getDimension().height+40));

		// frame should adapt to the panel size
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new DrawMain();
	}

}
