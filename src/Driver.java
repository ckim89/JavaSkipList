import java.util.Random;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		Slist d = new Slist(50);
		long fcount = 0;
		long tcount = 0;
		long dcount = 0;
		long lcount = 0;
		long gcount = 0;
		long zcount = 0;
		long a = 0;
		long b = 0;
		long c = 0;
		long num;
		int range;
		int choice;
		Scanner kb = new Scanner(System.in);
		System.out.print("Please enter the number of operations: ");
		num = kb.nextInt();
		System.out.print("Please enter the range: ");
		range = kb.nextInt();
		Random rand = new Random();
		double startTime = System.currentTimeMillis();
		for (int i=0;i<num;i++) {
			choice = rand.nextInt(4);
			switch (choice) {
			case 0:
				if (Slist.insert(rand.nextInt(range), rand.nextInt(range))) {
					tcount++;
				}
				else {
					fcount++;
				}
				a++;
				break;
			case 1:
				if (Slist.delete(rand.nextInt(range))) {
					dcount++;
				}
				else {
					lcount++;
				}
				b++;
				break;
			case 2:
			case 3:
				if (Slist.get(rand.nextInt(range)) != -1) {
					gcount++;
				}
				else {
					zcount++;
				}
				c++;
				break;
			}
		}
		if (num <= 10000) {
			Slist.print();
		}
		System.out.println("number of total elements is: " + Slist.getNum());
		System.out.println("number of unique elements is: " + Slist.getUnum());
		System.out.println("number of successful adds are: " + tcount);
		System.out.println("number of failed adds are: " + fcount);
		System.out.println("number of successful deletes are: " + dcount);
		System.out.println("number of failed deletes are: " + lcount);
		System.out.println("number of successful gets are: " + gcount);
		System.out.println("number of failed gets are: " + zcount);
		System.out.println();
		System.out.println("total number of adds: " + a);
		System.out.println("total number of deletes: " + b);
		System.out.println("total number of gets: " + c);
		double endTime   = System.currentTimeMillis();
		double totalTime = endTime - startTime;
		System.out.println("total time taken: " + totalTime/1000.0 + " seconds");
	}
	
}
