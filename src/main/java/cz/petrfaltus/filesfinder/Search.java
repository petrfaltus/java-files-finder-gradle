package cz.petrfaltus.filesfinder;

import java.io.File;
import java.io.FileFilter;

public class Search {
    private String fileMask;

    private String result = Const.EMPTY_STRING;
    private String error = null;
    private int count = 0;

    private void runRecursive(File directory) {
        FileFilter directoryFilter = new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }

                return false;
            }
        };

        File[] subdirectories = directory.listFiles(directoryFilter);
        for (File subdirectory: subdirectories) {
            runRecursive(subdirectory);

            if (error != null) {
                result = null;
                return;
            }
        }

        FileFilter fileMaskFilter = new FileFilter() {
            public boolean accept(File file) {
                if (!file.isFile()) {
                    return false;
                }

                if (fileMask.equals(Const.EMPTY_STRING)) {
                    return true;
                }

                String fileName = file.getName();
                if (fileName.contains(fileMask)) {
                    return true;
                }

                return false;
            }
        };

        File[] files = directory.listFiles(fileMaskFilter);
        for (File file: files) {
            result += file.getPath();
            result += System.lineSeparator();

            count++;

            if (error != null) {
                result = null;
                return;
            }
        }
    }

    public void run(File rootDirectory, String searchFileMask) {
        fileMask = searchFileMask;
        runRecursive(rootDirectory);

        return;
    }

    public synchronized void cancel() {
        error = "Canceled by the user";
        return;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public int getCount() {
        return count;
    }
}
