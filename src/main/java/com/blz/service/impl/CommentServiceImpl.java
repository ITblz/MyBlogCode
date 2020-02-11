package com.blz.service.impl;

import com.blz.dao.CommentDao;
import com.blz.domain.Comment;
import com.blz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public long findCommentTotal() {
        return commentDao.findCommentTotal();
    }

    @Override
    public Comment findById(Integer commentId) {
        return commentDao.findById(commentId);
    }

    @Override
    public List<Comment> findByArticleId(Integer articleId) {
        return commentDao.findByArticleId(articleId);
    }

    @Override
    public long insert(Comment comment) {
        return commentDao.insert(comment);
    }

    @Override
    public Integer updateLikeCount(Comment comment) {
        comment.setChecked(1);
        return commentDao.update(comment);
    }

    @Override
    public long updateChecked(Comment comment) {
        Comment comment1 = commentDao.findById(comment.getId());
        comment1.setChecked(comment.getChecked());
        return commentDao.update(comment1);
    }

    @Override
    public List<Comment> findAllComments() {
        return commentDao.findAllComments();
    }

    @Override
    public long batchUpdateCommentCheckedFiled(List<String> ids) {
        return commentDao.batchUpdateCommentCheckedFiled(ids);
    }

    @Override
    public long batchDelete(List<String> ids) {
        return commentDao.batchDelete(ids);
    }
}
