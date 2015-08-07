package za.co.blacklemon.logreadermvn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
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
    private static String FILENAME = "D:\\Server\\wildfly-8.1.0.Final\\standalone\\log\\server.log";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fileName = FILENAME;
        if(args.length > 0)
            fileName = args[0];
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
            in.skip(in.available() - 100000);
            int c;
            StringBuilder sb = null;
            while (1 == 1) {
                sb = new StringBuilder();
                while ((c = in.read()) != -1) {
                    byte[] b = new byte[]{(byte) c};
                    sb.append(new String(b, Charset.forName("UTF-8")));
                }

                Color color = null;
                if (sb.length() > 0) {
                    String[] lines = sb.toString().split("\n");
                    for (String str : lines) {
                        if (str.indexOf(" INFO ") == 23) {
                            color = null;
                        } else if (str.indexOf(" WARN ") == 23) {
                            color = YELLOW;
                            //out.println(ansi().fg(YELLOW).a(str).reset());
                        } else if (str.indexOf(" FATAL ") == 23) {
                            color = RED;
                            //out.println(ansi().fg(RED).bg(WHITE).a(str).reset());
                        } else if (str.indexOf(" ERROR ") == 23) {
                            color = RED;
                            //out.println(ansi().fg(RED).a(str).reset());
                        } else {
                            //out.println(str);
                        }
                        if(color != null) {
                            out.println(ansi().fg(color).a(str).reset());
                        } else {
                            out.println(ansi().a(str));
                        }
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
