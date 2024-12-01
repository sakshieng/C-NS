#include <iostream>
#include <string>
using namespace std;

int main() {
    int t;
    cin >> t;
    
    while (t--) {
        int n, m, k;
        cin >> n >> m >> k;
        string s;
        cin >> s;

        int cntZero = 0, cnt = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == '0') {
                cntZero++;
                if (cntZero == m) {
                    cnt++;
                    cntZero = 0;
                    i += (k - 1);
                }
            } else {
                cntZero = 0; 
            }
        }
        
        cout << cnt << endl; 
    }
    
    return 0;
}
