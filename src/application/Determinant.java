package application;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Determinant {
	
	
	private BigDecimal[][] matrix;
	private BigDecimal [][] determinant=new BigDecimal[2][2];
	
	
	
	private Set<Integer>set=new HashSet<>();
	
	Determinant(BigDecimal [][]m){		
		matrix=m;		
	}
	
	public BigDecimal calculateDeterminant() {
		
		BigDecimal det=BigDecimal.valueOf(0);
		if(matrix.length!=matrix[0].length) {
			return det;
		}
		else if(matrix.length==1)return matrix[0][0];
		else if(matrix.length==2) {
			det=(matrix[0][0].multiply(matrix[1][1])).subtract(matrix[0][1].multiply(matrix[1][0]));
			System.out.println(matrix[0][0]);
		}			
		else if(matrix.length>2) {
			
			int n=matrix.length;
			det=findDeterminant(n);			
			
		}		
		return det;				
	}
	
	private BigDecimal findDeterminant(int n) {
		
		
		BigDecimal det=BigDecimal.valueOf(0);		
								
		if(n<=2) {
						
			int countX=0,countY=0;
			for(Integer i=0;i<matrix.length;i++) {
				for(Integer j=matrix.length-2;j<matrix.length;j++) {
					
					if(!set.contains(i)) {
						determinant[countX][countY++]=matrix[i][j];
					}
				}
				if(countY>0) {
					countX++;
					countY=0;
				}					
			}
			
			BigDecimal up=determinant[0][0].multiply(determinant[1][1]);
			BigDecimal down=determinant[0][1].multiply(determinant[1][0]);			
			det=up.subtract(down);
			return det;
			
		}			
		else {
			BigDecimal op=BigDecimal.valueOf(1);
			int col=matrix.length-n;			
			
			for(Integer i=0;i<matrix.length;i++) {					
				if(!set.contains(i)) {
					set.add(i);
					det=det.add(op.multiply(matrix[i][col].multiply(findDeterminant(n-1))));
					op=op.multiply(BigDecimal.valueOf(-1));
					set.remove(i);					
				}
			}
				
			return det;
			
		}	
			
	}	
	

}
