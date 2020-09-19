import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N; // 2~100
	static int L; // 1~100
	static int[][] map = new int[100][100];
	static boolean[][] visit = new boolean[100][100];
	static int res = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	private static void solution() {
		for(int i = 0; i < N; i++) {
			if(check(i)) res++;
			//printVisit();
		}
		
		rotate(); // 90도 회전
		//printMap();
		for(int i = 0; i < N; i++) {
			if(check(i)) res++;
			//printVisit();
		}
	}
	
	private static boolean check(int r) {
		int[] path = map[r];
		boolean[] temp = new boolean[N];
		
		int high = 0;
		
		for(int i = 0; i < N; i++) {
			if(high == 0) {
				high = path[i];
				continue;
			}
			
			int diff = high - path[i];
			
			if(diff == 0) continue;
			else if(Math.abs(diff) != 1) return false;
			// 현재 위치로 올라오는 경사로를 현재 위치의 이전 부터 뒤로 놓는다.
			else if(diff < 0) {
				boolean can = true;
				for(int j = i-1; j > i-1-L; j--) {
					if(!(j >= 0)) return false;
					if(high != path[j]) return false;
					if(temp[j]) return false;
					temp[j] = true;	
				}
				high = path[i];
			}
			// 다음 위치로 내려가는 경사로를 현재 위치 부터 앞으로 놓는다.
			else if(diff > 0) {
				int j = i;
				for(; j < i+L; j++) {
					if(!(j < N)) return false;
					if(path[i] != path[j]) return false;
					if(temp[j]) return false;
					temp[j] = true;
				}
				high = path[i];
			}
		}
		
		visit[r] = temp.clone();
		return true;
	}
	
	private static void rotate() {
		int[][] newMap = new int[N][N];
		boolean[][] newVisit = new boolean[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				newMap[j][N-1-i] = map[i][j];
				newVisit[j][N-1-i] = visit[i][j];
			}
		}
		
		map = newMap;
		visit = newVisit;
	}
	
	private static void printVisit() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(visit[i][j]?"1 " : "0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}