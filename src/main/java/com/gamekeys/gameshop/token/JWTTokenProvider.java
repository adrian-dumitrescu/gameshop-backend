package com.gamekeys.gameshop.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gamekeys.gameshop.domain.user.AppUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.gamekeys.gameshop.constant.SecurityConstant.*;
import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    // The token is a string
    // Whenever the user logs in, we take the info, make sure they exist,
    // and then we can create a UserAppDetails.
    // We want to get all the claims for that user (All authorities/permissions)
    // This method can generate the web token once we give it the UserAppDetails (After the User has been authenticated)
    public String generateJwtToken(AppUserDetails appUserDetails){
        String[] claims = getArrayClaimsFromUser(appUserDetails);

        return JWT.create()
                .withIssuer(GAME_KEY_INC)
                .withAudience(GAME_KEY_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(appUserDetails.getUsername())
                //.withClaim(ROLES, Arrays.asList(claims))
                //.withClaim(ROLES, appUserDetails.getAuthorities().toString())
                .withArrayClaim(ROLES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));
    }

    // Once I have this list, I have to get all the authorities from the actual AppUserDetails/UserPrincipal
    private String[] getArrayClaimsFromUser(AppUserDetails appUserDetails) {
        List<String> authorities = new ArrayList<>();
        // Cred ca aici extrag din lista de autoritati sub forma de string. Pt fiecare autoritate extrag un element de tip
        // granted authority
        // Sau, pentru fiecare rol pe care il are, extrage autoritatile?

        for(GrantedAuthority grantedAuthority : appUserDetails.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    // From the token, we will need to get all the authorities.
    // We take the token, and we need to determine the authorities before we let them do anything
    public List<GrantedAuthority> getTokenAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    // Once we have this authentication, we will be able to say this user is authenticated, process his request
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new
                UsernamePasswordAuthenticationToken(username,null,authorities);

        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return usernamePasswordAuthToken;
    }

    // Check to see if the token is valid
    public boolean isTokenValid(String username, String token){
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    // Verifies if the token is past the x days
    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    // Extract the claims from the token
    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(ROLES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(GAME_KEY_INC).build();
        }catch (JWTVerificationException jwtVerificationException){
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
}
