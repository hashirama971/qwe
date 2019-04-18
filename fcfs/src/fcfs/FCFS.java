package fcfs;

import java.util.*;

public class FCFS {
	
	
	public static void main(String args[])
	{
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the number of process:");
		
		int n=sc.nextInt();
		
		int at[]= new int[n];
		int bt[]=new int[n];
		int tat[]=new int[n];
		int wt[]= new int[n];
		int ct[]= new int[n];
		int p[]=new int[n];
		float avgwt=0;
		System.out.println("enter the arrival time and burst time");
		for(int i=0;i<n;i++)
		{
			at[i]=sc.nextInt();
			bt[i]=sc.nextInt();
		p[i]=i+1;

	}
		
		for(int i=0;i<n;i++)
		{
			
			if(i==0)
			{
				ct[i]=at[i]+bt[i];
				
			}
			else
			{
					ct[i]=ct[i-1]+bt[i];
				
					
			}
			tat[i] = ct[i] - at[i] ;          
			wt[i] = tat[i] - bt[i] ;          
			avgwt += wt[i] ; 
		}
	
		
		System.out.println("\npid  arrival  brust  complete turn waiting");
		for(int  i = 0 ; i< n;  i++)
		{
			System.out.println(p[i] + "  \t " + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t"  + wt[i] ) ;
		}
		System.out.println("\naverage waiting time: "+ (avgwt/n));
		
	}

}
