#include <bits/stdc++.h>
using namespace std;


string str;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    getline(cin, str);
    int strSize = str.size();

    for (int i = 0; i < strSize; i++) {
        if (str[i] >= 'A' && str[i] <= 'Z') {
            if (str[i] + 13 > 'Z') str[i] = str[i] + 13 - 26;
            else str[i] = str[i] + 13;
        } else if (str[i] >= 'a' && str[i] <= 'z') {
            if (str[i] + 13 > 'z') str[i] = str[i] + 13 - 26;
            else str[i] = str[i] + 13;
        }
        cout << str[i];
    }


    return 0;
}
