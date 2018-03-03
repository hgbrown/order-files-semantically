package hbrown.demo;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class FilenameComparatorTest {

    // Random order
    private final List<String> filenameList = asList(
            "version-10",
            "version-2",
            "version-21",
            "version-1",
            "version-10.1"
    );

    // Turn versions into files
    private final List<File> fileList = filenameList
            .stream()
            .map(s -> "/home/user/tmp/" + s + ".sql")
            .map(File::new)
            .collect(Collectors.toList());

    @Test
    void naturalSorting() {
        final List<String> sortedFileNames = fileList.stream()
                .sorted()
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        assertThat(sortedFileNames).containsExactly(
                "/home/user/tmp/version-1.sql",
                "/home/user/tmp/version-10.1.sql",
                "/home/user/tmp/version-10.sql",
                "/home/user/tmp/version-2.sql",
                "/home/user/tmp/version-21.sql"
        );
    }

    @Test
    void semanticSorting() {
        final List<String> sortedFileNames = fileList.stream()
                .sorted(Comparator.comparing(
                        File::getName,
                        new FilenameComparator()))
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        assertThat(sortedFileNames).containsExactly(
                "/home/user/tmp/version-1.sql",
                "/home/user/tmp/version-2.sql",
                "/home/user/tmp/version-10.1.sql",
                "/home/user/tmp/version-10.sql",
                "/home/user/tmp/version-21.sql"
        );
    }
}