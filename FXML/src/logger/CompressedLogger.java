package logger;

import controller.MainScreenController;
import java.io.Closeable;
import java.io.IOException;
import model.StudentModel;
import crawler.Crawler.STATUS;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class CompressedLogger implements Logger, Closeable {

    private FileOutputStream fos;
    private final File dir;
    private final File zip;
    private final String adress;
    private TextLogger textLogger;
    private String fileName;
    private static final int FILE_BUFFER_SIZE = 51200;

    public CompressedLogger(String adress) {
        this.adress = adress;
        dir = new File("compressedLoggerDir");
        zip = new File(this.adress);

        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
    }

    @Override
    public void log(STATUS status, StudentModel student) {
        int idx = 0;
        try (ZipOutputStream outs = new ZipOutputStream(fos = new FileOutputStream(zip));) {

            Collection<File> files = FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            idx = files.size() + 1;

            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss.SSS");
            Date date = new Date();
            this.fileName = dateFormat.format(date) + ".txt";
            textLogger = new TextLogger(dir + "/" + fileName);
            textLogger.log(status, student);

            for (File srcFile : files) {
                String filepath = srcFile.getAbsolutePath();
                String dirpath = dir.getAbsolutePath();
                String entryName = filepath.substring(dirpath.length() + 1)
                        .replace('\\', '/');
                byte[] buffer = new byte[FILE_BUFFER_SIZE];
                int bytes_read;
                ZipEntry zipEntry = new ZipEntry(entryName);
                zipEntry.setTime(srcFile.lastModified());
                try (FileInputStream ins = new FileInputStream(srcFile)) {
                    outs.putNextEntry(zipEntry);
                    while ((bytes_read = ins.read(buffer)) != -1) {
                        outs.write(buffer, 0, bytes_read);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Unable to compress zip file:" + zip);
        }
    }

    @Override
    public void log(STATUS status, int iteration) {

    }

    @Override
    public void log(STATUS status) {
    }

    @Override
    public void close() throws IOException {

    }

}
