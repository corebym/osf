package com.lvwang.osf.dao;

import java.util.List;

import com.lvwang.osf.model.Tag;

public interface TagDAO {

	int save(String tag);
	String getTagByID(int id);
	int getTagID(String tag);
	List<Tag> getTags(List<Integer> tags_id);
}
