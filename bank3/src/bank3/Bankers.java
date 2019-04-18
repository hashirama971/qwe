package bank3;

import java.util.*;

public class Bankers {
	
	public static void main(String args[]){
		
		int max[][],allo[][],need[][],avail[][],totres[][];
		
		Scanner sc= new Scanner(System.in);
		System.out.println("enter the no of process and resource no.");
		int n=sc.nextInt();
		int r=sc.nextInt();
		
		
		
		need=new int[n][r];
		allo=new int[n][r];
		max=new int[n][r];
		totres=new int[1][r];
		avail=new int[1][r];
		int check[]=new int[n];
		int flow[]=new int[n];
		int f=0;
		
		
		
		System.out.println("enter total resource no.");
		for(int i=0;i<r;i++){
			totres[0][i]=sc.nextInt();
		}
		
		
		System.out.println("max resources required");
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
				max[i][j]=sc.nextInt();
			}
		}
		
		System.out.println("allocated resources ");
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
				allo[i][j]=sc.nextInt();
			}
		}
		/*--------------need count-------------*/
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
				need[i][j]=max[i][j]-allo[i][j];
			}
		}
		
		
		
		
		
		
		
		
		
		//only out --------------------------------------------
		System.out.println("max resources ");
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
				System.out.print(max[i][j] + "  "  );
			}
			  System.out.println();
		}
		
		System.out.println("allocated resources ");
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
				System.out.print(allo[i][j] + "  "  );
			}
			  System.out.println();
		}
		
		
		
		
		System.out.println("needed resources ");
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
				System.out.print(need[i][j] + "  "  );
			}
			  System.out.println();
		}
		
		
		System.out.println("tot used resouces");
		for(int i=0;i<r;i++){
			System.out.print(totres[0][i]+" ");
		}System.out.println("");
		
		
		
		int a=0,b=0,c=0;
		for(int i=0;i<n;i++){
			for(int j=0;j<r;j++){
			if(j==0){
				a+=allo[i][j];
			}
			else if(j==1){
				b+=allo[i][j];
					
			}
			else if(j==2)
			{
				c+=allo[i][j];
				
			}
			}
		}
		avail[0][0]=totres[0][0]-a;
		avail[0][1]=totres[0][1]-b;
		avail[0][2]=totres[0][2]-c;

		
		System.out.println("tot avail resouces");

		for(int i=0;i<r;i++){
			System.out.print(avail[0][i]+" ");
		}System.out.println("");
		
		/*-------------compute--------------*/
		
		while(f!=n){
			
			for(int i=0;i<n;i++){
				
				if(need[i][0]<=avail[0][0] && need[i][1]<=avail[0][1] && need[i][2]<=avail[0][2] && check[i]!=1){
					
					check[i]=1;
					flow[f]=i+1;
					f++;
					
					avail[0][0]=allo[i][0]+avail[0][0];
					avail[0][1]=allo[i][1]+avail[0][1];
					avail[0][2]=allo[i][2]+avail[0][2];
					
					
					
				}
			}
			
		}
		
		
		System.out.println("\nflow  check  ");

		for(int i=0;i<n;i++){
					
			System.out.println(  flow[i] + "\t" + check[i]) ;
				}
		
		
		
		
	}

}
