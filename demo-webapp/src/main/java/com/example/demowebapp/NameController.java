package com.example.demowebapp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class NameController {
    @Autowired
    NameService nameService;

    @Operation(summary = "returns a name you give")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns the name",
                    content = { @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid string given for the name",
                    content = @Content) })
    @GetMapping(path = "/{name}")
    public String getName(@PathVariable String name){
        return nameService.getName(name);
    }

    @Operation(summary = "returns a name you give reversed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns the name reversed",
                    content = { @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid string given for the name to reverse",
                    content = @Content) })
    @PostMapping(path = "/{name}")
    public String revertName(@PathVariable String name){
        return nameService.revertName(name);
    }
}
