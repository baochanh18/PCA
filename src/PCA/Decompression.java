package PCA;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Decompression {

	public static void main(String[] args) {
		new Decompression();
	}
	
	double[] Mean;
	int k ;
	int w ;
	int h ;
	int SL;
	ArrayList<double[]> ui;
	File file;
	double[] wi;
	
	public Decompression()
	{
		Scanner scan;
	    file = new File("Compress\\Mean.txt");
	    try {
	        scan = new Scanner(file);
	        
	        w = scan.nextInt();
	        h = scan.nextInt();
	        k = scan.nextInt();
	        SL = scan.nextInt();

	        Mean = new double[w * h * 3];
	        for(int i = 0; i < w * h * 3; i++)
	        {
	        	Mean[i] = scan.nextDouble();
	        }
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
        ui = new ArrayList<>();
        for(int i = 0; i < k; i++)
        {
        	file = new File("Compress\\u" + i+ ".txt");
    	    try {
    	        scan = new Scanner(file);
    	        double[] temp = new double[w * h * 3];
    	        for(int j = 0; j < w * h * 3; j++)
    	        {
    	        	temp[j] = scan.nextDouble();
    	        }
    	        ui.add(temp);
    	    } catch (Exception e) {
    	            e.printStackTrace();
    	    }
        }
        for(int i = 0; i < SL; i++)
        {
        	file = new File("Compress\\w" + i+ ".txt");
    	    try {
    	    	scan = new Scanner(file);
    	    	wi = new double[k];
    	        for(int j = 0; j < k; j++)
    	        {
    	        	wi[j] = scan.nextDouble();
    	        }
    	        Calculator cal = new Calculator();
    	        double[] newimg = new double[w * h * 3];
    	        for(int j = 0; j < k; j++)
    			{
    				newimg = cal.addVetor(newimg, cal.VectorMultiNumber(ui.get(j), wi[j]));
    			}
    	        newimg = cal.addVetor(newimg, Mean);
//    	        newimg = ui.get(1);
    	        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    			int temp = 0;
    			for(int j = 0; j < h; j++)
    				for(int k = 0; k < w; k++)
    				{
    					int b = temp;
    					int g = temp + 1;
    					int r = temp + 2;
    					if(Math.abs(newimg[b]) > 255)
    						newimg[b] = 255;
    					if(Math.abs(newimg[r]) > 255)
    						newimg[r] = 255;
    					if(Math.abs(newimg[g]) > 255)
    						newimg[g] = 255;
    					Color color = new Color(Math.abs((int)newimg[r]), Math.abs((int)newimg[g]), Math.abs((int)newimg[b]));
    					img.setRGB(k, j, color.getRGB());
    					temp = temp + 3;
    				}
    			File file = new File("Decompress\\"+ i +".jpg");
    			ImageIO.write(img, "jpg", file);
    	    } catch (Exception e) {
    	            e.printStackTrace();
    	    }
        }
	}

}
