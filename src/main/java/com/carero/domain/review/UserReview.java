package com.carero.domain.review;

import com.carero.domain.RoleType;
import com.carero.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false)
    private int star;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private RoleType position;
}
