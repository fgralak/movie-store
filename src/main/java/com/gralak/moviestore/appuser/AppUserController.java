package com.gralak.moviestore.appuser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Api(tags = {"App User Controller"})
public class AppUserController
{
    private final AppUserService appUserService;

    @ApiOperation(value = "Get all available users", notes = "Retrieve a list of all users")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "List of users retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get all users"),
                    @ApiResponse(code = 404, message = "Users couldn't be found")}
    )
    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getUsers()
    {
        return new ResponseEntity<>(appUserService.getAllUsers(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user with a given username", notes = "Retrieve a user with a given username", response = AppUser.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "Movie with given id retrieved successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to get a user"),
                    @ApiResponse(code = 404, message = "Movie with a given id couldn't be found")}
    )
    @GetMapping
    public ResponseEntity<AppUser> getUser(@RequestParam String username)
    {
        return new ResponseEntity<>(appUserService.getUser(username), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new user", notes = "Add a new user into the system", response = AppUser.class)
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "User created successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to create a user"),
                    @ApiResponse(code = 404, message = "User couldn't be created")}
    )
    @PostMapping
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser)
    {
        return new ResponseEntity<>(appUserService.saveUser(appUser), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update existing user", notes = "Update existing user information", response = AppUser.class)
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "User information updated successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to update a user"),
                    @ApiResponse(code = 404, message = "User couldn't be updated")}
    )
    @PutMapping
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser appUser)
    {
        return new ResponseEntity<>(appUserService.updateUser(appUser), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete user with given username", notes = "Delete existing user with given username")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "User information deleted successfully"),
                    @ApiResponse(code = 401, message = "You are not authorised to delete a user"),
                    @ApiResponse(code = 404, message = "User couldn't be deleted")}
    )
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String username)
    {
        appUserService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
