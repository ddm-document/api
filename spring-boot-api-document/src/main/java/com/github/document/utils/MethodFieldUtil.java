package com.github.document.utils;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.document.common.model.FieldType;
import com.github.document.common.model.FieldTypeInfo;
import com.github.document.common.model.KVEntity;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.multipart.MultipartFile;

import com.github.document.annotation.ApiField;
import com.github.document.annotation.ApiParam;
import com.github.document.annotation.ApiResponseParam;
import com.github.document.model.vo.ParamBaseVo;
import com.github.document.model.vo.ParamChildrenVo;
import com.github.document.model.vo.ParameterVo;
import com.github.document.model.vo.ResponseVo;

public class MethodFieldUtil {

	public static void main(String[] args) {

		// Method[] ms = AppTestController.class.getDeclaredMethods();
		// for (Method method : ms) {
		// System.out.println(JSON.toJSONString(extractMothodReturnField(method,
		// null)));
		// System.out.println(JSON.toJSONString(extractMothodParamerField(method, 0,
		// null)));
		// List<ParameterVo> vos = null;

		// KVEntity<ParameterVo, List<ParameterVo>> kv =
		// FieldUtil.extractMothodParamerField(method, null, 0, "testd.cData.myD");

		// System.out.println(JSON.toJSONString(kv.getKey()));
		// System.out.println(JSON.toJSONString(kv.getValue()));
		// }

	}

	private static final int LEVEL_START = 0;
	private static final int LEVEL_END = 4;
	
	public static String checkClazzType(Class<?> cla, Type type) {

		if (cla == void.class) {
			// System.out.println("是 void");
			return null;
		} else if (cla == Object.class) {
			// System.out.println("是 Object");
			return null;
		} else if (Map.class.isAssignableFrom(cla)) {
			// map
			// System.out.println("是 map");
			return "Map";
		} else {
			Class<?> supCla = cla;
			boolean isArray = false;
			if (cla.isArray()) {
				supCla = cla.getComponentType();
				isArray = true;
			}

			String method = "";
			// 判断是不是 字符串
			if (String.class.isAssignableFrom(supCla)) {
				method = "String";
			} else if (Enum.class.isAssignableFrom(supCla)) {
				method = "Enum/Number";
			} else if (Boolean.class.isAssignableFrom(supCla) || boolean.class.isAssignableFrom(supCla)) {
				// 判断是不是布尔
				method = "Boolean";
			} else if (Number.class.isAssignableFrom(supCla) || int.class.isAssignableFrom(supCla)
					|| byte.class.isAssignableFrom(supCla) || char.class.isAssignableFrom(supCla)
					|| double.class.isAssignableFrom(supCla) || float.class.isAssignableFrom(supCla)
					|| long.class.isAssignableFrom(supCla) || short.class.isAssignableFrom(supCla)) {
				if (int.class.isAssignableFrom(supCla) || byte.class.isAssignableFrom(supCla)
						|| char.class.isAssignableFrom(supCla) || long.class.isAssignableFrom(supCla)
						|| short.class.isAssignableFrom(supCla)) {
					method = "Integer";
				} else if (double.class.isAssignableFrom(supCla) || float.class.isAssignableFrom(supCla)) {
					method = "Double";
				} else {
					// 数字
					method = "Number";
				}
			} else if (List.class.isAssignableFrom(supCla) || Set.class.isAssignableFrom(supCla)) {
				ParameterizedType pt = (ParameterizedType) type;
				if (pt.getActualTypeArguments().length > 1) {
					return null;
				}
				if (pt.getActualTypeArguments()[0] instanceof Class) {
					supCla = (Class<?>) pt.getActualTypeArguments()[0];
					// 判断是不是 字符串
					if (String.class.isAssignableFrom(supCla)) {
						method = "String";
					} else if (Enum.class.isAssignableFrom(supCla)) {
						method = "Enum/Number";
					} else if (Boolean.class.isAssignableFrom(supCla) || boolean.class.isAssignableFrom(supCla)) {
						// 判断是不是布尔
						method = "Boolean";
					} else if (Number.class.isAssignableFrom(supCla) || int.class.isAssignableFrom(supCla)
							|| byte.class.isAssignableFrom(supCla) || char.class.isAssignableFrom(supCla)
							|| double.class.isAssignableFrom(supCla) || float.class.isAssignableFrom(supCla)
							|| long.class.isAssignableFrom(supCla) || short.class.isAssignableFrom(supCla)) {

						if (int.class.isAssignableFrom(supCla) || byte.class.isAssignableFrom(supCla)
								|| char.class.isAssignableFrom(supCla) || long.class.isAssignableFrom(supCla)
								|| short.class.isAssignableFrom(supCla)) {
							method = "Integer";
						} else if (double.class.isAssignableFrom(supCla) || float.class.isAssignableFrom(supCla)) {
							method = "Double";
						} else {
							// 数字
							method = "Number";
						}
					} else {
						method = "Object";
					}

				} else {
					method = "Object";
				}
				method = "Array<" + method + ">";
			} else {
				method = "Object";
			}

			if (isArray) {
				method = "Array<" + method + ">";
			}

			return method;
		}
	}

