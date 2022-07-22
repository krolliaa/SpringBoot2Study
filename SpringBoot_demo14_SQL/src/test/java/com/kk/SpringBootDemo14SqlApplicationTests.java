package com.kk;

import com.kk.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootDemo14SqlApplicationTests {

    @Test
    void contextLoads(@Autowired JdbcTemplate jdbcTemplate) {
        String sql = "select * from TBL_BOOK";
        List<Map<String, Object>> books = jdbcTemplate.queryForList(sql);
        System.out.println(books);
    }

    @Test
    void testList(@Autowired JdbcTemplate jdbcTemplate){
        //Map 很难用我们有一套更标准的方法
        String sql = "select * from tbl_book";
        RowMapper<Book> rowMapper = new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setType(rs.getString("type"));
                book.setDescription(rs.getString("description"));
                return book;
            }
        };
        List<Book> bookList = jdbcTemplate.query(sql, rowMapper);
        System.out.println(bookList);
    }
}
