package com.sofian.codingtest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Getter
@Setter
public class CreateMemberResponseDTO implements Serializable {
    @JsonProperty("member_id")
    private Long memberId;

    private String name;

    private String nik;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;

    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    private Date updatedAt;
}