	/**
	 * 判断是不是数组或集合类型
	 * 
	 * @author DDM 2019年11月28日
	 * @param cla
	 * @return
	 */
	public static boolean checkIsArray(Class<?> cla) {

		// 判断是不是不能反射的基本类型
		if (null == checkParamTypeIsNotKey(cla)) {
			return false;
		}
		// 判断是不是数组
		if (cla.isArray()) {
			// 数组
			return true;
		}

		if (List.class.isAssignableFrom(cla) || Set.class.isAssignableFrom(cla)) {
			return true;
		}

		return false;
	}

	public static KVEntity<Class<?>, Type> extractMyClass(Class<?> cla, Type type) {
		// Class<?> cla2 = cla;

		KVEntity<Class<?>, Type> kv = new KVEntity<>();
		// 判断是不是不能反射的基本类型
		if (null == checkParamTypeIsNotKey(cla)) {
			return null;
		}
		// 判断是不是数组
		if (cla.isArray()) {
			// 数组
			// System.out.println("是数组：" + cla + " -- " + type);
			return extractMyClass(cla.getComponentType(), type);
		} else if (List.class.isAssignableFrom(cla) || Set.class.isAssignableFrom(cla)) {
			// System.out.println("是集合：" + cla + " --- " + type);
			FieldTypeInfo info = checkType(type, null, null);
			switch (info.getClaType()) {
			case Clazz:
			case ArrayClass:
				kv.setKey(info.getClazz());
				kv.setValue(type);
				return kv;
			case ClassT:
			case ArrayClassT:
				return extractMyClass(info.getInsideClazz(), info.getGenType());
			case T:
			case ArrayT:
				return null;
			}

		}
		kv.setKey(cla);
		kv.setValue(type);
		return kv;
	}

	// 提取指定位置索引参数
	public static List<ParameterVo> extractMothodParamerField(Method method, Integer index, ApiParam[] params) {

		// System.out.println("我是接口类型：" + cla.isInterface());
		// String[] names = u.getParameterNames(method);
		// System.out.println(method.getName() + "参数类型是：" + cla + " -- " + names[0]);

		if (null == index) {
			List<ParameterVo> vos = new ArrayList<ParameterVo>();

			LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
			// 取出参数名
			String[] names = u.getParameterNames(method);
			// 取出参数
			Class<?>[] clas = method.getParameterTypes();

			for (int i = 0; i < clas.length; i++) {
				List<ParameterVo> vos2 = extractFromField(clas[i], names[i]);
				if (null != vos2 && vos2.size() > 0) {
					for (ParameterVo vo : vos2) {
						if (isDuplicate(vos, vo)) {
							continue;
						}
						if (null != params && params.length > 0) {
							for (ApiParam param : params) {
								// 注解替换
								if (param.field().startsWith(vo.getField()) || param.field().equals(vo.getField())) {
									replaceRaramerField(param, vo);
									break;
								}
							}
						}
						vos.add(vo);
					}
				}
			}

			return vos;
		} else {
			// JSON 类型
			return extractMothodParamerByRequestBody(method, index, params);
		}
	}

