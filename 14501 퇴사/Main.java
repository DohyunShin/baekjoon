import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //1~15
	static int[] T = new int[15];
	static int[] P = new int[15];
	static boolean[] visit = new boolean[15];
	static int max = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			T[i] = Integer.parseInt(st.nextToken());
			P[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		recursive(0, 0);
	}
	
	private static void recursive(int idx, int sum) {
		if(idx >= N) {
			if(sum > max)
				max = sum;
			return;
		}
		
		if(visit[idx]) return;
		
		if(idx+T[idx] <= N) {
			visit[idx] = true;
			recursive(idx+T[idx], sum+P[idx]);
			visit[idx] = false;
		}
		
		recursive(idx+1, sum);
	}
}
