package com.blz.service;

import com.blz.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论 Service接口
 */
public interface CommentService {

    public List<Comment> findAll();

    public long findCommentTotal();

    public Comment findById(Integer commentId);

    public List<Comment> findByArticleId(Integer articleId);

    public long insert(Comment comment);

    public Integer updateLikeCount(Comment comment);

    public long updateChecked(Comment comment);

    public List<Comment> findAllComments();

    public long batchUpdateCommentCheckedFiled(List<String> ids);

    public long batchDelete(List<String> ids);

}