	private static List<ParameterVo> extractFromField(Class<?> cla, String name) {
		// System.out.println("处理：" + name + " -- " + cla);
		List<ParameterVo> vos = new ArrayList<>();

		ParameterVo vo = null;
		if (cla.isInterface()) {
			// 是接口
			if (cla.isAssignableFrom(MultipartFile.class)) {
				vo = new ParameterVo();
				vo.setField(name);
				vo.setDescribe(name);
				vo.setType("File");
				vos.add(vo);
				return vos;
			}

		} else if (cla.isArray()) {
			cla = cla.getComponentType();
			if (Number.class.isAssignableFrom(cla)) {
				vo = new ParameterVo();
				vo.setField(name);
				vo.setDescribe(name);
				vo.setType("Array<Number>");
				vos.add(vo);
				return vos;
			} else if (cla == String.class) {
				vo = new ParameterVo();
				vo.setField(name);
				vo.setDescribe(name);
				vo.setType("Array<String>");
				vos.add(vo);
				return vos;
			} else {
				// 不处理
			}
		} else if (Number.class.isAssignableFrom(cla)) {
			vo = new ParameterVo();
			vo.setField(name);
			vo.setDescribe(name);
			vo.setType("Number");
			vos.add(vo);
			return vos;
		} else if (cla == String.class) {
			vo = new ParameterVo();
			vo.setField(name);
			vo.setDescribe(name);
			vo.setType("String");
			vos.add(vo);
			return vos;
		} else {
			// 自定义类
			Class<?> supClass = cla;
			while (supClass != Object.class) {
				Field[] fields = supClass.getDeclaredFields();
				for (Field field : fields) {
					if (field.getType() instanceof Class) {
						//System.out.println("循环：" + field.getName());
						Class<?> cla2 = (Class<?>) field.getType();

						if (cla2.isArray()) {

							cla2 = cla2.getComponentType();
							if (Number.class.isAssignableFrom(cla2)) {
								// System.out.println("循环ArrayNumber：" + field.getName());
								vo = new ParameterVo();
								vo.setField(field.getName());
								vo.setDescribe(field.getName());
								vo.setType("Array<Number>");
								vos.add(vo);
							} else if (cla2.isAssignableFrom(String.class)) {
								// System.out.println("循环ArrayString：" + field.getName());
								vo = new ParameterVo();
								vo.setField(field.getName());
								vo.setDescribe(field.getName());
								vo.setType("Array<String>");
								vos.add(vo);
							} else {
								// 不处理
							}
						} else if (Number.class.isAssignableFrom(cla2)) {
							// System.out.println("循环Number：" + field.getName());
							vo = new ParameterVo();
							vo.setField(field.getName());
							vo.setDescribe(field.getName());
							vo.setType("Number");
							vos.add(vo);
						} else if (cla2 == String.class) {
							// System.out.println("循环String：" + field.getName());
							vo = new ParameterVo();
							vo.setField(field.getName());
							vo.setDescribe(field.getName());
							vo.setType("String");
							vos.add(vo);
						} else {
							// 不处理
							// System.out.println("循环不处理：" + field.getName());
						}
					}
				}
				supClass = supClass.getSuperclass();
			}
		}

		// 其他不处理
		return vos.size() > 0 ? vos : null;
	}

