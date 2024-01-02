package mk.dmt.pb.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WrapperService {

    @Autowired
    private HttpService httpService;


    public void callService() {

    }
}
