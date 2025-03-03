#include <bits/stdc++.h>
using namespace std;


int n, cnt[26];
string s, ret;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> s;
        cnt[s[0] - 'a']++;
    }

    for (int i = 0; i < n; i++) {
        if (cnt[i] >= 5) {
            ret += (i + 'a');
        }
    }

    int size = ret.size();
    if (size == 0) {
        cout << "PREDAJA";
    } else {
        cout << ret;
    }


    return 0;
}