	/**
	 * 判断是否重复
	 * 
	 * @author DDM 2019年11月28日
	 * @param vos
	 * @param vo
	 * @return
	 */
	public static <T extends ParamBaseVo> boolean isDuplicate(List<T> vos, ParamBaseVo vo) {
		if (null != vos && vos.size() > 0 && null != vo) {
			for (int i = 0; i < vos.size(); i++) {
				if (vos.get(i).getField().equals(vo.getField())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 提取方法的JSON的类型
	 * 
	 * @author DDM 2019年11月28日
	 * @param method
	 * @param index
	 * @param params
	 * @return
	 */
	private static List<ParameterVo> extractMothodParamerByRequestBody(Method method, Integer index,
			ApiParam[] params) {
		Type genType = null;
		index = null == index ? 0 : index < 0 ? 0 : index;
		if (method.getParameterTypes().length < index + 1) {
			// System.out.println("下标不对,或没有参数");
			return null;
		}

		Class<?> cla = method.getParameterTypes()[index];
		KVEntity<Class<?>, Type> kv = extractMyClass(cla, method.getGenericParameterTypes()[index]);

		if (null == kv) {
			return null;
		}

		cla = kv.getKey();
		genType = kv.getValue();

		List<ParameterVo> vos = new ArrayList<ParameterVo>();
		// 开始循环处理返回值
		while (null != cla && !Object.class.toString().equals(cla.toString())) {
			// 返回本身的
			Field[] fields = cla.getDeclaredFields();
			for (Field fi : fields) {
				ParameterVo vo = extractField(fi, new ParameterVo(), genType,LEVEL_START);
				if (null != vo) {
					if (null != params && params.length > 0) {
						for (ApiParam param : params) { // 注解替换
							if (param.field().startsWith(vo.getField()) || param.field().equals(vo.getField())) {
								replaceRaramerField(param, vo);
								break;
							}
						}
					}
					vos.add(vo);
				}
				// System.out.println(JSON.toJSONString(vo));
			}
			cla = cla.getSuperclass();
		}

		return vos.size() == 0 ? null : vos;
	}

	public static void replaceRaramerField(ApiParam param, ParameterVo vo) {
		String[] fields = param.field().split("\\.");
		ParameterVo vo2 = vo;

		for (int i = 0; i < fields.length; i++) {
			if (fields[0].equals(vo2.getField())) {
				// 判断是不是最后一个
				if (i < fields.length - 1) {
					// 不是
					if (null != vo2.getChildren() && vo2.getChildren().size() > 0) {
						// 取第一个
						vo2 = vo2.getChildren().get(0);
					} else {
						// 未找到结束
						return;
					}
				} else {
					// 是,替换
					if (!isEmpty(param.describe())) {
						vo2.setDescribe(param.describe());
					}
					vo2.setRequired(param.required());
					vo2.setReg(param.reg());

					// 最大或最小

					vo2.setMax(2147483647 == param.max() ? null : param.max());

					vo2.setMin(-2147483648 == param.min() ? null : param.min());

					return;
				}
			}
		}
	}

	/**
	 * 提取方法上的返回值
	 * 
	 * @param method
	 * @return
	 */
	public static List<ResponseVo> extractMothodReturnField(Method method, ApiResponseParam[] params) {

		Class<?> cla = method.getReturnType();

		// 判断是不是不能反射的基本类型
		// if (null == checkParamTypeIsNotKey(cla)) {
		// return null;
		// }
		// 判断是不是数组
		// if (cla.isArray()) {
		// // 数组
		// cla = method.getReturnType().getComponentType();
		// }

		// 泛型处理
		Type genType = null;
		/*
		 * if (method.getGenericReturnType() instanceof ParameterizedType) {
		 * ParameterizedType pat = (ParameterizedType) method.getGenericReturnType();
		 * 
		 * if (pat.getActualTypeArguments().length > 1) { //
		 * System.out.println("泛型过多，为 map"); return null; } genType =
		 * pat.getActualTypeArguments()[0]; }
		 */

		KVEntity<Class<?>, Type> kv = extractMyClass(cla, method.getGenericReturnType());

		if (null == kv) {
			return null;
		}
		cla = kv.getKey();
		genType = kv.getValue();

		List<ResponseVo> vos = new ArrayList<ResponseVo>();

		// 开始循环处理返回值
		while (null != cla && !Object.class.toString().equals(cla.toString())) {
			// 返回本身的
			Field[] fields = cla.getDeclaredFields();
			for (Field fi : fields) {

				ResponseVo vo = extractField(fi, new ResponseVo(), genType,LEVEL_START);
				if (null != vo) {
					if (null != params && params.length > 0) {
						for (ApiResponseParam param : params) {
							// 注解替换
							if (param.field().startsWith(vo.getField()) || param.field().equals(vo.getField())) {
								replaceResponseField(param, vo);
								break;
							}
						}
					}
					vos.add(vo);
				}
				// System.out.println(JSON.toJSONString(vo));
			}
			cla = cla.getSuperclass();
		}

		return vos.size() > 0 ? vos : null;
	}

	public static void replaceResponseField(ApiResponseParam param, ResponseVo vo) {
		String[] fields = param.field().split("\\.");
		ResponseVo vo2 = vo;
		for (int i = 0; i < fields.length; i++) {
			if (fields[0].equals(vo2.getField())) {
				// 判断是不是最后一个
				if (i < fields.length - 1) {
					// 不是
					if (null != vo2.getChildren() && vo2.getChildren().size() > 0) {
						// 取第一个
						vo2 = vo2.getChildren().get(0);
					} else {
						// 未找到结束
						return;
					}
				} else {
					// 是,替换
					if (!isEmpty(param.describe())) {
						vo2.setDescribe(param.describe());
					}
					vo2.setRequired(param.required());
					return;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends ParamChildrenVo<?>> T extractField(Field field, T t, Type genType,int count) {
		if (count >= LEVEL_END) {
			return null;
		}
		count++;
		// 属性是静态的或Final 修饰的，不处理
		if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
			return null;
		}

		T vo = null;
		try {
			vo = (T) t.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		ApiField doc = field.getAnnotation(ApiField.class);
		// 需要隐藏
		if (null != doc && doc.hide()) {
			return null;
		}

		vo.setField(field.getName());
		vo.setDescribe(field.getName());

		if (null != doc) {
			vo.setDescribe(doc.value());
		}

		// 属性类型
		Type type = field.getGenericType();

		FieldTypeInfo info = checkType(type, genType, null);

		int checkType = checkTypeIsBaseOrString(info.getClazz());

		switch (info.getClaType()) {
		case Clazz:
			extractParamByGenType(t, vo, info.getClazz(), genType, checkType, false,count);
			break;
		case ArrayClass:
			extractParamByGenType(t, vo, info.getClazz(), genType, checkType, true,count);
			break;
		case ClassT:
			checkType = checkTypeIsBaseOrString(info.getInsideClazz());
			if (List.class.isAssignableFrom(info.getClazz()) || Set.class.isAssignableFrom(info.getClazz())) {
				extractParamByGenType(t, vo, info.getInsideClazz(), info.getGenType(), checkType, true,count);
				return vo;
			} else {
				extractParamByGenType(t, vo, info.getInsideClazz(), info.getGenType(), checkType, false,count);
			}

			break;
		case ArrayClassT:

			if (List.class.isAssignableFrom(info.getClazz()) || Set.class.isAssignableFrom(info.getClazz())) {
				checkType = checkTypeIsBaseOrString(info.getInsideClazz());
				extractParamByGenType(t, vo, info.getInsideClazz(), info.getGenType(), checkType, true,count);
				vo.setType("Array<" + vo.getType() + ">");
			} else {
				extractParamByGenType(t, vo, info.getClazz(), info.getGenType(), checkType, true,count);
			}

			break;
		default:
			checkType = checkTypeIsBaseOrString(info.getInsideClazz());
			extractParamByGenType(t, vo, info.getInsideClazz(), genType, checkType, false,count);
			break;
		}

		return vo;
	}

	/**
	 * 提取 param 属性
	 * 
	 * @author DDM 2019年11月26日
	 * @param t
	 * @param vo
	 * @param cla
	 * @param genType
	 * @param checkType
	 * @param array
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends ParamChildrenVo> void extractParamByGenType(T t, T vo, Class<?> cla, Type genType,
			int checkType, boolean array, int count) {
		
		switch (checkType) {
		case 0:
			vo.setType("Boolean");
			break;
		case 1:
			vo.setType("Integer");
			break;
		case 2:
			vo.setType("Double");
			break;
		case 3:
			vo.setType("Number");
			break;
		case 4:
			vo.setType("String");
			break;
		case 5:
			vo.setType("Enum/Number");
			break;
		default:
			// 需要才次循环
			vo.setType("Object");
			List<T> vos = new ArrayList<T>();

			Class<?> supClass = cla;
			while (null != supClass && !Object.class.equals(supClass.getClass())) {
				Field[] fields = supClass.getDeclaredFields();

				for (Field field2 : fields) {
					//System.out.println("---- " + field2.getName());
					T t2 = extractField(field2, t, genType,count);
					if (null != t2) {
						vos.add(t2);
					}
				}
				supClass = supClass.getSuperclass();
			}
			if (null != vos && vos.size() > 0) {
				vo.setChildren(vos);
			}
			break;
		}

		if (array) {
			vo.setType("Array<" + vo.getType() + ">");
		}
	}

	/**
	 * 检查类型
	 * 
	 * @author DDM 2019年11月25日
	 * @param type
	 * @param genType
	 * @return
	 */
	public static FieldTypeInfo checkType(Type type, Type genType, Boolean array) {

		FieldTypeInfo info = new FieldTypeInfo();
		Type myType = type;
		// 判断是不是泛型
		FieldType ct = checkFieldType(myType);
		info.setClaType(ct);
		ParameterizedType pt = null;
		switch (ct) {
		case Clazz:
			info.setClazz((Class<?>) type);
			info.setClaType(FieldType.Clazz);
			if (null != array && array) {
				info.setClaType(FieldType.ArrayClass);
			}
			break;
		case ArrayClass:
			info.setClazz(((Class<?>) type).getComponentType());
			info.setClaType(FieldType.ArrayClass);
			break;
		case ClassT:
			// List<?>
			pt = (ParameterizedType) myType;

			info.setClaType(FieldType.ClassT);
			if (null != array && array) {
				info.setClaType(FieldType.ArrayClassT);
			}

			info.setClazz((Class<?>) pt.getRawType());

			// 里类型
			FieldType ct2 = checkFieldType(pt.getActualTypeArguments()[0]);
			info.setInsideType(ct2);
			KVEntity<Class<?>, Type> kv = extractGenType(pt.getActualTypeArguments()[0], pt, genType);

			info.setInsideClazz(kv.getKey());
			info.setGenType(kv.getValue());

			break;
		case ArrayClassT:

			GenericArrayType gat = (GenericArrayType) type;
			pt = (ParameterizedType) gat.getGenericComponentType();

			info.setClaType(FieldType.ArrayClassT);
			info.setClazz((Class<?>) pt.getRawType());

			// 里类型
			FieldType ct3 = checkFieldType(pt.getActualTypeArguments()[0]);
			info.setInsideType(ct3);
			KVEntity<Class<?>, Type> kv2 = extractGenType(pt.getActualTypeArguments()[0], pt, genType);
			info.setInsideClazz(kv2.getKey());
			info.setGenType(kv2.getValue());

			break;
		case T:
			if (null == genType) {
				info.setClazz(Object.class);
				info.setClaType(FieldType.T);
				if (null != array && array) {
					info.setClaType(FieldType.ArrayT);
				}
			} else {
				// System.out.println(" ++++++ ");
				info = checkType(genType, null, false);
			}
			break;
		case ArrayT:
			if (null == genType) {
				info.setClazz(Object.class);
				info.setClaType(FieldType.ArrayT);
			} else {
				info = checkType(genType, null, true);
			}
			break;
		}
		return info;
	}

	/**
	 * 查询指定泛型
	 * 
	 * @param type
	 * @param genType
	 * @return
	 */
	public static KVEntity<Class<?>, Type> extractGenType(Type type, ParameterizedType pt, Type genType) {

		KVEntity<Class<?>, Type> kv = new KVEntity<>();

		FieldType ct = checkFieldType(type);
		switch (ct) {
		case Clazz:

			kv.setKey((Class<?>) type);
			if (null != pt)
				kv.setValue(pt.getActualTypeArguments()[0]);
			break;
		case ArrayClass:
			kv.setKey((Class<?>) type);
			if (null != pt)
				kv.setValue(pt.getActualTypeArguments()[0]);
			break;
		case ClassT:
			ParameterizedType type2 = (ParameterizedType) type;

			if (type2 instanceof ParameterizedType) {
				// 是泛型
				kv.setKey((Class<?>) type2.getRawType());
				kv.setValue(type2.getActualTypeArguments()[0]);
				// 集合需要进一步处理
				if (List.class.isAssignableFrom(kv.getKey()) || Set.class.isAssignableFrom(kv.getKey())) {

					return extractGenType(type2.getActualTypeArguments()[0], type2, genType);
				}
			} else {
				return extractGenType(type2, null, genType);
			}
			break;
		case ArrayClassT:
			System.out.println(type);
			GenericArrayType gat = (GenericArrayType) type;

			return extractGenType(gat.getGenericComponentType(), null, genType);

		case T:
			if (null == genType) {
				kv.setKey(Object.class);
				kv.setValue(null);
			} else {

				if (genType instanceof ParameterizedType) {
					pt = (ParameterizedType) genType;

					return extractGenType(pt.getActualTypeArguments()[0], pt, null);
				} else if (genType instanceof GenericArrayType) {
					GenericArrayType gat2 = (GenericArrayType) genType;
					return extractGenType((ParameterizedType) gat2.getGenericComponentType(), null,
							pt.getActualTypeArguments()[0]);
				}

			}
			break;
		case ArrayT:
			if (null == genType) {
				kv.setKey(Object.class);
				kv.setValue(null);
			} else {
				if (genType instanceof ParameterizedType) {
					return extractGenType((ParameterizedType) genType, null, pt.getActualTypeArguments()[0]);
				} else if (genType instanceof GenericArrayType) {
					GenericArrayType gat2 = (GenericArrayType) genType;
					return extractGenType((ParameterizedType) gat2.getGenericComponentType(), null,
							pt.getActualTypeArguments()[0]);
				}

			}
		}
		return kv;
	}

	/**
	 * 提取类型
	 * 
	 * @author DDM 2019年11月25日
	 * @param type
	 * @param ct
	 * @return
	 */
	public static Class<?> extractClassByType(Type type, FieldType ct) {
		KVEntity<Class<?>, Type> kv = new KVEntity<>();
		Class<?> cla = null;
		if (FieldType.Clazz == ct) {
			// MyClass
			// System.out.print("不是泛型");
			cla = (Class<?>) type;
		} else if (FieldType.ArrayClass == ct) {
			// MyClass[]
			// System.out.print("普通数组");
			cla = (Class<?>) type;
			// 取出具体类型： String[] 中的 String
			cla = cla.getComponentType();
		} else if (FieldType.ArrayClassT == ct) {
			// MyClass<T>[]
			// System.out.print("泛型数组");
			// 取出 MyClass
			GenericArrayType gat = (GenericArrayType) type;

			ParameterizedType pt = (ParameterizedType) gat.getGenericComponentType();

			cla = (Class<?>) pt.getRawType();
			// cla = cla.getComponentType();

		} else if (FieldType.ClassT == ct) {
			// MyClass<T>
			// System.out.print("泛型<T>");
			// 取出 MyClass
			ParameterizedType pt = (ParameterizedType) type;
			cla = (Class<?>) pt.getRawType();
		}
		kv.setKey(cla);
		// kv.setValue(ct);
		return cla;
	}

	/**
	 * 判断是不是泛型
	 * 
	 * @author DDM 2019年11月25日
	 * @param ct
	 * @return true 是 false 不是 null 泛型指定
	 */
	public static Boolean isGeneric(FieldType ct) {
		switch (ct) {
		case Clazz:
		case ArrayClass:
			return false;
		case ClassT:
		case ArrayClassT:
			return true;
		default:
			return null;
		}
	}

	/**
	 * 判断类型,不考虑二维数组
	 * 
	 * @author DDM 2019年11月25日
	 * @param type
	 * @return
	 */
	public static FieldType checkFieldType(Type type) {

		if (type instanceof ParameterizedType) {
			// MyClass<T> 普通泛型
			return FieldType.ClassT;
		} else if (type instanceof GenericArrayType) {
			// MyClass<T>[] 或 T[]
			GenericArrayType gat = (GenericArrayType) type;

			if (gat.getGenericComponentType() instanceof ParameterizedType) {
				// MyClass<T>[] 泛型数组
				return FieldType.ArrayClassT;
			} else {
				// T[] 纯泛型数组
				return FieldType.ArrayT;
			}
		} else if (type instanceof Class) {
			// MyClass
			if (((Class<?>) type).isArray()) {
				// MyClass[] 普通数组
				return FieldType.ArrayClass;
			}
			// MyClass 不是泛型
			return FieldType.Clazz;
		} else {
			// T 纯泛型
			return FieldType.T;
		}
		//
	}

	/**
	 * 判断是不是不可反射的基本类型
	 * 
	 * @param method
	 * @param cla
	 * @return
	 */
	private static Class<?> checkParamTypeIsNotKey(Class<?> cla) {
		if (checkTypeIsBaseOrString(cla) != -1) {
			// 是基本类型
			return null;
		} else if (cla == void.class) {
			// System.out.println("是 void");
			return null;
		} else if (cla == Object.class) {
			// System.out.println("是 Object");
			return null;
		} else if (Map.class.isAssignableFrom(cla)) {
			// map
			// System.out.println("是 map");
			return null;
		}
		return cla;
	}

	/**
	 * 判断对象是不是基本数据类型或String
	 * 
	 * @author DDM 2019年11月22日
	 * @param cla
	 * @return -1-> 不是基本数据类型 0->布尔 1->Integer 2->Double 3->Number 4->String 5->Enum
	 */
	public static int checkTypeIsBaseOrString(Class<?> cla) {
		// 判断是不是布尔
		if (Boolean.class.isAssignableFrom(cla) || boolean.class.isAssignableFrom(cla)) {
			return 0;
		}
		// 数字
		if (Number.class.isAssignableFrom(cla) || int.class.isAssignableFrom(cla) || byte.class.isAssignableFrom(cla)
				|| char.class.isAssignableFrom(cla) || double.class.isAssignableFrom(cla)
				|| float.class.isAssignableFrom(cla) || long.class.isAssignableFrom(cla)
				|| short.class.isAssignableFrom(cla)) {

			if (int.class.isAssignableFrom(cla) || byte.class.isAssignableFrom(cla) || char.class.isAssignableFrom(cla)
					|| long.class.isAssignableFrom(cla) || short.class.isAssignableFrom(cla)) {
				return 1;
			} else if (double.class.isAssignableFrom(cla) || float.class.isAssignableFrom(cla)) {
				return 2;
			}

			return 3;
		}
		// 判断是不是 字符串
		if (String.class.isAssignableFrom(cla)) {
			return 4;
		}
		if (Enum.class.isAssignableFrom(cla)) {
			return 5;
		}
		return -1;
	}

	/**
	 * 检查是否重复
	 * 
	 * @author DDM 2019年11月22日
	 * @param vos
	 * @param field
	 * @return
	 */
	public static <T extends ParamChildrenVo<?>> boolean checkFieldRepeat(List<T> vos, String field) {
		if (null != vos) {
			for (T vo : vos) {
				if (vo.getField().equals(field)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 从集合中提取
	 * 
	 * @author DDM 2019年11月22日
	 * @param vos
	 * @param field
	 * @return
	 */
	public static <T extends ParamBaseVo> T extractFieldByList(List<T> vos, String field) {
		if (null != vos) {
			for (T vo : vos) {
				if (vo.getField().equals(field)) {
					return vo;
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否为空
	 * 
	 * @author DDM 2019年11月22日
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str ? true : "".equals(str.trim()) ? true : false;
	}

}
