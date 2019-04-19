package cn.witsky.smb.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangYX
 * @date 2018/6/21 15:32
 */
@Configuration
@RefreshScope
public class RestfulConfig {

  private static final Logger logger = LoggerFactory.getLogger(RestfulConfig.class);

  private final Config config;


  @Value("${witsky.http-max-conn-per-route:100}")
  private int maxConnPerRoute;

  @Value("${witsky.http-max-conn-total:1000}")
  private int maxConnTotal;

  @Autowired
  public RestfulConfig(Config config) {
    this.config = config;
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory(RequestConfig requestConfig, SocketConfig socketConfig) {
    CloseableHttpClient client = HttpClientBuilder.create()
        .setDefaultRequestConfig(requestConfig)
        .setDefaultSocketConfig(socketConfig)
        .setMaxConnPerRoute(maxConnPerRoute)
        .setMaxConnTotal(maxConnTotal)
        .build();
    return new HttpComponentsClientHttpRequestFactory(client);
  }


  // 创建用于rest调用的restTemplate
  @Bean(name = "restTemplate")
  public RestTemplate createRestTemplateForInternal() {
    RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(config.getAstoslfHttpTimeout())
        .setConnectTimeout(config.getAstoslfHttpTimeout())
            .setConnectionRequestTimeout(config.getAstoslfHttpTimeout()).build();
    SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoLinger(0).setSoKeepAlive(false).build();

    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(requestConfig, socketConfig));
    setStringHttpMessageConverterCharset(restTemplate, "utf-8");
    return restTemplate;
  }


  @Bean
  public RetryTemplate createRetryTemplate() {
    RetryTemplate r = new RetryTemplate();

    r.setBackOffPolicy(new ExponentialBackOffPolicy());

    SimpleRetryPolicy policy = new SimpleRetryPolicy(config.getAstoslfHttpMaxRetry());
    r.setRetryPolicy(policy);

    return r;
  }

  // 创建用于rest调用的restTemplate
  @Bean(name = "restTemplateForSmsSubmit")
  public RestTemplate createRestTemplateForSmsSubmit() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(
        clientHttpRequestFactory(
            config.getRestTemplateMaxTotalConnections(),
            config.getRestTemplateMaxConnectionsPerRoute(),
            config.getAstoslfHttpMaxRetry(),
            config.getSubmitTimeout(),
            config.getSubmitTimeout(),
            200
        )
    );

    setStringHttpMessageConverterCharset(restTemplate, "UTF-8");

    return restTemplate;
  }

  public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory(int maxTotalConnections,
      int connectionsPerRoute, int retrytimes, int connectTimeout, int readTimeout, int connectionRequestTimeout) {
    try {
      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            httpClientBuilder.setSSLContext(sslContext);
//            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
//                    hostnameVerifier);
      // 注册http
      // --和https请求
      Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
          .register("http", PlainConnectionSocketFactory.getSocketFactory())
          /*.register("https", sslConnectionSocketFactory)*/.build();
      // 开始设置连接池
      PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
          socketFactoryRegistry);
      // 最大连接数
      poolingHttpClientConnectionManager.setMaxTotal(maxTotalConnections);
      // 同路由并发数
      poolingHttpClientConnectionManager.setDefaultMaxPerRoute(connectionsPerRoute);

      httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
      // 重试次数
      httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retrytimes, true));

      HttpClient httpClient = httpClientBuilder.build();
      // httpClient连接配置
      HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
          httpClient);
      // 连接超时
      clientHttpRequestFactory.setConnectTimeout(connectTimeout);
      // 数据读取超时时间
      clientHttpRequestFactory.setReadTimeout(readTimeout);
      // 连接不够用的等待时间
      clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
      return clientHttpRequestFactory;
    } catch (Exception e) {
      logger.error("初始化HTTP连接池出错", e);
    }
    return null;
  }

  private void setStringHttpMessageConverterCharset(RestTemplate restTemplate, String charset) {
    // 找出并修改默认的StringHttpMessageConverter
    // 关闭Accept-Charset的输出（防止输出超长的编码列表）
    // 设置默认编码为UTF-8
    boolean stringConverterFound = false;
    for (HttpMessageConverter httpMessageConverter : restTemplate.getMessageConverters()) {
      if (httpMessageConverter instanceof StringHttpMessageConverter) {
        StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) httpMessageConverter;
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        stringHttpMessageConverter.setDefaultCharset(Charset.forName(charset));
        stringConverterFound = true;
        break;
      }
    }
    if (!stringConverterFound) {
      // 如果不存在StringHttpMessageConverter，则创建一个
      StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName(charset));
      stringHttpMessageConverter.setWriteAcceptCharset(false);
      restTemplate.getMessageConverters().add(stringHttpMessageConverter);
    }
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    fastMediaTypes.add(MediaType.TEXT_PLAIN);
    fastMediaTypes.add(MediaType.TEXT_HTML);

    fastConverter.setSupportedMediaTypes(fastMediaTypes);

    messageConverters.add(fastConverter);
  }

}
