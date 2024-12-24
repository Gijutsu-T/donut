#include <iostream>
#include <cmath>
#include <thread>
#include <chrono>
#include <string>


static double A = 1;
static double B = 1;

int main() {
    const bool isWindows = std::system("ver") == 0;

    if (isWindows) {
        std::cout << "This animation may not work smoothly on the Windows console. Try using a terminal emulator like Windows Terminal or Git Bash." << std::endl;
    }

    // hide cursor
    std::cout << "\033[?25l";

    try {
        while (true) {
            // create a buffer for the output
            std::string output;
            output.append("\033[H"); // move cursor to the top-left corner

            A += 0.07;
            B += 0.03;

            double cA = std::cos(A),
                   sA = std::sin(A),
                   cB = std::cos(B),
                   sB = std::sin(B);

            char b[1760];
            double z[1760];

            for (int k = 0; k < 1760; k++) {
                b[k] = k % 80 == 79 ? '\n' : ' ';
                z[k] = 0;
            }

            for (double j = 0; j < 6.28; j += 0.07) {
                double ct = std::cos(j), st = std::sin(j);
                for (double i = 0; i < 6.28; i += 0.02) {
                    double sp = std::sin(i),
                           cp = std::cos(i),
                           h = ct + 2,
                           D = 1 / (sp * h * sA + st * cA + 5),
                           t = sp * h * cA - st * sA;

                    int x = static_cast<int>(40 + 30 * D * (cp * h * cB - t * sB)),
                        y = static_cast<int>(12 + 15 * D * (cp * h * sB + t * cB)),
                        N = static_cast<int>(8 *
                                ((st * sA - sp * ct * cA) * cB -
                                 sp * ct * sA - st * cA -
                                 cp * ct * sB)),
                        o = x + 80 * y;

                    if (y < 22 && y >= 0 && x >= 0 && x < 79 && D > z[o]) {
                        z[o] = D;
                        b[o] = ".,-~:;=!*#$@"[std::max(N, 0)];
                    }
                }
            }

            // append the buffer to the output
            output.append(b, 1760);
            std::cout << output;

            
            std::this_thread::sleep_for(std::chrono::milliseconds(50));
        }
    } catch (...) {
        // show cursor again before exiting
        std::cout << "\033[?25h";
    }

    return 0;
}

