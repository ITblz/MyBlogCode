package com.blz.service;

import com.blz.domain.Article;
import com.blz.domain.Theme;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface ThemeService {

    public List<Theme> findAll();

    public Theme findById(Integer themeId);

    public Theme findByName(String themeName);

    public long insert(Theme theme);

    public long update(Theme theme);

    public long batchDelete(List<String> ids);
}
