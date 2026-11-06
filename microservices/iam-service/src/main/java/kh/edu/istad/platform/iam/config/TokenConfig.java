package kh.edu.istad.platform.iam.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class TokenConfig {

    @Bean
    public OAuth2TokenCustomizer <JwtEncodingContext> TokenCustomizer() {
        return context -> {
            SecurityContext cn = SecurityContextHolder.getContext();
            Authentication authentication = cn.getAuthentication();
            System.out.print(cn);

//            if (OAuth2ParameterNames.ACCESS_TOKEN.equals(context.getTokenType().getValue())){
//                    context.getClaims().claim()
//            } else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
//                // Customize headers/claims for id_token
//            }

            var principal = authentication.getPrincipal();
            if (principal instanceof UserDetails user) {
                if (OAuth2ParameterNames.ACCESS_TOKEN.equals(context.getTokenType().getValue())) {
                    context.getClaims()
                            .claim( claim -> {
                                claim
                            });
                }
            }
        };
    }

}
