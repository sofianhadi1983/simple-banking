package com.alami.sofianhcodingtest.repositories;

import com.alami.sofianhcodingtest.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
