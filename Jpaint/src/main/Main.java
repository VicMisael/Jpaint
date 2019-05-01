package main;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main {
	static Display d;
	static Canvas c;
	static Thread t;
	static JFrame jf;
	
	static boolean shuffleMode=false;
	static boolean stopped;
	static boolean secondButtonClicked = false;
	static boolean isClicked = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		stopped = false;
		jf = new JFrame();
		jf.setSize(800, 600);
		d = new Display(800, 600);
		c = new Canvas();
		jf.add(c);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setTitle("JPaint");
		jf.pack();
		t = new Thread(new renderer());
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopped = true;
				try {
					t.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(9);
			}
		});
		c.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar()=='s') {
					shuffleMode=!shuffleMode;
					d.zeroScreen();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		c.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				secondButtonClicked = (e.getButton() == 3);
			}

		});

		c.addMouseMotionListener(new MouseAdapter() {

			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if (!secondButtonClicked) {
					d.drawAtPoint(x, y);
				} else {
					d.blackenAtPoint(x, y);

				}
			}

		});

		BufferStrategy bs = c.getBufferStrategy();
		if (bs == null) {
			c.createBufferStrategy(2);
		}
		t.start();

	}

	public static BufferedImage getScreen() {

		BufferedImage bimage = new BufferedImage(d.WIDTH, d.HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < d.HEIGHT; y++) {
			for (int x = 0; x < d.WIDTH; x++) {
				bimage.setRGB(x, y, d.pixels[x + y * d.WIDTH]);
			}
		}

		return bimage;
	}

	public static class renderer implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!stopped) {
				c.getGraphics().drawImage(getScreen(), 0, 0, null);
				if(shuffleMode) {
					d.shuffleScreen();
				}else {
					
				}
			}
		}
	}

}
