import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavacFlagTests {

    @Test
    public void testCompleteCompilation() {
        try {
            String expectedOutput = "\nBY_TODO\n" +
                    "Parsing: Complete\n" +
                    "Attribution: Complete\n" +
                    "Aliveness: Complete\n" +
                    "Definite Assignment: Complete\n" +
                    "Exception Flow: Complete";
            Process process = Runtime.getRuntime().exec("langtools/bin/javac HelloWorld.java");
            process.waitFor();
            assertEquals(0, process.exitValue());

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String console = reader.lines().reduce("", (a, b) -> a + "\n" + b);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
