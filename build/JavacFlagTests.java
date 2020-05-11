import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavacFlagTests {

    private String runCompilation(String program, boolean pass) throws Exception {
        ProcessBuilder builder = new ProcessBuilder("langtools/bin/javac", program);
        builder.redirectErrorStream(true);
//        builder.redirectError(System.out);

        Process process = builder.start();
        process.waitFor();
        if (pass) {
            assertEquals(0, process.exitValue());
        } else {
            assertNotEquals(0, process.exitValue());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String console = reader.lines()/*.filter(s -> s.contains("Flag - "))*/.reduce("", (a, b) -> a + "\n" + b).substring(1);
        return console;
    }

    @Test
    public void testCompleteCompilation () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Complete\n" +
                    "Flag - Liveness: Complete\n" +
                    "Flag - Definite Assignment: Complete\n" +
                    "Flag - Exception Flow: Complete\n" +
                    "Flag - Capture: Complete";

            String console = runCompilation("HelloWorld.java", true);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedParsing () {
        try {
            String expectedOutput = "Flag - Parsing: Failed";

            String console = runCompilation("FailParsing.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedTypeCheck_01 () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Failed";

            String console = runCompilation("TypeCheck.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedTypeCheck_02 () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Failed\n" +
                    "Flag - Attribution: Failed\n" +
                    "Flag - Attribution: Failed";

            String console = runCompilation("ComplexTypeCheck.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedLiveness () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Complete\n" +
                    "Flag - Liveness: Failed\n" +
                    "Flag - Definite Assignment: Complete\n" +
                    "Flag - Exception Flow: Complete\n" +
                    "Flag - Capture: Complete";

            String console = runCompilation("Liveness.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedDefiniteAssignment () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Complete\n" +
                    "Flag - Liveness: Complete\n" +
                    "Flag - Definite Assignment: Failed\n" +
                    "Flag - Exception Flow: Complete\n" +
                    "Flag - Capture: Complete";

            String console = runCompilation("DefiniteAssignment.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedDefiniteUnassignment () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Failed";

            String console = runCompilation("DefiniteUnassignment.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFailedExceptionFlow () {
        try {
            String expectedOutput = "Flag - Parsing: Complete\n" +
                    "Flag - Attribution: Complete\n" +
                    "Flag - Liveness: Failed\n" +
                    "Flag - Definite Assignment: Complete\n" +
                    "Flag - Exception Flow: Failed\n" +
                    "Flag - Capture: Complete";

            String console = runCompilation("ExceptionFlow.java", false);

            assertEquals(expectedOutput, console);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
