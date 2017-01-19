package hello;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {
    public static MetricRegistry metrics = new MetricRegistry();
    static {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                                                  .convertRatesTo(TimeUnit.SECONDS)
                                                  .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                  .build();
        JmxReporter jmxReporter = JmxReporter.forRegistry(metrics)
                                       .convertRatesTo(TimeUnit.SECONDS)
                                       .convertDurationsTo(TimeUnit.MILLISECONDS)
                                       .build();
        reporter.start(5, TimeUnit.SECONDS);
        jmxReporter.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
