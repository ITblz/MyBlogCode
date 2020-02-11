package com.blz.service;

import com.blz.domain.Article;
import com.blz.domain.Sort;

import java.util.List;

public interface SortService {

    public List<Sort> findAll();

    public Sort findByName(String sortName);

    public Sort findById(Integer sortId);

    public long insert(Sort sort);

    public long update(Sort sort);

    public long batchDelete(List<String> ids);

}
