import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //2~100
	static int L; //1~100
	static int[][] map = new int[100][100]; //각 1~10
	static boolean[][] visit = new boolean[100][100]; //사다리 체크
	static int cnt =0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		for(int i =0;i<N;i++) {
			st =new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		checkRows();
		checkCols();
	}
	
	private static void checkRows() {
		for(int r=0; r<N; r++) {
			int c=0;
			int nc;
			
			boolean[] visitRow = new boolean[100];
			
			while(true) {
				if(c == N-1) {
					cnt++;
					for(int i=0; i<N; i++) {
						visit[r][i] = visitRow[i];
					}
					break;
				}
				
				nc = c+1;
				
				if(nc >= N) break;
				
				int diff = map[r][c]-map[r][nc];
				
				if(diff == 0) { //동일한 높이
					c=nc;
				}
				else if(diff == 1) { //현재 위치가 1더 높다. 앞에 경사로 놓기
					int start = c+1;
					int end = start+L-1;
					
					if(end >= N) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					boolean build = true;
					for(int check = start; check <= end; check++) {
						if(map[r][start] != map[r][check] || visitRow[check]) {
							build = false;
							break;
						}
					}
					
					if(!build) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					//경사로 놓기
					for(int check = start; check <= end; check++) {
						visitRow[check] = true;
					}
					
					c = nc; //현재 위치 다음 부터 검사
				}
				else if(diff == -1) { //현재 위치가 1더 낮다. 뒤에 경사로 놓기
					int start = c-L+1;
					int end = c;
					
					if(start < 0) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					boolean build = true;
					for(int check = start; check <= end; check++) {
						if(map[r][start] != map[r][check] || visitRow[check]) {
							build = false;
							break;
						}
					}
					
					if(!build) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					//경사로 놓기
					for(int check = start; check <= end; check++) {
						visitRow[check] = true;
					}
					
					c = nc; //현재 위치 다음 부터 검사
				}
				else { //경사로를 놓을 수 없음. 다음 행 검사
					break;
				}
			}
		}
	}
	
	private static void checkCols() {
		for(int c=0; c<N; c++) {
			int r=0;
			int nr;
			
			boolean[] visitCol = new boolean[100];
			
			while(true) {
				if(r == N-1) {
					cnt++;
					for(int i=0; i<N; i++) {
						visit[i][c] = visitCol[i];
					}
					break;
				}
				
				nr = r+1;
				
				if(nr >= N) break;
				
				int diff = map[r][c]-map[nr][c];
				
				if(diff == 0) { //동일한 높이
					r=nr;
				}
				else if(diff == 1) { //현재 위치가 1더 높다. 앞에 경사로 놓기
					int start = r+1;
					int end = start+L-1;
					
					if(end >= N) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					boolean build = true;
					for(int check = start; check <= end; check++) {
						if(map[start][c] != map[check][c] || visitCol[check]) {
							build = false;
							break;
						}
					}
					
					if(!build) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					//경사로 놓기
					for(int check = start; check <= end; check++) {
						visitCol[check] = true;
					}
					
					r = nr; //현재 위치 다음 부터 검사
				}
				else if(diff == -1) { //현재 위치가 1더 낮다. 뒤에 경사로 놓기
					int start = r-L+1;
					int end = r;
					
					if(start < 0) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					boolean build = true;
					for(int check = start; check <= end; check++) {
						if(map[start][c] != map[check][c] || visitCol[check]) {
							build = false;
							break;
						}
					}
					
					if(!build) break; //경사로를 놓을 수 없음. 다음 행 검사
					
					//경사로 놓기
					for(int check = start; check <= end; check++) {
						visitCol[check] = true;
					}
					
					r = nr; //현재 위치 다음 부터 검사
				}
				else { //경사로를 놓을 수 없음. 다음 행 검사
					break;
				}
			}
		}
	}
}