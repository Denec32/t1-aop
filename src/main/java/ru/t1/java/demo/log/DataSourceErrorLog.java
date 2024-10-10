package ru.t1.java.demo.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceErrorLog {
    private String stacktraceExceptionText;
    private String message;
    private String methodSignature;
}
