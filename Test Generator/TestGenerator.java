import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;


public class TestGenerator {

	private static ArrayList<QuestionGenerator> generators;
	
	public static void generate(String[] categories) throws IOException {
		
		Scanner scan = new Scanner(new File("templates.dat"));
		generators = new ArrayList<QuestionGenerator>();
		Arrays.sort(categories);
		
		while(scan.hasNext()) {
			scan.nextLine();
			if (Arrays.binarySearch(categories, scan.nextLine()) >= 0 || true)
				generators.add(new QuestionGenerator(scan.nextLine(), scan.nextLine(), scan.nextLine()));
			else {
				scan.nextLine();
				scan.nextLine();
				scan.nextLine();
			}
			if (scan.hasNext())
				scan.nextLine();
		}
		
		BufferedWriter testWriter = new BufferedWriter(new FileWriter("test.txt"));
		BufferedWriter answerWriter = new BufferedWriter(new FileWriter("answers.txt"));
		
		int i = 1;
		for (QuestionGenerator qg : generators) {
			Question q = qg.generate(); //getQuestion();
			testWriter.write(i + ". " + q.question());
			answerWriter.write(i + ". " + q.answer());
			testWriter.newLine();
			answerWriter.newLine();
			System.out.println("Question " + i + " finished");
			i++;
		}
		
		testWriter.close();
		answerWriter.close();
		
	}	
	private static Question getQuestion() {
		return generators.get((int) (Math.random() * generators.size())).generate();
	}
	
	public static void main(String[] args) throws IOException {
		generate(new String[] {"Combinations", "Multiplication Principle", "Permutations", "Factorials"});
	}
	
	

	
	
	
	

}
