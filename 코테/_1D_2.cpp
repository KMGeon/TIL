#include <bits/stdc++.h>
using namespace std;


string str;


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> str;

    string temp = str;
    reverse(temp.begin(), temp.end());

    if (temp == str) {
        cout << "1";
    } else {
        cout << "0";
    }

    return 0;
}
