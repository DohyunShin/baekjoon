- 총감독관을 각 시험장 마다 오직 1명만 있어야한다는 말이 없어도 된다는 것인지 애매했는데, 테스트케이스를 보고 무조건 1명은 있어야한다는 것을 알았다.
- 본 문제는 시험장의 개수가 최대 1,000,000개 이고 각 시험장의 응시자 수 역시 최대 1,000,000개 이다. 최악의 경우 B, C가 모두 1이라면 한 시험장 당 1,000,000명의 감독관이 필요하다. 따라서 필요한 감독관 수를 저장하는 변수의 자료형은 `long`으로 사용해야한다.
- 시험장 개수가 최대 1,000,000개 이고 동일한 응시자 수를 갖는 시험장이 여러개 있을 수 있다. 응시자 수에 따라 필요한 감독관 수는 정해지기 때문에 메모이제이션을 활용하여 반복되는 상황에서 시간을 단축시켜야한다.