package com.example.restapi.responseobject;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseObject {
    private String status;
    private String message;
    private Object data;
}
