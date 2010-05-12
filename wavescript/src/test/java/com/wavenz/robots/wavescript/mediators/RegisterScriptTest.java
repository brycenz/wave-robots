package com.wavenz.robots.wavescript.mediators;

import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RegisterScriptTest {

//    private static final Pattern SCRIPT_PATTERN = Pattern.compile(".*wavescript.*", Pattern.DOTALL);
    private static final Pattern SCRIPT_PATTERN = Pattern.compile(".*#!wavescript\\.(\\w*) = <<<(.*?)>>>.*", Pattern.DOTALL);

    @Test
    public void testValidScript() {
        final String script =
            "#!wavescript.test = <<<\n" +
            "var processEvents = function(bundle) {\n" +
            "    var wavelet = bundle.getWavelet();\n" +
            "    var rootBlip = wavelet.getRootBlip();\n" +
            "    var document = rootBlip.getDocument();\n" +
            "\n" +
            "    if (bundle.wasSelfAdded()) {\n" +
            "        document.append(\"WaveScript Test\");\n" +
            "    }\n" +
            "};\n" +
            ">>>";
        Matcher matcher = SCRIPT_PATTERN.matcher(script);
        assertTrue(matcher.matches());
        assertEquals(matcher.group(1), "test");
    }

}
