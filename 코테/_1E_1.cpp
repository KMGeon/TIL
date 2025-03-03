#include <bits/stdc++.h>
using namespace std;

int cnt;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> cnt;
    string str[cnt];

    for (int i = 0; i < cnt; i++) {
        cin >> str[i];
    }

    map<char, int> map;

    for (string s: str) {
        char first = s[0];
        if (map.find(first) != map.end()) {
            map[first]++;
        }else {
            map.insert(make_pair(first, 1));
        }
    }

    string result ="";
    for (auto x : map) {
        if (x.second >= 5) {
            result += x.first;
        }
    }

    if (result == "") {
        cout << "PREDAJA";
    }else {
        cout << result;
    }

    return 0;
}
