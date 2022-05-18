package com.test.starwarsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static java.util.Objects.requireNonNull;

@SpringBootTest
public abstract class BaseTest {

    private final ClassLoader classLoader = this.getClass().getClassLoader();

    @SneakyThrows
    protected  <T> T loadFile(Class<T> t, String jsonName) {
        File file = new File(requireNonNull(classLoader.getResource(String.format("json/%s.json", jsonName))).getPath());
        return new ObjectMapper().readValue(file, t);
    }

}
