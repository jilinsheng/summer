// JavaScript Document
// JavaScript Document By lishewen    
var theTable = document.getElementById("tablelsw");    
var totalPage = document.getElementById("spanTotalPage");    
var pageNum = document.getElementById("spanPageNum");    
   
var spanPre = document.getElementById("spanPre");    
var spanNext = document.getElementById("spanNext");    
var spanFirst = document.getElementById("spanFirst");    
var spanLast = document.getElementById("spanLast");    
   
var totalPaget = document.getElementById("spanTotalPaget");    
var pageNumt = document.getElementById("spanPageNumt");    
   
var spanPret = document.getElementById("spanPret");    
var spanNextt = document.getElementById("spanNextt");    
var spanFirstt = document.getElementById("spanFirstt");    
var spanLastt = document.getElementById("spanLastt");    
   
var numberRowsInTable = theTable.rows.length;    
var pageSize = 4;    
var page = 1;    
   
//��һҳ    
function next(){    
   
    hideTable();    
        
    currentRow = pageSize * page;    
    maxRow = currentRow + pageSize;    
    if ( maxRow > numberRowsInTable ) maxRow = numberRowsInTable;    
    for ( var i = currentRow; i< maxRow; i++ ){    
        theTable.rows[i].style.display = '';    
    }    
    page++;    
        
    if ( maxRow == numberRowsInTable ) { nextText(); lastText(); }    
    showPage();    
    preLink();    
    firstLink();    
}    
   
//��һҳ    
function pre(){    
   
    hideTable();    
        
    page--;    
        
    currentRow = pageSize * page;    
    maxRow = currentRow - pageSize;    
    if ( currentRow > numberRowsInTable ) currentRow = numberRowsInTable;    
    for ( var i = maxRow; i< currentRow; i++ ){    
        theTable.rows[i].style.display = '';    
    }    
        
        
    if ( maxRow == 0 ){ preText(); firstText(); }    
    showPage();    
    nextLink();    
    lastLink();    
}    
   
//��һҳ    
function first(){    
    hideTable();    
    page = 1;    
    for ( var i = 0; i<pageSize; i++ ){    
        theTable.rows[i].style.display = '';    
    }    
    showPage();    
        
    preText();    
    nextLink();    
    lastLink();    
}    
   
//���һҳ    
function last(){    
    hideTable();    
    page = pageCount();    
    currentRow = pageSize * (page - 1);    
    for ( var i = currentRow; i<numberRowsInTable; i++ ){    
        theTable.rows[i].style.display = '';    
    }    
    showPage();    
        
    preLink();    
    nextText();    
    firstLink();    
}    
   
function hideTable(){    
    for ( var i = 0; i<numberRowsInTable; i++ ){    
        theTable.rows[i].style.display = 'none';    
    }    
}    
   
function showPage(){    
    spanPageNum.innerHTML = page;    
    spanPageNum.innerHTML = page;    
}    
   
//�ܹ�ҳ��    
function pageCount(){    
    var count = 0;    
    if ( numberRowsInTable%pageSize != 0 ) count = 1;     
    return parseInt(numberRowsInTable/pageSize) + count;    
}    
   
//��ʾ����    
function preLink(){ spanPre.innerHTML = "<a href='javascript:pre();'>��һҳ</a>"; spanPret.innerHTML = "<a href='javascript:pre();'>��һҳ</a>";}    
function preText(){ spanPre.innerHTML = "��һҳ"; spanPret.innerHTML = "��һҳ"; }    
   
function nextLink(){ spanNext.innerHTML = "<a href='javascript:next();'>��һҳ</a>"; spanNextt.innerHTML = "<a href='javascript:next();'>��һҳ</a>";}    
function nextText(){ spanNext.innerHTML = "��һҳ"; spanNextt.innerHTML = "��һҳ";}    
   
function firstLink(){ spanFirst.innerHTML = "<a href='javascript:first();'>��һҳ</a>"; spanFirstt.innerHTML = "<a href='javascript:first();'>��һҳ</a>";}    
function firstText(){ spanFirst.innerHTML = "��һҳ"; spanFirstt.innerHTML = "��һҳ";}    
   
function lastLink(){ spanLast.innerHTML = "<a href='javascript:last();'>���һҳ</a>"; spanLastt.innerHTML = "<a href='javascript:last();'>���һҳ</a>";}    
function lastText(){ spanLast.innerHTML = "���һҳ"; spanLastt.innerHTML = "���һҳ";}    
   
//���ر��    
function hide(){    
    for ( var i = pageSize; i<numberRowsInTable; i++ ){    
        theTable.rows[i].style.display = 'none';    
    }    
        
    totalPage.innerHTML = pageCount();    
    pageNum.innerHTML = '1';    
        
    totalPaget.innerHTML = pageCount();    
    pageNumt.innerHTML = '1';    
        
    nextLink();    
    lastLink();    
}    
   
hide();  