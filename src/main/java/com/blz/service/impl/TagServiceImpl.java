package com.blz.service.impl;

import com.blz.dao.TagDao;
import com.blz.domain.Tag;
import com.blz.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(Integer tagId) {
        return tagDao.findById(tagId);
    }

    @Override
    public Tag findByName(String tagName) {
        return tagDao.findByName(tagName);
    }

    @Override
    public long insert(Tag tag) {
        long res = tagDao.insert(tag);
        if (res == -1){
            return -1;
        }else {
            return tag.getId();
        }
    }

    @Override
    public long update(Tag tag) {
        return tagDao.update(tag);
    }


    @Override
    public long batchDelete(List<String> ids) {
        return tagDao.batchDelete(ids);
    }


}
