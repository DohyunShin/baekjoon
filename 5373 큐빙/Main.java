import java.util.Scanner;

/*
 전개도
            -----------
            | 0  1  2 |
            | 3  4  5 |
            | 6  7  8 |
 ----------------------------------
 | 9  10 11 | 18 19 20 | 27 28 29 |
 | 12 13 14 | 21 22 23 | 30 31 32 |
 | 15 16 17 | 24 25 26 | 33 34 35 |
 ----------------------------------
            | 36 37 38 |
            | 39 40 41 |
            | 42 43 44 |
            ------------
            | 45 46 47 |
            | 48 49 50 |
            | 51 52 53 |
            ------------
U : 0~8
L : 9~17
F : 18~26
R : 27~35
D : 36~44
B : 45~53

초기 색상 : U(w), L(g), F(r), R(b), D(y), B(o)

 */

public class Main {
	static int n;
	static int nuberingCube[][][]; //인덱스 번호를 부여
	static char cube[]; //큐브 인덱스에 따른 색상 부여
	
	//시계방향으로 돌렸을 때 세 개씩 밀리는 상황을 생각해서 정렬하여 삽입
	//네개의 면에 각 3개씩, 총 12개
	//각 시작을 면의 위로 생각하고 위->왼->아->오 로 생각
	final static int SIDE_NUMBERS[][] = {
			{51, 52, 53, 11, 10, 9, 20, 19, 18, 29, 28, 27}, 	//U
			{0, 3, 6, 45, 48, 51, 36, 39, 42, 18, 21, 24}, 		//L
			{6, 7, 8, 17, 14, 11, 38, 37, 36, 27, 30, 33}, 		//F
			{8, 5, 2, 26, 23, 20, 44, 41, 38, 53, 50, 47}, 		//R
			{24, 25, 26, 15, 16, 17, 47, 46, 45, 33, 34 ,35}, 	//D
			{42, 43, 44, 9, 12, 15, 2, 1, 0, 35, 32, 29}  		//B
	};
	
	final static char COLORS[] = {'w', 'g', 'r', 'b', 'y', 'o'};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		
		int cnt;
		
		for(int i = 0; i < n; i++) {
			//큐브 초기화
			initCube();
			
			cnt = sc.nextInt();
			
			for(int j = 0; j < cnt; j++) {
				String F = sc.next();
				char face = F.charAt(0);
				char dir = F.charAt(1);
				
				rotate(face, dir);	
			}
			
			//큐브 윗면 출력
			printCubeUpFace();
		}
		sc.close();
	}
	
	private static void initCube() {
		nuberingCube = new int[6][3][3];
		int num = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 3; j++) {
				for(int c = 0; c < 3; c++) {
					nuberingCube[i][j][c] = num++;
				}
			}
		}
		
		cube = new char[54];
		for(int i = 0; i < 54; i++) {
			cube[i] = COLORS[i / 9 ];
		}
	}
	
	private static void printCubeUpFace() {
		int index = 0;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(cube[index++]);
			}
			System.out.println();
		}
		//System.out.println();
	}
	
	private static int getFaceIndex(char face) {
		int index = -1;
		
		switch(face) {
		case 'U':
			index = 0;
			break;
		case 'L':
			index = 1;
			break;
		case 'F':
			index = 2;
			break;
		case 'R':
			index = 3;
			break;
		case 'D':
			index = 4;
			break;
		case 'B':
			index = 5;
			break;
		}
		
		return index;
	}
	
	private static void rotate(char face, char dir) {
		int faceIndex = getFaceIndex(face);
		int cnt  = dir == '+' ? 1 : 3; //1: 시계방향, 3: 시계반대방향
		
		for(int i = 0 ; i < cnt; i++) {
			//1. 네 방향에 붙어있는 옆면 돌리기
			char tempCubeSide[] = new char[12];
			
			for(int j = 0; j < 12; j++) {
				tempCubeSide[j] = cube[SIDE_NUMBERS[faceIndex][j]]; //해당 면에 해당하는 네방향의 면에 붙어있는 인덱스를 통해 색상 배열을 가져온다.
			}
			
			//3개씩 밀어서 다시 넣는다.
			for(int j = 0; j < 12; j++) {
				cube[SIDE_NUMBERS[faceIndex][j]] = tempCubeSide[(j+3) % 12];
			}
			
			//2. 해당 면 돌리기
			char tempCubeFace[][] = new char[3][3];
			for(int j = 0; j < 3; j++) {
				for(int z = 0; z < 3; z++) {
					//해당 면의 인덱스들을 통해 색상을 옮긴다.
					//열을 반대의 행으로 변경한다. 예) (0,0) (0,1) (0,2) -> (0,2) (1,2) (2,2)
					tempCubeFace[z][2 - j] = cube[nuberingCube[faceIndex][j][z]]; 
				}
			}
			
			for(int j = 0; j < 3; j++) {
				for(int z = 0; z < 3; z++) {
					cube[nuberingCube[faceIndex][j][z]] = tempCubeFace[j][z];
				}
			}
		}
	}
}

