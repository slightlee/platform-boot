package com.demain.server.core.util;

import java.lang.reflect.Field;

/**
 * 自定义
 */
public final class ObectUtil {

	/**
	 * 判断类中每个属性是否都为空
	 * @param obj
	 * @return
	 */
	public static boolean checkObjFieldIsNotNull(Object obj){
		try {
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.get(obj) != null) {
					return true;
				}
			}
		}catch (IllegalAccessException e){

		}
		return false;
	}

}
