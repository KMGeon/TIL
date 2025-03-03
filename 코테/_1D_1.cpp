#include <bits/stdc++.h>
using namespace std;


string str;

int main() {
    int result = 0; // false
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> str;
    int a = str.size();

    bool isEven = a % 2 == 0;

    if (isEven) {
        // 짝수
        int temp =  a/2;
        if (str[(temp) - 1] != str[temp]) {
            cout << result;
            return 0;
        }
        for (int i=0; i< temp-1; i++) {
            if (str[i] != str[a-i-1]) {
                cout << result;
                return 0;
            }
        }

        cout <<"1"<<'\n';
    } else {
        for (int i = 0; i < a; i++) {
            if (str[i] != str[a-i-1]) {
                cout << result << " ";
                return 0;
            }
        }

        cout <<"1"<<'\n';
    }
    return 0;
}