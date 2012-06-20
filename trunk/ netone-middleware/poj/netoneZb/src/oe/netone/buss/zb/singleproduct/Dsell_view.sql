//分销进货明细表
select sb.column3 codex,
sb.column4 productname,
sb.column8 productno,
sb.column9 cardno,
sb.column13 handlength,
sb.column11 totalweight,
sb.column12 kingweight,
sb.column14 sbcolumn14,
sb.column19 sbcolumn19,
sb.column16 sbcolumn16,
sb.column17 sbcolumn17,
sb.column23 sbcolumn23,
sb.column5 sbcolumn5,
fa.column3 fcolumn3,
fa.column12 fcolumn12,
fa1.column8 fa1column8,
sb1.column50 sb1column50,
sb1.column48 sb1column48,
fa1.column14 fa1column14,
sb1.column52 sb1column52,
sb1.column27 sb1column27,
sb.column6 sbcolumn6 
from DY_661338441749388 sb,DY_661338441749389 fa,DY_271334208897441 sb1,DY_271334208897439 fa1 
where sb.fatherlsh=fa.lsh and sb.column3=sb1.column4 and fa1.lsh=sb1.fatherlsh 