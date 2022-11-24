
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.*;

public class paging_algo {
	Scanner in = new Scanner(System.in);
	int n = in.nextInt();// page frame
	int m = in.nextInt();// no of pages
	int virtual_address[] = new int[m];
	int physical_memory[] = new int[n];

	void inputs() {
		for (int i = 0; i < m; i++) {
			int x = in.nextInt();
			virtual_address[i] = x;
		}
		for (int i = 0; i < n; i++)
			physical_memory[i] = -1;

	}

	void initialize() {
		for (int i = 0; i < n; i++)
			physical_memory[i] = -1;
	}

	void FIFO() {
		System.out.println("\tFIFO");
		Queue<Integer> q = new LinkedList<>();
		int fault = 0, hit = 0;
		for (int i = 0; i < m; i++) {
			int x = virtual_address[i];
			int flag = 0, space = 0;
			for (int j = 0; j < n; j++) {
				if (x == physical_memory[j])
					flag = 1;
				else if (physical_memory[j] == -1)
					space = 1;
			}
			if (flag != 0)
				hit++;
			else if (space != 0) {
				fault++;
				q.add(x);
				for (int j = 0; j < n; j++)
					if (physical_memory[j] == -1) {
						physical_memory[j] = x;
						break;
					}
			} else {
				fault++;
				int head = q.peek();
				q.remove();
				for (int j = 0; j < n; j++)
					if (physical_memory[j] == head) {
						physical_memory[j] = x;
						break;
					}
				q.add(x);
			}
			show();
		}
		System.out.print("Hits : ");
		System.out.println(hit);
		System.out.print("Fault: ");
		System.out.println(fault);
		System.out.println();

	}

	void show() {
		for (int i = 0; i < n; i++)
			if (physical_memory[i] != -1)
				System.out.print(physical_memory[i]);
		System.out.println();
	}

	void lru() {
		System.out.println("\tLRU");
		int num = virtual_address.length;
		int hit = 0, miss = 0;
		ArrayList<Integer> frame = new ArrayList<>(n);
		ArrayList<Integer> index = new ArrayList<>(n);

		for (int i = 0; i < num; i++) {
			if (!frame.contains(virtual_address[i])) {
				if (frame.size() < n) {
					frame.add(virtual_address[i]);
					index.add(i);
				} else {
					int min = getMin(index);
					frame.remove(min);
					frame.add(min, virtual_address[i]);
					index.remove(min);
					index.add(min, i);
				}
				miss++;
			} else {
				if (frame.size() < n) {
					int a = frame.indexOf(virtual_address[i]);
					index.remove(a);
					index.add(a, i);
				} else {
					int a = frame.indexOf(virtual_address[i]);
					index.remove(a);
					index.add(a, i);
				}
				hit++;
			}
			for (Integer elem : frame) {
				System.out.print(elem);
			}
			System.out.println();
		}
		System.out.println("Hits : " + hit);
		System.out.println("Miss : " + miss);
		System.out.println();
	}

	int getMin(ArrayList<Integer> index) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < index.size(); i++) {
			int val = index.get(i);
			if (val < min)
				min = val;
		}
		return index.indexOf(min);
	}

	void optimal() {
		System.out.println("\tOPTIMAL");
		ArrayList<Integer> frame = new ArrayList<>(n);
		ArrayList<Integer> index = new ArrayList<>(n);
		int hit = 0, miss = 0;
		int num = virtual_address.length;
		for (int i = 0; i < num; i++) {
			if (!frame.contains(virtual_address[i])) {
				if (frame.size() < n) {
					int nextAcc = getNextAccess(i);// frame.subList(i+1, num).indexOf(arr[i]);

					frame.add(virtual_address[i]);
					index.add(nextAcc);
				} else {
					int nextAcc = getNextAccess(i);// frame.subList(i+1, num).indexOf(arr[i]);

					int max = index.indexOf(getMax(index));
					frame.remove(max);
					frame.add(max, virtual_address[i]);
					index.remove(max);
					index.add(max, nextAcc);
				}
				miss++;
			} else {
				if (frame.size() < n) {
					int nextAcc = getNextAccess(i);// frame.subList(i+1, num).indexOf(arr[i]);

					int ind = frame.indexOf(virtual_address[i]);
					index.remove(ind);
					index.add(ind, nextAcc);
				} else {
					int nextAcc = getNextAccess(i);// frame.subList(i+1, num).indexOf(arr[i]);

					int ind = frame.indexOf(virtual_address[i]);
					index.remove(ind);
					index.add(ind, nextAcc);
				}
				hit++;
			}

			for (Integer elem : frame)
				System.out.print(elem);
			System.out.println();
		}
		System.out.println("Hits : " + hit);
		System.out.println("Miss : " + miss);
	}

	public int getNextAccess(int cPos) {
		int elem = virtual_address[cPos];
		for (int i = cPos + 1; i < virtual_address.length; i++) {
			if (virtual_address[i] == elem)
				return i;
		}
		return Integer.MAX_VALUE;
	}

	public int getMax(ArrayList<Integer> index) {
		int max = Integer.MIN_VALUE;
		int val;
		for (int i = 0; i < index.size(); i++) {
			val = index.get(i);
			if (val > max)
				max = val;
		}
		return max;
	}

	public static void main(String[] args) {
		paging_algo obj = new paging_algo();
		obj.inputs();
		obj.FIFO();
		obj.lru();
		obj.optimal();
	}
}
