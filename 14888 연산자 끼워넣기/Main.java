import java.util.HashMap;
import java.util.Scanner;

public class Main {
	static int N;
	static int nums[];
	static int ops[] = new int[4]; //0: +, 1: -, 2: *, 3: /
	static long min = 1000000000;
	static long max = -1000000000;
	static HashMap<String, Long> check = new HashMap<String, Long>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		nums = new int[N];
		for(int i = 0; i < N; i++) {
			nums[i] = sc.nextInt();
		}
		for(int i = 0; i < 4; i++) {
			ops[i] = sc.nextInt();
		}
		sc.close();
		
		solution();
		System.out.println(max);
		System.out.println(min);
	}

	private static void solution() {
		dp(ops[0], ops[1], ops[2], ops[3], "");
	}
	
	private static void dp(int add, int sub, int mul, int div, String opStr) {
		if(add == 0 && sub == 0 && mul == 0 && div == 0) {
			//check 및 계산
			if(!check.containsKey(opStr)) {
				long result = calculate(opStr);
				if(max < result) {
					max = result;
				}
				
				if(min > result) {
					min = result;
				}
				check.put(opStr, result);
			}
			return;
		}
		
		if(add > 0) {
			dp(add - 1, sub, mul, div, opStr + "+");	
		}
		if(sub> 0) {
			dp(add, sub - 1, mul, div, opStr + "-");	
		}
		if(mul > 0) {
			dp(add, sub, mul - 1, div, opStr + "*");	
		}
		if(div > 0) {
			dp(add, sub, mul, div - 1, opStr + "/");	
		}
	}
	
	private static long calculate(String opStr) {
		long result = 0;
		int num_index = 0;
		
		int tempNums[] = nums.clone();
		
		for(char op : opStr.toCharArray()) {
			if(op == '+') {
				tempNums[num_index + 1] = tempNums[num_index] + tempNums[num_index + 1];
			}
			else if(op == '-') {
				tempNums[num_index + 1] = tempNums[num_index] - tempNums[num_index + 1];
			}
			else if(op == '*') {
				tempNums[num_index + 1] = tempNums[num_index] * tempNums[num_index + 1];
			}
			else if(op == '/') {
				tempNums[num_index + 1] = tempNums[num_index] / tempNums[num_index + 1];
			}
			num_index++;
		}
		
		result = tempNums[num_index];
		return result;
	}
}
