package com.blz.service.impl;

import com.blz.dao.GuestbookDao;
import com.blz.domain.Guestbook;
import com.blz.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("guestbookService")
public class GuestbookServcieImpl implements GuestbookService {

    @Autowired
    private GuestbookDao guestbookDao;

    @Override
    public List<Guestbook> findGuestbookList(Integer offset, Integer rows) {
        return guestbookDao.findGuestbookList((offset-1)*rows,rows);
    }

    @Override
    public List<Guestbook> findGuestbookListByChecked(Integer offset, Integer rows) {
        return guestbookDao.findGuestbookListByChecked((offset-1)*rows,rows);
    }

    @Override
    public Integer insert(Guestbook guestbook) {
        return guestbookDao.insert(guestbook);
    }

    @Override
    public long batchDelete(List<String> ids) {
        return guestbookDao.batchDelete(ids);
    }

    @Override
    public long batchUpdateGuestbookCheckedFiled(List<String> idList) {
        return guestbookDao.batchUpdateGuestbookCheckedFiled(idList);
    }
}
