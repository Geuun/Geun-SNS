package com.dev.geunsns.apps.post.data.entity;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.jpaauditing.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comment SET is_deleted = true WHERE id = ?") // Soft Delete
@Where(clause = "is_deleted = false")
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private boolean isDeleted = false; // 삭제 여부 : 기본 값 false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public CommentEntity(Long id, String comment, PostEntity post, UserEntity user) {
        this.id = id;
        this.comment = comment;
        this.post = post;
        this.user = user;
    }

    // Update Comment
    public void updateComment(CommentEntity update){
        this.comment = update.comment;
    }
}
