/**
 * CLI entry for {@code scripts/generate-golden-master.sh} / {@code .ps1}.
 *
 * <pre>mvn -q exec:java -Dexec.classpathScope=test -Dexec.mainClass=GoldenMasterGenerator -Dexec.args=--write</pre>
 */
public final class GoldenMasterGenerator {

    public static void main(String[] args) throws Exception {
        GoldenMasterSupport.runGeneratorMain(args);
    }

    private GoldenMasterGenerator() {
    }
}
