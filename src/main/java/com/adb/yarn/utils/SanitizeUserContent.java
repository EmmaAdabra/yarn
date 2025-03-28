package com.adb.yarn.utils;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SanitizeUserContent {
    public static String sanitize(String content){
        // Sanitize input: allow basic formatting and links
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        return policy.sanitize(content);
    }
}
