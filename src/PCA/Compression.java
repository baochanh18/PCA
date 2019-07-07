package PCA;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

public class Compression {
	
	ArrayList<double[]> L = new ArrayList<>();
	double[] Mean;
	double A[][];
	double TransposeA[][];
	double CovarianceMatrix[][];
	double eVectors[][];
	double eValues[];
	int k = 0;
	int w = 3;
	int h = 3;
	int SL = 80;
	PrintStream out = null;
	
	public Compression() throws IOException
	{
		for(int i = 1; i <= SL; i++)
		{
			File imgPath = new File("Input\\"+"picture ("+i+").jpg");
			BufferedImage bufferedImage = ImageIO.read(imgPath);  
			w = bufferedImage.getWidth();
			h = bufferedImage.getHeight();
			double[] temp = new double[w * h * 3];
			int count = 0;
			for(int j = 0; j < bufferedImage.getHeight();j++)
				for(int k = 0; k < bufferedImage.getWidth(); k++)
				{
					temp[count] = (double) (bufferedImage.getRGB(k,j)& 0xff);
					count++;
					temp[count] = (double) ((bufferedImage.getRGB(k,j) >> 8) & 0xff);
					count++;
					temp[count] = (double) ((bufferedImage.getRGB(k,j) >> 16) & 0xff);
					count++;
				}
			L.add(temp);

		}

//		tinh mean vector
		
		Mean = new double[w * h * 3];
		for(int i = 0; i < w * h * 3; i++)
		{
			for(int j = 0; j < SL; j++)
				Mean[i] += ((double)(L.get(j)[i])/SL);
		}
		

		//tru di vector ky vong

		A = new double[w * h * 3][SL];
		for(int i = 0; i < w * h * 3; i++)
		{
			for(int j = 0; j < SL; j++)
			{
				A[i][j] = L.get(j)[i] - Mean[i];

			}	
		}
		Calculator cal = new Calculator();
		TransposeA = new double[SL][w * h * 3];
		TransposeA = cal.TransposeMatrix(A);
		
		//ma tran hiep phuong sai
		CovarianceMatrix = new double[SL][SL];
		CovarianceMatrix = cal.MatrixMultiMatrix(TransposeA, A);

		//tri rieng 
		eVectors = new double[CovarianceMatrix.length][CovarianceMatrix.length];
		Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(CovarianceMatrix);
		EigenDecomposition eigenvector = new EigenDecomposition(matrix);
		eValues = eigenvector.getRealEigenvalues();
		
		//vector rieng
		RealMatrix ABC = new Array2DRowRealMatrix();
		ABC = eigenvector.getV();
		eVectors = ABC.getData();

		k = cal.Getk(eValues);
		ArrayList<double[]> leVector = new ArrayList<>();
		leVector = cal.GetEigenVector(eVectors, eValues, k);
		ArrayList<double[]> ui = new ArrayList<>();
		ui = cal.PrivateVector(leVector, A, k);
		for(int i = 0; i < ui.size(); i++)
			cal.Standardized(ui.get(i));
		for(int z = 0; z < SL; z++)
		{
			double[] wi = new double[k];
			
			for(int i = 0; i < k; i++)
			{
				wi[i] = cal.VectorMultiVector(ui.get(i), cal.GetCol(A, z));
			}				 
	        try {	            
	            	out = new PrintStream(new FileOutputStream("Compress\\w"+z+".txt"));
	            	
	            	for (int j = 0; j < wi.length; j++)
		                out.print((double) Math.floor(wi[j] * 1000000) / 1000000 +" ");
	        } catch (ArrayIndexOutOfBoundsException e) {
	            System.err.println("ArrayIndexOutOfBoundsException Error:" +
	                 e.getMessage());
	        } catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	        } finally {
	            if (out != null) {
	                out.close();
	            	}
	        }
		}
        try {
            out = new PrintStream(new FileOutputStream("Compress\\Mean.txt"));
            out.println(w + " " + h + " "+ k + " " + SL);
            for (int i = 0; i < Mean.length; i++)
                out.print((double) Math.floor(Mean[i] * 100) / 100 +" ");
            for(int i = 0; i < ui.size(); i++)
            {
            	out = new PrintStream(new FileOutputStream("Compress\\u"+i+".txt"));
            	for (int j = 0; j < ui.get(i).length; j++)
	                out.print((double) Math.floor(ui.get(i)[j] * 1000000) / 1000000 +" ");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("ArrayIndexOutOfBoundsException Error:" +
                 e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            } else {
                System.out.println("Couldn't open connection");
            }
        }
	        
	}
	
	public static void main(String[] args) throws Exception {
		new Compression();
	}

}
