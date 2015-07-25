package com.lvwang.osf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.lvwang.osf.dao.TagDAO;
import com.lvwang.osf.model.Tag;

@Repository("tagDao")
public class TagDAOImpl implements TagDAO{

	private static final String TABLE = "osf_tags";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParaJdbcTemplate;
	
	public int save(final String tag) {
		final String sql = "insert into "+TABLE + "(tag) values(?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
				ps.setString(1, tag);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public String getTagByID(int id) {
		
		return null;
	}
	
	public int getTagID(final String tag) {
		final String sql = "select id from "+TABLE+" where tag=?";
		return jdbcTemplate.query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, tag);
				return ps;
			}
		}, new ResultSetExtractor<Integer>() {

			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if(rs.next()) {
					return rs.getInt("id");
				}
				return 0;
			}
		});
	}
	
	public List<Tag> getTags(List<Integer> tags_id){
		
		String sql = "select * from "+ TABLE + " where id in (:ids)";
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", tags_id);
		return namedParaJdbcTemplate.query(sql, paramMap, new RowMapper<Tag>() {

			public Tag mapRow(ResultSet rs, int row) throws SQLException {
				Tag tag = new Tag();
				tag.setId(rs.getInt("id"));
				tag.setTag(rs.getString("tag"));
				tag.setAdd_ts(rs.getTimestamp("add_ts"));
				return tag;
			}
		});
	}
	
}
