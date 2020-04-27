import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
	
	//각 시험장마다 응시생 모두 감시해야할 때, 필요한 감독관 수의 최솟값은?
	
	static int N; //1~1,000,000 시험장 개수
	static int[] As = new int[1000000]; //1~1,000,000 각 시험장 응시자 수
	//총 감독관은 1명, 부감독 1명 이상
	static int B; //1~1,000,000 총감독관이 한 시험장에서 감시할 수 있는 응시자 수
	static int C; //1~1,000,000 부감독관이 한 시험장에서 감시할 수 있는 응시자 수
	static long cnt = 0;
	static HashMap<Integer, Integer> mem = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			As[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	//각 시험장 마다 총감독관은 무조건 한 명 있어야하는듯
	private static void solution() {
		int temp = 0;
		
		for(int i = 0; i < N; i++) {
			int A = As[i];
			temp = 0;
			
			if(mem.containsKey(A)) {
				cnt += mem.get(A);
				continue;
			}
				
			//총 감독관 할당량 제거
			int rest = A-B;
			temp++;
			
			//남은 인원은 부감독관들에게 분배
			while(rest > 0) {
				rest = rest-C;
				temp++;
			}
			
			mem.put(A, temp);
			cnt += temp;
		}
	}
}
