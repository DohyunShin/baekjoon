import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int T;
	static int cnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int t=0; t<T; t++) {
			solution(Integer.parseInt(br.readLine()));
		}
		br.close();
	}
	
	private static void solution(int num) {
		recursive(num, 0);
		System.out.println(cnt);
		cnt = 0;
	}
	
	private static void recursive(int num, int sum) {
		if(sum > num) return;
		if(sum == num) {
			cnt++;
			return;
		}
		
		recursive(num, sum + 1);
		recursive(num, sum + 2);
		recursive(num, sum + 3);
	}
}
