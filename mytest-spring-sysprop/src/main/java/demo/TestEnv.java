package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("testEnv")
public class TestEnv {
    @Value("#{env['AA']}")
    public String AA;
}
