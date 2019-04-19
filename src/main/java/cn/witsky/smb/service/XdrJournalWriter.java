package cn.witsky.smb.service;

import cn.witsky.smb.config.XdrConfig;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author HuangYX
 */
@Component
public class XdrJournalWriter {

    private static final DateTimeFormatter DD = DateTimeFormat.forPattern("dd");
    private static final DateTimeFormatter YYYYMMDDHHMM = DateTimeFormat.forPattern("yyyyMMddHHmm");
    private static final String CODEUTF = "utf-8";

    private Map<String, BlockingQueue<String>> queues = Maps.newHashMap();
    private Map<String, String> types = Maps.newHashMap();

    private static final String NAME_SEPARATOR = "_";

    private static String XDR_FILE = "XDR";

    private final static Format FORMAT_INDEX = new DecimalFormat("000");
    private final static String TEMP_FILE_SUFFIX = ".tmp";
    private final static String REGULAR_FILE_SUFFIX = ".txt";

    private boolean logSwitch;

    private String rootPath;

    private long maxAge;

    @Autowired
    public XdrJournalWriter(XdrConfig xdrConfig) {
        logSwitch = Optional.fromNullable(xdrConfig.getLogSwitch()).or(Boolean.TRUE);
        rootPath = Optional.fromNullable(xdrConfig.getLogPath()).or("./XDR");
        maxAge = Optional.fromNullable(xdrConfig.getLogMaxage()).or(60) * 1000;

    }

    public void addTypes(Map<String, String> typesMaps) {
        if (typesMaps == null || typesMaps.size() == 0) {
            return;
        }
        types.putAll(typesMaps);
        for (String key : types.keySet()) {
            if (!queues.containsKey(key)) {
                queues.put(key, new LinkedBlockingQueue<String>());
                new Thread(new JournalWriterRunnable(key)).start();
            }
        }
    }

    public void write(String name, String content) {
        if (logSwitch && queues.containsKey(name)) {
            queues.get(name).offer(content);
        }
    }

    class JournalWriterRunnable implements Runnable {

        private String name;
        private int index = -1;
        private JournalFile file;
        private BlockingQueue<String> queue;

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
            //对历史的临时文件进行老化
            ageHistoryJournal();

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

        private void ageHistoryJournal() {
            //>>>>>>>>>>>>>>>>>>>>>>>>
        //    System.out.println("ageHistoryJournal");
            DecimalFormat format = new DecimalFormat("00");
            //一个月最多有31天
            for (int i = 1; i <= 31; i++) {
                File dayDir = new File(rootPath + File.separator + name + File.separator + format.format(i));
                listAndRenameTmpFiles(dayDir);
            }
            //<<<<<<<<<<<<<<<<<<<<<<<<
        }

        private synchronized void writeJournal(String content) {
            if (file != null) {
                if (!YYYYMMDDHHMM.print(System.currentTimeMillis()).equals(file.getCreateMinutesStr())) {
                    // 换了一分钟
                    // 关闭现有文件
                    file.close();
                    // 新建一个文件
                    file = initJournalFile(1);
                } else {
                    //暂时不需要判断大小等

                    // 同一分钟
                    // 判断文件大小、寿命和行数
//                    if (System.currentTimeMillis() - file.getCreateTime() >= maxAge) {
//                        file.close();
//                        // 新建一个文件
//                        file = initJournalFile(file.getIndex() + 1);
//                    }

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
            String time = YYYYMMDDHHMM.print(ms);
            String subDir = DD.print(ms);
            String dirName = rootPath + File.separator + name + File.separator + subDir;
//            String dirName = rootPath  + File.separator + subDir;
            File dir = new File(dirName);
            if (!dir.exists()) {
                // 目录不存在，意味着该天还没有生成文件，直接建第一个即可
                dir.mkdirs();
                index = 1;
                String fileName = dirName + File.separator + XDR_FILE + NAME_SEPARATOR + types.get(name)
                        + NAME_SEPARATOR + time;
                File file = new File(fileName + TEMP_FILE_SUFFIX);
                try {
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file, true), CODEUTF));

                    info = new JournalFile(index, subDir, fileName, file, bw, time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (index >= 0) {

                String fileName = dirName + File.separator + XDR_FILE + NAME_SEPARATOR + types.get(name)
                        + NAME_SEPARATOR + time;
                File file = new File(fileName + TEMP_FILE_SUFFIX);
                try {
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file, true), CODEUTF));
                    info = new JournalFile(index, subDir, fileName, file, bw, time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 先找有没有临时文件.tmp(上次异常退出的结果)
                listAndRenameTmpFiles(dir);

                String fileName = dirName + File.separator + XDR_FILE + NAME_SEPARATOR + types.get(name)
                        + NAME_SEPARATOR + time;
                File file = new File(fileName + TEMP_FILE_SUFFIX);
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(file, true), CODEUTF));
                    info = new JournalFile(index, subDir, fileName, file, bw, time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return info;
        }

        private void listAndRenameTmpFiles(File dir) {
           // System.out.println("listAndRenameTmpFiles");
            String time = YYYYMMDDHHMM.print(System.currentTimeMillis());

            File[] tmpFiles = dir.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(TEMP_FILE_SUFFIX);
                }

            });
            // 重命名为正常文件
            if (tmpFiles != null && tmpFiles.length > 0) {
                for (File file : tmpFiles) {
                    String parent = file.getParent();
                    String fileName = file.getName();
                    if (!fileName.contains(time)) {
                        file.renameTo(new File(parent, fileName.replace(TEMP_FILE_SUFFIX, REGULAR_FILE_SUFFIX)));
                    }
                }
            }
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
            private String createMinutesStr;
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

            public String getCreateMinutesStr() {
                return createMinutesStr;
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
                    addSize(content.getBytes().length);
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

            public JournalFile(int index, String subDir, String fileName, File file, BufferedWriter bw, String createMinutesStr) {
                super();
                this.index = index;
                this.subDir = subDir;
                this.fileName = fileName;
                this.file = file;
                this.bw = bw;
                this.createTime = System.currentTimeMillis();
                this.createMinutesStr = createMinutesStr;
            }

        }

    }

}