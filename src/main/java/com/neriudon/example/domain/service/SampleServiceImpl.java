package com.neriudon.example.domain.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {

  @Override
  public String echo(String body) {
    return body;
  }

}
