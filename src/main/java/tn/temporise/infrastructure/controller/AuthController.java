package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.*;
import tn.temporise.infrastructure.api.AuthentificationApi;

public class AuthController implements AuthentificationApi {
    @Override
    public ResponseEntity<Void> _logoutUser() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<RefreshToken200Response> _refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<SigninFacebook200Response> _signinFacebook(SigninFacebookRequest signinFacebookRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<SigninGoogle200Response> _signinGoogle(SigninGoogleRequest signinGoogleRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<SigninUser200Response> _signinUser(SigninUserRequest signinUserRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _signupUser(SignupUserRequest signupUserRequest) throws Exception {
        return null;
    }
}
