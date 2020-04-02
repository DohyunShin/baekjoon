import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int[] s = new int[49];
	static boolean[] visit = new boolean[49];
	static int k = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		while(true) {
			st = new StringTokenizer(br.readLine());
			k = Integer.parseInt(st.nextToken());
			if(k == 0) break;
			
			for(int i = 0; i < k; i++) {
				s[i] = Integer.parseInt(st.nextToken());
			}
			
			solution();
		}
		
		br.close();
	}
	
	static void solution() {
		combination(0, 0);
		System.out.println();
	}
	
	static void combination(int cnt, int idx) {
		if(cnt == 6) {
			for(int i = 0; i < k; i++) {
				if(!visit[i]) continue;
				System.out.print(s[i] + " ");
			}
			System.out.println();
			return;
		}
		
		if(cnt > 6 || idx >= k || visit[idx]) return;
		
		visit[idx] = true;
		combination(cnt+1, idx+1);
		visit[idx] = false;
		
		combination(cnt, idx+1);
	}
	
}
