import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static class Shark{
		public int r, c, s, d, z;
		//d는 방향 정보 0: 위, 1: 아래, 2: 오른쪽, 3: 왼쪽
	}
	
	static int R, C;
	static int map[][]; //상어의 수로 상어 분포 상태만 표시한다.
	static ArrayList<Shark> sharks = new ArrayList<Main.Shark>();
	static int human = -1; //인간의 첫 위치
	static int sum = 0; //잡은 상어 크기 합
	static final int dr[] = {-1, 1, 0, 0};
	static final int dc[] = {0, 0, 1, -1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		map = new int[R][C];
		int M = sc.nextInt();
		for(int i = 0; i < M; i++) {
			Shark shark = new Shark();
			shark.r = sc.nextInt() - 1;
			shark.c = sc.nextInt() - 1;
			shark.s = sc.nextInt();
			shark.d = sc.nextInt() - 1;
			shark.z = sc.nextInt();
			
			sharks.add(shark);
			map[shark.r][shark.c] = 1;
		}
		sc.close();
		
		solution();
		System.out.println(sum);
	}
	
	private static void solution() {
		while(true) {
			human++;
			if(human == C) {
				break;
			}
			hunt();
			move();
			arrangement();
		}
	}
	
	private static void printMap() {
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	//상어들이 모두 이동한다. (map 상에 한 위치에 여러개의 상어가 존재할 수 있음)
	//수 계산이 속도가 더 빠를것 같음
	private static void move() {
		map = new int[R][C]; //맵 갱신
		
		for(Shark shark : sharks) {
			int d = shark.d;
			int cr = shark.r;
			int cc = shark.c;
			int nr, nc;
			
			for(int i = 0; i < shark.s; i++) {
				//현재 위치가 경계 위치 일 때 반대방향으로 바꿔준다.
				if(cr == 0 && d == 0 ) {
					d = 1;
				}
				else if(cr == R - 1 && d == 1) {
					d = 0;
				}
				else if(cc == C - 1 && d == 2) {
					d = 3;
				}
				else if(cc == 0 && d == 3) {
					d = 2;
				}
				
				nr = cr + dr[d];
				nc = cc + dc[d];
				
				cr = nr;
				cc = nc;
			}
			
			//속도 만큼 이동한 후 위치로 변경해준다.
			//상어의 현재 이동 방향도 저장한다.
			shark.r = cr;
			shark.c = cc;
			shark.d = d;
			
			//맵의 현재 위치에 상어 수를 증가시킨다.
			map[shark.r][shark.c]++; 
		}
	}
	
	//같은 위치에 여러 상어가 있는 경우 큰 상어만 남기고 정리한다. (map 상에 한 위치에 한마리의 상어만 존재하게 됨)
	private static void arrangement() {
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				if(map[r][c] > 1) {
					Shark maxSizeShark = null;
					ArrayList<Shark> removeSharks = new ArrayList<Main.Shark>();
					
					for(Shark shark : sharks) {
						if(shark.r == r && shark.c == c) {
							if(maxSizeShark == null) {
								maxSizeShark = shark;
							}
							else {
								//더 큰 상어를 찾으면 교체하고 교체 당한 상어는 제거 목록에 추가.
								if(maxSizeShark.z < shark.z) {
									Shark removeShark = maxSizeShark;
									removeSharks.add(removeShark);
									maxSizeShark = shark;
								}
								//가장 큰 상어가 아니라면 제거 목록에 추가.
								else {
									Shark removeShark = shark;
									removeSharks.add(removeShark);
								}
							}
						}
					}
					
					//제거 목록의 상어 제거
					for(Shark shark : removeSharks) {
						sharks.remove(shark);
						map[r][c]--; //맵에서도 개수를 줄인다.
					}
				}
			}
		}
	}
	
	//인간의 현재 위치에서 가장 가까운 상어를 잡는다.
	private static void hunt() {
		for(int i = 0; i < R; i++) {
			
			//map의 상태는 위치당 한마리의 상어만 존재해야한다.
			if(map[i][human] > 0) {
				
				//상어 정보를 찾아서 잡아먹고 삭제
				for(Shark shark : sharks) {
					if(shark.r == i && shark.c == human) {
						sum += shark.z;
						sharks.remove(shark);
						break;
					}
				}
				
				map[i][human] = 0; //맵에서도 삭제
				
				break;
			}
		}
	}
}
