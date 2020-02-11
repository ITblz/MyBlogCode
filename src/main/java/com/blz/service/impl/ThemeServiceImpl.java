package com.blz.service.impl;

import com.blz.dao.ArticleDao;
import com.blz.dao.ThemeDao;
import com.blz.domain.Article;
import com.blz.domain.Theme;
import com.blz.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("themeService")
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeDao themeDao;

    @Override
    public List<Theme> findAll() {
        return themeDao.findAll();
    }


    @Override
    public Theme findById(Integer themeId) {
        return themeDao.findById(themeId);
    }

    @Override
    public Theme findByName(String themeName) {
        return themeDao.findByName(themeName);
    }

    @Override
    public long insert(Theme theme) {
        long res = themeDao.insert(theme);
        if (res == -1){
            return -1;
        }else {
            return theme.getId();
        }
    }

    @Override
    public long update(Theme theme) {
        return themeDao.update(theme);
    }


    @Override
    public long batchDelete(List<String> ids) {
        return themeDao.batchDelete(ids);
    }
}
