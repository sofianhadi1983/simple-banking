package com.alami.sofianhcodingtest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class CreateMemberResquestDTO implements Serializable {
    @NonNull
    private String nik;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
}
