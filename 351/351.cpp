#include <math.h>
#include <stdio.h>
#include <iostream>

#define ll long long

using namespace std;

// O(sqrt(n))
void phi_1_to_n(int n) {
    vector<int> phi(n + 1);
    for (int i = 0; i <= n; i++)
        phi[i] = i;

    for (int i = 2; i <= n; i++) {
        if (phi[i] == i) {
            for (int j = i; j <= n; j += i)
                phi[j] -= phi[j] / i;
        }
    }
}

// sum the totient of each diagonal --> the sum of the # of relative primes
int main() {
    int ceil = 100000000;
    ll blocked = 0;
    int s = 1;
    for (int i = 0; i < ceil; i++) {
        blocked += i - phi(i);
        if (i % 1000000 == 0) {
            cout << s << "% of the way" << endl;
            s++;
        }
    } 
    cout << blocked * 6 + 36000000 << endl;
}