#include <bits/stdc++.h>
using namespace std;

vector<string> split(string str, char delimiter) {
    vector<string> result;
    stringstream ss(str);
    string temp;

    while (getline(ss, temp, delimiter)) {
        result.push_back(temp);
    }
    return result;
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);


    int n;
    cin >> n;


    for (int i = 0; i < n; i++) {
        int k;
        cin >> k;
        cin.ignore();

        map<string, int> wear;

        for (int i = 0; i < k; i++) {
            string temp;
            getline(cin, temp);

            auto x = split(temp, ' ');

            if (auto it = wear.find(x[1]); it != wear.end()) wear[x[1]]++;
            else wear.insert(make_pair(x[1], 1));
        }

        int result = 1;


        if (wear.size() == 1) {
            cout << wear.begin()->second << '\n';
            continue;
        }

        for (const auto& x : wear) {
            result *= (x.second + 1);
        }
        // 알몸인 경우를 제외
        cout << result - 1 << '\n';

    }
    return 0;
}
