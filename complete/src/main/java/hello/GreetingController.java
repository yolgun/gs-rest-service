package hello;

import java.util.concurrent.atomic.AtomicLong;

import com.codahale.metrics.Timer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final Timer timer = Application.metrics.timer("timer");

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Timer.Context time = timer.time();
        Greeting result = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
        time.stop();
        return result;
    }

    @RequestMapping("/v2/greeting")
    public Greeting greetingv2(@RequestParam(value="name", defaultValue="World") String name) {
        Timer.Context time = timer.time();
        Object[] arr = new Object[100000];
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Greeting result = new Greeting(counter.incrementAndGet(),
                String.format(template, name));
        time.stop();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time.stop();
        return result;
    }
}
