#include <bits/stdc++.h>
using namespace std;


int n, k;

// 시간초과

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> k;

    map<int, string> map;
    vector<string> v;


    for (int i = 1; i <= n; i++) {
        string temp;
        cin >> temp;
        map.insert(make_pair(i, temp));
    }

    for (int i = 0; i < k; i++) {
        string temp;
        cin >> temp;
        v.push_back(temp);
    }


// O(n*k) 시간복잡도를 가진다.
    for (string &x: v) {
        if (isalpha(x[0])) {
            for (auto const &[key,value] : map) {
                if (value == x) {
                    cout << key << endl;
                }
            }
        }else {
            for (auto const &[key,value] : map) {
                if (to_string(key) == x) {
                    cout << value << endl;
                }
            }
        }
    }


    return 0;
}
