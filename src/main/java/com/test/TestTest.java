package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.pojo.Student;
import com.pojo.Teacher;

public class TestTest {

	@Test
	public void test() throws IOException {
//		一.MyBatis 实现多表查询
//		1.Mybatis 实现多表查询方式
//			1.1 业务装配.对两个表编写单表查询语句,在业务(Service)把查询的两个结果进行关联.
//			1.2 使用Auto Mapping 特性,在实现两表联合查询时通过别名完成映射.
//			1.3 使用MyBatis 的<resultMap>标签进行实现.
//		2.多表查询时,类中包含另一个类的对象的分类
//			2.1 单个对象
//			2.2 集合对象.
		InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

//		二.resultMap 标签
//		1. <resultMap>标签写在mapper.xml中,由程序员控制SQL查询结果与实体类的映射关系.
//			1.1 默认MyBatis 使用Auto Mapping特性.
//		2. 使用<resultMap>标签时,<select>标签不写resultType属性,而是使用resultMap属性引用<resultMap>标签.
//		3. 使用resultMap 实现单表映射关系
//			3.1 数据库设计
//			3.2 实体类设计
//			3.3 mapper.xml 代码

		/* 使用resultMap 实现单表映射关系 */
		List<Teacher> list = sqlSession.selectList("com.mapper.TeacherMapper.selAll");
		for (Teacher teacher : list) {
			System.out.println(teacher);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sqlSession.close();
	}

	@Test
	public void test2() throws IOException {
		InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

//		4. 使用resultMap 实现关联单个对象(N+1 方式)
//			4.1 N+1 查询方式,先查询出某个表的全部信息,根据这个表的信息查询另一个表的信息.
//			4.2 与业务装配的区别:
//				4.2.1 在service 里面写的代码,由mybatis 完成装配
//			4.3 实现步骤:
//				4.3.1 在Student 实现类中包含了一个Teacher对象
//				4.3.2 在TeacherMapper 中提供一个查询
//				4.3.3 在StudentMapper 中
//					4.3.3.1 <association> 装配一个对象时使用
//					4.3.3.2 property: 对象在类中的属性名
//					4.3.3.3 select:通过哪个查询查询出这个对象的信息
//					4.3.3.4 column: 把当前表的哪个列的值做为参数传递给另一个查询
//					4.3.3.5 大前提使用N+1 方式时,如果列名和属性名相同可以不配置,使用Auto mapping 特性.但是mybatis 默认只会给列专配一次
//					4.3.3.6 把上面代码可以简化
		List<Student> list1 = sqlSession.selectList("com.mapper.StudentMapper.selAll");
		for (Student student : list1) {
			System.out.println(student);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sqlSession.close();

	}

	@Test
	public void test3() throws IOException {
		InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

//		5. 使用resultMap 实现关联单个对象(联合查询方式)
//			5.1 只需要编写一个SQL,在StudentMapper 中添加下面效果
//				5.1.1 <association/>只要装配一个对象就用这个标签
//				5.1.2 此时把<association/>当小的<resultMap>看待
//				5.1.3 javaType 属性:<association/>装配完后返回一个什么类型的对象.取值是一个类(或类的别名)

//		6. N+1 方式和联合查询方式对比
//		6.1 N+1:需求不确定时.
//		6.2 联合查询:需求中确定查询时两个表一定都查询.
//	7. N+1 名称由来
//		7.1 举例:学生中有3 条数据
//		7.2 需求:查询所有学生以及授课老师信息
//		7.3 需要执行的SQL 命令
//			7.3.1 查询全部学生信息:select * from 学生
//			7.3.2 执行3 遍select * from 老师where id=学生的外键
//		7.4 使用多条SQl 命令查询两表数据时,如果希望把需要的数据都查询出来,需要执行N+1 条SQl 才能把所有数据库查询出来.
//		7.5 缺点:
//			7.5.1 效率低
//		7.6 优点:
//			7.6.1 如果有的时候只需要查询学生不查询老师.只需要执行一个select * from student;
//		7.7 适用场景: 有的时候需要查询学生同时查询老师,有的时候只需要查询学生.
//		7.8 如果解决N+1 查询带来的效率低的问题
//			7.8.1 默认带的前提: 每次都是两个都查询.
//			7.8.2 使用两表联合查询.

		List<Student> list2 = sqlSession.selectList("com.mapper.StudentMapper.selAll2");
		for (Student student : list2) {
			System.out.println(student);
		}
		sqlSession.close();
	}

	@Test
	public void test4() throws IOException {
		InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

//			三.使用<resultMap>查询关联集合对象(N+1)
//			1. 在Teacher 中添加List<Student>
//			2. 在StudentMapper.xml 中添加通过tid查询
//			3. 在TeacherMapper.xml 中添加查询全部
//				3.1 <collection/> 当属性是集合类型时使用的标签.
		/* 使用<resultMap>查询关联 集合对象（表N+1次查询方式） */
		List<Teacher> list3 = sqlSession.selectList("com.mapper.TeacherMapper.selAll1");
		for (Teacher teacher : list3) {
			System.out.println(teacher);
		}
		sqlSession.close();
	}

	@Test
	public void test5() throws IOException {
		InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

//		四.使用<resultMap>实现加载集合数据(联合查询方式)
//			1.在teacherMapper.xml 中添加
//				1.1 mybatis 可以通过主键判断对象是否被加载过.
//				1.2 不需要担心创建重复Teacher
		/* 使用<resultMap>实现加载集合数据（联合查询） */
		List<Teacher> list4 = sqlSession.selectList("com.mapper.TeacherMapper.selAll2");
		for (Teacher teacher : list4) {
			System.out.println(teacher);
		}
		sqlSession.close();
	}

	public static void main(String[] args) {
		try {
			InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
			SqlSession sqlSession = sqlSessionFactory.openSession();

//			五.使用Auto Mapping 结合别名实现多表查询.
//				5.1 只能使用多表联合查询方式.
//				5.2 要求:查询出的列别和属性名相同.
//				5.3 实现方式
//					5.3.1 .在SQL是关键字符,两侧添加反单引号

			/* 使用auto mapping 结合别名实现多表查询(只能使用多表联合查询) 要求查询的列名和表字段名一样 只能单个对象，集合对象不行 */
			List<Student> list5 = sqlSession.selectList("com.mapper.StudentMapper.selAll1");
			for (Student student : list5) {
				System.out.println(student);
			}
			// mybatis注解

			sqlSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
