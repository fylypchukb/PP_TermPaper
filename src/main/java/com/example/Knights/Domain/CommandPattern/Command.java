package com.example.Knights.Domain.CommandPattern;

import com.example.Knights.Domain.Response.RestException;

public interface Command<T> {
   T execute()throws RestException;
}
