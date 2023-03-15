package Debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Debug {
	static Random rand = new Random();
	static boolean intervalSubdivision(int[] data, int x) {
		int left = 0;
		int right = data.length - 1; 
		int mid = (left + right) / 2; 
		while (data[mid] != x) {
			if (left >= right) {
				return false;
			}

			if (data[mid] > x) {
				right = mid - 1;		
			}
			
			else {
				left = mid + 1;
			}
			mid = (left + right) / 2;
		}
		
		return true; 
	}
	
	static int[] generatedfield(int L) {
		
		int[] field = new int[L];
		int last = 0;
		
		for(int i = 0; i < L; i++) {
			field[i] = last + rand.nextInt(1, 100);
			last = field[i];
		}
		
		return field;
	}
	
	static boolean sequentialSearch(int[] data, int x) {
		for(int i = 0; i < data.length; i++) {
			if(x == data[i]) {
				
				return true;
			}	
		}

		return false;
	}
	
	static int[] loadField(String nameOfTheFile) throws FileNotFoundException {
		File file = new File(nameOfTheFile);
		Scanner sc = new Scanner(file);
		
		List<Integer> field = new ArrayList<Integer>();
		
		while(sc.hasNextLine()) {
			String number = sc.nextLine();
			
			if(number.isBlank()) {
				continue;
			}
			
			int num = Integer.parseInt(number);
			field.add(num);
		}
		
		return field.stream().mapToInt(i -> i).toArray();
		
		//int[] resultField = new int[((CharSequence) field).length()];
		
		/*
		Integer [] =  field.toArray();
		
		for(int i = 0; i < resultField.length; i++) {
			
		}
		*/
	}
		
	static boolean isSorted(int [] data) {
		boolean sorted = true;
		
		for (int i = 1; i < data.length && sorted == true; i++) {
		    sorted = sorted && data[i] >= data[i-1];
		}
		
		return sorted;
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		long intervalSubdivisionTime = 0;
		long sequentialSearchTime = 0;
		
		for(int i = 0; i < 1000; i ++) {
			int length = rand.nextInt(10000 + 1, 100000);
			int [] field = generatedfield(length);
			
			for(int j = 0; j < 1000; j++) {
				int number = rand.nextInt(0, length);
				
				long startSubdivision = System.nanoTime();
				intervalSubdivision(field, number);
				long stopSubdivision = System.nanoTime();
				
				long startSequential = System.nanoTime();
				sequentialSearch(field, number);
				long stopSequential = System.nanoTime();
				
				intervalSubdivisionTime += (stopSubdivision - startSubdivision);
				sequentialSearchTime += (stopSequential - startSequential);
				
				if (intervalSubdivisionTime != intervalSubdivisionTime) {
					System.exit(7);
				}
			}
		}
		System.out.format("Resulting search time from subdivision method = %dns \nResulting search time from sequential search method = %dns \n", intervalSubdivisionTime, sequentialSearchTime); 
		
		long subdivisionTime = 0;
		long seqSearchTime = 0;
		
		for(int j = 0; j < 10000; j++) {
			int randomNumber = rand.nextInt(0, 10000);
			for(int k = 1; k <= 10; k++){
				
				int[] fileField = loadField("seq" + k + ".txt");
				boolean sorted = isSorted(fileField);
				
				System.out.println("Sequence "+ k +":");
				if(sorted == true){
					System.out.println("Number of elements = "+ fileField.length);
					
					long startSubdivision = System.nanoTime();
					intervalSubdivision(fileField, randomNumber);
					long endSubdivision = System.nanoTime();
					subdivisionTime += (endSubdivision - startSubdivision);
					System.out.println("Resulting search time for 10000 random elements with the subdivision method = " + subdivisionTime);
					
					long startSeqSearch = System.nanoTime();
					sequentialSearch(fileField, randomNumber);
					long endSeqSearch = System.nanoTime();
					seqSearchTime += (endSeqSearch - startSeqSearch);
					System.out.println("Resulting search time for 10000 random elements with the sequential search method = " + seqSearchTime);
					System.out.println("The subdivision method is "+ (seqSearchTime / subdivisionTime) + "times faster than the sequential search method.");
				}
				
				else {
					System.out.println("Is not sorted.");
					continue;
				}
			}
		}
		
		/*
		int[] data = new int[] {1, 3, 5, 41, 48, 52, 63, 71};
		long start = System.nanoTime();
		boolean found = intervalSubdivision(data, 71);
		long stop = System.nanoTime();
		System.out.println("Interval subdivision finished in " + (stop - start) + " ns");
		System.out.println("Number found: " + found );
		
		System.out.println("Number found: " + detected );
		System.out.println("Interval subdivision finished in " + (stopSequential - startSequential) + " ns");
		*/
	}

}