import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //2~11
	static int[] digits = new int[11];
	static int[] opCnts = new int[4];
	static long min = Long.MAX_VALUE;
	static long max = Long.MIN_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			digits[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 4; i++) {
			opCnts[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(max);
		System.out.println(min);
	}
	
	private static void solution() {
		recursive(0, 1, digits[0]);
	}
	
	private static void recursive(int opCnt, int numIdx, int res) {
		if(opCnt == N-1) {
			if(res > max) max = res;
			if(res < min) min = res;
			return;
		}
		
		if(opCnt > N-1 || numIdx >= N) return;
		
		for(int i = 0; i < 4; i++) {
			if(opCnts[i] <= 0) continue;
			
			opCnts[i]--;
			recursive(opCnt+1, numIdx+1, calculate(numIdx, res, i));
			opCnts[i]++;
		}		
	}
	
	private static int calculate(int numIdx, int res, int op) {
		int cur = digits[numIdx];
		
		switch(op) {
		case 0:
			return res + cur;
		case 1:
			return res - cur;
		case 2:
			return res * cur;
		case 3:
			return res / cur;
		}
		
		return 0;
	}
}
