package com.neriudon.example.app;

import com.neriudon.example.domain.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @Autowired
  SampleService service;

  @PostMapping("/sample")
  public String hello(@RequestBody String request) {
    return service.echo(request);
  }

}
