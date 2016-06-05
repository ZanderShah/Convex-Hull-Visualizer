import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame
{
	public static ConvexHull convexHull;

	public Test()
	{
		super("Convex Hull Visualizer");
		Panel p = new Panel();
		setResizable(false);
		setContentPane(p);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	static class Panel extends JPanel implements MouseListener
	{
		public Panel()
		{
			setPreferredSize(new Dimension(750, 750));
			setFocusable(true);
			addMouseListener(this);
		}

		public void paintComponent(Graphics g)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			convexHull.draw(g);
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			convexHull.addPoint(new Point(e.getX(), e.getY()));
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}
	}

	public static void main(String[] args)
	{
		convexHull = new ConvexHull();
		Test convexHullVisualizer = new Test();
		convexHullVisualizer.setVisible(true);
	}

}
