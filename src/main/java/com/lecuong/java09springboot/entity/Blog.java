package com.lecuong.java09springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author CuongLM18
 * @created 10/08/2022 - 7:53 AM
 * @project java-09-springboot
 */
@Entity
@Table(name = "blog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog extends BaseEntity{

    @Column
    private String thumbnail;

    @Column
    private String url;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
