import java.util.Scanner;

public class Main {
	static class Unit{
		int r, c;
		int d; //우좌상하
		public Unit() {};
	}
	static int dr[] = {0,0,-1,1};
	static int dc[] = {1,-1,0,0};
	static int N; //4~12
	static int K; //4~10
	static int map[][]; //0: 흰, 1: 빨, 2: 파
	static int stackMap[][][]; //맵 위치에 있는 말들의 정보, (i, j, z) i행, j열, z번째에 x(value)번말이 있다.
	static Unit units[]; //말들의 정보, x번(index) 말의 정보
	static int turnCnt = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		map = new int[N][N];
		units = new Unit[K];
		stackMap = new int[N][N][K];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
				
				int stack[] = new int[K];
				for(int z = 0; z < K; z++) {
					stack[z] = -1;
				}
				stackMap[i][j] = stack;
			}
		}
		
		for(int i = 0; i < K; i++) {
			Unit unit = new Unit();
			unit.r = sc.nextInt()-1;
			unit.c = sc.nextInt()-1;
			unit.d = sc.nextInt()-1;
			
			stackMap[unit.r][unit.c][0] = i; //처음에 쌓여있는 말은 없다.
			units[i] = unit;
		}
		
		sc.close();
		
		solution();
		System.out.println(turnCnt);
	}
	
	//종료 조건: 말이 4개 이상 쌓이면 종료
	//턴 횟수가 1000번 보다 큰 경우 ( -1 출력)
	private static void solution() {
		//이동하려는 칸이
		//흰색 : 해당 칸으로 이동, 흰색 칸에 말이 있는 경우 그 위로 쌓인다. 이동하는 말 위의 말도 함께 이동
		//빨간색 : 위에 쌓여있는 말들과 순서를 뒤집어서 해당 칸으로 이동, 이동하려는 칸에 말이 잇는 경우 그 위로 쌓인다.
		//파란색 : 이동방향의 반대로 이동, 이동하려는 칸의 색이 또 파란색인 경우 이동은 하지 않는다.
		//체스판을 벗어나는 경우 파란색과 동일하게 취급
		
		int maxStack = 0; //한턴이 끝날때 마다 가장 많인 쌓인 개수를 저장
		boolean blueAgainFlag = false; //해당 칸이 파란색인 경우 방향을 바꿔서 다시 이동하려 할때 또 해당 칸이 파란색인 경우을 알기 위한 플래그
		
		while(maxStack < 4) {
			if(turnCnt >= 1000) {
				turnCnt = -1;
				return;
			}
			
			for(int i = 0; i < K; i++) {
				int nr = units[i].r + dr[units[i].d];
				int nc = units[i].c + dc[units[i].d];
				
				if(!(nr >= 0 && nr < N && nc >= 0 && nc < N) || map[nr][nc] == 2) { //파란색과 벽은 동일하게 취급
					if(blueAgainFlag == false) {
						//해당 말의 방향을 변경한다.
						int d = units[i].d;
						
						if(d == 0) {d = 1;}
						else if(d == 1) {d = 0;}
						else if(d == 2) {d = 3;}
						else if(d == 3) {d = 2;};
						
						units[i].d = d;
						i--; //현재 말을 변경한 이동방향으로 한 번 더 진행해야하기 때문에
						blueAgainFlag = true;
						continue;
					}
					else {
						//방향을 바꾼 말이 다시 파란색으로 온 경우 이동하지 않는다.
						blueAgainFlag = false;
					}
				}
				else if(map[nr][nc] == 0) { //흰
					//방향을 바꾼 말이 해당 위치로 온 경우
					if(blueAgainFlag == true) {
						blueAgainFlag = false;
					}
					
					int nUnitStackSize = moveWhiteRed(true, i, nr, nc);
					
					if(nUnitStackSize > maxStack) {
						maxStack = nUnitStackSize;
					}
				}
				else if(map[nr][nc] == 1) { //빨
					//방향을 바꾼 말이 해당 위치로 온 경우
					if(blueAgainFlag == true) {
						blueAgainFlag = false;
					}
					
					int nUnitStackSize = moveWhiteRed(false, i, nr, nc);
					
					if(nUnitStackSize > maxStack) {
						maxStack = nUnitStackSize;
					}
				}
			}
			
			turnCnt++;
			//printStackMap();
		}
		
	}
	
	static int moveWhiteRed(boolean isWhite, int unitNum, int nr, int nc) {
		//해당 위치와 현재 위치의 말의 더미 정보를 구한다.
		int nUnitStack[] = stackMap[nr][nc];
		int cUnitStack[] = stackMap[units[unitNum].r][units[unitNum].c];
		
		int nUnitStackSize = 0;
		int cUnitStackSize = 0;
		
		for(int j = 0; j < K; j++) {
			if(nUnitStack[nUnitStackSize] == -1 && cUnitStack[cUnitStackSize] == -1) {
				break;
			}
			
			if(nUnitStack[nUnitStackSize] != -1) {
				nUnitStackSize++;
			}
			
			if(cUnitStack[cUnitStackSize] != -1) {
				cUnitStackSize++;
			}
		}
		
		//현재 위치의 현재 말 부터 위에 있는 말들을 구한다.
		int startIndex = 0;
		for(int j = 0; j < cUnitStackSize; j++) {
			if(cUnitStack[j] == unitNum) {
				startIndex = j;
				break;
			}
		}
		
		if(isWhite) {
			//흰색인 경우 해당 위치의 말 위에 쌓는다.
			for(int j = startIndex; j < cUnitStackSize; j++) {
				int temp = cUnitStack[j];
				cUnitStack[j] = -1;
				nUnitStack[nUnitStackSize++] = temp;
				
				//해당 칸으로 이동
				units[temp].r = nr;
				units[temp].c = nc;
			}
		}
		else {
			//빨간색인 경우 해당 위치의 말 위에 거꾸로 쌓는다.
			for(int j = cUnitStackSize - 1; j >= startIndex; j--) {
				int temp = cUnitStack[j];
				cUnitStack[j] = -1;
				nUnitStack[nUnitStackSize++] = temp;
				
				//해당 칸으로 이동
				units[temp].r = nr;
				units[temp].c = nc;
			}
		}
		
		//해당 위치에 쌓인 크기를 반환
		return nUnitStackSize;
	}
	
	static void printStackMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int stackSize = 0;
				for(int z = 0; z < K; z++) {
					if(stackMap[i][j][z] == -1) {
						break;
					}
					stackSize++;
				}
				
				if(stackSize > 0) {
					System.out.print(i + ", " + j + " : ");	
					
					for(int z = 0; z < stackSize; z++) {
						System.out.print(stackMap[i][j][z] + ", ");
					}
					System.out.println();
				}
			}
		}
		System.out.println();
	}
}
