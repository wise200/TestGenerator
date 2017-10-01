import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionGenerator{
	private String[] template;
	private Range[] ranges;
	private String answerTemplate;
	private Scanner scan;
	private ArrayList<String> males;
	private ArrayList<String> females;
	
	public QuestionGenerator(String templateString, String rangeString, String answerTemplate) throws IOException {
		
		males = new ArrayList<String>(1219);
		females = new ArrayList<String>(4275);
		
		scan = new Scanner(new File("dudes.txt"));
		while (scan.hasNext()) 
			males.add(scan.next());
		scan.close();
		
		scan = new Scanner(new File("dudettes.txt"));
		while (scan.hasNext()) 
			females.add(scan.next());
		scan.close();
		
		for (int i = templateString.length() - 1; i >= 0; i--) {
			if (templateString.charAt(i) == '~') {
				if (templateString.charAt(i+1) == 'm')
					templateString = templateString.substring(0, i) + getMale() + templateString.substring(i+2);
				else templateString = templateString.substring(0, i) + getFemale() + templateString.substring(i+2);
			}
		}
		template = templateString.split("#");
		ranges = new Range[template.length - 1];
		String[] rangeStrings = rangeString.split(" ");
		
		for (int i = 0; i < ranges.length; i++)
			ranges[i] = new Range(Integer.parseInt(rangeStrings[i*2]), Integer.parseInt(rangeStrings[i*2+1]));
		
		this.answerTemplate = answerTemplate;
		
		
	}
	
	private String getMale() {
		return males.get((int) (Math.random() * males.size()));
	}
	
	private String getFemale() {
		return females.get((int) (Math.random() * females.size()));
	}
	
	protected String answer(int[] nums) {
		String answer = "";
		char[] chars = answerTemplate.toCharArray();
		for (int i = 0; i < answerTemplate.length(); i++) {
			char c = chars[i];
			if (c == '#') {
				i++;
				answer += nums[chars[i] - 49]; //entry at value of char - 1
			}
			else
				answer += c;
		}
		return answer;
	}
	

	public Question generate() {
		String question = "";
		int[] nums = new int[ranges.length];
		for (int i = 0; i < ranges.length; i++) {
			nums[i] = ranges[i].val();
			question += template[i] + nums[i];
		}
		question += template[template.length - 1];
		
		
		return new Question(question, PostFixEvaluator.evalMultiple(answer(nums)));
	}
}
