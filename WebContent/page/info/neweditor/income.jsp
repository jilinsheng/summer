<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>收入支出</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="../../css/style.css" type="text/css"></link>
		<script type="text/javascript" src="../../js/Validator.js"></script>
	</head>
	<script type="text/javascript">
	function countincome() {
		var income1 = parseFloat(inputform.income1.value);
		if (isNaN( income1)) {
			income1 = 0.0;
		}
		var income2 = parseFloat(inputform.income2.value);
		if (isNaN(income2)) {
			income2 = 0.0;
		}
		var income3 = parseFloat(inputform.income3.value);
		if (isNaN(income3)) {
			income3 = 0.0;
		}
		var income4 = parseFloat(inputform.income4.value);
		if (isNaN(income4)) {
			income4 = 0.0;
		}
		var income5 = parseFloat(inputform.income5.value);
		if (isNaN(income5)) {
			income5 = 0.0;
		}

		inputform.allincome.value = income1 + income2 + income3 + income4
				+ income5;
		inputform.avgincome.value = Math.round(((income1 + income2 + income3
				+ income4 + income5) / inputform.population.value) * 100) / 100;
		incometext.innerHTML = '<span>以上各项收入合计:' + inputform.allincome.value
				+ '元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平均收入：'
				+ inputform.avgincome.value + '</span>';
	}
	function countpayout() {
	
		var income1 = parseFloat(inputform.income1.value);
		if (isNaN(income1)) {
			income1 = 0.0;
			inputform.income1.value=0.0;
		}
		var income2 = parseFloat(inputform.income2.value);
		if (isNaN( income2)) {
			income2 = 0.0;
			inputform.income2.value=0.0;
		}
		var income3 = parseFloat(inputform.income3.value);
		if (isNaN(income3)) {
			income3 = 0.0;
			inputform.income3.value=0.0;
		}
		var income4 = parseFloat(inputform.income4.value);
		if (isNaN(income4)) {
			income4 = 0.0;
			inputform.income4.value=0.0;
		}
		var income5 = parseFloat(inputform.income5.value);
		if (isNaN(income5)) {
			income5 = 0.0;
			inputform.income5.value=0.0;
		}

		var payout1 = parseFloat(inputform.payout1.value);
		if (isNaN(payout1)) {
			payout1 = 0.0;
			inputform.payout1.value=0.0;
			
		}
		var payout2 = parseFloat(inputform.payout2.value);
		if (isNaN(payout2)) {
			payout2 = 0.0;
			inputform.payout2.value=0.0;
		}
		var payout3 = parseFloat(inputform.payout3.value);
		if (isNaN(payout3)) {
			payout3 = 0.0;
			inputform.payout3.value=0.0;
		}
		var payout4 = parseFloat(inputform.payout4.value);
		if (isNaN(payout4)) {
			payout4 = 0.0;
			inputform.payout4.value=0.0;
		}
		var payout5 = parseFloat(inputform.payout5.value);
		if (isNaN(payout5)) {
			payout5 = 0.0;
			inputform.payout5.value=0.0;
		}
		var payout6 = parseFloat(inputform.payout6.value);
		if (isNaN(payout6)) {
			payout6 = 0.0;
			inputform.payout6.value=0.0;
		}
		var payout7 = parseFloat(inputform.payout7.value);
		if (isNaN(payout7)) {
			payout7 = 0.0;
			inputform.payout7.value=0.0;
		}
		var payout8 = parseFloat(inputform.payout8.value);
		if (isNaN(payout8)) {
			payout8 = 0.0;
			inputform.payout8.value=0.0;
		}
		var payout9 = parseFloat(inputform.payout9.value);
		if (isNaN(payout9)) {
			payout9 = 0.0;
			inputform.payout9.value=0.0;
		}
		var allpayout = payout1 + payout2 + payout3 + payout4 + payout5
				+ payout6 + payout7 + payout8 + payout9;
		payouttext.innerHTML = '<span>以上各项支出合计:' + allpayout + '元</span>';
	}
	function checkform() {
		countincome();
		countpayout();
		return true;
	}
</script>
	<body onLoad="countpayout();countincome()">
		<form action="incomesave.do" method="post" name="inputform"
			onsubmit="return checkform()">
			<input type="hidden" name="payoutId" value="${payout.payoutId}" />
			<input type="hidden" name="incomeId" value="${income.familyincomeId}" />
			<input type="hidden" name="familyId" value="${familyId }" />
			<input type="hidden" name="allincome" value="${income.allincome}"
				onchange="countincome()" />
			<input type="hidden" name="avgincome" value="${income.avgincome}" />
			<input type="hidden" name="population" value="${population}"/>
			<table width="99%" border="0" class="table8" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="120">
						经营性收入					</td>
					<td width="200">
						<input name="income1" value="${income.income1}"
							onblur="CheckNumber('', this, false);countincome()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						工资性收入					</td>
					<td>
						<input name="income2" value="${income.income2}"
							onblur="CheckNumber('', this, false);countincome()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						财产性收入					</td>
					<td>
						<input name="income3" value="${income.income3}"
							onblur="CheckNumber('', this, false);countincome()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						转移性收入					</td>
					<td>
						<input name="income4" value="${income.income4}"
							onblur="CheckNumber('', this, false);countincome()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						其它收入					</td>
					<td>
						<input name="income5" value="${income.income5}"
							onblur="CheckNumber('', this, false);countincome()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<th colspan="3" id="incometext">
						以上各项收入合计：${income.allincome}元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平均收入：${income.avgincome}元					</th>
				</tr>
			</table>
			<table width="99%" border="0" class="table8">
				<tr>
					<td width="120">
						生活费支出					</td>
					<td width="200">
						<input name="payout1" value="${payout.payout1}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						租房支出					</td>
					<td>
						<input name="payout2" value="${payout.payout2}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						赡养、抚养费支出					</td>
					<td>
						<input name="payout3" value="${payout.payout3}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						学费支出					</td>
					<td>
						<input name="payout4" value="${payout.payout4}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						电费支出					</td>
					<td>
						<input name="payout5" value="${payout.payout5}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						水费支出					</td>
					<td>
						<input name="payout6" value="${payout.payout6}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						医疗费支出					</td>
					<td>
						<input name="payout7" value="${payout.payout7}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						取暖费支出					</td>
					<td>
						<input name="payout8" value="${payout.payout8}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元				    </div></td>
				</tr>
				<tr>
					<td>
						其他支出					</td>
					<td>
						<input name="payout9" value="${payout.payout9}"
							onblur="CheckNumber('', this, false);countpayout()" />					</td>
					<td>
					<div align="left">元	</div></td>
				</tr>
				<tr>
					<th colspan="3" id="payouttext">
						支出	</th>
				</tr>
			</table>
			<div align="center">
				<input type="submit" value="保存" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  class="btn" value="返回" onclick="parent.location.reload('../../intro/modifamily.jsp')"/>
			</div>
		</form>
	</body>
</html>
