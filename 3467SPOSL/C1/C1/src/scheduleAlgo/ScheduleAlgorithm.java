package scheduleAlgo;

import java.util.Scanner;

public class ScheduleAlgorithm {
	public static void main(String[] args) {
		ScheduleAlgorithm sc = new ScheduleAlgorithm();
		Scanner scan  = new Scanner(System.in);
		
		int choice= 0;
		
		System.out.println("1.FCFS\n2.SJF\n3.Priority\n4.Round Robin\n5.SJF(Preemtive)");
		
		choice = scan.nextInt();
		
		switch(choice){
		case 1:
			sc.FCFS();
			break;
		case 2:
			sc.SJF();
			break;
		case 3:
			sc.Priority();
			break;
		case 4:
			sc.roundRobin();
			break;
		case 5:
			sc.SJFPreem();
		
		}
		scan.close();
	}

	private void SJFPreem() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of Processes");
		int pno = s.nextInt();
		System.out.println("Enter CPU burst time for each process");
		int bt[][]=new int[pno+1][2];
		int st[][]=new int[pno+2][4];
		bt[0][0]=bt[0][1]=0;
		st[0][0]=st[0][1]=st[0][2]=st[0][3]=st[0][3]=0;
		for(int i=1;i<=pno;i++){
			System.out.print("burst time for P"+i);
			bt[i][0] = s.nextInt();
			bt[i][1]=0;
			
		}
		System.out.println("Enter burst time and release time for interrupt process");
		int ibt = s.nextInt();
		int irt = s.nextInt();
		int minp = 0;
		int time = 0;
		int flag=0;
		for(int i=1;i<=pno;i++){
			minp = 9999;
			int mini=0;
			for(int j=1;j<=pno;j++){
				if(minp>bt[j][0]&&bt[j][1]==0){
					minp = bt[j][0];
					mini = j;
				}
			}
			if((time==irt||(time+bt[mini][0])>irt)&&flag==0){
				System.out.println("Process P extra"+mini);
				int r = irt-time;
				int rct = bt[i][0] - r;
				int wct = time+ibt;
				st[pno+1][0] = ibt;
				st[pno+1][1] = time+r;
				st[pno+1][2] = time+r+ibt;
				st[pno+1][3] = pno+1;
				time = time+ibt;
				System.out.println("Process P"+mini);
				st[i][0] = bt[mini][0];
				st[i][1] = wct;
				st[i][2] = wct+bt[mini][0];
				st[i][3]=mini;
				bt[mini][1]=1;
				time = time+st[i][0];
				flag=1;
			}else{
				System.out.println("Process P"+mini);
				int pretat =time ;// st[i-1][2];
				st[i][0] = bt[mini][0];
				st[i][1] = pretat;
				pretat = pretat+st[i][0];
				st[i][2] = pretat;
				st[i][3]=mini;
				bt[mini][1]=1;
				time = time+st[i][0];
			}
			
		}
		System.out.println("p\tbt\twt\ttat");
		for(int i=1;i<=pno+1;i++){
			
			System.out.println("P"+st[i][3]+"\t"+st[i][0]+"\t"+st[i][1]+"\t"+st[i][2]);
		}
		
		double sumWT=0,sumTAT=0;
		
