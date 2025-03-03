#include <bits/stdc++.h>
using namespace std;
int payA, payB, payC;
int carAEnter, carBEnter, carCEnter;
int carAEnd, carBEnd, carCEnd;
int container[102];
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> payA >> payB >> payC;
    cin >> carAEnter >> carAEnd;
    cin >> carBEnter >> carBEnd;
    cin >> carCEnter >> carCEnd;

    for (int i = carAEnter; i < carAEnd; i++) {
        container[i] ++;
    }

    for (int i = carBEnter; i < carBEnd; i++) {
        container[i] ++;
    }

    for (int i = carCEnter; i < carCEnd; i++) {
        container[i] ++;
    }

    int sum = 0;
    for (int x: container) {
        switch (x) {
            case 1:
                sum += payA;
            break;
            case 2:
                sum += payB *2;
            break;
            case 3:
                sum += payC *3;
            break;
        }
    }
    cout << sum << "\n";
    return 0;
}
