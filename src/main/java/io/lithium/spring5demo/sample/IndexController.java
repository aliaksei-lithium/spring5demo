package io.lithium.spring5demo.sample;

import io.lithium.spring5demo.gitter.GitterClient;
import io.lithium.spring5demo.gitter.model.GitterMessage;
import io.lithium.spring5demo.gitter.GitterProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
public class IndexController {

    private final GitterClient gitterClient;
    private final GitterProperties props;

    public IndexController(GitterProperties props) {
        this.props = props;
        this.gitterClient = new GitterClient(props);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping(value = "/gitter/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<GitterMessage> messages() {
        return gitterClient.streamMessages(props.getChatRoom());
    }
}
