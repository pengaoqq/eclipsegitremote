package cn.goktech.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestClass {
	public static void main(String[] args) throws Exception {
		//反射 全类名
		Class stuClass= Class.forName("cn.goktech.entity.Student");
		System.out.println(stuClass);
		//创建全参数对象	获取构造器
		Constructor constructor= stuClass.getConstructor(int.class, String.class, String.class);
		System.out.println(constructor);
		//通过实体创建对象
		Student student = (Student) constructor.newInstance(1, "张三", "男");
		System.out.println(student);
		//获取字段
		Field name = stuClass.getDeclaredField("name");
		System.out.println(name);
		//解除不可接近性
		name.setAccessible(true);
		name.set(student, "王五");
		System.out.println(student.getName());
		//获取方法
		Method getName = stuClass.getMethod("getName");
		System.out.println(getName);
		Method setName = stuClass.getMethod("setName", String.class);
		setName.invoke(student, "李四");
		System.out.println(student.getName());
	}
}
