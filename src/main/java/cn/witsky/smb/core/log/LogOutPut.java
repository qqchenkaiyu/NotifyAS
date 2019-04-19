package cn.witsky.smb.core.log;

import cn.witsky.smb.core.constant.*;
import cn.witsky.smb.core.domain.*;
import cn.witsky.smb.service.JournalWriter;
import com.alibaba.fastjson.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

/**
 * Created by admin on 2018/4/5 15:58.
 *
 * @author admin
 */
@Component
public class LogOutPut {
    private static final Logger logger = LoggerFactory.getLogger(LogOutPut.class);
    private final Environment environment;
    private String from;
    private JournalWriter journalWriter;

    @Autowired
    public LogOutPut(Environment environment, JournalWriter journalWriter) {
        this.environment = environment;
        this.from = environment.getProperty(CoreCons.SERVER_ADDRESS) + ":" + environment.getProperty(CoreCons.SERVER_PORT);
        this.journalWriter = journalWriter;
    }

    public void exportLogs(Log... logs) {
        try {
            for (Log log : logs) {
                //sender.send(logConfig.getJournalKafkaTopic(), JSON.toJSONString(log));
                journalWriter.write(LogCons.JOURNAL_NAME_EVENT_LOG, JSON.toJSONString(log));
            }
        } catch (Exception e) {
            logger.error("LogOutPut.run().EXCEPTION,cause of={}", e.getMessage(), e);
        }
    }

    public void exportLogs(Log log) {
        try {
//            sender.send(logConfig.getJournalKafkaTopic(), JSON.toJSONString(log));
            journalWriter.write(LogCons.JOURNAL_NAME_EVENT_LOG, JSON.toJSONString(log));
        } catch (Exception e) {
            logger.error("LogOutPut.run().EXCEPTION,cause of={}", e.getMessage(), e);
        }
    }

    public void exportLogs(Iterable<Log> logs) {
        try {
            if (logs == null) {
                return;
            }
            for (Log log : logs) {
//                sender.send(logConfig.getJournalKafkaTopic(), JSON.toJSONString(log));
                journalWriter.write(LogCons.JOURNAL_NAME_EVENT_LOG, JSON.toJSONString(log));
            }
        } catch (Exception e) {
            logger.error("LogOutPut.run().EXCEPTION,cause of={}", e.getMessage(), e);
        }
    }

    public Log getNewLog() {
        return getNewLog(null);
    }

    public Log getNewLog(Long time) {
        String module = environment.getProperty("witsky.module-name");

        String instanceId =environment.getProperty("witsky.module-instance-id");
        String timestamp;
        if (time != null) {
            timestamp = DateFormaters.YYYYMMDDHHMMSSSSS.print(time);
        } else {
            timestamp = DateFormaters.YYYYMMDDHHMMSSSSS.print(System.currentTimeMillis());

        }
        String ip = environment.getProperty(CoreCons.SERVER_ADDRESS);

        Log log = new Log();
        log.setTimestamp(timestamp);
        log.setModule(module);
        log.setInstance(instanceId);
        log.setIp(ip);
        return log;
    }

    public Log getImportLog(Long time) {
        Log log = getNewLog(time);

        log.setType(LogType.MSG);
        log.setDirection(DirectionEnum.IN.name());


        log.getData().setProtocol(LogCons.HTTP);
        log.getData().setIF(LogCons.IF_SERVICEAPI);
        log.getData().setPhase(LogPhase.PHASE_BOTH);

        return log;
    }

    public Log getImportLog() {
        return getImportLog(null);
    }


    public Log getRestOutLog() {
        Log log = getNewLog();
        log.setType(LogType.MSG);
        log.setDirection(DirectionEnum.OUT.name());

        log.setFrom(from);

        log.getData().setProtocol(LogCons.HTTP);
        log.getData().setIF(LogCons.IF_SERVICEAPI);
        log.getData().setPhase(LogPhase.PHASE_BOTH);

        return log;
    }

    public Log getProcessLog() {
        return getProcessLog(null, null);
    }

    public Log getProcessLog(String phase, String globalId) {
        Log log = getNewLog();
        log.setType(LogType.LOG);
        log.setDirection(DirectionEnum.INTERNAL.name());
        log.setFrom(from);
        log.setGlobalId(globalId);

        if (phase == null) {
            log.getData().setPhase(LogPhase.PHASE_ROUTINE);
        } else {
            log.getData().setPhase(phase);
        }

        return log;
    }

}
