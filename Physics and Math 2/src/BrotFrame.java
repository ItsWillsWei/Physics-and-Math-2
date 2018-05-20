import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrotFrame extends JPanel implements ActionListener {
	// Declare global variables
	JPanel main;
	Julia2 julia;
	public final Dimension FIELD = new Dimension(100, 20);
	public final Dimension STRETCH = new Dimension(120, 20);
	public Point selected = new Point(0, 0);
	JLabel xPosLabel, yPosLabel;
	JTextField xSelectText, ySelectText;
	JCheckBox clickZoom;

	public BrotFrame(double x1, double x2, double y1, double y2) {
		super(new GridLayout(1, 0));

		setUp(x1, x2, y1, y2);

		add(main);
		julia.requestFocusInWindow();

		// Add actionlisteners
		this.addMouseMotionListener(new MouseMotionHandler());
		this.addMouseListener(new MouseHandler());

		this.setVisible(true);
	}

	public void setUp(double x1, double x2, double y1, double y2) {
		// Creates the main panel
		main = new JPanel();
		main.setLayout(new GridBagLayout());

		// Creates a Graph area to display the brot
		julia = new Julia2(x1, x2, y1, y2);
		// Create spacers
		JLabel paramLabel = new JLabel("Parameters: ");
		JLabel spacer1 = new JLabel(" ");
		JLabel spacer2 = new JLabel(" ");

		// Adds Labels and TextFields for parameters
		JLabel x1Label = new JLabel("x1: ");
		JTextField x1Text = new JTextField("-2");
		JLabel x2Label = new JLabel("x2: ");
		JTextField x2Text = new JTextField("2");
		JLabel y1Label = new JLabel("y1: ");
		JTextField y1Text = new JTextField("-2");
		JLabel y2Label = new JLabel("y2: ");
		JTextField y2Text = new JTextField("2");

		JButton resize = new JButton("Resize!");
		resize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Set the graph window size
				julia.setSize(Double.parseDouble(x1Text.getText()), //
						Double.parseDouble(x2Text.getText()), //
						Double.parseDouble(y1Text.getText()), //
						Double.parseDouble(y2Text.getText()));//
			}
		});

		JLabel coorLabel = new JLabel("Coordinates: ");
		JLabel xLabel = new JLabel("x: ");
		xPosLabel = new JLabel(" ");
		JLabel yLabel = new JLabel("y: ");
		yPosLabel = new JLabel(" ");

		JLabel selectionLabel = new JLabel("Selected Coordinates:");
		JLabel xSelectLabel = new JLabel("x: ");
		xSelectText = new JTextField("0");
		JLabel ySelectLabel = new JLabel("y: ");
		ySelectText = new JTextField("0");

		clickZoom = new JCheckBox("Click-Zoom!");

		JButton zoom = new JButton("Zoom!");
		zoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected.x = Double.parseDouble(xSelectText.getText());
				selected.y = Double.parseDouble(ySelectText.getText());
				zoom();
			}
		});

		JLabel holder1 = new JLabel(" ");
		JLabel holder2 = new JLabel(" ");
		JLabel holder3 = new JLabel(" ");
		JLabel holder4 = new JLabel(" ");
		JLabel resLabel = new JLabel("Resolution: ");
		JTextField resText = new JTextField("1000,1000");
		JButton exportButton = new JButton("Export as photo");
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String dim = resText.getText();
				int comma = dim.indexOf(',');

				double x = Integer.parseInt(dim.substring(0, comma));
				double y = Integer.parseInt(dim.substring(comma + 1));
				julia.setDimension(new Dimension((int)x, (int)y));
				// If the dimensions are not the same as the display
				if (x != 1000 && y != 1000) {
					if(x != y){
						julia.setSize(julia.x1,julia.x2,julia.y1,julia.y1+y/x*(julia.x2-julia.x1));
						//It calculates points in setSize
					}
				}
				julia.createImage();
			}

		});

		// Initializes the specifications for component placement
		GridBagConstraints constraints = new GridBagConstraints();
		julia.setPreferredSize(julia.getDimension());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 21;
		main.add(julia, constraints);
		
		// Adds the spacers
		paramLabel.setPreferredSize(STRETCH);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		main.add(paramLabel, constraints);
		spacer1.setPreferredSize(STRETCH);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		main.add(spacer1, constraints);

		// Add graph size labels
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		main.add(x1Label, constraints);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		main.add(x1Text, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		main.add(x2Label, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		main.add(x2Text, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		main.add(y1Label, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		main.add(y1Text, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridheight = 1;
		main.add(y2Label, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridheight = 1;
		main.add(y2Text, constraints);

		// Resize button
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(resize, constraints);

		// Add coordinates label
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.gridheight = 1;
		main.add(coorLabel, constraints);

		// Add Position labels
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 7;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		main.add(xLabel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 7;
		constraints.gridheight = 1;
		main.add(xPosLabel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 8;
		constraints.gridheight = 1;
		main.add(yLabel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 8;
		constraints.gridheight = 1;
		main.add(yPosLabel, constraints);

		// Add Selection label
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 9;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(selectionLabel, constraints);

		// Add Position labels
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 10;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		main.add(xSelectLabel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 10;
		constraints.gridheight = 1;
		main.add(xSelectText, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 11;
		constraints.gridheight = 1;
		main.add(ySelectLabel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 11;
		constraints.gridheight = 1;
		main.add(ySelectText, constraints);

		// Clickzoom
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 12;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(clickZoom, constraints);

		// Zoom
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 13;
		constraints.gridheight = 1;
		main.add(zoom, constraints);

		// Holders
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 14;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(holder1, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 15;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(holder2, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 16;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(holder3, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 17;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(holder4, constraints);

		// Resolution
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 18;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		main.add(resLabel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 18;
		constraints.gridheight = 1;
		main.add(resText, constraints);

		// Export button
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 19;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		main.add(exportButton, constraints);

		// Spacer
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 20;
		constraints.gridheight = 1;
		main.add(spacer2, constraints);

	}

	public void zoom() {
		double deltaX = julia.x2 - julia.x1;
		double deltaY = julia.y2 - julia.y1;
		julia.setSize(selected.x - deltaX / 4, selected.x + deltaX / 4, selected.y - deltaY / 4,
				selected.y + deltaY / 4);
	}

	private class MouseMotionHandler extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent event) {
			java.awt.Point selected = event.getPoint();

			xPosLabel.setText(String.format("  %.8f", julia.x1 + selected.getX() * julia.dx));
			yPosLabel.setText(String.format("  %.8f", julia.y2 - selected.getY() * julia.dy));

		}
	}

	private class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			java.awt.Point newSelected = event.getPoint();
			selected.x = julia.x1 + newSelected.getX() * julia.dx;
			selected.y = julia.y2 - newSelected.getY() * julia.dy;
			xSelectText.setText(String.format("%.8f", selected.x));
			ySelectText.setText(String.format("%.8f", selected.y));

			if (clickZoom.isSelected()) {
				zoom();
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
