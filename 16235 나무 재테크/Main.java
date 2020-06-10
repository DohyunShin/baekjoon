import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	static int N; // 1~10
	static int M; // 1~100 (N^2)
	static int K; // 1~1000
	static int[][] map = new int[10][10]; //현재 땅 양분 정보
	static int[][] A = new int[10][10]; //추가되는 고정된 양분 정보
	static ArrayList<Integer>[][] treeMap = new ArrayList[10][10]; //현재 땅의 나무 정보
	static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
	static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
	static int treeCnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = 5;
				A[i][j] = Integer.parseInt(st.nextToken());
				treeMap[i][j] = new ArrayList<Integer>();
			}
		}
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int z = Integer.parseInt(st.nextToken());
			treeMap[r][c].add(z);
		}
		treeCnt = M;
		br.close();
		
		solution();
		System.out.println(treeCnt);
	}
	
	private static void solution() {
		int year = 0;
		
		while(true) {
			//봄 & 여름					
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {
					ArrayList<Integer> ages = treeMap[r][c];
					if(ages.size() == 0) continue;
										
					Collections.sort(ages);
					int rest = map[r][c];
					
					ArrayList<Integer> newAges = new ArrayList<Integer>();
					
					for(int i = 0; i < ages.size(); i++) {
						if(rest >= ages.get(i)){
							rest -= ages.get(i);							
							//나이 갱신
							newAges.add(ages.get(i) + 1);
						}
						else {
							//정렬시켰으니까 양분 부족한 이후 부터는 다 필요 없음
							for(int j = i; j < ages.size(); j++) {
								rest += ages.get(j)/2;
							}
							break;
						}
					}
					
					int deathCnt = ages.size() - newAges.size();
					treeCnt -= deathCnt;
					
					map[r][c] = rest; //양분 갱신
					treeMap[r][c] = newAges; //나무 리스트 교체
				}
			}
			
			//가을
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {
					ArrayList<Integer> ages = treeMap[r][c];
					if(ages.size() == 0) continue;
					
					for(Integer age : ages) {
						if(age % 5 == 0) {
							int nr, nc;
							
							for(int d = 0; d < 8; d++) {
								nr = r + dr[d];
								nc = c + dc[d];
								
								if(!(nr >= 0 && nr < N && nc >= 0 && nc < N)) continue;
								
								treeMap[nr][nc].add(1);
								treeCnt++;
							}
						}
					}
				}
			}
			
			//겨울
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(A[i][j] == 0) continue;
					map[i][j] += A[i][j];
				}
			}
			
			year++;
			if(year == K)break;
		}
	}
}
