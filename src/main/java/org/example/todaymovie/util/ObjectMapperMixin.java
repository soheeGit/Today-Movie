package org.example.todaymovie.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperMixin {
    ObjectMapper objectMapper = new ObjectMapper();
}
