#include <bits/stdc++.h>
using namespace std;

int n;
string strPtn;

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

    cin >> n;
    string files[n];

    cin >> strPtn;

    for (int i = 0; i < n; i++) cin >> files[i];


    vector<string> pattern = split(strPtn, '*');
    string prefix = pattern[0];  // 패턴의 앞부분
    string suffix = pattern[1];  // 패턴의 뒷부분



    for (string file : files) {
        if (file.length() < prefix.length() + suffix.length()) {
            cout << "NE" << '\n';
            continue;
        }

        bool prefixMatch = file.substr(0, prefix.length()) == prefix;
        bool suffixMatch = file.substr(file.length() - suffix.length()) == suffix;

        if (prefixMatch && suffixMatch) {
            cout << "DA" << '\n';
        } else {
            cout << "NE" << '\n';
        }
    }



    return 0;
}


/**
#include <bits/stdc++.h>
using namespace std;

int n;
string strPtn;


int main() {
ios_base::sync_with_stdio(false);
cin.tie(NULL);
cout.tie(NULL);

cin >> n;
string files[n];

cin >> strPtn;

for (int i=0; i<n; i++) cin >> files[i];

char start = strPtn[0];
char end = strPtn[strPtn.length()-1];


for (string x : files) {
char fileStart = x[0];
char fileEnd = x[x.length()-1];
if (start == fileStart && end == fileEnd) {
cout << "DA" << endl;
}else {
cout << "NE"<< endl;
}
}




return 0;
}
*/
