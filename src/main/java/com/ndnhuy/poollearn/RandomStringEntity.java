package com.ndnhuy.poollearn;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "random_strings")
@Getter
@Setter
@Builder
public class RandomStringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String randomString;
}
