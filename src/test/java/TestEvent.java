import com.github.retucio.neutrino.Event;

public class TestEvent extends Event {

    private String text;

    public TestEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
