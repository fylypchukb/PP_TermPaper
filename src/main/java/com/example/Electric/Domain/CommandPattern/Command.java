package com.example.Electric.Domain.CommandPattern;

import com.example.Electric.Domain.Response.RestException;

public interface Command<T> {
   T execute()throws RestException;
}
