package com.alami.sofianhcodingtest.services;

import com.alami.sofianhcodingtest.dtos.CreateMemberResponseDTO;
import com.alami.sofianhcodingtest.dtos.CreateMemberResquestDTO;
import com.alami.sofianhcodingtest.entities.Member;
import com.alami.sofianhcodingtest.repositories.MemberRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class MemberService implements IMemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ModelMapper modelMapper;

    @SneakyThrows
    @Override
    public CreateMemberResponseDTO createNewMember(CreateMemberResquestDTO resquestDTO) {
        Member member = convertToEntity(resquestDTO);
        Member createdMember = memberRepository.save(member);

        return convertToDto(createdMember);
    }

    private Member convertToEntity(CreateMemberResquestDTO resquestDTO) throws ParseException {
         return modelMapper.map(resquestDTO, Member.class);
    }

    private CreateMemberResponseDTO convertToDto(Member member) throws ParseException {
        return modelMapper.map(member, CreateMemberResponseDTO.class);
    }
}
