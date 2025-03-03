#include <bits/stdc++.h>
using namespace std;


int n, k;


#include <bits/stdc++.h>
using namespace std;

// 시간초과
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> k;

    map<string, int> str_to_num;    // 포켓몬 이름 -> 번호
    map<int, string> num_to_str;     // 번호 -> 포켓몬 이름

    // 입력받으면서 양쪽 맵에 모두 저장
    for (int i = 1; i <= n; i++) {
        string temp;
        cin >> temp;
        str_to_num[temp] = i;
        num_to_str[i] = temp;
    }

    // 질문 처리
    for (int i = 0; i < k; i++) {
        string query;
        cin >> query;

        if (isalpha(query[0])) {
            // 포켓몬 이름이 주어진 경우
            cout << str_to_num[query] << '\n';
        } else {
            // 번호가 주어진 경우
            cout << num_to_str[stoi(query)] << '\n';
        }
    }

    return 0;
}
