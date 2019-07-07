package PCA;

import java.util.ArrayList;

public class Calculator {
	
	double[] addVetor (double[]m, double[]n)
	{
		double[] result = new double[m.length];
		for(int i = 0; i < m.length; i++)
			result[i] = m[i] + n[i];	
		return result;
	}
	
	int Getk (double[]m)
	{
		int k = 0;
		double temp = 0;
		double sum = 0;
		for(int i = 0; i < m.length; i++)
			sum += m[i];
		for(int i = 0; i < m.length; i++)
		{
			if((temp/sum) <= 0.3)
			{
				temp += m[i];
				k++;
			}
			else break;
		}
		return k ;
	}
	
	void Sort (double[][]m , double[]n)
	{
		for(int i = 0; i < n.length; i++)
			for(int j = i+1; j < n.length; j++)
			{
				if(n[i] < n[j])
				{
					//doi evalue
					double temp = 0;
					temp = n[i];
					n[i] = n[j];
					n[j] = temp;
					//doi evector
					for(int k = 0; k < m.length; k++)
					{
						temp = m[k][i];
						m[k][i] = m[k][j];
						m[k][j] = temp;
					}
				}
			}
	}
	
	void Standardized (double[]m)
	{
		double sum = 0;
		for(int j = 0; j < m.length; j++)
		{
			sum += m[j] * m[j];
		}
		for(int j = 0; j < m.length; j++)
		{
			m[j] /= Math.sqrt(sum);
		}
	}
	
	ArrayList<double[]> PrivateVector (ArrayList<double[]>m, double[][]n, int k)
	{
		ArrayList<double[]> result = new ArrayList<>();
		for(int i = 0; i < k; i++)
		{
			double[] temp = new double[n.length];
			temp = MatrixMultiVector(n, m.get(i));
			result.add(temp);
		}
		return result;
	}
	
	ArrayList<double[]> GetEigenVector (double [][]m, double[]n, int k)
	{
		ArrayList<double[]> result = new ArrayList<double[]>();
		for(int i = 0; i < k; i++)
		{
			if((n[i]) == 0)
				continue;
			double[] temp = new double[m.length];
			for(int j = 0 ; j < m.length; j++)
			{
				temp[j] = m[j][i];
			}
			result.add(temp);
		}
		return result;
	}
	
	double[] MatrixMultiVector (double[][]m, double[]n)
	{
		double[] result = new double[m.length];
        for (int i = 0; i < m.length; i++)
        {
            for (int j = 0; j < m[0].length ; j++)
            {
            	result[i] += m[i][j] * n[j];
            }
            
        }
		return result;
	}
	
//	double[] GeteValues (double[][]m)
//	{
//		double[] result = new double[m.length];
//		for(int i = 0; i < m.length; i++)
//			result[i] = m[i][i];
//		return result;
//	}
	
	double[][] MatrixMultiMatrix(double [][] m, double [][] n){
        double[][] temp = new double[m.length][n[0].length];
        double sum = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < n[0].length; j++)
            {
            	for(int k = 0; k < m[0].length; k++)
            	{
            		sum += m[i][k] * n[k][j];
            	}
            	temp[i][j] = sum;
            	sum = 0;
            }
        return temp;
    }
	
	double[][] TransposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
	
//	boolean AccurateResult (double[][] m) 
//	{
//		int size = m.length;
//		for(int i = 0; i < size; i++)
//			for(int j = 0; j < size; j++)
//				if(i != j && m[i][j] >= 0.000001)
//					return false;
//		return true;
//	}
	
//	void QRdecompose (double [][] m)
//	{
//		int size = m.length;
//		for(int i = 0; i < size; i++)
//		{
//			double V[] = new double[size];
//			for(int j = 0; j < size; j++)
//				V[j] = m[j][i];
//			for(int j = 0; j < i; j++)
//			{
//				R[j][i] = MultiVector(GetCol(Q, j), GetCol(m, i));
//				V = DivVector(V, MulVetorvsNumber(GetCol(Q, j), R[j][i]));
//			}
//			R[i][i] = EuclidianNorm(V);
//			for(int j = 0; j < Q.length; j++)
//			{
//				Q[j][i] = V[j] / R[i][i];
//			}
//		}
//		
//	}
	
//	double EuclidianNorm (double[]v)
//	{
//		int size = v.length;
//		double result = 0;
//		for(int i = 0; i < size; i++)
//			result += v[i] * v[i];
//		return Math.sqrt(result);
//	}
	
	double VectorMultiVector (double[]m , double[]n)
	{
		double result = 0;
		for(int i = 0; i < m.length; i++)
			result += m[i] * n[i];
		return result ;
	}
	
//	double[] SubVector (double[]m, double[]n)
//	{
//		double[] result = new double[m.length];
//		for(int i = 0; i < m.length; i++)
//			result[i] = m[i] - n[i];
//		return result;
//	}
	
	double[] GetCol (double[][]m, int index)
	{
		double[] result = new double[m.length];
		for(int i = 0; i < m.length; i++)
			result[i] = m[i][index];
		return result;
	}
	
	double[] VectorMultiNumber (double[]m, double n)
	{
		double[] result = new double [m.length];
		for(int i = 0; i < m.length; i++)
			result[i] = m[i] * n;
		return result;
	}
}
