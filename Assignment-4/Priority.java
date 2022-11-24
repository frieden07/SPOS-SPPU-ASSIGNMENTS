import java.util.*;

class Priority{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter no. of processes: ");
		int n = sc.nextInt();
		int[] pid = new int[n];
		int[] at = new int[n];
		int[] bt = new int[n];
		int[] p = new int[n];
		int[] ct = new int[n];
		int[] tat = new int[n];
		int[] wt = new int[n];
		int[] completed = new int[n];
		float avgtat = 0;
		float avgwt = 0;
		int count = 0;
	
	
		for(int i=0; i<n; i++){
			pid[i] = i;
			System.out.print("\nEnter Process "+(i)+" arrival time: ");
			at[i] = sc.nextInt();
			System.out.print("\nEnter Process "+(i)+" burst time: ");
			bt[i] = sc.nextInt();
			System.out.print("\nEnter Process "+(i)+" Priority: ");
			p[i] = sc.nextInt();
		}
		
		
		int t = 0;
		while(count < n){
			int curr = -1;
			int highestPriority = Integer.MIN_VALUE;
			for(int i=0; i<n; i++){				//find the process with arrival time under t and has highest priority
				if(at[i] <= t && completed[i]!=1 && p[i]>highestPriority){
					curr = i;
					highestPriority = p[i];
				}
			}
			if(curr != -1){
				t = t + bt[curr];
				ct[curr] = t;
				tat[curr] = ct[curr] - at[curr];
				wt[curr] = tat[curr] - bt[curr];
				avgtat += tat[curr];
				avgwt += wt[curr];
				completed[curr] = 1;
				count++;
			}
			else{
				t++;	
			}
		}
		
		System.out.println("\nPid\tAT\tBT\tP\tCT\tTAT\tWT");
		for(int  i = 0 ; i< n;  i++){
			System.out.println(pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + p[i] + "\t" + ct[i] + "\t" + tat[i] + "\t"  + wt[i] ) ;
		}
		sc.close();
		System.out.println("\naverage waiting time: "+ (avgwt/n));     // printing average waiting time.
		System.out.println("average turnaround time:"+(avgtat/n));    // printing average turnaround time.
		
		
	}
}
