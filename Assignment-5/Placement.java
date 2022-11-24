import java.util.*;

class Technique{

    static int[] FirstFit(int process[], int block[], int a, int b){
        int allocate[] = new int[a];

        for (int i = 0; i < a; i++){
            allocate[i] = -1;
        }

        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                if (block[j] >= process[i]){
                    allocate[i] = j;
                    block[j] -= process[i];
                    break;
                }
            }
        }

        return allocate;
    }
            
    static int[] BestFit(int process[], int block[], int a, int b){
        int allocate[] = new int[a];

        for (int i = 0; i < a; i++){
            allocate[i] = -1;
        }

        for (int i=0; i<a; i++){
            int bestIdx = -1;
            for (int j=0; j<b; j++){
                if (block[j] >= process[i]){
                    if (bestIdx == -1)
                        bestIdx = j;
                    else if (block[bestIdx] > block[j])
                        bestIdx = j;
                }
            }
       
            if (bestIdx != -1){
                allocate[i] = bestIdx;
                block[bestIdx] -= process[i];
            }
        }

        return allocate;
    }
            
    static int[] WorstFit(int process[], int block[], int a, int b){
        int allocate[] = new int[a];

        for (int i = 0; i < a; i++){
            allocate[i] = -1;
        }

        for (int i=0; i<a; i++){
            int wstIdx = -1;
            for (int j=0; j<b; j++){
                if (block[j] >= process[i]){
                    if (wstIdx == -1)
                        wstIdx = j;
                    else if (block[wstIdx] < block[j])
                        wstIdx = j;
                }
            }

            if (wstIdx != -1){
                allocate[i] = wstIdx;
                block[wstIdx] -= process[i];
            }
        }

        return allocate;
    }
            
    static int[] NextFit(int process[], int block[], int a, int b){
        int allocate[] = new int[a];

        for (int i = 0; i < a; i++){
            allocate[i] = -1;
        }

        int j = 0;
        for (int i = 0; i < a; i++) {
            int count  = 0;
            while (count < b) {
                if (block[j] >= process[i]) {
                    allocate[i] = j;
                    block[j] -= process[i];
                    break;
                }
                count++;
                j = (j + 1) % b;
            }
        }
        
        return allocate;
    }

    static void display(int process[], int allocate[]){
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < process.length; i++){
            System.out.print(" " + (i+1) + "\t\t" + process[i] + "\t\t");
            if (allocate[i] == 0){
                System.out.print("Not Allocated");
                System.out.println();
            }
            else{
                System.out.print(allocate[i] + 1);
                System.out.println();
            }
        }
    }
            
}

public class Placement{
    public static void main(String[] args){
        int choice;
        int flag = 1;

        Scanner sc = new Scanner(System.in);

        while (flag==1){
            System.out.println("User manual:\n1. Start\n2. Exit\nEnter the choice: ");
            choice = sc.nextInt(); 
            if (choice == 1){
                int option, n, m ;

                System.out.println("Enter the No. of process : ");
                n = sc.nextInt();

                int[] process = new int[n];
                int[] output = new int[n];
                System.out.println("Enter the required process memory size : ");
                for (int i = 0; i < n; i++){
                    process[i] = sc.nextInt();
                }

                System.out.println("Enter the total memory block available : ");
                m = sc.nextInt();

                int[] block = new int[m];
                System.out.println("Enter the available memory block size : ");
                for (int i = 0; i < m; i++){
                    block[i] = sc.nextInt();
                }

                System.out.println("Process Placement Strategies : \n1. First Fit\n2. Best fit\n3. Worst Fit\n 4. Next Fit\nEnter the method through which you want to allocate memory: ");
                option = sc.nextInt();

                if(option == 1){
                    System.out.println("First Fit");
                    output = Technique.FirstFit(process, block, n, m);
                    Technique.display(process, output);
                }
                else if(option == 2){
                    System.out.println("Best Fit");
                    output = Technique.BestFit(process, block, n, m);
                    Technique.display(process, output);
                }
                else if(option == 3){
                    System.out.println("worst Fit");
                    output = Technique.WorstFit(process, block, n, m);  
                    Technique.display(process, output);
                }
                else if(option == 4){
                    System.out.println("Next Fit");
                    output = Technique.NextFit(process, block, n, m);
                    Technique.display(process, output);
                }
                else{
                    for(int i = 0; i <n; i++){
                        output[i] = -1; 
                    }
                    System.out.println("Not a valid Response!!");
                }
            }
            else if (choice == 2){
                System.out.println("Successfully terminated !!");
                flag = 0;
            }

            else{
                System.out.println("Some error occurred !!");
                flag = 0;
            }
        }

        sc.close();
    }
}