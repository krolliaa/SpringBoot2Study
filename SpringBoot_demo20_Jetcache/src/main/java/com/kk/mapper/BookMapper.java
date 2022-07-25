package com.kk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
