package cognitivity.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * initializing spring security configuration, after this class is initialized,
 * every http request passes through spring security.
 *
 */
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

}