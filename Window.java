import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Window extends Algorithm {
	
	 private JLabel eventLabel;
	 private Canvas canvas;
	 private int flag = 0;
	 private Point saveStart, saveFinish;
	 JFrame frame;
	
	 public Window() {
		super(new Map(70, 50));
	 }
	
	 public void init()
	{
			
		JFrame frame = new JFrame("Algorithm A*");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setLocationRelativeTo(null);
		
		frame.setLayout(new GridBagLayout());
		
		JButton button1 = new JButton("Generate map");
		JButton button2 = new JButton("Run");
		JButton button3 = new JButton("Clear way");
		
		canvas = new Canvas();
		
        GridBagConstraints c = new GridBagConstraints(); 
		c.gridx = 0;
		c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1; 
        c.weightx = 0.08;
        c.weighty = 0.05;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        Insets insets = new Insets(30, 30, 0, 15);
        c.insets = insets;
        c.ipady = 10;
        frame.add(button1, c);

        button1.addMouseListener(new GenerateListener());
                
		c.gridx = 0;
		c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;        
        c.weightx = 0.08;
        c.weighty = 0.05;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        insets = new Insets(0, 30, 0, 15);
        c.insets = insets;
        c.ipady = 10;
        frame.add(button2, c);     
 
        button2.addMouseListener(new RunListener());
        
        
        eventLabel = new JLabel();
        eventLabel.setText("123");
		c.gridx = 0;
		c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;        
        c.weightx = 0.08;
        c.weighty = 0.9;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        insets = new Insets(0, 30, 0, 15);
        c.insets = insets;
        c.ipady = 10;   
        frame.add(button3, c);     

        button3.addMouseListener(new ClearListener());        
        
   		c.gridx = 1;
		c.gridy = 0;
        c.gridheight = 3;
        c.gridwidth = 1;
        c.weightx = 0.92;
        c.fill = GridBagConstraints.BOTH;
        insets = new Insets(30, 10, 10, 10);
        c.insets = insets;
        frame.add(canvas, c);
        
        SelectStartEndListener listener = new SelectStartEndListener();
        canvas.addMouseListener(listener);
        
		frame.setVisible(true);

	}
	 @Override
	 protected Queue<Point> includeOpenSet(Queue<Point> openset, Point current) {
		 if (!(current.equals(saveStart)) && !(current.equals(saveFinish)))
			 canvas.drawPoint(new Point(current.x*10, current.y*10), Color.magenta);
		 return super.includeOpenSet(openset, current);
	 }
	 
	 @Override
	 protected Vector<Point> includeCloseSet(Vector<Point> closeset, Point current) {
		 if (!(current.equals(saveStart)) && !(current.equals(saveFinish)))
			 canvas.drawPoint(new Point(current.x*10, current.y*10), Color.pink);
		 try {
			 Thread.sleep(20);
		 }
		 catch(Exception e) {
			 
		 }
		 return super.includeCloseSet(closeset, current);
	 }
	 
	 private void printWay(Vector<Point> way) {
		 if (way == null) {
			 JOptionPane.showMessageDialog(frame, "There is not way!");
			 return;
		 }
		 
		 for (Point p : way) {
			 if (!(p.equals(saveStart)) && !(p.equals(saveFinish)))
			 canvas.drawPoint(new Point(p.x*10, p.y*10), Color.red);
		 }
	 }
	 
    public class GenerateListener implements MouseListener {
    	 
        public void mouseClicked(MouseEvent e) {
        	
        	_map.generate();
        	canvas.drawMap(_map);
        }

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
   }
    
    public class RunListener implements MouseListener {
   	 
        public void mouseClicked(MouseEvent e) {
        	if (saveStart!=null && saveFinish!=null)
        	{
        		printWay(aStar(saveStart, saveFinish));
        	}
        	else
				JOptionPane.showMessageDialog(frame, "There is no start and / or finish!");        		
        }

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
   }

    public class ClearListener implements MouseListener {
      	 
        public void mouseClicked(MouseEvent e) {
        	canvas.drawMap(_map);
         }

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
   }
    
    
    public class SelectStartEndListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Integer x = e.getX();
			Integer y = e.getY();
			
			if (_map.isExist(new Point((x-x%10)/10,(y-y%10)/10)))
			{
				if(e.getButton()==MouseEvent.BUTTON1)
				{
					saveStart = new Point((x-x%10)/10,(y-y%10)/10);
					canvas.setStart(x, y);
				}
				else
				{
					saveFinish = new Point((x-x%10)/10,(y-y%10)/10);
					canvas.setFinish(x, y);
				}
			}
			else
				JOptionPane.showMessageDialog(frame, "There's an obstacle here!");
        		
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
      	 
        
   }
    



}