		for(int i=1;i<=pno+1;i++){
			sumWT =sumWT+st[i][1];
			sumTAT =sumTAT+st[i][2];
		}
		double avgWT =  sumWT/(double)(pno+1);
		double avgTAT = sumTAT/(double)(pno+1);
		System.out.println("Average Waiting Time"+avgWT);
		System.out.println("Average Turn Around Time"+avgTAT);
		
	}

	private void roundRobin() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter contant round time");
		int rtime = s.nextInt();
		System.out.println("Enter number of Processes");
		int pno = s.nextInt();
		System.out.println("Enter CPU burst time for each process");
		int bt[][]=new int[pno+1][4];
		bt[0][0]=bt[0][1]=bt[0][2]=bt[0][3]=0;
		int tmap=0;
		for(int i=1;i<=pno;i++){
			System.out.print("burst time for P"+i);
			bt[i][0] = s.nextInt();
			bt[i][1] =0;
			bt[i][2]=0;
			bt[i][3]=bt[i][0];
			if(bt[i][3]<rtime&&bt[i][3]!=0){
				tmap = tmap+bt[i][3];
				bt[i][1]= tmap-bt[i][0];
				bt[i][2]= bt[i][1] + bt[i][0];
				bt[i][3] = 0; 
			}else if(bt[i][3]>rtime){
				tmap = tmap + rtime;
				bt[i][3] = bt[i][3]-rtime;
			}
		}
		int zeroc=0;
		while(zeroc<pno){
			//zeroc=pno;
			for(int i=1;i<pno;i++){
				if(bt[i][3]==0){
						zeroc++;
				}else if(bt[i][3]<=rtime){
					tmap = tmap+bt[i][3];
					bt[i][1]= tmap-bt[i][0];
					bt[i][2]= bt[i][1] + bt[i][0];
					bt[i][3]=0;
					zeroc++;
				}else if(bt[i][3]>rtime){
					tmap = tmap + rtime;
					bt[i][3] = bt[i][3]-rtime;
				}
			}
			
			System.out.println(zeroc);
		}
		System.out.println("p\tb\twt\ttat");
		for(int i=1;i<=pno;i++){
			
			System.out.println("P"+i+"\t"+bt[i][0]+"\t"+bt[i][1]+"\t"+bt[i][2]+"\t"+bt[i][3]);
		}
		
		int sumWT=0,sumTAT=0;
		
		for(int i=1;i<=pno;i++){
			sumWT =sumWT+bt[i][1];
			sumTAT =sumTAT+bt[i][2];
		}
		
		System.out.println("Average Waiting Time"+((double)sumWT/(double)pno));
		System.out.println("Average Turn Around Time"+((double)sumTAT/(double)pno));
	}

	private void Priority() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of Processes");
		int pno = s.nextInt();
		System.out.println("Enter CPU burst time for each process");
		int bt[][]=new int[pno+1][3];
		int st[][]=new int[pno+1][5];
		bt[0][0]=bt[0][1]=bt[0][2]=0;
		st[0][0]=st[0][1]=st[0][2]=st[0][3]=st[0][4]=0;
		for(int i=1;i<=pno;i++){
			System.out.print("burst time and priority for P"+i);
			bt[i][0] = s.nextInt();
			
			bt[i][1] = s.nextInt();
			bt[i][2]=0;
			
		}
		int minp = 0;
		
		for(int i=1;i<=pno;i++){
			minp = 9999;
			int mini=0;
			for(int j=1;j<=pno;j++){
				if(minp>bt[j][1]&&bt[j][2]==0){
					minp = bt[j][1];
					mini = j;
				}
			}
			System.out.println("Process P"+mini);
			int pretat = st[i-1][3];
			st[i][0] = bt[mini][0];
			st[i][1] = bt[mini][1];
			st[i][2] = pretat;
			pretat = pretat+st[i][0];
			st[i][3] = pretat;
			st[i][4]=mini;
			bt[mini][2]=1;
			
		}
		System.out.println("p\tbt\tpri\twt\ttat");
		for(int i=1;i<=pno;i++){
			
			System.out.println("P"+st[i][4]+"\t"+st[i][0]+"\t"+st[i][1]+"\t"+st[i][2]+"\t"+st[i][3]);
		}
		
		int sumWT=0,sumTAT=0;
		
		for(int i=1;i<=pno;i++){
			sumWT =sumWT+st[i][2];
			sumTAT =sumTAT+st[i][3];
		}
		
		System.out.println("Average Waiting Time"+((double)sumWT/(double)pno));
		System.out.println("Average Turn Around Time"+((double)sumTAT/(double)pno));
	}

	private void SJF() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of Processes");
		int pno = s.nextInt();
		System.out.println("Enter CPU burst time for each process");
		int bt[][]=new int[pno+1][2];
		int st[][]=new int[pno+1][4];
		bt[0][0]=bt[0][1]=0;
		st[0][0]=st[0][1]=st[0][2]=st[0][3]=st[0][4]=0;
		for(int i=1;i<=pno;i++){
			System.out.print("burst time for P"+i);
			bt[i][0] = s.nextInt();
			bt[i][1]=0;
			
		}
		int minp = 0;
		
		for(int i=1;i<=pno;i++){
			minp = 9999;
			int mini=0;
			for(int j=1;j<=pno;j++){
				if(minp>bt[j][0]&&bt[j][1]==0){
					minp = bt[j][0];
					mini = j;
				}
			}
			System.out.println("Process P"+mini);
			int pretat = st[i-1][2];
			st[i][0] = bt[mini][0];
			st[i][1] = pretat;
			pretat = pretat+st[i][0];
			st[i][2] = pretat;
			st[i][3]=mini;
			bt[mini][1]=1;
			
		}
		System.out.println("p\tbt\twt\ttat");
		for(int i=1;i<=pno;i++){
			
			System.out.println("P"+st[i][3]+"\t"+st[i][0]+"\t"+st[i][1]+"\t"+st[i][2]);
		}
		
		int sumWT=0,sumTAT=0;
		
		for(int i=1;i<=pno;i++){
			sumWT =sumWT+st[i][1];
			sumTAT =sumTAT+st[i][2];
		}
		
		System.out.println("Average Waiting Time"+((double)sumWT/(double)pno));
		System.out.println("Average Turn Around Time"+((double)sumTAT/(double)pno));
	}

	private void FCFS() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of Processes");
		int pno = s.nextInt();
		System.out.println("Enter CPU burst time for each process");
		int bt[][]=new int[pno+1][3];
		bt[0][0]=bt[0][1]=bt[0][2]=0;
		int prewt=0;
		int pretat=0;
		for(int i=1;i<=pno;i++){
			System.out.print("burst time for P"+i);
			bt[i][0] = s.nextInt();
			bt[i][1] = pretat;
			System.out.println("Waiting time of P"+i+" "+(pretat));
			pretat = pretat+bt[i][0];
			System.out.println("Waiting time of P"+i+" "+(pretat));
			bt[i][2] = pretat;
			
		}
		System.out.println("p\tbt\twt\ttat");
		for(int i=1;i<=pno;i++){
			
			System.out.println("P"+i+"\t"+bt[i][0]+"\t"+bt[i][1]+"\t"+bt[i][2]);
		}
		
		int sumWT=0,sumTAT=0;
		
		for(int i=1;i<=pno;i++){
			sumWT =sumWT+bt[i][1];
			sumTAT =sumTAT+bt[i][2];
		}
		
		System.out.println("Average Waiting Time"+((double)sumWT/(double)pno));
		System.out.println("Average Turn Around Time"+((double)sumTAT/(double)pno));
		
		
	}
}
