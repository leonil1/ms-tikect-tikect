package com.codigo.msticket.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBase<T> implements Serializable {
    private Integer code;
    private String mensaje;
    private Boolean estado;
    private T data;

    public static <T> ResponseBase<T> exitoso(String mensaje, T data) {
        return new ResponseBase<>(HttpStatus.OK.value(), mensaje, true, data);
    }

    public static ResponseBase<?> error(String mensaje, HttpStatus httpStatus) {
        return new ResponseBase<>(httpStatus.value(), mensaje, false, null);
    }
}
