package banker;

import java.util.Scanner;

public class BankerAlgo {
	int np,nr,alloc[][],max[][],need[][],avail[],comp[],order[];
	void getData(){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter no of processess");
		np = sc.nextInt();
		System.out.println("Enter no of resources");
		nr = sc.nextInt();
		alloc = new int[np][nr];
		max = new int[np][nr];
		need = new int[np][nr];
		avail = new int[nr];
		
		System.out.println("Enter allocation matrix");
		for(int i=0;i<np;i++){
			for(int j=0;j<nr;j++){
				alloc[i][j] = sc.nextInt();
			}
		}
		System.out.println("Enter max matrix");
		for(int i=0;i<np;i++){
			for(int j=0;j<nr;j++){
				max[i][j] = sc.nextInt();
			}
		}
		comp = new int[np];
		order = new int[np];
		System.out.println("Enter available vector");
		for(int i=0;i<nr;i++){
			avail[i]=sc.nextInt();
		}
		for(int i=0;i<np;i++){
			comp[i]=999;
		}
		for(int i=0;i<np;i++){
			for(int j=0;j<nr;j++){
				need[i][j]=max[i][j]-alloc[i][j];
			}
		}
	}
	public int getProcess(int k){
		int p=0;
		for(int i=k;;i=(i+1)%np){
			int flag=0;
			for(int j=0;j<nr;j++){
				if(need[i][j]<=avail[j] && comp[i]==999){
					flag++;
				}
			}
			if(flag==nr){
				System.out.println(i);
				return i;
			}
			
			if(p==np){
				System.out.println("404");
				return 404;
				
			}
			p++;
		}
	
	}
	public boolean safetyAlgo(){
		
		for(int i=0;i<np;){
			for(int j=0;;){
				
				j = getProcess(j);
				System.out.println(j);
				if(j==404){
					return false;
				}else{
					
					for(int k=0;k<nr;k++){
						avail[k] = avail[k] + alloc[j][k];
						System.out.println("===="+avail[k]);
						alloc[j][k]=0;
					}
					comp[j]=i;
					order[i]=j;
					i++;
				}
				
				if(i==np){
					break;
				}
			}
			
			
		}
		return true;
	}
	public static void main(String[] args) {
		BankerAlgo a = new BankerAlgo();
		a.getData();
		boolean safe = a.safetyAlgo();
		if(safe==true){
			System.out.println("safe");
			for(int i=0;i<a.np;i++){
				System.out.println(a.order[i]);
			}
		}else{
			System.out.println("not safe");
		}
	}
}
