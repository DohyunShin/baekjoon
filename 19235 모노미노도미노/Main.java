import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class Main {
	static int N; //1~10000
	static int ROW_SIZE = 4;
	static int COL_SIZE = 6;
	static int[][] greenMap = new int[ROW_SIZE][COL_SIZE];
	static int[][] blueMap = new int[ROW_SIZE][COL_SIZE];
	static HashMap<Integer, Integer> blockInfos = new HashMap<Integer, Integer>();
	static int BLOCK_IDX = 1;
	
	static int score = 0;
	static int blockCnt = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(br.readLine());
		int t, x, y;
		for(int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			t = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			solution(t, x, y);
		}
		br.close();
		
		System.out.println(score);
		if(blockInfos.size() > 0) {
			for(Entry<Integer, Integer> entry : blockInfos.entrySet()) {
				int tv = entry.getValue();
				if(tv >= 2) blockCnt += 2;
				else blockCnt++;
			}
			System.out.println(blockCnt);
		}
		else 
			System.out.println(0);
		
	}

	public static void solution(int t, int x, int y) {
		int bt, br;
		bt = t;
		br = x;
		
		int gt, gr;
		switch(t) {
		case 2:
			gt = 3;
			break;
		case 3:
			gt = 2;
			break;
		default:
			gt = t;
			break;
		}
		gr = y;
		
		add(true, bt, br);
		add(false, gt, gr);

		removeFullColumnLoop(true);
		removeFullColumnLoop(false);

		removeEndColumn(true);
		removeEndColumn(false);		
	}
	
	//블록을 시작 지점에 추가, 시작 지점은 항상 비어있다고 생각.
	public static void add(boolean isBlue, int t, int r) {
		int[][] map = isBlue ? greenMap : blueMap;
		
		if(t == 1) {			
			map[r][0] = BLOCK_IDX;
		}
		else if(t == 2) {			
			map[r][0] = BLOCK_IDX;
			map[r][1] = BLOCK_IDX;
		}
		else if(t == 3) {			
			map[r][0] = BLOCK_IDX;
			map[r+1][0] = BLOCK_IDX;
		}

		blockInfos.put(BLOCK_IDX, t);
		BLOCK_IDX++;
	}
	
	//모든 블록을 오른쪽으로 이동시킨다.
	public static void moveRightAllBlock(boolean isBlue) {
		int[][] map = isBlue ? greenMap : blueMap;
		
		for(int c = COL_SIZE-1; c >= 0; c--) {
			int r = 0;
			while(r < ROW_SIZE) {
				int blockIdx = map[r][c];

				if(blockIdx != 0) {
					 int t = blockInfos.get(blockIdx);
					
					if(t == 1) {
						int nc = c;
						while(nc < COL_SIZE) {
							//현재 블록이 아닌 처음 만나는 블록을 만나면 멈춘다.
							if(map[r][nc] != blockIdx && map[r][nc] != 0) break;
							nc++;
						}
						if(nc > c) nc--; //만난 블록 앞으로 열의 인덱스를 맞춘다.
						
						//이동
						map[r][c] = 0;
						map[r][nc] = blockIdx;
					}
					else if(t == 2 && c > 0 && map[r][c-1] == blockIdx) {
						int nc = c;
						while(nc < COL_SIZE) {
							//현재 블록이 아닌 처음 만나는 블록을 만나면 멈춘다.
							if(map[r][nc] != blockIdx && map[r][nc] != 0) break;
							nc++;
						}
						if(nc > c) nc--; //만난 블록 앞으로 열의 인덱스를 맞춘다.
						
						//이동
						map[r][c] = 0;
						map[r][c-1] = 0;
						map[r][nc] = blockIdx;
						map[r][nc-1] = blockIdx;
					}
					else if(t == 3 && r+1 < ROW_SIZE && map[r+1][c] == blockIdx) {
						int nc1 = c; //첫번째 행의 열
						int nc2 = c; //두번째 행의 열
						
						while(nc1 < COL_SIZE || nc2 < COL_SIZE) {
							if(nc1 < COL_SIZE) {
								//현재 블록이 아닌 처음 만나는 블록을 만나면 멈춘다.
								if(map[r][nc1] != blockIdx && map[r][nc1] != 0) break;
							}
						
							if(nc2 < COL_SIZE) {
								//현재 블록이 아닌 처음 만나는 블록을 만나면 멈춘다.
								if(map[r+1][nc2] != blockIdx && map[r+1][nc2] != 0) break;
							}
							nc1++; nc2++;
						}
						int nc = Integer.min(nc1, nc2); //작은 인덱스의 열을 따른다.
						if(nc > c) nc--; //만난 블록 앞으로 열의 인덱스를 맞춘다.
						
						map[r][c] = 0;
						map[r+1][c] = 0;
						map[r][nc] = blockIdx;
						map[r+1][nc] = blockIdx;
						
						r++;
					}
				}
				
				r++;
			}
		}
	}
	
	//가득 차있는 열을 제거하고 점수를 증가 시킨다. 만약 하나의 열이라도 제거되는 경우 true를 반환한다.
	public static boolean removeFullColumn(boolean isBlue) {
		int[][] map = isBlue ? greenMap : blueMap;
		
		int removeCnt = 0;
		for(int c = COL_SIZE-1; c >= 0; c--) {
			boolean isFull = true;
			
			for(int r = 0; r < ROW_SIZE; r++) {
				if(map[r][c] == 0) {
					isFull = false;
					break;
				}
			}
			
			if(isFull) {
				//해당 열을 제거한다.
				int r = 0;
				while(r < ROW_SIZE) {
					int idx = map[r][c];
					int t = blockInfos.get(idx);
					
					if(t == 1) {
						map[r][c] = 0;
						blockInfos.remove(idx);
						r++;
					}
					else if(t == 2) {
						map[r][c] = 0;
						blockInfos.replace(idx, 1); //1번 타입 블록으로 변경
						r++;
					}
					else if(t == 3 && r+1 < ROW_SIZE) {
						map[r][c] = 0;
						map[r+1][c] = 0;
						blockInfos.remove(idx);
						r+=2;
					}
				}
				
				removeCnt++;
			}
		}
		
		score += removeCnt;
		
		if(removeCnt > 0) return true;
		else return false;
	}
	
	//가득 차있는 열이 없을 때 까지 가득 차있는 열을 제거하고 오른쪽으로 모든 블록을 이동한다.
	public static void removeFullColumnLoop(boolean isBlue) {		
		do {
			moveRightAllBlock(isBlue);
			if(!removeFullColumn(isBlue))break;
		}while(true);
	}
	
	//시작점을 검사하여 블록이 존재하는 시작점 개수 만큼 끝에서 제거한다.
	public static void removeEndColumn(boolean isBlue) {
		int[][] map = isBlue ? greenMap : blueMap;
		
		int removeColumn = COL_SIZE-1;
		
		for(int c = 0; c < 2; c++) {
			boolean isExist = false;
			
			for(int r = 0; r < ROW_SIZE; r++) {
				if(map[r][c] != 0) {
					isExist = true;
					break;
				}
			}
			
			if(isExist) {
				//열 제거
				int r = 0;
				while(r < ROW_SIZE) {
					int idx = map[r][removeColumn];
					if(idx == 0) {
						r++;
						continue;
					}
					
					int t = blockInfos.get(idx);
					
					if(t == 1) {
						map[r][removeColumn] = 0;
						blockInfos.remove(idx);
						r++;
					}
					else if(t == 2) {
						map[r][removeColumn] = 0;
						blockInfos.replace(idx, 1); //1번 타입 블록으로 변경
						r++;
					}
					else if(t == 3 && r+1 < ROW_SIZE) {
						map[r][removeColumn] = 0;
						map[r+1][removeColumn] = 0;
						blockInfos.remove(idx);
						r+=2;
					}
				}
				
				removeColumn--;
			}
		}
		
		if(removeColumn < COL_SIZE-1) {
			moveRightAllBlock(isBlue);	
		}
	}
	
	public static void printMap(boolean isBlue) {
		int[][] map = isBlue ? greenMap : blueMap;
		
		if(isBlue) System.out.println("<BLUE>");
		else System.out.println("<GREEN>");
		
		for(int r = 0; r < ROW_SIZE; r++) {
			for(int c = 0; c < COL_SIZE; c++) {
				System.out.print(map[r][c] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
}