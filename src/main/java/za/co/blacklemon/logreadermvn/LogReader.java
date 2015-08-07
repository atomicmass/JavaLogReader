package za.co.blacklemon.logreadermvn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import org.fusesource.jansi.AnsiConsole;

/**
 *
 * @author g980064
 */
public class LogReader {

    private static Logger log = Logger.getLogger(LogReader.class);
    private static String fileName = "D:\\Server\\wildfly-8.1.0.Final\\standalone\\log\\server.log";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        readFile(fileName);
    }

    private static void readFile(String fileName) {
        AnsiConsole.systemInstall();
        FileInputStream in = null;
        PrintStream out = null;

        try {
            in = new FileInputStream(fileName);
            out = System.out;

//            System.out.println(ansi().fg(RED).a("Hello World").reset());
//            System.out.println("My Name is Raman");
            in.skip(in.available() - 10000);
            int c;
            StringBuilder sb = null;
            while (1 == 1) {
                sb = new StringBuilder();
                while ((c = in.read()) != -1) {
                    byte[] b = new byte[]{(byte) c};
                    sb.append(new String(b, Charset.forName("UTF-8")));
                }

                if (sb.length() > 0) {
                    String str = sb.toString();
                    if (str.indexOf(" WARN ") == 23) {
                        out.println(ansi().fg(YELLOW).a(str).reset());
                    } else if (str.indexOf(" FATAL ") == 23 ) {
                        out.println(ansi().fg(RED).bg(WHITE).a(str).reset());
                    } else if (str.indexOf(" ERROR ") == 23 ) {
                        out.println(ansi().fg(RED).a(str).reset());
                    } else {
                        out.println(str);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ex) {
                    log.error(ex);
                }
            }

        }

        AnsiConsole.systemUninstall();
    }
}
