package com.blz.service;

import com.blz.domain.Tag;

import java.util.List;

public interface TagService {

    public List<Tag> findAll();

    public Tag findById(Integer tagId);

    public Tag findByName(String tagName);

    public long insert(Tag tag);

    public long update(Tag tag);


    public long batchDelete(List<String> ids);
}
