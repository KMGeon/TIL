#include <bits/stdc++.h>
using namespace std;

/**
 *


1213
맵 구성: O(n log k)
팰린드롬 가능성 검사: O(k)
팰린드롬 생성: O(n)

이 중 가장 지배적인 복잡도는 O(n log k)이므로, 전체 알고리즘의 시간 복잡도는 O(n log k)입니다.
 */
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    string str;
    cin >> str;

    int strSize = str.size();
    bool isEven = strSize % 2 == 0;

    map<int, int> _map;

    for (char x : str) {
        _map[int(x)]++;
    }

    // 미리 실패
    if (isEven) {
        for (auto x: _map) {
            if (x.second % 2 != 0) {
                cout << "I'm Sorry Hansoo";
                return 0;
            }
        }
    } else {
        int cnt = 0;
        for (auto x: _map) {
            if (x.second % 2 != 0) {
                cnt++;
                if (cnt != 1) {
                    cout << "I'm Sorry Hansoo";
                    return 0;
                }
            }
        }
    }

    // // even
    if (isEven) {
        string temp;
        for (auto x: _map) {
            int count =  x.second/2;
            for (int i=0; i<count; i++) {
                temp += x.first;
            }

        }
        string result;
        result += temp;
        reverse(temp.begin(), temp.end());
        result += temp;
        cout << result;
    }
    // else {
    //     string key;
    //     string temp;
    //     for (auto x: _map) {
    //         if (x.second%2==0) {
    //             int count = x.second / 2;
    //             for (int i = 0; i < count; i++) {
    //                 temp += x.first;
    //             }
    //         }else {
    //             key = x.first;
    //         }
    //     }
    //     string result;
    //     result += temp + key;
    //     reverse(temp.begin(), temp.end());
    //     result += temp;
    //     cout << result;
    // }
    else {
        char middleChar;
        string temp;
        for (auto x: _map) {
            if (x.second % 2 == 0) {
                int count = x.second / 2;
                temp += string(count, char(x.first));
            } else {
                // 여기서 디테일 실수
                int count = (x.second - 1) / 2;
                temp += string(count, char(x.first));
                middleChar = char(x.first);
            }
        }
        string result = temp;
        result += middleChar;
        reverse(temp.begin(), temp.end());
        result += temp;
        cout << result;
    }


    return 0;
}
