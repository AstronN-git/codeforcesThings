#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

/*
    author: _Astron
    for codeforces
    file creation date: ${DATE}-${HOUR}:${MINUTE}:${SECOND}
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.StringTokenizer;

public class ${NAME} {
    public static void main (String[] args) {
        ${NAME} main = new ${NAME}();
        main.solution();
    }

    public PrintWriter out;

    public void solution() {
        CustomScanner in = new CustomScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));





        out.close();
    }

    /*
    thanks to Flatfoot for this class
    */
    public static class CustomScanner {
        BufferedReader br;
        StringTokenizer st;

        public CustomScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine(){
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
