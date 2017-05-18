package com.kosta.book.admin.mEmployee.model;

import java.util.List;
import com.kosta.book.admin.mEmployee.model.ManageEmployeeVO;

public interface ManageEmployeeDAO {
	public List<ManageEmployeeVO> selectAll();
	public List<ManageEmployeeVO> select(ManageEmployeeVO vo);
	public void update(ManageEmployeeVO vo);
	public void insert(ManageEmployeeVO vo);
	public void delete(ManageEmployeeVO vo);
}
