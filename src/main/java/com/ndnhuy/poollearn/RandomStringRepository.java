package com.ndnhuy.poollearn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStringRepository extends JpaRepository<RandomStringEntity, Long> {

}
