import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N; //1~20
	static int[][] map = new int[20][20];
	static int max = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		findMax();//최대값 초기 설정
		solution();
		System.out.println(max);
	}
	
	private static void solution() {
		recursive(0);
	}
	
	private static void recursive(int cnt) {
		if(cnt == 5) {
			return;
		}
		
		//현재 맵 상태 저장(원복을 위해)
		int[][] pre = new int[20][20];
		for(int i=0;i<N;i++) {
			pre[i] = map[i].clone();
		}
		
		for(int d = 0; d < 4; d++) {
			if(move(d)) {
				//변화가 있을 경우에만
				findMax();
				recursive(cnt+1);
				
				//원복
				for(int i=0;i<N;i++) {
					map[i] = pre[i].clone();
				}
			}
		}
	}
	
	private static void findMax() {
		for(int r=0;r<N;r++) {
			for(int c=0;c<N;c++) {
				if(map[r][c] > max) max = map[r][c];
			}
		}
	}
	
	private static boolean move(int d) {
		boolean isChanged = false; //값이 변경되거나 이동할 경우 true
		
		switch(d) {
		case 0: //상
			for(int c=0;c<N;c++) {
				for(int r=0;r<N;r++) {
					if(map[r][c] == 0) continue;
					
					for(int nr=r+1; nr<N;nr++) {
						if(map[nr][c] != 0 && map[nr][c] != map[r][c]) break;
						
						if(map[nr][c] == 0) continue;
						else if(map[nr][c] == map[r][c]) {
							map[r][c] *= 2;
							map[nr][c] = 0;
							isChanged = true;
							break;
						}
					}
					
					//이동
					for(int mr = 0; mr < r; mr++) {
						if(map[mr][c] == 0) {
							map[mr][c] = map[r][c];
							map[r][c] = 0;
							isChanged = true;
							break;
						}
					}
				}
			}
			break;
		case 1: //하
			for(int c=0;c<N;c++) {
				for(int r=N-1;r>=0;r--) {
					if(map[r][c] == 0) continue;
					
					for(int nr=r-1; nr>=0;nr--) {
						if(map[nr][c] != 0 && map[nr][c] != map[r][c]) break;
						
						if(map[nr][c] == 0) continue;
						else if(map[nr][c] == map[r][c]) {
							map[r][c] *= 2;
							map[nr][c] = 0;
							isChanged = true;
							break;
						}
					}
					
					//이동
					for(int mr = N-1; mr > r; mr--) {
						if(map[mr][c] == 0) {
							map[mr][c] = map[r][c];
							map[r][c] = 0;
							isChanged = true;
							break;
						}
					}
				}
			}
			break;
		case 2: //좌
			for(int r=0;r<N;r++) {
				for(int c=0;c<N;c++) {
					if(map[r][c] == 0) continue;
					
					for(int nc=c+1; nc<N;nc++) {
						if(map[r][nc] != 0 && map[r][nc] != map[r][c]) break;
						
						if(map[r][nc] == 0) continue;
						else if(map[r][nc] == map[r][c]) {
							map[r][c] *= 2;
							map[r][nc] = 0;
							isChanged = true;
							break;
						}
					}
					
					//이동
					for(int mc = 0; mc < c; mc++) {
						if(map[r][mc] == 0) {
							map[r][mc] = map[r][c];
							map[r][c] = 0;
							isChanged = true;
							break;
						}
					}
				}
			}
			break;
		case 3: //우
			for(int r=0;r<N;r++) {
				for(int c=N-1;c>=0;c--) {
					if(map[r][c] == 0) continue;
					
					for(int nc=c-1; nc>=0;nc--) {
						if(map[r][nc] != 0 && map[r][nc] != map[r][c]) break;
						
						if(map[r][nc] == 0) continue;
						else if(map[r][nc] == map[r][c]) {
							map[r][c] *= 2;
							map[r][nc] = 0;
							isChanged = true;
							break;
						}
					}
					
					//이동
					for(int mc = N-1; mc > c; mc--) {
						if(map[r][mc] == 0) {
							map[r][mc] = map[r][c];
							map[r][c] = 0;
							isChanged = true;
							break;
						}
					}
				}
			}
			break;
		}
		
		return isChanged;
	}
}
