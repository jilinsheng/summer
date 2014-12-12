package com.mingda.common.node;

import org.dom4j.Document;
import org.dom4j.Element;

public interface TreeHandle {

	/**
	 * 加载结构树 <br>
	 * 运行于应用启动时或是重新加载时
	 * 
	 * @return 信息结构树形成的XML文档，文档以ROOT/做为根节点
	 */
	public abstract Document loadTree();

	/**
	 * 取出结构分支
	 * 
	 * @param code
	 *            节点名称（对应数据表名的CODE字段）
	 * @return 从已加载的信息结构树上取给定节点名称的结构分支的克隆
	 */
	public abstract Document getBranch(String code);

	/**
	 * 取出结构子节点
	 * 
	 * @param code
	 *            节点名称（对应数据表名的CODE字段）
	 * @return 从已加载的信息结构树上取给定节点名称的包含子节点的克隆
	 */
	public abstract Document getChild(String code);

	/**
	 * 取出结构节点
	 * 
	 * @param code
	 *            节点名称（对应数据表名的CODE字段）
	 * @param root
	 *            在指定的节点上取指定类型的节点
	 * @return 从已加载的信息结构树上取给定节点名称结构节点，只包含属性子节点
	 */
	public abstract Element getNode(Element root, String code);

	/**
	 * 加载数据实体
	 * 
	 * @param code
	 *            节点名称（对应数据表名的CODE字段）
	 * @param entityId
	 *            对应的数据实体的ID
	 * @return 返回结构节点对应的数据实体节点，结构中只包含属性子节点（有值）,形成数据实体节点组成的XML文档
	 */
	public abstract Document selectSingleEntity(String code, Long entityId);

	/**
	 * 加载指定类型数据实体列表
	 * 
	 * @param code
	 *            节点名称（对应数据表名的CODE字段）
	 * @param parentId
	 *            父级数据实体ID
	 * @return 返回结构节点对应的数据实体节点列表，节点结构中只包含属性子节点（有值），形成多个数据实体节点组成的XML文档
	 */
	public abstract Document selectEntities(String code, Long parentId);

	/**
	 * 保存实体数据
	 * 
	 * @param doc
	 *            数据实体对象,结构和selectEntities返回的XML文档格式相同, 可以是单个实体，也可以是多个实体
	 * 
	 */
	public abstract Document saveEntity(Document doc, Long employeeId);

	public abstract Document getEntityLogs(String code, Long entityid);

	public abstract Document getLogAsXML(Long infologId);
}