import java.util.Scanner;

public class Main {
	static class Rotation{
		int x, d, k; //x배수의 원판을 d방향(0:시계, 1:반시계)으로 k칸 회전
		Rotation(){
			
		}
	}
	static int N; //원판개수
	static int M; //각 원판 숫자 개수
	static int T; //회전 횟수
	static int plates[][]; //원판 정보 (i번째 원판 j번째 숫자에 대한 정보)
	static Rotation rotations[];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		T = sc.nextInt();
		
		plates = new int[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				plates[i][j] = sc.nextInt();
			}
		}
		
		rotations = new Rotation[T];
		for(int i = 0; i < T; i++) {
			Rotation r = new Rotation();
			r.x = sc.nextInt();
			r.d = sc.nextInt();
			r.k = sc.nextInt();
			
			rotations[i] = r;
		}
		sc.close();
		
		System.out.println(solution());
	}

	private static void printPlates() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++){
				System.out.print(plates[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	private static int solution() {
		for(int t = 0; t < T; t++) {
			Rotation rotation = rotations[t];
			
			int cnt = rotation.k;
			//시계반대방향인 경우
			//반시계방향 1번 = 시계방향 M - 1번
			//반시계방향 2번 = 시계방향 M - 2번
			//반시계방향 k번 = 시계방향 M - k번
			if(rotation.d == 1) {
				cnt = M-rotation.k;
			}
			
			//x의 배수인 원판을 찾는다.
			for(int i = 0; i < N; i++) {
				if((i + 1)%rotation.x == 0) {
					//x의 배수인 원판을 d 방향으로 k칸 회전한다.
					rotate(i, cnt);
				}
			}
			//printPlates();
			
			//인접한 수가 같은 경우 모두 찾아서 정리한다.
			arrange();
			//printPlates();
		}
		
		//모든 원판의 수를 계산
		int sum = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(plates[i][j] == -1) {
					continue;
				}
				
				sum+=plates[i][j];
			}
		}
		return sum;
	}
	
	//인접한 수가 같은 경우 모두 찾아서 정리
	//인접한 수가 없는 경우 모든 원판의 수의 평균을 구하고 평균보다 큰 수에서 -1, 작은 수에서 +1
	private static void arrange() {
		boolean nothing = true;
		int sum = 0;
		int cnt = 0;
		
		//정리되는 것 저장해뒀다가 한번에 정리해야한다. 연쇄적으로 정리되는 것들을 생각해야함.
		boolean deleteList[][] = new boolean[N][M];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				//정리된 것은 -1로 표시된다.
				if(plates[i][j] == -1) {
					continue;
				}
				
				int value = plates[i][j];
				boolean same = false;
				//인접한 수가 없는 경우를 대비해 모든 원판 위의 수를 더한다.
				sum += value;
				cnt++;
				
				//위로 인접
				int nr = i + 1;
				if(nr >= 0 && nr < N) {
					if(value == plates[nr][j]) {
						deleteList[nr][j] = true;
						same = true;
					}
				}
				
				//옆으로 인접
				int ncLeft = j - 1;
				if(ncLeft < 0) {
					ncLeft = M - 1;
				}
				
				if(value == plates[i][ncLeft]) {
					deleteList[i][ncLeft] = true;
					same = true;
				}
				
				
				int ncRight = j + 1;
				if(ncRight >= M) {
					ncRight = 0;
				}
				
				if(value == plates[i][ncRight]) {
					deleteList[i][ncRight] = true;
					same = true;
				}
				
				
				if(same) {
					deleteList[i][j] = true;
					nothing = false; //하나라도 인접한게 있었다면
				}
			}
		}
		
		//정리
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(deleteList[i][j] == true) {
					plates[i][j] = -1;
				}
			}
		}
		
		//주의!) 나누기할 때 항상 0값 체크하기!
		if(nothing && cnt != 0) {
			//평균구해서 정리
			int avr = sum/cnt;
			boolean isRest = sum%cnt == 0 ? false : true; //나머지가 있는지
			
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < M; j++) {
					if(plates[i][j] == -1) {
						continue;
					}
					
					if(plates[i][j] > avr) {
						plates[i][j]--;
					}
					else if(plates[i][j] < avr) {
						plates[i][j]++;
					}
					else if(plates[i][j] == avr && isRest) {
						//평균의 정수값이 같은 경우 나머지가 있으면 평균보다 작은 것으로 판단.
						plates[i][j]++;
					}
				}
			}
		}
	}
	
	//해당 원판을 시계방향으로 회전시켜주는 함수
	private static void rotate(int plateNum, int cnt) {
		int plateOrder[] = plates[plateNum];
		int newOrder[] = new int[M];
		
		for(int i = 0; i < M; i++) {
			int newIndex = i + cnt;
			if(newIndex >= M) {
				newIndex -= M;
			}
			
			newOrder[newIndex] = plateOrder[i];
		}
		
		plates[plateNum] = newOrder;
	}
}
