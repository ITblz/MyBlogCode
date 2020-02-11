package com.blz.service.impl;

import com.blz.dao.ArticleDao;
import com.blz.dao.SortDao;
import com.blz.domain.Article;
import com.blz.domain.Sort;
import com.blz.service.ArticleService;
import com.blz.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sortService")
public class SortServiceImpl implements SortService {

    @Autowired
    private SortDao sortDao;

    @Override
    public List<Sort> findAll() {
        return sortDao.findAll();
    }

    @Override
    public Sort findByName(String sortName) {
        return sortDao.findByName(sortName);
    }

    @Override
    public Sort findById(Integer sortId) {
        return sortDao.findById(sortId);
    }

    @Override
    public long insert(Sort sort) {
        long res = sortDao.insert(sort);
        if (res == -1){
            return -1;
        }else {
            return sort.getId();
        }
    }

    @Override
    public long update(Sort sort) {
        return sortDao.update(sort);
    }


    @Override
    public long batchDelete(List<String> ids) {
        return sortDao.batchDelete(ids);
    }
}
