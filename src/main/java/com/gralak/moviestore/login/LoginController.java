package com.gralak.moviestore.login;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Login Controller"})
public class LoginController
{
    @ApiOperation(value = "Log into your account", notes = "Log into your account to get your API KEY")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Logged in successfully"),
                    @ApiResponse(code = 404, message = "Couldn't log into your account")}
    )
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials)
    {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
