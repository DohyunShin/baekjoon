import java.util.Scanner;

public class Main {

	static int N;
	static int T[];
	static int P[];
	static int max;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		T = new int[N];
		P = new int[N];
		for(int i = 0; i < N; i++) {
			T[i] = sc.nextInt();
			P[i] = sc.nextInt();
		}
		sc.close();
		
		dfs(0,0);
		System.out.println(max);
	}
	
	static private void dfs(int day, int pay) {
		if(day >= N) {
			if(pay > max) {
				max = pay;
			}
			return;
		}
	
		//일하지 않는 경우
		dfs(day + 1, pay);
		//일하는 경우(할 수 있으면)
		if(day + T[day] <= N) {
			dfs(day + T[day], pay + P[day]);	
		}
	}
}
