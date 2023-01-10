package com.dev.geunsns.apps.post.data.entity;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.global.config.auditing.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE id = ?") // Soft Delete
@Where(clause = "is_deleted = false") // Soft Delete Filter
@Table(name = "post")
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private boolean isDeleted = false; // 삭제 여부 : 기본 값 false
    private Integer postLikeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostLikeEntity> postLikeList = new ArrayList<>();

    @Builder
    public PostEntity(Long id, String title, String body, boolean isDeleted, Integer postLikeCount, UserEntity user, List<CommentEntity> comments, List<PostLikeEntity> postLikeList) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.isDeleted = isDeleted;
        this.postLikeCount = postLikeCount;
        this.user = user;
        this.comments = comments;
        this.postLikeList = postLikeList;
    }

    public void updatePost(PostEntity update) {
        this.title = update.title;
        this.body = update.body;
    }
}
