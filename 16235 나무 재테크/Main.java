import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	//땅 정보 클래스
	private static class Space{
		public ArrayList<Integer> treeList; //땅이 갖는 나무 나이 목록
		public ArrayList<Integer> deadTreeList; //땅이 갖는 죽은 나무 나이 목록
		public int n; //땅이 갖는 양분 양
		
		public Space() {
			treeList = new ArrayList<Integer>();
			deadTreeList = new ArrayList<Integer>();
			n = 5; //초기 모든 땅의 양분 양은 5
		}
	}
	static int N, M, K; //N: 맵 크기, M: 초기 나무 개수, K: 목표 날짜 (K년 동안)
	static Space map[][];
	static int A[][]; //각 땅에 추가되는 양분의 양
	static int cnt = 0;
	//상, 하, 좌, 우, 상좌, 하좌, 상우, 하우
	static final int dx[] = {-1, 0, 1, -1, 1, -1, 0, 1};
	static final int dy[] = {-1, -1, -1, 0, 0, 1, 1, 1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		
		map = new Space[N][N];
		A = new int[N][N];
		
		//각 땅에 뿌려줄 양분 입력과 땅 생성
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				map[i][j] = new Space();
				
				A[i][j] = sc.nextInt();
			}
		}
		
		//초기 나무 위치와 나이
		for(int i = 0; i < M; i++) {
			int x = sc.nextInt() - 1;
			int y = sc.nextInt() - 1;
			int age = sc.nextInt();
			
			map[x][y].treeList.add(age); //x,y 위치에 나이가 age인 나무를 추가
		}
		
		sc.close();
		
		solution();
		System.out.println(cnt);
	}
	
	private static void solution() {
		while(K-- > 0) {
			//사계절
			for(int i = 0; i < 4; i++) {
				oneYear(i);
			}
		}
		
		//나무 개수 세기
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j].treeList.size() > 0) {
					cnt += map[i][j].treeList.size();
				}
			}
		}
	}
	
	private static void oneYear(int season) {
		switch(season) {
		case 0: //봄
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(map[i][j].treeList.size() > 0) {
						ArrayList<Integer> tempTreeList = (ArrayList<Integer>) map[i][j].treeList.clone();
						ArrayList<Integer> tempDeadTreeList = (ArrayList<Integer>) map[i][j].deadTreeList.clone();
						int tempN = map[i][j].n;
						
						Collections.sort(tempTreeList); //나이 어린 나무 부터 정렬
						
						for(int z = 0; z < tempTreeList.size(); z++) {
							//양분이 모자라는 경우 현재 나무 부터 다 죽인다.(정렬되어있으니까 뒤에는 나이가 더 많은 나무들)
							if(tempN < tempTreeList.get(z)) {								
								for(int h = z; h < tempTreeList.size(); h++) {
									int deadTreeAge = tempTreeList.get(h);
									tempDeadTreeList.add(deadTreeAge); //죽은 나무 저장
								}
								
								//죽은 나무 개수 만큼 뒤에서 부터 제거한다.
								for(int h = 0; h < tempDeadTreeList.size(); h++) {
									tempTreeList.remove(tempTreeList.size() - 1);
								}
								break;
							}
							else {
								//양분 먹기
								tempN -= tempTreeList.get(z);
								
								//나이 증가
								int tempAge = tempTreeList.get(z);
								tempTreeList.set(z, tempAge + 1);
							}
						}
						
						//계산된 양분과 나무 목록 정보를 다시 입력
						map[i][j].n = tempN;
						map[i][j].treeList = tempTreeList;
						map[i][j].deadTreeList = tempDeadTreeList;
					}
				}
			}
			break;
		case 1: //여름
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					//죽은 나무가 있으면 양분으로 변경
					if(map[i][j].deadTreeList.size() > 0) {
						int tempN = map[i][j].n;
						ArrayList<Integer> tempDeadTreeList = (ArrayList<Integer>) map[i][j].deadTreeList.clone();
						
						for(int z = 0; z < tempDeadTreeList.size(); z++) {
							int addN = tempDeadTreeList.get(z) / 2;
							tempN += addN;
						}
						tempDeadTreeList.clear();
						
						map[i][j].n = tempN;
						map[i][j].deadTreeList = tempDeadTreeList;
					}
				}
			}
			break;
		case 2: //가을
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					//나무가 있는 경우 번식할 나무 (나이가 5배수)가 있는지 검사하고 있으면 주변에 나무 번식한다.
					if(map[i][j].treeList.size() > 0) {
						ArrayList<Integer> tempTreeList = (ArrayList<Integer>) map[i][j].treeList.clone();
						
						for(int z = 0; z < tempTreeList.size(); z++) {
							int age = tempTreeList.get(z);
							//번식할 나무라면 번식 주변 8개 땅에 번식시킨다.
							if(age % 5 == 0) {
								
								for(int h = 0; h < 8; h++) {
									int nx = j + dx[h];
									int ny = i + dy[h];
									
									if(nx >= 0 && nx < N && ny >= 0 && ny < N) {
										map[ny][nx].treeList.add(1); //번식
									}
								}
							}
						}
					}
				}
			}
			break;
		case 3: //겨울
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(A[i][j] != 0) {
						map[i][j].n += A[i][j]; //양분 추가
					}
				}
			}
			break;
		}
	}
}
