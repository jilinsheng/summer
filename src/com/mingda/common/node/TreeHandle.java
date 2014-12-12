package com.mingda.common.node;

import org.dom4j.Document;
import org.dom4j.Element;

public interface TreeHandle {

	/**
	 * ���ؽṹ�� <br>
	 * ������Ӧ������ʱ�������¼���ʱ
	 * 
	 * @return ��Ϣ�ṹ���γɵ�XML�ĵ����ĵ���ROOT/��Ϊ���ڵ�
	 */
	public abstract Document loadTree();

	/**
	 * ȡ���ṹ��֧
	 * 
	 * @param code
	 *            �ڵ����ƣ���Ӧ���ݱ�����CODE�ֶΣ�
	 * @return ���Ѽ��ص���Ϣ�ṹ����ȡ�����ڵ����ƵĽṹ��֧�Ŀ�¡
	 */
	public abstract Document getBranch(String code);

	/**
	 * ȡ���ṹ�ӽڵ�
	 * 
	 * @param code
	 *            �ڵ����ƣ���Ӧ���ݱ�����CODE�ֶΣ�
	 * @return ���Ѽ��ص���Ϣ�ṹ����ȡ�����ڵ����Ƶİ����ӽڵ�Ŀ�¡
	 */
	public abstract Document getChild(String code);

	/**
	 * ȡ���ṹ�ڵ�
	 * 
	 * @param code
	 *            �ڵ����ƣ���Ӧ���ݱ�����CODE�ֶΣ�
	 * @param root
	 *            ��ָ���Ľڵ���ȡָ�����͵Ľڵ�
	 * @return ���Ѽ��ص���Ϣ�ṹ����ȡ�����ڵ����ƽṹ�ڵ㣬ֻ���������ӽڵ�
	 */
	public abstract Element getNode(Element root, String code);

	/**
	 * ��������ʵ��
	 * 
	 * @param code
	 *            �ڵ����ƣ���Ӧ���ݱ�����CODE�ֶΣ�
	 * @param entityId
	 *            ��Ӧ������ʵ���ID
	 * @return ���ؽṹ�ڵ��Ӧ������ʵ��ڵ㣬�ṹ��ֻ���������ӽڵ㣨��ֵ��,�γ�����ʵ��ڵ���ɵ�XML�ĵ�
	 */
	public abstract Document selectSingleEntity(String code, Long entityId);

	/**
	 * ����ָ����������ʵ���б�
	 * 
	 * @param code
	 *            �ڵ����ƣ���Ӧ���ݱ�����CODE�ֶΣ�
	 * @param parentId
	 *            ��������ʵ��ID
	 * @return ���ؽṹ�ڵ��Ӧ������ʵ��ڵ��б��ڵ�ṹ��ֻ���������ӽڵ㣨��ֵ�����γɶ������ʵ��ڵ���ɵ�XML�ĵ�
	 */
	public abstract Document selectEntities(String code, Long parentId);

	/**
	 * ����ʵ������
	 * 
	 * @param doc
	 *            ����ʵ�����,�ṹ��selectEntities���ص�XML�ĵ���ʽ��ͬ, �����ǵ���ʵ�壬Ҳ�����Ƕ��ʵ��
	 * 
	 */
	public abstract Document saveEntity(Document doc, Long employeeId);

	public abstract Document getEntityLogs(String code, Long entityid);

	public abstract Document getLogAsXML(Long infologId);
}