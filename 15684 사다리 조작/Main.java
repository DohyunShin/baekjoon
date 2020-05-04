import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //세로선 col 2~10
	static int H; //가로선 row 1~30
	static int M; //이미 존재하는 가로선 개수 0~(N-1)*H
	static int[][] map = new int[30][10];
	static int min = 4;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = 1;
		}
		br.close();
		
		solution();
		System.out.println(min > 3 ? -1 : min);
	}
	
	private static void solution() {
		for(int i=0;i<=3;i++) {
			if(recursive(0,i,0,0)) break;	
		}
	}
	
	private static boolean recursive(int cnt, int targetCnt, int r, int c) {
		//printMap();
		
		if(cnt > 3) {
			return false;
		}
		
		//확인
		if(cnt==targetCnt) {
			if(simulation()) {
				if(cnt < min) min = cnt;
				return true;	
			}
			else return false;
		}
		
		for(int i=r;i<H;i++) {
			for(int j=c;j<N-1;j++) {
				if(map[i][j] != 0 || (j-1 >= 0 && map[i][j-1] != 0)) continue;
				
				map[i][j] = 1;
				if(recursive(cnt+1, targetCnt, i, j+2)) return true; //어차피 사다리를 연속해서 놓을  수 없기 때문에 사다리를 놓은 바로 옆을 건너뛰고 시작한다. (시간 단축)
				map[i][j] = 0;
			}
			c = 0;
		}
		
		return false;
	}
	
	private static boolean simulation() {		
		for(int c=0;c<N;c++) {
			int cRes = c;
			int r = 0;
			
			while(r < H) {
				//오른쪽으로 가야하는지 확인
				if(cRes+1 < N && map[r][cRes]==1) {
					cRes++;
				}
				//왼쪽으로 가야하는지 확인
				else if(cRes-1 >= 0 && map[r][cRes-1]==1) {
					cRes--;
				}
				r++;
			}
			
			if(cRes != c) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void printMap() {
		for(int i=0;i<H;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}