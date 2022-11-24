import java.util.*;

public class RR{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter no. of processes: ");
		int n = sc.nextInt();
		System.out.println("Enter Time Quantum: ");
		int qt = sc.nextInt();
		int[] pid = new int[n];		//process id
		int[] bt = new int[n];		//burst time
		int[] rt = new int[n];		//remaining burst time
		int[] at = new int[n];		//arrival time
		int[] ct = new int[n];		//completion time
		int[] tat = new int[n];		//turn around time
		int[] wt = new int[n];		//waiting time
		int count = 0;			//completed process count
		float avgtat = 0;
		float avgwt = 0;
		for(int i=0;  i<n; i++){
			pid[i] = i;
			System.out.println("Enter Process "+(i+1)+" Arrival time: ");
			at[i] = sc.nextInt();
			System.out.println("Enter Process "+(i+1)+" Burst time: ");
			bt[i] = sc.nextInt();
			rt[i] = bt[i];
		}
		
		for(int i=0; i<n; i++){			//sorting according to arrival time (if arrival time is same then according to burst time)
			for(int j=i+1; j<n; j++){
				if(at[i] > at[j] || (at[i] == at[j] && bt[i] > bt[j])){
					int temp = at[i];
					pid[i] = j;
					pid[j] = i;
				}
			}
		}
		
		Queue<Integer> q = new LinkedList<>();
		int t = 0;		//current time
		int index = -1;	
		while(index+1<n && at[index+1] <= t){		//adding the process in queue which arrived at Time = 0
			index++;
			q.add(pid[index]);	
		}
		while(count<n){
			int curr = -1;
			if(q.size()!=0){
				curr = q.peek();		//current process on the top of the queue
				if(rt[curr] > qt){
					rt[curr] -= qt;
					t += qt;
				}
				else{
					t += rt[curr];
					rt[curr] = 0;
					ct[curr] = t;
					tat[curr] = ct[curr] - at[curr];
					wt[curr] = tat[curr] - bt[curr];
					avgtat += tat[curr];
					avgwt += wt[curr];
					count++;
				}
			}
			else{
				t++;
			}
			while(index+1<n && at[index+1] <= t){
				index++;
				q.add(pid[index]);	
			}
			if(curr!=-1){
				q.remove();
				if(rt[curr]>0){
					q.add(curr);
				}
			}
		}
		System.out.println("\nPid\tAT\tBT\tCT\tTAT\tWT");
		for(int  i = 0 ; i< n;  i++){
			System.out.println(pid[i] + "  \t " + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t"  + wt[i] ) ;
		}
		sc.close();
		System.out.println("\naverage waiting time: "+ (avgwt/n));     // printing average waiting time.
		System.out.println("average turnaround time:"+(avgtat/n));    // printing average turnaround time.
	}
}
