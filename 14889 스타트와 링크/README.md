- N명 중 N/2명을 선택해서 팀을 만들어야한다. 이를 위해서 순열 또는 조합 방식의 구현을 할 수 있다. 조합의 경우 순서를 고려하지 않기 때문에 중복되는 집합을 얻을 수 있다. 예를들어 {1, 3, 5} 가 한 팀일 때, {3, 1, 5}가 또 도출될 수 있다. 반면 순열 구현 시 이와 같은 중복 집합을 얻지 않는다.  
  하지만 조합 구현 시 비트연산을 사용했고, 순열 구현에는 재귀함수를 사용하여 속도 측면에서는 조합을 이용한 방법이 더 빨랐다.
- 팀이 정해지고 팀의 능력치를 계산할 때, 탐색을 진행하면서 i번 사람과 j번 사람이 같은 팀일 경우 $S_{ij}$ 와 $S_{ji}$ 을 함께 합산해주는 것이 효율적이다.  그렇지 않으면 i번 사람과 같은 팀인 j번 사람을 찾기 위해 인덱스 0부터 다시 탐색해야한다.  
  예를들어 {1, 3, 5} 가 한 팀일 때 {1,3}, {1,5}, {3,1}, {3,5}, {5,1}, {5,3} 의 탐색과 {1,3}, {3,1}, {1, 5}, {5,1}, {3,5}, {5,3} 의 차이 이다.