#include <bits/stdc++.h>
using namespace std;

int n, k;
int arr[100001], psum[100001];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> k;

    for (int i = 1; i <= n; i++) {
        cin >> arr[i];
        psum[i] = psum[i - 1] + arr[i];
    }

    int max = psum[k] - psum[0];

    // i는 구간의 시작점
    for (int i = 1; i <= n-k; i++) {
        int temp = psum[i+k] - psum[i];
        if (temp > max) max = temp;
    }

    cout << max;

    return 0;
}
