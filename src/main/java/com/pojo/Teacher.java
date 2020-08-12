package com.pojo;

import java.util.List;

public class Teacher {
	private int id1;
	private String name1;
	private List<Student> studentList;
	@Override
	public String toString() {
		return "Teacher [id1=" + id1 + ", name1=" + name1 + ", studentList=" + studentList + "]";
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public List<Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
	

}
