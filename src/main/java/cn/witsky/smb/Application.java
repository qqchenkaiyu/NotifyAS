package cn.witsky.smb;

import cn.witsky.smb.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author HuangYX
 */
@SpringBootApplication
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	@Autowired
	public Application(Config config) {
		logger.info("config = " + config);
	}

	public static void main(String[] args) {
		if (args.length == 1 && "-v".equalsIgnoreCase(args[0])) {
			cn.witsky.common.Version.showVersion();
			return;
		}

		SpringApplication.run(Application.class, args);
	}



}
