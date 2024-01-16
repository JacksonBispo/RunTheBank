package com.runthebank.dtos;

public record CustomerDTO(


        String name,

        String email,

        String document,

        String address,

        String password
) {
}
