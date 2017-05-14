package csu.lzw.lmediaserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by allenzwli on 2017/5/14.
 */
public interface HomeService {
    int getSongAllCount();
    int getVideoAllCount();
}
