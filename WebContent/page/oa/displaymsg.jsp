<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.mingda.common.SessionFactory"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.hibernate.HibernateException"%>
<%@page import="org.hibernate.Transaction"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>������Ա���</title>
		<script type="text/javascript" src="../js/Calendar2.js"></script>
		<style type="text/css">
<!--
.resulttable {
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #999999;
	border-right-color: #999999;
	border-bottom-color: #999999;
	border-left-color: #999999;
}

.resulttd {
	border-top-width: 0px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #999999;
	border-right-color: #999999;
	border-bottom-color: #999999;
	border-left-color: #999999;
}
-->
</style>
	<link rel="stylesheet" href="../css/style.css" type="text/css"></link></head>
	<body>
		<div>
			<form name="form1" method="post" action="displaymsg.jsp">
				<label>
					����״̬
					<select name="questionstate">
						<option value="0">
							δ���
						</option>
						<option value="1">
							�ѽ��
						</option>
					</select>
				</label>
				<label>
					����ʱ��
					<input type="text" name="testtime" onFocus="setday(this)"
						readonly="readonly">
				</label>
				<input type="submit" name="button" id="button" value="��ѯ">
				<a href="writemsg.jsp" target="_blank">��д������Ա���</a>
		  </form>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="resulttable">
			<tr>
				<th height="25" class="resulttd" scope="col">
					����ʱ��
				</th>
				<th class="resulttd" scope="col">
					����λ��
				</th>
				<th class="resulttd" scope="col">
					��������
				</th>
				<th class="resulttd" scope="col">
					�޸Ľ���
				</th>
				<th class="resulttd" scope="col">
					����״̬
				</th>
			</tr>
			<%
				String questionstate = request.getParameter("questionstate");
				String testtime = request.getParameter("testtime");
				String subsql = "";
				if (null != testtime && !"".equals(testtime)) {
					subsql = " and trunc(opttime)=to_date('" + testtime
							+ "','yyyy-mm-dd')";
				}
				String sql = "select msgid, content, opttime, optip, location, propose, questionstate "
						+ "from message where questionstate='"
						+ questionstate
						+ "' " + subsql;
				Transaction tx = SessionFactory.getSession().beginTransaction();
				Connection con = SessionFactory.getSession().connection();
				try {
					ResultSet rs = con.prepareStatement(sql).executeQuery();
					while (rs.next()) {
			%>

			<tr>
				<td height="25" class="resulttd">
					<%=rs.getString("opttime")%>
					&nbsp;
				</td>
				<td class="resulttd"><%=rs.getString("location")%>
					&nbsp;
				</td>
				<td class="resulttd">
					<%=rs.getString("content")%>
					&nbsp;
				</td>
				<td class="resulttd"><%=rs.getString("propose")%>
					&nbsp;
				</td>
				<td class="resulttd">
					<%
						String tmp = rs.getString("questionstate");
								if (null != tmp && "1".equals(tmp)) {
									out.print("�����");
								} else {
									out.print("δ���");
								}
					%>
					&nbsp;
				</td>
			</tr>
			<%
				}
				} catch (HibernateException e1) {
					tx.rollback();
					e1.printStackTrace();
				} catch (SQLException e) {
					tx.rollback();
					e.printStackTrace();
				}
			%>
		</table>
	</body>
</html>
