package main;

import java.util.Random;

public class Display {
	int pixels[];
	int WIDTH;
	int HEIGHT;
	Random r;

	public Display(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		zeroScreen();
		r = new Random();
	}

	public void zeroScreen() {
		pixels = new int[WIDTH * HEIGHT];
		for (int y = 0; y < HEIGHT; y++) {

			for (int x = 0; x < WIDTH; x++) {
				pixels[x + y * WIDTH] = 0;
			}
		}

	}

	public void shuffleScreen() {
		

		for (int y = 0; y < HEIGHT; y++) {

			for (int x = 0; x < WIDTH; x++) {
				if (x + y * WIDTH <= 1000) {
					pixels[x + y * WIDTH] = r.nextInt(0xffffff);
				} else {
					pixels[x + y * WIDTH] = pixels[(x + y * WIDTH)-1]^pixels.hashCode()^(x+y);
				}
			}
		}
	}

	public void drawAtPoint(int x, int y) {
		if (x >= WIDTH) {
			System.out.println("Error");
			x = 0;
		}
		if (y >= HEIGHT) {
			x = 0;
		}

		for (int yar = 0; yar < 5; yar++)
			for (int xar = 0; xar < 5; xar++) {

				pixels[x + xar + (yar + y) * WIDTH] = 0xfffffff;
			}

	}

	public void blackenAtPoint(int x, int y) {
		if (x >= WIDTH || x<0) {
			System.out.println("Error");
			x = 0;
		}
		if (y >= HEIGHT || y<0) {
			y = 0;
		}

		for (int yar = 0; yar < 10; yar++)
			for (int xar = 0; xar < 10; xar++) {

				pixels[x + xar + (yar + y) * WIDTH] = 0xff0000;
			}
	}

}
