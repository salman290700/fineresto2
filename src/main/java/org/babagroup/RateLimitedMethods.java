package org.babagroup;

import io.quarkiverse.bucket4j.runtime.RateLimited;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RateLimited(bucket = "group1")
public class RateLimitedMethods {
    public String limited() {
        return "LIMITED";
    }
}
