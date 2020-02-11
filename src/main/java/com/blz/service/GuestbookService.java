package com.blz.service;

import com.blz.domain.Guestbook;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface GuestbookService {

    public List<Guestbook> findGuestbookList(Integer offset, Integer rows);

    /**
     * 查询审核成功的留言
     * @param offset
     * @param rows
     * @return
     */
    public List<Guestbook> findGuestbookListByChecked(Integer offset, Integer rows);

    public Integer insert(Guestbook guestbook);

    public long batchDelete(List<String> ids);

    public long batchUpdateGuestbookCheckedFiled(List<String> idList);
}
