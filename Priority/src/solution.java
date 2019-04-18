import java.util.Scanner;

public class solution {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[]args)
	{
		System.out.println("ENTER CHOICE");
		System.out.println("1.FCFS\n2.SJF\n"
				+ "3.Non-Preemptive priority\n4.Round-robin");
		int choice = sc.nextInt();
		switch(choice)
		{
			case(1):
			{
				fcfs();
				break;
			}
			case(2):
			{
				sjf();
				break;
			}
			case(3):
			{
				priority();
				break;
			}
			case(4):
			{
				roundrobin();
				break;
			}
		}
	}
	//==========================================================FCFS==================================================
	private static void fcfs() {
		// TODO Auto-generated method stub
		System.out.println("processes");
		int num_process = sc.nextInt();
		Process[] process = new Process[num_process];
		int[]burst_time =new int[num_process];
		int[]waiting_time = new int[num_process];
		int[]arrival_time =new int[num_process];
		
		//BURST TIME & ARRIVAL TIME 
			for (int i = 0; i < process.length; i++) {
				System.out.println("BURST TIME FOR "+(i+1)+" =");
				burst_time[i] =sc.nextInt();
				System.out.println("ARRIVAL TIME FOR "+(i+1)+" =");
				arrival_time[i] = sc.nextInt();
			}	
		//SORTING ACCORDING TO ARRIVAL TIME
			for (int i = 0; i < num_process; i++) {
				for (int j = 0; j < num_process-i-1; j++) {
					if(arrival_time[j]>arrival_time[j+1])
					{
						int temp = arrival_time[j];
						arrival_time[j]=arrival_time[j+1];
						arrival_time[j+1] = temp;
						
						temp = burst_time[j];
						burst_time[j]=burst_time[j+1];
						burst_time[j+1] =temp;
					}
				}
			}			
			int total =0;
			waiting_time[0] = 0;
			for (int i = 1; i < waiting_time.length; i++)
			{
				//AVERAGE WAITING TIME CALCULATION   (SERVICE TIME - ARRIVAL TIME)
				waiting_time[i] = waiting_time[i-1]+burst_time[i-1]+arrival_time[i-1]-arrival_time[i];
				total = total+waiting_time[i];
			}
			float average = (float)total/num_process;
			System.out.println("\n PROCESS\tBURST TIME \tWAIT TIME");
			for (int i = 0; i < num_process; i++) {
				System.out.println((i+1)+"\t\t"+burst_time[i]+"\t\t"+waiting_time[i]);
			}
			System.out.println("AVERAGE WAITING TIME :"+average);		
	}
	
	//===================================SHORTEST JOB FIRST==================================================================
	
	private static void sjf() {
		System.out.println("PROCESSES");
		int num_process = sc.nextInt();
		int[]arrival_time = new int[num_process];
		int[]burst_time=new int[num_process];
		int[]remaining_time = new int[num_process];
		int complete=0,time=0,shortest =0,finish_time;
		int[] waiting_time = new int[num_process];
		boolean check = false;
		int min =Integer.MAX_VALUE;
		
		//BURST TIME & ARRIVAL TIME 
		for (int i = 0; i < arrival_time.length; i++) {
			System.out.println("ARRIVAL TIME FOR "+(i+1));
			arrival_time[i] =sc.nextInt();
			System.out.println("BURST TIME FOR "+(i+1));
			burst_time[i] = sc.nextInt();
		}
		
		for (int i = 0; i < remaining_time.length; i++) {
			remaining_time[i] = burst_time[i];
		}
		while(complete!=num_process)
		{
			for (int i = 0; i < num_process; i++) {
				if((arrival_time[i]<=time) && (remaining_time[i]<min) && remaining_time[i]>0)
				{
					min = remaining_time[i];
					shortest = i;
					check  = true;
				}
			}
			if(check==false)
			{
				time++;
				continue;
			}
			remaining_time[shortest]--;
			min = remaining_time[shortest];
			if(min==0)
			{
				min =Integer.MAX_VALUE;
			}
			if(remaining_time[shortest] == 0)
			{
				complete++;
				finish_time = time+1;
				waiting_time[shortest] = finish_time-burst_time[shortest]-arrival_time[shortest];
				if(waiting_time[shortest]<0)
				{
					waiting_time[shortest]=0;
				}
			}
			time++;
		}
		
		System.out.println("===========SJF===========");
		System.out.println();
		System.out.println("BURST\tWAIT ");
		for (int i = 0; i < num_process; i++) {
			System.out.println(burst_time[i]+"\t"+waiting_time[i]);
		}
		
	}
	//===================================================NON PREEMPTIVE PRIORITY============================================
	
	private static void priority() {
	
		System.out.println("processes");
		int num_process = sc.nextInt();
		int[]burst_time =new int[num_process];
		int[]waiting_time = new int[num_process];
		int[]arrival_time =new int[num_process];
		int[]priority = new int[num_process];
		int [] TAT = new int[num_process]; 
		int[]completion_time =new int[num_process];
		//BURST TIME & ARRIVAL TIME 
			for (int i = 0; i < num_process; i++) {
				System.out.println("BURST TIME FOR "+(i+1)+" =");
				burst_time[i] =sc.nextInt();
				System.out.println("ARRIVAL TIME FOR "+(i+1)+" =");
				arrival_time[i] = sc.nextInt();
				System.out.println("PRIORITY TIME FOR "+(i+1)+" =");
				priority[i] = sc.nextInt();
			}
			//SORTING ACCORDING TO ARRIVAL TIME
			for (int i = 0; i < num_process; i++) {
				for (int j = 0; j < num_process-i-1; j++) {
					if(arrival_time[j]>arrival_time[j+1])
					{
						int temp = arrival_time[j];
						arrival_time[j]=arrival_time[j+1];
						arrival_time[j+1] = temp;
						
						temp = burst_time[j];
						burst_time[j]=burst_time[j+1];
						burst_time[j+1] =temp;
						
						temp = priority[j];
						priority[j] =priority[j+1];
						priority[j+1] =temp;
						
					}
					else if(arrival_time[j]==arrival_time[j+1])
					{
						if(priority[j]<priority[j+1])
						{
							int temp = arrival_time[j];
							arrival_time[j]=arrival_time[j+1];
							arrival_time[j+1] = temp;
							
							temp = burst_time[j];
							burst_time[j]=burst_time[j+1];
							burst_time[j+1] =temp;
							
							temp = priority[j];
							priority[j] =priority[j+1];
							priority[j+1] =temp;
							
						}
					}
				}
			}
			waiting_time[0] = 0;
			int TURN_AROUND_TIME = 0;
			int avg_waiting =0;
			
			for (int i = 1; i < completion_time.length; i++) {
				waiting_time[i] = burst_time[i-1]+waiting_time[i-1]+arrival_time[i-1]-arrival_time[i];
				
			}
			for (int i = 0; i < completion_time.length; i++) {
				completion_time[i] = waiting_time[i]+arrival_time[i]+burst_time[i];
				TAT[i] =burst_time[i]+waiting_time[i];
			}
			for (int i = 0; i < TAT.length; i++) {
				TURN_AROUND_TIME = TURN_AROUND_TIME+TAT[i];
				avg_waiting = avg_waiting+waiting_time[i];
				
			}
			System.out.println("======PRIORITY SCHEDULING ALGORITHM=========");
			
			System.out.println("Process\t\tBurst-Time\tWaiting-Time\tCompletion time\tTurnAroundTime");
			for (int i = 0; i < num_process; i++) {
				System.out.println((i+1)+"\t\t"+burst_time[i]+"\t\t"+waiting_time[i]+"\t\t"+completion_time[i]+"\t\t"+TAT[i]);
			}
			System.out.println("AVERAGE WAITING TIME => "+avg_waiting/num_process);
			System.out.println("AVERAGE TURN AROUND TIME => "+TURN_AROUND_TIME/num_process);
		
	}
	
	//============================================ROUND ROBIN=================================================
	
	private static void roundrobin() {
		
		System.out.println(" SET QUANTUM TO : ");
		int quantum = sc.nextInt();
		System.out.println("processes");
		int num_process = sc.nextInt();
		int[]burst_time =new int[num_process];
		int[]waiting_time = new int[num_process];
		int[]arrival_time =new int[num_process];
		int[]remaining_time = new int[num_process];
		int[]TAT = new int[num_process];
		int time = 0;
		
		//BURST TIME & ARRIVAL TIME 
		for (int i = 0; i < num_process; i++) {
			System.out.println("BURST TIME FOR "+(i+1)+" =");
			burst_time[i] =sc.nextInt();
			System.out.println("ARRIVAL TIME FOR "+(i+1)+" =");
			arrival_time[i] = sc.nextInt();
			remaining_time[i] = burst_time[i];
		}
			//SORTING BASED ON ARRIVAL TIME
			for (int i = 0; i < remaining_time.length; i++) {
				for (int j = 0; j < num_process-i-1; j++) {
					if(arrival_time[i]<arrival_time[j])
					{
						int temp = arrival_time[j];
						arrival_time[j] = arrival_time[j+1];
						arrival_time[j+1] =temp;
						
						temp = burst_time[j];
						burst_time[j]= burst_time[j+1];
						burst_time[j+1] = temp;
						
						temp = remaining_time[j];
						remaining_time[j] = remaining_time[j+1];
						remaining_time[j+1] =temp;
					}
				}
			}
			
			while(true)
			{
				boolean flag = false;
				boolean done = true;
				for (int i = 0; i < remaining_time.length; i++) {
					if(time>=arrival_time[i])
					{
						flag = true;
						if(remaining_time[i]>0)
						{
							done = false;
							if(remaining_time[i]>quantum)
							{
								time = time + quantum;
								remaining_time[i] = remaining_time[i]-quantum;
							}
							else
							{
								time =time + remaining_time[i];
								waiting_time[i] =time-burst_time[i];
								remaining_time[i] = 0;
							}
						}
					}
				}
				if(flag == false)
				{
					time++;
					continue;
				}
				if(done == true)
				{
					break;
				}
			}
			for (int i = 0; i < remaining_time.length; i++) {
				TAT[i] = waiting_time[i]+burst_time[i];
			}
			System.out.println("==================ROUND ROBIN====================");
			System.out.println("BURST\tWAITING TIME\tTAT ");
			for (int i = 0; i < TAT.length; i++) {
				System.out.println(burst_time[i]+"\t"+waiting_time[i]+"\t"+TAT[i]);
			}
	}
}
