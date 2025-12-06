package com.factory.tycoon.factory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FactoryRequest {
    private String name;
    private String location;
    private String description;
    private String phone;
}
