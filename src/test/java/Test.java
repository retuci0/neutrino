import com.github.retucio.neutrino.EventBus;
import com.github.retucio.neutrino.EventListener;

public class Test {

    public static EventBus EVENT_BUS = new EventBus();

    public static void main(String[] args) {
        EVENT_BUS.subscribe(new Test());
        // or EVENT_BUS.subscribe(Test.class);
        EVENT_BUS.post(new TestEvent("hi world"));
    }

    @EventListener
    // if the class is subscribed instead of an instance of it, the listener method will need
    // to be static
    private void onTestEvent(TestEvent event) {
        // should print out "hi world"
        System.out.println(event.getText());
    }
}
