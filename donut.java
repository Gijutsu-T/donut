import java.io.IOException;
import java.util.concurrent.TimeUnit;

class Main {
    static double A = 1;
    static double B = 1;

    public static void main(String[] args) throws InterruptedException, IOException {
        final boolean isWindows = System.getProperty("os.name").contains("Windows");

        if (isWindows) {
            System.out.println("This animation may not work smoothly on the Windows console. Try using a terminal emulator like Windows Terminal or Git Bash.");
        }

        // hide cursor
        System.out.print("\033[?25l");

        try {
            while (true) {
                // create a buffer for the output
                StringBuilder output = new StringBuilder();
                output.append("\033[H"); // move cursor to the top-left corner

                A += 0.07;
                B += 0.03;

                double cA = Math.cos(A),
                        sA = Math.sin(A),
                        cB = Math.cos(B),
                        sB = Math.sin(B);

                final char[] b = new char[1760];
                final double[] z = new double[1760];

                for (int k = 0; k < 1760; k++) {
                    b[k] = k % 80 == 79 ? '\n' : ' ';
                    z[k] = 0;
                }

                for (double j = 0; j < 6.28; j += 0.07) {
                    double ct = Math.cos(j), st = Math.sin(j);
                    for (double i = 0; i < 6.28; i += 0.02) {
                        double sp = Math.sin(i),
                                cp = Math.cos(i),
                                h = ct + 2,
                                D = 1 / (sp * h * sA + st * cA + 5),
                                t = sp * h * cA - st * sA;

                        int x = (int) (40 + 30 * D * (cp * h * cB - t * sB)),
                                y = (int) (12 + 15 * D * (cp * h * sB + t * cB)),
                                N = (int) (8 *
                                        ((st * sA - sp * ct * cA) * cB -
                                                sp * ct * sA - st * cA -
                                                cp * ct * sB)),
                                o = x + 80 * y;

                        if (y < 22 && y >= 0 && x >= 0 && x < 79 && D > z[o]) {
                            z[o] = D;
                            b[o] = ".,-~:;=!*#$@".charAt(Math.max(N, 0));
                        }
                    }
                }

                // append the buffer to the output
                output.append(String.valueOf(b));
                System.out.print(output.toString());

                
                TimeUnit.MILLISECONDS.sleep(50);
            }
        } finally {
            // show cursor again before exiting
            System.out.print("\033[?25h");
        }
    }
}
