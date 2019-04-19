package cn.witsky.smb.service;

import cn.witsky.smb.config.LogConfig;
import cn.witsky.smb.core.constant.LogCons;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class JournalWriter {

    private Map<String, BlockingQueue<String>> queues;
    private Map<String, String> types;
    private String rootPath;
    private String progId;
    private long maxSize;
    private long maxAge;
    private int maxLines;

    private static final String NAME_SEPARATOR = "_";
    private final static Format FORMAT_INDEX = new DecimalFormat("000");
    private final static String TEMP_FILE_SUFFIX = ".tmp";
    private final static String REGULAR_FILE_SUFFIX = ".txt";

    public JournalWriter(LogConfig config) {
        types = new HashMap<String, String>();
        types.put(LogCons.JOURNAL_NAME_EVENT_LOG, LogCons.JOURNAL_ID_EVENT_LOG);

        if(config.isLogSwitch()){


        }

        queues = new HashMap<String, BlockingQueue<String>>();
        for (String key : types.keySet()) {
            queues.put(key, new LinkedBlockingQueue<String>());
            new Thread(new JournalWriterRunnable(key)).start();
        }

        progId = config.getLogHh() + config.getLogIi()+ "000";
        rootPath = config.getLogPath();
        maxSize = Long.parseLong(config.getLogFilesize());
        maxAge = Integer.parseInt(config.getLogMaxage()) * 1000;
        maxLines = Integer.parseInt(config.getLogMaxlines());
    }

    public void write(String name, String content) {
        if (queues.containsKey(name)) {
            queues.get(name).offer(content);
        }
    }

    class JournalWriterRunnable implements Runnable {

        private String name;
        private int index = -1;
        private JournalFile file;
        private BlockingQueue<String> queue;
        private DateTimeFormatter DATE_FORMATTER_DD = DateTimeFormat.forPattern("dd");
        private DateTimeFormatter DATE_FORMATTER_YYYYMMDDHHMMSS = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        private String CODEUTF = "UTF-8";

        public JournalWriterRunnable(String name) {
            this.name = name;
            this.queue = queues.get(name);
            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    closeJournal();
                }
            });
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    // 从队列中获取待写数据，1秒后超时
                    String content = queue.poll(1, TimeUnit.SECONDS);
                    if (content != null) {
                        // 有数据时写入
                        writeJournal(content);
                    } else {
                        // 超时时进行老化
                        ageJournal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void writeJournal(String content) {
            if (file != null) {
                if (!DATE_FORMATTER_DD.print(System.currentTimeMillis()).equals(file.getSubDir())) {
                    // 换了一天
                    // 关闭现有文件
                    file.close();
                    // 新建一个文件
                    file = initJournalFile(-1);
                } else {
                    // 同一天
                    // 判断文件大小、寿命和行数
//                    logger.debug("write:file=" + name + ";time=" + file.getCreateTime() + ";current_time="
//                            + System.currentTimeMillis());
                    if (file.getLines() >= maxLines || file.getSize() >= maxSize
                            || System.currentTimeMillis() - file.getCreateTime() >= maxAge) {
                        file.close();
                        // 新建一个文件
                        file = initJournalFile(file.getIndex() + 1);
                    }
                }
            } else {
                // 新建一个文件
                file = initJournalFile(index);
            }
            file.write(content);
        }

        private void ageJournal() {

            if (file != null) {
                if (System.currentTimeMillis() - file.getCreateTime() >= maxAge) {
                    // 关闭文件
                    file.close();
                    // 保留索引
                    index = file.getIndex() + 1;
                    file = null;
                } else {
                    // 写入磁盘
                    file.flush();
                }
            }
        }

        private JournalFile initJournalFile(int index) {
            JournalFile info = null;
            long ms = System.currentTimeMillis();
            String time = DATE_FORMATTER_YYYYMMDDHHMMSS.print(ms);
            String subDir = DATE_FORMATTER_DD.print(ms);
            String dirName = rootPath + File.separator + name + File.separator + subDir;
            File dir = new File(dirName);
            if (!dir.exists()) {
                // 目录不存在，意味着该天还没有生成文件，直接建第一个即可
                dir.mkdirs();
                index = 1;
                String fileName = dirName + File.separator + "A" + progId + NAME_SEPARATOR + "B" + types.get(name)
                        + NAME_SEPARATOR + "C" + time + NAME_SEPARATOR + "D" + FORMAT_INDEX.format(index);
                File file = new File(fileName + TEMP_FILE_SUFFIX);
                try {
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file), CODEUTF));

                    info = new JournalFile(index, subDir, fileName, file, bw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (index >= 0) {
                // 提供了索引，不需要重新查找
                if (index > 999) {
                    index = 1;
                }
                String fileName = dirName + File.separator + "A" + progId + NAME_SEPARATOR + "B" + types.get(name)
                        + NAME_SEPARATOR + "C" + time + NAME_SEPARATOR + "D" + FORMAT_INDEX.format(index);
                File file = new File(fileName + TEMP_FILE_SUFFIX);
                try {
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file), CODEUTF));
                    info = new JournalFile(index, subDir, fileName, file, bw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 先找有没有临时文件.tmp(上次异常退出的结果)
                File[] tmpFiles = dir.listFiles(new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(TEMP_FILE_SUFFIX);
                    }

                });
                // 重命名为正常文件
                if (tmpFiles.length > 0) {
                    for (File file : tmpFiles) {
                        String s = file.getAbsolutePath();
                        file.renameTo(new File(s.replace(TEMP_FILE_SUFFIX, REGULAR_FILE_SUFFIX)));
                    }
                }
                // 查找时间戳最新的文件
                String[] files = dir.list();
                String maxIndex = "000";
                for (String s : files) {
                    String s1 = s.substring(s.length() - 7, s.length() - 4);
                    if (s1.compareTo(maxIndex) > 0) {
                        maxIndex = s1;
                    }
                }
                index = Integer.parseInt(maxIndex) + 1;
                if (index > 999) {
                    index = 1;
                }
                String fileName = dirName + File.separator + "A" + progId + NAME_SEPARATOR + "B" + types.get(name)
                        + NAME_SEPARATOR + "C" + time + NAME_SEPARATOR + "D" + FORMAT_INDEX.format(index);
                File file = new File(fileName + TEMP_FILE_SUFFIX);
                try {
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file), CODEUTF));
                    info = new JournalFile(index, subDir, fileName, file, bw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return info;
        }

        private void closeJournal() {
            if (file != null) {
                file.close();
            }
        }

        class JournalFile {

            private int index;
            private String subDir;
            private String fileName;
            private File file;
            private int lines;
            private int size;
            private long createTime;
            private BufferedWriter bw;

            public int getIndex() {
                return index;
            }

            public String getFileName() {
                return fileName;
            }

            public BufferedWriter getBw() {
                return bw;
            }

            public String getSubDir() {
                return subDir;
            }

            public File getFile() {
                return file;
            }

            public void addLines(int lines) {
                this.lines += lines;
            }

            public void addSize(int size) {
                this.size += size;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getLines() {
                return lines;
            }

            public int getSize() {
                return size;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void write(String content) {
                try {
                    bw.write(content);
                    bw.newLine();
                    addLines(1);
                    addSize(content.getBytes(Charset.defaultCharset()).length);
                    if (lines % 100 == 99) {
                        // 每100行flush一次
                        bw.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void flush() {
                if (lines > 0) {
                    try {
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void close() {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (lines > 0) {
                    // 非空
                    // 将现有文件(.tmp)重命名为正常文件(.txt)
                    file.renameTo(new File(fileName + REGULAR_FILE_SUFFIX));
                } else {
                    // 空则删除
                    file.delete();
                }
            }

            public JournalFile(int index, String subDir, String fileName, File file, BufferedWriter bw) {
                super();
                this.index = index;
                this.subDir = subDir;
                this.fileName = fileName;
                this.file = file;
                this.bw = bw;
                this.createTime = System.currentTimeMillis();
            }

        }

    }

}