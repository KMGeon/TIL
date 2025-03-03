#include <bits/stdc++.h>
using namespace std;

string str;
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    map<char, int> map;
    for (char c = 'a'; c <= 'z'; c++) map[c] = 0;

    cin >> str;

    for ( char x : str) {
        if (auto it = map.find(x); it != map.end()) {
            it->second++;
        }
    }


    for (auto x : map) {
        cout << x.second << " ";
    }
    return 0;
}

// 다른 방식으로 문제를 풀면



