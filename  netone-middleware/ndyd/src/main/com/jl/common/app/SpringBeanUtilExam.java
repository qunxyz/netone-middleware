package com.jl.common.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jl.common.SpringBeanUtil;
import com.jl.dao.CommonDAO;
import com.jl.entity.Department;
import com.jl.entity.User;

/**
 * SpringBean 工具类
 * <p>
 * <li></li>
 * </p>
 * <description></description>
 * 
 * @author zhang.chao.yi
 * @version 1.0
 * @date 2008-1-28
 */
public class SpringBeanUtilExam {

	// ----------------------------------------------------------Class Methods

	private static SpringBeanUtilExam beanUtil = null;

	private static BeanFactory factory = null;

	private static final Logger LOG = Logger
			.getLogger(SpringBeanUtilExam.class);

	public static SpringBeanUtilExam getInstance() {
		if (beanUtil == null) {
			beanUtil = new SpringBeanUtilExam();
		}
		return beanUtil;
	}

	// -------------------------------------------------------Private Variables

	private SpringBeanUtilExam() {
		if (factory == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:application-exam.xml");
			factory = (BeanFactory) context;
		}
	}

	private CommonDAO getExamDao() {
		CommonDAO dao = (CommonDAO) this.getBean("commonDAO");
		return dao;
	}

	private CommonDAO getNdydDao() {
		CommonDAO dao = (CommonDAO) SpringBeanUtil.getInstance().getBean(
				"commonDAO");
		return dao;
	}

	/**
	 * 获取Bean对象
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		return factory.getBean(name);
	}

	public void insertStudent(User data) throws Exception {
		Map map = new HashMap();
		map.put("userId", data.getUserCode());
		map.put("userName", data.getUserName());
		map.put("majorID", null);
		map.put("gradeID", null);
		map.put("stuClassID", null);
		map.put("studentGroupId", getStuGroupId(data.getDepartmentId()));
		map.put("password", null);
		map.put("sex", data.getSex());
		map.put("telphone", data.getPhone());
		map.put("cellphone", data.getPhone());
		map.put("email", data.getEmail());
		map.put("status", null);
		map.put("note", "");
		map.put("operate", "");
		map.put("operateTime", new Date());
		map.put("photo", null);

		getExamDao().insert("Exam.insertStudent", map);
	}

	public void updateStudent(User data) throws Exception {
		Map map = new HashMap();
		map.put("userId", data.getUserCode());
		map.put("userName", data.getUserName());
		map.put("majorID", null);
		map.put("gradeID", null);
		map.put("stuClassID", null);
		map.put("studentGroupId", getStuGroupId(data.getDepartmentId()));
		map.put("password", null);
		map.put("sex", data.getSex());
		map.put("telphone", data.getPhone());
		map.put("cellphone", data.getPhone());
		map.put("email", data.getEmail());
		map.put("status", null);
		map.put("note", "");
		map.put("operate", "");
		map.put("operateTime", new Date());
		map.put("photo", null);

		getExamDao().insert("Exam.updateStudent", map);
	}

	public void deleteStudent(String code) throws Exception {
		getExamDao().insert("Exam.deleteStudent", code);
	}

	public void insertStudentGroup(Department data) throws Exception {
		Map map = new HashMap();
		map.put("studentGroupCode", data.getDepartmentId());
		map.put("studentGroupName", data.getDepartmentName());
		map.put("serialNO", null);
		map.put("parentStudentGroupId",
				data.getParentDepartmentId() == null ? data
						.getParentDepartmentId() : getStuGroupId(data
						.getParentDepartmentId()));
		getExamDao().insert("Exam.insertStudentGroup", map);
	}

	public void updateStudentGroup(Department data) throws Exception {
		Map map = new HashMap();
		map.put("studentGroupCode", data.getDepartmentId());
		map.put("studentGroupName", data.getDepartmentName());
		map.put("serialNO", null);
		map.put("parentStudentGroupId",
				data.getParentDepartmentId() == null ? data
						.getParentDepartmentId() : getStuGroupId(data
						.getParentDepartmentId()));

		getExamDao().insert("Exam.updateStudentGroup", map);
	}

	public void deleteStudentGroup(String code) throws Exception {
		getExamDao().insert("Exam.deleteStudentGroup", code);
	}

	public Integer getStuGroupId(String id) throws Exception {
		return (Integer) getExamDao().findForObject("Exam.getStuGroupId", id);
	}

	public Integer getStuId(String code) throws Exception {
		return (Integer) getExamDao().findForObject("Exam.getStuId", code);
	}

	public void syncDeptToExam() throws Exception {
		Map map = new HashMap();
		map.put("departmentId", 0);
		Collection<Department> set = getNdydDao().select(
				"Department.findChildDepartment", map);
		for (Department department : set) {
			doChild(department.getDepartmentId());
		}
	}

	public void doChild(String id) throws Exception {

		Department dept = (Department) getNdydDao().findForObject(
				"Department.selectInfo", id);

		Integer stugruopid = getStuGroupId(id);

		if (stugruopid != null) {
			updateStudentGroup(dept);
		} else {
			insertStudentGroup(dept);
		}

		if (dept.getParentDepartment() != null) {
			doChild(dept.getParentDepartment().getDepartmentId());
		}
	}

	public void syncUserToExam() throws Exception {
		List list = (List) getNdydDao().select("Exam.selectAllUser", null);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			Integer userid = getStuId(user.getUserCode());
			if (userid != null) {
				updateStudent(user);
			} else {
				insertStudent(user);
			}
		}
	}

	public static void main(String[] args) {
		try {
			SpringBeanUtilExam.getInstance().syncDeptToExam();
			SpringBeanUtilExam.getInstance().syncUserToExam();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
