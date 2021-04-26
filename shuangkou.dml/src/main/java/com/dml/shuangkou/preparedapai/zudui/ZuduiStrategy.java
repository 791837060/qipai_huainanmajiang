package com.dml.shuangkou.preparedapai.zudui;

import com.dml.shuangkou.ju.Ju;

/**
 * 组队,包括确定每个人的为位置
 * 
 * @author Neo
 *
 */
public interface ZuduiStrategy {
	public void zudui(Ju ju) throws Exception;
}
