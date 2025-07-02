package com.barcode.uniplo.dao;

import com.barcode.uniplo.domain.PostDto;
import com.barcode.uniplo.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository implements PostDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.barcode.uniplo.dao.PostMapper.";

    @Override
    public int count() {
        return session.selectOne(namespace + "count");
    }

    @Override
    public int delete(Integer post_id, Integer user_id) {
        Map map = new HashMap<>();
        map.put("post_id", post_id);
        map.put("user_id", user_id);
        return session.delete(namespace + "delete", map);
    }

    @Override
    public int insert(PostDto postDto) {
        return session.insert(namespace + "insert", postDto);
    }

    @Override
    public List<PostDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }

    @Override
    public PostDto select(Integer post_id) {
        return session.selectOne(namespace + "select", post_id);
    }

    @Override
    public List<PostDto> selectPage(Map map) {
        return session.selectList(namespace + "selectPage", map);
    }

    @Override
    public int update(PostDto postDto) {
        return session.update(namespace + "update", postDto);
    }

    @Override
    public int updateCommentCount(Integer post_id, int count) {
        Map map = new HashMap();
        map.put("post_id", post_id);
        map.put("count", count);
        return session.update(namespace + "updateCommentCount", map);
    }

    @Override
    public int increaseViewCount(Integer post_id) {
        return session.update(namespace + "increaseViewCount", post_id);
    }

    @Override
    public List<PostDto> searchSelectPage(SearchCondition sc) {
        return session.selectList(namespace + "searchSelectPage", sc);
    }

    @Override
    public int searchResultCount(SearchCondition sc) {
        return session.selectOne(namespace + "searchResultCount", sc);
    }


    @Override
    public Boolean deletePost(Map map) {
        return 1== session.delete(namespace + "delete", map);
    }
}
